package xyz.tomasi.videosync.dto;

public sealed interface ServerMessage permits ServerMessage.JoinRoomConfirmation {

  record JoinRoomConfirmation(
    long participantId,
    long roomId
  ) implements ServerMessage {
  }
}
