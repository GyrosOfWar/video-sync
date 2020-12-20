import {Body, Controller, Get, Ip, Param, Post, Query} from "@nestjs/common"
import {Room} from "src/schemas/room.schema"
import {RoomService} from "./room.service"
import {log} from "../utils"

export interface CreateRoomResponse {
  roomName: string
  roomId: string
  userName: string
  userId: string
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
  async create(@Ip() ipAddress: string): Promise<CreateRoomResponse> {
    this.log.info(`creating new room`)
    return await this.roomService.create(ipAddress)
  }

  @Post(":id/video")
  async addVideo(
    @Param() params: any,
    @Query("url") url: string,
    @Query("userId") userId: string
  ): Promise<void> {
    await this.roomService.addVideo(params.id, url, userId)
  }
}
