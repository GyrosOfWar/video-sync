package xyz.tomasi.videosync.dto.client;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(
  use = Id.NAME,
  property = "type"
)
@JsonSubTypes({
  @Type(value = ClientMessage.JoinRoomRequest.class, name = "joinRoom"),
  @Type(value = ClientMessage.Ping.class, name = "ping")
})
public sealed interface ClientMessage permits ClientMessage.JoinRoomRequest, ClientMessage.Ping {

  record JoinRoomRequest(
    String participantName
  ) implements ClientMessage { }

  record Ping() implements ClientMessage { }

}
