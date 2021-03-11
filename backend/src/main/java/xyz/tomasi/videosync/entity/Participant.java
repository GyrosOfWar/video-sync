package xyz.tomasi.videosync.entity;

import java.time.Instant;

public record Participant(
  String name,
  Instant createdAt
) {
}
