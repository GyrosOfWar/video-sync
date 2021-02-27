package xyz.tomasi.videosync.entity;

import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;

public class Room {

  @Id
  private long id;

  private String name;
  private ZonedDateTime createdAt;
  private long activeVideo;

  public long getId() {
    return id;
  }

  public long getActiveVideo() {
    return activeVideo;
  }

  public void setActiveVideo(long activeVideo) {
    this.activeVideo = activeVideo;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(long id) {
    this.id = id;
  }
}
