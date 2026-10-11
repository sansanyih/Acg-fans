<template>
   <div class="anime-detail-page" v-loading="loading">
      <div class="detail-container" v-if="anime">
         <!-- 顶部信息区 -->
         <div class="hero-section">
            <div class="hero-main">
               <div class="cover-large">
                  <el-image :src="anime.image" fit="cover">
                     <template #error>
                        <div class="cover-fallback">{{ anime.chineseTitle.charAt(0) }}</div>
                     </template>
                  </el-image>
               </div>
               <div class="hero-info">
                  <h1 class="title">{{ anime.chineseTitle }}</h1>
                  <div class="subtitle">{{ anime.title }}</div>
                  <div class="tags">
                     <el-tag v-for="tag in anime.tags" :key="tag" type="danger" effect="light">{{ tag }}</el-tag>
                     <el-tag type="info" effect="light">{{ anime.studio }}</el-tag>
                  </div>
                  <p class="description">{{ anime.description }}</p>
 <div class="actions">
   <el-button type="danger" :icon="Star">收藏</el-button>
   <el-button :icon="Edit" @click="handleEdit">编辑</el-button>
</div>
               </div>
            </div>
         </div>

         <div class="detail-body">
            <!-- 左侧内容 -->
            <div class="left-content">
               <!-- 评分区域 -->
               <div class="rating-section" v-if="anime.viewingRecords?.length">
                  <div class="section-header">
                     <el-icon><TrendCharts /></el-icon>
                     <span>观看记录</span>
                     <el-button text class="more-btn">查看详情</el-button>
                  </div>
                  <div class="rating-content">
                     <div class="radar-chart" ref="radarChartRef"></div>
                     <div class="rating-list">
                        <div v-for="record in anime.viewingRecords.slice(0, 2)" :key="record.id" class="rating-card">
                           <div class="rating-user">
                              <el-image class="user-avatar" :src="record.userAvatar" fit="cover" />
                              <div>
                                 <div class="username">{{ record.user }}</div>
                                 <div class="rating-score">
                                    总评({{ record.rating }})
                                    <el-rate :model-value="record.rating / 2" disabled show-score />
                                 </div>
                              </div>
                           </div>
                           <p class="rating-comment">{{ record.comment }}</p>
                           <div class="rating-date">{{ record.date }}</div>
                        </div>
                     </div>
                  </div>
               </div>

               <!-- 介绍 -->
               <div class="info-section">
                  <div class="section-header">
                     <el-icon><Document /></el-icon>
                     <span>介绍</span>
                  </div>
                  <div class="section-content">
                     <h3>作品简介</h3>
                     <p>{{ anime.introduction }}</p>
                  </div>
               </div>

               <!-- 留言板 -->
               <div class="comment-section">
                  <div class="section-header">
                     <el-icon><ChatDotSquare /></el-icon>
                     <span>留言板</span>
                  </div>
                  
                  <div v-if="!isLoggedIn" class="login-prompt">
                     <p>请在留言板中友善发言，理性讨论</p>
                     <el-button type="primary" @click="goToLogin">登入后发表评论</el-button>
                  </div>
                  
                  <div v-else class="comment-input">
                     <el-input v-model="commentContent" type="textarea" :rows="3" placeholder="发表你的评论" />
                     <el-button type="primary" @click="submitComment">发表评论</el-button>
                  </div>

                  <div class="comment-list">
                     <div v-for="comment in anime.comments" :key="comment.id" class="comment-item">
                        <el-image class="comment-avatar" :src="comment.userAvatar" fit="cover" />
                        <div class="comment-body">
                           <div class="comment-header">
                              <span class="username">{{ comment.user }}</span>
                              <span class="date">{{ comment.date }}</span>
                           </div>
                           <p class="comment-text">{{ comment.content }}</p>
                        </div>
                     </div>
                  </div>
               </div>
            </div>

            <!-- 右侧侧边栏 -->
            <div class="right-sidebar">
               <!-- 基础信息 -->
               <div class="info-card">
                  <div class="card-title">
                     <el-icon><InfoFilled /></el-icon>
                     <span>基础信息</span>
                  </div>
                  <div class="info-item"><span>原作：</span>{{ anime.originalWork }}</div>
                  <div class="info-item"><span>监督：</span>{{ anime.director }}</div>
                  <div class="info-item"><span>系列构成：</span>{{ anime.seriesComposition }}</div>
                  <div class="info-item"><span>人物设计：</span>{{ anime.characterDesign }}</div>
                  <div class="info-item"><span>音乐：</span>{{ anime.music }}</div>
                  <div class="info-item"><span>制作公司：</span>{{ anime.studio }}</div>
                  <div class="info-item"><span>集数：</span>{{ anime.episodes }}集</div>
                  <div class="info-item"><span>开播日期：</span>{{ anime.startDate }}</div>
                  <div class="info-item"><span>更新时间：</span>{{ anime.broadcastDay }}</div>
               </div>

               <!-- 登场角色 -->
               <div class="info-card" v-if="anime.characters?.length">
                  <div class="card-title">
                     <el-icon><User /></el-icon>
                     <span>登场角色</span>
                  </div>
                  <div class="character-list">
                     <div v-for="char in anime.characters" :key="char.id" class="character-item">
                        <el-image class="char-image" :src="char.image" fit="cover" />
                        <div class="char-info">
                           <div class="char-name">{{ char.name }}</div>
                           <div class="char-actor">CV：{{ char.voiceActor }}</div>
                        </div>
                     </div>
                  </div>
               </div>

               <!-- 外部链接 -->
               <div class="info-card" v-if="anime.officialWebsite">
                  <div class="card-title">
                     <el-icon><Link /></el-icon>
                     <span>外部链接</span>
                  </div>
                  <a :href="anime.officialWebsite" target="_blank" class="external-link">
                     前往官网
                  </a>
               </div>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, nextTick } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
   Star,
   Edit,
   TrendCharts,
   Document,
   ChatDotSquare,
   InfoFilled,
   User,
   Link
} from '@element-plus/icons-vue'
import { animeApi, type AnimeDetail } from '@/api/modules/anime'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'
import * as echarts from 'echarts'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const anime = ref<AnimeDetail | null>(null)
const loading = ref(false)
const commentContent = ref('')
const radarChartRef = ref<HTMLElement>()

