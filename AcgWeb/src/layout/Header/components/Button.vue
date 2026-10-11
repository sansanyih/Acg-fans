<template>
   <div class="header-actions flex-center">
      <button class="action-button" @click="goToSearch">
         <Search class="action-icon" />
      </button>
      <button class="action-button flex-center">
         <Sunny class="action-icon" />
      </button>
      
      <!-- 加号菜单（仅登录后显示） -->
      <el-dropdown
         v-if="userStore.isLoggedIn"
         trigger="click"
         placement="bottom-end"
         popper-class="create-dropdown"
      >
         <button class="action-button flex-center">
            <Plus class="action-icon" />
         </button>
         <template #dropdown>
            <div class="create-menu">
               <div class="create-item" @click="goToAnimeAdd">
                  <el-icon><Collection /></el-icon>
                  <span>创建词条</span>
               </div>
               <div class="create-item" @click="goToArticleAdd">
                  <el-icon><Document /></el-icon>
                  <span>发表文章</span>
               </div>
               <div class="create-item" @click="goToVideoAdd">
                  <el-icon><VideoCamera /></el-icon>
                  <span>发布视频</span>
               </div>
            </div>
         </template>
      </el-dropdown>
      
      <!-- 未登录 -->
      <button 
         v-if="!userStore.isLoggedIn" 
         class="action-button flex-center" 
         @click="goToLogin"
      >
         <Cpu class="action-icon" />
      </button>
      
      <!-- 已登录用户下拉菜单 -->
      <el-dropdown 
         v-if="userStore.isLoggedIn" 
         trigger="click" 
         placement="bottom-end"
         popper-class="user-dropdown"
      >
         <button class="action-button user-avatar-btn flex-center">
            <el-image 
               class="user-avatar" 
               :src="userStore.userInfo?.avatar" 
               fit="cover"
            >
               <template #error>
                  <div class="avatar-fallback">
                     {{ userStore.userInfo?.username?.charAt(0) || 'U' }}
                  </div>
               </template>
            </el-image>
         </button>
         
         <template #dropdown>
            <div class="user-menu">
               <!-- 用户信息 -->
               <div class="user-info">
                  <el-image 
                     class="menu-avatar" 
                     :src="userStore.userInfo?.avatar" 
                     fit="cover"
                  >
                     <template #error>
                        <div class="menu-avatar-fallback">
                           {{ userStore.userInfo?.username?.charAt(0) || 'U' }}
                        </div>
                     </template>
                  </el-image>
                  <h3 class="username">{{ userStore.userInfo?.username }}</h3>
                  <p class="bio">{{ userStore.userInfo?.bio }}</p>
                  <span class="level">Lv.{{ userStore.userInfo?.level }}</span>
               </div>
               
               <!-- 菜单项 -->
               <div class="menu-list">
                  <div class="menu-item">
                     <el-icon><User /></el-icon>
                     <span>个人空间</span>
                  </div>
                  
                  <div class="menu-divider"></div>
                  
                  <div class="menu-item logout" @click="handleLogout">
                     <el-icon><SwitchButton /></el-icon>
                     <span>退出登录</span>
                  </div>
               </div>
            </div>
         </template>
      </el-dropdown>
   </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { 
   Cpu, 
   Search, 
   Sunny, 
   User,
   SwitchButton,
   Plus,
   Collection,
   Document,
   VideoCamera
} from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const goToLogin = () => {
   router.push('/login')
}

const goToSearch = () => {
   router.push('/search')
}

const goToAnimeAdd = () => {
   router.push('/anime/add')
}

const goToArticleAdd = () => {
   router.push('/article/add')
}

const goToVideoAdd = () => {
   router.push('/video/add')
}

const handleLogout = () => {
   ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
   }).then(() => {
      userStore.logout()
      ElMessage.success('已退出登录')
      router.push('/home')
   }).catch(() => {})
}
</script>

<style scoped lang="scss">
@import '../index.scss';

.header-actions {
   display: flex;
   align-items: center;
   gap: 15px;
}

.action-button {
   width: 40px;
   height: 40px;
   border: none;
   border-radius: 50%;
   background: transparent;
   cursor: pointer;
   display: flex;
   align-items: center;
   justify-content: center;
   transition: background 0.3s;
   
   &:hover {
      background: rgba(255, 178, 190, 0.15);
   }
   
   .action-icon {
      color: #bc004b;
      width: 24px;
      height: 24px;
   }
}

.user-avatar-btn {
   padding: 0;
   overflow: hidden;
   
   .user-avatar {
      width: 36px;
      height: 36px;
      border-radius: 50%;
   }
   
   .avatar-fallback {
      width: 36px;
      height: 36px;
      border-radius: 50%;
      background: #c41e3a;
      color: #fff;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 16px;
      font-weight: bold;
   }
}
</style>

<style lang="scss">
// 创建下拉菜单全局样式
.create-dropdown {
   padding: 0 !important;
   border-radius: 12px !important;
   overflow: hidden;
   
   .create-menu {
      width: 180px;
      background: #fff;
      padding: 8px 0;
      
      .create-item {
         display: flex;
         align-items: center;
         gap: 12px;
         padding: 12px 20px;
         cursor: pointer;
         transition: background 0.2s;
         font-size: 14px;
         color: #333;
         
         &:hover {
            background: #fff5f5;
            color: #c41e3a;
            
            .el-icon {
               color: #c41e3a;
            }
         }
         
         .el-icon {
            font-size: 18px;
            color: #666;
         }
      }
   }
}

// 用户下拉菜单全局样式
.user-dropdown {
   padding: 0 !important;
   border-radius: 12px !important;
   overflow: hidden;
   
   .user-menu {
      width: 200px;
      background: #fff;
   }
   
   .user-info {
      padding: 20px;
      text-align: center;
      background: linear-gradient(180deg, #fff5f5 0%, #fff 100%);
      border-bottom: 1px solid #f0f0f0;
      
      .menu-avatar {
         width: 60px;
         height: 60px;
         border-radius: 50%;
         margin-bottom: 12px;
      }
      
      .menu-avatar-fallback {
         width: 60px;
         height: 60px;
         border-radius: 50%;
         background: #c41e3a;
         color: #fff;
         display: flex;
         align-items: center;
         justify-content: center;
         font-size: 24px;
         font-weight: bold;
         margin: 0 auto 12px;
      }
      
      .username {
         font-size: 18px;
         font-weight: 600;
         color: #333;
         margin-bottom: 6px;
      }
      
      .bio {
         font-size: 12px;
         color: #999;
         margin-bottom: 10px;
         padding: 0 10px;
         line-height: 1.4;
      }
      
      .level {
         display: inline-block;
         padding: 2px 12px;
         background: #c41e3a;
         color: #fff;
         font-size: 12px;
         border-radius: 10px;
      }
   }
   
   .menu-list {
      padding: 8px 0;
   }
   
   .menu-item {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 12px 20px;
      cursor: pointer;
      transition: background 0.2s;
      
      &:hover {
         background: #fff5f5;
      }
      
      &.logout {
         color: #c41e3a;
         
         &:hover {
            background: #fff5f5;
         }
         
         .el-icon {
            color: #c41e3a;
         }
         
         span {
            color: #c41e3a;
         }
      }
      
      .el-icon {
         font-size: 18px;
         color: #666;
      }
      
      span {
         font-size: 14px;
         color: #333;
      }
   }
   
   .menu-divider {
      height: 1px;
      background: #f0f0f0;
      margin: 8px 0;
   }
}
</style>

