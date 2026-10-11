export interface SearchAnimeItem {
   id: number
   title: string
   chineseTitle: string
   image: string
   description: string
   type: 'anime'
}

export interface SearchArticleItem {
   id: number
   title: string
   cover: string
   summary: string
   author: string
   publishDate: string
   type: 'article'
}

export interface SearchVideoItem {
   id: number
   title: string
   thumbnail: string
   description: string
   author: string
   publishDate: string
   type: 'video'
}

export interface SearchCharacterItem {
   id: number
   name: string
   chineseName: string | null
   image: string
   description: string
   type: 'character'
}

export interface SearchResult {
   anime: SearchAnimeItem[]
   article: SearchArticleItem[]
   video: SearchVideoItem[]
   character: SearchCharacterItem[]
}
