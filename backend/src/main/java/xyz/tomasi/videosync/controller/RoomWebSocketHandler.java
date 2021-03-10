package xyz.tomasi.videosync.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import xyz.tomasi.videosync.dto.ClientMessage;
import xyz.tomasi.videosync.dto.ServerMessage;
import xyz.tomasi.videosync.service.RoomService;

@Component
public class RoomWebSocketHandler implements WebSocketHandler {

  public RoomWebSocketHandler(
    RoomService roomService,
    ObjectMapper objectMapper
  ) {
    this.roomService = roomService;
    this.objectMapper = objectMapper;
  }

  private final RoomService roomService;
  private final ObjectMapper objectMapper;

  private static final Logger log = LoggerFactory.getLogger(
    RoomWebSocketHandler.class
  );

  private ObjectId getRoomId(WebSocketSession session) {
    var path = session.getHandshakeInfo().getUri().getPath();
    try {
      var segments = path.split("/");
      var lastSegment = segments[segments.length - 1];

      return new ObjectId(lastSegment);
    } catch (Exception e) {
      throw new IllegalArgumentException(
        String.format("Failed to parse session URL %s: %s", path, e.getMessage())
      );
    }
  }

  private ClientMessage parseMessage(WebSocketMessage message) {
    try {
      return objectMapper.readValue(
        message.getPayloadAsText(),
        ClientMessage.class
      );
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private Mono<ServerMessage> handleMessage(ObjectId roomId, ClientMessage incoming) {
    if (incoming instanceof ClientMessage.JoinRoomRequest msg) {
      return roomService.onRoomJoined(roomId, msg.participantName());
    } else {
      throw new RuntimeException("unknown message " + incoming);
    }
  }

  private Mono<WebSocketMessage> prepareResponse(
    ServerMessage message,
    WebSocketSession session
  ) {
    return Mono
      .fromSupplier(
        () -> {
          try {
            return objectMapper.writeValueAsString(message);
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        }
      )
      .map(session::textMessage);
  }

  @Override
  public Mono<Void> handle(WebSocketSession session) {
    var roomId = getRoomId(session);
    log.info("receiving session, room ID: {}", roomId);

    return session
      .receive()
      .map(this::parseMessage)
      .flatMap(
        msg ->
          session.send(
            this.handleMessage(roomId, msg)
              .flatMap(response -> this.prepareResponse(response, session))
          )
      )
      .then();
  }
}