const isLoggedIn = computed(() => userStore.isLoggedIn)

const fetchAnimeDetail = async () => {
   const id = Number(route.params.id)
   if (!id) return
   
   loading.value = true
   try {
      const data = await animeApi.getById(id)
      anime.value = data
      nextTick(() => initRadarChart())
   } catch (error) {
      ElMessage.error('获取动画详情失败')
   } finally {
      loading.value = false
   }
}
const handleEdit = () => {
   if (!isLoggedIn.value) {
      goToLogin()
      return
   }
   router.push(`/anime/edit/${route.params.id}`)
}

const initRadarChart = () => {
   if (!radarChartRef.value || !anime.value?.viewingRecords?.length) return
   
   const chart = echarts.init(radarChartRef.value)
   const record = anime.value.viewingRecords[0]
   
   // 添加空值检查
   if (!record) return
   
   const option = {
      radar: {
         indicator: [
            { name: '音乐', max: 10 },
            { name: '美术', max: 10 },
            { name: '剧本', max: 10 },
            { name: '演出', max: 10 },
            { name: '程序', max: 10 },
            { name: '配音', max: 10 }
         ],
         radius: '70%'
      },
      series: [{
         type: 'radar',
         data: [{
            value: [
               record.musicRating,
               record.artRating,
               record.scriptRating,
               record.performanceRating,
               record.programRating,
               record.voiceRating
            ],
            name: '评分',
            areaStyle: { color: 'rgba(196, 30, 58, 0.2)' },
            lineStyle: { color: '#c41e3a' },
            itemStyle: { color: '#c41e3a' }
         }]
      }]
   }
   
   chart.setOption(option)
}

const goToLogin = () => {
   router.push({ path: '/login', query: { redirect: route.fullPath } })
}

const submitComment = () => {
   if (!commentContent.value.trim()) return
   ElMessage.success('评论提交成功')
   commentContent.value = ''
}

onMounted(() => {
   fetchAnimeDetail()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>