package xyz.tomasi.videosync.dto;

import java.net.URI;
import java.time.ZonedDateTime;

public record VideoDto(
  long id,
  ZonedDateTime addedAt,
  long addedBy,
  URI url
) {
}
