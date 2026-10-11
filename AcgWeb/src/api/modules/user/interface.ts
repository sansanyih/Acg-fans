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

// 用户数据（新增 bio 和 level）
export interface UserInfo {
   id: number
   username: string
   email: string
   avatar: string
   registerDate: string
   lastLogin: string
   articleCount: number
   commentCount: number
   bio: string        // 新增
   level: number      // 新增
}

// 登录/注册响应
export interface UserResponse {
   success: boolean
   message: string
   user: UserInfo
}