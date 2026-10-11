<template>
   <div class="links-section">
      <h2 class="links-title">友情链接</h2>
      <div class="links-grid">
         <a 
            v-for="item in linksList" 
            :key="item.id"
            :href="item.link"
            target="_blank"
            class="link-card"
         >
            <div class="link-image">
               <el-image 
                  :src="item.url" 
                  :alt="item.name"
                  fit="cover"
                  style="width: 100%; height: 100%"
               >
                  <template #error>
                     <div class="image-error">{{ item.name.charAt(0) }}</div>
                  </template>
               </el-image>
            </div>
            <span class="link-name">{{ item.name }}</span>
         </a>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { linksApi, type LinkItem } from '@/api/modules/links'

const linksList = ref<LinkItem[]>([])

const fetchLinksData = async () => {
   try {
      const data = await linksApi.getAll()
      linksList.value = data
   } catch (error) {
      console.error('获取友情链接失败:', error)
   }
}

onMounted(() => {
   fetchLinksData()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>