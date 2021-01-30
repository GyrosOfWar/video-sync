import {useNavigate} from "react-router-dom"
import tw from "twin.macro"

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

export interface Room {
  _id: string
  name: string
  participants: Participant[]
  playlist: PlaylistEntry[]
  createdAt: Date
}

export interface CreateRoomResponse {
  roomName: string
  roomId: string
  userName: string
  userId: string
}

const Icon: React.FC = () => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    fill="none"
    viewBox="0 0 24 24"
    stroke="currentColor"
    css={tw`w-20 h-20`}
  >
    <path
      strokeLinecap="round"
      strokeLinejoin="round"
      strokeWidth={2}
      d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z"
    />
  </svg>
)

const BigButton = tw.button`bg-green-200 hover:bg-green-300 dark:bg-gray-700 p-4 rounded-2xl dark:hover:bg-gray-600 transition shadow-lg`

const Home = () => {
  const navigate = useNavigate()

  const onCreateRoom = async () => {
    const response = await fetch("/api/rooms", {
      method: "POST",
      headers: {"Content-Type": "application/json"},
    })
    const data: CreateRoomResponse = await response.json()
    window.localStorage.setItem("userId", data.userId)

    navigate(`/rooms/${data.roomId}`)
  }

  return (
    <div css={tw`flex flex-col items-center`}>
      <h1 css={tw`text-3xl font-bold mb-4`}>Welcome to video-sync!</h1>
      <BigButton onClick={onCreateRoom}>
        <Icon />
        <span>New room</span>
      </BigButton>
    </div>
  )
}

export default Home
