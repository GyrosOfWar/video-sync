package xyz.tomasi.videosync.dto;

import xyz.tomasi.videosync.entity.Participant;
import xyz.tomasi.videosync.entity.Room;
import xyz.tomasi.videosync.entity.Video;

import java.time.Instant;
import java.util.List;

public record RoomDto(
  String id,
  String name,
  Instant createdAt,
  List<Participant> participants,
  List<Video> videos
) {
  public static RoomDto from(Room room) {
    return new RoomDto(
      room.getId().toHexString(),
      room.getName(),
      room.getCreatedAt(),
      room.getParticipants(),
      room.getVideos()
    );
  }
}
