export interface Character {
   id: number
   name: string
   japaneseName: string
   image: string
   animeId: number
   animeTitle: string
   animeChineseTitle: string
   voiceActor: string
   gender: string
   age: number
   height: string
   weight: string
   birthday: string
   description: string
   cv: string
   likes: number
   dislikes: number
}

export type CharacterListResponse = Character[]