<template>
  <el-card shadow="never" class="mt-8px">
    <template #header>
      <div class="h-3 flex justify-between">
        <span>备件库存统计</span>
        <el-button type="text" size="small" @click="refreshData" :loading="loading">
          刷新
        </el-button>
      </div>
    </template>
    <el-skeleton :loading="loading" animated>
      <div v-if="statistics">
        <el-row :gutter="12">
          <el-col :span="12">
            <div class="text-center p-3 bg-blue-50 rounded-lg">
              <div class="text-20px font-bold text-blue-600">{{ statistics.overview?.totalSparePartTypes || 0 }}</div>
              <div class="text-12px text-gray-500 mt-1">备件种类</div>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="text-center p-3 bg-green-50 rounded-lg">
              <div class="text-20px font-bold text-green-600">{{ formatNumber(statistics.overview?.totalStockQuantity) }}</div>
              <div class="text-12px text-gray-500 mt-1">总库存</div>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="12" class="mt-3">
          <el-col :span="12">
            <div class="text-center p-3 bg-orange-50 rounded-lg">
              <div class="text-20px font-bold text-orange-600">{{ statistics.alertStatistics?.zeroStockCount || 0 }}</div>
              <div class="text-12px text-gray-500 mt-1">零库存</div>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="text-center p-3 bg-red-50 rounded-lg">
              <div class="text-20px font-bold text-red-600">{{ statistics.alertStatistics?.lowStockCount || 0 }}</div>
              <div class="text-12px text-gray-500 mt-1">低库存</div>
            </div>
          </el-col>
        </el-row>
        <div class="mt-3 text-center">
          <div class="text-14px text-gray-600">库存健康度</div>
          <div class="text-24px font-bold" :class="getHealthScoreColor(statistics.overview?.healthScore)">
            {{ statistics.overview?.healthScore || 0 }}%
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
import { ref, onMounted } from 'vue'
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

// 获取备件统计数据
const getSparePartStatistics = async () => {
  try {
    loading.value = true
    console.log('开始获取备件统计数据...')
    const response = await SparePartInfoApi.getStockStatistics()
    console.log('备件统计数据完整响应:', response)
    console.log('响应类型:', typeof response)
    console.log('响应结构:', Object.keys(response))

    // 检查响应结构
    if (response && response.data) {
      console.log('response.data 存在:', response.data)
      statistics.value = response.data
    } else if (response && response.overview) { // Fallback for direct response
      console.log('response 直接包含数据:', response)
      statistics.value = response
    } else {
      console.log('响应结构异常，使用默认数据')
      statistics.value = {
        overview: { totalSparePartTypes: 0, totalStockQuantity: 0, totalStockValue: 0, averageTurnoverRate: 0, healthScore: 0 },
        alertStatistics: { lowStockCount: 0, zeroStockCount: 0, overStockCount: 0, stagnantStockCount: 0 }
      }
    }
    console.log('备件统计数据设置成功:', statistics.value)
  } catch (error) {
    console.error('获取备件统计数据失败:', error)
    statistics.value = {
      overview: { totalSparePartTypes: 0, totalStockQuantity: 0, totalStockValue: 0, averageTurnoverRate: 0, healthScore: 0 },
      alertStatistics: { lowStockCount: 0, zeroStockCount: 0, overStockCount: 0, stagnantStockCount: 0 }
    }
  } finally {
    loading.value = false
  }
}

// 刷新数据
const refreshData = () => {
  getSparePartStatistics()
}

// 格式化数字
const formatNumber = (num: number) => {
  if (!num) return '0'
  return num.toString()
}

// 获取健康度颜色
const getHealthScoreColor = (score: number) => {
  if (!score) return 'text-gray-500'
  if (score >= 80) return 'text-green-600'
  if (score >= 60) return 'text-yellow-600'
  return 'text-red-600'
}

// 暴露方法给父组件
defineExpose({
  refreshData,
  getSparePartStatistics
})

// 组件挂载时获取数据
onMounted(() => {
  getSparePartStatistics()
  
  // 如果启用自动刷新，设置定时器
  if (props.autoRefresh) {
    setInterval(() => {
      getSparePartStatistics()
    }, props.refreshInterval)
  }
})
</script>

<style scoped>
.el-card {
  margin-bottom: 20px;
}
</style>
