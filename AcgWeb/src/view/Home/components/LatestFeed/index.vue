<template>
   <CardSlider title="最新动态" :scrollStep="800">
      <div class="dynamics-grid">
         <a 
            v-for="item in feedList" 
            :key="item.id"
            :href="item.link"
            target="_blank"
            class="dynamic-card"
         >
            <el-image class="user-avatar" :src="item.userAvatar" fit="cover">
               <template #error>
                  <div class="avatar-fallback">{{ item.username.charAt(0) }}</div>
               </template>
            </el-image>
            <div class="card-info">
               <div class="info-header">
                  <span class="username">{{ item.username }}</span>
                  <span class="time">{{ item.time }}</span>
               </div>
               <p class="content">{{ item.content }}</p>
            </div>
         </a>
      </div>
   </CardSlider>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import CardSlider from '@/components/ContentCardSlider/index.vue'
import { feedApi, type FeedItem } from '@/api/modules/feed'

const feedList = ref<FeedItem[]>([])

const formatTime = (timeStr: string) => {
   const date = new Date(timeStr)
   const now = new Date()
   const diff = now.getTime() - date.getTime()
   const days = Math.floor(diff / (1000 * 60 * 60 * 24))
   
   if (days === 0) return '今天'
   if (days === 1) return '1天前'
   return `${days}天前`
}

const fetchFeedData = async () => {
   try {
      const data = await feedApi.getAll()
      feedList.value = data
   } catch (error) {
      console.error('获取动态失败:', error)
   }
}

onMounted(() => {
   fetchFeedData()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>