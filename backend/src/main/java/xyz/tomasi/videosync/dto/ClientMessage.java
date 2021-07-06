package xyz.tomasi.videosync.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(
  use = Id.NAME,
  property = "type"
)
@JsonSubTypes({
  @Type(value = ClientMessage.JoinRoomRequest.class, name = "joinRoom"),
  @Type(value = ClientMessage.Ping.class, name = "ping")
})
public interface ClientMessage {

  record JoinRoomRequest() implements ClientMessage { }

  record Ping(
    UUID videoId,
    int currentTimeMillis,
    String participantId
  ) implements ClientMessage { }

}
