<template>
  <div class="header">
    <div class="header-lf">
      <Icon />
      <Tab />
    </div>
    <div class="header-ri">
      <Button />
    </div>
  </div>
</template>

<script setup lang="ts">
import Tab from './components/Tab.vue'
import Icon from './components/Icon.vue'
import Button from './components/Button.vue'
</script>

<style scoped lang="scss">
@import './index.scss';
</style>

