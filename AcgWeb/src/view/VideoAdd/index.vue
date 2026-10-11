<template>
   <div class="video-add-page">
      <div class="edit-container">
         <div class="edit-header">
            <h2>发布视频</h2>
            <div class="header-actions">
               <el-button @click="goBack">返回</el-button>
               <el-button type="primary" @click="saveVideo" :loading="saving">发布</el-button>
            </div>
         </div>

         <el-form :model="form" label-position="top" class="edit-form">
            <el-row :gutter="20">
               <el-col :span="16">
                  <el-form-item label="视频标题">
                     <el-input v-model="form.title" placeholder="请输入视频标题" />
                  </el-form-item>
               </el-col>
               <el-col :span="8">
                  <el-form-item label="缩略图">
                     <el-input v-model="form.thumbnail" placeholder="请输入缩略图URL" />
                  </el-form-item>
               </el-col>
            </el-row>

            <el-form-item label="视频描述">
               <el-input
                  v-model="form.description"
                  type="textarea"
                  :rows="4"
                  placeholder="请输入视频描述"
               />
            </el-form-item>

            <el-row :gutter="20">
               <el-col :span="12">
                  <el-form-item label="作者名称">
                     <el-input v-model="form.author" placeholder="请输入作者名称" />
                  </el-form-item>
               </el-col>
               <el-col :span="12">
                  <el-form-item label="作者头像">
                     <el-input v-model="form.authorAvatar" placeholder="请输入作者头像URL" />
                  </el-form-item>
               </el-col>
            </el-row>

            <el-row :gutter="20">
               <el-col :span="8">
                  <el-form-item label="作者角色">
                     <el-input v-model="form.authorRole" placeholder="如：创作者" />
                  </el-form-item>
               </el-col>
               <el-col :span="8">
                  <el-form-item label="作者等级">
                     <el-input-number v-model="form.authorLevel" :min="1" style="width: 100%" />
                  </el-form-item>
               </el-col>
               <el-col :span="8">
                  <el-form-item label="外部链接">
                     <el-input v-model="form.externalLink" placeholder="请输入视频链接" />
                  </el-form-item>
               </el-col>
            </el-row>

            <el-form-item label="作者简介">
               <el-input v-model="form.authorBio" placeholder="请输入作者简介" />
            </el-form-item>

            <el-form-item label="相关标签">
               <el-select
                  v-model="form.relatedTags"
                  multiple
                  filterable
                  allow-create
                  placeholder="请输入标签"
                  style="width: 100%"
               />
            </el-form-item>
         </el-form>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { videoApi, type VideoDetail } from '@/api/modules/video'

const router = useRouter()

const form = ref<Partial<VideoDetail>>({
   title: '',
   thumbnail: '',
   description: '',
   author: '',
   authorAvatar: '',
   authorRole: '',
   authorLevel: 1,
   authorBio: '',
   externalLink: '',
   relatedTags: []
})

const saving = ref(false)

const saveVideo = async () => {
   saving.value = true
   try {
      await videoApi.insert(form.value)
      ElMessage.success('发布成功')
      router.push('/home')
   } catch (error) {
      ElMessage.error('发布失败')
   } finally {
      saving.value = false
   }
}

const goBack = () => {
   router.push('/home')
}
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
