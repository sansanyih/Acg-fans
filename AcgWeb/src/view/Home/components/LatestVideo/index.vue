<template>
   <CardSlider title="最新视频" :scrollStep="860">
      <div 
         v-for="item in videoList" 
         :key="item.id"
         class="video-card"
         @click="goToDetail(item.id)"
      >
         <div class="thumbnail-wrapper">
            <el-image class="thumbnail" :src="item.thumbnail" fit="cover">
               <template #error>
                  <div class="thumbnail-fallback">
                     <el-icon><VideoPlay /></el-icon>
                  </div>
               </template>
            </el-image>
         </div>
         <div class="card-info">
            <h3 class="title" :title="item.title">{{ item.title }}</h3>
            <div class="meta">
               <span class="author">{{ item.author }}</span>
               <span class="date">{{ formatDate(item.publishDate) }}</span>
            </div>
         </div>
      </div>
   </CardSlider>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { VideoPlay } from '@element-plus/icons-vue'
import CardSlider from '@/components/ContentCardSlider/index.vue'
import { videoApi, type VideoItem } from '@/api/modules/video'

const router = useRouter()
const videoList = ref<VideoItem[]>([])

// 每列3个，计算列数
const columns = computed(() => {
   const result = []
   for (let i = 0; i < videoList.value.length; i += 3) {
      result.push(videoList.value.slice(i, i + 3))
   }
   return result
})

const formatDate = (dateStr: string) => {
   const date = new Date(dateStr)
   const now = new Date()
   const diff = now.getTime() - date.getTime()
   const days = Math.floor(diff / (1000 * 60 * 60 * 24))
   
   if (days === 0) return '今天'
   if (days < 7) return `${days}天前`
   if (days < 30) return `${Math.floor(days / 7)}周前`
   if (days < 365) return `${Math.floor(days / 30)}个月前`
   return `${Math.floor(days / 365)}年前`
}

const fetchVideoData = async () => {
   try {
      const data = await videoApi.getAll()
      videoList.value = data
   } catch (error) {
      console.error('获取视频失败:', error)
   }
}

const goToDetail = (id: number) => {
   router.push(`/video/${id}`)
}

onMounted(() => {
   fetchVideoData()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>