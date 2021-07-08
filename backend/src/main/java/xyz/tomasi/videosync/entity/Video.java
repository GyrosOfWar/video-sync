package xyz.tomasi.videosync.entity;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

public record Video(
  UUID id, URI url, String addedBy, Instant addedAt, int currentTimeMillis
) {
  public static Video fromUrl(URI url, String addedBy) {
    return new Video(UUID.randomUUID(), url, addedBy, Instant.now(), 0);
  }
}
