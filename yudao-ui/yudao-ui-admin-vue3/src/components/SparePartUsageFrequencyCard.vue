<template>
  <el-card shadow="never" class="mt-8px">
    <template #header>
      <div class="h-3 flex justify-between">
        <span>备件使用频率分析</span>
        <el-button type="text" size="small" @click="refreshData" :loading="loading">
          刷新
        </el-button>
      </div>
    </template>
    <el-skeleton :loading="loading" animated>
      <div v-if="statistics">
        <el-row :gutter="12">
          <el-col :span="6">
            <div class="text-center p-3 bg-blue-50 rounded-lg">
              <div class="text-20px font-bold text-blue-600">{{ statistics.highFrequencyCount || 0 }}</div>
              <div class="text-12px text-gray-500 mt-1">高频使用</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="text-center p-3 bg-green-50 rounded-lg">
              <div class="text-20px font-bold text-green-600">{{ statistics.mediumFrequencyCount || 0 }}</div>
              <div class="text-12px text-gray-500 mt-1">中频使用</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="text-center p-3 bg-orange-50 rounded-lg">
              <div class="text-20px font-bold text-orange-600">{{ statistics.lowFrequencyCount || 0 }}</div>
              <div class="text-12px text-gray-500 mt-1">低频使用</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="text-center p-3 bg-red-50 rounded-lg">
              <div class="text-20px font-bold text-red-600">{{ statistics.unusedCount || 0 }}</div>
              <div class="text-12px text-gray-500 mt-1">未使用</div>
            </div>
          </el-col>
        </el-row>
        
        <!-- 使用频率分布图表 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">使用频率分布</div>
          <div class="flex items-center justify-between text-12px">
            <div class="flex items-center">
              <div class="w-3 h-3 bg-blue-500 rounded mr-1"></div>
              <span>高频 {{ getPercentage(statistics.highFrequencyCount) }}%</span>
            </div>
            <div class="flex items-center">
              <div class="w-3 h-3 bg-green-500 rounded mr-1"></div>
              <span>中频 {{ getPercentage(statistics.mediumFrequencyCount) }}%</span>
            </div>
            <div class="flex items-center">
              <div class="w-3 h-3 bg-orange-500 rounded mr-1"></div>
              <span>低频 {{ getPercentage(statistics.lowFrequencyCount) }}%</span>
            </div>
            <div class="flex items-center">
              <div class="w-3 h-3 bg-red-500 rounded mr-1"></div>
              <span>未使用 {{ getPercentage(statistics.unusedCount) }}%</span>
            </div>
          </div>
        </div>

        <!-- 高频使用备件列表 -->
        <div v-if="highFrequencySpareParts.length > 0" class="mt-4">
          <div class="text-14px text-gray-600 mb-2">高频使用备件</div>
          <div class="space-y-1">
            <div 
              v-for="item in highFrequencySpareParts.slice(0, 3)" 
              :key="item.sparePartId"
              class="flex justify-between items-center text-12px p-2 bg-blue-50 rounded"
            >
              <span class="truncate">{{ item.sparePartName }}</span>
              <el-tag size="small" type="danger">{{ item.usageCount }}次</el-tag>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="text-center text-gray-400 py-8">
        暂无统计数据
      </div>
    </el-skeleton>
  </el-card>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { SparePartInfoApi } from '@/api/coal/sparepartinfo'

// 定义组件属性
interface Props {
  autoRefresh?: boolean // 是否自动刷新
  refreshInterval?: number // 刷新间隔(毫秒)
}

const props = withDefaults(defineProps<Props>(), {
  autoRefresh: false,
  refreshInterval: 300000 // 5分钟
})

// 响应式数据
const loading = ref(false)
const statistics = ref(null)

// 计算属性 - 高频使用备件
const highFrequencySpareParts = computed(() => {
  if (!statistics.value || !statistics.value.frequencyAnalysis) {
    return []
  }
  return statistics.value.frequencyAnalysis
    .filter(item => item.frequencyLevel === '高频')
    .sort((a, b) => b.usageCount - a.usageCount)
})

// 获取使用频率分析数据
const getUsageFrequencyAnalysis = async () => {
  try {
    loading.value = true
    console.log('开始获取使用频率分析数据...')
    const response = await SparePartInfoApi.getUsageFrequencyAnalysis()
    console.log('使用频率分析数据完整响应:', response)
    
    if (response && response.data) {
      statistics.value = response.data
    } else {
      console.log('响应结构异常，使用默认数据')
      statistics.value = {
        highFrequencyCount: 0,
        mediumFrequencyCount: 0,
        lowFrequencyCount: 0,
        unusedCount: 0,
        frequencyAnalysis: []
      }
    }
    console.log('使用频率分析数据设置成功:', statistics.value)
  } catch (error) {
    console.error('获取使用频率分析失败:', error)
    statistics.value = {
      highFrequencyCount: 0,
      mediumFrequencyCount: 0,
      lowFrequencyCount: 0,
      unusedCount: 0,
      frequencyAnalysis: []
    }
  } finally {
    loading.value = false
  }
}

// 刷新数据
const refreshData = () => {
  getUsageFrequencyAnalysis()
}

// 计算百分比
const getPercentage = (count: number) => {
  if (!statistics.value) return 0
  const total = (statistics.value.highFrequencyCount || 0) + 
                (statistics.value.mediumFrequencyCount || 0) + 
                (statistics.value.lowFrequencyCount || 0) + 
                (statistics.value.unusedCount || 0)
  if (total === 0) return 0
  return Math.round((count / total) * 100)
}

// 暴露方法给父组件
defineExpose({
  refreshData,
  getUsageFrequencyAnalysis
})

// 组件挂载时获取数据
onMounted(() => {
  getUsageFrequencyAnalysis()
  
  // 如果启用自动刷新，设置定时器
  if (props.autoRefresh) {
    setInterval(() => {
      getUsageFrequencyAnalysis()
    }, props.refreshInterval)
  }
})
</script>

<style scoped>
.el-card {
  margin-bottom: 20px;
}
</style>
