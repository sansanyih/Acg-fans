
import { createRouter, createWebHistory } from 'vue-router'
import { routerArray } from './router'

const router = createRouter({
   history: createWebHistory(import.meta.env.BASE_URL),
   routes: routerArray,
   // 添加滚动行为
   scrollBehavior() {
      return { top: 0 }
   }
})

// 只设置标题，不强制登录
router.beforeEach((to) => {
   document.title = to.meta.title ? `${to.meta.title} - CnAcg` : 'CnAcg'
})

export default router

