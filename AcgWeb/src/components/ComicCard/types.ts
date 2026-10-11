
export interface Comic {
   id: number | string
   url: string
   title: string
   desc: string
   tags: string[]
}

export interface Discuss {
   id: number | string
   avatar: string        // 用户头像
   username: string      // 用户名
   bio?: string          // 简介/签名
   rating: number        // 评分（星星数）
   scores: {             // 各项评分
      music?: number
      art?: number
      story?: number
      gameplay?: number
   }
   content: string       // 评语内容
   gameName: string      // 评测的游戏名
   date: string          // 发布时间
}

// Slider 用泛型，不限定具体类型
export interface CardSlider<T = any> {
   title?: string
   items: T[]           // 改为 items，更通用
}