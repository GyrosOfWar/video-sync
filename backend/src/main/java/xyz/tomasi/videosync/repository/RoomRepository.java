package xyz.tomasi.videosync.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import xyz.tomasi.videosync.entity.Room;

public interface RoomRepository extends ReactiveCrudRepository<Room, Long> {
    
}
