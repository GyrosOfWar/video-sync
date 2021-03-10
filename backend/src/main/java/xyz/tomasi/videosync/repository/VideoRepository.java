package xyz.tomasi.videosync.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import xyz.tomasi.videosync.entity.Video;

public interface VideoRepository extends ReactiveCrudRepository<Video, Long> {
}
