import { http } from '@/api/index'
import { ANNOUNCEMENT_URLS } from './urls.const'
import type { AnnouncementItem, AnnouncementListResponse } from './interface'

export const announcementApi = {
   getAll(): Promise<AnnouncementListResponse> {
      return http.get(ANNOUNCEMENT_URLS.GET_ALL)
   },
   
   getById(id: number): Promise<AnnouncementItem> {
      return http.get(ANNOUNCEMENT_URLS.GET_BY_ID(id))
   }
}

export type { AnnouncementItem, AnnouncementListResponse }