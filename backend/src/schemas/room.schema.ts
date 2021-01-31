import {Prop, Schema, SchemaFactory} from "@nestjs/mongoose"
import {Document} from "mongoose"

export type RoomDocument = Room & Document

export interface Participant {
  name: string
  ipAddress: string
  id: string
}

export interface PlaylistEntry {
  id: string
  // current time in milliseconds
  currentTime: number
  url: string
  addedByUser: string
  addedAt: Date
}

@Schema()
export class Room {
  @Prop({required: true})
  _id!: string

  @Prop({required: true})
  name!: string

  @Prop({required: true})
  participants!: Participant[]

  @Prop({required: true})
  playlist!: PlaylistEntry[]

  @Prop()
  currentEntry?: string

  @Prop({required: true})
  createdAt!: Date
}

export const RoomSchema = SchemaFactory.createForClass(Room)
