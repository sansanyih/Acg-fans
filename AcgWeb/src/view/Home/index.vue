<template>
   <div class="home">
      <div class="container flx-center">
         <div class="carousel">
            <Carousel></Carousel>
         </div>

         <div class="card-slider">
            <RandomRecommend />
         </div>

         <div class="card-slider">
            <LatestAnimation />
         </div>

         <div class="card-slider">
            <UpcomingAnimation />
         </div>

         <div class="banner-container flx-center">
            <div v-for="item in bannerList" :key="item.id" class="banner-item">
               <a v-if="item.imageUrl" :href="item.link || 'javascript:void(0)'" target="_blank">
                  <el-image style="width: 100%; height: 100%" :src="item.imageUrl"/>
               </a>
            </div>
         </div>
         <div class="content-with-sidebar">
            <div class="main-content">
               <LatestFeed />
            </div>
            <div class="sidebar">
               <WeeklyReport />
            </div>
         </div>
         <div class="featured-section ">
            <div class="left-container">
               <LatesArticles />
               <LatestVideo />
               <Link></Link>
            </div>
            <div class="right-container">
               <CharacterBirthday />
               <AnnouncementSection />
               <RecentEdit />
               <CommunityLinks />
            </div>
         </div>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import Carousel from './components/Carousel/Carousel.vue'
import RandomRecommend from "./components/RandomRecommend/index.vue"
import LatestAnimation from "./components/LatestAnimation/index.vue"
import UpcomingAnimation from "./components/UpcomingAnimation/index.vue"
import LatestFeed from "./components/LatestFeed/index.vue"
import LatestVideo from "./components/LatestVideo/index.vue"
import WeeklyReport from "./components/WeeklyReport/index.vue"
import LatesArticles from "./components/LatesArticles/index.vue"
import CharacterBirthday from "./components/CharacterBirthday/index.vue"
import AnnouncementSection from "./components/AnnouncementSection/index.vue"
import RecentEdit from "./components/RecentEdit/index.vue"
import CommunityLinks from "./components/CommunityLinks/index.vue"
import Link from "@/view/Home/components/Link/index.vue"

const bannerList = ref([
   { id: 1, link: '', imageUrl: 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/view_3.jpg' },
   { id: 2, link: '', imageUrl: 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/view_2.jpg' },
   { id: 3, link: '', imageUrl: 'https://jsd.onmicrosoft.cn/gh/sansanyih/tuchuang@main/img/view_3.jpg' }
])
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
