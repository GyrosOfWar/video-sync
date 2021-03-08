package xyz.tomasi.videosync.dto.client;

public record JoinRoomRequest(
  String participantName
) implements ClientMessage {

}
