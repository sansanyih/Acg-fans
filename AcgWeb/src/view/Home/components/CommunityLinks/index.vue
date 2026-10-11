<template>
   <div class="community-links-section">
      <h2 class="section-title">社区交流</h2>
      <div class="links-list">
         <a 
            v-for="item in links" 
            :key="item.id"
            :href="item.url"
            target="_blank"
            class="link-card"
         >
            <div class="link-icon" :style="{ color: item.color }">
               <el-icon v-if="item.icon === 'ChatRound'"><ChatRound /></el-icon>
               <el-icon v-else-if="item.icon === 'Opportunity'"><Opportunity /></el-icon>
               <el-icon v-else-if="item.icon === 'Connection'"><Connection /></el-icon>
               <span v-else class="icon-text">{{ item.icon }}</span>
            </div>
            <div class="link-info">
               <div class="link-title">{{ item.title }}</div>
               <div class="link-desc">{{ item.description }}</div>
            </div>
            <el-icon class="link-arrow"><ArrowRight /></el-icon>
         </a>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ChatRound, Opportunity, Connection, ArrowRight } from '@element-plus/icons-vue'

const links = [
   {
      id: 1,
      title: 'CnGal玩家交流群',
      description: '本群无资源请支持正版，调戏看板娘要注意节度哦',
      url: 'https://jq.qq.com/?_wv=1027&k=xxx',
      icon: 'ChatRound',
      color: '#ff4757'
   },
   {
      id: 2,
      title: 'CnGal编辑者交流与Bug反馈群',
      description: '群主很懒,什么都没有留下',
      url: 'https://jq.qq.com/?_wv=1027&k=yyy',
      icon: 'Opportunity',
      color: '#ffa502'
   },
   {
      id: 3,
      title: 'QQ频道',
      description: 'CnGal资料站官方QQ频道',
      url: 'https://pd.qq.com/s/xxxxx',
      icon: 'Connection',
      color: '#3742fa'
   }
]
</script>

<style scoped lang="scss">
@import './index.scss';
</style>