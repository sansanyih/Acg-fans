import type { RouteRecordRaw } from 'vue-router'

/**
 * @description 自动扫描 modules 目录下的所有路由模块
 */

// 获取所有 .ts 路由模块
const modules: Record<string, any> = import.meta.glob('./modules/*.ts', { eager: true })

// 收集所有路由
export const routerArray: RouteRecordRaw[] = []

Object.keys(modules).forEach((key) => {
   const mod = modules[key].default
   if (mod) {
      routerArray.push(mod)
   }
})
