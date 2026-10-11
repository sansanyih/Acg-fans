import { http } from '@/api/index'
import { WEEKLY_URLS } from './urls.const'
import type { 
   WeeklyItem, 
   WeeklyDetail, 
   WeeklyListResponse,
   Comment
} from './interface'

export const weeklyApi = {
   // 获取周报列表
   getAll(): Promise<WeeklyListResponse> {
      return http.get(WEEKLY_URLS.GET_ALL)
   },
   
   // 获取周报详情
   getById(id: number): Promise<WeeklyDetail> {
      return http.get(WEEKLY_URLS.GET_BY_ID(id))
   }
}

export type { 
   WeeklyItem, 
   WeeklyDetail, 
   WeeklyListResponse,
   Comment
}