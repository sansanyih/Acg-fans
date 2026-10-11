<template>
   <CardSlider title="最新文章" :scrollStep="320">
      <!-- 文章卡片 -->
      <div 
         v-for="item in articleList" 
         :key="item.id" 
         class="article-card" 
         @click="goToDetail(item.id)"
      >
         <div class="article-cover">
            <el-image :src="item.cover" :alt="item.title" fit="cover">
               <template #error>
                  <div class="cover-placeholder">文章</div>
               </template>
            </el-image>
         </div>
         <div class="article-info">
            <h3 class="article-title">{{ item.title }}</h3>
            <p class="article-summary">{{ item.summary }}</p>
            <div class="article-meta">
               <div class="author">
                  <el-image class="avatar" :src="item.authorAvatar" fit="cover">
                     <template #error>
                        <div class="avatar-placeholder">{{ item.author.charAt(0) }}</div>
                     </template>
                  </el-image>
                  <span class="author-name">{{ item.author }}</span>
               </div>
               <span class="publish-date">{{ formatDate(item.publishDate) }}</span>
            </div>
         </div>
      </div>
   </CardSlider>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import CardSlider from '@/components/ContentCardSlider/index.vue'
import { articleApi, type ArticleItem } from '@/api/modules/article'

const router = useRouter()
const articleList = ref<ArticleItem[]>([])

const fetchArticles = async () => {
   try {
      const data = await articleApi.getAll()
      articleList.value = data
   } catch (error) {
      console.error('获取文章失败:', error)
   }
}

const formatDate = (dateStr: string) => {
   const date = new Date(dateStr)
   const now = new Date()
   const diff = now.getTime() - date.getTime()
   const days = Math.floor(diff / (1000 * 60 * 60 * 24))
   
   if (days === 0) return '今天'
   if (days === 1) return '昨天'
   if (days < 30) return `${days}天前`
   if (days < 365) return `${Math.floor(days / 30)}个月前`
   return `${Math.floor(days / 365)}年前`
}

const goToDetail = (id: number) => {
   router.push(`/articles/index/${id}`)
}

onMounted(() => {
   fetchArticles()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>