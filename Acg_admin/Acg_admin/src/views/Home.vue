<template>
  <div class="dashboard">
    <!-- 欢迎卡片 -->
    <el-card class="welcome-card">
      <template #header>
        <div class="card-header">
          <span class="welcome-title">欢迎使用ACG后台管理系统</span>
        </div>
      </template>
      <div class="welcome-content">
        <p>您现在可以管理以下内容：</p>
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="feature-item">
              <el-icon class="feature-icon" :size="24" color="#409EFF"><VideoCamera /></el-icon>
              <span>番剧管理</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="feature-item">
              <el-icon class="feature-icon" :size="24" color="#67C23A"><Document /></el-icon>
              <span>文章管理</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="feature-item">
              <el-icon class="feature-icon" :size="24" color="#E6A23C"><VideoPlay /></el-icon>
              <span>视频管理</span>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="feature-item">
              <el-icon class="feature-icon" :size="24" color="#F56C6C"><User /></el-icon>
              <span>用户管理</span>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card anime-card" @click="navigateTo('/anime/list')">
          <div class="stat-icon">
            <el-icon :size="40" color="#fff"><VideoCamera /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-number">{{ stats.animeCount }}</div>
            <div class="stat-label">番剧数量</div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card article-card" @click="navigateTo('/article/list')">
          <div class="stat-icon">
            <el-icon :size="40" color="#fff"><Document /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-number">{{ stats.articleCount }}</div>
            <div class="stat-label">文章数量</div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card video-card" @click="navigateTo('/video/list')">
          <div class="stat-icon">
            <el-icon :size="40" color="#fff"><VideoPlay /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-number">{{ stats.videoCount }}</div>
            <div class="stat-label">视频数量</div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card user-card" @click="navigateTo('/user/list')">
          <div class="stat-icon">
            <el-icon :size="40" color="#fff"><User /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-number">{{ stats.userCount }}</div>
            <div class="stat-label">用户数量</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="chart-row">
      <el-col :xs="24" :lg="12">
        <el-card class="chart-card">
          <template #header>
            <div class="chart-header">
              <span>内容分布</span>
            </div>
          </template>
          <div ref="pieChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :xs="24" :lg="12">
        <el-card class="chart-card">
          <template #header>
            <div class="chart-header">
              <span>近7天新增趋势</span>
            </div>
          </template>
          <div ref="lineChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最近活动 -->
    <el-row :gutter="20" class="activity-row">
      <el-col :xs="24" :lg="16">
        <el-card class="activity-card">
          <template #header>
            <div class="card-header">
              <span>最近新增内容</span>
              <el-button link type="primary" @click="refreshData">刷新</el-button>
            </div>
          </template>
          <el-table :data="recentItems" style="width: 100%" v-loading="loading">
            <el-table-column prop="type" label="类型" width="100">
              <template #default="{ row }">
                <el-tag :type="getTagType(row.type)" size="small">{{ row.type }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="title" label="标题" show-overflow-tooltip />
            <el-table-column prop="date" label="时间" width="180" />
            <el-table-column label="操作" width="100" align="center">
              <template #default="{ row }">
                <el-button link type="primary" size="small" @click="viewDetail(row)">查看</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :xs="24" :lg="8">
        <el-card class="quick-actions">
          <template #header>
            <div class="card-header">
              <span>快捷操作</span>
            </div>
          </template>
          <div class="action-list">
            <el-button type="primary" plain class="action-btn" @click="navigateTo('/anime/add')">
              <el-icon><Plus /></el-icon>
              新增番剧
            </el-button>
            <el-button type="success" plain class="action-btn" @click="navigateTo('/article/add')">
              <el-icon><Plus /></el-icon>
              新增文章
            </el-button>
            <el-button type="warning" plain class="action-btn" @click="navigateTo('/video/add')">
              <el-icon><Plus /></el-icon>
              新增视频
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick } from 'vue';
import { useRouter } from 'vue-router';
import * as echarts from 'echarts';
import { VideoCamera, Document, VideoPlay, User, Plus } from '@element-plus/icons-vue';
import * as animeApi from '../api/anime';
import * as articleApi from '../api/article';
import * as videoApi from '../api/video';
import * as userApi from '../api/user';

const router = useRouter();
const pieChartRef = ref<HTMLDivElement>();
const lineChartRef = ref<HTMLDivElement>();
let pieChart: echarts.ECharts | null = null;
let lineChart: echarts.ECharts | null = null;
const loading = ref(false);

const stats = ref({
  animeCount: 0,
  articleCount: 0,
  videoCount: 0,
  userCount: 0
});

const recentItems = ref([
  { type: '番剧', title: '某科学的超电磁炮', date: '2024-01-15 10:30' },
  { type: '文章', title: '2024年春季新番推荐', date: '2024-01-14 15:20' },
  { type: '视频', title: '进击的巨人最终季解说', date: '2024-01-13 09:15' },
  { type: '番剧', title: '鬼灭之刃 柱训练篇', date: '2024-01-12 14:45' },
  { type: '文章', title: '动漫音乐推荐TOP10', date: '2024-01-11 11:30' }
]);

