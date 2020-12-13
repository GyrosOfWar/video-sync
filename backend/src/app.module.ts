import {Module} from "@nestjs/common"
import {MongooseModule} from "@nestjs/mongoose"
import {RoomModule} from "./room/room.module"

@Module({
  imports: [
    MongooseModule.forRoot("mongodb://localhost/video-sync"),
    RoomModule,
  ],
  controllers: [],
  providers: [],
})
export class AppModule {}
