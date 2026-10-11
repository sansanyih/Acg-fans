<!-- SidebarCard.vue -->
<template>
  <div class="sidebar-card">
    <h3 class="title">{{ title }}</h3>
    <div class="content">
      <slot />
    </div>
  </div>
</template>

<script setup lang="ts">
interface Props {
  title: string
}

defineProps<Props>()
</script>

<style scoped lang="scss">
@import './index.scss';
</style>