package xyz.tomasi.videosync.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.tomasi.videosync.dto.RoomDto;
import xyz.tomasi.videosync.entity.Participant;
import xyz.tomasi.videosync.entity.Room;
import xyz.tomasi.videosync.entity.Video;
import xyz.tomasi.videosync.repository.ParticipantRepository;
import xyz.tomasi.videosync.repository.RoomRepository;
import xyz.tomasi.videosync.repository.VideoRepository;

import java.net.URI;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

  public RoomController(RoomRepository roomRepository, VideoRepository videoRepository, ParticipantRepository participantRepository) {
    this.roomRepository = roomRepository;
    this.videoRepository = videoRepository;
    this.participantRepository = participantRepository;
  }

  private final RoomRepository roomRepository;
  private final VideoRepository videoRepository;
  private final ParticipantRepository participantRepository;

  @GetMapping
  public Flux<Room> getRooms(@RequestParam(required = false) Integer count) {
    return roomRepository.findAll().take(count == null ? 10 : count);
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<RoomDto>> getSingleRoom(@PathVariable long id) {
    return roomRepository
      .findRoom(id)
      .map(ResponseEntity::ok)
      .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @PostMapping
  public Mono<Void> createRoom() {
    Participant participant = new Participant(1L, "user", 1, ZonedDateTime.now());
    Video video = new Video(1L, URI.create("http://example.com"), participant.getId(), ZonedDateTime.now(), 0);
    Room room = new Room(1L, "cool-room", ZonedDateTime.now(), null);

    return Mono.zip(
      participantRepository.save(participant),
      videoRepository.save(video),
      roomRepository.save(room)
    ).then();
  }
}
