import { fileURLToPath, URL } from 'node:url'
import { defineConfig, loadEnv, ConfigEnv, UserConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import vueDevTools from 'vite-plugin-vue-devtools'
import { wrapperEnv } from './src/utils/getEnv'

export default defineConfig(({ mode }: ConfigEnv): UserConfig => {
   const env = loadEnv(mode, process.cwd())
   const viteEnv = wrapperEnv(env)

   const baseUrl = viteEnv.VITE_API_BASE_URL
   const target = viteEnv.VITE_PROXY_TARGET
   const port = viteEnv.VITE_PORT
   const open = viteEnv.VITE_OPEN

   return {
      plugins: [vue(), vueJsx(), vueDevTools()],
      resolve: {
         alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
         },
      },
      css: {
         preprocessorOptions: {
            scss: {
               additionalData: `@use "@/styles/variables.scss" as *;`
            }
         }
      },
      server: {
         host: '0.0.0.0',
         port: port,
         open: open,
         cors: true,
         proxy: {
            [baseUrl]: {
               target: target,
               changeOrigin: true,
            },
         },
      },
   }
})