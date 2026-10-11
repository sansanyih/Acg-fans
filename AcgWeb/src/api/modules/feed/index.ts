import { http } from '@/api/index'
import { FEED_URLS } from './urls.const'
import type { FeedItem, FeedListResponse } from './interface'

export const feedApi = {
   // 获取动态列表
   getAll(): Promise<FeedListResponse> {
      return http.get(FEED_URLS.GET_ALL)
   }
}

export type { FeedItem, FeedListResponse }