package xyz.tomasi.videosync.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;
import xyz.tomasi.videosync.repository.ParticipantRepository;
import xyz.tomasi.videosync.repository.RoomRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
          assertEquals(1, room.getId());
          assertEquals("new-room", room.getName());
        }
      )
      .verifyComplete();
  }
}
