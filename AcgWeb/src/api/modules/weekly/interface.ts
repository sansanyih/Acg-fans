// 评论类型
export interface Comment {
   id: number
   user: string
   userAvatar: string
   content: string
   date: string
   replyCount: number
}

// 周报基础类型
export interface WeeklyItem {
   id: number
   cover: string
   title: string
   summary: string
   author: string
   authorAvatar: string
   publishDate: string
   readCount: number
   likeCount: number
}

// 周报详情类型
export interface WeeklyDetail extends WeeklyItem {
   content: string
   lastEdit: string
   comments: Comment[]
}

export type WeeklyListResponse = WeeklyItem[]