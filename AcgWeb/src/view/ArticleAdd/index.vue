<template>
   <div class="article-add-page">
      <div class="edit-container">
         <div class="edit-header">
            <h2>发表文章</h2>
            <div class="header-actions">
               <el-button @click="goBack">返回</el-button>
               <el-button type="primary" @click="saveArticle" :loading="saving">发布</el-button>
            </div>
         </div>

         <el-form :model="form" label-position="top" class="edit-form">
            <el-row :gutter="20">
               <el-col :span="16">
                  <el-form-item label="文章标题">
                     <el-input v-model="form.title" placeholder="请输入文章标题" />
                  </el-form-item>
               </el-col>
               <el-col :span="8">
                  <el-form-item label="封面图片">
                     <el-input v-model="form.cover" placeholder="请输入封面URL" />
                  </el-form-item>
               </el-col>
            </el-row>

            <el-form-item label="文章摘要">
               <el-input
                  v-model="form.summary"
                  type="textarea"
                  :rows="2"
                  placeholder="请输入文章摘要"
               />
            </el-form-item>

            <el-form-item label="文章内容">
               <el-input
                  v-model="form.content"
                  type="textarea"
                  :rows="12"
                  placeholder="请输入文章内容"
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
         </el-form>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { articleApi, type ArticleDetail } from '@/api/modules/article'

const router = useRouter()

const form = ref<Partial<ArticleDetail>>({
   title: '',
   content: '',
   summary: '',
   cover: '',
   author: '',
   authorAvatar: ''
})

const saving = ref(false)

const saveArticle = async () => {
   saving.value = true
   try {
      await articleApi.insert(form.value)
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
