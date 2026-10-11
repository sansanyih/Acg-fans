<template>
  <div class="card-slider">
    <div class="slider-header">
      <h2 class="title">{{ title }}</h2>
      <slot name="extra"></slot>
    </div>

    <div class="slider-wrapper">
      <button class="arrow left" @click="scroll('left')">
        <el-icon><ArrowLeftBold /></el-icon>
      </button>
      <div ref="sliderRef" class="slider">
        <slot></slot>
      </div>
      <button class="arrow right" @click="scroll('right')">
        <el-icon><ArrowRightBold /></el-icon>
      </button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const props = defineProps<{
  title: string;
  scrollStep?: number; // 允许外部控制滚动距离
}>();

const sliderRef = ref<HTMLElement>();

const scroll = (direction: 'left' | 'right') => {
  if (!sliderRef.value) return;
  // 如果没传 scrollStep，默认滚一个大致的宽度
  const step = props.scrollStep || 300;
  sliderRef.value.scrollBy({
    left: direction === 'left' ? -step : step,
    behavior: 'smooth'
  });
};
</script>
<style scoped lang="scss">
@import './index.scss';
</style>
