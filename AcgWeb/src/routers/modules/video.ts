import type { RouteRecordRaw } from 'vue-router'
import { Layout } from '@/routers/constant'

export default {
   path: '/video/:id',
   component: Layout,  // 需要使用 Layout 布局
   children: [
      {
         path: '',
         name: 'VideoDetail',
         component: () => import('@/view/VideoDetail/index.vue'),
         meta: {
            title: '视频详情',
            keepAlive: false
         }
      }
   ]
} as RouteRecordRaw