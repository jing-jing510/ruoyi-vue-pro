<template>
  <el-card shadow="never" class="mt-8px">
    <template #header>
      <div class="h-3 flex justify-between">
        <span>设备维护业务流程</span>
        <el-button type="text" size="small" @click="refreshData" :loading="loading">
          刷新
        </el-button>
      </div>
    </template>
    
    <el-skeleton :loading="loading" animated>
      <div v-if="businessFlowData">
        <!-- 业务流程图 -->
        <div class="business-flow-container">
          <!-- 第一行：检修计划和报修单 -->
          <div class="flow-row">
            <div class="flow-node source-node" @click="goToMaintenancePlan">
              <div class="node-icon">
                <Icon icon="ep:calendar" class="text-20px" />
              </div>
              <div class="node-title">检修计划</div>
              <div class="node-count">{{ businessFlowData.maintenancePlanCount || 0 }}</div>
            </div>
            
            <div class="flow-arrow">
              <Icon icon="ep:arrow-right" class="text-16px" />
            </div>
            
            <div class="flow-node source-node" @click="goToRepairRequest">
              <div class="node-icon">
                <Icon icon="ep:warning" class="text-20px" />
              </div>
              <div class="node-title">报修单</div>
              <div class="node-count">{{ businessFlowData.repairRequestCount || 0 }}</div>
            </div>
          </div>
          
          <!-- 箭头指向检修单 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第二行：检修单 -->
          <div class="flow-row">
            <div class="flow-node center-node" @click="goToMaintenanceOrder">
              <div class="node-icon">
                <Icon icon="ep:tools" class="text-20px" />
              </div>
              <div class="node-title">检修单</div>
              <div class="node-count">{{ businessFlowData.maintenanceOrderCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="success" v-if="businessFlowData.pendingMaintenanceCount > 0">
                  待执行: {{ businessFlowData.pendingMaintenanceCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向备件使用记录 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第三行：备件使用记录 -->
          <div class="flow-row">
            <div class="flow-node center-node" @click="goToSparePartUsageRecord">
              <div class="node-icon">
                <Icon icon="ep:box" class="text-20px" />
              </div>
              <div class="node-title">备件使用记录</div>
              <div class="node-count">{{ businessFlowData.sparePartUsageCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="info" v-if="businessFlowData.todayUsageCount > 0">
                  今日使用: {{ businessFlowData.todayUsageCount }}
                </el-tag>
              </div>
            </div>
          </div>
          
          <!-- 箭头指向库存更新 -->
          <div class="flow-arrow-down">
            <Icon icon="ep:arrow-down" class="text-16px" />
          </div>
          
          <!-- 第四行：库存更新 -->
          <div class="flow-row">
            <div class="flow-node end-node" @click="goToSparePartStock">
              <div class="node-icon">
                <Icon icon="ep:goods" class="text-20px" />
              </div>
              <div class="node-title">备件库存更新</div>
              <div class="node-count">{{ businessFlowData.stockUpdateCount || 0 }}</div>
              <div class="node-status">
                <el-tag size="small" type="warning" v-if="businessFlowData.lowStockCount > 0">
                  低库存: {{ businessFlowData.lowStockCount }}
                </el-tag>
              </div>
            </div>
          </div>
        </div>
        
        <!-- 业务流程统计 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">业务流程统计</div>
          <el-row :gutter="12">
            <el-col :span="6">
              <div class="text-center p-2 bg-blue-50 rounded">
                <div class="text-16px font-bold text-blue-600">{{ businessFlowData.totalProcessed || 0 }}</div>
                <div class="text-11px text-gray-500">已处理流程</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-green-50 rounded">
                <div class="text-16px font-bold text-green-600">{{ businessFlowData.completedToday || 0 }}</div>
                <div class="text-11px text-gray-500">今日完成</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-orange-50 rounded">
                <div class="text-16px font-bold text-orange-600">{{ businessFlowData.pendingCount || 0 }}</div>
                <div class="text-11px text-gray-500">待处理</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="text-center p-2 bg-red-50 rounded">
                <div class="text-16px font-bold text-red-600">{{ businessFlowData.urgentCount || 0 }}</div>
                <div class="text-11px text-gray-500">紧急任务</div>
              </div>
            </el-col>
          </el-row>
        </div>
        
        <!-- 快速操作 -->
        <div class="mt-4">
          <div class="text-14px text-gray-600 mb-2">快速操作</div>
          <el-row :gutter="8">
            <el-col :span="6">
              <el-button size="small" type="primary" @click="goToMaintenancePlan">
                <Icon icon="ep:plus" class="mr-1" />
                新建计划
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="warning" @click="goToRepairRequest">
                <Icon icon="ep:plus" class="mr-1" />
                新建报修
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="success" @click="goToSparePartUsageRecord">
                <Icon icon="ep:plus" class="mr-1" />
                使用记录
              </el-button>
            </el-col>
            <el-col :span="6">
              <el-button size="small" type="info" @click="goToSparePartStock">
                <Icon icon="ep:view" class="mr-1" />
                库存查看
              </el-button>
            </el-col>
          </el-row>
        </div>
      </div>
      
      <div v-else class="text-center text-gray-400 py-8">
        暂无业务流程数据
      </div>
    </el-skeleton>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { MaintenancePlanApi } from '@/api/coal/maintenanceplan'
import { RepairRequestApi } from '@/api/coal/repairrequest'
import { MaintenanceOrderApi } from '@/api/coal/maintenanceorder'
import { SparePartUsageRecordApi } from '@/api/coal/sparepartusagerecord'
import { SparePartStockApi } from '@/api/coal/sparepartstock'

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
const businessFlowData = ref(null)
const refreshTimer = ref<NodeJS.Timeout | null>(null)

// 路由
const router = useRouter()

// 获取业务流程数据
const getBusinessFlowData = async () => {
  try {
    loading.value = true
    console.log('开始获取业务流程数据...')
    
    // 并行获取各模块的统计数据
    const [
      maintenancePlanResponse,
      repairRequestResponse,
      maintenanceOrderResponse,
      sparePartUsageResponse,
      sparePartStockResponse
    ] = await Promise.all([
      MaintenancePlanApi.getMaintenancePlanPage({ pageNo: 1, pageSize: 1 }),
      RepairRequestApi.getRepairRequestPage({ pageNo: 1, pageSize: 1 }),
      MaintenanceOrderApi.getMaintenanceOrderPage({ pageNo: 1, pageSize: 1 }),
      SparePartUsageRecordApi.getSparePartUsageRecordPage({ pageNo: 1, pageSize: 1 }),
      SparePartStockApi.getSparePartStockPage({ pageNo: 1, pageSize: 1 })
    ])
    
    // 获取待执行的检修单数量
    const pendingMaintenanceResponse = await MaintenanceOrderApi.getMaintenanceOrderPage({ 
      pageNo: 1, 
      pageSize: 1,
      orderStatus: 1 // 待执行状态
    })
    
    // 获取今日的备件使用记录数量
    const today = new Date().toISOString().split('T')[0]
    const todayUsageResponse = await SparePartUsageRecordApi.getSparePartUsageRecordPage({
      pageNo: 1,
      pageSize: 1,
      usageDate: [today + ' 00:00:00', today + ' 23:59:59']
    })
    
    // 获取低库存数量
    const lowStockResponse = await SparePartStockApi.getSparePartStockPage({
      pageNo: 1,
      pageSize: 1,
      lowStockFilter: true
    })
    
    // 获取紧急报修单数量
    const urgentRepairResponse = await RepairRequestApi.getRepairRequestPage({
      pageNo: 1,
      pageSize: 1,
      urgencyLevel: 1 // 紧急级别
    })
    
    // 构建业务流程数据
    businessFlowData.value = {
      // 各模块数量
      maintenancePlanCount: maintenancePlanResponse.total || 0,
      repairRequestCount: repairRequestResponse.total || 0,
      maintenanceOrderCount: maintenanceOrderResponse.total || 0,
      sparePartUsageCount: sparePartUsageResponse.total || 0,
      stockUpdateCount: sparePartStockResponse.total || 0,
      
      // 状态统计
      pendingMaintenanceCount: pendingMaintenanceResponse.total || 0,
      todayUsageCount: todayUsageResponse.total || 0,
      lowStockCount: lowStockResponse.total || 0,
      urgentCount: urgentRepairResponse.total || 0,
      
      // 计算统计
      totalProcessed: (maintenanceOrderResponse.total || 0) + (sparePartUsageResponse.total || 0),
      completedToday: todayUsageResponse.total || 0,
      pendingCount: pendingMaintenanceResponse.total || 0
    }
    
    console.log('业务流程数据获取成功:', businessFlowData.value)
  } catch (error) {
    console.error('获取业务流程数据失败:', error)
    ElMessage.error('获取业务流程数据失败')
    
    // 设置默认数据
    businessFlowData.value = {
      maintenancePlanCount: 0,
      repairRequestCount: 0,
      maintenanceOrderCount: 0,
      sparePartUsageCount: 0,
      stockUpdateCount: 0,
      pendingMaintenanceCount: 0,
      todayUsageCount: 0,
      lowStockCount: 0,
      urgentCount: 0,
      totalProcessed: 0,
      completedToday: 0,
      pendingCount: 0
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
const goToMaintenancePlan = () => {
  router.push({ name: 'MaintenancePlan' })
}

const goToRepairRequest = () => {
  router.push({ name: 'RepairRequest' })
}

const goToMaintenanceOrder = () => {
  router.push({ name: 'MaintenanceOrder' })
}

const goToSparePartUsageRecord = () => {
  router.push({ name: 'SparePartUsageRecord' })
}

const goToSparePartStock = () => {
  router.push({ name: 'SparePartStock' })
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
  min-width: 120px;
  position: relative;
}

.flow-node:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.source-node {
  background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
  border: 2px solid #2196f3;
  color: #1976d2;
}

.center-node {
  background: linear-gradient(135deg, #f3e5f5 0%, #e1bee7 100%);
  border: 2px solid #9c27b0;
  color: #7b1fa2;
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
  margin-bottom: 4px;
}

.node-status {
  margin-top: 4px;
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
    min-width: 100px;
    padding: 12px;
  }
}
</style>
