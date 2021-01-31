import {
  SubscribeMessage,
  WebSocketGateway,
  WsResponse,
} from "@nestjs/websockets"
import {log} from "src/utils"
import {RoomService} from "./room.service"

interface BaseMessage {
  roomId: string
  userId: string
}

type OnConnection = BaseMessage

interface OnPing extends BaseMessage {
  currentTime: number
}

interface VideoInfo {
  currentTime?: number
  entryId?: string
}

@WebSocketGateway({path: "/ws/room"})
export class RoomGateway {
  constructor(private roomService: RoomService) {}

  private log = log.child({class: "RoomGateway"})

  @SubscribeMessage("connect")
  async onConnect(
    client: unknown,
    data: OnConnection
  ): Promise<WsResponse<VideoInfo>> {
    this.log.info(`onConnect: roomId: '${data.roomId}'`)
    const room = await this.roomService.findOne(data.roomId)
    const playlistEntry = room?.playlist.find((e) => e.id === room.currentEntry)

    return {
      event: "connect",
      data: {
        currentTime: playlistEntry?.currentTime,
        entryId: playlistEntry?.id,
      },
    }
  }

  @SubscribeMessage("ping")
  onPing(client: unknown, data: OnPing): WsResponse {
    return {
      event: "ping",
      data: {},
    }
  }
}
