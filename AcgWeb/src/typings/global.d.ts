// src/typings/global.d.ts

// * Menu
declare namespace Menu {
  interface MenuOptions {
    path: string
    title: string
    icon?: string
    isLink?: string
    close?: boolean
    children?: MenuOptions[]
  }
}

// * Vite
declare type Recordable<T = any> = Record<string, T>

declare interface ViteEnv {
  VITE_API_BASE_URL: string
  VITE_PROXY_TARGET: string
  VITE_PORT: number
  VITE_OPEN: boolean
  VITE_BASE: string
  VITE_GLOB_APP_TITLE: string
  VITE_DROP_CONSOLE: boolean
  VITE_BUILD_GZIP: boolean
  VITE_REPORT: boolean
  // 根据你的 .env 需要继续添加...
}