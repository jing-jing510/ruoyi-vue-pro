<template>
  <ContentWrap>
    <div class="statistics-container">
      <!-- 页面标题 -->
      <div class="page-header">
        <h2>备件库存统计分析</h2>
        <p>实时监控备件库存状况，为管理决策提供数据支持</p>
      </div>

      <!-- 库存概览卡片 -->
      <el-row :gutter="20" class="overview-cards">
        <el-col :span="6">
          <el-card class="statistics-card">
            <div class="card-content">
              <div class="card-icon total-types">
                <el-icon><Box /></el-icon>
              </div>
              <div class="card-info">
                <div class="card-title">备件种类</div>
                <div class="card-value">{{ statisticsData?.overview?.totalSparePartTypes || 0 }}</div>
                <div class="card-unit">种</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="statistics-card">
            <div class="card-content">
              <div class="card-icon total-quantity">
                <el-icon><Collection /></el-icon>
              </div>
              <div class="card-info">
                <div class="card-title">总库存数量</div>
                <div class="card-value">{{ formatNumber(statisticsData?.overview?.totalStockQuantity) }}</div>
                <div class="card-unit">件</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="statistics-card">
            <div class="card-content">
              <div class="card-icon total-value">
                <el-icon><Money /></el-icon>
              </div>
              <div class="card-info">
                <div class="card-title">总库存价值</div>
                <div class="card-value">{{ formatMoney(statisticsData?.overview?.totalStockValue) }}</div>
                <div class="card-unit">元</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="statistics-card">
            <div class="card-content">
              <div class="card-icon health-score">
                <el-icon><TrendCharts /></el-icon>
              </div>
              <div class="card-info">
                <div class="card-title">库存健康度</div>
                <div class="card-value">{{ statisticsData?.overview?.healthScore || 0 }}%</div>
                <div class="card-unit">评分</div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 预警统计卡片 -->
      <el-row :gutter="20" class="alert-cards">
        <el-col :span="6">
          <el-card class="alert-card low-stock">
            <div class="alert-content">
              <div class="alert-icon">
                <el-icon><Warning /></el-icon>
              </div>
              <div class="alert-info">
                <div class="alert-title">低库存预警</div>
                <div class="alert-value">{{ statisticsData?.alertStatistics?.lowStockCount || 0 }}</div>
                <div class="alert-desc">需要补货</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="alert-card zero-stock">
            <div class="alert-content">
              <div class="alert-icon">
                <el-icon><Close /></el-icon>
              </div>
              <div class="alert-info">
                <div class="alert-title">零库存</div>
                <div class="alert-value">{{ statisticsData?.alertStatistics?.zeroStockCount || 0 }}</div>
                <div class="alert-desc">急需采购</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="alert-card over-stock">
            <div class="alert-content">
              <div class="alert-icon">
                <el-icon><Plus /></el-icon>
              </div>
              <div class="alert-info">
                <div class="alert-title">超库存</div>
                <div class="alert-value">{{ statisticsData?.alertStatistics?.overStockCount || 0 }}</div>
                <div class="alert-desc">库存过多</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="alert-card stagnant-stock">
            <div class="alert-content">
              <div class="alert-icon">
                <el-icon><Clock /></el-icon>
              </div>
              <div class="alert-info">
                <div class="alert-title">呆滞库存</div>
                <div class="alert-value">{{ statisticsData?.alertStatistics?.stagnantStockCount || 0 }}</div>
                <div class="alert-desc">长期未动</div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 图表区域 -->
      <el-row :gutter="20" class="charts-row">
        <!-- 分类统计饼图 -->
        <el-col :span="12">
          <el-card class="chart-card">
            <template #header>
              <div class="card-header">
                <span>库存分类统计</span>
                <el-button type="text" @click="refreshData">刷新</el-button>
              </div>
            </template>
            <div ref="categoryChart" class="chart-container"></div>
          </el-card>
        </el-col>
        
        <!-- ABC分类统计 -->
        <el-col :span="12">
          <el-card class="chart-card">
            <template #header>
              <div class="card-header">
                <span>ABC分类分析</span>
                <el-button type="text" @click="refreshData">刷新</el-button>
              </div>
            </template>
            <div ref="abcChart" class="chart-container"></div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 趋势图表 -->
      <el-row :gutter="20" class="trend-row">
        <el-col :span="24">
          <el-card class="chart-card">
            <template #header>
              <div class="card-header">
                <span>库存趋势分析（最近30天）</span>
                <el-button type="text" @click="refreshData">刷新</el-button>
              </div>
            </template>
            <div ref="trendChart" class="chart-container trend-chart"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </ContentWrap>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { Box, Collection, Money, TrendCharts, Warning, Close, Plus, Clock } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import { SparePartInfoApi } from '@/api/coal/sparepartinfo'

// 统计数据
const statisticsData = ref<any>(null)
const loading = ref(false)

// 图表引用
const categoryChart = ref<HTMLElement>()
const abcChart = ref<HTMLElement>()
const trendChart = ref<HTMLElement>()

// 获取统计数据
const getStatisticsData = async () => {
  try {
    loading.value = true
    const response = await SparePartInfoApi.getStockStatistics()
    statisticsData.value = response.data
    await nextTick()
    initCharts()
  } catch (error) {
    console.error('获取统计数据失败:', error)
    ElMessage.error('获取统计数据失败')
  } finally {
    loading.value = false
  }
}

