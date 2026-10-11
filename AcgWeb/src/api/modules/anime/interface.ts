// 声优角色类型
export interface Character {
   id: number           // 新增
   name: string
   voiceActor: string
   image: string        // 新增
   likes: number        // 新增
}

// 评论类型
export interface Comment {
   id: number
   user: string
   userAvatar: string   // 新增
   content: string
   date: string
}

// 观影记录类型（新增）
export interface ViewingRecord {
   id: number
   user: string
   userAvatar: string
   rating: number           // 总评分
   totalRating: number      // 总分
   musicRating: number      // 音乐
   artRating: number        // 美术
   scriptRating: number     // 剧本
   performanceRating: number // 演出
   programRating: number    // 节目
   voiceRating: number      // 声优
   comment: string
   date: string
}

// 动漫基础数据类型（列表用）- 不变
export interface AnimeItem {
   id: number
   title: string
   chineseTitle: string
   image: string
   description: string
   tags: string[]        // 列表也返回 tags
   episodes: number      // 列表也返回集数
   startDate: string     // 列表也返回日期
   lastEdit: string 
}

// 动漫详情类型（详情页用）
export interface AnimeDetail extends AnimeItem {
   introduction: string
   studio: string
   broadcastDay: string
   originalWork: string
   director: string
   script: string
   storyboard: string
   performance: string
   music: string
   characterDesign: string
   seriesComposition: string
   artDirector: string
   colorDesign: string
   chiefAnimationDirector: string
   characters: Character[]
   officialWebsite: string
   viewingRecords: ViewingRecord[]  // 新增观影记录
   comments: Comment[]
}

export type AnimeListResponse = AnimeItem[]