<template>
  <div class="video-add">
      <el-card class="page-card">
        <template #header>
          <div class="card-header">
            <span>新增视频</span>
            <el-button @click="navigateToList">返回列表</el-button>
          </div>
        </template>
        <el-form :model="videoForm" :rules="videoRules" ref="videoFormRef" label-width="120px">
          <el-form-item label="缩略图" prop="thumbnail">
            <el-input v-model="videoForm.thumbnail" placeholder="请输入缩略图URL" />
          </el-form-item>
          <el-form-item label="标题" prop="title">
            <el-input v-model="videoForm.title" placeholder="请输入标题" />
          </el-form-item>
          <el-form-item label="作者" prop="author">
            <el-input v-model="videoForm.author" placeholder="请输入作者" />
          </el-form-item>
          <el-form-item label="作者头像" prop="authorAvatar">
            <el-input v-model="videoForm.authorAvatar" placeholder="请输入作者头像URL" />
          </el-form-item>
          <el-form-item label="作者角色" prop="authorRole">
            <el-input v-model="videoForm.authorRole" placeholder="请输入作者角色" />
          </el-form-item>
          <el-form-item label="作者等级" prop="authorLevel">
            <el-input v-model.number="videoForm.authorLevel" placeholder="请输入作者等级" type="number" />
          </el-form-item>
          <el-form-item label="作者简介" prop="authorBio">
            <el-input v-model="videoForm.authorBio" placeholder="请输入作者简介" type="textarea" />
          </el-form-item>
          <el-form-item label="发布日期" prop="publishDate">
            <el-date-picker v-model="videoForm.publishDate" type="date" placeholder="选择发布日期" style="width: 100%;" />
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input v-model="videoForm.description" placeholder="请输入描述" type="textarea" />
          </el-form-item>
          <el-form-item label="外部链接" prop="externalLink">
            <el-input v-model="videoForm.externalLink" placeholder="请输入外部链接" />
          </el-form-item>
          <el-form-item label="相关标签" prop="relatedTags">
            <el-input v-model="videoForm.relatedTags" placeholder="请输入相关标签，用逗号分隔" />
          </el-form-item>
          <el-form-item label="相关番剧" prop="relatedAnime">
            <el-input v-model="videoForm.relatedAnime" placeholder="请输入相关番剧" />
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
import * as videoApi from '../../api/video';

const router = useRouter();
const videoFormRef = ref();

const videoForm = reactive({
  thumbnail: '',
  title: '',
  author: '',
  authorAvatar: '',
  authorRole: '',
  authorLevel: 0,
  authorBio: '',
  publishDate: '',
  description: '',
  externalLink: '',
  relatedTags: '',
  relatedAnime: ''
});

const videoRules = {
  title: [
    { required: true, message: '请输入标题', trigger: 'blur' }
  ],
  author: [
    { required: true, message: '请输入作者', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请输入描述', trigger: 'blur' }
  ],
  publishDate: [
    { required: true, message: '请选择发布日期', trigger: 'blur' }
  ]
};

const navigateToList = () => {
  router.push('/video/list');
};

const handleSubmit = async () => {
  if (!videoFormRef.value) return;
  
  await videoFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        // 处理相关标签，将字符串转换为数组
        const submitData = {
          ...videoForm,
          relatedTags: videoForm.relatedTags ? videoForm.relatedTags.split(',').map(tag => tag.trim()) : []
        };
        
        await videoApi.addVideo(submitData);
        ElMessage.success('新增视频成功');
        router.push('/video/list');
      } catch (error) {
        ElMessage.error('新增视频失败，请重试');
      }
    }
  });
};

const resetForm = () => {
  if (videoFormRef.value) {
    videoFormRef.value.resetFields();
  }
};
</script>

<style scoped>
.video-add {
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