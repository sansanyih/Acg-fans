<template>
   <el-carousel v-if="carouselList.length > 0" indicator-position="none" height="400px">
      <el-carousel-item 
         v-for="item in carouselList" 
         :key="item.id"
         class="carousel-item"
         style="width: 100%"
      >
         <el-image 
            :src="item.url" 
            :alt="item.title"
            fit="cover"
            style="width: 100%; height: 100%"
         >
            <template #error>
               <div class="image-error">图片加载失败</div>
            </template>
         </el-image>
      </el-carousel-item>
   </el-carousel>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { carouselApi, type CarouselItem } from '@/api/modules/carousel'

const carouselList = ref<CarouselItem[]>([])

const fetchCarouselData = async () => {
   try {
      const data = await carouselApi.getAll()
      carouselList.value = data
      console.log(data)
   } catch (error) {
      console.error('获取轮播图失败:', error)
   }
}

onMounted(() => {
   fetchCarouselData()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>