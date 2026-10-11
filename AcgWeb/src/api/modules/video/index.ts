import { http } from '@/api/index'
import { VIDEO_URLS } from './urls.const'
import type { VideoItem, VideoDetail, VideoListResponse } from './interface'

export const videoApi = {
   getAll(): Promise<VideoListResponse> {
      return http.get(VIDEO_URLS.GET_ALL)
   },
   
   getById(id: number): Promise<VideoDetail> {
      return http.get(VIDEO_URLS.GET_BY_ID(id))
   },
   insert(data: Partial<VideoDetail>): Promise<VideoDetail> {
      return http.post(VIDEO_URLS.INSERT, data)
   }
}

export type { VideoItem, VideoDetail, VideoListResponse }