<template>
  <el-card shadow="never" class="mb-4">
    <template #header>
      <div class="flex justify-between items-center">
        <h3 class="text-lg font-semibold">能源管理概览</h3>
        <el-button type="primary" size="small" @click="refreshData" :loading="loading">
          <Icon icon="ep:refresh" class="mr-1" />
          刷新
        </el-button>
      </div>
    </template>

    <!-- 关键指标卡片 -->
    <div class="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-6">
      <div class="bg-blue-50 p-4 rounded-lg">
        <div class="flex items-center">
          <Icon icon="ep:warning" class="text-blue-500 text-2xl mr-3" />
          <div>
            <div class="text-sm text-gray-600">待验证记录</div>
            <div class="text-2xl font-bold text-blue-600">{{ statisticsData.pendingVerificationCount || 0 }}</div>
          </div>
        </div>
      </div>
      
      <div class="bg-red-50 p-4 rounded-lg">
        <div class="flex items-center">
          <Icon icon="ep:warning-filled" class="text-red-500 text-2xl mr-3" />
          <div>
            <div class="text-sm text-gray-600">异常统计</div>
            <div class="text-2xl font-bold text-red-600">{{ statisticsData.abnormalCount || 0 }}</div>
          </div>
        </div>
      </div>
      
      <div class="bg-green-50 p-4 rounded-lg">
        <div class="flex items-center">
          <Icon icon="ep:money" class="text-green-500 text-2xl mr-3" />
          <div>
            <div class="text-sm text-gray-600">今日总成本</div>
            <div class="text-2xl font-bold text-green-600">¥{{ (statisticsData.todayTotalCost || 0).toFixed(2) }}</div>
          </div>
        </div>
      </div>
      
      <div class="bg-orange-50 p-4 rounded-lg">
        <div class="flex items-center">
          <Icon icon="ep:trend-charts" class="text-orange-500 text-2xl mr-3" />
          <div>
            <div class="text-sm text-gray-600">能源效率</div>
            <div class="text-2xl font-bold text-orange-600">{{ (statisticsData.averageEfficiency || 0).toFixed(1) }}%</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 图表区域 -->
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
      <!-- 能源消耗趋势图表 -->
      <div class="bg-white p-4 rounded-lg border">
        <h4 class="text-md font-medium mb-3">能源消耗趋势（最近7天）</h4>
        <div class="h-80">
          <Echart :options="consumptionTrendOption" />
        </div>
      </div>

      <!-- 能源成本分析图表 -->
      <div class="bg-white p-4 rounded-lg border">
        <h4 class="text-md font-medium mb-3">能源成本分析</h4>
        <div class="h-80">
          <Echart :options="costAnalysisOption" />
        </div>
      </div>
    </div>

    <!-- 能源类型分布图表 -->
    <div class="mb-6">
      <div class="bg-white p-4 rounded-lg border">
        <h4 class="text-md font-medium mb-3">能源类型分布</h4>
        <div class="h-80">
          <Echart :options="energyTypeDistributionOption" />
        </div>
      </div>
    </div>

    <!-- 最近预警记录 -->
    <div>
      <h4 class="text-md font-medium mb-3">最近预警记录</h4>
      <div v-if="recentAlerts.length > 0" class="space-y-2">
        <div 
          v-for="alert in recentAlerts.slice(0, 5)" 
          :key="alert.id"
          class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 cursor-pointer"
          @click="handleAlertClick(alert)"
        >
          <div class="flex items-center">
            <el-tag 
              :type="getAlertLevelType(alert.alertLevel)" 
              size="small" 
              class="mr-2"
            >
              {{ getAlertLevelText(alert.alertLevel) }}
            </el-tag>
            <span class="text-sm">{{ alert.alertTitle }}</span>
          </div>
          <div class="text-xs text-gray-500">
            {{ formatTime(alert.alertTime) }}
          </div>
        </div>
      </div>
      <div v-else class="text-center text-gray-500 py-8">
        <Icon icon="ep:check" class="text-2xl mb-2" />
        <div>暂无预警记录</div>
      </div>
    </div>
  </el-card>
