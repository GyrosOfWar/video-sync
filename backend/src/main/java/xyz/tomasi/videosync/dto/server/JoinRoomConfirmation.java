package xyz.tomasi.videosync.dto.server;

public record JoinRoomConfirmation(
    long participantId,
    long roomId
) implements ServerMessage {
}
