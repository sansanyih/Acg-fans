<template>
  <div class="article-add">
      <el-card class="page-card">
        <template #header>
          <div class="card-header">
            <span>新增文章</span>
            <el-button @click="navigateToList">返回列表</el-button>
          </div>
        </template>
        <el-form :model="articleForm" :rules="articleRules" ref="articleFormRef" label-width="120px">
          <el-form-item label="封面图片" prop="cover">
            <el-input v-model="articleForm.cover" placeholder="请输入封面图片URL" />
          </el-form-item>
          <el-form-item label="标题" prop="title">
            <el-input v-model="articleForm.title" placeholder="请输入标题" />
          </el-form-item>
          <el-form-item label="作者" prop="author">
            <el-input v-model="articleForm.author" placeholder="请输入作者" />
          </el-form-item>
          <el-form-item label="摘要" prop="summary">
            <el-input v-model="articleForm.summary" placeholder="请输入摘要" type="textarea" />
          </el-form-item>
          <el-form-item label="内容" prop="content">
            <el-input v-model="articleForm.content" placeholder="请输入内容" type="textarea" rows="10" />
          </el-form-item>
          <el-form-item label="发布日期" prop="publishDate">
            <el-date-picker v-model="articleForm.publishDate" type="date" placeholder="选择发布日期" style="width: 100%;" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSubmit">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import * as articleApi from '../../api/article';

const router = useRouter();
const articleFormRef = ref();

const articleForm = reactive({
  cover: '',
  title: '',
  author: '',
  summary: '',
  content: '',
  publishDate: ''
});

const articleRules = {
  title: [
    { required: true, message: '请输入标题', trigger: 'blur' }
  ],
  author: [
    { required: true, message: '请输入作者', trigger: 'blur' }
  ],
  summary: [
    { required: true, message: '请输入摘要', trigger: 'blur' }
  ],
  content: [
    { required: true, message: '请输入内容', trigger: 'blur' }
  ],
  publishDate: [
    { required: true, message: '请选择发布日期', trigger: 'blur' }
  ]
};

const navigateToList = () => {
  router.push('/article/list');
};

const handleSubmit = async () => {
  if (!articleFormRef.value) return;
  
  await articleFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        await articleApi.addArticle(articleForm);
        ElMessage.success('新增文章成功');
        router.push('/article/list');
      } catch (error) {
        ElMessage.error('新增文章失败，请重试');
      }
    }
  });
};

const resetForm = () => {
  if (articleFormRef.value) {
    articleFormRef.value.resetFields();
  }
};
</script>

<style scoped>
.article-add {
  width: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-form {
  margin-top: 20px;
}

.el-form-item {
  margin-bottom: 20px;
}
</style>