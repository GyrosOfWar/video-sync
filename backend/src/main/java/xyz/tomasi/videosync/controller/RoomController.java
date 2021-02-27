package xyz.tomasi.videosync.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping("{id}")
  public Mono<Room> getSingleRoom(@PathVariable long id) {
    return roomRepository.findById(id);
  }
}
