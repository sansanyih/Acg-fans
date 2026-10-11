import { http } from '@/api/index'
import { ARTICLE_URLS } from './urls.const'
import type { 
   ArticleItem, 
   ArticleDetail, 
   ArticleListResponse,
   Comment
} from './interface'

export const articleApi = {
   // 获取文章列表
   getAll(): Promise<ArticleListResponse> {
      return http.get(ARTICLE_URLS.GET_ALL)
   },
   
   // 获取文章详情
   getById(id: number): Promise<ArticleDetail> {
      return http.get(ARTICLE_URLS.GET_BY_ID(id))
   },
   insert(data: Partial<ArticleDetail>): Promise<ArticleDetail> {
      return http.post(ARTICLE_URLS.INSERT, data)
   }
}

export type { 
   ArticleItem, 
   ArticleDetail, 
   ArticleListResponse,
   Comment
}