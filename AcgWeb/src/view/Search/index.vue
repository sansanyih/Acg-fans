<template>
   <div class="search-page">
      <!-- 搜索主页 -->
      <div v-if="viewMode === 'home'" class="search-home">
         <button class="back-btn" @click="goHome">
            <el-icon><ArrowLeft /></el-icon>
            <span>返回首页</span>
         </button>
         <h1 class="search-title">CnAcg 搜索</h1>
         <div class="search-box">
            <el-input
               v-model="keyword"
               type="textarea"
               :rows="3"
               resize="none"
               placeholder="搜索番剧、文章、视频、角色..."
               class="search-input"
               @keydown.enter.prevent="handleSearch"
            />
            <div class="search-actions">
               <div class="action-left">
                  <button
                     class="ai-toggle-btn"
                     :class="{ active: isAiMode }"
                     @click="isAiMode = !isAiMode"
                  >
                     <el-icon><Cpu /></el-icon>
                     <span>{{ isAiMode ? '使用 AI' : '不使用 AI' }}</span>
                  </button>
               </div>
               <div class="action-right">
                  <button
                     class="send-btn"
                     :disabled="!keyword.trim()"
                     @click="handleSearch"
                  >
                     <el-icon><ArrowUp /></el-icon>
                  </button>
               </div>
            </div>
         </div>
      </div>

      <!-- 搜索结果 / AI 对话 -->
      <div v-else class="search-content">
         <button class="back-btn top" @click="goHome">
            <el-icon><ArrowLeft /></el-icon>
            <span>返回首页</span>
         </button>
         <!-- 顶部搜索栏 -->
         <div class="search-header">
            <div class="search-input-bar">
               <el-input
                  v-model="keyword"
                  placeholder="搜索..."
                  @keydown.enter.prevent="handleSearch"
               >
                  <template #suffix>
                     <el-icon class="search-icon" @click="handleSearch"><Search /></el-icon>
                  </template>
               </el-input>
               <button
                  class="ai-toggle-btn small"
                  :class="{ active: isAiMode }"
                  @click="isAiMode = !isAiMode"
               >
                  <el-icon><Cpu /></el-icon>
                  <span>{{ isAiMode ? 'AI 中' : 'AI' }}</span>
               </button>
            </div>
         </div>

         <!-- 搜索结果 -->
         <div v-if="viewMode === 'result'" class="result-container">
            <el-empty v-if="isEmpty" description="没有找到相关结果" />
            <div v-else class="result-list">
               <!-- 番剧 -->
               <template v-if="results.anime?.length">
                  <div class="result-section">
                     <h3 class="section-title">番剧</h3>
                     <div
                        v-for="item in results.anime"
                        :key="`anime-${item.id}`"
                        class="result-card"
                        @click="goToAnime(item.id)"
                     >
                        <div class="card-image">
                           <el-image :src="item.image" fit="cover">
                              <template #error>
                                 <div class="image-fallback">{{ item.chineseTitle?.charAt(0) || '番' }}</div>
                              </template>
                           </el-image>
                        </div>
                        <div class="card-info">
                           <div class="card-header">
                              <el-tag size="small" type="danger">番剧</el-tag>
                              <h4 class="card-title">{{ item.chineseTitle || item.title }}</h4>
                           </div>
                           <p class="card-desc">{{ item.description }}</p>
                        </div>
                     </div>
                  </div>
               </template>

               <!-- 文章 -->
               <template v-if="results.article?.length">
                  <div class="result-section">
                     <h3 class="section-title">文章</h3>
                     <div
                        v-for="item in results.article"
                        :key="`article-${item.id}`"
                        class="result-card"
                        @click="goToArticle(item.id)"
                     >
                        <div class="card-image">
                           <el-image :src="item.cover" fit="cover">
                              <template #error>
                                 <div class="image-fallback">文</div>
                              </template>
                           </el-image>
                        </div>
                        <div class="card-info">
                           <div class="card-header">
                              <el-tag size="small" type="success">文章</el-tag>
                              <h4 class="card-title">{{ item.title }}</h4>
                           </div>
                           <p class="card-desc">{{ item.summary }}</p>
                        </div>
                     </div>
                  </div>
               </template>

               <!-- 视频 -->
               <template v-if="results.video?.length">
                  <div class="result-section">
                     <h3 class="section-title">视频</h3>
                     <div
                        v-for="item in results.video"
                        :key="`video-${item.id}`"
                        class="result-card"
                        @click="goToVideo(item.id)"
                     >
                        <div class="card-image">
                           <el-image :src="item.thumbnail" fit="cover">
                              <template #error>
                                 <div class="image-fallback">视</div>
                              </template>
                           </el-image>
                        </div>
                        <div class="card-info">
                           <div class="card-header">
                              <el-tag size="small" type="warning">视频</el-tag>
                              <h4 class="card-title">{{ item.title }}</h4>
                           </div>
                           <p class="card-desc">{{ item.description }}</p>
                        </div>
                     </div>
                  </div>
               </template>

               <!-- 角色 -->
               <template v-if="results.character?.length">
                  <div class="result-section">
                     <h3 class="section-title">角色</h3>
                     <div
                        v-for="item in results.character"
                        :key="`character-${item.id}`"
                        class="result-card"
                     >
                        <div class="card-image round">
                           <el-image :src="item.image" fit="cover">
                              <template #error>
                                 <div class="image-fallback">{{ item.name?.charAt(0) || '角' }}</div>
                              </template>
                           </el-image>
                        </div>
                        <div class="card-info">
                           <div class="card-header">
                              <el-tag size="small" type="info">角色</el-tag>
                              <h4 class="card-title">{{ item.name }}</h4>
                           </div>
                           <p class="card-desc">{{ item.description }}</p>
                        </div>
                     </div>
                  </div>
               </template>
            </div>
         </div>

         <!-- AI 对话 -->
         <div v-if="viewMode === 'chat'" class="chat-container">
            <div class="chat-messages" ref="chatRef">
               <div
                  v-for="(msg, index) in chatMessages"
                  :key="index"
                  class="chat-message"
                  :class="msg.role"
               >
                  <div class="message-avatar">
                     <el-icon v-if="msg.role === 'user'"><User /></el-icon>
                     <el-icon v-else><Cpu /></el-icon>
                  </div>
                  <div class="message-content">
                     <div class="message-text" v-html="formatMessage(msg.content)"></div>
                  </div>
               </div>
               <div v-if="chatLoading" class="chat-message assistant">
                  <div class="message-avatar">
                     <el-icon><Cpu /></el-icon>
                  </div>
                  <div class="message-content">
                     <el-skeleton :rows="2" animated />
                  </div>
               </div>
            </div>
            <div class="chat-input-bar">
               <el-input
                  v-model="chatInput"
                  placeholder="继续提问..."
                  @keydown.enter.prevent="sendChatMessage"
               >
                  <template #suffix>
                     <el-icon
                        class="send-icon"
                        :class="{ disabled: !chatInput.trim() || chatLoading }"
                        @click="sendChatMessage"
                     >
                        <ArrowUp />
                     </el-icon>
                  </template>
               </el-input>
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, computed, nextTick, watch } from 'vue'
import { useRouter } from 'vue-router'
import { Cpu, ArrowUp, Search, User, ArrowLeft } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { searchApi, type SearchResult } from '@/api/modules/search'

