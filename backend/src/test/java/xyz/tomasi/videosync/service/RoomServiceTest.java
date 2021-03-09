package xyz.tomasi.videosync.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;
import xyz.tomasi.videosync.entity.Room;
import xyz.tomasi.videosync.repository.ParticipantRepository;
import xyz.tomasi.videosync.repository.RoomRepository;

@SpringBootTest
class RoomServiceTest {

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private ParticipantRepository participantRepository;

  @Autowired
  private RoomService roomService;

  @BeforeEach
  void beforeEach() {
    roomRepository.deleteAll();
    participantRepository.deleteAll();
  }

  @Test
  void saveRoom() {
    StepVerifier
      .create(roomService.createRoom("new-room", "person"))
      .assertNext(
        room -> {
          assertEquals(1, room.id());
          assertEquals("new-room", room.name());
        }
      )
      .verifyComplete();
  }
}
