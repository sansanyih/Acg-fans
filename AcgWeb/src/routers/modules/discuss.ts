import type { RouteRecordRaw } from 'vue-router'
import { Layout } from '@/routers/constant'

export default {
   path: '/discuss',
   component: Layout,
   children: [
      {
         path: '',
         name: 'Discuss',
         component: () => import('@/view/Discuss/index.vue'),
         meta: {
            title: '广场',
            keepAlive: true
         }
      }
   ]
} as RouteRecordRaw