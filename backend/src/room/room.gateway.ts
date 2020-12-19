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
}

@WebSocketGateway(3010)
export class RoomGateway {
  @SubscribeMessage("connect")
  onConnect(client: any, data: OnConnection): Observable<WsResponse<number>> {
    log.info(`roomId: '${data.roomId}'`)
    return from([1, 2, 3]).pipe(map((item) => ({event: "events", data: item})))
  }
}
