import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../components/layout/Layout.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/Login.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/',
      component: Layout,
      meta: { requiresAuth: true },
      children: [
        {
          path: '',
          name: 'home',
          component: () => import('../views/Home.vue')
        },
        // 番剧管理路由
        {
          path: 'anime/list',
          name: 'animeList',
          component: () => import('../views/anime/List.vue')
        },
        {
          path: 'anime/add',
          name: 'animeAdd',
          component: () => import('../views/anime/Add.vue')
        },
        {
          path: 'anime/edit/:id',
          name: 'animeEdit',
          component: () => import('../views/anime/Edit.vue')
        },
        // 文章管理路由
        {
          path: 'article/list',
          name: 'articleList',
          component: () => import('../views/article/List.vue')
        },
        {
          path: 'article/add',
          name: 'articleAdd',
          component: () => import('../views/article/Add.vue')
        },
        {
          path: 'article/edit/:id',
          name: 'articleEdit',
          component: () => import('../views/article/Edit.vue')
        },
        // 视频管理路由
        {
          path: 'video/list',
          name: 'videoList',
          component: () => import('../views/video/List.vue')
        },
        {
          path: 'video/add',
          name: 'videoAdd',
          component: () => import('../views/video/Add.vue')
        },
        {
          path: 'video/edit/:id',
          name: 'videoEdit',
          component: () => import('../views/video/Edit.vue')
        },
        // 用户管理路由
        {
          path: 'user/list',
          name: 'userList',
          component: () => import('../views/user/List.vue')
        },
        {
          path: 'user/edit/:id',
          name: 'userEdit',
          component: () => import('../views/user/Edit.vue')
        }
      ]
    }
  ],
})

// 路由守卫，实现权限控制
router.beforeEach((to, from) => {
  const requiresAuth = to.meta.requiresAuth;
  const token = localStorage.getItem('token');
  
  if (requiresAuth && !token) {
    // 需要登录但未登录，重定向到登录页
    return { path: '/login' };
  } else if (!requiresAuth && token && to.path === '/login') {
    // 已登录但访问登录页，重定向到主页
    return { path: '/' };
  }
  // 其他情况，正常导航
});

export default router
