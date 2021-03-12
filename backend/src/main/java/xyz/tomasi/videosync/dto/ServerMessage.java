package xyz.tomasi.videosync.dto;

public sealed interface ServerMessage permits ServerMessage.JoinRoomConfirmation, ServerMessage.Pong, ServerMessage.OutOfSync {

  record JoinRoomConfirmation(
    String participantName,
    String roomId
  ) implements ServerMessage {
  }

  record Pong() implements ServerMessage {}

  record OutOfSync(int serverTime) implements ServerMessage {}
}