const router = useRouter()

const keyword = ref('')
const isAiMode = ref(false)
const viewMode = ref<'home' | 'result' | 'chat'>('home')
const results = ref<SearchResult>({ anime: [], article: [], video: [], character: [] })
const loading = ref(false)

// AI 对话
const chatMessages = ref<{ role: 'user' | 'assistant'; content: string }[]>([])
const chatInput = ref('')
const chatLoading = ref(false)
const chatRef = ref<HTMLElement>()

const isEmpty = computed(() => {
   return !results.value.anime?.length &&
          !results.value.article?.length &&
          !results.value.video?.length &&
          !results.value.character?.length
})

const handleSearch = async () => {
   if (!keyword.value.trim()) return

   if (isAiMode.value) {
      // AI 模式：进入对话
      const question = keyword.value.trim()
      viewMode.value = 'chat'
      chatMessages.value.push({ role: 'user', content: question })
      keyword.value = ''
      await callDeepSeek()
   } else {
      // 普通搜索
      viewMode.value = 'result'
      loading.value = true
      try {
         const data = await searchApi.searchAll(keyword.value.trim())
         results.value = data
      } catch (error) {
         ElMessage.error('搜索失败')
      } finally {
         loading.value = false
      }
   }
}

const callDeepSeek = async () => {
   chatLoading.value = true
   try {
      const response = await fetch('https://api.deepseek.com/v1/chat/completions', {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer sk-78525a25cfc244269efa510c87807ec0'
         },
         body: JSON.stringify({
            model: 'deepseek-chat',
            messages: [
               { role: 'system', content: '你是一个 ACG 领域的专家助手，擅长回答关于中文 GnAcg 资料站 ACG 相关的问题。请用中文回答。' },
               ...chatMessages.value
            ]
         })
      })

      if (!response.ok) {
         throw new Error('AI 请求失败')
      }

      const data = await response.json()
      const reply = data.choices?.[0]?.message?.content || '抱歉，我没有理解您的问题。'
      chatMessages.value.push({ role: 'assistant', content: reply })
   } catch (error) {
      ElMessage.error('AI 对话出错，请重试')
      chatMessages.value.push({ role: 'assistant', content: '抱歉，服务暂时不可用，请稍后再试。' })
   } finally {
      chatLoading.value = false
      nextTick(() => {
         chatRef.value?.scrollTo({ top: chatRef.value.scrollHeight, behavior: 'smooth' })
      })
   }
}

const sendChatMessage = async () => {
   if (!chatInput.value.trim() || chatLoading.value) return
   chatMessages.value.push({ role: 'user', content: chatInput.value.trim() })
   chatInput.value = ''
   await callDeepSeek()
}

const formatMessage = (content: string) => {
   return content.replace(/\n/g, '<br>')
}

const goToAnime = (id: number) => router.push(`/anime/${id}`)
const goToArticle = (id: number) => router.push(`/articles/index/${id}`)
const goToVideo = (id: number) => router.push(`/video/${id}`)

const goHome = () => {
   router.push('/home')
}

// 监听 viewMode，返回主页时清空部分状态
watch(viewMode, (val) => {
   if (val === 'home') {
      results.value = { anime: [], article: [], video: [], character: [] }
      chatMessages.value = []
   }
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
