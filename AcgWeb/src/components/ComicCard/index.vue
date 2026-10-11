<template>
   <div class="game-card">
      <!-- 封面图 -->
      <div class="cover">
         <img :src="props.url" alt="">
      </div>
      <!-- 文字信息 -->
      <div class="info">
         <h3 class="title">{{ props.title }}</h3>
         <p class="desc">{{ props.desc }}</p>
         <span class="tag" v-for="value in props.tags">
            <el-tag>{{ value}}</el-tag>
         </span>
      </div>
   </div>
</template>
<script setup lang="ts">
import type { Comic } from './types';
const props = defineProps<Comic>();
</script>
<style scoped lang="scss"></style>
