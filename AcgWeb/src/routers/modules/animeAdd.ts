import type { RouteRecordRaw } from 'vue-router'
import { Layout } from '@/routers/constant'

export default {
   path: '/anime/add',
   component: Layout,
   children: [
      {
         path: '',
         name: 'AnimeAdd',
         component: () => import('@/view/AnimeAdd/index.vue'),
         meta: {
            title: '创建词条'
         }
      }
   ]
} as RouteRecordRaw
