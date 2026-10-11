import { http } from '@/api/index'
import { TAG_URLS } from './urls.const'
import type { RandomTagResponse } from './interface'

export const tagApi = {
   getRandom(): Promise<RandomTagResponse> {
      return http.get(TAG_URLS.GET_RANDOM)
   }
}

export type { RandomTagResponse }