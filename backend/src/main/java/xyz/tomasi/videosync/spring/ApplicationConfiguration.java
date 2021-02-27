package xyz.tomasi.videosync.spring;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

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
}
