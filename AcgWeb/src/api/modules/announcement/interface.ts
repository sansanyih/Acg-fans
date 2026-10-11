export interface AnnouncementItem {
   id: number
   cover: string
   title: string
   content: string
   author: string
   authorAvatar: string
   publishDate: string
   readCount: number
   likeCount: number
   summary: string
   lastEdit: string
}

export type AnnouncementListResponse = AnnouncementItem[]