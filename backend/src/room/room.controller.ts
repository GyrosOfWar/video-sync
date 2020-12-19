import {Body, Controller, Get, Ip, Param, Post} from "@nestjs/common"
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

  @Get(":id")
  async findAll(@Param() params: any): Promise<Room | null> {
    this.log.info(`finding room with id ${params.id}`)
    return await this.roomService.findOne(params.id)
  }

  @Post()
  async create(
    @Body() room: CreateRoomDto,
    @Ip() ipAddress: string
  ): Promise<Room> {
    this.log.info(`creating new room`)
    return await this.roomService.create(room, ipAddress)
  }
}
