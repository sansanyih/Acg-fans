// 列表项
export interface VideoItem {
   id: number
   thumbnail: string
   title: string
   author: string
   authorAvatar: string
   publishDate: string
}

// 详情项
export interface VideoDetail {
   id: number
   thumbnail: string
   title: string
   author: string
   authorAvatar: string
   authorRole: string
   authorLevel: number
   authorBio:string
   publishDate: string
   description: string
   externalLink: string
   relatedTags: string[]
   relatedAnime: {
      id: number
      title: string
      chineseTitle: string
      image: string
      description: string
   }[]
   comments: {
      id: number
      user: string
      userAvatar: string
      content: string
      date: string
      likeCount: number
   }[]
}

export type VideoListResponse = VideoItem[]