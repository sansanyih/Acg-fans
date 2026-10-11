import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { UserInfo } from '@/api/modules/user'

export const useUserStore = defineStore('user', () => {
   // 从 localStorage 读取初始状态
   const token = ref<string | null>(localStorage.getItem('token'))
   const userInfo = ref<UserInfo | null>(null)

   // 初始化时读取用户信息
   const savedUser = localStorage.getItem('userInfo')
   if (savedUser) {
      try {
         userInfo.value = JSON.parse(savedUser)
      } catch {
         userInfo.value = null
      }
   }

   // 是否登录
   const isLoggedIn = computed(() => !!token.value && !!userInfo.value)

   // 登录
   const login = (newToken: string, user: UserInfo) => {
      token.value = newToken
      userInfo.value = user
      localStorage.setItem('token', newToken)
      localStorage.setItem('userInfo', JSON.stringify(user))
   }

   // 登出
   const logout = () => {
      token.value = null
      userInfo.value = null
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
   }

   return {
      token,
      userInfo,
      isLoggedIn,
      login,
      logout
   }
})