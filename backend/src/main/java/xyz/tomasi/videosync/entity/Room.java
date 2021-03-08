package xyz.tomasi.videosync.entity;

import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;

public record Room(
  @Id Long id,
  String name,
  ZonedDateTime createdAt,
  long activeVideo
) { }
