package xyz.tomasi.videosync.spring;

import io.r2dbc.spi.ConnectionFactory;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import xyz.tomasi.videosync.controller.RoomWebSocketHandler;
import xyz.tomasi.videosync.repository.RoomRepository;

@SpringBootConfiguration
public class ApplicationConfiguration {

  @Bean
  public ConnectionFactoryInitializer initializer(
    ConnectionFactory connectionFactory
  ) {
    var initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    initializer.setDatabasePopulator(
      new ResourceDatabasePopulator(new ClassPathResource("/db/init.sql"))
    );

    return initializer;
  }

  @Bean
  public HandlerMapping handlerMapping(RoomRepository roomRepository, ObjectMapper objectMapper) {
    Map<String, WebSocketHandler> map = new HashMap<>();
    map.put("/ws/room/*", new RoomWebSocketHandler(roomRepository, objectMapper));
    int order = -1; // before annotated controllers

    return new SimpleUrlHandlerMapping(map, order);
  }
}
