import { css, Global } from "@emotion/react"
import React from "react"
import ReactDOM from "react-dom"
import {GlobalStyles} from "twin.macro"
import App from "./App"
import reportWebVitals from "./reportWebVitals"
/** @jsxImportSource @emotion/react */
import tw from 'twin.macro'

ReactDOM.render(
  <React.StrictMode>
    <GlobalStyles />
    <Global styles={css`
      body {
        ${tw`dark:bg-gray-900 dark:text-white`}
        }
      }      
    `} />
    <App />
  </React.StrictMode>,
  document.getElementById("root")
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
