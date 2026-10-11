import { http } from '@/api/index'
import { RATING_URLS } from './urls.const'
import type { RatingItem, RatingListResponse } from './interface'

export const ratingApi = {
   getRandom(): Promise<RatingListResponse> {
      return http.get(RATING_URLS.GET_RANDOM)
   }
}

export type { RatingItem, RatingListResponse }