import { http } from '@/api/index'
import { STATISTICS_URLS } from './urls.const'
import type { EditStatistics, EditStatisticsResponse } from './interface'

export const statisticsApi = {
   getEdits(): Promise<EditStatisticsResponse> {
      return http.get(STATISTICS_URLS.GET_EDITS)
   },
   
   getEditsRecent(): Promise<EditStatisticsResponse> {
      return http.get(STATISTICS_URLS.GET_EDITS_RECENT)
   }
}

export type { EditStatistics, EditStatisticsResponse }