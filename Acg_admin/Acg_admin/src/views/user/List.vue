<template>
  <div class="user-list">
    <!-- 页面标题 -->
    <div class="page-header">
      <div class="header-content">
        <div class="header-icon">
          <el-icon :size="32"><User /></el-icon>
        </div>
        <div class="header-text">
          <h1>用户管理</h1>
          <p>管理平台所有用户信息</p>
        </div>
      </div>
      <el-button type="primary" size="large" @click="navigateToAdd">
        <el-icon><Plus /></el-icon>
        新增用户
      </el-button>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-icon blue">
            <el-icon :size="24"><User /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ total }}</div>
            <div class="stat-label">总用户数</div>
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
          <div class="stat-icon purple">
            <el-icon :size="24"><Management /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ adminCount }}</div>
            <div class="stat-label">管理员</div>
          </div>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <div class="stat-card">
          <div class="stat-icon orange">
            <el-icon :size="24"><Star /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ activeCount }}</div>
            <div class="stat-label">活跃用户</div>
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
            <span>用户列表</span>
          </div>
          <div class="header-actions">
            <el-input
              v-model="searchKeyword"
              placeholder="搜索用户名或邮箱"
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
        :data="userList"
        style="width: 100%;"
        v-loading="loading"
        :header-cell-style="headerCellStyle"
        :cell-style="cellStyle"
        stripe
      >
        <el-table-column prop="id" label="ID" width="80" align="center">
          <template #default="{ row }">
            <span class="id-badge">#{{ row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" min-width="150">
          <template #default="{ row }">
            <div class="user-info">
              <el-avatar :size="32" :src="row.avatar" :icon="UserFilled" />
              <div class="user-details">
                <span class="username">{{ row.username }}</span>
                <el-tag v-if="row.isAdmin" type="danger" size="small" effect="dark">管理员</el-tag>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="200">
          <template #default="{ row }">
            <div class="email-info">
              <el-icon><Message /></el-icon>
              <span>{{ row.email }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="level" label="等级" width="100" align="center">
          <template #default="{ row }">
            <div class="level-badge">
              <el-icon><Trophy /></el-icon>
              <span>Lv.{{ row.level }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="isAdmin" label="管理员权限" width="120" align="center">
          <template #default="{ row }">
            <el-switch
              v-model="row.isAdmin"
              @change="handleAdminChange(row.id, row.isAdmin)"
              active-color="#13ce66"
              inactive-color="#ff4949"
            />
          </template>
        </el-table-column>
        <el-table-column prop="registerDate" label="注册日期" width="150">
          <template #default="{ row }">
            <div class="date-info">
              <el-icon><Calendar /></el-icon>
              <span>{{ row.registerDate }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="lastLogin" label="最后登录" width="150">
          <template #default="{ row }">
            <div class="date-info">
              <el-icon><Timer /></el-icon>
              <span>{{ row.lastLogin }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right" align="center">
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
      <el-empty v-if="userList.length === 0 && !loading" description="暂无用户数据" />

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
  User,
  Plus,
  Search,
  Refresh,
  Edit,
  Delete,
  TrendCharts,
  Star,
  List,
  UserFilled,
  Message,
  Trophy,
  Calendar,
  Timer,
  Management
} from '@element-plus/icons-vue';
import * as userApi from '../../api/user';

const router = useRouter();
const loading = ref(false);
const userList = ref<any[]>([]);
const total = ref(0);
const currentPage = ref(1);
const pageSize = ref(10);
const searchKeyword = ref('');

// 统计数据
const recentCount = ref(12);
const adminCount = ref(5);
const activeCount = ref(89);

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

const navigateToAdd = () => {
  router.push('/user/add');
};

const navigateToEdit = (id: number) => {
  router.push(`/user/edit/${id}`);
};

const handleSearch = () => {
  currentPage.value = 1;
  fetchUserList();
};

const refreshData = () => {
  searchKeyword.value = '';
  currentPage.value = 1;
  fetchUserList();
  ElMessage.success('数据已刷新');
};

const handleSizeChange = (size: number) => {
  pageSize.value = size;
  fetchUserList();
};

const handleCurrentChange = (page: number) => {
  currentPage.value = page;
  fetchUserList();
};

const handleAdminChange = (id: number, isAdmin: boolean) => {
  ElMessage.success(`用户 ${id} 的管理员权限已${isAdmin ? '开启' : '关闭'}`);
  // 实际项目中会调用API更新用户权限
};

const handleDelete = (id: number) => {
  ElMessageBox.confirm('确定要删除这个用户吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      // 实际项目中会调用API删除用户
      ElMessage.success('删除成功');
      fetchUserList();
    } catch (error) {
      ElMessage.error('删除失败，请重试');
    }
  }).catch(() => {
    // 取消删除
  });
};

const fetchUserList = async () => {
  loading.value = true;
  try {
    const data: any = await userApi.getAllUsers();
    userList.value = data;
    total.value = data.length;
  } catch (error) {
    ElMessage.error('获取用户列表失败');
    console.error(error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  fetchUserList();
});
</script>

<style scoped>
.user-list {
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

.stat-icon.blue {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.stat-icon.green {
  background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
}

.stat-icon.purple {
  background: linear-gradient(135deg, #a855f7 0%, #ec4899 100%);
}

.stat-icon.orange {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
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
  padding: 2px 8px;
  background: #f4f4f5;
  border-radius: 4px;
  font-size: 12px;
  color: #606266;
  font-weight: 500;
}

/* 用户信息 */
.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-details {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.username {
  font-weight: 500;
  color: #303133;
}

/* 邮箱信息 */
.email-info {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #606266;
}

/* 等级徽章 */
.level-badge {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 10px;
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
  color: white;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 600;
}

/* 日期信息 */
.date-info {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #606266;
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
  .user-list {
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

  .user-info {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
}
</style>
