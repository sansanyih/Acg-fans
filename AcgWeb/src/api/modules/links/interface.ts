export interface LinkItem {
   id: number
   name: string       // 网站名称
   url: string        // 图片URL
   link: string       // 跳转链接
}

export type LinksListResponse = LinkItem[]