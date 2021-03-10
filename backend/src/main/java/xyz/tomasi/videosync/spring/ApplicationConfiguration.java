package xyz.tomasi.videosync.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import xyz.tomasi.videosync.controller.RoomWebSocketHandler;
import xyz.tomasi.videosync.service.RoomService;

import java.util.HashMap;
import java.util.Map;

@SpringBootConfiguration
public class ApplicationConfiguration {

  @Bean
  public HandlerMapping handlerMapping(
    RoomService roomService,
    ObjectMapper objectMapper
  ) {
    Map<String, WebSocketHandler> map = new HashMap<>();
    map.put("/ws/room/*", new RoomWebSocketHandler(roomService, objectMapper));
    int order = -1; // before annotated controllers

    return new SimpleUrlHandlerMapping(map, order);
  }
}
