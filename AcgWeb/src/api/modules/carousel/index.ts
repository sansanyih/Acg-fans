import { http } from '@/api/index'
import { CAROUSEL_URLS } from './urls.const'
import type { CarouselItem, CarouselListResponse } from './interface'

export const carouselApi = {
   // 获取所有轮播图
   getAll(): Promise<CarouselListResponse> {
      return http.get(CAROUSEL_URLS.GET_ALL)
   }
}

export type { CarouselItem, CarouselListResponse }