import {Prop, Schema, SchemaFactory} from "@nestjs/mongoose"
import {Document} from "mongoose"

export type RoomDocument = Room & Document

export interface Participant {
  name: string
  ipAddress: string
  id: string
}

export interface PlaylistEntry {
  url: string
  addedByUser: string
  addedAt: Date
}

@Schema()
export class Room {
  @Prop({required: true})
  name!: string

  @Prop()
  participants!: Participant[]

  @Prop()
  playlist!: PlaylistEntry[]

  @Prop({required: true})
  createdAt!: Date
}

export const RoomSchema = SchemaFactory.createForClass(Room)
