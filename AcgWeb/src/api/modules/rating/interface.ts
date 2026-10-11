export interface RatingItem {
   id: number
   user: string
   userAvatar: string
   rating: number
   totalRating: number
   musicRating: number
   artRating: number
   scriptRating: number
   performanceRating: number
   programRating: number
   voiceRating: number
   comment: string
   date: string
   animeId: number
   animeTitle: string
   animeChineseTitle: string
}

export type RatingListResponse = RatingItem[]