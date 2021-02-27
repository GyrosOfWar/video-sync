package xyz.tomasi.videosync.spring;

import io.r2dbc.spi.ConnectionFactory;
import java.util.Map;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.support.RouterFunctionMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import xyz.tomasi.videosync.controller.RoomWebSocketHandler;

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
  public HandlerMapping webSocketMapping(
    RoomWebSocketHandler webSocketHandler
  ) {
    var mapping = new SimpleUrlHandlerMapping();
    mapping.setUrlMap(Map.of("/api/ws", webSocketHandler));

    return mapping;
  }
}
