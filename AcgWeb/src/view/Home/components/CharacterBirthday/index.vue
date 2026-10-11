<template>
   <div class="character-birthday-section">
      <h2 class="section-title">角色生日</h2>
      
      <div v-if="todayBirthdayList.length" class="character-list">
         <div 
            v-for="item in todayBirthdayList" 
            :key="item.id" 
            class="character-card"
            @click="goToCharacter(item.id)"
         >
            <el-image class="character-image" :src="item.image" fit="cover">
               <template #error>
                  <div class="image-fallback">{{ item.name.charAt(0) }}</div>
               </template>
            </el-image>
            <div class="character-info">
               <div class="character-name">
                  {{ item.name }}
                  <span class="anime-title">《{{ item.animeChineseTitle }}》</span>
               </div>
               <p class="character-desc">{{ item.description }}</p>
            </div>
         </div>
      </div>
      
      <div v-else class="empty-state">
         <el-icon><Calendar /></el-icon>
         <span>今天没有角色生日</span>
      </div>
   </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { Calendar } from '@element-plus/icons-vue'
import { characterApi, type Character } from '@/api/modules/character/'

const router = useRouter()
const characterList = ref<Character[]>([])

const getTodayString = () => {
   const now = new Date()
   const month = String(now.getMonth() + 1).padStart(2, '0')
   const day = String(now.getDate()).padStart(2, '0')
   return `${month}-${day}`
}

const todayBirthdayList = computed(() => {
   const today = getTodayString()
   return characterList.value.filter((char: Character) => char.birthday === today)
})

const fetchCharacterData = async () => {
   try {
      const data = await characterApi.getAll()
      characterList.value = data
   } catch (error) {
      console.error('获取角色数据失败:', error)
   }
}

const goToCharacter = (id: number) => {
   router.push(`/character/${id}`)
}

onMounted(() => {
   fetchCharacterData()
})
</script>

<style scoped lang="scss">
@import './index.scss';
</style>