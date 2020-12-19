import {useEffect, useRef, useState} from "react"
import {useParams} from "react-router-dom"
import {Room} from "./Home"

interface Message {
  type: string
  data: any
}

const useWebsocket = (
  url: string,
  messageHandler: (message: Message) => void,
  initialMessage: any
) => {
  useEffect(() => {
    const ws = new WebSocket(url)
    ws.addEventListener("open", () => {
      ws.send(JSON.stringify(initialMessage))
    })

    ws.addEventListener("message", (event) => {
      const message = JSON.parse(event.data) as Message
      messageHandler(message)
    })

    return () => ws.close()
  }, [url, initialMessage, messageHandler])
}

const RoomView: React.FC = () => {
  const {id} = useParams()
  const [room, setRoom] = useState<Room | null>(null)
  const [currentVideo, setCurrentVideo] = useState(null)
  const [error, setError] = useState<Error | null>(null)
  const messages = useRef<any[]>([])

  useWebsocket(
    "ws://localhost:3010",
    (message) => {
      messages.current.push(message)
    },
    {
      event: "connect",
      data: {
        roomId: id,
      },
    }
  )

  const fetchRoom = async (id: string): Promise<void> => {
    try {
      const response = await fetch(`/api/rooms/${id}`)
      const data: Room = await response.json()
      setRoom(data)
    } catch (e) {
      console.error(e)
      setError(e)
    }
  }

  useEffect(() => {
    fetchRoom(id)
  }, [id])

  return (
    <ul>
      {error && <p>{error.message}</p>}
    </ul>
  )
}

export default RoomView
