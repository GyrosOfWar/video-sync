package xyz.tomasi.videosync.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.tomasi.videosync.entity.Room;
import xyz.tomasi.videosync.repository.RoomRepository;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

  public RoomController(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  private final RoomRepository roomRepository;

  @GetMapping
  public Flux<Room> getRooms(@RequestParam(required = false) Integer count) {
    return roomRepository.findAll().take(count == null ? 10 : count);
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<Room>> getSingleRoom(@PathVariable long id) {
    return roomRepository
      .findById(id)
      .map(ResponseEntity::ok)
      .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }
}