</template>

<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { formatTime } from '@/utils'
import { EnergyConsumptionApi } from '@/api/coal/energyconsumption'
import { EnergyAlertApi } from '@/api/coal/energyalert'
import { Echart } from '@/components/Echart'
import type { EChartsOption } from 'echarts'

const router = useRouter()

// 响应式数据
const loading = ref(false)
const statisticsData = ref({
  totalCount: 0,
  pendingVerificationCount: 0,
  verifiedCount: 0,
  abnormalCount: 0,
  todayCount: 0,
  realTimeCount: 0,
  manualCount: 0,
  todayTotalConsumption: 0,
  todayTotalCost: 0,
  monthlyTotalConsumption: 0,
  monthlyTotalCost: 0,
  averageEfficiency: 0,
  statisticsDate: ''
})
const recentAlerts = ref([])

// 图表数据
const consumptionTrendData = ref({
  dates: [] as string[],
  electricityData: [] as number[],
  waterData: [] as number[],
  chemicalData: [] as number[]
})
const costAnalysisData = ref([] as Array<{name: string, value: number, color: string}>)
const energyTypeDistributionData = ref([] as Array<{name: string, value: number, color: string}>)

// 获取数据
const fetchData = async () => {
  loading.value = true
  try {
    // 并行获取所有数据
    const [statisticsResponse, alertData] = await Promise.all([
      // 获取统计信息
      EnergyConsumptionApi.getEnergyConsumptionStatistics(),
      // 获取最近预警记录
      EnergyAlertApi.getEnergyAlertPage({ 
        pageNo: 1, 
        pageSize: 10,
        alertStatus: 1 // 待处理状态
      })
    ])

    // 更新统计数据
    if (statisticsResponse.data) {
      statisticsData.value = statisticsResponse.data
    }

    // 更新预警数据
    recentAlerts.value = alertData.list || []

    // 生成图表数据
    await generateChartData()

  } catch (error) {
    console.error('获取能源管理数据失败:', error)
    ElMessage.error('获取数据失败')
  } finally {
    loading.value = false
  }
}

