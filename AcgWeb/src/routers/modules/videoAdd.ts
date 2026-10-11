import type { RouteRecordRaw } from 'vue-router'
import { Layout } from '@/routers/constant'

export default {
   path: '/video/add',
   component: Layout,
   children: [
      {
         path: '',
         name: 'VideoAdd',
         component: () => import('@/view/VideoAdd/index.vue'),
         meta: {
            title: '发布视频'
         }
      }
   ]
} as RouteRecordRaw
