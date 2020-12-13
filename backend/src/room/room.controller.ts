import {Body, Controller, Delete, Get, Ip, Post} from "@nestjs/common"
import {Room} from "src/schemas/room.schema"
import {RoomService} from "./room.service"
import {log} from "../utils"

export class CreateRoomDto {
  readonly name?: string
  readonly participantName?: string
}

@Controller("/api/rooms")
export class RoomController {
  constructor(private roomService: RoomService) {}

  private log = log.child({class: "RoomController"})

  @Get()
  async findAll(): Promise<Room[]> {
    this.log.info("finding all rooms")
    return await this.roomService.findAll()
  }

  @Post()
  async create(
    @Body() room: CreateRoomDto,
    @Ip() ipAddress: string
  ): Promise<Room> {
    return await this.roomService.create(room, ipAddress)
  }
}
