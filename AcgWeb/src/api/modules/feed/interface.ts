// 轮播图数据类型
// 动态数据类型
export interface FeedItem {
   id: number
   userAvatar: string    // 头像链接
   username: string      // 用户名
   time: string          // 发布时间
   content: string       // 内容
   link: string          // 跳转链接
}

export type FeedListResponse = FeedItem[]