package xyz.tomasi.videosync.dto;

import java.time.ZonedDateTime;

public record ParticipantDto(
  long id,
  String name,
  ZonedDateTime createdAt
) {
}
