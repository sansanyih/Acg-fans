export interface EditStatistics {
   anime: number
   article: number
   video: number
   character: number
}

export interface EditStatisticsResponse {
   [date: string]: EditStatistics
}