<template>
  <div class="tab-list" ref="tabListRef">
    <div
      v-for="(item, index) in tabs"
      :key="item.name"
      class="tab-item"
      :class="{ active: activeIndex === index }"
      @click="handleClick(index, $event)"
    >
      <span class="tab-text">{{ item.label }}</span>
      <span class="tab-ripple"></span>
    </div>
    <div class="tab-line" :style="lineStyle"></div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted, onUnmounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const tabs = [
  { label: '首页', name: 'home', path: '/home' },
  { label: '广场', name: 'discuss', path: '/discuss' },
  { label: '分享游玩记录', name: 'share', path: '/share' },
]

const activeIndex = ref(-1)
const tabListRef = ref<HTMLElement>()
const itemWidths = ref<number[]>([])

// 根据当前路由设置高亮
const updateActiveByRoute = () => {
  const currentPath = route.path
  const index = tabs.findIndex(tab => {
    if (tab.path === '/home') {
      return currentPath === '/home' || currentPath === '/'
    }
    return currentPath === tab.path
  })
  activeIndex.value = index
}

// 计算下划线位置
const lineStyle = computed(() => {
  const index = activeIndex.value
  if (index === -1) return { display: 'none' }
  
  const left = itemWidths.value
    .slice(0, index)
    .reduce((sum, w) => sum + w, 0)
  const width = itemWidths.value[index] || 0
  return {
    transform: `translateX(${left}px)`,
    width: `${width}px`,
    display: 'block'
  }
})

// 遮罩动画
const triggerRipple = (el: HTMLElement) => {
  const ripple = el.querySelector('.tab-ripple') as HTMLElement
  if (!ripple) return
  ripple.style.animation = 'none'
  void ripple.offsetHeight
  ripple.style.animation = 'ripple-effect 0.4s ease-out'
}

// 计算宽度
const calcWidths = () => {
  if (!tabListRef.value) return
  const items = tabListRef.value.querySelectorAll('.tab-item')
  itemWidths.value = Array.from(items).map((el) => (el as HTMLElement).offsetWidth)
}

// 点击跳转
const handleClick = (index: number, event: MouseEvent) => {
  const target = event.currentTarget as HTMLElement
  triggerRipple(target)
  
  const tab = tabs[index]
  if (tab && tab.path) {
    router.push(tab.path)
  }
}

let resizeObserver: ResizeObserver | null = null

onMounted(() => {
  updateActiveByRoute()
  calcWidths()
  if (window.ResizeObserver && tabListRef.value) {
    resizeObserver = new ResizeObserver(calcWidths)
    resizeObserver.observe(tabListRef.value)
  } else {
    window.addEventListener('resize', calcWidths)
  }
})

onUnmounted(() => {
  if (resizeObserver) {
    resizeObserver.disconnect()
  } else {
    window.removeEventListener('resize', calcWidths)
  }
})

// 监听路由变化更新高亮
watch(() => route.path, updateActiveByRoute)
</script>

<style scoped lang="scss">
@import '../index.scss';
</style>