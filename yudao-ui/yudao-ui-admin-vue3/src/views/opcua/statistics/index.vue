<template>
  <ContentWrap>
    <!-- 查询条件 -->
    <el-form :model="queryParams" :inline="true" label-width="100px" class="-mb-15px">
      <el-form-item label="统计类型">
        <el-select
          v-model="queryParams.type"
          placeholder="请选择统计类型"
          @change="handleTypeChange"
          class="!w-240px"
        >
          <el-option label="当班统计" value="shift" />
          <el-option label="日统计" value="day" />
          <el-option label="月统计" value="month" />
          <el-option label="年统计" value="year" />
        </el-select>
      </el-form-item>
      <el-form-item label="时间范围">
        <el-date-picker
          v-model="timeRange"
          type="datetimerange"
          range-separator="-"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          value-format="YYYY-MM-DD HH:mm:ss"
          @change="handleQuery"
          class="!w-340px"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">
          <Icon icon="ep:search" class="mr-5px" /> 查询
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 统计卡片 -->
  <el-row :gutter="20" class="mt-4">
    <el-col :span="6">
      <el-card shadow="hover">
        <div class="stat-card">
          <div class="stat-icon" style="background: #409eff">
            <Icon icon="ep:warning" :size="40" color="#fff" />
          </div>
          <div class="stat-content">
            <div class="stat-title">总报警数</div>
            <div class="stat-value">{{ statistics.totalCount || 0 }}</div>
          </div>
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover">
        <div class="stat-card">
          <div class="stat-icon" style="background: #f56c6c">
            <Icon icon="ep:circle-close" :size="40" color="#fff" />
          </div>
          <div class="stat-content">
            <div class="stat-title">待处理</div>
            <div class="stat-value">{{ getStatusCount(0) }}</div>
          </div>
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover">
        <div class="stat-card">
          <div class="stat-icon" style="background: #67c23a">
            <Icon icon="ep:circle-check" :size="40" color="#fff" />
          </div>
          <div class="stat-content">
            <div class="stat-title">已处理</div>
            <div class="stat-value">{{ getStatusCount(1) }}</div>
          </div>
        </div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card shadow="hover">
        <div class="stat-card">
          <div class="stat-icon" style="background: #909399">
            <Icon icon="ep:finished" :size="40" color="#fff" />
          </div>
          <div class="stat-content">
            <div class="stat-title">处理率</div>
            <div class="stat-value">{{ getProcessRate() }}%</div>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>

  <!-- 图表 -->
  <el-row :gutter="20" class="mt-4">
    <el-col :span="12">
      <ContentWrap>
        <template #title>按级别统计</template>
        <div ref="levelChartRef" style="height: 400px"></div>
      </ContentWrap>
    </el-col>
    <el-col :span="12">
      <ContentWrap>
        <template #title>按状态统计</template>
        <div ref="statusChartRef" style="height: 400px"></div>
      </ContentWrap>
    </el-col>
  </el-row>

  <!-- 设备报警排行 -->
  <ContentWrap class="mt-4">
    <template #title>设备报警排行 TOP 10</template>
    <el-table :data="deviceRankList" style="width: 100%">
      <el-table-column type="index" label="排名" width="80" align="center" />
      <el-table-column prop="deviceName" label="设备名称" min-width="200" />
      <el-table-column prop="count" label="报警次数" width="150" align="center">
        <template #default="scope">
          <el-tag type="danger">{{ scope.row.count }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="占比" width="200" align="center">
        <template #default="scope">
          <el-progress :percentage="getPercentage(scope.row.count)" />
        </template>
      </el-table-column>
    </el-table>
  </ContentWrap>
</template>

<script setup lang="ts" name="OpcuaStatistics">
import { getAlarmStatistics } from '@/api/opcua/alarm'
import * as echarts from 'echarts'

const queryParams = ref({
  type: 'day',
  startTime: '',
  endTime: ''
})

const timeRange = ref<any[]>([])
const statistics = ref<any>({})
const deviceRankList = ref<any[]>([])
const levelChartRef = ref<HTMLElement>()
const statusChartRef = ref<HTMLElement>()
let levelChart: echarts.ECharts | null = null
let statusChart: echarts.ECharts | null = null

/** 初始化时间范围 */
const initTimeRange = () => {
  const now = new Date()
  const start = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 0, 0, 0)
  const end = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 23, 59, 59)
  timeRange.value = [
    start.toISOString().slice(0, 19).replace('T', ' '),
    end.toISOString().slice(0, 19).replace('T', ' ')
  ]
}

