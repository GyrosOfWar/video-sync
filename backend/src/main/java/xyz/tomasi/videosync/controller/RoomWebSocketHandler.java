package xyz.tomasi.videosync.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import xyz.tomasi.videosync.dto.ClientMessage;
import xyz.tomasi.videosync.dto.ServerMessage;
import xyz.tomasi.videosync.repository.RoomRepository;

@Component
public class RoomWebSocketHandler implements WebSocketHandler {

  public RoomWebSocketHandler(
    RoomRepository roomRepository,
    ObjectMapper objectMapper
  ) {
    this.roomRepository = roomRepository;
    this.objectMapper = objectMapper;
  }

  private final RoomRepository roomRepository;
  private final ObjectMapper objectMapper;

  private static final Logger log = LoggerFactory.getLogger(
    RoomWebSocketHandler.class
  );

  private int getRoomId(WebSocketSession session) {
    var path = session.getHandshakeInfo().getUri().getPath();

    var segments = path.split("/");
    var lastSegment = segments[segments.length - 1];

    return Integer.parseInt(lastSegment);
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

  private Mono<ServerMessage> handleMessage(ClientMessage incoming) {
    throw new RuntimeException("not implemented");
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
            this.handleMessage(msg)
              .flatMap(response -> this.prepareResponse(response, session))
          )
      )
      .then();
  }
}
