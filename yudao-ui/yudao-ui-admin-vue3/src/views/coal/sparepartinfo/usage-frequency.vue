<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="100px"
    >
      <el-form-item label="频率等级" prop="frequencyLevel">
        <el-select v-model="queryParams.frequencyLevel" placeholder="请选择频率等级" clearable>
          <el-option label="高频" value="高频" />
          <el-option label="中频" value="中频" />
          <el-option label="低频" value="低频" />
          <el-option label="未使用" value="未使用" />
        </el-select>
      </el-form-item>
      <el-form-item label="使用趋势" prop="usageTrend">
        <el-select v-model="queryParams.usageTrend" placeholder="请选择使用趋势" clearable>
          <el-option label="上升" value="上升" />
          <el-option label="稳定" value="稳定" />
          <el-option label="下降" value="下降" />
          <el-option label="数据不足" value="数据不足" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button type="success" @click="handleRefresh" :loading="loading">
          <Icon icon="ep:refresh" class="mr-5px" /> 刷新数据
        </el-button>
      </el-form-item>
    </el-form>

    <!-- 统计概览 -->
    <el-row :gutter="20" class="mb-20px">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="text-center">
            <div class="text-24px font-bold text-blue-600">{{ statistics.highFrequencyCount || 0 }}</div>
            <div class="text-14px text-gray-500 mt-1">高频使用备件</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="text-center">
            <div class="text-24px font-bold text-green-600">{{ statistics.mediumFrequencyCount || 0 }}</div>
            <div class="text-14px text-gray-500 mt-1">中频使用备件</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="text-center">
            <div class="text-24px font-bold text-orange-600">{{ statistics.lowFrequencyCount || 0 }}</div>
            <div class="text-14px text-gray-500 mt-1">低频使用备件</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="text-center">
            <div class="text-24px font-bold text-red-600">{{ statistics.unusedCount || 0 }}</div>
            <div class="text-14px text-gray-500 mt-1">未使用备件</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 使用频率分析表格 -->
    <el-table v-loading="loading" :data="filteredList" stripe>
      <el-table-column label="备件名称" align="center" prop="sparePartName" width="200" />
      <el-table-column label="使用次数" align="center" prop="usageCount" width="100">
        <template #default="scope">
          <el-tag :type="getUsageCountTagType(scope.row.usageCount)">
            {{ scope.row.usageCount }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="频率等级" align="center" prop="frequencyLevel" width="100">
        <template #default="scope">
          <el-tag :type="getFrequencyLevelTagType(scope.row.frequencyLevel)">
            {{ scope.row.frequencyLevel }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="最后使用时间" align="center" prop="lastUsageDate" width="180">
        <template #default="scope">
          {{ scope.row.lastUsageDate ? formatDate(scope.row.lastUsageDate) : '无' }}
        </template>
      </el-table-column>
      <el-table-column label="平均使用间隔(天)" align="center" prop="averageUsageInterval" width="150">
        <template #default="scope">
          {{ scope.row.averageUsageInterval ? scope.row.averageUsageInterval.toFixed(1) : '0' }}
        </template>
      </el-table-column>
      <el-table-column label="使用趋势" align="center" prop="usageTrend" width="100">
        <template #default="scope">
          <el-tag :type="getUsageTrendTagType(scope.row.usageTrend)">
            {{ scope.row.usageTrend }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="120" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openDetailDialog(scope.row)"
          >
            查看详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 详情对话框 -->
    <el-dialog v-model="detailDialogVisible" title="备件使用频率详情" width="600px">
      <div v-if="selectedItem">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="备件名称">{{ selectedItem.sparePartName }}</el-descriptions-item>
          <el-descriptions-item label="备件ID">{{ selectedItem.sparePartId }}</el-descriptions-item>
          <el-descriptions-item label="使用次数">{{ selectedItem.usageCount }}</el-descriptions-item>
          <el-descriptions-item label="频率等级">
            <el-tag :type="getFrequencyLevelTagType(selectedItem.frequencyLevel)">
              {{ selectedItem.frequencyLevel }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="最后使用时间">
            {{ selectedItem.lastUsageDate ? formatDate(selectedItem.lastUsageDate) : '无' }}
          </el-descriptions-item>
          <el-descriptions-item label="平均使用间隔">
            {{ selectedItem.averageUsageInterval ? selectedItem.averageUsageInterval.toFixed(1) + '天' : '0天' }}
          </el-descriptions-item>
          <el-descriptions-item label="使用趋势">
            <el-tag :type="getUsageTrendTagType(selectedItem.usageTrend)">
              {{ selectedItem.usageTrend }}
            </el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </el-dialog>
  </ContentWrap>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { SparePartInfoApi } from '@/api/coal/sparepartinfo'
import { dateFormatter } from '@/utils/formatTime'

defineOptions({ name: 'SparePartUsageFrequency' })

// 响应式数据
const loading = ref(false)
const statistics = ref({
  highFrequencyCount: 0,
  mediumFrequencyCount: 0,
  lowFrequencyCount: 0,
  unusedCount: 0,
  frequencyAnalysis: []
})

const queryParams = reactive({
  frequencyLevel: '',
  usageTrend: ''
})

const detailDialogVisible = ref(false)
const selectedItem = ref(null)

// 计算属性 - 过滤后的列表
const filteredList = computed(() => {
  let list = statistics.value.frequencyAnalysis || []
  
  if (queryParams.frequencyLevel) {
    list = list.filter(item => item.frequencyLevel === queryParams.frequencyLevel)
  }
  
  if (queryParams.usageTrend) {
    list = list.filter(item => item.usageTrend === queryParams.usageTrend)
  }
  
  return list
})

// 获取使用频率分析数据
const getUsageFrequencyAnalysis = async () => {
  try {
    loading.value = true
    const response = await SparePartInfoApi.getUsageFrequencyAnalysis()
    if (response && response.data) {
      statistics.value = response.data
    }
  } catch (error) {
    console.error('获取使用频率分析失败:', error)
    ElMessage.error('获取使用频率分析失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleQuery = () => {
  // 过滤逻辑在计算属性中处理
}

// 重置搜索
const resetQuery = () => {
  queryParams.frequencyLevel = ''
  queryParams.usageTrend = ''
}

// 刷新数据
const handleRefresh = () => {
  getUsageFrequencyAnalysis()
}

// 打开详情对话框
const openDetailDialog = (item) => {
  selectedItem.value = item
  detailDialogVisible.value = true
}

// 格式化日期
const formatDate = (date) => {
  return dateFormatter(new Date(date))
}

// 获取使用次数标签类型
const getUsageCountTagType = (count) => {
  if (count >= 10) return 'danger'
  if (count >= 5) return 'warning'
  if (count >= 1) return 'success'
  return 'info'
}

// 获取频率等级标签类型
const getFrequencyLevelTagType = (level) => {
  switch (level) {
    case '高频': return 'danger'
    case '中频': return 'warning'
    case '低频': return 'success'
    case '未使用': return 'info'
    default: return 'info'
  }
}

// 获取使用趋势标签类型
const getUsageTrendTagType = (trend) => {
  switch (trend) {
    case '上升': return 'success'
    case '稳定': return 'primary'
    case '下降': return 'warning'
    case '数据不足': return 'info'
    default: return 'info'
  }
}

// 组件挂载时获取数据
onMounted(() => {
  getUsageFrequencyAnalysis()
})
</script>

<style scoped>
.el-card {
  margin-bottom: 20px;
}
</style>
