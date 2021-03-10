package xyz.tomasi.videosync.entity;

import java.time.Instant;
import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;

public record Participant(
  String name,
  Instant createdAt
) {
}
