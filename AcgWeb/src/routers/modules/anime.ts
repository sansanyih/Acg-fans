import type { RouteRecordRaw } from 'vue-router'
import { Layout } from '@/routers/constant'

export default {
   path: '/anime',
   component: Layout,
   children: [
      {
         path: ':id',
         name: 'AnimeDetail',
         component: () => import('@/view/AnimeDetail/index.vue'),
         meta: {
            title: '动画详情',
            keepAlive: false
         }
      },
      {
         path: 'edit/:id',
         name: 'AnimeEdit',
         component: () => import('@/view/AnimeEdit/index.vue'),
         meta: {
            title: '编辑动画',
            keepAlive: false
         }
      }
   ]
} as RouteRecordRaw