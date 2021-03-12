package xyz.tomasi.videosync.spring;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import xyz.tomasi.videosync.controller.RoomWebSocketHandler;

@SpringBootConfiguration
public class ApplicationConfiguration {

  @Bean
  public HandlerMapping handlerMapping(
    RoomWebSocketHandler roomWebSocketHandler
  ) {
    Map<String, WebSocketHandler> map = new HashMap<>();
    map.put("/ws/room/*", roomWebSocketHandler);
    int order = -1; // before annotated controllers

    return new SimpleUrlHandlerMapping(map, order);
  }
}
