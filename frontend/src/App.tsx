import React from "react"
import {BrowserRouter, Routes, Route} from "react-router-dom"
import Home from "./pages/Home"
import Room from "./pages/Room"
/** @jsxImportSource @emotion/react */
import tw from 'twin.macro'

const NotFound = () => <p>404 - Not found</p>

function App() {
  return (
    <>
      <nav css={tw`dark:bg-gray-800 w-full p-2 bg-gray-100 `}>
        <span css={tw`dark:text-gray-50 text-lg font-bold`}>video-sync</span>
      </nav>

      <main css={tw`container mt-4`}>
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/rooms/:id" element={<Room />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </BrowserRouter>
      </main>
    </>
  )
}

export default App
