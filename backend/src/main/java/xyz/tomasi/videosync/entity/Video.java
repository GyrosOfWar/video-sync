package xyz.tomasi.videosync.entity;

import org.springframework.data.annotation.Id;

import java.net.URI;
import java.time.ZonedDateTime;

public class Video {
  @Id
  private final Long id;
  private final URI url;
  private final long addedBy;
  private final ZonedDateTime addedAt;
  private final int currentTimeMillis;

  public Video(Long id, URI url, long addedBy, ZonedDateTime addedAt, int currentTimeMillis) {
    this.id = id;
    this.url = url;
    this.addedBy = addedBy;
    this.addedAt = addedAt;
    this.currentTimeMillis = currentTimeMillis;
  }

  public Long getId() {
    return id;
  }

  public URI getUrl() {
    return url;
  }

  public long getAddedBy() {
    return addedBy;
  }

  public ZonedDateTime getAddedAt() {
    return addedAt;
  }

  public int getCurrentTimeMillis() {
    return currentTimeMillis;
  }
}
