package xyz.tomasi.videosync.entity;

import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;

public class Room {
  @Id
  private final Long id;
  private final String name;
  private final ZonedDateTime createdAt;
  private final Long activeVideo;

  public Room(Long id, String name, ZonedDateTime createdAt, Long activeVideo) {
    this.id = id;
    this.name = name;
    this.createdAt = createdAt;
    this.activeVideo = activeVideo;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public Long getActiveVideo() {
    return activeVideo;
  }
}