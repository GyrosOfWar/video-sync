import {Injectable} from "@nestjs/common"
import {InjectModel} from "@nestjs/mongoose"
import {Model} from "mongoose"
import {v4 as newUuid} from "uuid"
import {PlaylistEntry, Room, RoomDocument} from "src/schemas/room.schema"
import {randomInt, readLines, resourcePath} from "src/utils"
import {CreateRoomResponse} from "./room.controller"

const animals = readLines(resourcePath("animals.txt"))
const adjectives = readLines(resourcePath("adjectives.txt"))

function generateUserName(): string {
  const animal = animals[randomInt(animals.length)]
  const adjective1 = adjectives[randomInt(adjectives.length)]
  const adjective2 = adjectives[randomInt(adjectives.length)]
  return `${adjective1}-${adjective2}-${animal}`
}

@Injectable()
export class RoomService {
  constructor(@InjectModel(Room.name) private roomModel: Model<RoomDocument>) {}

  async findAll(): Promise<Room[]> {
    return await this.roomModel.find().exec()
  }

  async delete(id: string) {
    this.roomModel.deleteOne({_id: id})
  }

  async findOne(id: string): Promise<RoomDocument | null> {
    return this.roomModel.findOne({_id: id})
  }

  async create(ipAddress: string): Promise<CreateRoomResponse> {
    const model = new this.roomModel({})
    model.createdAt = new Date()
    model.playlist = []
    // todo custom function
    model.name = generateUserName()
    const participant = {
      name: generateUserName(),
      ipAddress,
      id: newUuid(),
    }
    model.participants = [participant]
    model._id = newUuid()
    await model.save()

    return {
      roomName: model.name,
      roomId: model._id,
      userName: participant.name,
      userId: participant.id,
    }
  }

  async addVideo(
    roomId: string,
    videoUrl: string,
    userId: string
  ): Promise<void> {
    const entry: PlaylistEntry = {
      url: videoUrl,
      addedAt: new Date(),
      addedByUser: userId,
    }

    const room = await this.findOne(roomId)
    if (!room) {
      throw new Error("Failed to find room with ID " + roomId)
    }

    room.playlist.push(entry)
    await room.save()
  }
}
