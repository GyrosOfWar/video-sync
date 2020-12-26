import {
  SubscribeMessage,
  WebSocketGateway,
  WsResponse,
} from "@nestjs/websockets"
import {from, Observable} from "rxjs"
import {map} from "rxjs/operators"
import {log} from "src/utils"

interface OnConnection {
  roomId: string
  userId: string
}

@WebSocketGateway({path: "/ws/room"})
export class RoomGateway {
  private log = log.child({class: "RoomGateway"})

  @SubscribeMessage("connect")
  onConnect(client: any, data: OnConnection): Observable<WsResponse<number>> {
    this.log.info(`onConnect: roomId: '${data.roomId}'`)
    return from([1, 2, 3]).pipe(map((item) => ({event: "events", data: item})))
  }
}
