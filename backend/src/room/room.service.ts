import {Injectable} from "@nestjs/common"
import {InjectModel} from "@nestjs/mongoose"
import {Model} from "mongoose"
import {v4 as newUuid} from "uuid"
import {Room, RoomDocument} from "src/schemas/room.schema"
import {CreateRoomDto} from "./room.controller"
import {randomInt, readLines, resourcePath} from "src/utils"

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

  async create(room: CreateRoomDto, ipAddress: string): Promise<Room> {
    const model = new this.roomModel(room)
    model.createdAt = new Date()
    model.playlist = []
    const participant = {
      name: room.participantName || generateUserName(),
      ipAddress,
      id: newUuid(),
    }
    model.participants = [participant]

    return await model.save()
  }
}
