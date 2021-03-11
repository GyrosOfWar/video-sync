package xyz.tomasi.videosync.controller;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.tomasi.videosync.dto.RoomDto;
import xyz.tomasi.videosync.entity.Participant;
import xyz.tomasi.videosync.entity.Room;
import xyz.tomasi.videosync.entity.Video;
import xyz.tomasi.videosync.repository.RoomRepository;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

  public RoomController(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  private final RoomRepository roomRepository;

  @GetMapping
  public Flux<RoomDto> getRooms(@RequestParam(required = false) Integer count) {
    return roomRepository
      .findAll()
      .map(RoomDto::from)
      .take(count == null ? 10 : count);
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<RoomDto>> getSingleRoom(
    @PathVariable ObjectId id
  ) {
    return roomRepository
      .findById(id)
      .map(room -> ResponseEntity.ok(RoomDto.from(room)))
      .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  // create some random test data
  @PostMapping
  public Mono<Void> createRoom() {
    Participant participant = new Participant("user", Instant.now());
    Video video = new Video(
      URI.create("http://example.com"),
      "user",
      Instant.now(),
      0
    );
    Room room = new Room(
      null,
      "cool-room",
      Instant.now(),
      null,
      List.of(participant),
      List.of(video)
    );

    return roomRepository.save(room).then();
  }
}
