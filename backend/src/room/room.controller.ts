import {Body, Controller, Get, Ip, Post} from "@nestjs/common"
import {Room} from "src/schemas/room.schema"
import {RoomService} from "./room.service"

export class CreateRoomDto {
  readonly name?: string
  readonly participantName?: string
}

@Controller("/api/rooms")
export class RoomController {
  constructor(private roomService: RoomService) {}

  @Get()
  async findAll(): Promise<Room[]> {
    return this.roomService.findAll()
  }

  @Post()
  async create(
    @Body() room: CreateRoomDto,
    @Ip() ipAddress: string
  ): Promise<Room> {
    return this.roomService.create(room, ipAddress)
  }
}
