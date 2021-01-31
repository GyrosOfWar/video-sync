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

// 1 second of time difference between clients
// and database is allowed
const ALLOWED_TIME_DIFFERENCE = 1000

@WebSocketGateway({path: "/ws/room"})
export class RoomGateway {
  constructor(private roomService: RoomService) {}

  private log = log.child({class: "RoomGateway"})

  @SubscribeMessage("connect")
  async onConnect(
    client: unknown,
    data: OnConnection
  ): Promise<WsResponse<VideoInfo>> {
    this.log.info(`onConnect: roomId: '${data.roomId}, userId: ${data.userId}'`)
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
  async onPing(client: unknown, data: OnPing): Promise<WsResponse> {
    const room = await this.roomService.findOne(data.roomId)
    if (room == null) {
      throw new Error(`Room with ID ${data.roomId} not found`)
    }

    const playlistEntry = room?.playlist.find((e) => e.id === room.currentEntry)
    if (playlistEntry == null) {
      throw new Error(`No active playlist entry found`)
    }
    const databaseTime = playlistEntry.currentTime
    const userTime = data.currentTime

    if (Math.abs(databaseTime - userTime) > ALLOWED_TIME_DIFFERENCE) {
      return {
        event: "ping",
        data: {
          serverTime: databaseTime,
        },
      }
    } else {
      return {
        event: "ping",
        data: {},
      }
    }
  }
}
