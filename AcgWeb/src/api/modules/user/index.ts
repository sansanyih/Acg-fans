import { http } from '@/api/index'
import { USER_URLS } from './urls.const'
import type { LoginRequest, RegisterRequest, LoginResponse, UserInfo } from './interface'

export const userApi = {
   // 登录：返回 { token, tokenType, expiresIn, user }
   login(data: LoginRequest): Promise<LoginResponse> {
      return http.post(USER_URLS.LOGIN, data)
   },

   // 注册：注册成功后同样直接返回 token
   register(data: RegisterRequest): Promise<LoginResponse> {
      return http.post(USER_URLS.REGISTER, data)
   }
}

export type { LoginRequest, RegisterRequest, LoginResponse, UserInfo }
