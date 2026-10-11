// 周报 API 地址常量
export const WEEKLY_URLS = {
   GET_ALL: '/weekly/get',
   GET_BY_ID: (id: number) => `/weekly/get/${id}`
} as const