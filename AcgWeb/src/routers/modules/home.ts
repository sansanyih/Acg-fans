import type { RouteRecordRaw } from 'vue-router'
import { Layout } from '@/routers/constant'

export default {
   path: '/',
   component: Layout,
   redirect: '/home',
   children: [
      {
         path: '/home',
         name: 'Home',
         component: () => import('@/view/Home/index.vue'),
         meta: {
            title: '首页',
            keepAlive: true
         }
      },
      {
         path: '/articles/index/:id',
         name: 'ArticleDetail',
         component: () => import('@/view/ArticleDetail/index.vue'),
         meta: {
            title: '文章详情'
         }
      },
   {
      path: '/weekly/:id',
      name: 'WeeklyDetail',
      component: () => import('@/view/WeeklyDetail/index.vue'),
      meta: {
         title: '周报详情'
      }
   }
   ]
} as RouteRecordRaw