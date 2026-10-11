<template>
  <div class="article-list">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="header-content">
        <div class="title-section">
          <el-icon class="header-icon" :size="32" color="#67C23A"><Document /></el-icon>
          <div class="title-text">
            <h1 class="main-title">文章管理</h1>
            <p class="sub-title">管理所有文章信息，包括新增、编辑、删除等操作</p>
          </div>
        </div>
        <el-button type="primary" size="large" @click="navigateToAdd" class="add-btn">
          <el-icon><Plus /></el-icon>
          新增文章
        </el-button>
      </div>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-icon green">
            <el-icon :size="24"><Document /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ total }}</div>
            <div class="stat-label">总文章数</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-icon blue">
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
            <div class="stat-value">{{ totalViews }}</div>
            <div class="stat-label">总阅读量</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-icon purple">
            <el-icon :size="24"><Star /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ totalLikes }}</div>
            <div class="stat-label">总点赞数</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 搜索和操作栏 -->
    <el-card class="search-card" shadow="never">
      <div class="search-bar">
        <el-input
          v-model="searchKeyword"
          placeholder="搜索文章标题或作者..."
          class="search-input"
          clearable
          @keyup.enter="handleSearch"
          @clear="handleSearch"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button type="primary" @click="handleSearch" class="search-btn">
          <el-icon><Search /></el-icon>
          搜索
        </el-button>
        <el-button @click="refreshData" class="refresh-btn">
          <el-icon><Refresh /></el-icon>
          刷新
        </el-button>
      </div>
    </el-card>

    <!-- 数据表格 -->
    <el-card class="table-card" shadow="never" v-loading="loading">
      <el-table
        :data="articleList"
        style="width: 100%"
        :header-cell-style="headerCellStyle"
        :cell-style="cellStyle"
        row-class-name="table-row"
        @row-click="handleRowClick"
      >
        <el-table-column type="index" label="序号" width="60" align="center" />
        
        <el-table-column label="文章信息" min-width="350">
          <template #default="{ row }">
            <div class="article-info">
              <el-avatar
                :size="50"
                :src="row.cover"
                shape="square"
                class="article-avatar"
                @click.stop="previewImage(row.cover)"
              >
                <el-icon :size="24"><Picture /></el-icon>
              </el-avatar>
              <div class="article-detail">
                <div class="article-title" :title="row.title">{{ row.title }}</div>
                <div class="article-summary">{{ row.summary || '暂无摘要' }}</div>
                <div class="article-meta">
                  <el-tag size="small" type="info">ID: {{ row.id }}</el-tag>
                  <span class="article-author">
                    <el-icon><User /></el-icon>
                    {{ row.author || '未知作者' }}
                  </span>
                </div>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="统计数据" width="180" align="center">
          <template #default="{ row }">
            <div class="stats-info">
              <div class="stat-item">
                <el-icon><View /></el-icon>
                <span>{{ row.readCount || 0 }}</span>
              </div>
              <div class="stat-item">
                <el-icon><Star /></el-icon>
                <span>{{ row.likeCount || 0 }}</span>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="发布时间" width="160" align="center">
          <template #default="{ row }">
            <div class="time-info">
              <el-icon><Clock /></el-icon>
              <span>{{ formatTime(row.publishDate) }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="200" fixed="right" align="center">
          <template #default="{ row }">
            <div class="action-buttons">
              <el-button
                type="primary"
                link
                size="small"
                @click.stop="navigateToEdit(row.id)"
                class="action-btn"
              >
                <el-icon><Edit /></el-icon>
                编辑
              </el-button>
              <el-button
                type="danger"
                link
                size="small"
                @click.stop="handleDelete(row.id)"
                class="action-btn"
              >
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 空状态 -->
      <el-empty v-if="articleList.length === 0 && !loading" description="暂无数据" />

      <!-- 分页 -->
      <div class="pagination-wrapper" v-if="total > 0">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          background
        />
      </div>
    </el-card>

    <!-- 图片预览对话框 -->
    <el-dialog
      v-model="previewVisible"
      title="图片预览"
      width="500px"
      align-center
    >
      <div class="preview-container">
        <el-image :src="previewImageUrl" fit="contain" style="width: 100%; max-height: 400px;" />
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from 'element-plus';
import {
  Document,
  Plus,
  Search,
  Refresh,
  Edit,
  Delete,
  Picture,
  Clock,
  TrendCharts,
  Star,
  View,
  User
} from '@element-plus/icons-vue';
import * as articleApi from '../../api/article';

const router = useRouter();
const loading = ref(false);
const articleList = ref<any[]>([]);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const searchKeyword = ref('');
const previewVisible = ref(false);
const previewImageUrl = ref('');

// 统计数据（模拟）
const recentCount = ref(8);
const totalViews = ref(5680);
const totalLikes = ref(2340);

// 表格样式
const headerCellStyle = {
  background: '#f5f7fa',
  color: '#606266',
  fontWeight: 'bold',
  fontSize: '14px'
};

const cellStyle = {
  padding: '12px 0'
};

// 格式化时间
const formatTime = (time: string | undefined) => {
  if (!time) return '暂无';
  const date = new Date(time);
  return date.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  });
};

