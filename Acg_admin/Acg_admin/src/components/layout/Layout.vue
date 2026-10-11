<template>
  <div class="layout-container">
    <!-- 侧边栏 -->
    <el-aside width="200px" class="sidebar">
      <div class="logo">
        <h2>ACG Admin</h2>
      </div>
      <el-menu
        :default-active="activeMenu"
        class="sidebar-menu"
        @select="handleMenuSelect"
      >
        <el-menu-item index="/">
          <el-icon><House /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-sub-menu index="anime">
          <template #title>
            <el-icon><VideoCamera /></el-icon>
            <span>番剧管理</span>
          </template>
          <el-menu-item index="/anime/list">番剧列表</el-menu-item>
          <el-menu-item index="/anime/add">新增番剧</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="article">
          <template #title>
            <el-icon><Document /></el-icon>
            <span>文章管理</span>
          </template>
          <el-menu-item index="/article/list">文章列表</el-menu-item>
          <el-menu-item index="/article/add">新增文章</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="video">
          <template #title>
            <el-icon><VideoPlay /></el-icon>
            <span>视频管理</span>
          </template>
          <el-menu-item index="/video/list">视频列表</el-menu-item>
          <el-menu-item index="/video/add">新增视频</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="user">
          <template #title>
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </template>
          <el-menu-item index="/user/list">用户列表</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>
    
    <!-- 主内容区 -->
    <el-container class="main-content">
      <!-- 顶部导航 -->
      <el-header class="header">
        <div class="header-left">
          <el-button link @click="toggleSidebar" class="menu-button">
            <el-icon><Menu /></el-icon>
          </el-button>
        </div>
        <div class="header-right">
          <el-dropdown>
            <span class="user-info">
              <el-avatar :size="32" :src="user?.avatar || ''">{{ user?.username?.charAt(0) || 'A' }}</el-avatar>
              <span class="username">{{ user?.username || 'Admin' }}</span>
              <el-icon class="el-icon--right"><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>
      
      <!-- 内容区域 -->
      <el-main class="content">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { ElMessage } from 'element-plus';
import { House, VideoCamera, Document, VideoPlay, User, Menu, ArrowDown } from '@element-plus/icons-vue';

const router = useRouter();
const route = useRoute();
const isSidebarCollapsed = ref(false);
const user = ref<any>(null);

const activeMenu = computed(() => {
  // 根据当前路由路径返回对应的菜单index
  const path = route.path;
  if (path === '/') return '/';
  if (path.startsWith('/anime')) return path;
  if (path.startsWith('/article')) return path;
  if (path.startsWith('/video')) return path;
  if (path.startsWith('/user')) return path;
  return path;
});

const toggleSidebar = () => {
  isSidebarCollapsed.value = !isSidebarCollapsed.value;
};

const handleMenuSelect = (key: string) => {
  router.push(key);
};

const handleLogout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('user');
  ElMessage.success('退出登录成功');
  router.push('/login');
};

onMounted(() => {
  const userStr = localStorage.getItem('user');
  if (userStr) {
    user.value = JSON.parse(userStr);
  }
});
</script>

<style scoped>
.layout-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

.sidebar {
  background-color: #2c3e50;
  color: white;
  transition: width 0.3s ease;
}

.logo {
  padding: 20px;
  text-align: center;
  border-bottom: 1px solid #34495e;
}

.logo h2 {
  margin: 0;
  font-size: 18px;
  font-weight: bold;
}

.sidebar-menu {
  height: calc(100vh - 70px);
  border-right: none;
}

.sidebar-menu .el-menu-item,
.sidebar-menu .el-sub-menu__title {
  color: white;
  height: 60px;
  line-height: 60px;
}

.sidebar-menu .el-menu-item:hover,
.sidebar-menu .el-sub-menu__title:hover {
  background-color: #34495e;
}

.sidebar-menu .el-menu-item.is-active {
  background-color: #1890ff;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.header {
  height: 60px;
  background-color: white;
  border-bottom: 1px solid #e0e0e0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.menu-button {
  margin-right: 20px;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.username {
  margin: 0 10px;
  font-size: 14px;
}

.content {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background-color: #f5f7fa;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .sidebar {
    width: 60px !important;
  }
  
  .logo h2 {
    display: none;
  }
  
  .sidebar-menu .el-menu-item span,
  .sidebar-menu .el-sub-menu__title span {
    display: none;
  }
  
  .sidebar-menu .el-menu-item,
  .sidebar-menu .el-sub-menu__title {
    justify-content: center;
  }
}
</style>