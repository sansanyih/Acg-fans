import { http } from '@/api/index'
import { CHARACTER_URLS } from './urls.const'
import type { Character, CharacterListResponse } from './interface'

export const characterApi = {
   getAll(): Promise<CharacterListResponse> {
      return http.get(CHARACTER_URLS.GET_ALL)
   }
}

export type { Character, CharacterListResponse }