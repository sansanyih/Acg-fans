<template>
   <div class="anime-edit-page">
      <div class="edit-container">
         <div class="edit-header">
            <h2>编辑番剧信息</h2>
            <div class="header-actions">
               <el-button @click="goBack">返回</el-button>
               <el-button type="primary" @click="saveAnime" :loading="saving">保存</el-button>
            </div>
         </div>

         <el-form :model="form" label-position="top" class="edit-form">
            <div class="form-section">
               <h3>基本信息</h3>
               <el-row :gutter="20">
                  <el-col :span="12">
                     <el-form-item label="日文标题">
                        <el-input v-model="form.title" placeholder="请输入日文标题" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="12">
                     <el-form-item label="中文标题">
                        <el-input v-model="form.chineseTitle" placeholder="请输入中文标题" />
                     </el-form-item>
                  </el-col>
               </el-row>

               <el-row :gutter="20">
                  <el-col :span="12">
                     <el-form-item label="封面图片">
                        <el-input v-model="form.image" placeholder="请输入图片URL" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="12">
                     <el-form-item label="制作公司">
                        <el-input v-model="form.studio" placeholder="请输入制作公司" />
                     </el-form-item>
                  </el-col>
               </el-row>

               <el-form-item label="标签">
                  <el-select
                     v-model="form.tags"
                     multiple
                     filterable
                     allow-create
                     placeholder="请输入标签"
                     style="width: 100%"
                  />
               </el-form-item>

               <el-row :gutter="20">
                  <el-col :span="8">
                     <el-form-item label="集数">
                        <el-input-number v-model="form.episodes" :min="1" style="width: 100%" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="开播日期">
                        <el-date-picker
                           v-model="form.startDate"
                           type="date"
                           placeholder="选择日期"
                           style="width: 100%"
                           value-format="YYYY-MM-DD"
                        />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="更新时间">
                        <el-input v-model="form.broadcastDay" placeholder="如：星期四" />
                     </el-form-item>
                  </el-col>
               </el-row>
            </div>

            <div class="form-section">
               <h3>制作人员</h3>
               <el-row :gutter="20">
                  <el-col :span="8">
                     <el-form-item label="原作">
                        <el-input v-model="form.originalWork" placeholder="请输入原作" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="监督">
                        <el-input v-model="form.director" placeholder="请输入监督" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="系列构成">
                        <el-input v-model="form.seriesComposition" placeholder="请输入系列构成" />
                     </el-form-item>
                  </el-col>
               </el-row>

               <el-row :gutter="20">
                  <el-col :span="8">
                     <el-form-item label="人物设计">
                        <el-input v-model="form.characterDesign" placeholder="请输入人物设计" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="音乐">
                        <el-input v-model="form.music" placeholder="请输入音乐" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="脚本">
                        <el-input v-model="form.script" placeholder="请输入脚本" />
                     </el-form-item>
                  </el-col>
               </el-row>

               <el-row :gutter="20">
                  <el-col :span="8">
                     <el-form-item label="分镜">
                        <el-input v-model="form.storyboard" placeholder="请输入分镜" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="演出">
                        <el-input v-model="form.performance" placeholder="请输入演出" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="8">
                     <el-form-item label="美术监督">
                        <el-input v-model="form.artDirector" placeholder="请输入美术监督" />
                     </el-form-item>
                  </el-col>
               </el-row>

               <el-row :gutter="20">
                  <el-col :span="12">
                     <el-form-item label="色彩设计">
                        <el-input v-model="form.colorDesign" placeholder="请输入色彩设计" />
                     </el-form-item>
                  </el-col>
                  <el-col :span="12">
                     <el-form-item label="总作画监督">
                        <el-input v-model="form.chiefAnimationDirector" placeholder="请输入总作画监督" />
                     </el-form-item>
                  </el-col>
               </el-row>
            </div>

            <div class="form-section">
               <h3>详细介绍</h3>
               <el-form-item label="简介">
                  <el-input
                     v-model="form.description"
                     type="textarea"
                     :rows="2"
                     placeholder="请输入简短描述"
                  />
               </el-form-item>
               <el-form-item label="详细介绍">
                  <el-input
                     v-model="form.introduction"
                     type="textarea"
                     :rows="6"
                     placeholder="请输入详细介绍"
                  />
               </el-form-item>
            </div>

            <div class="form-section">
               <h3>其他信息</h3>
               <el-form-item label="官方网站">
                  <el-input v-model="form.officialWebsite" placeholder="请输入官网链接" />
               </el-form-item>
            </div>
         </el-form>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { animeApi, type AnimeDetail } from '@/api/modules/anime'

const route = useRoute()
const router = useRouter()

const form = ref<Partial<AnimeDetail>>({
   title: '',
   chineseTitle: '',
   image: '',
   description: '',
   introduction: '',
   studio: '',
   tags: [],
   episodes: 12,
   startDate: '',
   broadcastDay: '',
   originalWork: '',
   director: '',
   script: '',
   storyboard: '',
   performance: '',
   music: '',
   characterDesign: '',
   seriesComposition: '',
   artDirector: '',
   colorDesign: '',
   chiefAnimationDirector: '',
   officialWebsite: ''
})

const saving = ref(false)

const fetchAnime = async () => {
   const id = Number(route.params.id)
   if (!id) return
   
   try {
      const data = await animeApi.getById(id)
      form.value = { ...data }
   } catch (error) {
      ElMessage.error('获取动画信息失败')
   }
}

const saveAnime = async () => {
   const id = Number(route.params.id)
   if (!id) return

   saving.value = true
   try {
      await animeApi.update(id, form.value)
      ElMessage.success('保存成功')
      router.push(`/anime/${id}`)
   } catch (error) {
      ElMessage.error('保存失败')
   } finally {
      saving.value = false
   }
}

const goBack = () => {
   const id = route.params.id
   router.push(`/anime/${id}`)
}

onMounted(() => {
   fetchAnime()
})
</script>

<style scoped lang="scss">
@import'./index.scss'
</style>