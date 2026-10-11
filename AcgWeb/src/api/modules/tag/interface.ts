export interface TagAnimeItem {
   id: number
   title: string
   chineseTitle: string
   image: string
   description: string
}

export interface RandomTagResponse {
   tag: string
   anime: TagAnimeItem[]
}