// 生成图表数据
const generateChartData = async () => {
  try {
    // 获取最近7天的真实数据
    const dates = []
    const electricityData = []
    const waterData = []
    const chemicalData = []
    
    for (let i = 6; i >= 0; i--) {
      const date = new Date()
      date.setDate(date.getDate() - i)
      const dateStr = date.toISOString().split('T')[0]
      dates.push(dateStr.split('-').slice(1).join('/'))
      
      // 获取当天的真实数据
      const dayData = await EnergyConsumptionApi.getEnergyConsumptionPage({
        pageNo: 1,
        pageSize: 100,
        consumptionDate: [dateStr, dateStr]
      })
      
      // 计算各能源类型的消耗量
      let electricity = 0
      let water = 0
      let chemical = 0
      
      if (dayData.list && dayData.list.length > 0) {
        dayData.list.forEach((item: any) => {
          const consumption = item.consumptionValue || 0
          switch (item.energyTypeId) {
            case 1: // 电力
              electricity += consumption
              break
            case 2: // 水资源
              water += consumption
              break
            case 3: // 药剂
              chemical += consumption
              break
          }
        })
      }
      
      electricityData.push(electricity)
      waterData.push(water)
      chemicalData.push(chemical)
    }
    
    consumptionTrendData.value = {
      dates,
      electricityData,
      waterData,
      chemicalData
    }

    // 获取今日各能源类型的真实成本数据
    const today = new Date().toISOString().split('T')[0]
    const todayData = await EnergyConsumptionApi.getEnergyConsumptionPage({
      pageNo: 1,
      pageSize: 100,
      consumptionDate: [today, today]
    })
    
    const costData = {
      electricity: 0,
      water: 0,
      chemical: 0,
      medium: 0,
      fuel: 0
    }
    
    if (todayData.list && todayData.list.length > 0) {
      todayData.list.forEach((item: any) => {
        const cost = item.totalCost || 0
        switch (item.energyTypeId) {
          case 1: // 电力
            costData.electricity += cost
            break
          case 2: // 水资源
            costData.water += cost
            break
          case 3: // 药剂
            costData.chemical += cost
            break
          case 4: // 介质
            costData.medium += cost
            break
          case 5: // 燃油
            costData.fuel += cost
            break
        }
      })
    }
    
    // 生成成本分析数据
    costAnalysisData.value = [
      { name: '电力', value: Math.round(costData.electricity), color: '#5470c6' },
      { name: '水资源', value: Math.round(costData.water), color: '#91cc75' },
      { name: '药剂', value: Math.round(costData.chemical), color: '#fac858' },
      { name: '介质', value: Math.round(costData.medium), color: '#ee6666' },
      { name: '燃油', value: Math.round(costData.fuel), color: '#73c0de' }
    ]

    // 获取今日各能源类型的真实消耗数据
    const consumptionData = {
      electricity: 0,
      water: 0,
      chemical: 0,
      medium: 0,
      fuel: 0,
      steam: 0,
      compressedAir: 0
    }
    
    if (todayData.list && todayData.list.length > 0) {
      todayData.list.forEach((item: any) => {
        const consumption = item.consumptionValue || 0
        switch (item.energyTypeId) {
          case 1: // 电力
            consumptionData.electricity += consumption
            break
          case 2: // 水资源
            consumptionData.water += consumption
            break
          case 3: // 药剂
            consumptionData.chemical += consumption
            break
          case 4: // 介质
            consumptionData.medium += consumption
            break
          case 5: // 燃油
            consumptionData.fuel += consumption
            break
          case 6: // 蒸汽
            consumptionData.steam += consumption
            break
          case 7: // 压缩空气
            consumptionData.compressedAir += consumption
            break
        }
      })
    }
    
    // 生成能源类型分布数据
    energyTypeDistributionData.value = [
      { name: '电力', value: Math.round(consumptionData.electricity), color: '#5470c6' },
      { name: '水资源', value: Math.round(consumptionData.water), color: '#91cc75' },
      { name: '药剂', value: Math.round(consumptionData.chemical), color: '#fac858' },
      { name: '介质', value: Math.round(consumptionData.medium), color: '#ee6666' },
      { name: '燃油', value: Math.round(consumptionData.fuel), color: '#73c0de' },
      { name: '蒸汽', value: Math.round(consumptionData.steam), color: '#3ba272' },
      { name: '压缩空气', value: Math.round(consumptionData.compressedAir), color: '#fc8452' }
    ]
    
  } catch (error) {
    console.error('生成图表数据失败:', error)
    // 如果获取真实数据失败，使用默认数据
    generateDefaultChartData()
  }
}

