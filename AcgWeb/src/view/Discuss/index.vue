<template>
   <div class="discuss-page">
      <div class="container">
         <!-- 多线多结局 - 使用 CardSlider -->
         <div class="section-card">
            <div class="section-header">
               <div class="header-left">
                  <el-icon>
                     <Collection />
                  </el-icon>
                  <span>{{ currentTag ? `  ${currentTag}` : '' }}</span>
               </div>
               <el-button text class="more-btn" @click="refreshTag">
                  <el-icon>
                     <Refresh />
                  </el-icon>
                  换一批
               </el-button>
            </div>
            <CardSlider title="" :scrollStep="300">
               <div v-for="item in tagAnimeList" :key="item.id" class="anime-card" @click="goToAnime(item.id)">
                  <div class="card-image">
                     <el-image :src="item.image" fit="cover">
                        <template #error>
                           <div class="image-fallback">{{ item.chineseTitle.charAt(0) }}</div>
                        </template>
                     </el-image>
                  </div>
                  <div class="card-info">
                     <h3 class="card-title">{{ item.chineseTitle }}</h3>
                     <p class="card-desc">{{ item.description }}</p>
                  </div>
               </div>
            </CardSlider>
         </div>

         <!-- 随机评价 - 使用 CardSlider -->
         <div class="section-card">
            <div class="section-header">
               <div class="header-left">
                  <el-icon>
                     <ChatDotRound />
                  </el-icon>
                  <span>随机评价</span>
               </div>
               <el-button type="primary" class="refresh-btn" @click="refreshRatings">
                  <el-icon>
                     <Refresh />
                  </el-icon>
                  换一批
               </el-button>
            </div>
            <CardSlider title="" :scrollStep="420">
               <div v-for="item in ratingList" :key="item.id" class="rating-card" @click="goToAnime(item.animeId)">
                  <div class="rating-header">
                     <el-image class="user-avatar" :src="item.userAvatar" fit="cover">
                        <template #error>
                           <div class="avatar-fallback">{{ item.user.charAt(0) }}</div>
                        </template>
                     </el-image>
                     <div class="user-info">
                        <div class="username">{{ item.user }}</div>
                        <div class="user-bio">哇，这里什么都没有呢</div>
                     </div>
                  </div>
                  <div class="rating-body">
                     <div class="rating-score">
                        <span>总评({{ item.rating }})</span>
                        <el-rate :model-value="item.rating / 2" disabled show-score />
                     </div>
                     <div class="rating-detail">
                        音乐{{ item.musicRating }}分,美术{{ item.artRating }}分,剧本{{ item.scriptRating }}分,演出{{
                           item.performanceRating
                        }}分,程序{{ item.programRating }}分,配音{{ item.voiceRating }}分
                     </div>
                     <p class="rating-comment">{{ item.comment }}</p>
                     <div class="rating-anime">《{{ item.animeChineseTitle }}》</div>
                     <div class="rating-date">{{ item.date }}</div>
                  </div>
               </div>
            </CardSlider>
         </div>

         <!-- 编辑概览 -->
         <div class="section-card">
            <div class="section-header">
               <div class="header-left">
                  <el-icon>
                     <TrendCharts />
                  </el-icon>
                  <span>编辑概览</span>
               </div>
               <el-select v-model="timeRange" size="small" style="width: 100px">
                  <el-option label="1个月" value="1month" />
                  <el-option label="3个月" value="3month" />
                  <el-option label="半年" value="6month" />
               </el-select>
            </div>
            <div ref="chartRef" class="statistics-chart"></div>
         </div>

         <!-- 底部链接 -->
         <div class="footer-links">
            <div class="link-card">
               <div class="link-title">
                  <el-icon>
                     <HomeFilled />
                  </el-icon>
                  <span>CnGal资料站</span>
               </div>
               <div class="link-list">
                  <a href="#">关于我们</a>
                  <a href="#">组织架构</a>
                  <a href="#">隐私政策</a>
                  <a href="#">数据汇总</a>
               </div>
            </div>
            <div class="link-card">
               <div class="link-title">
                  <el-icon>
                     <PriceTag />
                  </el-icon>
                  <span>标签</span>
               </div>
               <div class="link-list">
                  <a href="#"><el-icon>
                        <VideoCamera />
                     </el-icon>游戏</a>
                  <a href="#"><el-icon>
                        <User />
                     </el-icon>角色</a>
                  <a href="#"><el-icon>
                        <Star />
                     </el-icon>STAFF</a>
                  <a href="#"><el-icon>
                        <OfficeBuilding />
                     </el-icon>制作组</a>
               </div>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import {
   Collection,
   Refresh,
   ChatDotRound,
   TrendCharts,
   HomeFilled,
   PriceTag,
   VideoCamera,
   User,
   Star,
   OfficeBuilding
} from '@element-plus/icons-vue'
import CardSlider from '@/components/ContentCardSlider/index.vue'
import { tagApi, type RandomTagResponse } from '@/api/modules/tag'
import { ratingApi, type RatingItem } from '@/api/modules/rating'
import { statisticsApi, type EditStatistics } from '@/api/modules/statistics'
import * as echarts from 'echarts'

