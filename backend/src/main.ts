import {NestFactory} from "@nestjs/core"
import {WsAdapter} from "@nestjs/platform-ws"
import {AppModule} from "./app.module"
import {log} from "./utils"
import {WinstonLogger} from "./winston-logger"

async function bootstrap() {
  const app = await NestFactory.create(AppModule)
  app.useLogger(new WinstonLogger(log))
  app.useWebSocketAdapter(new WsAdapter(app))
  await app.listen(3000)
}
bootstrap()
