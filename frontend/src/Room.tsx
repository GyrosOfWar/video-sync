import {FormEventHandler, useEffect, useRef, useState} from "react"
import {useParams} from "react-router-dom"
import tw from "twin.macro"
import {Room} from "./Home"
import useWebSocket, {ReadyState} from "react-use-websocket"

interface Message {
  event: string
  data: unknown
}

const connectionStates = {
  [ReadyState.CONNECTING]: "Connecting",
  [ReadyState.OPEN]: "Open",
  [ReadyState.CLOSING]: "Closing",
  [ReadyState.CLOSED]: "Closed",
  [ReadyState.UNINSTANTIATED]: "Uninstantiated",
}

const RoomView: React.FC = () => {
  const {id} = useParams()
  const [room, setRoom] = useState<Room | null>(null)
  const [error, setError] = useState<Error | null>(null)
  const [videoUrlInput, setVideoUrlInput] = useState("")

  // TODO "register" as new user if this is null
  const userId = window.localStorage.getItem("userId")
  const videoElement = useRef<HTMLVideoElement>(null)

  const {sendJsonMessage, lastMessage, readyState} = useWebSocket(
    "ws://localhost:3090/ws/room"
  )
  const connectionStatus = connectionStates[readyState]

  useEffect(() => {
    sendJsonMessage({
      event: "connect",
      data: {
        roomId: id,
        userId: userId,
      },
    })
  }, [id, userId, sendJsonMessage])

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

  const onAddVideo: FormEventHandler = async (e) => {
    e.preventDefault()

    const params = new URLSearchParams({
      url: videoUrlInput,
      userId: userId || "",
    })
    try {
      const response = await fetch(
        `/api/rooms/${id}/video?${params.toString()}`,
        {
          method: "POST",
        }
      )

      if (!response.ok) {
        const json = await response.json()
        setError(json)
      }
    } catch (e: unknown) {
      if (e instanceof Error) {
        setError(e)
      }
    }
  }

  useEffect(() => {
    fetchRoom(id)
  }, [id])

  if (!room) {
    return <p>Loading...</p>
  }

  return (
    <>
      {error && <p>{error.message}</p>}
      <h1 css={tw`text-3xl font-bold`}>
        Welcome to room <code>{room.name}</code>!
      </h1>

      <form onSubmit={onAddVideo}>
        <label css={tw`dark:text-gray-200 mr-2 inline-block`}>Add video</label>
        <input
          type="text"
          css={tw`inline-block dark:bg-gray-700 dark:text-black`}
          placeholder="Enter URL..."
          value={videoUrlInput}
          onChange={(e) => setVideoUrlInput(e.target.value)}
        />
        <button type="submit">Submit</button>
      </form>

      <p>{connectionStatus}</p>

      <video ref={videoElement}></video>
    </>
  )
}

export default RoomView
