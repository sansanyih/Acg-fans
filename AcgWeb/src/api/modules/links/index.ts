import { http } from '@/api/index'
import { LINKS_URLS } from './urls.const'
import type { LinkItem, LinksListResponse } from './interface'

export const linksApi = {
   getAll(): Promise<LinksListResponse> {
      return http.get(LINKS_URLS.GET_ALL)
   }
}

export type { LinkItem, LinksListResponse }