<template>
   <div class="weekly-section">
      <h2 class="section-title">每周速报</h2>
      <div class="weekly-list">
         <div 
            v-for="(item, index) in weeklyList" 
            :key="item.id"
            class="weekly-item"
            :class="{ 'is-new': index === 0 }"
            @click="goToDetail(item.id)"
         >
            <span v-if="index === 0" class="new-tag">New</span>
            <span class="weekly-title">{{ item.title }}</span>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { weeklyApi, type WeeklyItem } from '@/api/modules/weekly'

const router = useRouter()
const weeklyList = ref<WeeklyItem[]>([])

const fetchWeeklyData = async () => {
   try {
      const data = await weeklyApi.getAll()
      weeklyList.value = data.slice(0, 5)  // 只显示前5条
   } catch (error) {
      console.error('获取周报失败:', error)
   }
}

const goToDetail = (id: number) => {
   router.push(`/weekly/${id}`)
}

onMounted(() => {
   fetchWeeklyData()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>