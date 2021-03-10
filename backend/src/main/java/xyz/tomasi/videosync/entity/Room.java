package xyz.tomasi.videosync.entity;

import java.time.Instant;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;

@Immutable
public class Room {
  @Id
  private final ObjectId id;
  private final String name;
  private final Instant createdAt;
  private final Long activeVideo;
  private final List<Participant> participants;
  private final List<Video> videos;

  public Room(ObjectId id, String name, Instant createdAt, Long activeVideo, List<Participant> participants, List<Video> videos) {
    this.id = id;
    this.name = name;
    this.createdAt = createdAt;
    this.activeVideo = activeVideo;
    this.participants = participants;
    this.videos = videos;
  }

  public ObjectId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Long getActiveVideo() {
    return activeVideo;
  }

  public List<Participant> getParticipants() {
    return participants;
  }

  public List<Video> getVideos() {
    return videos;
  }
}