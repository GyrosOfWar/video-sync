package xyz.tomasi.videosync.entity;

import java.time.Instant;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;

@Immutable
public record Room(@Id ObjectId id, String name,
                   Instant createdAt, Long activeVideo,
                   List<Participant> participants,
                   List<Video> videos) {
}
