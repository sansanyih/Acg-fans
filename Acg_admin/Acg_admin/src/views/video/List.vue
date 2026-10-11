<template>
  <div class="video-list">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="header-content">
        <div class="header-icon">
          <el-icon :size="32"><VideoCamera /></el-icon>
        </div>
        <div class="header-text">
          <h1>视频管理</h1>
          <p>管理平台所有视频内容</p>
        </div>
      </div>
      <el-button type="primary" size="large" @click="navigateToAdd">
        <el-icon><Plus /></el-icon>
        新增视频
      </el-button>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-icon purple">
            <el-icon :size="24"><VideoCamera /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ total }}</div>
            <div class="stat-label">总视频数</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-icon green">
            <el-icon :size="24"><TrendCharts /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ recentCount }}</div>
            <div class="stat-label">本周新增</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-icon orange">
            <el-icon :size="24"><View /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ viewCount }}</div>
            <div class="stat-label">总播放量</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-icon blue">
            <el-icon :size="24"><Star /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ hotCount }}</div>
            <div class="stat-label">热门视频</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 数据表格卡片 -->
    <el-card class="table-card" shadow="never">
      <template #header>
        <div class="table-header">
          <div class="header-title">
            <el-icon><List /></el-icon>
            <span>视频列表</span>
          </div>
          <div class="header-actions">
            <el-input
              v-model="searchKeyword"
              placeholder="搜索视频标题或作者"
              style="width: 280px;"
              clearable
              @keyup.enter="handleSearch"
              @clear="handleSearch"
            >
              <template #prefix>
                <el-icon><Search /></el-icon>
              </template>
              <template #append>
                <el-button @click="handleSearch">搜索</el-button>
              </template>
            </el-input>
            <el-button type="info" plain @click="refreshData">
              <el-icon><Refresh /></el-icon>
              刷新
            </el-button>
          </div>
        </div>
      </template>

      <el-table
        :data="videoList"
        style="width: 100%;"
        v-loading="loading"
        :header-cell-style="headerCellStyle"
        :cell-style="cellStyle"
        stripe
      >
        <el-table-column prop="id" label="ID" width="70" align="center">
          <template #default="{ row }">
            <span class="id-badge">#{{ row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="视频标题" min-width="220">
          <template #default="{ row }">
            <div class="video-title">
              <el-icon class="title-icon"><VideoPlay /></el-icon>
              <span class="title-text">{{ row.title }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="author" label="作者" width="160">
          <template #default="{ row }">
            <div class="author-info">
              <el-avatar :size="28" :icon="UserFilled" />
              <span class="author-name">{{ row.author }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="publishDate" label="发布时间" width="180">
          <template #default="{ row }">
            <div class="date-info">
              <el-icon><Calendar /></el-icon>
              <span class="date-text">{{ row.publishDate }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="视频描述" min-width="180" show-overflow-tooltip />
        <el-table-column label="操作" width="160" fixed="right" align="center">
          <template #default="{ row }">
            <div class="action-buttons">
              <el-button type="primary" size="small" plain @click="navigateToEdit(row.id)">
                <el-icon><Edit /></el-icon>
                编辑
              </el-button>
              <el-button type="danger" size="small" plain @click="handleDelete(row.id)">
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 空状态 -->
      <el-empty v-if="videoList.length === 0 && !loading" description="暂无视频数据" />

      <!-- 分页 -->
      <div class="pagination-wrapper" v-if="total > 0">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';
import {
  VideoCamera,
  Plus,
  Search,
  Refresh,
  Edit,
  Delete,
  TrendCharts,
  Star,
  View,
  List,
  VideoPlay,
  UserFilled,
  Calendar
} from '@element-plus/icons-vue';
import * as videoApi from '../../api/video';

const router = useRouter();
const loading = ref(false);
const videoList = ref<any[]>([]);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const searchKeyword = ref('');

// 统计数据（模拟）
const recentCount = ref(15);
const hotCount = ref(6);
const viewCount = ref(25680);

// 表格样式
const headerCellStyle = {
  background: '#f5f7fa',
  color: '#606266',
  fontWeight: 'bold',
  fontSize: '14px'
};

const cellStyle = {
  padding: '16px 0'
};

const navigateToAdd = () => {
  router.push('/video/add');
};

const navigateToEdit = (id: number) => {
  router.push(`/video/edit/${id}`);
};

const handleSearch = () => {
  currentPage.value = 1;
  fetchVideoList();
};

const refreshData = () => {
  searchKeyword.value = '';
  currentPage.value = 1;
  fetchVideoList();
  ElMessage.success('数据已刷新');
};

const handleSizeChange = (size: number) => {
  pageSize.value = size;
  fetchVideoList();
};

const handleCurrentChange = (page: number) => {
  currentPage.value = page;
  fetchVideoList();
};

const handleDelete = (id: number) => {
  ElMessageBox.confirm('确定要删除这个视频吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await videoApi.deleteVideo(id);
      ElMessage.success('删除成功');
      fetchVideoList();
    } catch (error) {
      ElMessage.error('删除失败，请重试');
    }
  }).catch(() => {
    // 取消删除
  });
};

const fetchVideoList = async () => {
  loading.value = true;
  try {
    let res: any;
    if (searchKeyword.value) {
      res = await videoApi.searchVideo(searchKeyword.value);
    } else {
      res = await videoApi.getVideoList();
    }
    // 处理 API 返回的数据格式
    const data = res.data || res || [];
    videoList.value = Array.isArray(data) ? data : [];
    total.value = videoList.value.length;
  } catch (error) {
    console.error('获取视频列表失败:', error);
    ElMessage.error('获取视频列表失败');
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchVideoList();
});
</script>

<style scoped>
.video-list {
  padding: 20px;
  background: #f0f2f5;
  min-height: 100%;
}

/* 页面标题 */
.page-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  padding: 24px 32px;
  margin-bottom: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.3);
}

