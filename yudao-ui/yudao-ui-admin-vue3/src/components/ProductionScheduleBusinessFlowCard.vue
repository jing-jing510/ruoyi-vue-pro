<template>
  <el-card shadow="never" class="mt-8px">
    <template #header>
      <div class="h-3 flex justify-between">
        <div class="flex items-center">
          <span>生产调度业务流程</span>
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
          <!-- 第一行：生产计划 -->
          <div class="flow-row">
            <div class="flow-node start-node" @click="goToProductionPlan">
              <div class="node-icon">
                <Icon icon="ep:calendar" class="text-20px" />
              </div>
              <div class="node-title">生产计划</div>
              <div class="node-count">{{ businessFlowData.productionPlanCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="warning" v-if="businessFlowData.yearlyPlanCount > 0">
                  年度: {{ businessFlowData.yearlyPlanCount }}
                </el-tag>
                <el-tag size="small" type="info" v-if="businessFlowData.monthlyPlanCount > 0">
                  月度: {{ businessFlowData.monthlyPlanCount }}
                </el-tag>
                <el-tag size="small" type="success" v-if="businessFlowData.dailyPlanCount > 0">
                  日计划: {{ businessFlowData.dailyPlanCount }}
                </el-tag>
                <el-tag size="small" type="primary">
                  班计划: {{ businessFlowData.shiftPlanCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向班制班次 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第二行：班制与班次设置 -->
          <div class="flow-row">
            <div class="flow-node process-node" @click="goToShiftSystem">
              <div class="node-icon">
                <Icon icon="ep:clock" class="text-20px" />
              </div>
              <div class="node-title">班制与班次设置</div>
              <div class="node-count">{{ businessFlowData.shiftSystemCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="success" v-if="businessFlowData.productionShiftCount > 0">
                  生产班制: {{ businessFlowData.productionShiftCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向排班管理 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第三行：排班管理 -->
          <div class="flow-row">
            <div class="flow-node process-node" @click="goToSchedule">
              <div class="node-icon">
                <Icon icon="ep:user" class="text-20px" />
              </div>
              <div class="node-title">排班管理</div>
              <div class="node-count">{{ businessFlowData.scheduleCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="primary" v-if="businessFlowData.todayScheduleCount > 0">
                  今日排班: {{ businessFlowData.todayScheduleCount }}
                </el-tag>
                <el-tag size="small" type="info" v-if="businessFlowData.todayScheduleCount === 0">
                  今日无排班
                </el-tag>
                <el-tag size="small" type="warning" v-if="businessFlowData.pendingApprovalCount > 0">
                  待审批: {{ businessFlowData.pendingApprovalCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向生产日报 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第四行：生产日报 -->
          <div class="flow-row">
            <div class="flow-node end-node" @click="goToProductionDailyReport">
              <div class="node-icon">
                <Icon icon="ep:document" class="text-20px" />
              </div>
              <div class="node-title">生产日报</div>
              <div class="node-count">{{ businessFlowData.dailyReportCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="success" v-if="businessFlowData.todayReportCount > 0">
                  今日填报: {{ businessFlowData.todayReportCount }}
                </el-tag>
                <el-tag size="small" type="danger" v-if="businessFlowData.missingReportCount > 0">
                  缺失填报: {{ businessFlowData.missingReportCount }}
                </el-tag>
                <el-tag size="small" type="info" v-if="businessFlowData.todayReportCount === 0 && businessFlowData.missingReportCount === 0">
                  今日未填报
                </el-tag>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 生产调度统计 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">生产调度统计</div>
          <el-row :gutter="12">
            <el-col :span="6">
              <div class="text-center p-2 bg-blue-50 rounded">
                <div class="text-16px font-bold text-blue-600">{{ businessFlowData.totalProductionDays || 0 }}</div>
                <div class="text-11px text-gray-500">生产天数</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-green-50 rounded">
                <div class="text-16px font-bold text-green-600">{{ formatNumber(businessFlowData.monthlyOutput) }}</div>
                <div class="text-11px text-gray-500">月度产量(吨)</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-orange-50 rounded">
                <div class="text-16px font-bold text-orange-600">{{ businessFlowData.activeStaffCount || 0 }}</div>
                <div class="text-11px text-gray-500">在岗人员</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-purple-50 rounded">
                <div class="text-16px font-bold text-purple-600">{{ formatPercentage(businessFlowData.planCompletionRate) }}%</div>
                <div class="text-11px text-gray-500">计划完成率</div>
              </div>
            </el-col>
          </el-row>
        </div>
        
        <!-- 生产调度概览 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">
            今日生产概览 
            <span class="text-12px text-blue-600 ml-2">（{{ getCurrentDate() }}）</span>
          </div>
          <el-row :gutter="8">
            <el-col :span="8">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ formatNumber(businessFlowData.todayRawCoalInput) }}</div>
                <div class="text-10px text-gray-500">入洗原煤(吨)</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ formatNumber(businessFlowData.todayCleanCoalOutput) }}</div>
                <div class="text-10px text-gray-500">精煤产量(吨)</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ businessFlowData.todayOperatingHours || 0 }}</div>
                <div class="text-10px text-gray-500">运行时间(小时)</div>
              </div>
            </el-col>
          </el-row>
        </div>

        <!-- 计划完成率统计 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">计划完成率统计</div>
          <el-row :gutter="8">
            <el-col :span="8">
              <div class="text-center p-2 bg-blue-50 rounded">
                <div class="text-14px font-bold text-blue-700">{{ formatPercentage(businessFlowData.yearlyPlanCompletionRate) }}%</div>
                <div class="text-10px text-gray-500">年度计划完成率</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="text-center p-2 bg-green-50 rounded">
                <div class="text-14px font-bold text-green-700">{{ formatPercentage(businessFlowData.monthlyPlanCompletionRate) }}%</div>
                <div class="text-10px text-gray-500">月度计划完成率</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="text-center p-2 bg-purple-50 rounded">
                <div class="text-14px font-bold text-purple-700">{{ formatPercentage(businessFlowData.dailyPlanCompletionRate) }}%</div>
                <div class="text-10px text-gray-500">日计划完成率</div>
              </div>
            </el-col>
          </el-row>
        </div>
        
        <!-- 快速操作 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">快速操作</div>
          <el-row :gutter="8">
            <el-col :span="6">
              <el-button size="small" type="primary" @click="goToProductionPlan">
                <Icon icon="ep:plus" class="mr-1" />
                生产计划
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="warning" @click="goToSchedule">
                <Icon icon="ep:user" class="mr-1" />
                排班管理
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="success" @click="goToProductionDailyReport">
                <Icon icon="ep:document" class="mr-1" />
                填写日报
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="info" @click="goToShiftSystem">
                <Icon icon="ep:setting" class="mr-1" />
                班制设置
              </el-button>
            </el-col>
          </el-row>
        </div>
      </div>
      
      <div v-else class="text-center text-gray-400 py-8">
        暂无生产调度数据
      </div>
    </el-skeleton>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ProductionPlanApi } from '@/api/coal/productionplan'
import { ProductionDailyReportApi, type ProductionDailyReportStatistics } from '@/api/coal/productiondailyreport'
import { ShiftSystemApi } from '@/api/coal/shiftsystem'
import { ScheduleApi } from '@/api/coal/schedule'

// 定义业务数据接口
interface BusinessFlowData {
  productionPlanCount: number
  dailyReportCount: number
  shiftSystemCount: number
  scheduleCount: number
  yearlyPlanCount: number
  monthlyPlanCount: number
  dailyPlanCount: number
  shiftPlanCount: number // 班计划数量（早中晚三班）
  productionShiftCount: number
  todayScheduleCount: number
  pendingApprovalCount: number
  todayReportCount: number
  missingReportCount: number
  totalProductionDays: number
  monthlyOutput: number
  activeStaffCount: number
  yearlyPlanCompletionRate: number
  monthlyPlanCompletionRate: number
  dailyPlanCompletionRate: number
  todayRawCoalInput: number
  todayCleanCoalOutput: number
  todayOperatingHours: number
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

// 获取生产调度业务流程数据
const getBusinessFlowData = async () => {
  try {
    loading.value = true
    console.log('开始获取生产调度业务流程数据...')
    
    // 定义今日日期（在所有使用之前）
    const today = new Date().toISOString().split('T')[0]
    console.log('当前查询日期:', today)
    
    // 获取生产日报统计数据（使用新的统计接口）
    let reportStatistics: ProductionDailyReportStatistics | null = null
    try {
      reportStatistics = await ProductionDailyReportApi.getProductionDailyReportStatistics()
      console.log('生产日报统计数据:', reportStatistics)
    } catch (error) {
      console.warn('获取生产日报统计数据失败:', error)
    }

    // 并行获取各模块的其他数据（添加错误处理）
    const [
      productionPlanResponse,
      shiftSystemResponse,
      scheduleResponse
    ] = await Promise.allSettled([
      ProductionPlanApi.getProductionPlanList({ pageNo: 1, pageSize: 1 }),
      ShiftSystemApi.getShiftSystemList({}),
      ScheduleApi.getSchedulePage({ pageNo: 1, pageSize: 1 })
    ]).then(results => [
      results[0].status === 'fulfilled' ? results[0].value : { total: 0 },
      results[1].status === 'fulfilled' ? results[1].value : [],
      results[2].status === 'fulfilled' ? results[2].value : { total: 0 }
    ])
    
    // 获取年度计划数量（当前年度）
    const currentYear = new Date().getFullYear()
    let yearlyPlanResponse = []
    try {
      const response = await ProductionPlanApi.getProductionPlanList({ 
        planType: 1, // 年度计划
        planYear: currentYear
      })
      yearlyPlanResponse = Array.isArray(response) ? response : (response?.data || [])
      console.log(`${currentYear}年度计划查询结果:`, yearlyPlanResponse)
    } catch (error) {
      console.warn('获取年度计划数量失败:', error)
      yearlyPlanResponse = []
    }
    
    // 获取月度计划数量（当前月度）
    const currentMonth = new Date().getMonth() + 1
    let monthlyPlanResponse = []
    try {
      const response = await ProductionPlanApi.getProductionPlanList({
        planType: 2, // 月度计划
        planYear: currentYear,
        planMonth: currentMonth
      })
      monthlyPlanResponse = Array.isArray(response) ? response : (response?.data || [])
      console.log(`${currentYear}年${currentMonth}月度计划查询结果:`, monthlyPlanResponse)
    } catch (error) {
      console.warn('获取月度计划数量失败:', error)
      monthlyPlanResponse = []
    }
    
    // 获取日计划数量（今日）- 由于后端planDate参数格式问题，先获取当月所有日计划再前端筛选
    let dailyPlanResponse = []
    try {
      const response = await ProductionPlanApi.getProductionPlanList({
        planType: 3, // 日计划
        planYear: currentYear,
        planMonth: currentMonth
        // 暂时不传planDate参数，避免格式错误
      })
      const allDailyPlans = Array.isArray(response) ? response : (response?.data || [])
      
      // 前端筛选今日日计划（如果有日期字段）
      dailyPlanResponse = allDailyPlans.filter(plan => {
        if (plan.planDate) {
          const planDate = new Date(plan.planDate).toISOString().split('T')[0]
          return planDate === today
        }
        return false
      })
      
      console.log(`${today}日计划查询结果（前端筛选）:`, dailyPlanResponse, '原始数据:', allDailyPlans)
    } catch (error) {
      console.warn('获取日计划数量失败:', error)
      dailyPlanResponse = []
    }
    
    // 班计划数量（固定为早中晚三班）
    const shiftPlanCount = 3
    
    // 获取生产班制数量
    const productionShifts = Array.isArray(shiftSystemResponse) 
      ? shiftSystemResponse.filter(shift => shift.isProduction === 1)
      : []
    
    // 获取排班数据用于筛选今日排班
    let allScheduleResponse = { total: 0, list: [] }
    try {
      allScheduleResponse = await ScheduleApi.getSchedulePage({
        pageNo: 1,
        pageSize: 50 // 获取更多数据用于筛选
      })
    } catch (error) {
      console.warn('获取排班数据失败:', error)
    }
    
    // 筛选今日排班（前端过滤）
    const todaySchedules = (allScheduleResponse.list || []).filter(schedule => {
      if (schedule.scheduleDate) {
        const scheduleDate = new Date(schedule.scheduleDate).toISOString().split('T')[0]
        return scheduleDate === today
      }
      return false
    })
    console.log('今日排班筛选结果:', todaySchedules)
    
    // 获取待审批排班数量（可能不支持此状态查询，使用默认值）
    let pendingApprovalResponse = { total: 0 }
    try {
      pendingApprovalResponse = await ScheduleApi.getSchedulePage({
        pageNo: 1,
        pageSize: 1,
        scheduleStatus: 1 // 待审批
      })
    } catch (error) {
      console.warn('获取待审批排班数量失败，使用默认值:', error)
    }
    
    // 注：今日生产日报统计已通过统计接口获取，不再需要前端筛选
    
    // 构建生产调度业务流程数据（使用统计接口数据）
    businessFlowData.value = {
      // 各模块数量
      productionPlanCount: productionPlanResponse.total || 0,
      dailyReportCount: reportStatistics?.monthlyProduction?.monthlyProductionDays || 0,
      shiftSystemCount: Array.isArray(shiftSystemResponse) ? shiftSystemResponse.length : 0,
      scheduleCount: scheduleResponse.total || 0,
      
      // 详细统计
      yearlyPlanCount: yearlyPlanResponse.length,
      monthlyPlanCount: monthlyPlanResponse.length,
      dailyPlanCount: dailyPlanResponse.length,
      shiftPlanCount: shiftPlanCount, // 班计划数量（早中晚三班）
      productionShiftCount: productionShifts.length,
      todayScheduleCount: todaySchedules.length, // 今日排班数量（前端筛选结果）
      pendingApprovalCount: pendingApprovalResponse.total || 0,
      todayReportCount: reportStatistics?.todayProduction?.todayReportCount || 0, // 使用统计接口数据
      missingReportCount: reportStatistics?.todayProduction?.todayMissingReportCount || 0, // 使用统计接口数据
      
      // 生产统计（使用统计接口数据）
      totalProductionDays: reportStatistics?.overview?.totalProductionDays || 0,
      monthlyOutput: reportStatistics?.monthlyProduction?.monthlyOutput || 0,
      activeStaffCount: reportStatistics?.overview?.activeStaffCount || 0, // 使用统计接口的真实用户数据
      yearlyPlanCompletionRate: reportStatistics?.overview?.yearlyPlanCompletionRate || 0,
      monthlyPlanCompletionRate: reportStatistics?.overview?.monthlyPlanCompletionRate || 0,
      dailyPlanCompletionRate: reportStatistics?.overview?.dailyPlanCompletionRate || 0,
      
      // 今日生产（使用统计接口数据）
      todayRawCoalInput: reportStatistics?.todayProduction?.todayRawCoalInput || 0,
      todayCleanCoalOutput: reportStatistics?.todayProduction?.todayCleanCoalOutput || 0,
      todayOperatingHours: reportStatistics?.todayProduction?.todayOperatingHours || 0
    }
    
    console.log('生产调度业务流程数据获取成功:', businessFlowData.value)
  } catch (error) {
    console.error('获取生产调度业务流程数据失败:', error)
    ElMessage.error('获取生产调度业务流程数据失败')
    
    // 设置默认数据
    businessFlowData.value = {
      productionPlanCount: 0,
      dailyReportCount: 0,
      shiftSystemCount: 0,
      scheduleCount: 0,
      yearlyPlanCount: 0,
      monthlyPlanCount: 0,
      dailyPlanCount: 0,
      shiftPlanCount: 3, // 班计划默认值（早中晚三班）
      productionShiftCount: 0,
      todayScheduleCount: 0,
      pendingApprovalCount: 0,
      todayReportCount: 0,
      missingReportCount: 0,
      totalProductionDays: 0,
      monthlyOutput: 0,
      activeStaffCount: 0,
      yearlyPlanCompletionRate: 0,
      monthlyPlanCompletionRate: 0,
      dailyPlanCompletionRate: 0,
      todayRawCoalInput: 0,
      todayCleanCoalOutput: 0,
      todayOperatingHours: 0
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
const goToProductionPlan = () => {
  router.push({ name: 'ProductionPlan' })
}

const goToProductionDailyReport = () => {
  router.push({ name: 'ProductionDailyReport' })
}

const goToShiftSystem = () => {
  router.push({ name: 'ShiftSystem' })
}

const goToSchedule = () => {
  router.push({ name: 'Schedule' })
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

.process-node {
  background: linear-gradient(135deg, #fff3e0 0%, #ffcc80 100%);
  border: 2px solid #ff9800;
  color: #f57c00;
}

.end-node {
  background: linear-gradient(135deg, #e8f5e8 0%, #c8e6c9 100%);
  border: 2px solid #4caf50;
  color: #388e3c;
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
