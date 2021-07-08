package xyz.tomasi.videosync.controller;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.tomasi.videosync.dto.RoomDto;
import xyz.tomasi.videosync.entity.Participant;
import xyz.tomasi.videosync.entity.Room;
import xyz.tomasi.videosync.entity.Video;
import xyz.tomasi.videosync.repository.RoomRepository;
import xyz.tomasi.videosync.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

  private static final Logger log = LoggerFactory.getLogger(
    RoomController.class
  );

  public RoomController(
    RoomRepository roomRepository,
    RoomService roomService
  ) {
    this.roomRepository = roomRepository;
    this.roomService = roomService;
  }

  private final RoomRepository roomRepository;
  private final RoomService roomService;

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
    log.info("fetching single room with id {}", id.toHexString());
    return roomRepository
      .findById(id)
      .map(room -> ResponseEntity.ok(RoomDto.from(room)))
      .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @PostMapping
  public Mono<RoomDto> createRoom() {
    var userName = roomService.generateRandomName();
    var participant = Participant.withName(userName);
    var room = new Room(
      null,
      roomService.generateRandomName(),
      Instant.now(),
      null,
      List.of(participant),
      List.of()
    );

    log.info("generated room {}", room);

    return roomRepository.save(room).map(RoomDto::from);
  }

  @PostMapping("{id}/video")
  public Mono<Void> addVideo(
    @PathVariable ObjectId id,
    @RequestParam URI url,
    @RequestParam String userId
  ) {
   log.info("adding video {} to room {} by user {}", url, id, userId);
    return roomRepository.findById(id)
      .flatMap(room -> {
        room.videos().add(Video.fromUrl(url, userId));
        return roomRepository.save(room);
      })
      .then();
  }
}
