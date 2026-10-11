<template>
   <div class="login-page">
      <!-- 返回首页按钮 -->
      <button class="back-btn" @click="goHome">
         <el-icon><ArrowLeft /></el-icon>
         <span>返回首页</span>
      </button>
      
      <div class="login-container">
         <h1 class="login-title">{{ isRegister ? '用户注册' : '欢迎登录' }}</h1>
         
         <el-form 
            ref="formRef"
            :model="form"
            :rules="rules"
            class="login-form"
         >
            <!-- 表单内容不变... -->
            <el-form-item prop="username">
               <el-input 
                  v-model="form.username"
                  placeholder="用户名"
                  size="large"
               >
                  <template #prefix>
                     <el-icon><User /></el-icon>
                  </template>
               </el-input>
            </el-form-item>
            
            <el-form-item prop="password">
               <el-input 
                  v-model="form.password"
                  type="password"
                  placeholder="密码"
                  size="large"
                  show-password
               >
                  <template #prefix>
                     <el-icon><Lock /></el-icon>
                  </template>
               </el-input>
            </el-form-item>
            
            <template v-if="isRegister">
               <el-form-item prop="confirmPassword">
                  <el-input 
                     v-model="form.confirmPassword"
                     type="password"
                     placeholder="确认密码"
                     size="large"
                     show-password
                  >
                     <template #prefix>
                        <el-icon><Lock /></el-icon>
                     </template>
                  </el-input>
               </el-form-item>
               
               <el-form-item prop="email">
                  <el-input 
                     v-model="form.email"
                     placeholder="邮箱"
                     size="large"
                  >
                     <template #prefix>
                        <el-icon><Message /></el-icon>
                     </template>
                  </el-input>
               </el-form-item>
            </template>
            
            <el-form-item>
               <el-button 
                  type="primary" 
                  size="large"
                  class="login-btn"
                  :loading="loading"
                  @click="handleSubmit"
               >
                  {{ isRegister ? '注册' : '登录' }}
               </el-button>
            </el-form-item>
         </el-form>
         
         <div class="switch-mode">
            {{ isRegister ? '已有账号？' : '还没有账号？' }}
            <a href="#" @click.prevent="toggleMode">
               {{ isRegister ? '立即登录' : '立即注册' }}
            </a>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ArrowLeft, User, Lock, Message } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { userApi } from '@/api/modules/user'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const formRef = ref()
const loading = ref(false)
const isRegister = ref(false)

const form = reactive({
   username: '',
   password: '',
   confirmPassword: '',
   email: ''
})

const rules = {
   username: [
      { required: true, message: '请输入用户名', trigger: 'blur' },
      { min: 3, message: '用户名至少3位', trigger: 'blur' }
   ],
   password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, message: '密码至少6位', trigger: 'blur' }
   ],
   confirmPassword: [
      { 
         validator: (rule: any, value: string, callback: Function) => {
            if (isRegister.value && value !== form.password) {
               callback(new Error('两次输入的密码不一致'))
            } else {
               callback()
            }
         },
         trigger: 'blur'
      }
   ],
   email: [
      { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
   ]
}

const toggleMode = () => {
   isRegister.value = !isRegister.value
   formRef.value?.resetFields()
}

// 返回首页
const goHome = () => {
   router.push('/home')
}

const handleSubmit = async () => {
   await formRef.value.validate()
   
   loading.value = true
   try {
      if (isRegister.value) {
         const res = await userApi.register({
            username: form.username,
            password: form.password,
            email: form.email
         })
         
         if (res.success) {
            ElMessage.success('注册成功，请登录')
            isRegister.value = false
            form.password = ''
            form.confirmPassword = ''
         } else {
            ElMessage.error(res.message || '注册失败')
         }
      } else {
         const res = await userApi.login({
            username: form.username,
            password: form.password
         })
         
         if (res.success) {
            ElMessage.success('登录成功')
            userStore.login('user_token_' + res.user.id, res.user)
            
            const redirect = route.query.redirect as string
            router.push(redirect || '/home')
         } else {
            ElMessage.error(res.message || '登录失败')
         }
      }
   } catch (error: any) {
      ElMessage.error(error?.message || '操作失败，请重试')
   } finally {
      loading.value = false
   }
}
</script>

<style scoped lang="scss">
@import './index.scss';
</style>