<template>
   <div class="weekly-detail-page">
      <div class="weekly-container" v-if="weekly">
         <!-- 周报标题 -->
         <h1 class="weekly-title">{{ weekly.title }}</h1>
         
         <!-- 周报元信息 -->
         <div class="weekly-meta">
            <span class="date">
               <el-icon><Calendar /></el-icon>
               {{ weekly.publishDate }}
            </span>
            <span class="read-count">
               <el-icon><View /></el-icon>
               {{ weekly.readCount }}次阅读
            </span>
            <span class="like-count">
               <el-icon><Pointer /></el-icon>
               {{ weekly.likeCount }}人点赞
            </span>
         </div>

         <!-- 作者信息 -->
         <div class="author-card">
            <el-image class="author-avatar" :src="weekly.authorAvatar" fit="cover">
               <template #error>
                  <div class="avatar-placeholder">{{ weekly.author.charAt(0) }}</div>
               </template>
            </el-image>
            <div class="author-info">
               <div class="author-name">{{ weekly.author }}</div>
               <div class="author-bio">我们的理想必将实现</div>
            </div>
         </div>

         <!-- 周报内容 -->
         <div class="weekly-content">
            <p v-for="(paragraph, index) in contentParagraphs" :key="index">
               {{ paragraph }}
            </p>
         </div>

         <!-- 评论区 -->
         <div class="comment-section">
            <div class="section-header">
               <el-icon><ChatDotRound /></el-icon>
               <span>留言板</span>
            </div>
            
            <!-- 未登录 -->
            <div v-if="!isLoggedIn" class="login-prompt">
               <el-button type="primary" class="login-btn" @click="goToLogin">
                  <el-icon><Edit /></el-icon>
                  登入后发表评论
               </el-button>
            </div>
            
            <!-- 已登录 -->
            <div v-else class="comment-input-wrapper">
               <div class="current-user">
                  <el-image class="user-avatar" :src="currentUser?.avatar || ''" fit="cover">
                     <template #error>
                        <div class="avatar-placeholder">{{ currentUser?.username?.charAt(0) }}</div>
                     </template>
                  </el-image>
                  <span class="username">{{ currentUser?.username }}</span>
               </div>
               
               <el-input
                  v-model="commentContent"
                  type="textarea"
                  :rows="4"
                  placeholder="请在留言板中友善发言，理性讨论"
                  resize="none"
                  class="comment-textarea"
               />
               
               <div class="comment-actions">
                  <el-button type="primary" @click="submitComment">
                     <el-icon><Check /></el-icon>
                     发表评论
                  </el-button>
                  <el-button>
                     <el-icon><Refresh /></el-icon>
                     切换编辑器
                  </el-button>
                  <el-button>
                     <el-icon><Picture /></el-icon>
                     上传图片
                  </el-button>
               </div>
            </div>
            
            <!-- 评论列表 -->
            <div class="comment-list" v-if="weekly.comments?.length">
               <div 
                  v-for="comment in weekly.comments" 
                  :key="comment.id" 
                  class="comment-item"
                  :class="{ 'author-reply': comment.user === weekly.author }"
               >
                  <div class="comment-header">
                     <el-image class="comment-avatar" :src="comment.userAvatar || weekly.authorAvatar" fit="cover">
                        <template #error>
                           <div class="avatar-placeholder">{{ comment.user.charAt(0) }}</div>
                        </template>
                     </el-image>
                     <div class="comment-user">
                        <span class="username">{{ comment.user }}</span>
                        <span class="level">Lv.2</span>
                     </div>
                  </div>
                  <div class="comment-content">{{ comment.content }}</div>
                  <div class="comment-footer">
                     <span class="date">{{ comment.date }}</span>
                     <el-button size="small" type="primary">
                        <el-icon><ChatDotRound /></el-icon>
                        回复
                     </el-button>
                  </div>
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
   Calendar, 
   View, 
   Pointer, 
   ChatDotRound, 
   Edit, 
   Check, 
   Refresh, 
   Picture 
} from '@element-plus/icons-vue'
import { weeklyApi, type WeeklyDetail } from '@/api/modules/weekly'
import { useUserStore } from '@/stores/user'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const weekly = ref<WeeklyDetail | null>(null)
const commentContent = ref('')

const isLoggedIn = computed(() => userStore.isLoggedIn)
const currentUser = computed(() => userStore.userInfo)

const contentParagraphs = computed(() => {
   if (!weekly.value) return []
   return weekly.value.content.split('\n\n').filter(p => p.trim())
})

const fetchWeeklyDetail = async () => {
   const id = Number(route.params.id)
   if (!id) return
   
   try {
      const data = await weeklyApi.getById(id)
      weekly.value = data
   } catch (error) {
      console.error('获取周报详情失败:', error)
   }
}

const goToLogin = () => {
   router.push({
      path: '/login',
      query: { redirect: route.fullPath }
   })
}

const submitComment = async () => {
   if (!commentContent.value.trim()) return
   console.log('提交评论:', commentContent.value)
   commentContent.value = ''
}

onMounted(() => {
   fetchWeeklyDetail()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>