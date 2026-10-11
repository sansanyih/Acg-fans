/**
 * @description 常量路由配置
 */

// Layout 懒加载
export const Layout = () => import('@/layout/index.vue')

// 404 页面
export const NotFound = () => import('@/view/error/index.vue')
