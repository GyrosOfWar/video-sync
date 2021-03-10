package xyz.tomasi.videosync.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

class ClientMessageDeserializationTest {

  final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void deserializeJoinRoomMessage() throws Exception {
    var json =
      """
    {
      "type": "joinRoom",
      "participantName": "foo"
    }
    """;

    ClientMessage result = objectMapper.readValue(json, ClientMessage.class);

    Assertions
      .assertThat(result)
      .isInstanceOf(ClientMessage.JoinRoomRequest.class)
      .extracting("participantName")
      .asInstanceOf(InstanceOfAssertFactories.STRING)
      .isEqualTo("foo");
  }
}
