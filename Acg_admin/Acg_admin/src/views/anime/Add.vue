<template>
  <div class="anime-add">
      <el-card class="page-card">
        <template #header>
          <div class="card-header">
            <span>新增番剧</span>
            <el-button @click="navigateToList">返回列表</el-button>
          </div>
        </template>
        <el-form :model="animeForm" :rules="animeRules" ref="animeFormRef" label-width="120px">
          <el-form-item label="日文标题" prop="title">
            <el-input v-model="animeForm.title" placeholder="请输入日文标题" />
          </el-form-item>
          <el-form-item label="中文标题" prop="chineseTitle">
            <el-input v-model="animeForm.chineseTitle" placeholder="请输入中文标题" />
          </el-form-item>
          <el-form-item label="图片URL" prop="image">
            <el-input v-model="animeForm.image" placeholder="请输入图片URL" />
          </el-form-item>
          <el-form-item label="简短描述" prop="description">
            <el-input v-model="animeForm.description" placeholder="请输入简短描述" type="textarea" />
          </el-form-item>
          <el-form-item label="详细介绍" prop="introduction">
            <el-input v-model="animeForm.introduction" placeholder="请输入详细介绍" type="textarea" rows="4" />
          </el-form-item>
          <el-form-item label="制作公司" prop="studio">
            <el-input v-model="animeForm.studio" placeholder="请输入制作公司" />
          </el-form-item>
          <el-form-item label="标签列表" prop="tags">
            <el-input v-model="animeForm.tags" placeholder="请输入标签，用逗号分隔" />
          </el-form-item>
          <el-form-item label="集数" prop="episodes">
            <el-input v-model.number="animeForm.episodes" placeholder="请输入集数" type="number" />
          </el-form-item>
          <el-form-item label="开始日期" prop="startDate">
            <el-date-picker v-model="animeForm.startDate" type="date" placeholder="选择开始日期" style="width: 100%;" />
          </el-form-item>
          <el-form-item label="播出日" prop="broadcastDay">
            <el-select v-model="animeForm.broadcastDay" placeholder="选择播出日">
              <el-option label="周一" value="周一" />
              <el-option label="周二" value="周二" />
              <el-option label="周三" value="周三" />
              <el-option label="周四" value="周四" />
              <el-option label="周五" value="周五" />
              <el-option label="周六" value="周六" />
              <el-option label="周日" value="周日" />
            </el-select>
          </el-form-item>
          <el-form-item label="原作" prop="originalWork">
            <el-input v-model="animeForm.originalWork" placeholder="请输入原作" />
          </el-form-item>
          <el-form-item label="导演" prop="director">
            <el-input v-model="animeForm.director" placeholder="请输入导演" />
          </el-form-item>
          <el-form-item label="官方网站" prop="officialWebsite">
            <el-input v-model="animeForm.officialWebsite" placeholder="请输入官方网站" />
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
import * as animeApi from '../../api/anime';

const router = useRouter();
const animeFormRef = ref();

const animeForm = reactive({
  title: '',
  chineseTitle: '',
  image: '',
  description: '',
  introduction: '',
  studio: '',
  tags: '',
  episodes: 0,
  startDate: '',
  broadcastDay: '',
  originalWork: '',
  director: '',
  officialWebsite: ''
});

const animeRules = {
  title: [
    { required: true, message: '请输入日文标题', trigger: 'blur' }
  ],
  chineseTitle: [
    { required: true, message: '请输入中文标题', trigger: 'blur' }
  ],
  image: [
    { required: true, message: '请输入图片URL', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请输入简短描述', trigger: 'blur' }
  ],
  studio: [
    { required: true, message: '请输入制作公司', trigger: 'blur' }
  ],
  episodes: [
    { required: true, message: '请输入集数', trigger: 'blur' },
    { type: 'number', min: 1, message: '集数必须大于0', trigger: 'blur' }
  ],
  startDate: [
    { required: true, message: '请选择开始日期', trigger: 'blur' }
  ]
};

const navigateToList = () => {
  router.push('/anime/list');
};

const handleSubmit = async () => {
  if (!animeFormRef.value) return;
  
  await animeFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        // 处理标签列表，将字符串转换为数组
        const submitData = {
          ...animeForm,
          tags: animeForm.tags ? animeForm.tags.split(',').map(tag => tag.trim()) : []
        };
        
        await animeApi.addAnime(submitData);
        ElMessage.success('新增番剧成功');
        router.push('/anime/list');
      } catch (error) {
        ElMessage.error('新增番剧失败，请重试');
      }
    }
  });
};

const resetForm = () => {
  if (animeFormRef.value) {
    animeFormRef.value.resetFields();
  }
};
</script>

<style scoped>
.anime-add {
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