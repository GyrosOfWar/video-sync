package xyz.tomasi.videosync.entity;

import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;

public record Participant(
  @Id Long id,
  String name,
  long roomId,
  ZonedDateTime createdAt
) {
  public Participant(String name, long roomId) {
    this(null, name, roomId, ZonedDateTime.now());
  }
}
