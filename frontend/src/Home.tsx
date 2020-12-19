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

const Home = () => {
  const navigate = useNavigate()

  const onCreateRoom = async () => {
    const response = await fetch("/api/rooms", {
      method: "POST",
      body: JSON.stringify({
        name: "cool-room",
      }),
      headers: {"Content-Type": "application/json"},
    })
    const data: Room = await response.json()
    navigate(`/rooms/${data._id}`)
  }

  return (
    <div css={tw`flex flex-col`}>
      <div css={tw`self-center`}>
        <button
          onClick={onCreateRoom}
          css={tw`bg-indigo-100 hover:bg-indigo-200 dark:bg-gray-700 p-4 rounded-2xl dark:hover:bg-gray-600 transition shadow-lg`}
        >
          <Icon />
          <span>New room</span>
        </button>
      </div>
    </div>
  )
}

export default Home
