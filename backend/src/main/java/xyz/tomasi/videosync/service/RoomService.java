package xyz.tomasi.videosync.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.tomasi.videosync.dto.client.JoinRoomRequest;
import xyz.tomasi.videosync.dto.server.JoinRoomConfirmation;
import xyz.tomasi.videosync.dto.server.ServerMessage;
import xyz.tomasi.videosync.entity.Participant;
import xyz.tomasi.videosync.repository.ParticipantRepository;

@Service
public class RoomService {

  private static final Logger log = LoggerFactory.getLogger(RoomService.class);

  private final ParticipantRepository participantRepository;

  public RoomService(ParticipantRepository participantRepository) {
    this.participantRepository = participantRepository;
  }

  public Mono<ServerMessage> onRoomJoined(long roomId, JoinRoomRequest msg) {
    return participantRepository
      .save(new Participant(msg.participantName(), roomId))
      .map(participant -> new JoinRoomConfirmation(participant.id(), roomId));
  }
}
