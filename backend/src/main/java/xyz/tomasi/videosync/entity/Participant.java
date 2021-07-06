package xyz.tomasi.videosync.entity;

import java.time.Instant;
import java.util.UUID;

public record Participant(UUID id, String name, Instant createdAt) {
  public Participant(String name) {
    this(UUID.randomUUID(), name, Instant.now());
  }
}
