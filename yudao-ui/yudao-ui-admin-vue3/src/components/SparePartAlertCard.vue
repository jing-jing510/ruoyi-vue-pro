<template>
  <el-card shadow="never" class="mt-8px">
    <template #header>
      <div class="h-3 flex justify-between">
        <span>备件预警待处理</span>
        <el-badge :value="pendingAlerts.length" :hidden="pendingAlerts.length === 0">
          <Icon icon="ep:bell" class="text-16px text-gray-400" />
        </el-badge>
      </div>
    </template>

    <el-skeleton :loading="loading" animated>
      <div v-if="pendingAlerts.length === 0" class="text-center text-gray-400 py-8">
        暂无待处理预警
      </div>
      
      <div v-else>
        <!-- 预警列表 -->
        <div class="space-y-1">
          <div 
            v-for="alert in pendingAlerts.slice(0, 3)" 
            :key="alert.id" 
            class="flex justify-between items-center p-2 bg-gray-50 rounded"
          >
            <div class="flex-1 min-w-0">
              <div class="flex items-center gap-2 mb-1">
                <el-tag 
                  :type="getAlertLevelType(alert.alertLevel)" 
                  size="small"
                >
                  {{ getAlertLevelName(alert.alertLevel) }}
                </el-tag>
                <span class="text-12px font-medium text-gray-800 truncate">{{ alert.alertTitle }}</span>
              </div>
              <div class="text-11px text-gray-600 truncate">
                {{ alert.alertMessage }}
              </div>
            </div>
            
            <div class="ml-2 flex-shrink-0">
              <el-button 
                type="success" 
                size="small" 
                :loading="sendingIds.includes(alert.id)"
                @click="handleSendAlert(alert)"
              >
                发送
              </el-button>
            </div>
          </div>
        </div>
        
        <!-- 查看更多 -->
        <div v-if="pendingAlerts.length > 3" class="text-center mt-2">
          <el-button 
            type="text" 
            size="small" 
            @click="goToAlertList"
          >
            查看全部 {{ pendingAlerts.length }} 条预警
          </el-button>
        </div>
      </div>
    </el-skeleton>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { SparePartAlertApi } from '@/api/coal/sparepartalert'
import { SparePartInfoApi } from '@/api/coal/sparepartinfo'
import { dateFormatter } from '@/utils/formatTime'

// Props
interface Props {
  autoRefresh?: boolean
  refreshInterval?: number
}

const props = withDefaults(defineProps<Props>(), {
  autoRefresh: true,
  refreshInterval: 300000 // 5分钟
})

// 响应式数据
const loading = ref(false)
const pendingAlerts = ref([])
const sendingIds = ref([])
const refreshTimer = ref<NodeJS.Timeout | null>(null)

// 路由
const router = useRouter()

// 获取备件列表用于名称映射
const sparePartList = ref([])

// 获取备件列表
const getSparePartList = async () => {
  try {
    const response = await SparePartInfoApi.getSimpleSparePartList()
    sparePartList.value = response.data || response || []
  } catch (error) {
    console.error('获取备件列表失败:', error)
  }
}

// 获取待处理预警列表
const getPendingAlerts = async () => {
  try {
    loading.value = true
    console.log('开始获取预警列表...')
    const response = await SparePartAlertApi.getSparePartAlertPage({
      pageNo: 1,
      pageSize: 10,
      isSent: false // 只获取未发送的预警
    })
    
    console.log('预警API响应:', response)
    
    // 处理不同的响应数据结构
    let alertList = []
    if (response.data && response.data.list) {
      alertList = response.data.list
    } else if (response.list) {
      alertList = response.list
    } else if (Array.isArray(response)) {
      alertList = response
    }
    
    console.log('解析出的预警列表:', alertList)
    
    // 为预警记录添加备件名称
    const alertsWithNames = alertList.map(alert => {
      const sparePart = sparePartList.value.find(sp => sp.id === alert.sparePartId)
      return {
        ...alert,
        sparePartName: sparePart ? sparePart.sparePartName : `备件ID:${alert.sparePartId}`
      }
    })
    
    pendingAlerts.value = alertsWithNames
    console.log('最终预警列表:', pendingAlerts.value)
  } catch (error) {
    console.error('获取预警列表失败:', error)
    ElMessage.error('获取预警列表失败: ' + error.message)
  } finally {
    loading.value = false
  }
}

// 发送预警通知
const handleSendAlert = async (alert: any) => {
  try {
    // 检查是否有接收人
    if (!alert.recipients || alert.recipients.trim() === '') {
      ElMessage.warning('该预警没有设置接收人')
      return
    }

    // 确认发送
    await ElMessageBox.confirm(
      `确定要发送预警通知 "${alert.alertTitle}" 吗？`,
      '确认发送',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    // 添加到发送中列表
    sendingIds.value.push(alert.id)

    // 调用发送API
    await SparePartAlertApi.sendNotification(alert.id)
    
    ElMessage.success('预警通知发送成功')
    
    // 刷新列表
    await getPendingAlerts()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('发送预警通知失败:', error)
      ElMessage.error('发送预警通知失败: ' + error.message)
    }
  } finally {
    // 从发送中列表移除
    const index = sendingIds.value.indexOf(alert.id)
    if (index > -1) {
      sendingIds.value.splice(index, 1)
    }
  }
}

// 获取预警级别类型
const getAlertLevelType = (level: number) => {
  const levelMap: Record<number, string> = {
    1: 'danger',   // 高
    2: 'warning',  // 中
    3: 'info'      // 低
  }
  return levelMap[level] || 'info'
}

// 获取预警级别名称
const getAlertLevelName = (level: number) => {
  const levelMap: Record<number, string> = {
    1: '高',
    2: '中', 
    3: '低'
  }
  return levelMap[level] || '未知'
}

// 格式化时间
const formatTime = (time: string) => {
  return dateFormatter(time, 'MM-dd HH:mm')
}

// 跳转到预警列表页面
const goToAlertList = () => {
  router.push('/coal/sparepartalert')
}

// 启动自动刷新
const startAutoRefresh = () => {
  if (props.autoRefresh && !refreshTimer.value) {
    refreshTimer.value = setInterval(() => {
      getPendingAlerts()
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
onMounted(async () => {
  // 添加测试数据
  pendingAlerts.value = [
    {
      id: 1,
      alertTitle: '测试预警1',
      alertMessage: '这是一个测试预警消息',
      alertLevel: 2,
      currentStock: 5,
      thresholdValue: 10,
      createTime: new Date().toISOString(),
      sparePartName: '测试备件1'
    },
    {
      id: 2,
      alertTitle: '测试预警2',
      alertMessage: '这是另一个测试预警消息',
      alertLevel: 1,
      currentStock: 2,
      thresholdValue: 8,
      createTime: new Date().toISOString(),
      sparePartName: '测试备件2'
    }
  ]
  
  await getSparePartList() // 先获取备件列表
  await getPendingAlerts() // 再获取预警列表
  startAutoRefresh()
})

onUnmounted(() => {
  stopAutoRefresh()
})
</script>

