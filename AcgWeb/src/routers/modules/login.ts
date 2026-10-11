import type { RouteRecordRaw } from 'vue-router'

export default {
   path: '/login',
   name: 'Login',
   component: () => import('@/view/login/index.vue'),
   meta: {
      title: '登录',
      noAuth: true  // 不需要登录
   }
} as RouteRecordRaw