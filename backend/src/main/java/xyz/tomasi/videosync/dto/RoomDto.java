package xyz.tomasi.videosync.dto;

import java.time.Instant;
import java.util.List;
import xyz.tomasi.videosync.entity.Participant;
import xyz.tomasi.videosync.entity.Room;
import xyz.tomasi.videosync.entity.Video;

public record RoomDto(
  String id, String name, Instant createdAt, List<Participant> participants, List<Video> videos
) {
  public static RoomDto from(Room room) {
    return new RoomDto(
      room.id().toHexString(),
      room.name(),
      room.createdAt(),
      room.participants(),
      room.videos()
    );
  }
}
