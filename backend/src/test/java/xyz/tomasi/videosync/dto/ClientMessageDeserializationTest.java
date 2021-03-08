package xyz.tomasi.videosync.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import xyz.tomasi.videosync.dto.client.ClientMessage;
import xyz.tomasi.videosync.dto.client.JoinRoomRequest;

class ClientMessageDeserializationTest {

  final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void deserializeJoinRoomMessage() throws Exception {
    var json =
      """
    {
      "type": "JoinRoomMessage",
      "participantName": "foo"
    }
    """;

    ClientMessage result = objectMapper.readValue(json, ClientMessage.class);

    Assertions
      .assertThat(result)
      .isInstanceOf(JoinRoomRequest.class)
      .extracting("participantName")
      .asInstanceOf(InstanceOfAssertFactories.STRING)
      .isEqualTo("foo");
  }
}
