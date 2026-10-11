import type { RouteRecordRaw } from 'vue-router'
import { Layout } from '@/routers/constant'

export default {
   path: '/article/add',
   component: Layout,
   children: [
      {
         path: '',
         name: 'ArticleAdd',
         component: () => import('@/view/ArticleAdd/index.vue'),
         meta: {
            title: '发表文章'
         }
      }
   ]
} as RouteRecordRaw
