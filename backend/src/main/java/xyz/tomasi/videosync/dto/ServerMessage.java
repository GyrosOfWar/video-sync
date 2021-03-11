package xyz.tomasi.videosync.dto;

public sealed interface ServerMessage permits ServerMessage.JoinRoomConfirmation {

  record JoinRoomConfirmation(
    String participantName,
    String roomId
  ) implements ServerMessage {
  }
}
