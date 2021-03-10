package xyz.tomasi.videosync.entity;

import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;

public class Participant {

  @Id
  private final Long id;
  private final String name;
  private final long roomId;
  private final ZonedDateTime createdAt;

  public Participant(
    Long id,
    String name,
    long roomId,
    ZonedDateTime createdAt
  ) {
    this.id = id;
    this.name = name;
    this.roomId = roomId;
    this.createdAt = createdAt;
  }

  public Participant(String name, long roomId) {
    this(null, name, roomId, ZonedDateTime.now());
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public long getRoomId() {
    return roomId;
  }

  public String getName() {
    return name;
  }

  public Long getId() {
    return id;
  }
}
