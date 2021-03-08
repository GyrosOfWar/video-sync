package xyz.tomasi.videosync.dto;

public record JoinRoomMessage(
  String participantName
) implements ClientMessage {

}
