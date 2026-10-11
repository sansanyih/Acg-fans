<template>
   <el-container class="layout-container">
      <el-header height="80px" class="fixed-header">
         <Header></Header>
      </el-header>
      
      <el-main class="main-content" ref="mainRef">
         <router-view v-slot="{ Component, route }">
            <transition appear name="fade-transform" mode="out-in">
               <keep-alive :include="cacheRouter">
                  <component :is="Component" :key="route.path"></component>
               </keep-alive>
            </transition>
         </router-view>
      </el-main>
      
      <el-footer height="auto">
         <Footer></Footer>
      </el-footer>
      
      <!-- 回到顶部按钮 -->
      <el-backtop 
         :visibility-height="300"
         :right="40"
         :bottom="40"
      >
         <div class="backtop-btn">
            <el-icon><ArrowUp /></el-icon>
         </div>
      </el-backtop>
   </el-container>
</template>

<script setup lang="ts">
import { ArrowUp } from '@element-plus/icons-vue'
import Header from './Header/index.vue'
import Footer from './Footer/index.vue'
import cacheRouter from '@/routers/cacheRouter'
</script>
<style scoped lang="scss">
@import './index.scss';
</style>