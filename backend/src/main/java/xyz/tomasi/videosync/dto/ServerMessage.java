package xyz.tomasi.videosync.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "type"
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = ServerMessage.JoinRoomConfirmation.class, name = "joinRoomConfirmation"),
  @JsonSubTypes.Type(value = ServerMessage.Pong.class, name = "pong"),
  @JsonSubTypes.Type(value = ServerMessage.OutOfSync.class, name = "outOfSync")
})
public interface ServerMessage {

  record JoinRoomConfirmation(
    String participantName,
    String roomId
  ) implements ServerMessage {
  }

  record Pong() implements ServerMessage {}

  record OutOfSync(int serverTime) implements ServerMessage {}
}
