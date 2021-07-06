package xyz.tomasi.videosync.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.socket.HandshakeInfo;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class RoomWebSocketHandlerTest {

  @Autowired
  private RoomWebSocketHandler handler;

  @Test
  void testConnect() {
    var url = "http://localhost:8080/ws/room/604942312807237b0e33ca8c";
    var info = mock(HandshakeInfo.class);
    when(info.getUri()).thenReturn(URI.create(url));
    var session = mock(WebSocketSession.class);
    when(session.getHandshakeInfo()).thenReturn(info);

    //    when(session.receive()).thenReturn(
    //
    //    );

    handler.handle(session).block();
  }
}
