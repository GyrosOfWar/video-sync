import {readFileSync} from "fs"
import {join} from "path"
import * as winston from "winston"

export function readLines(path: string): string[] {
  return readFileSync(path, {encoding: "utf-8"})
    .split("\n")
    .map((s) => s.trim().toLowerCase())
    .filter((s) => s.length > 0)
}

export function randomInt(upperBound: number): number {
  return Math.floor(Math.random() * upperBound)
}

export function resourcePath(filename: string): string {
  return join(__dirname, "..", "resources", filename)
}

export const log = winston.createLogger({
  transports: [new winston.transports.Console()],
  level: "info",
})