/** 统计类型改变 */
const handleTypeChange = () => {
  const now = new Date()
  let start: Date, end: Date

  switch (queryParams.value.type) {
    case 'shift':
      start = new Date(now.getTime() - 8 * 60 * 60 * 1000)
      end = now
      break
    case 'day':
      start = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 0, 0, 0)
      end = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 23, 59, 59)
      break
    case 'month':
      start = new Date(now.getFullYear(), now.getMonth(), 1, 0, 0, 0)
      end = new Date(now.getFullYear(), now.getMonth() + 1, 0, 23, 59, 59)
      break
    case 'year':
      start = new Date(now.getFullYear(), 0, 1, 0, 0, 0)
      end = new Date(now.getFullYear(), 11, 31, 23, 59, 59)
      break
    default:
      start = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 0, 0, 0)
      end = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 23, 59, 59)
  }

  timeRange.value = [
    start.toISOString().slice(0, 19).replace('T', ' '),
    end.toISOString().slice(0, 19).replace('T', ' ')
  ]

  handleQuery()
}

/** 查询统计数据 */
const handleQuery = async () => {
  if (timeRange.value && timeRange.value.length === 2) {
    queryParams.value.startTime = timeRange.value[0]
    queryParams.value.endTime = timeRange.value[1]

    const data = await getAlarmStatistics(queryParams.value)
    statistics.value = data
    deviceRankList.value = data.deviceStatistics?.slice(0, 10) || []

    // 更新图表
    updateLevelChart()
    updateStatusChart()
  }
}

/** 获取状态数量 */
const getStatusCount = (status: number) => {
  if (!statistics.value.statusStatistics) return 0
  const item = statistics.value.statusStatistics.find((s: any) => s.status === status)
  return item ? item.count : 0
}

/** 计算处理率 */
const getProcessRate = () => {
  const total = statistics.value.totalCount || 0
  if (total === 0) return 0
  const processed = getStatusCount(1)
  return Math.round((processed / total) * 100)
}

/** 计算百分比 */
const getPercentage = (count: number) => {
  if (!statistics.value.totalCount || statistics.value.totalCount === 0) return 0
  return Math.round((count / statistics.value.totalCount) * 100)
}

/** 更新级别图表 */
const updateLevelChart = () => {
  if (!levelChartRef.value) return
  if (!levelChart) {
    levelChart = echarts.init(levelChartRef.value)
  }

  const levelNames = ['提示', '警告', '错误', '严重']
  const levelData = statistics.value.levelStatistics || []

  const data = levelNames.map((name, index) => {
    const item = levelData.find((d: any) => d.alarmLevel === index + 1)
    return {
      name: name,
      value: item ? item.count : 0
    }
  })

  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        type: 'pie',
        radius: '50%',
        data: data,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }

  levelChart.setOption(option)
}

/** 更新状态图表 */
const updateStatusChart = () => {
  if (!statusChartRef.value) return
  if (!statusChart) {
    statusChart = echarts.init(statusChartRef.value)
  }

  const statusNames = ['待处理', '已处理']
  const statusData = statistics.value.statusStatistics || []

  const data = statusNames.map((name, index) => {
    const item = statusData.find((d: any) => d.status === index)
    return item ? item.count : 0
  })

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: statusNames
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        type: 'bar',
        data: data,
        itemStyle: {
          color: function (params: any) {
            const colors = ['#f56c6c', '#67c23a']
            return colors[params.dataIndex]
          }
        }
      }
    ]
  }

  statusChart.setOption(option)
}

/** 初始化 **/
onMounted(() => {
  initTimeRange()
  handleQuery()

  // 监听窗口大小变化
  window.addEventListener('resize', () => {
    levelChart?.resize()
    statusChart?.resize()
  })
})

onUnmounted(() => {
  levelChart?.dispose()
  statusChart?.dispose()
})
</script>

<style scoped lang="scss">
.stat-card {
  display: flex;
  align-items: center;
}

.stat-icon {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  margin-right: 20px;
}

.stat-content {
  flex: 1;
}

.stat-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}
</style>
