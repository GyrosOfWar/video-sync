package xyz.tomasi.videosync.entity;

import java.net.URI;
import java.time.Instant;

public record Video(
  URI url,
  String addedBy,
  Instant addedAt,
  int currentTimeMillis
) {
}