const router = useRouter()

const tagAnimeList = ref<RandomTagResponse['anime']>([])
const currentTag = ref('')
const ratingList = ref<RatingItem[]>([])
const statisticsData = ref<Record<string, EditStatistics>>({})
const timeRange = ref('1month')
const chartRef = ref<HTMLElement>()

// 获取随机标签番剧
const fetchTagAnime = async () => {
   try {
      const data = await tagApi.getRandom()
      currentTag.value = data.tag
      tagAnimeList.value = data.anime
   } catch (error) {
      console.error('获取标签番剧失败:', error)
   }
}

// 获取随机评价
const fetchRatings = async () => {
   try {
      const data = await ratingApi.getRandom()
      ratingList.value = data
   } catch (error) {
      console.error('获取评价失败:', error)
   }
}

// 获取统计数据
const fetchStatistics = async () => {
   try {
      const data = await statisticsApi.getEditsRecent()
      statisticsData.value = data
      initChart()
   } catch (error) {
      console.error('获取统计失败:', error)
   }
}

// 刷新
const refreshTag = () => fetchTagAnime()
const refreshRatings = () => fetchRatings()

// 初始化图表
const initChart = () => {
   if (!chartRef.value || !statisticsData.value) return

   const chart = echarts.init(chartRef.value)

   const dates = Object.keys(statisticsData.value).sort()
   const data = dates.map(date => statisticsData.value[date] as EditStatistics)

   const animeData = data.map(item => item.anime)
   const articleData = data.map(item => item.article)
   const videoData = data.map(item => item.video)
   const characterData = data.map(item => item.character)

   const displayDates = dates.map(date => date.slice(5))

   const option = {
      tooltip: {
         trigger: 'axis',
         axisPointer: { type: 'shadow' }
      },
      legend: {
         data: ['词条', '文章', '视频', '角色'],
         bottom: 0
      },
      grid: {
         left: '3%',
         right: '4%',
         bottom: '10%',
         top: '10%',
         containLabel: true
      },
      xAxis: {
         type: 'category',
         data: displayDates
      },
      yAxis: {
         type: 'value'
      },
      series: [
         { name: '词条', type: 'bar', stack: 'total', data: animeData, itemStyle: { color: '#5470c6' } },
         { name: '文章', type: 'bar', stack: 'total', data: articleData, itemStyle: { color: '#91cc75' } },
         { name: '视频', type: 'bar', stack: 'total', data: videoData, itemStyle: { color: '#fac858' } },
         { name: '角色', type: 'bar', stack: 'total', data: characterData, itemStyle: { color: '#ee6666' } }
      ]
   }

   chart.setOption(option)
}

const goToAnime = (id: number) => {
   router.push(`/anime/${id}`)
}

watch(timeRange, fetchStatistics)

onMounted(() => {
   fetchTagAnime()
   fetchRatings()
   fetchStatistics()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>