// 预览图片
const previewImage = (url: string) => {
  if (url) {
    previewImageUrl.value = url;
    previewVisible.value = true;
  }
};

// 行点击事件
const handleRowClick = (row: any) => {
  navigateToEdit(row.id);
};

const navigateToAdd = () => {
  router.push('/article/add');
};

const navigateToEdit = (id: number) => {
  router.push(`/article/edit/${id}`);
};

const handleSearch = () => {
  currentPage.value = 1;
  fetchArticleList();
};

const refreshData = () => {
  searchKeyword.value = '';
  currentPage.value = 1;
  fetchArticleList();
};

const handleSizeChange = (size: number) => {
  pageSize.value = size;
  fetchArticleList();
};

const handleCurrentChange = (page: number) => {
  currentPage.value = page;
  fetchArticleList();
};

const handleDelete = (id: number) => {
  ElMessageBox.confirm('确定要删除这篇文章吗？删除后无法恢复！', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    confirmButtonClass: 'el-button--danger'
  }).then(async () => {
    try {
      await articleApi.deleteArticle(id);
      ElMessage.success('删除成功');
      fetchArticleList();
    } catch (error) {
      ElMessage.error('删除失败，请重试');
    }
  }).catch(() => {
    // 取消删除
  });
};

const fetchArticleList = async () => {
  loading.value = true;
  try {
    const res = await articleApi.getArticleList();
    const data = res.data || res;
    articleList.value = data || [];
    total.value = data?.length || 0;
  } catch (error) {
    ElMessage.error('获取文章列表失败');
    console.error(error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchArticleList();
});
</script>

<style scoped>
.article-list {
  padding: 20px;
}

/* 页面标题 */
.page-header {
  margin-bottom: 24px;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
  padding: 24px 32px;
  border-radius: 12px;
  color: #fff;
}

.title-section {
  display: flex;
  align-items: center;
  gap: 16px;
}

.header-icon {
  background: rgba(255, 255, 255, 0.2);
  padding: 12px;
  border-radius: 12px;
}

.main-title {
  font-size: 24px;
  font-weight: bold;
  margin: 0 0 4px 0;
}

.sub-title {
  font-size: 14px;
  opacity: 0.9;
  margin: 0;
}

.add-btn {
  background: rgba(255, 255, 255, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.3);
  color: #fff;
  font-weight: 500;
}

.add-btn:hover {
  background: rgba(255, 255, 255, 0.3);
  border-color: rgba(255, 255, 255, 0.5);
}

/* 统计卡片 */
.stats-row {
  margin-bottom: 24px;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 20px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.1);
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
  color: #fff;
}

.stat-icon.green {
  background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
}

.stat-icon.blue {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.stat-icon.orange {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.stat-icon.purple {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 13px;
  color: #909399;
}

/* 搜索栏 */
.search-card {
  margin-bottom: 20px;
  border-radius: 12px;
}

.search-bar {
  display: flex;
  gap: 12px;
  align-items: center;
}

.search-input {
  width: 400px;
}

.search-btn {
  background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
  border: none;
}

.search-btn:hover {
  opacity: 0.9;
}

/* 表格卡片 */
.table-card {
  border-radius: 12px;
}

/* 文章信息 */
.article-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.article-avatar {
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.article-avatar:hover {
  transform: scale(1.05);
}

.article-detail {
  flex: 1;
  min-width: 0;
}

.article-title {
  font-weight: 500;
  color: #303133;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.article-summary {
  font-size: 13px;
  color: #606266;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.article-meta {
  display: flex;
  align-items: center;
  gap: 12px;
}

.article-author {
  font-size: 12px;
  color: #909399;
  display: flex;
  align-items: center;
  gap: 4px;
}

/* 统计信息 */
.stats-info {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.stat-item {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  color: #606266;
  font-size: 13px;
}

.time-info {
  display: flex;
  align-items: center;
  gap: 4px;
  color: #909399;
  font-size: 13px;
}

/* 操作按钮 */
.action-buttons {
  display: flex;
  justify-content: center;
  gap: 8px;
}

.action-btn {
  font-weight: 500;
}

/* 分页 */
.pagination-wrapper {
  margin-top: 24px;
  padding-top: 24px;
  border-top: 1px solid #ebeef5;
  display: flex;
  justify-content: flex-end;
}

/* 表格行样式 */
:deep(.table-row) {
  cursor: pointer;
  transition: background-color 0.3s ease;
}

:deep(.table-row:hover) {
  background-color: #f5f7fa !important;
}

/* 预览容器 */
.preview-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

/* 响应式 */
@media (max-width: 768px) {
  .article-list {
    padding: 12px;
  }

  .header-content {
    flex-direction: column;
    gap: 16px;
    text-align: center;
  }

  .search-bar {
    flex-direction: column;
  }

  .search-input {
    width: 100%;
  }

  .stat-card {
    margin-bottom: 12px;
  }
}
</style>
