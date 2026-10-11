export const ANNOUNCEMENT_URLS = {
   GET_ALL: '/announcement/get',
   GET_BY_ID: (id: number) => `/announcement/get/${id}`
} as const