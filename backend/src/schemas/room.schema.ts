import { Prop, Schema, SchemaFactory } from "@nestjs/mongoose"
import { Document } from "mongoose"

export type RoomDocument = Room & Document

export class Participant {
  name!: string
  ipAddress!: string
  id!: string
}

@Schema()
export class Room {
  @Prop()
  name!: string

  @Prop()
  participants!: Participant[]

  @Prop()
  playlist!: string[]
}

export const RoomSchema = SchemaFactory.createForClass(Room)
