package xyz.tomasi.videosync.entity;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

public record Video(
  UUID id,
  URI url,
  String addedBy,
  Instant addedAt,
  int currentTimeMillis
) {
  public Video(URI url, String addedBy) {
    this(UUID.randomUUID(), url, addedBy, Instant.now(), 0);
  }
}