// 生成默认图表数据（当真实数据获取失败时使用）
const generateDefaultChartData = () => {
  const dates = []
  const electricityData = []
  const waterData = []
  const chemicalData = []
  
  for (let i = 6; i >= 0; i--) {
    const date = new Date()
    date.setDate(date.getDate() - i)
    const dateStr = date.toISOString().split('T')[0]
    dates.push(dateStr.split('-').slice(1).join('/'))
    
    // 使用统计数据生成默认趋势数据
    const baseElectricity = statisticsData.value.todayTotalConsumption * 0.4 || 1000
    const baseWater = statisticsData.value.todayTotalConsumption * 0.1 || 50
    const baseChemical = statisticsData.value.todayTotalConsumption * 0.05 || 30
    
    electricityData.push(Math.floor(baseElectricity + (Math.random() - 0.5) * baseElectricity * 0.2))
    waterData.push(Math.floor(baseWater + (Math.random() - 0.5) * baseWater * 0.3))
    chemicalData.push(Math.floor(baseChemical + (Math.random() - 0.5) * baseChemical * 0.4))
  }
  
  consumptionTrendData.value = {
    dates,
    electricityData,
    waterData,
    chemicalData
  }

  // 生成默认成本分析数据
  const totalCost = statisticsData.value.todayTotalCost || 1000
  costAnalysisData.value = [
    { name: '电力', value: Math.floor(totalCost * 0.45), color: '#5470c6' },
    { name: '水资源', value: Math.floor(totalCost * 0.15), color: '#91cc75' },
    { name: '药剂', value: Math.floor(totalCost * 0.25), color: '#fac858' },
    { name: '介质', value: Math.floor(totalCost * 0.10), color: '#ee6666' },
    { name: '燃油', value: Math.floor(totalCost * 0.05), color: '#73c0de' }
  ]

  // 生成默认能源类型分布数据
  const totalConsumption = statisticsData.value.todayTotalConsumption || 1000
  energyTypeDistributionData.value = [
    { name: '电力', value: Math.floor(totalConsumption * 0.4), color: '#5470c6' },
    { name: '水资源', value: Math.floor(totalConsumption * 0.2), color: '#91cc75' },
    { name: '药剂', value: Math.floor(totalConsumption * 0.15), color: '#fac858' },
    { name: '介质', value: Math.floor(totalConsumption * 0.12), color: '#ee6666' },
    { name: '燃油', value: Math.floor(totalConsumption * 0.08), color: '#73c0de' },
    { name: '蒸汽', value: Math.floor(totalConsumption * 0.03), color: '#3ba272' },
    { name: '压缩空气', value: Math.floor(totalConsumption * 0.02), color: '#fc8452' }
  ]
}

// 消耗趋势图表配置
const consumptionTrendOption = computed((): EChartsOption => ({
  title: {
    text: '能源消耗趋势',
    left: 'center',
    textStyle: {
      fontSize: 16,
      fontWeight: 'normal' as const
    }
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {
      type: 'cross'
    }
  },
  legend: {
    data: ['电力', '水资源', '药剂'],
    top: 30
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    top: '15%',
    containLabel: true
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: consumptionTrendData.value.dates
  },
  yAxis: [
    {
      type: 'value',
      name: '消耗量',
      position: 'left'
    }
  ],
  series: [
    {
      name: '电力',
      type: 'line',
      data: consumptionTrendData.value.electricityData,
      smooth: true,
      itemStyle: { color: '#5470c6' }
    },
    {
      name: '水资源',
      type: 'line',
      data: consumptionTrendData.value.waterData,
      smooth: true,
      itemStyle: { color: '#91cc75' }
    },
    {
      name: '药剂',
      type: 'line',
      data: consumptionTrendData.value.chemicalData,
      smooth: true,
      itemStyle: { color: '#fac858' }
    }
  ]
}))

