import {Module} from "@nestjs/common"
import {MongooseModule} from "@nestjs/mongoose"
import {Room, RoomSchema} from "src/schemas/room.schema"
import {RoomController} from "./room.controller"
import {RoomService} from "./room.service"

@Module({
  controllers: [RoomController],
  imports: [MongooseModule.forFeature([{name: Room.name, schema: RoomSchema}])],
  providers: [RoomService],
})
export class RoomModule {}
