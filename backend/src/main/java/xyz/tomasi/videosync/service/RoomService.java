package xyz.tomasi.videosync.service;

import java.time.Instant;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.tomasi.videosync.dto.ServerMessage;
import xyz.tomasi.videosync.entity.Participant;
import xyz.tomasi.videosync.entity.Room;
import xyz.tomasi.videosync.repository.RoomRepository;

@Service
public class RoomService {

  private static final Logger log = LoggerFactory.getLogger(RoomService.class);

  public RoomService(RoomRepository roomRepository) {
    this.roomRepository = roomRepository;
  }

  private final RoomRepository roomRepository;

  public Mono<ServerMessage> onRoomJoined(
    ObjectId roomId,
    String participantName
  ) {
    log.info("participant {} joined room {}", participantName, roomId);

    return roomRepository
      .findById(roomId)
      .flatMap(
        room -> {
          room
            .getParticipants()
            .add(new Participant(participantName, Instant.now()));
          return roomRepository.save(room);
        }
      )
      .map(
        room ->
          new ServerMessage.JoinRoomConfirmation(
            participantName,
            roomId.toHexString()
          )
      );
  }

  public Mono<Room> createRoom(String name, String initialParticipantName) {
    log.info(
      "creating room with name {} for participant {}",
      name,
      initialParticipantName
    );
    var participants = List.of(
      new Participant(initialParticipantName, Instant.now())
    );
    var room = new Room(
      null,
      name,
      Instant.now(),
      null,
      participants,
      List.of()
    );
    return roomRepository.save(room);
  }
}
