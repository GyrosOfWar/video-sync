package xyz.tomasi.videosync.dto.client;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(
  use = Id.NAME,
  include = As.PROPERTY,
  property = "type"
)
@JsonSubTypes({
  @Type(JoinRoomRequest.class)
})
public sealed interface ClientMessage permits JoinRoomRequest {

}
