import type { RouteRecordRaw } from 'vue-router'
import { Layout } from '@/routers/constant'

export default {
   path: '/share',
   component: Layout,
   children: [
      {
         path: '',
         name: 'Share',
         component: () => import('@/view/Share/index.vue'),
         meta: {
            title: '分享游玩记录',
            keepAlive: false
         }
      }
   ]
} as RouteRecordRaw