// 用户登录请求
export interface LoginRequest {
   username: string
   password: string
}

// 用户注册请求
export interface RegisterRequest {
   username: string
   password: string
   email: string
   avatar?: string
}

// 用户数据
export interface UserInfo {
   id: number
   username: string
   email: string
   avatar: string
   registerDate: string
   lastLogin: string
   articleCount: number
   commentCount: number
   bio: string
   level: number
   isAdmin?: boolean
}

// 登录 / 注册响应：后端签发 JWT，前端存入 localStorage 并放进 Authorization 头
export interface LoginResponse {
   token: string
   tokenType: string
   expiresIn: number
   user: UserInfo
}
