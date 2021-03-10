package xyz.tomasi.videosync.dto;

import java.time.ZonedDateTime;
import java.util.List;

public record RoomDto(
  long id,
  String name,
  ZonedDateTime createdAt,
  List<ParticipantDto> participants,
  List<VideoDto> playlist
) {
}
