<template>
  <el-card shadow="never" class="mt-8px">
    <template #header>
      <div class="flex items-center justify-between">
        <span class="text-lg font-medium">设备维护状态概览</span>
        <el-button link type="primary" @click="refreshData">
          <Icon icon="ep:refresh" class="mr-1" />
          刷新
        </el-button>
      </div>
    </template>
    
    <div v-loading="loading" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
      <!-- 待处理报修单 -->
      <div class="bg-blue-50 p-4 rounded-lg border border-blue-200">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-blue-600 font-medium">待处理报修单</p>
            <p class="text-2xl font-bold text-blue-800">{{ pendingRepairCount }}</p>
          </div>
          <Icon icon="ep:warning" class="text-3xl text-blue-500" />
        </div>
        <div class="mt-2">
          <el-button link type="primary" size="small" @click="goToRepairRequest">
            查看详情 <Icon icon="ep:arrow-right" class="ml-1" />
          </el-button>
        </div>
      </div>

      <!-- 进行中检修单 -->
      <div class="bg-orange-50 p-4 rounded-lg border border-orange-200">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-orange-600 font-medium">进行中检修单</p>
            <p class="text-2xl font-bold text-orange-800">{{ inProgressMaintenanceCount }}</p>
          </div>
          <Icon icon="ep:tools" class="text-3xl text-orange-500" />
        </div>
        <div class="mt-2">
          <el-button link type="primary" size="small" @click="goToMaintenanceOrder">
            查看详情 <Icon icon="ep:arrow-right" class="ml-1" />
          </el-button>
        </div>
      </div>

      <!-- 本月备件使用 -->
      <div class="bg-green-50 p-4 rounded-lg border border-green-200">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-green-600 font-medium">本月备件使用</p>
            <p class="text-2xl font-bold text-green-800">{{ monthlySparePartUsage }}</p>
          </div>
          <Icon icon="ep:box" class="text-3xl text-green-500" />
        </div>
        <div class="mt-2">
          <el-button link type="primary" size="small" @click="goToSparePartUsage">
            查看详情 <Icon icon="ep:arrow-right" class="ml-1" />
          </el-button>
        </div>
      </div>

      <!-- 设备完好率 -->
      <div class="bg-purple-50 p-4 rounded-lg border border-purple-200">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-sm text-purple-600 font-medium">设备完好率</p>
            <p class="text-2xl font-bold text-purple-800">{{ equipmentHealthRate }}%</p>
          </div>
          <Icon icon="ep:monitor" class="text-3xl text-purple-500" />
        </div>
        <div class="mt-2">
          <el-button link type="primary" size="small" @click="goToEquipmentInfo">
            查看详情 <Icon icon="ep:arrow-right" class="ml-1" />
          </el-button>
        </div>
      </div>
    </div>

    <!-- 最近报修单列表 -->
    <div class="mt-6">
      <h4 class="text-md font-medium mb-3">最近报修单</h4>
      <el-table :data="recentRepairRequests" size="small" stripe>
        <el-table-column prop="requestCode" label="报修单号" width="120" />
        <el-table-column prop="equipmentName" label="设备名称" />
        <el-table-column prop="faultDescription" label="故障描述" show-overflow-tooltip />
        <el-table-column prop="urgencyLevel" label="紧急程度" width="100">
          <template #default="scope">
            <el-tag :type="getUrgencyTagType(scope.row.urgencyLevel)" size="small">
              {{ getUrgencyText(scope.row.urgencyLevel) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="requestStatus" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusTagType(scope.row.requestStatus)" size="small">
              {{ getStatusText(scope.row.requestStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reportTime" label="报修时间" width="120">
          <template #default="scope">
            {{ formatDate(scope.row.reportTime) }}
          </template>
        </el-table-column>
      </el-table>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { RepairRequestApi } from '@/api/coal/repairrequest'
import { MaintenanceOrderApi } from '@/api/coal/maintenanceorder'
import { SparePartUsageRecordApi } from '@/api/coal/sparepartusagerecord'
import { EquipmentInfoApi } from '@/api/coal/equipmentinfo'

const router = useRouter()

// 响应式数据
const loading = ref(false)
const pendingRepairCount = ref(0)
const inProgressMaintenanceCount = ref(0)
const monthlySparePartUsage = ref(0)
const equipmentHealthRate = ref(0)
const recentRepairRequests = ref([])

// 获取数据
const fetchData = async () => {
  loading.value = true
  try {
    // 并行获取所有数据
    const [repairData, maintenanceData, usageData, equipmentData] = await Promise.all([
      // 获取待处理报修单数量
      RepairRequestApi.getRepairRequestPage({ 
        pageNo: 1, 
        pageSize: 1, 
        requestStatus: 1 
      }),
      // 获取进行中检修单数量
      MaintenanceOrderApi.getMaintenanceOrderPage({ 
        pageNo: 1, 
        pageSize: 1, 
        orderStatus: 2 
      }),
      // 获取本月备件使用数量
      SparePartUsageRecordApi.getSparePartUsageRecordPage({ 
        pageNo: 1, 
        pageSize: 1,
        usageDate: [
          new Date(new Date().getFullYear(), new Date().getMonth(), 1).toISOString(),
          new Date().toISOString()
        ]
      }),
      // 获取设备总数
      EquipmentInfoApi.getEquipmentInfoPage({ 
        pageNo: 1, 
        pageSize: 1 
      })
    ])

    // 设置数据
    pendingRepairCount.value = repairData.total || 0
    inProgressMaintenanceCount.value = maintenanceData.total || 0
    monthlySparePartUsage.value = usageData.total || 0
    
    // 计算设备完好率（简化计算）
    const totalEquipment = equipmentData.total || 1
    const faultyEquipment = repairData.total || 0
    equipmentHealthRate.value = Math.round(((totalEquipment - faultyEquipment) / totalEquipment) * 100)

    // 获取最近报修单
    const recentRepairResponse = await RepairRequestApi.getRepairRequestPage({
      pageNo: 1,
      pageSize: 5,
      orderBy: ['report_time desc']
    })
    recentRepairRequests.value = recentRepairResponse.list || []

  } catch (error) {
    console.error('获取设备维护概览数据失败:', error)
    ElMessage.error('获取数据失败')
  } finally {
    loading.value = false
  }
}

// 刷新数据
const refreshData = () => {
  fetchData()
}

// 页面跳转
const goToRepairRequest = () => {
  router.push('/coal/repairrequest')
}

const goToMaintenanceOrder = () => {
  router.push('/coal/maintenanceorder')
}

const goToSparePartUsage = () => {
  router.push('/coal/sparepartusagerecord')
}

const goToEquipmentInfo = () => {
  router.push('/coal/equipmentinfo')
}

// 工具函数
const getUrgencyTagType = (level: number) => {
  const types = { 1: 'success', 2: 'warning', 3: 'danger' }
  return types[level] || 'info'
}

const getUrgencyText = (level: number) => {
  const texts = { 1: '一般', 2: '紧急', 3: '非常紧急' }
  return texts[level] || '未知'
}

const getStatusTagType = (status: number) => {
  const types = { 1: 'warning', 2: 'primary', 3: 'success', 4: 'info' }
  return types[status] || 'info'
}

const getStatusText = (status: number) => {
  const texts = { 1: '待处理', 2: '已派单', 3: '处理中', 4: '已完成' }
  return texts[status] || '未知'
}

const formatDate = (date: string) => {
  if (!date) return '-'
  return new Date(date).toLocaleDateString()
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

.grid-cols-4 {
  grid-template-columns: repeat(4, minmax(0, 1fr));
}

.gap-4 {
  gap: 1rem;
}

@media (min-width: 768px) {
  .md\:grid-cols-2 {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (min-width: 1024px) {
  .lg\:grid-cols-4 {
    grid-template-columns: repeat(4, minmax(0, 1fr));
  }
}
</style>
