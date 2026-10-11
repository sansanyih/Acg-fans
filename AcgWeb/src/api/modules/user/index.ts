import { http } from '@/api/index'
import { USER_URLS } from './urls.const'
import type { LoginRequest, RegisterRequest, UserResponse, UserInfo } from './interface'

export const userApi = {
   // 登录
   login(data: LoginRequest): Promise<UserResponse> {
      return http.post(USER_URLS.LOGIN, data)
   },
   
   // 注册
   register(data: RegisterRequest): Promise<UserResponse> {
      return http.post(USER_URLS.REGISTER, data)
   }
}

export type { LoginRequest, RegisterRequest, UserResponse, UserInfo }