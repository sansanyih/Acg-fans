<template>
   <div class="recent-edit-section">
      <h2 class="section-title">近期编辑</h2>
      <div class="edit-list">
         <div 
            v-for="item in editList" 
            :key="item.id" 
            class="edit-item"
            @click="goToAnime(item.id)"
         >
            <el-image class="edit-image" :src="item.image" fit="cover">
               <template #error>
                  <div class="image-fallback">{{ item.chineseTitle.charAt(0) }}</div>
               </template>
            </el-image>
            <div class="edit-info">
               <div class="edit-title">{{ item.chineseTitle }}</div>
               <div class="edit-time">{{ formatTime(item.lastEdit) }}</div>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { animeApi, type AnimeItem } from '@/api/modules/anime'

const router = useRouter()
const editList = ref<AnimeItem[]>([])

const formatTime = (timeStr: string) => {
   const date = new Date(timeStr)
   const now = new Date()
   const diff = now.getTime() - date.getTime()
   const hours = Math.floor(diff / (1000 * 60 * 60))
   const days = Math.floor(diff / (1000 * 60 * 60 * 24))
   
   if (hours < 1) return '刚刚'
   if (hours < 24) return `${hours}小时前`
   if (days < 7) return `${days}天前`
   return date.toLocaleDateString()
}

const fetchData = async () => {
   try {
      const data = await animeApi.getRecentUpdated()
      editList.value = data
   } catch (error) {
      console.error('获取近期编辑失败:', error)
   }
}

const goToAnime = (id: number) => {
   router.push(`/anime/${id}`)
}

onMounted(() => {
   fetchData()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>