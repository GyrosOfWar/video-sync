package xyz.tomasi.videosync.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import xyz.tomasi.videosync.entity.Room;

public interface RoomRepository extends ReactiveMongoRepository<Room, ObjectId>, RoomRepositoryCustom {}
