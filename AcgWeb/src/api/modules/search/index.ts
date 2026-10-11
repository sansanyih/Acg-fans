import { http } from '@/api/index'
import { SEARCH_URLS } from './urls.const'
import type { SearchResult } from './interface'

export const searchApi = {
   searchAll(keyword: string): Promise<SearchResult> {
      return http.get(SEARCH_URLS.SEARCH_ALL, { params: { keyword } })
   }
}

export type { SearchResult }
