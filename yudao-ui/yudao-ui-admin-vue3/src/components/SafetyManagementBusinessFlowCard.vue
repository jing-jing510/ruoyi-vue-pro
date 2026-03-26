<template>
  <el-card shadow="never" class="mt-8px">
    <template #header>
      <div class="h-3 flex justify-between">
        <div class="flex items-center">
          <span>安全管理业务流程</span>
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
          <!-- 第一行：安全检查分类 -->
          <div class="flow-row">
            <div class="flow-node start-node" @click="goToSafetyCheckCategory">
              <div class="node-icon">
                <Icon icon="ep:list" class="text-20px" />
              </div>
              <div class="node-title">安全检查分类</div>
              <div class="node-count">{{ businessFlowData.safetyCheckCategoryCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="success" v-if="businessFlowData.activeCategoryCount > 0">
                  启用: {{ businessFlowData.activeCategoryCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向安全检查计划 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第二行：安全检查计划 -->
          <div class="flow-row">
            <div class="flow-node process-node" @click="goToSafetyCheckPlan">
              <div class="node-icon">
                <Icon icon="ep:calendar" class="text-20px" />
              </div>
              <div class="node-title">安全检查计划</div>
              <div class="node-count">{{ businessFlowData.safetyCheckPlanCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="warning" v-if="businessFlowData.pendingPlanCount > 0">
                  待执行: {{ businessFlowData.pendingPlanCount }}
                </el-tag>
                <el-tag size="small" type="info" v-if="businessFlowData.todayPlanCount > 0">
                  今日计划: {{ businessFlowData.todayPlanCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向安全检查记录 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第三行：安全检查记录 -->
          <div class="flow-row">
            <div class="flow-node process-node" @click="goToSafetyCheckRecord">
              <div class="node-icon">
                <Icon icon="ep:search" class="text-20px" />
              </div>
              <div class="node-title">安全检查记录</div>
              <div class="node-count">{{ businessFlowData.safetyCheckRecordCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="success" v-if="businessFlowData.qualifiedRecordCount > 0">
                  已提交: {{ businessFlowData.qualifiedRecordCount }}
                </el-tag>
                <el-tag size="small" type="warning" v-if="businessFlowData.unqualifiedRecordCount > 0">
                  待审核: {{ businessFlowData.unqualifiedRecordCount }}
                </el-tag>
                <el-tag size="small" type="info" v-if="businessFlowData.todayRecordCount > 0">
                  今日检查: {{ businessFlowData.todayRecordCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向安全检查项目 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第四行：安全检查项目 -->
          <div class="flow-row">
            <div class="flow-node process-node" @click="goToSafetyCheckItem">
              <div class="node-icon">
                <Icon icon="ep:document" class="text-20px" />
              </div>
              <div class="node-title">安全检查项目</div>
              <div class="node-count">{{ businessFlowData.safetyCheckItemCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="warning" v-if="businessFlowData.pendingItemCount > 0">
                  待整改: {{ businessFlowData.pendingItemCount }}
                </el-tag>
                <el-tag size="small" type="success" v-if="businessFlowData.completedItemCount > 0">
                  已完成: {{ businessFlowData.completedItemCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向安全事故记录 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第五行：安全事故记录 -->
          <div class="flow-row">
            <div class="flow-node end-node" @click="goToSafetyAccident">
              <div class="node-icon">
                <Icon icon="ep:warning" class="text-20px" />
              </div>
              <div class="node-title">安全事故记录</div>
              <div class="node-count">{{ businessFlowData.safetyAccidentCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="danger" v-if="businessFlowData.pendingAccidentCount > 0">
                  待处理: {{ businessFlowData.pendingAccidentCount }}
                </el-tag>
                <el-tag size="small" type="warning" v-if="businessFlowData.todayAccidentCount > 0">
                  今日事故: {{ businessFlowData.todayAccidentCount }}
                </el-tag>
                <el-tag size="small" type="info" v-if="businessFlowData.severeAccidentCount > 0">
                  重大事故: {{ businessFlowData.severeAccidentCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向安全附件 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第六行：安全附件 -->
          <div class="flow-row">
            <div class="flow-node end-node" @click="goToSafetyAttachment">
              <div class="node-icon">
                <Icon icon="ep:paperclip" class="text-20px" />
              </div>
              <div class="node-title">安全附件</div>
              <div class="node-count">{{ businessFlowData.safetyAttachmentCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="info" v-if="businessFlowData.todayAttachmentCount > 0">
                  今日上传: {{ businessFlowData.todayAttachmentCount }}
                </el-tag>
                <el-tag size="small" type="primary" v-if="businessFlowData.recordAttachmentCount > 0">
                  记录附件: {{ businessFlowData.recordAttachmentCount }}
                </el-tag>
                <el-tag size="small" type="warning" v-if="businessFlowData.accidentAttachmentCount > 0">
                  事故附件: {{ businessFlowData.accidentAttachmentCount }}
                </el-tag>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 安全管理统计 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">安全管理统计</div>
          <el-row :gutter="12">
            <el-col :span="6">
              <div class="text-center p-2 bg-blue-50 rounded">
                <div class="text-16px font-bold text-blue-600">{{ businessFlowData.totalCheckCount || 0 }}</div>
                <div class="text-11px text-gray-500">总检查次数</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-green-50 rounded">
                <div class="text-16px font-bold text-green-600">{{ formatPercentage(businessFlowData.qualifiedRate) }}%</div>
                <div class="text-11px text-gray-500">检查合格率</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-orange-50 rounded">
                <div class="text-16px font-bold text-orange-600">{{ businessFlowData.monthlyCheckCount || 0 }}</div>
                <div class="text-11px text-gray-500">本月检查</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-red-50 rounded">
                <div class="text-16px font-bold text-red-600">{{ businessFlowData.totalAccidentCount || 0 }}</div>
                <div class="text-11px text-gray-500">安全事故数</div>
              </div>
            </el-col>
          </el-row>
        </div>
        
        <!-- 今日安全概览 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">
            今日安全概览 
            <span class="text-12px text-blue-600 ml-2">（{{ getCurrentDate() }}）</span>
          </div>
          <el-row :gutter="8">
            <el-col :span="8">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ businessFlowData.todayCheckCount || 0 }}</div>
                <div class="text-10px text-gray-500">今日检查</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ businessFlowData.todayPlanCount || 0 }}</div>
                <div class="text-10px text-gray-500">今日计划</div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="text-center p-2 bg-gray-50 rounded">
                <div class="text-14px font-bold text-gray-700">{{ businessFlowData.todayAttachmentCount || 0 }}</div>
                <div class="text-10px text-gray-500">今日附件</div>
              </div>
            </el-col>
          </el-row>
        </div>
        
        <!-- 快速操作 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">快速操作</div>
          <el-row :gutter="8">
            <el-col :span="6">
              <el-button size="small" type="primary" @click="goToSafetyCheckPlan">
                <Icon icon="ep:plus" class="mr-1" />
                新建计划
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="warning" @click="goToSafetyCheckRecord">
                <Icon icon="ep:search" class="mr-1" />
                安全检查
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="danger" @click="goToSafetyAccident">
                <Icon icon="ep:warning" class="mr-1" />
                事故记录
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="info" @click="goToSafetyAttachment">
                <Icon icon="ep:paperclip" class="mr-1" />
                附件管理
              </el-button>
            </el-col>
          </el-row>
        </div>
      </div>
      
      <div v-else class="text-center text-gray-400 py-8">
        暂无安全管理数据
      </div>
    </el-skeleton>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { SafetyCheckCategoryApi } from '@/api/coal/safetycheckcategory'
import { SafetyCheckPlanApi } from '@/api/coal/safetycheckplan'
import { SafetyCheckRecordApi, SafetyCheckItemApi } from '@/api/coal/safetycheckrecord'
import { SafetyAccidentApi } from '@/api/coal/safetyaccident'
import { SafetyAttachmentApi } from '@/api/coal/safetyattachment'

// 定义业务数据接口
interface BusinessFlowData {
  // 各模块数量
  safetyCheckCategoryCount: number
  safetyCheckPlanCount: number
  safetyCheckRecordCount: number
  safetyCheckItemCount: number
  safetyAccidentCount: number
  safetyAttachmentCount: number
  
  // 状态统计
  activeCategoryCount: number
  pendingPlanCount: number
  todayPlanCount: number
  qualifiedRecordCount: number
  unqualifiedRecordCount: number
  todayRecordCount: number
  pendingItemCount: number
  completedItemCount: number
  pendingAccidentCount: number
  todayAccidentCount: number
  severeAccidentCount: number
  todayAttachmentCount: number
  recordAttachmentCount: number
  accidentAttachmentCount: number
  
  // 统计指标
  totalCheckCount: number
  qualifiedRate: number
  monthlyCheckCount: number
  totalAccidentCount: number
  todayCheckCount: number
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

// 获取安全管理业务流程数据
const getBusinessFlowData = async () => {
  try {
    loading.value = true
    console.log('开始获取安全管理业务流程数据...')
    
    // 定义今日日期
    const now = new Date()
    const year = now.getFullYear()
    const month = String(now.getMonth() + 1).padStart(2, '0')
    const day = String(now.getDate()).padStart(2, '0')
    const today = `${year}-${month}-${day}`
    console.log('当前查询日期:', today)
    
    // 并行获取各模块数据
    const [
      categoryResponse,
      planResponse,
      recordResponse,
      accidentResponse,
      attachmentResponse
    ] = await Promise.allSettled([
      SafetyCheckCategoryApi.getSafetyCheckCategoryList({}),
      SafetyCheckPlanApi.getSafetyCheckPlanPage({ pageNo: 1, pageSize: 1 }),
      SafetyCheckRecordApi.getSafetyCheckRecordPage({ pageNo: 1, pageSize: 1 }),
      SafetyAccidentApi.getSafetyAccidentPage({ pageNo: 1, pageSize: 1 }),
      SafetyAttachmentApi.getSafetyAttachmentPage({ pageNo: 1, pageSize: 1 })
    ]).then(results => [
      results[0].status === 'fulfilled' ? { total: Array.isArray(results[0].value) ? results[0].value.length : 0 } : { total: 0 },
      results[1].status === 'fulfilled' ? results[1].value : { total: 0 },
      results[2].status === 'fulfilled' ? results[2].value : { total: 0 },
      results[3].status === 'fulfilled' ? results[3].value : { total: 0 },
      results[4].status === 'fulfilled' ? results[4].value : { total: 0 }
    ])
    
    // 获取启用的安全检查分类数量
    let activeCategoryResponse = { total: 0 }
    try {
      const activeCategories = await SafetyCheckCategoryApi.getSafetyCheckCategoryList({
        status: 1 // 启用状态
      })
      activeCategoryResponse = { total: Array.isArray(activeCategories) ? activeCategories.length : 0 }
    } catch (error) {
      console.warn('获取启用安全检查分类数量失败:', error)
    }
    
    // 获取待执行的安全检查计划数量
    let pendingPlanResponse = { total: 0 }
    try {
      pendingPlanResponse = await SafetyCheckPlanApi.getSafetyCheckPlanPage({
        pageNo: 1,
        pageSize: 1,
        planStatus: 1 // 待执行状态
      })
    } catch (error) {
      console.warn('获取待执行安全检查计划数量失败:', error)
    }
    
    // 获取今日安全检查计划数量
    let todayPlanResponse = { total: 0 }
    try {
      todayPlanResponse = await SafetyCheckPlanApi.getSafetyCheckPlanPage({
        pageNo: 1,
        pageSize: 1,
        planDate: [today + ' 00:00:00', today + ' 23:59:59']
      })
    } catch (error) {
      console.warn('获取今日安全检查计划数量失败:', error)
    }
    
    // 获取安全检查记录统计数据
    let recordStatistics = null
    try {
      recordStatistics = await SafetyCheckRecordApi.getSafetyCheckRecordStatistics()
      console.log('安全检查记录统计数据:', recordStatistics)
    } catch (error) {
      console.warn('获取安全检查记录统计数据失败:', error)
    }
    
    // 获取今日安全检查记录数量
    let todayRecordResponse = { total: 0 }
    try {
      todayRecordResponse = await SafetyCheckRecordApi.getSafetyCheckRecordPage({
        pageNo: 1,
        pageSize: 1,
        checkTime: [today + ' 00:00:00', today + ' 23:59:59']
      })
    } catch (error) {
      console.warn('获取今日安全检查记录数量失败:', error)
    }
    
    // 获取安全检查项目统计数据
    let itemStatistics = null
    try {
      itemStatistics = await SafetyCheckRecordApi.getSafetyCheckItemStatistics()
    } catch (error) {
      console.warn('获取安全检查项目统计数据失败:', error)
    }
    
    // 使用统计数据或默认值
    const pendingItemCount = (itemStatistics as any)?.pendingCount || 0
    const completedItemCount = (itemStatistics as any)?.completedCount || 0
    
    // 获取安全事故记录统计数据
    let accidentStatistics = null
    try {
      accidentStatistics = await SafetyAccidentApi.getSafetyAccidentStatistics()
      console.log('安全事故记录统计数据:', accidentStatistics)
    } catch (error) {
      console.warn('获取安全事故记录统计数据失败:', error)
    }
    
    // 获取待处理的安全事故数量
    let pendingAccidentResponse = { total: 0 }
    try {
      pendingAccidentResponse = await SafetyAccidentApi.getSafetyAccidentPage({
        pageNo: 1,
        pageSize: 1,
        accidentStatus: 1 // 待处理状态
      })
    } catch (error) {
      console.warn('获取待处理安全事故数量失败:', error)
    }
    
    // 获取今日安全事故数量
    let todayAccidentResponse = { total: 0 }
    try {
      todayAccidentResponse = await SafetyAccidentApi.getSafetyAccidentPage({
        pageNo: 1,
        pageSize: 1,
        accidentTime: [today + ' 00:00:00', today + ' 23:59:59']
      })
    } catch (error) {
      console.warn('获取今日安全事故数量失败:', error)
    }
    
    // 获取重大安全事故数量
    let severeAccidentResponse = { total: 0 }
    try {
      severeAccidentResponse = await SafetyAccidentApi.getSafetyAccidentPage({
        pageNo: 1,
        pageSize: 1,
        accidentLevel: 1 // 重大事故级别
      })
    } catch (error) {
      console.warn('获取重大安全事故数量失败:', error)
    }
    
    // 获取安全附件统计数据
    let attachmentStatistics = null
    try {
      attachmentStatistics = await SafetyAttachmentApi.getSafetyAttachmentStatistics()
      console.log('安全附件统计数据:', attachmentStatistics)
    } catch (error) {
      console.warn('获取安全附件统计数据失败:', error)
    }
    
    // 获取今日安全附件数量
    let todayAttachmentResponse = { total: 0 }
    try {
      todayAttachmentResponse = await SafetyAttachmentApi.getSafetyAttachmentPage({
        pageNo: 1,
        pageSize: 1,
        uploadTime: [today + ' 00:00:00', today + ' 23:59:59']
      })
    } catch (error) {
      console.warn('获取今日安全附件数量失败:', error)
    }
    
    // 获取安全检查记录附件数量
    let recordAttachmentResponse = { total: 0 }
    try {
      recordAttachmentResponse = await SafetyAttachmentApi.getSafetyAttachmentPage({
        pageNo: 1,
        pageSize: 1,
        businessType: 1 // 安全检查记录
      })
    } catch (error) {
      console.warn('获取安全检查记录附件数量失败:', error)
    }
    
    // 获取安全事故记录附件数量
    let accidentAttachmentResponse = { total: 0 }
    try {
      accidentAttachmentResponse = await SafetyAttachmentApi.getSafetyAttachmentPage({
        pageNo: 1,
        pageSize: 1,
        businessType: 3 // 安全事故记录
      })
    } catch (error) {
      console.warn('获取安全事故记录附件数量失败:', error)
    }
    
    // 获取本月安全检查数量
    const currentYear = now.getFullYear()
    const currentMonth = String(now.getMonth() + 1).padStart(2, '0')
    const lastDayOfMonth = new Date(currentYear, now.getMonth() + 1, 0).getDate()
    
    let monthlyCheckResponse = { total: 0 }
    try {
      monthlyCheckResponse = await SafetyCheckRecordApi.getSafetyCheckRecordPage({
        pageNo: 1,
        pageSize: 1,
        checkTime: [
          `${currentYear}-${currentMonth}-01 00:00:00`, 
          `${currentYear}-${currentMonth}-${String(lastDayOfMonth).padStart(2, '0')} 23:59:59`
        ]
      })
    } catch (error) {
      console.warn('获取本月安全检查数量失败:', error)
    }
    
    // 使用统计数据或默认值
    const submittedCount = (recordStatistics as any)?.submittedCount || 0
    const pendingAuditCount = (recordStatistics as any)?.pendingAuditCount || 0
    const qualifiedRate = (recordStatistics as any)?.qualifiedRate || 0
    
    // 构建业务流程数据
    businessFlowData.value = {
      // 各模块数量
      safetyCheckCategoryCount: categoryResponse.total || 0,
      safetyCheckPlanCount: planResponse.total || 0,
      safetyCheckRecordCount: recordResponse.total || 0,
      safetyCheckItemCount: pendingItemCount + completedItemCount,
      safetyAccidentCount: accidentResponse.total || 0,
      safetyAttachmentCount: attachmentResponse.total || 0,
      
      // 状态统计
      activeCategoryCount: activeCategoryResponse.total || 0,
      pendingPlanCount: pendingPlanResponse.total || 0,
      todayPlanCount: todayPlanResponse.total || 0,
      qualifiedRecordCount: submittedCount,
      unqualifiedRecordCount: pendingAuditCount,
      todayRecordCount: todayRecordResponse.total || 0,
      pendingItemCount: pendingItemCount,
      completedItemCount: completedItemCount,
      pendingAccidentCount: pendingAccidentResponse.total || 0,
      todayAccidentCount: todayAccidentResponse.total || 0,
      severeAccidentCount: severeAccidentResponse.total || 0,
      todayAttachmentCount: todayAttachmentResponse.total || 0,
      recordAttachmentCount: recordAttachmentResponse.total || 0,
      accidentAttachmentCount: accidentAttachmentResponse.total || 0,
      
      // 统计指标
      totalCheckCount: recordResponse.total || 0,
      qualifiedRate: qualifiedRate,
      monthlyCheckCount: monthlyCheckResponse.total || 0,
      totalAccidentCount: accidentResponse.total || 0,
      todayCheckCount: todayRecordResponse.total || 0
    }
    
    console.log('安全管理业务流程数据获取成功:', businessFlowData.value)
  } catch (error) {
    console.error('获取安全管理业务流程数据失败:', error)
    ElMessage.error('获取安全管理业务流程数据失败')
    
    // 设置默认数据
    businessFlowData.value = {
      safetyCheckCategoryCount: 0,
      safetyCheckPlanCount: 0,
      safetyCheckRecordCount: 0,
      safetyCheckItemCount: 0,
      safetyAccidentCount: 0,
      safetyAttachmentCount: 0,
      activeCategoryCount: 0,
      pendingPlanCount: 0,
      todayPlanCount: 0,
      qualifiedRecordCount: 0,
      unqualifiedRecordCount: 0,
      todayRecordCount: 0,
      pendingItemCount: 0,
      completedItemCount: 0,
      pendingAccidentCount: 0,
      todayAccidentCount: 0,
      severeAccidentCount: 0,
      todayAttachmentCount: 0,
      recordAttachmentCount: 0,
      accidentAttachmentCount: 0,
      totalCheckCount: 0,
      qualifiedRate: 0,
      monthlyCheckCount: 0,
      totalAccidentCount: 0,
      todayCheckCount: 0
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
const goToSafetyCheckCategory = () => {
  router.push({ name: 'SafetyCheckCategory' })
}

const goToSafetyCheckPlan = () => {
  router.push({ name: 'SafetyCheckPlan' })
}

const goToSafetyCheckRecord = () => {
  router.push({ name: 'SafetyCheckRecord' })
}

const goToSafetyCheckItem = () => {
  router.push({ name: 'SafetyCheckRecord' }) // 安全检查项目在安全检查记录页面中
}

const goToSafetyAccident = () => {
  router.push({ name: 'SafetyAccident' })
}

const goToSafetyAttachment = () => {
  router.push({ name: 'SafetyAttachment' })
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
