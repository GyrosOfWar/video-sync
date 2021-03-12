package xyz.tomasi.videosync.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;
import xyz.tomasi.videosync.repository.RoomRepository;

@SpringBootTest
class RoomServiceTest {

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private RoomService roomService;

  @BeforeEach
  void beforeEach() {
    roomRepository.deleteAll();
  }

  @Test
  void saveRoom() {
    StepVerifier
      .create(roomService.createRoom("new-room", "person"))
      .assertNext(
        room -> {
          assertNotNull(room.id());
          assertEquals("new-room", room.name());
          assertEquals(1, room.participants().size());
        }
      )
      .verifyComplete();
  }
}
