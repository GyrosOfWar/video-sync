package xyz.tomasi.videosync.controller;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RoomWebSocketHandler implements WebSocketHandler {
  private static final Logger log = LoggerFactory.getLogger(RoomWebSocketHandler.class);

  @Override
  public Mono<Void> handle(WebSocketSession session) {
    log.info("receiving session {}", session);
    return session
      .send(
          session.receive().log()
      )
      .log()
      .then();
  }
}