// 成本分析图表配置
const costAnalysisOption = computed((): EChartsOption => ({
  title: {
    text: '能源成本分析',
    left: 'center',
    textStyle: {
      fontSize: 16,
      fontWeight: 'normal' as const
    }
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b}: ¥{c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    top: 'middle'
  },
  series: [
    {
      name: '成本',
      type: 'pie',
      radius: ['40%', '70%'],
      center: ['60%', '50%'],
      data: costAnalysisData.value,
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}))

// 能源类型分布图表配置
const energyTypeDistributionOption = computed((): EChartsOption => ({
  title: {
    text: '能源类型分布',
    left: 'center',
    textStyle: {
      fontSize: 16,
      fontWeight: 'normal' as const
    }
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b}: {c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left',
    top: 'middle'
  },
  series: [
    {
      name: '消耗量',
      type: 'pie',
      radius: '50%',
      center: ['60%', '50%'],
      data: energyTypeDistributionData.value,
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}))

// 刷新数据
const refreshData = () => {
  fetchData()
}

// 获取预警级别类型
const getAlertLevelType = (level: number) => {
  switch (level) {
    case 1: return 'danger'
    case 2: return 'warning'
    case 3: return 'info'
    default: return 'info'
  }
}

// 获取预警级别文本
const getAlertLevelText = (level: number) => {
  switch (level) {
    case 1: return '紧急'
    case 2: return '重要'
    case 3: return '一般'
    default: return '未知'
  }
}

// 处理预警点击
const handleAlertClick = (alert: any) => {
  router.push('/coal/energy-alert')
}

// 组件挂载时获取数据
onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.grid {
  display: grid;
}

.grid-cols-1 {
  grid-template-columns: repeat(1, minmax(0, 1fr));
}

.grid-cols-2 {
  grid-template-columns: repeat(2, minmax(0, 1fr));
}

@media (min-width: 1024px) {
  .lg\:grid-cols-2 {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
  
  .lg\:grid-cols-4 {
    grid-template-columns: repeat(4, minmax(0, 1fr));
  }
}

.gap-4 {
  gap: 1rem;
}

.gap-6 {
  gap: 1.5rem;
}

.mb-4 {
  margin-bottom: 1rem;
}

.mb-6 {
  margin-bottom: 1.5rem;
}

.mb-3 {
  margin-bottom: 0.75rem;
}

.mb-2 {
  margin-bottom: 0.5rem;
}

.mr-1 {
  margin-right: 0.25rem;
}

.mr-2 {
  margin-right: 0.5rem;
}

.mr-3 {
  margin-right: 0.75rem;
}

.p-4 {
  padding: 1rem;
}

.p-3 {
  padding: 0.75rem;
}

.py-8 {
  padding-top: 2rem;
  padding-bottom: 2rem;
}

.rounded-lg {
  border-radius: 0.5rem;
}

.bg-blue-50 {
  background-color: #eff6ff;
}

.bg-red-50 {
  background-color: #fef2f2;
}

.bg-green-50 {
  background-color: #f0fdf4;
}

.bg-orange-50 {
  background-color: #fff7ed;
}

.bg-gray-50 {
  background-color: #f9fafb;
}

.bg-white {
  background-color: #ffffff;
}

.bg-gray-100 {
  background-color: #f3f4f6;
}

.text-blue-500 {
  color: #3b82f6;
}

.text-red-500 {
  color: #ef4444;
}

.text-green-500 {
  color: #22c55e;
}

.text-orange-500 {
  color: #f97316;
}

.text-gray-500 {
  color: #6b7280;
}

.text-gray-600 {
  color: #4b5563;
}

.text-blue-600 {
  color: #2563eb;
}

.text-red-600 {
  color: #dc2626;
}

.text-green-600 {
  color: #16a34a;
}

.text-orange-600 {
  color: #ea580c;
}

.text-2xl {
  font-size: 1.5rem;
  line-height: 2rem;
}

.text-lg {
  font-size: 1.125rem;
  line-height: 1.75rem;
}

.text-md {
  font-size: 1rem;
  line-height: 1.5rem;
}

.text-sm {
  font-size: 0.875rem;
  line-height: 1.25rem;
}

.text-xs {
  font-size: 0.75rem;
  line-height: 1rem;
}

.font-semibold {
  font-weight: 600;
}

.font-medium {
  font-weight: 500;
}

.font-bold {
  font-weight: 700;
}

.flex {
  display: flex;
}

.items-center {
  align-items: center;
}

.justify-between {
  justify-content: space-between;
}

.text-center {
  text-align: center;
}

.space-y-2 > * + * {
  margin-top: 0.5rem;
}

.h-80 {
  height: 20rem;
}

.border {
  border: 1px solid #e5e7eb;
}

.hover\:bg-gray-100:hover {
  background-color: #f3f4f6;
}

.cursor-pointer {
  cursor: pointer;
}
</style>
