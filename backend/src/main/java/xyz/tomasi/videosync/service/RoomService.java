package xyz.tomasi.videosync.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
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
  private static final List<String> ADJECTIVES = readLines(
    "/text/adjectives.txt"
  );
  private static final List<String> NOUNS = readLines("/text/nouns.txt");
  private static final Random RANDOM = new Random();

  private static List<String> readLines(String classPath) {
    var inputStream = RoomService.class.getResourceAsStream(classPath);
    try {
      var string = new String(
        inputStream.readAllBytes(),
        StandardCharsets.UTF_8
      );
      return Arrays
        .stream(string.split("\n"))
        .filter(line -> line.length() > 0)
        .collect(Collectors.toList());
    } catch (IOException e) {
      log.error("Failed to open file " + classPath + ": " + e.getMessage(), e);
      return Collections.emptyList();
    }
  }

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
            .participants()
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

  public String generateRandomName() {
    var adjective = ADJECTIVES.get(RANDOM.nextInt(ADJECTIVES.size()));
    var noun = NOUNS.get(RANDOM.nextInt(NOUNS.size()));

    return adjective + "-" + noun;
  }
}
