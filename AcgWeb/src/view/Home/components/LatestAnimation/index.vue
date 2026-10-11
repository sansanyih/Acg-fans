<template>
   <CardSlider title="近期新作" :scrollStep="260">
      <!-- 动漫卡片 -->
      <div v-for="item in animeList" :key="item.id" class="anime-card" @click="goToDetail(item.id)">
         <div class="card-image">
            <el-image :src="item.image" :alt="item.chineseTitle" fit="cover">
               <template #error>
                  <div class="image-placeholder">{{ item.chineseTitle.charAt(0) }}</div>
               </template>
            </el-image>
         </div>
         <div class="card-text">
            <div class="card-title">
               {{ item.title }}
            </div>
            <div class="card-desc">
               {{ item.description }}
            </div>
         </div>

      </div>
      <!-- 换一批卡片（放在最后） -->
      <div class="anime-card refresh-card" @click="refresh">
         <div class="refresh-content">
            <el-icon class="refresh-icon">
               <Refresh />
            </el-icon>
            <span>换一批</span>
         </div>
      </div>
   </CardSlider>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Refresh } from '@element-plus/icons-vue'
import CardSlider from '@/components/ContentCardSlider/index.vue'
import { animeApi, type AnimeItem } from '@/api/modules/anime'

const router = useRouter()
const animeList = ref<AnimeItem[]>([])

const fetchAnimeData = async () => {
   try {
      const data = await animeApi.getLatest()
      animeList.value = data
   } catch (error) {
      console.error('获取最新动漫失败:', error)
   }
}

const refresh = () => {
   fetchAnimeData()
}

const goToDetail = (id: number) => {
   router.push(`/anime/${id}`)
}

onMounted(() => {
   fetchAnimeData()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>