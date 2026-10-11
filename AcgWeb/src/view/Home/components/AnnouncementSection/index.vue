<template>
   <div class="announcement-section">
      <h2 class="section-title">公告</h2>
      <div class="announcement-list">
         <div 
            v-for="(item, index) in announcementList" 
            :key="item.id"
            class="announcement-item"
            :class="{ 'is-new': index === 0 }"
            @click="goToDetail(item.id)"
         >
            <span v-if="index === 0" class="new-tag">New</span>
            <el-icon class="announcement-icon"><Bell /></el-icon>
            <span class="announcement-title">{{ item.title }}</span>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Bell } from '@element-plus/icons-vue'
import { announcementApi, type AnnouncementItem } from '@/api/modules/announcement'

const router = useRouter()
const announcementList = ref<AnnouncementItem[]>([])

const fetchData = async () => {
   try {
      const data = await announcementApi.getAll()
      announcementList.value = data.slice(0, 6)
   } catch (error) {
      console.error('获取公告失败:', error)
   }
}

const goToDetail = (id: number) => {
   router.push(`/announcement/${id}`)
}

onMounted(() => {
   fetchData()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>