.header-content {
  display: flex;
  align-items: center;
  gap: 16px;
}

.header-icon {
  width: 56px;
  height: 56px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.header-text h1 {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
  color: white;
}

.header-text p {
  margin: 4px 0 0 0;
  font-size: 14px;
  color: rgba(255, 255, 255, 0.8);
}

/* 统计卡片 */
.stats-row {
  margin-bottom: 24px;
}

.stat-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  transition: transform 0.3s, box-shadow 0.3s;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.stat-icon.purple {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.stat-icon.green {
  background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
}

.stat-icon.orange {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.stat-icon.blue {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  color: #303133;
  line-height: 1.2;
}

.stat-label {
  font-size: 13px;
  color: #909399;
  margin-top: 4px;
}

/* 表格卡片 */
.table-card {
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
}

.table-card :deep(.el-card__header) {
  padding: 16px 20px;
  border-bottom: 1px solid #ebeef5;
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

/* ID 徽章 */
.id-badge {
  display: inline-block;
  padding: 4px 10px;
  background: #f4f4f5;
  border-radius: 6px;
  font-size: 13px;
  color: #606266;
  font-weight: 500;
}

/* 视频标题 */
.video-title {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 4px 0;
}

.title-icon {
  color: #667eea;
  flex-shrink: 0;
}

.title-text {
  font-weight: 500;
  color: #303133;
  line-height: 1.4;
}

/* 作者信息 */
.author-info {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 4px 0;
}

.author-name {
  font-weight: 500;
  color: #606266;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 100px;
}

/* 日期信息 */
.date-info {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #606266;
  padding: 4px 0;
}

.date-text {
  font-size: 13px;
  white-space: nowrap;
}

/* 操作按钮 */
.action-buttons {
  display: flex;
  justify-content: center;
  gap: 8px;
}

/* 分页 */
.pagination-wrapper {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #ebeef5;
  display: flex;
  justify-content: flex-end;
}

/* 响应式 */
@media (max-width: 768px) {
  .video-list {
    padding: 12px;
  }

  .page-header {
    flex-direction: column;
    gap: 16px;
    text-align: center;
  }

  .header-content {
    flex-direction: column;
  }

  .table-header {
    flex-direction: column;
    gap: 12px;
    align-items: stretch;
  }

  .header-actions {
    flex-direction: column;
  }

  .header-actions .el-input {
    width: 100% !important;
  }
}
</style>