// 初始化图表
const initCharts = () => {
  initCategoryChart()
  initABCChart()
  initTrendChart()
}

// 分类统计饼图
const initCategoryChart = () => {
  if (!categoryChart.value || !statisticsData.value?.categoryStatistics) return
  
  const chart = echarts.init(categoryChart.value)
  const data = statisticsData.value.categoryStatistics.map((item: any) => ({
    name: item.categoryName || `分类${item.categoryId}`,
    value: item.stockValue || 0
  }))
  
  const option = {
    title: {
      text: '库存价值分布',
      left: 'center',
      textStyle: { fontSize: 14 }
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
    series: [{
      name: '库存价值',
      type: 'pie',
      radius: ['40%', '70%'],
      center: ['60%', '50%'],
      data: data,
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }]
  }
  
  chart.setOption(option)
}

// ABC分类统计柱状图
const initABCChart = () => {
  if (!abcChart.value || !statisticsData.value?.abcStatistics) return
  
  const chart = echarts.init(abcChart.value)
  const data = statisticsData.value.abcStatistics
  
  const option = {
    title: {
      text: 'ABC分类价值分析',
      left: 'center',
      textStyle: { fontSize: 14 }
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' }
    },
    xAxis: {
      type: 'category',
      data: data.map((item: any) => item.category)
    },
    yAxis: {
      type: 'value',
      name: '价值占比(%)'
    },
    series: [{
      name: '价值占比',
      type: 'bar',
      data: data.map((item: any) => item.valuePercentage || 0),
      itemStyle: {
        color: function(params: any) {
          const colors = ['#5470c6', '#91cc75', '#fac858']
          return colors[params.dataIndex] || '#5470c6'
        }
      }
    }]
  }
  
  chart.setOption(option)
}

// 趋势图表
const initTrendChart = () => {
  if (!trendChart.value || !statisticsData.value?.trendData) return
  
  const chart = echarts.init(trendChart.value)
  const data = statisticsData.value.trendData
  
  const option = {
    title: {
      text: '库存变化趋势',
      left: 'center',
      textStyle: { fontSize: 14 }
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['入库数量', '出库数量', '库存数量'],
      top: 30
    },
    xAxis: {
      type: 'category',
      data: data.map((item: any) => item.date)
    },
    yAxis: {
      type: 'value',
      name: '数量'
    },
    series: [
      {
        name: '入库数量',
        type: 'line',
        data: data.map((item: any) => item.inQuantity || 0),
        smooth: true,
        itemStyle: { color: '#67c23a' }
      },
      {
        name: '出库数量',
        type: 'line',
        data: data.map((item: any) => item.outQuantity || 0),
        smooth: true,
        itemStyle: { color: '#f56c6c' }
      },
      {
        name: '库存数量',
        type: 'line',
        data: data.map((item: any) => item.stockQuantity || 0),
        smooth: true,
        itemStyle: { color: '#409eff' }
      }
    ]
  }
  
  chart.setOption(option)
}

// 刷新数据
const refreshData = () => {
  getStatisticsData()
}

// 格式化数字
const formatNumber = (value: any) => {
  if (!value) return '0'
  return Number(value).toLocaleString()
}

// 格式化金额
const formatMoney = (value: any) => {
  if (!value) return '0.00'
  return Number(value).toLocaleString('zh-CN', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  })
}

// 页面加载时获取数据
onMounted(() => {
  getStatisticsData()
})
</script>

<style scoped>
.statistics-container {
  padding: 20px;
}

.page-header {
  margin-bottom: 30px;
  text-align: center;
}

.page-header h2 {
  margin: 0 0 10px 0;
  color: #303133;
  font-size: 24px;
  font-weight: 600;
}

.page-header p {
  margin: 0;
  color: #909399;
  font-size: 14px;
}

.overview-cards, .alert-cards {
  margin-bottom: 20px;
}

.statistics-card, .alert-card {
  height: 120px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-content, .alert-content {
  display: flex;
  align-items: center;
  height: 100%;
  padding: 20px;
}

.card-icon, .alert-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  font-size: 24px;
  color: white;
}

.total-types { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
.total-quantity { background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); }
.total-value { background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%); }
.health-score { background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%); }

.low-stock .alert-icon { background: linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%); color: #e6a23c; }
.zero-stock .alert-icon { background: linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%); color: #f56c6c; }
.over-stock .alert-icon { background: linear-gradient(135deg, #a8edea 0%, #fed6e3 100%); color: #409eff; }
.stagnant-stock .alert-icon { background: linear-gradient(135deg, #d299c2 0%, #fef9d7 100%); color: #909399; }

.card-info, .alert-info {
  flex: 1;
}

.card-title, .alert-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.card-value, .alert-value {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  line-height: 1;
  margin-bottom: 4px;
}

.card-unit, .alert-desc {
  font-size: 12px;
  color: #c0c4cc;
}

.charts-row, .trend-row {
  margin-bottom: 20px;
}

.chart-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 600;
  color: #303133;
}

.chart-container {
  height: 300px;
  width: 100%;
}

.trend-chart {
  height: 400px;
}
</style>
