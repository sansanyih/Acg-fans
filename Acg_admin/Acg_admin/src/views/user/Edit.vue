<template>
  <div class="user-edit">
      <el-card class="page-card">
        <template #header>
          <div class="card-header">
            <span>编辑用户</span>
            <el-button @click="navigateToList">返回列表</el-button>
          </div>
        </template>
        <el-form :model="userForm" :rules="userRules" ref="userFormRef" label-width="120px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="userForm.username" placeholder="请输入用户名" />
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="userForm.email" placeholder="请输入邮箱" type="email" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="userForm.password" placeholder="请输入密码（留空则不修改）" type="password" />
          </el-form-item>
          <el-form-item label="头像" prop="avatar">
            <el-input v-model="userForm.avatar" placeholder="请输入头像URL" />
          </el-form-item>
          <el-form-item label="个人简介" prop="bio">
            <el-input v-model="userForm.bio" placeholder="请输入个人简介" type="textarea" />
          </el-form-item>
          <el-form-item label="等级" prop="level">
            <el-input v-model.number="userForm.level" placeholder="请输入等级" type="number" />
          </el-form-item>
          <el-form-item label="是否管理员" prop="isAdmin">
            <el-switch v-model="userForm.isAdmin" />
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
import { ref, reactive, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ElMessage } from 'element-plus';
import * as userApi from '../../api/user';

const router = useRouter();
const route = useRoute();
const userFormRef = ref();
const userId = ref(Number(route.params.id));

const userForm = reactive({
  username: '',
  email: '',
  password: '',
  avatar: '',
  bio: '',
  level: 0,
  isAdmin: false
});

const userRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入有效的邮箱地址', trigger: 'blur' }
  ],
  level: [
    { required: true, message: '请输入等级', trigger: 'blur' },
    { type: 'number', min: 1, max: 9, message: '等级必须在1-9之间', trigger: 'blur' }
  ]
};

const navigateToList = () => {
  router.push('/user/list');
};

const fetchUserDetail = async () => {
  try {
    const data: any = await userApi.getUserById(userId.value);
    // 不显示密码
    delete data.password;
    Object.assign(userForm, data);
  } catch (error) {
    ElMessage.error('获取用户详情失败');
    console.error(error);
  }
};

const handleSubmit = async () => {
  if (!userFormRef.value) return;
  
  await userFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        // 实际项目中会调用API更新用户信息
        ElMessage.success('更新用户成功');
        router.push('/user/list');
      } catch (error) {
        ElMessage.error('更新用户失败，请重试');
      }
    }
  });
};

const resetForm = () => {
  if (userFormRef.value) {
    userFormRef.value.resetFields();
  }
};

onMounted(() => {
  fetchUserDetail();
});
</script>

<style scoped>
.user-edit {
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