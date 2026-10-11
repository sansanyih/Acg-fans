<template>
   <div class="video-detail-page" v-loading="loading">
      <div class="detail-container" v-if="video">
         <!-- 顶部视频信息 -->
         <div class="video-header">
            <div class="video-main">
               <div class="thumbnail-large">
                  <el-image :src="video.thumbnail" fit="cover">
                     <template #error>
                        <div class="thumbnail-fallback">
                           <el-icon><VideoPlay /></el-icon>
                        </div>
                     </template>
                  </el-image>
               </div>
               <div class="video-info">
                  <h1 class="title">{{ video.title }}</h1>
                  <p class="description">{{ video.description }}</p>
               </div>
            </div>
         </div>

         <div class="detail-body">
            <!-- 左侧内容 -->
            <div class="left-content">
               <!-- 介绍 -->
               <div class="info-section">
                  <div class="section-title">
                     <el-icon><Document /></el-icon>
                     <span>介绍</span>
                  </div>
                  <div class="section-content">
                     <p v-for="(para, idx) in descriptionParagraphs" :key="idx">{{ para }}</p>
                  </div>
               </div>

               <!-- 相关词条 -->
               <div class="info-section" v-if="video.relatedAnime?.length">
                  <div class="section-title">
                     <el-icon><Collection /></el-icon>
                     <span>相关词条</span>
                     <el-button text class="collapse-btn">折叠</el-button>
                  </div>
                  <div class="anime-list">
                     <div v-for="anime in video.relatedAnime" :key="anime.id" class="anime-card">
                        <el-image class="anime-cover" :src="anime.image" fit="cover" />
                        <div class="anime-info">
                           <div class="anime-title">
                              <span>{{ anime.chineseTitle }}</span>
                           </div>
                           <p class="anime-desc">{{ anime.description }}</p>
                           <div class="anime-meta">
                              <span>2020-03-17</span>
                              <span>1条评论</span>
                              <span>20348次阅读</span>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>

               <!-- 留言板 -->
               <div class="comment-section">
                  <div class="section-title">
                     <el-icon><ChatDotSquare /></el-icon>
                     <span>留言板</span>
                  </div>
                  
                  <!-- 未登录 -->
                  <div v-if="!isLoggedIn" class="login-prompt">
                     <p class="prompt-text">请在留言板中友善发言，理性讨论</p>
                     <el-button type="primary" class="login-btn" @click="goToLogin">
                        <el-icon><Edit /></el-icon>
                        登入后发表评论
                     </el-button>
                  </div>
                  
                  <!-- 已登录 -->
                  <div v-else class="comment-input-wrapper">
                     <el-input
                        v-model="commentContent"
                        type="textarea"
                        :rows="3"
                        placeholder="请在留言板中友善发言，理性讨论"
                        resize="none"
                        class="comment-textarea"
                     />
                     <div class="comment-actions">
                        <el-button type="primary" @click="submitComment">
                           <el-icon><Check /></el-icon>
                           发表评论
                        </el-button>
                     </div>
                  </div>

                  <!-- 评论列表 -->
                  <div class="comment-list" v-if="video.comments?.length">
                     <div v-for="comment in video.comments" :key="comment.id" class="comment-item">
                        <el-image class="comment-avatar" :src="comment.userAvatar" fit="cover" />
                        <div class="comment-body">
                           <div class="comment-header">
                              <span class="username">{{ comment.user }}</span>
                              <span class="date">{{ comment.date }}</span>
                           </div>
                           <p class="comment-content">{{ comment.content }}</p>
                        </div>
                     </div>
                  </div>
               </div>
            </div>

            <!-- 右侧侧边栏 -->
            <div class="right-sidebar">
               <!-- 作者卡片 -->
               <div class="author-card">
                  <div class="author-cover">
                     <el-image :src="video.thumbnail" fit="cover" />
                  </div>
                  <div class="author-avatar-large">
                     <el-image :src="video.authorAvatar" fit="cover" />
                  </div>
                  <div class="author-info">
                     <h3 class="author-name">{{ video.author }}</h3>
                     <div class="author-badges">
                        <span class="badge level">Lv{{ video.authorLevel }}</span>
                        <span class="badge role">{{ video.authorRole }}</span>
                        <span class="badge tag">作者</span>
                     </div>
                     <p class="author-bio">{{ video.authorBio }}</p>
                  </div>
               </div>

               <!-- 基础信息 -->
               <div class="info-card">
                  <div class="card-title">
                     <el-icon><InfoFilled /></el-icon>
                     <span>基础信息</span>
                  </div>
                  <div class="info-list">
                     <div class="info-item">
                        <el-icon><Headset /></el-icon>
                        <span>音乐综合</span>
                     </div>
                     <div class="info-item">
                        <el-icon><CircleCheck /></el-icon>
                        <span>原创</span>
                     </div>
                     <div class="info-item">
                        <el-icon><View /></el-icon>
                        <span>浏览数：3025</span>
                     </div>
                     <div class="info-item">
                        <el-icon><Timer /></el-icon>
                        <span>时长：00:02:01</span>
                     </div>
                     <div class="info-item">
                        <el-icon><User /></el-icon>
                        <span>原作者：{{ video.author }}</span>
                     </div>
                     <div class="info-item">
                        <el-icon><Clock /></el-icon>
                        <span>发布时间：{{ video.publishDate }}</span>
                     </div>
                  </div>
               </div>

               <!-- 外部链接 -->
               <div class="info-card" v-if="video.externalLink">
                  <div class="card-title">
                     <el-icon><Link /></el-icon>
                     <span>外部链接</span>
                     <el-button text class="collapse-btn">折叠</el-button>
                  </div>
                  <div class="external-link-hint">
                     <el-icon><Warning /></el-icon>
                     <span>以下为外部链接，与本站没有任何从属关系，本站亦不对其安全性负责</span>
                  </div>
                  <a :href="video.externalLink" target="_blank" class="link-button">
                     <div class="link-icon">
                        <img src="https://www.bilibili.com/favicon.ico" alt="bilibili" />
                     </div>
                     <span>前往 bilibili 观看</span>
                  </a>
               </div>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import {
   VideoPlay,
   Document,
   Collection,
   ChatDotSquare,
   Edit,
   Check,
   Refresh,
   Upload,
   InfoFilled,
   Headset,
   CircleCheck,
   View,
   Timer,
   User,
   Clock,
   Link,
   Warning
} from '@element-plus/icons-vue'
import { videoApi, type VideoDetail } from '@/api/modules/video'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const video = ref<VideoDetail | null>(null)
const loading = ref(false)
const commentContent = ref('')

const isLoggedIn = computed(() => userStore.isLoggedIn)

const descriptionParagraphs = computed(() => {
   if (!video.value) return []
   return video.value.description.split('\n').filter(p => p.trim())
})

const fetchVideoDetail = async () => {
   const id = Number(route.params.id)
   if (!id) return
   
   loading.value = true
   try {
      const data = await videoApi.getById(id)
      video.value = data
   } catch (error) {
      ElMessage.error('获取视频详情失败')
   } finally {
      loading.value = false
   }
}

const goToLogin = () => {
   router.push({
      path: '/login',
      query: { redirect: route.fullPath }
   })
}

const submitComment = () => {
   if (!commentContent.value.trim()) return
   ElMessage.success('评论提交成功')
   commentContent.value = ''
}

onMounted(() => {
   fetchVideoDetail()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>