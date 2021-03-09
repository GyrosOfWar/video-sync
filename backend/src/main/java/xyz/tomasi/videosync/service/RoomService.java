package xyz.tomasi.videosync.service;

import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.tomasi.videosync.dto.server.ServerMessage;
import xyz.tomasi.videosync.entity.Participant;
import xyz.tomasi.videosync.entity.Room;
import xyz.tomasi.videosync.repository.ParticipantRepository;
import xyz.tomasi.videosync.repository.RoomRepository;

@Service
public class RoomService {

  private static final Logger log = LoggerFactory.getLogger(RoomService.class);

  public RoomService(
    ParticipantRepository participantRepository,
    RoomRepository roomRepository
  ) {
    this.participantRepository = participantRepository;
    this.roomRepository = roomRepository;
  }

  private final ParticipantRepository participantRepository;
  private final RoomRepository roomRepository;

  public Mono<ServerMessage> onRoomJoined(long roomId, String participantName) {
    log.info("participant {} joined room {}", participantName, roomId);
    return participantRepository
      .save(new Participant(participantName, roomId))
      .map(
        participant ->
          new ServerMessage.JoinRoomConfirmation(participant.getId(), roomId)
      );
  }

  public Mono<Room> createRoom(String name, String initialParticipantName) {
    log.info(
      "creating room with name {} for participant {}",
      name,
      initialParticipantName
    );
    var room = new Room(null, name, ZonedDateTime.now(), null);
    return roomRepository
      .save(room)
      .flatMap(
        newRoom ->
          onRoomJoined(newRoom.id(), initialParticipantName)
            .then(Mono.just(newRoom))
      );
  }
}
