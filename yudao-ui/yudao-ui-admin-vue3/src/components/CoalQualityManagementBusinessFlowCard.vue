<template>
  <el-card shadow="never" class="mt-8px">
    <template #header>
      <div class="h-3 flex justify-between">
        <div class="flex items-center">
          <span>煤质管理业务流程</span>
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
          <!-- 第一行：检测项目和质量标准 -->
          <div class="flow-row">
            <div class="flow-node start-node" @click="goToQualityItem">
              <div class="node-icon">
                <Icon icon="ep:list" class="text-20px" />
              </div>
              <div class="node-title">检测项目</div>
              <div class="node-count">{{ businessFlowData.qualityItemCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="success" v-if="businessFlowData.activeItemCount > 0">
                  启用: {{ businessFlowData.activeItemCount }}
                </el-tag>
              </div>
            </div>
            
            <div class="flow-arrow">
              <Icon icon="ep:arrow-right" class="text-16px" />
            </div>
            
            <div class="flow-node start-node" @click="goToQualityStandard">
              <div class="node-icon">
                <Icon icon="ep:document" class="text-20px" />
              </div>
              <div class="node-title">质量标准</div>
              <div class="node-count">{{ businessFlowData.qualityStandardCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="primary" v-if="businessFlowData.activeStandardCount > 0">
                  生效: {{ businessFlowData.activeStandardCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向检测记录 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第二行：质量检测记录 -->
          <div class="flow-row">
            <div class="flow-node center-node" @click="goToQualityInspection">
              <div class="node-icon">
                <Icon icon="ep:search" class="text-20px" />
              </div>
              <div class="node-title">质量检测记录</div>
              <div class="node-count">{{ businessFlowData.inspectionCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="warning" v-if="businessFlowData.pendingInspectionCount > 0">
                  待检测: {{ businessFlowData.pendingInspectionCount }}
                </el-tag>
                <el-tag size="small" type="info" v-if="businessFlowData.todayInspectionCount > 0">
                  今日检测: {{ businessFlowData.todayInspectionCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向检测数据 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第三行：质量检测数据 -->
          <div class="flow-row">
            <div class="flow-node center-node" @click="goToQualityData">
              <div class="node-icon">
                <Icon icon="ep:data-analysis" class="text-20px" />
              </div>
              <div class="node-title">质量检测数据</div>
              <div class="node-count">{{ businessFlowData.qualityDataCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="success" v-if="businessFlowData.qualifiedDataCount > 0">
                  合格: {{ businessFlowData.qualifiedDataCount }}
                </el-tag>
                <el-tag size="small" type="danger" v-if="businessFlowData.unqualifiedDataCount > 0">
                  不合格: {{ businessFlowData.unqualifiedDataCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向质量预警 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第四行：质量预警记录 -->
          <div class="flow-row">
            <div class="flow-node end-node" @click="goToQualityAlert">
              <div class="node-icon">
                <Icon icon="ep:warning" class="text-20px" />
              </div>
              <div class="node-title">质量预警记录</div>
              <div class="node-count">{{ businessFlowData.alertCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="danger" v-if="businessFlowData.pendingAlertCount > 0">
                  待处理: {{ businessFlowData.pendingAlertCount }}
                </el-tag>
                <el-tag size="small" type="warning" v-if="businessFlowData.todayAlertCount > 0">
                  今日预警: {{ businessFlowData.todayAlertCount }}
                </el-tag>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 煤质管理统计 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">煤质管理统计</div>
          <el-row :gutter="12">
            <el-col :span="6">
              <div class="text-center p-2 bg-blue-50 rounded">
                <div class="text-16px font-bold text-blue-600">{{ businessFlowData.totalInspections || 0 }}</div>
                <div class="text-11px text-gray-500">总检测次数</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-green-50 rounded">
                <div class="text-16px font-bold text-green-600">{{ formatPercentage(businessFlowData.qualifiedRate) }}%</div>
                <div class="text-11px text-gray-500">合格率</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-orange-50 rounded">
                <div class="text-16px font-bold text-orange-600">{{ businessFlowData.monthlyInspections || 0 }}</div>
                <div class="text-11px text-gray-500">本月检测</div>
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
        
        <!-- 今日质量概览 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">
            今日质量概览 
            <span class="text-12px text-blue-600 ml-2">（{{ getCurrentDate() }}）</span>
          </div>
          <el-row :gutter="8">
            <el-col :span="8">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ businessFlowData.todayRawCoalInspections || 0 }}</div>
                <div class="text-10px text-gray-500">原煤检测</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ businessFlowData.todayCleanCoalInspections || 0 }}</div>
                <div class="text-10px text-gray-500">精煤检测</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ businessFlowData.todayMidCoalInspections || 0 }}</div>
                <div class="text-10px text-gray-500">中煤检测</div>
              </div>
            </el-col>
          </el-row>
        </div>
        
        <!-- 快速操作 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">快速操作</div>
          <el-row :gutter="8">
            <el-col :span="6">
              <el-button size="small" type="primary" @click="goToQualityInspection">
                <Icon icon="ep:plus" class="mr-1" />
                新建检测
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="warning" @click="goToQualityAlert">
                <Icon icon="ep:warning" class="mr-1" />
                预警处理
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="success" @click="goToQualityStandard">
                <Icon icon="ep:setting" class="mr-1" />
                标准管理
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="info" @click="goToQualityItem">
                <Icon icon="ep:list" class="mr-1" />
                项目管理
              </el-button>
            </el-col>
          </el-row>
        </div>
      </div>
      
      <div v-else class="text-center text-gray-400 py-8">
        暂无煤质管理数据
      </div>
    </el-skeleton>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { QualityItemApi } from '@/api/coal/qualityitem'
import { QualityStandardApi } from '@/api/coal/qualitystandard'
import { QualityInspectionApi, type QualityDataStatistics } from '@/api/coal/qualityinspection'
import { QualityAlertApi } from '@/api/coal/qualityalert'

// 定义业务数据接口
interface BusinessFlowData {
  qualityItemCount: number
  qualityStandardCount: number
  inspectionCount: number
  qualityDataCount: number
  alertCount: number
  activeItemCount: number
  activeStandardCount: number
  pendingInspectionCount: number
  todayInspectionCount: number
  qualifiedDataCount: number
  unqualifiedDataCount: number
  pendingAlertCount: number
  todayAlertCount: number
  totalInspections: number
  qualifiedRate: number
  monthlyInspections: number
  urgentAlertCount: number
  todayRawCoalInspections: number
  todayCleanCoalInspections: number
  todayMidCoalInspections: number
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

// 获取煤质管理业务流程数据
const getBusinessFlowData = async () => {
  try {
    loading.value = true
    console.log('开始获取煤质管理业务流程数据...')
    
    // 定义今日日期 - 使用更安全的日期生成方式
    const now = new Date()
    const year = now.getFullYear()
    const month = String(now.getMonth() + 1).padStart(2, '0')
    const day = String(now.getDate()).padStart(2, '0')
    const today = `${year}-${month}-${day}`
    console.log('当前查询日期:', today)
    
    // 并行获取各模块数据
    const [
      qualityItemResponse,
      qualityStandardResponse,
      inspectionResponse,
      alertResponse
    ] = await Promise.allSettled([
      QualityItemApi.getQualityItemPage({ pageNo: 1, pageSize: 1 }),
      QualityStandardApi.getQualityStandardPage({ pageNo: 1, pageSize: 1 }),
      QualityInspectionApi.getQualityInspectionPage({ pageNo: 1, pageSize: 1 }),
      QualityAlertApi.getQualityAlertPage({ pageNo: 1, pageSize: 1 })
    ]).then(results => [
      results[0].status === 'fulfilled' ? results[0].value : { total: 0 },
      results[1].status === 'fulfilled' ? results[1].value : { total: 0 },
      results[2].status === 'fulfilled' ? results[2].value : { total: 0 },
      results[3].status === 'fulfilled' ? results[3].value : { total: 0 }
    ])
    
    // 获取启用的检测项目数量
    let activeItemResponse = { total: 0 }
    try {
      activeItemResponse = await QualityItemApi.getQualityItemPage({
        pageNo: 1,
        pageSize: 1,
        status: 1 // 启用状态
      })
    } catch (error) {
      console.warn('获取启用检测项目数量失败:', error)
    }
    
    // 获取生效的质量标准数量
    let activeStandardResponse = { total: 0 }
    try {
      activeStandardResponse = await QualityStandardApi.getQualityStandardPage({
        pageNo: 1,
        pageSize: 1,
        status: 2 // 启用状态
      })
    } catch (error) {
      console.warn('获取生效质量标准数量失败:', error)
    }
    
    // 获取待检测的记录数量
    let pendingInspectionResponse = { total: 0 }
    try {
      pendingInspectionResponse = await QualityInspectionApi.getQualityInspectionPage({
        pageNo: 1,
        pageSize: 1,
        inspectionStatus: 1 // 待检测状态
      })
    } catch (error) {
      console.warn('获取待检测记录数量失败:', error)
    }
    
    // 获取今日检测记录数量
    let todayInspectionResponse = { total: 0 }
    try {
      todayInspectionResponse = await QualityInspectionApi.getQualityInspectionPage({
        pageNo: 1,
        pageSize: 1,
        inspectionDate: [today + ' 00:00:00', today + ' 23:59:59']
      })
    } catch (error) {
      console.warn('获取今日检测记录数量失败:', error)
    }
    
    // 获取质量检测数据统计 - 使用新的统计接口
    let qualityDataStatistics: QualityDataStatistics | null = null
    try {
      qualityDataStatistics = await QualityInspectionApi.getQualityDataStatistics()
      console.log('质量检测数据统计:', qualityDataStatistics)
    } catch (error) {
      console.warn('获取质量检测数据统计失败:', error)
    }
    
    // 使用统计数据或默认值
    const qualityDataResponse = { total: qualityDataStatistics?.totalCount || 0 }
    const qualifiedCount = qualityDataStatistics?.qualifiedCount || 0
    const unqualifiedCount = qualityDataStatistics?.unqualifiedCount || 0
    
    // 获取待处理预警数量
    let pendingAlertResponse = { total: 0 }
    try {
      pendingAlertResponse = await QualityAlertApi.getQualityAlertPage({
        pageNo: 1,
        pageSize: 1,
        alertStatus: 1 // 待处理状态
      })
    } catch (error) {
      console.warn('获取待处理预警数量失败:', error)
    }
    
    // 获取今日预警数量
    let todayAlertResponse = { total: 0 }
    try {
      todayAlertResponse = await QualityAlertApi.getQualityAlertPage({
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
      urgentAlertResponse = await QualityAlertApi.getQualityAlertPage({
        pageNo: 1,
        pageSize: 1,
        alertLevel: 3 // 紧急级别
      })
    } catch (error) {
      console.warn('获取紧急预警数量失败:', error)
    }
    
    // 获取本月检测数量 - 使用更安全的日期范围
    const currentYear = now.getFullYear()
    const currentMonth = String(now.getMonth() + 1).padStart(2, '0')
    
    // 获取当月最后一天
    const lastDayOfMonth = new Date(currentYear, now.getMonth() + 1, 0).getDate()
    
    let monthlyInspectionResponse = { total: 0 }
    try {
      monthlyInspectionResponse = await QualityInspectionApi.getQualityInspectionPage({
        pageNo: 1,
        pageSize: 1,
        inspectionDate: [
          `${currentYear}-${currentMonth}-01 00:00:00`, 
          `${currentYear}-${currentMonth}-${String(lastDayOfMonth).padStart(2, '0')} 23:59:59`
        ]
      })
    } catch (error) {
      console.warn('获取本月检测数量失败:', error)
    }
    
    // 获取今日各产品类型检测数量
    let todayRawCoalResponse = { total: 0 }
    let todayCleanCoalResponse = { total: 0 }
    let todayMidCoalResponse = { total: 0 }
    
    try {
      const [rawCoal, cleanCoal, midCoal] = await Promise.allSettled([
        QualityInspectionApi.getQualityInspectionPage({
          pageNo: 1,
          pageSize: 1,
          productType: 1, // 原煤
          inspectionDate: [today + ' 00:00:00', today + ' 23:59:59']
        }),
        QualityInspectionApi.getQualityInspectionPage({
          pageNo: 1,
          pageSize: 1,
          productType: 2, // 精煤
          inspectionDate: [today + ' 00:00:00', today + ' 23:59:59']
        }),
        QualityInspectionApi.getQualityInspectionPage({
          pageNo: 1,
          pageSize: 1,
          productType: 3, // 中煤
          inspectionDate: [today + ' 00:00:00', today + ' 23:59:59']
        })
      ])
      
      todayRawCoalResponse = rawCoal.status === 'fulfilled' ? rawCoal.value : { total: 0 }
      todayCleanCoalResponse = cleanCoal.status === 'fulfilled' ? cleanCoal.value : { total: 0 }
      todayMidCoalResponse = midCoal.status === 'fulfilled' ? midCoal.value : { total: 0 }
    } catch (error) {
      console.warn('获取今日各产品类型检测数量失败:', error)
    }
    
    // 使用真实统计的合格率
    const qualifiedRate = qualityDataStatistics?.qualifiedRate || 0
    
    // 构建业务流程数据
    businessFlowData.value = {
      // 各模块数量
      qualityItemCount: qualityItemResponse.total || 0,
      qualityStandardCount: qualityStandardResponse.total || 0,
      inspectionCount: inspectionResponse.total || 0,
      qualityDataCount: qualityDataResponse.total || 0, // 使用真实的质量检测数据数量
      alertCount: alertResponse.total || 0,
      
      // 状态统计
      activeItemCount: activeItemResponse.total || 0,
      activeStandardCount: activeStandardResponse.total || 0,
      pendingInspectionCount: pendingInspectionResponse.total || 0,
      todayInspectionCount: todayInspectionResponse.total || 0,
      qualifiedDataCount: qualifiedCount, // 使用真实的合格数据数量
      unqualifiedDataCount: unqualifiedCount, // 使用真实的不合格数据数量
      pendingAlertCount: pendingAlertResponse.total || 0,
      todayAlertCount: todayAlertResponse.total || 0,
      
      // 统计指标
      totalInspections: inspectionResponse.total || 0,
      qualifiedRate: qualifiedRate, // 使用真实计算的合格率
      monthlyInspections: monthlyInspectionResponse.total || 0,
      urgentAlertCount: urgentAlertResponse.total || 0,
      
      // 今日概览
      todayRawCoalInspections: todayRawCoalResponse.total || 0,
      todayCleanCoalInspections: todayCleanCoalResponse.total || 0,
      todayMidCoalInspections: todayMidCoalResponse.total || 0
    }
    
    console.log('煤质管理业务流程数据获取成功:', businessFlowData.value)
  } catch (error) {
    console.error('获取煤质管理业务流程数据失败:', error)
    ElMessage.error('获取煤质管理业务流程数据失败')
    
    // 设置默认数据
    businessFlowData.value = {
      qualityItemCount: 0,
      qualityStandardCount: 0,
      inspectionCount: 0,
      qualityDataCount: 0,
      alertCount: 0,
      activeItemCount: 0,
      activeStandardCount: 0,
      pendingInspectionCount: 0,
      todayInspectionCount: 0,
      qualifiedDataCount: 0,
      unqualifiedDataCount: 0,
      pendingAlertCount: 0,
      todayAlertCount: 0,
      totalInspections: 0,
      qualifiedRate: 0,
      monthlyInspections: 0,
      urgentAlertCount: 0,
      todayRawCoalInspections: 0,
      todayCleanCoalInspections: 0,
      todayMidCoalInspections: 0
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
const goToQualityItem = () => {
  router.push({ name: 'QualityItem' })
}

const goToQualityStandard = () => {
  router.push({ name: 'QualityStandard' })
}

const goToQualityInspection = () => {
  router.push({ name: 'QualityInspection' })
}

const goToQualityData = () => {
  router.push({ name: 'QualityInspection' }) // 检测数据在检测记录页面中
}

const goToQualityAlert = () => {
  router.push({ name: 'QualityAlert' })
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

.flow-arrow {
  margin: 0 16px;
  color: #666;
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
  
  .flow-arrow {
    transform: rotate(90deg);
    margin: 8px 0;
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
