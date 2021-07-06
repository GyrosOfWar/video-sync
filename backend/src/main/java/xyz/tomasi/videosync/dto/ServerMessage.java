package xyz.tomasi.videosync.dto;

public interface ServerMessage {

  record JoinRoomConfirmation(
    String participantName,
    String roomId
  ) implements ServerMessage {
  }

  record Pong() implements ServerMessage {}

  record OutOfSync(int serverTime) implements ServerMessage {}
}
