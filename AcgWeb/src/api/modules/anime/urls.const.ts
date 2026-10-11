// 动漫 API 地址常量
export const ANIME_URLS = {
   GET_ALL: '/anime/get',//获取所有简易数据
   GET_RANDOM:'/anime/get/random',//随机获取
   GET_LATEST:'/anime/get/latest',//最新作品
   GET_UPCOMING:'/anime/get/upcoming',//即将上映的番剧
   GET_BY_ID: (id: number) => `/anime/get/${id}`,  // 详情页
   UPDATE: (id: number) => `/anime/update/${id}`, //更新
   GET_RECENT_UPDATED: '/anime/get/recent-updated',//最近编辑
   INSERT: '/anime/insert' // 新增
} as const