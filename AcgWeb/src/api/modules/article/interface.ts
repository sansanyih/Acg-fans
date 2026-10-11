// 评论类型
export interface Comment {
   id: number
   user: string
   content: string
   date: string
}

// 文章基础类型（列表用）
export interface ArticleItem {
   id: number
   title: string
   summary: string
   author: string
   authorAvatar: string
   publishDate: string
   readCount: number
   likeCount: number
   cover: string
}

// 文章详情类型
export interface ArticleDetail extends ArticleItem {
   content: string
   lastEdit: string
   comments: Comment[]
}

export type ArticleListResponse = ArticleItem[]