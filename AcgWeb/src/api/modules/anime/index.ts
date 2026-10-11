import { http } from '@/api/index'
import { ANIME_URLS } from './urls.const'
import type { 
   AnimeItem, 
   AnimeDetail, 
   AnimeListResponse,
   Character,
   Comment
} from './interface'

export const animeApi = {
   // 获取随机列表
   getRandom(): Promise<AnimeListResponse> {
      return http.get(ANIME_URLS.GET_RANDOM)
   },
   
   // 获取最新作品（新增）
   getLatest(): Promise<AnimeListResponse> {
      return http.get(ANIME_URLS.GET_LATEST)
   },
      getUpcoming(): Promise<AnimeListResponse> {
      return http.get(ANIME_URLS.GET_UPCOMING)
   },
   
   // 获取详情
   getById(id: number): Promise<AnimeDetail> {
      return http.get(ANIME_URLS.GET_BY_ID(id))
   },
      update(id: number, data: Partial<AnimeDetail>): Promise<AnimeDetail> {
      return http.put(ANIME_URLS.UPDATE(id), data)
   },
      getRecentUpdated(): Promise<AnimeListResponse> {
      return http.get(ANIME_URLS.GET_RECENT_UPDATED)
   },
   insert(data: Partial<AnimeDetail>): Promise<AnimeDetail> {
      return http.post(ANIME_URLS.INSERT, data)
   }
}

export type { 
   AnimeItem, 
   AnimeDetail, 
   AnimeListResponse,
   Character,
   Comment
}