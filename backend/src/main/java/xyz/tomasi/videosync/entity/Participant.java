package xyz.tomasi.videosync.entity;

import java.time.Instant;
import java.util.UUID;

public record Participant(UUID id, String name, Instant createdAt) {
  public static Participant withName(String userName) {
    return new Participant(UUID.randomUUID(), userName, Instant.now());
  }
}
