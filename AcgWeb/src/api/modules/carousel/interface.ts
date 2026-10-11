// 轮播图数据类型
export interface CarouselItem {
   id: number
   url: string        // 图片URL（后端字段）
   title: string      // 标题
}

export type CarouselListResponse = CarouselItem[]