<template>
  <el-card shadow="never" class="mt-8px">
    <template #header>
      <div class="h-3 flex justify-between">
        <div class="flex items-center">
          <span>能源管理业务流程</span>
          <el-tag type="info" size="small" class="ml-2">
            {{ getCurrentDate() }}
          </el-tag>
        </div>
        <el-button type="text" size="small" @click="refreshData" :loading="loading">
          刷新
        </el-button>
      </div>
    </template>
    
    <el-skeleton :loading="loading" animated>
      <div v-if="businessFlowData">
        <!-- 业务流程图 -->
        <div class="business-flow-container">
          <!-- 第一行：能源类型配置 -->
          <div class="flow-row">
            <div class="flow-node start-node" @click="goToEnergyType">
              <div class="node-icon">
                <Icon icon="ep:setting" class="text-20px" />
              </div>
              <div class="node-title">能源类型配置</div>
              <div class="node-count">{{ businessFlowData.energyTypeCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="success" v-if="businessFlowData.activeEnergyTypeCount > 0">
                  启用: {{ businessFlowData.activeEnergyTypeCount }}
                </el-tag>
                <el-tag size="small" type="primary" v-if="businessFlowData.realTimeTypeCount > 0">
                  实时采集: {{ businessFlowData.realTimeTypeCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向消耗记录 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第二行：能源消耗记录 -->
          <div class="flow-row">
            <div class="flow-node center-node" @click="goToEnergyConsumption">
              <div class="node-icon">
                <Icon icon="ep:document" class="text-20px" />
              </div>
              <div class="node-title">能源消耗记录</div>
              <div class="node-count">{{ businessFlowData.consumptionCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="warning" v-if="businessFlowData.pendingVerificationCount > 0">
                  待验证: {{ businessFlowData.pendingVerificationCount }}
                </el-tag>
                <el-tag size="small" type="info" v-if="businessFlowData.todayConsumptionCount > 0">
                  今日记录: {{ businessFlowData.todayConsumptionCount }}
                </el-tag>
                <el-tag size="small" type="success" v-if="businessFlowData.realTimeCount > 0">
                  实时采集: {{ businessFlowData.realTimeCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向统计分析 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第三行：能源统计分析 -->
          <div class="flow-row">
            <div class="flow-node center-node" @click="goToEnergyStatistics">
              <div class="node-icon">
                <Icon icon="ep:data-analysis" class="text-20px" />
              </div>
              <div class="node-title">能源统计分析</div>
              <div class="node-count">{{ businessFlowData.statisticsCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="success" v-if="businessFlowData.normalStatisticsCount > 0">
                  正常: {{ businessFlowData.normalStatisticsCount }}
                </el-tag>
                <el-tag size="small" type="warning" v-if="businessFlowData.abnormalStatisticsCount > 0">
                  异常: {{ businessFlowData.abnormalStatisticsCount }}
                </el-tag>
                <el-tag size="small" type="info" v-if="businessFlowData.todayStatisticsCount > 0">
                  今日统计: {{ businessFlowData.todayStatisticsCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向预警管理 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第四行：能源预警管理 -->
          <div class="flow-row">
            <div class="flow-node end-node" @click="goToEnergyAlert">
              <div class="node-icon">
                <Icon icon="ep:warning" class="text-20px" />
              </div>
              <div class="node-title">能源预警管理</div>
              <div class="node-count">{{ businessFlowData.alertCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="danger" v-if="businessFlowData.pendingAlertCount > 0">
                  待处理: {{ businessFlowData.pendingAlertCount }}
                </el-tag>
                <el-tag size="small" type="warning" v-if="businessFlowData.highLevelAlertCount > 0">
                  高级预警: {{ businessFlowData.highLevelAlertCount }}
                </el-tag>
                <el-tag size="small" type="info" v-if="businessFlowData.todayAlertCount > 0">
                  今日预警: {{ businessFlowData.todayAlertCount }}
                </el-tag>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 能源管理统计 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">能源管理统计</div>
          <el-row :gutter="12">
            <el-col :span="6">
              <div class="text-center p-2 bg-blue-50 rounded">
                <div class="text-16px font-bold text-blue-600">{{ formatNumber(businessFlowData.totalConsumption) }}</div>
                <div class="text-11px text-gray-500">总消耗量</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-green-50 rounded">
                <div class="text-16px font-bold text-green-600">{{ formatNumber(businessFlowData.totalCost) }}</div>
                <div class="text-11px text-gray-500">总成本(元)</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-orange-50 rounded">
                <div class="text-16px font-bold text-orange-600">{{ formatPercentage(businessFlowData.averageEfficiency) }}%</div>
                <div class="text-11px text-gray-500">平均效率</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-red-50 rounded">
                <div class="text-16px font-bold text-red-600">{{ businessFlowData.urgentAlertCount || 0 }}</div>
                <div class="text-11px text-gray-500">紧急预警</div>
              </div>
            </el-col>
          </el-row>
        </div>
        
        <!-- 今日能源概览 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">
            今日能源概览 
            <span class="text-12px text-blue-600 ml-2">（{{ getCurrentDate() }}）</span>
          </div>
          <el-row :gutter="8">
            <el-col :span="6">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ formatNumber(businessFlowData.todayElectricityConsumption) }}</div>
                <div class="text-10px text-gray-500">电力(kWh)</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ formatNumber(businessFlowData.todayWaterConsumption) }}</div>
                <div class="text-10px text-gray-500">水资源(m³)</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ formatNumber(businessFlowData.todayChemicalConsumption) }}</div>
                <div class="text-10px text-gray-500">药剂(kg)</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ formatNumber(businessFlowData.todayFuelConsumption) }}</div>
                <div class="text-10px text-gray-500">燃油(L)</div>
              </div>
            </el-col>
          </el-row>
        </div>
        
        <!-- 能源成本分析 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">能源成本分析</div>
          <el-row :gutter="8">
            <el-col :span="8">
              <div class="text-center p-2 bg-blue-50 rounded">
                <div class="text-14px font-bold text-blue-700">{{ formatNumber(businessFlowData.electricityCost) }}</div>
                <div class="text-10px text-gray-500">电力成本(元)</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="text-center p-2 bg-green-50 rounded">
                <div class="text-14px font-bold text-green-700">{{ formatNumber(businessFlowData.waterCost) }}</div>
                <div class="text-10px text-gray-500">水资源成本(元)</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="text-center p-2 bg-purple-50 rounded">
                <div class="text-14px font-bold text-purple-700">{{ formatNumber(businessFlowData.chemicalCost) }}</div>
                <div class="text-10px text-gray-500">药剂成本(元)</div>
              </div>
            </el-col>
          </el-row>
        </div>
        
        <!-- 快速操作 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">快速操作</div>
          <el-row :gutter="8">
            <el-col :span="6">
              <el-button size="small" type="primary" @click="goToEnergyConsumption">
                <Icon icon="ep:plus" class="mr-1" />
                新建记录
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="warning" @click="goToEnergyAlert">
                <Icon icon="ep:warning" class="mr-1" />
                预警处理
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="success" @click="goToEnergyStatistics">
                <Icon icon="ep:data-analysis" class="mr-1" />
                统计分析
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="info" @click="goToEnergyType">
                <Icon icon="ep:setting" class="mr-1" />
                类型配置
              </el-button>
            </el-col>
          </el-row>
        </div>
      </div>
      
      <div v-else class="text-center text-gray-400 py-8">
        暂无能源管理数据
      </div>
    </el-skeleton>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { EnergyTypeApi } from '@/api/coal/energytype'
import { EnergyConsumptionApi } from '@/api/coal/energyconsumption'
import { EnergyStatisticsApi } from '@/api/coal/energystatistics'
import { EnergyAlertApi } from '@/api/coal/energyalert'

// 定义业务数据接口
interface BusinessFlowData {
  // 各模块数量
  energyTypeCount: number
  consumptionCount: number
  statisticsCount: number
  alertCount: number
  
  // 状态统计
  activeEnergyTypeCount: number
  realTimeTypeCount: number
  pendingVerificationCount: number
  todayConsumptionCount: number
  realTimeCount: number
  normalStatisticsCount: number
  abnormalStatisticsCount: number
  todayStatisticsCount: number
  pendingAlertCount: number
  highLevelAlertCount: number
  todayAlertCount: number
  
  // 统计指标
  totalConsumption: number
  totalCost: number
  averageEfficiency: number
  urgentAlertCount: number
  
  // 今日概览
  todayElectricityConsumption: number
  todayWaterConsumption: number
  todayChemicalConsumption: number
  todayFuelConsumption: number
  
  // 成本分析
  electricityCost: number
  waterCost: number
  chemicalCost: number
}

// 定义组件属性
interface Props {
  autoRefresh?: boolean // 是否自动刷新
  refreshInterval?: number // 刷新间隔(毫秒)
}

const props = withDefaults(defineProps<Props>(), {
  autoRefresh: true,
  refreshInterval: 300000 // 5分钟
})

// 响应式数据
const loading = ref(false)
const businessFlowData = ref<BusinessFlowData | null>(null)
const refreshTimer = ref<NodeJS.Timeout | null>(null)

// 路由
const router = useRouter()

// 获取能源管理业务流程数据
const getBusinessFlowData = async () => {
  try {
    loading.value = true
    console.log('开始获取能源管理业务流程数据...')
    
    // 定义今日日期
    const now = new Date()
    const year = now.getFullYear()
    const month = String(now.getMonth() + 1).padStart(2, '0')
    const day = String(now.getDate()).padStart(2, '0')
    const today = `${year}-${month}-${day}`
    console.log('当前查询日期:', today)
    
    // 并行获取各模块数据
    const [
      energyTypeResponse,
      consumptionResponse,
      consumptionStatisticsResponse,
      statisticsResponse,
      alertResponse
    ] = await Promise.allSettled([
      EnergyTypeApi.getEnergyTypePage({ pageNo: 1, pageSize: 1 }),
      EnergyConsumptionApi.getEnergyConsumptionPage({ pageNo: 1, pageSize: 1 }),
      EnergyConsumptionApi.getEnergyConsumptionStatistics(),
      EnergyStatisticsApi.getEnergyStatisticsPage({ pageNo: 1, pageSize: 1 }),
      EnergyAlertApi.getEnergyAlertPage({ pageNo: 1, pageSize: 1 })
    ]).then(results => [
      results[0].status === 'fulfilled' ? results[0].value : { total: 0 },
      results[1].status === 'fulfilled' ? results[1].value : { total: 0 },
      results[2].status === 'fulfilled' ? results[2].value.data : null,
      results[3].status === 'fulfilled' ? results[3].value : { total: 0 },
      results[4].status === 'fulfilled' ? results[4].value : { total: 0 }
    ])
    
    // 获取启用的能源类型数量
    let activeEnergyTypeResponse = { total: 0 }
    try {
      activeEnergyTypeResponse = await EnergyTypeApi.getEnergyTypePage({
        pageNo: 1,
        pageSize: 1,
        status: 1 // 启用状态
      })
    } catch (error) {
      console.warn('获取启用能源类型数量失败:', error)
    }
    
    // 获取实时采集的能源类型数量
    let realTimeTypeResponse = { total: 0 }
    try {
      realTimeTypeResponse = await EnergyTypeApi.getEnergyTypePage({
        pageNo: 1,
        pageSize: 1,
        isRealTime: 1 // 实时采集
      })
    } catch (error) {
      console.warn('获取实时采集能源类型数量失败:', error)
    }
    
    // 使用统计接口获取的数据
    const pendingVerificationCount = consumptionStatisticsResponse?.pendingVerificationCount || 0
    const todayConsumptionCount = consumptionStatisticsResponse?.todayCount || 0
    const realTimeCount = consumptionStatisticsResponse?.realTimeCount || 0
    const todayTotalConsumption = consumptionStatisticsResponse?.todayTotalConsumption || 0
    const todayTotalCost = consumptionStatisticsResponse?.todayTotalCost || 0
    const averageEfficiency = consumptionStatisticsResponse?.averageEfficiency || 0
    
    // 获取正常的统计数据数量
    let normalStatisticsResponse = { total: 0 }
    try {
      normalStatisticsResponse = await EnergyStatisticsApi.getEnergyStatisticsPage({
        pageNo: 1,
        pageSize: 1,
        statisticsStatus: 1 // 正常状态
      })
    } catch (error) {
      console.warn('获取正常统计数据数量失败:', error)
    }
    
    // 获取异常的统计数据数量
    let abnormalStatisticsResponse = { total: 0 }
    try {
      abnormalStatisticsResponse = await EnergyStatisticsApi.getEnergyStatisticsPage({
        pageNo: 1,
        pageSize: 1,
        statisticsStatus: 2 // 异常状态
      })
    } catch (error) {
      console.warn('获取异常统计数据数量失败:', error)
    }
    
    // 获取今日统计数据数量
    let todayStatisticsResponse = { total: 0 }
    try {
      todayStatisticsResponse = await EnergyStatisticsApi.getEnergyStatisticsPage({
        pageNo: 1,
        pageSize: 1,
        statisticsDate: [today, today]
      })
    } catch (error) {
      console.warn('获取今日统计数据数量失败:', error)
    }
    
    // 获取待处理的预警数量
    let pendingAlertResponse = { total: 0 }
    try {
      pendingAlertResponse = await EnergyAlertApi.getEnergyAlertPage({
        pageNo: 1,
        pageSize: 1,
        alertStatus: 1 // 待处理状态
      })
    } catch (error) {
      console.warn('获取待处理预警数量失败:', error)
    }
    
    // 获取高级预警数量
    let highLevelAlertResponse = { total: 0 }
    try {
      highLevelAlertResponse = await EnergyAlertApi.getEnergyAlertPage({
        pageNo: 1,
        pageSize: 1,
        alertLevel: 3 // 高级预警
      })
    } catch (error) {
      console.warn('获取高级预警数量失败:', error)
    }
    
    // 获取今日预警数量
    let todayAlertResponse = { total: 0 }
    try {
      todayAlertResponse = await EnergyAlertApi.getEnergyAlertPage({
        pageNo: 1,
        pageSize: 1,
        alertTime: [today + ' 00:00:00', today + ' 23:59:59']
      })
    } catch (error) {
      console.warn('获取今日预警数量失败:', error)
    }
    
    // 获取紧急预警数量
    let urgentAlertResponse = { total: 0 }
    try {
      urgentAlertResponse = await EnergyAlertApi.getEnergyAlertPage({
        pageNo: 1,
        pageSize: 1,
        alertLevel: 3, // 高级预警
        alertStatus: 1 // 待处理状态
      })
    } catch (error) {
      console.warn('获取紧急预警数量失败:', error)
    }
    
    // 获取今日各能源类型的消耗数据（简化处理）
    let todayElectricityConsumption = 0
    let todayWaterConsumption = 0
    let todayChemicalConsumption = 0
    let todayFuelConsumption = 0
    let electricityCost = 0
    let waterCost = 0
    let chemicalCost = 0
    
    try {
      // 获取今日电力消耗
      const electricityResponse = await EnergyConsumptionApi.getEnergyConsumptionPage({
        pageNo: 1,
        pageSize: 10,
        energyTypeId: 1, // 电力
        consumptionDate: [today, today]
      })
      if (electricityResponse.list && electricityResponse.list.length > 0) {
        todayElectricityConsumption = electricityResponse.list.reduce((sum: number, item: any) => sum + (item.consumptionValue || 0), 0)
        electricityCost = electricityResponse.list.reduce((sum: number, item: any) => sum + (item.totalCost || 0), 0)
      }
      
      // 获取今日水资源消耗
      const waterResponse = await EnergyConsumptionApi.getEnergyConsumptionPage({
        pageNo: 1,
        pageSize: 10,
        energyTypeId: 2, // 水资源
        consumptionDate: [today, today]
      })
      if (waterResponse.list && waterResponse.list.length > 0) {
        todayWaterConsumption = waterResponse.list.reduce((sum: number, item: any) => sum + (item.consumptionValue || 0), 0)
        waterCost = waterResponse.list.reduce((sum: number, item: any) => sum + (item.totalCost || 0), 0)
      }
      
      // 获取今日药剂消耗
      const chemicalResponse = await EnergyConsumptionApi.getEnergyConsumptionPage({
        pageNo: 1,
        pageSize: 10,
        energyTypeId: 3, // 药剂
        consumptionDate: [today, today]
      })
      if (chemicalResponse.list && chemicalResponse.list.length > 0) {
        todayChemicalConsumption = chemicalResponse.list.reduce((sum: number, item: any) => sum + (item.consumptionValue || 0), 0)
        chemicalCost = chemicalResponse.list.reduce((sum: number, item: any) => sum + (item.totalCost || 0), 0)
      }
      
      // 获取今日燃油消耗
      const fuelResponse = await EnergyConsumptionApi.getEnergyConsumptionPage({
        pageNo: 1,
        pageSize: 10,
        energyTypeId: 5, // 燃油
        consumptionDate: [today, today]
      })
      if (fuelResponse.list && fuelResponse.list.length > 0) {
        todayFuelConsumption = fuelResponse.list.reduce((sum: number, item: any) => sum + (item.consumptionValue || 0), 0)
      }
    } catch (error) {
      console.warn('获取今日各能源类型消耗数据失败:', error)
    }
    
    // 使用统计接口的数据
    const totalConsumption = todayTotalConsumption
    const totalCost = todayTotalCost
    
    // 构建业务流程数据
    businessFlowData.value = {
      // 各模块数量
      energyTypeCount: energyTypeResponse.total || 0,
      consumptionCount: consumptionResponse.total || 0,
      statisticsCount: statisticsResponse.total || 0,
      alertCount: alertResponse.total || 0,
      
      // 状态统计
      activeEnergyTypeCount: activeEnergyTypeResponse.total || 0,
      realTimeTypeCount: realTimeTypeResponse.total || 0,
      pendingVerificationCount: pendingVerificationCount,
      todayConsumptionCount: todayConsumptionCount,
      realTimeCount: realTimeCount,
      normalStatisticsCount: normalStatisticsResponse.total || 0,
      abnormalStatisticsCount: abnormalStatisticsResponse.total || 0,
      todayStatisticsCount: todayStatisticsResponse.total || 0,
      pendingAlertCount: pendingAlertResponse.total || 0,
      highLevelAlertCount: highLevelAlertResponse.total || 0,
      todayAlertCount: todayAlertResponse.total || 0,
      
      // 统计指标
      totalConsumption: totalConsumption,
      totalCost: totalCost,
      averageEfficiency: averageEfficiency,
      urgentAlertCount: urgentAlertResponse.total || 0,
      
      // 今日概览
      todayElectricityConsumption: todayElectricityConsumption,
      todayWaterConsumption: todayWaterConsumption,
      todayChemicalConsumption: todayChemicalConsumption,
      todayFuelConsumption: todayFuelConsumption,
      
      // 成本分析
      electricityCost: electricityCost,
      waterCost: waterCost,
      chemicalCost: chemicalCost
    }
    
    console.log('能源管理业务流程数据获取成功:', businessFlowData.value)
  } catch (error) {
    console.error('获取能源管理业务流程数据失败:', error)
    ElMessage.error('获取能源管理业务流程数据失败')
    
    // 设置默认数据
    businessFlowData.value = {
      energyTypeCount: 0,
      consumptionCount: 0,
      statisticsCount: 0,
      alertCount: 0,
      activeEnergyTypeCount: 0,
      realTimeTypeCount: 0,
      pendingVerificationCount: 0,
      todayConsumptionCount: 0,
      realTimeCount: 0,
      normalStatisticsCount: 0,
      abnormalStatisticsCount: 0,
      todayStatisticsCount: 0,
      pendingAlertCount: 0,
      highLevelAlertCount: 0,
      todayAlertCount: 0,
      totalConsumption: 0,
      totalCost: 0,
      averageEfficiency: 0,
      urgentAlertCount: 0,
      todayElectricityConsumption: 0,
      todayWaterConsumption: 0,
      todayChemicalConsumption: 0,
      todayFuelConsumption: 0,
      electricityCost: 0,
      waterCost: 0,
      chemicalCost: 0
    }
  } finally {
    loading.value = false
  }
}

// 刷新数据
const refreshData = () => {
  getBusinessFlowData()
}

// 页面跳转方法
const goToEnergyType = () => {
  router.push({ name: 'EnergyType' })
}

const goToEnergyConsumption = () => {
  router.push({ name: 'EnergyConsumption' })
}

const goToEnergyStatistics = () => {
  router.push({ name: 'EnergyStatistics' })
}

const goToEnergyAlert = () => {
  router.push({ name: 'EnergyAlert' })
}

// 格式化数字
const formatNumber = (number: number) => {
  if (!number) return '0'
  return number.toLocaleString()
}

// 格式化百分比
const formatPercentage = (percentage: number) => {
  if (!percentage) return '0'
  return Math.round(percentage * 100) / 100
}

// 获取当前日期
const getCurrentDate = () => {
  const today = new Date()
  const year = today.getFullYear()
  const month = String(today.getMonth() + 1).padStart(2, '0')
  const day = String(today.getDate()).padStart(2, '0')
  const weekDays = ['日', '一', '二', '三', '四', '五', '六']
  const weekDay = weekDays[today.getDay()]
  return `${year}年${month}月${day}日 星期${weekDay}`
}

// 启动自动刷新
const startAutoRefresh = () => {
  if (props.autoRefresh && !refreshTimer.value) {
    refreshTimer.value = setInterval(() => {
      getBusinessFlowData()
    }, props.refreshInterval)
  }
}

// 停止自动刷新
const stopAutoRefresh = () => {
  if (refreshTimer.value) {
    clearInterval(refreshTimer.value)
    refreshTimer.value = null
  }
}

// 生命周期
onMounted(() => {
  getBusinessFlowData()
  startAutoRefresh()
})

onUnmounted(() => {
  stopAutoRefresh()
})
</script>

<style scoped>
.business-flow-container {
  padding: 16px;
}

.flow-row {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 16px;
}

.flow-node {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 140px;
  position: relative;
}

.flow-node:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.start-node {
  background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
  border: 2px solid #2196f3;
  color: #1976d2;
}

.center-node {
  background: linear-gradient(135deg, #fff3e0 0%, #ffcc80 100%);
  border: 2px solid #ff9800;
  color: #f57c00;
}

.end-node {
  background: linear-gradient(135deg, #ffebee 0%, #ffcdd2 100%);
  border: 2px solid #f44336;
  color: #d32f2f;
}

.node-icon {
  margin-bottom: 8px;
}

.node-title {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 4px;
}

.node-count {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 6px;
}

.node-status {
  margin-top: 4px;
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.flow-arrow-down {
  display: flex;
  justify-content: center;
  margin: 8px 0;
  color: #666;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .flow-row {
    flex-direction: column;
  }
  
  .flow-node {
    min-width: 120px;
    padding: 12px;
  }
  
  .node-title {
    font-size: 12px;
  }
  
  .node-count {
    font-size: 16px;
  }
}
</style>
