<template>
   <div class="share-page">
      <div class="container">
         <!-- 宣传横幅 -->
         <div class="banner-section">
            <div class="banner-content">
               <h2>留下你的Gal游玩感想！</h2>
               <p>到CnGal资料站留下对游玩作品的评语，<br>还可以参与抽奖？！</p>
               <el-button type="primary" size="large" @click="scrollToForm">立即参与</el-button>
            </div>
            <div class="banner-image">
               <img src="https://pic.imgdb.cn/item/65c5f0f99f345e8d0329379c.png" alt="看板娘" />
            </div>
         </div>

         <!-- 随机评价 -->
         <div class="section-card">
            <CardSlider title="随机评价" :scrollStep="420">
               <div 
                  v-for="item in ratingList" 
                  :key="item.id" 
                  class="rating-card"
                  @click="goToAnime(item.animeId)"
               >
                  <div class="rating-header">
                     <el-image class="user-avatar" :src="item.userAvatar" fit="cover">
                        <template #error>
                           <div class="avatar-fallback">{{ item.user.charAt(0) }}</div>
                        </template>
                     </el-image>
                     <div class="user-info">
                        <div class="username">{{ item.user }}</div>
                        <div class="user-bio">其实不是柚子厨</div>
                     </div>
                  </div>
                  <div class="rating-score">
                     <span>总评({{ item.totalRating }})</span>
                     <el-rate :model-value="item.rating / 2" disabled show-score />
                  </div>
                  <div class="rating-detail">
                     音乐{{ item.musicRating }}分,美术{{ item.artRating }}分,剧本{{ item.scriptRating }}分,演出{{ item.performanceRating }}分,程序{{ item.programRating }}分,配音{{ item.voiceRating }}分
                  </div>
                  <p class="rating-comment">{{ item.comment }}</p>
                  <div class="rating-anime">《{{ item.animeChineseTitle }}》</div>
                  <div class="rating-date">{{ formatDate(item.date) }}</div>
               </div>
            </CardSlider>
         </div>

         <!-- 发表评价表单 -->
         <div ref="formRef" class="section-card">
            <div class="section-header">
               <div class="header-left">
                  <el-icon><Edit /></el-icon>
                  <span>发表评价</span>
               </div>
            </div>
            <el-form :model="form" label-position="top" class="share-form">
               <el-form-item label="选择作品">
                  <el-select 
                     v-model="form.animeId" 
                     placeholder="请选择作品"
                     filterable
                     style="width: 100%"
                  >
                     <el-option 
                        v-for="item in animeList" 
                        :key="item.id" 
                        :label="item.chineseTitle" 
                        :value="item.id" 
                     />
                  </el-select>
               </el-form-item>
               
               <el-form-item label="总体评分">
                  <el-rate v-model="form.rating" :max="10" show-score />
               </el-form-item>
               
               <el-row :gutter="20">
                  <el-col :span="8">
                     <el-form-item label="音乐">
                        <el-rate v-model="form.musicRating" :max="10" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="美术">
                        <el-rate v-model="form.artRating" :max="10" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="剧本">
                        <el-rate v-model="form.scriptRating" :max="10" />
                     </el-form-item>
                  </el-col>
               </el-row>
               
               <el-row :gutter="20">
                  <el-col :span="8">
                     <el-form-item label="演出">
                        <el-rate v-model="form.performanceRating" :max="10" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="程序">
                        <el-rate v-model="form.programRating" :max="10" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="配音">
                        <el-rate v-model="form.voiceRating" :max="10" />
                     </el-form-item>
                  </el-col>
               </el-row>
               
               <el-form-item label="评价内容">
                  <el-input 
                     v-model="form.comment" 
                     type="textarea" 
                     :rows="6"
                     placeholder="分享你的游玩感想..."
                  />
               </el-form-item>
               
               <el-form-item>
                  <el-button type="primary" size="large" @click="submitRating">
                     提交评价
                  </el-button>
               </el-form-item>
            </el-form>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ChatDotRound, Edit } from '@element-plus/icons-vue'
import CardSlider from '@/components/ContentCardSlider/index.vue'
import { ratingApi, type RatingItem } from '@/api/modules/rating'
import { animeApi } from '@/api/modules/anime'
import { ElMessage } from 'element-plus'

const router = useRouter()

const ratingList = ref<RatingItem[]>([])
const animeList = ref<any[]>([])
const formRef = ref<HTMLElement>()

const form = ref({
   animeId: null as number | null,
   rating: 8,
   musicRating: 8,
   artRating: 8,
   scriptRating: 8,
   performanceRating: 8,
   programRating: 8,
   voiceRating: 8,
   comment: ''
})

const formatDate = (dateStr: string) => {
   const date = new Date(dateStr)
   return date.toLocaleDateString()
}

const fetchRatings = async () => {
   try {
      const data = await ratingApi.getRandom()
      ratingList.value = data
   } catch (error) {
      console.error('获取评价失败:', error)
   }
}

const fetchAnimeList = async () => {
   try {
      const data = await animeApi.getLatest()
      animeList.value = data
   } catch (error) {
      console.error('获取作品列表失败:', error)
   }
}

const goToAnime = (id: number) => {
   router.push(`/anime/${id}`)
}

const scrollToForm = () => {
   formRef.value?.scrollIntoView({ behavior: 'smooth' })
}

const submitRating = async () => {
   if (!form.value.animeId) {
      ElMessage.warning('请选择作品')
      return
   }
   if (!form.value.comment.trim()) {
      ElMessage.warning('请输入评价内容')
      return
   }
   
   try {
      // 这里调用提交评价的 API
      ElMessage.success('评价提交成功！')
      form.value.comment = ''
      fetchRatings()
   } catch (error) {
      ElMessage.error('提交失败')
   }
}

onMounted(() => {
   fetchRatings()
   fetchAnimeList()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>