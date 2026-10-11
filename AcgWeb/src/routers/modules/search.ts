import type { RouteRecordRaw } from 'vue-router'
import { Layout } from '@/routers/constant'

export default {
   path: '/search',
   component: Layout,
   children: [
      {
         path: '',
         name: 'Search',
         component: () => import('@/view/Search/index.vue'),
         meta: {
            title: '搜索'
         }
      }
   ]
} as RouteRecordRaw