const navigateTo = (path: string) => {
  router.push(path);
};

const getTagType = (type: string) => {
  const types: Record<string, string> = {
    '番剧': 'primary',
    '文章': 'success',
    '视频': 'warning',
    '用户': 'info'
  };
  return types[type] || 'info';
};

const viewDetail = (row: any) => {
  const paths: Record<string, string> = {
    '番剧': '/anime/list',
    '文章': '/article/list',
    '视频': '/video/list'
  };
  router.push(paths[row.type] || '/');
};

const refreshData = () => {
  loading.value = true;
  setTimeout(() => {
    loading.value = false;
  }, 500);
};

const initPieChart = () => {
  if (!pieChartRef.value) return;
  
  pieChart = echarts.init(pieChartRef.value);
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      top: 'center'
    },
    series: [
      {
        name: '内容分布',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 20,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: stats.value.animeCount, name: '番剧', itemStyle: { color: '#409EFF' } },
          { value: stats.value.articleCount, name: '文章', itemStyle: { color: '#67C23A' } },
          { value: stats.value.videoCount, name: '视频', itemStyle: { color: '#E6A23C' } },
          { value: stats.value.userCount, name: '用户', itemStyle: { color: '#F56C6C' } }
        ]
      }
    ]
  };
  pieChart.setOption(option);
};

const initLineChart = () => {
  if (!lineChartRef.value) return;
  
  lineChart = echarts.init(lineChartRef.value);
  const option = {
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['番剧', '文章', '视频'],
      bottom: 0
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '10%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '番剧',
        type: 'line',
        smooth: true,
        data: [12, 15, 8, 20, 18, 25, 22],
        itemStyle: { color: '#409EFF' },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
            { offset: 1, color: 'rgba(64, 158, 255, 0.05)' }
          ])
        }
      },
      {
        name: '文章',
        type: 'line',
        smooth: true,
        data: [8, 12, 15, 10, 16, 14, 18],
        itemStyle: { color: '#67C23A' },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(103, 194, 58, 0.3)' },
            { offset: 1, color: 'rgba(103, 194, 58, 0.05)' }
          ])
        }
      },
      {
        name: '视频',
        type: 'line',
        smooth: true,
        data: [5, 8, 12, 6, 10, 15, 11],
        itemStyle: { color: '#E6A23C' },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(230, 162, 60, 0.3)' },
            { offset: 1, color: 'rgba(230, 162, 60, 0.05)' }
          ])
        }
      }
    ]
  };
  lineChart.setOption(option);
};

const fetchStats = async () => {
  try {
    const [animeRes, articleRes, videoRes, userRes] = await Promise.all([
      animeApi.getAnimeList(),
      articleApi.getArticleList(),
      videoApi.getVideoList(),
      userApi.getAllUsers()
    ]);
    
    stats.value = {
      animeCount: animeRes.data?.length || 128,
      articleCount: articleRes.data?.length || 256,
      videoCount: videoRes.data?.length || 512,
      userCount: userRes.data?.length || 1024
    };
  } catch (error) {
    console.error('获取统计数据失败:', error);
    // 使用默认数据
    stats.value = {
      animeCount: 128,
      articleCount: 256,
      videoCount: 512,
      userCount: 1024
    };
  }
};

const handleResize = () => {
  pieChart?.resize();
  lineChart?.resize();
};

onMounted(async () => {
  await fetchStats();
  
  nextTick(() => {
    initPieChart();
    initLineChart();
  });
  
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
  pieChart?.dispose();
  lineChart?.dispose();
});
</script>

<style scoped>
.dashboard {
  width: 100%;
  padding: 20px;
}

.welcome-card {
  margin-bottom: 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
}

.welcome-card :deep(.el-card__header) {
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

.welcome-title {
  font-size: 20px;
  font-weight: bold;
  color: #fff;
}

.welcome-content {
  color: #fff;
}

.welcome-content p {
  margin-bottom: 20px;
  font-size: 16px;
}

.feature-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 15px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
}

.feature-item:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: translateY(-2px);
}

.feature-icon {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 8px;
  padding: 8px;
}

.stats-row {
  margin-bottom: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: none;
  color: #fff;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
}

.anime-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.article-card {
  background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
}

.video-card {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.user-card {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-icon {
  margin-right: 15px;
  padding: 10px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 12px;
}

.stat-content {
  flex: 1;
}

.stat-number {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  opacity: 0.9;
}

.chart-row {
  margin-bottom: 20px;
}

.chart-card {
  height: 400px;
}

.chart-header {
  font-weight: bold;
  font-size: 16px;
}

.chart-container {
  width: 100%;
  height: 320px;
}

.activity-row {
  margin-bottom: 20px;
}

.activity-card {
  min-height: 400px;
}

.quick-actions {
  min-height: 400px;
}

.action-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.action-btn {
  width: 100%;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-size: 16px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

@media (max-width: 768px) {
  .dashboard {
    padding: 10px;
  }
  
  .feature-item {
    margin-bottom: 10px;
  }
  
  .stat-card {
    margin-bottom: 10px;
  }
  
  .chart-card {
    margin-bottom: 10px;
  }
}
</style>
