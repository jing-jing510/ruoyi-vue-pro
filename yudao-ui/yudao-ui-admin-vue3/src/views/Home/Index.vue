<template>
  <div>
    <!-- 业务模块卡片区域 -->
    <el-row :gutter="16">
      <!-- 左侧：生产调度和设备维护 -->
      <el-col :xl="12" :lg="12" :md="24" :sm="24" :xs="24" class="mb-16px">
        <!-- 生产调度业务流程卡片 -->
        <ProductionScheduleBusinessFlowCard :auto-refresh="true" />
        
        <!-- 设备维护业务流程卡片 -->
        <EquipmentMaintenanceBusinessFlowCard :auto-refresh="true" />
      </el-col>
      
      <!-- 右侧：煤质管理和安全管理 -->
      <el-col :xl="12" :lg="12" :md="24" :sm="24" :xs="24" class="mb-16px">
        <!-- 煤质管理业务流程卡片 -->
        <CoalQualityManagementBusinessFlowCard :auto-refresh="true" />
        
        <!-- 安全管理业务流程卡片 -->
        <SafetyManagementBusinessFlowCard :auto-refresh="true" />
      </el-col>
    </el-row>

    <!-- 第二行：备件管理和能源管理 -->
    <el-row :gutter="16">
      <!-- 左侧：备件管理 -->
      <el-col :xl="12" :lg="12" :md="24" :sm="24" :xs="24" class="mb-16px">
        <!-- 备件管理标题 -->
        <el-card shadow="never" class="mb-16px">
          <template #header>
            <div class="h-3 flex justify-between">
              <span>备件管理</span>
            </div>
          </template>
          
          <el-row :gutter="16">
            <!-- 备件库存统计卡片 -->
            <el-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24" class="mb-16px">
              <SparePartStockStatisticsCard :auto-refresh="true" />
            </el-col>
            
            <!-- 备件使用频率分析卡片 -->
            <el-col :xl="12" :lg="12" :md="12" :sm="24" :xs="24" class="mb-16px">
              <SparePartUsageFrequencyCard :auto-refresh="true" />
            </el-col>
          </el-row>
          
          <!-- 备件预警待处理卡片 -->
          <SparePartAlertCard :auto-refresh="true" />
        </el-card>
      </el-col>
      
      <!-- 右侧：能源管理 -->
      <el-col :xl="12" :lg="12" :md="24" :sm="24" :xs="24" class="mb-16px">
        <!-- 能源管理业务流程卡片 -->
        <EnergyManagementBusinessFlowCard :auto-refresh="true" />
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" setup>
import SparePartStockStatisticsCard from '@/components/SparePartStockStatisticsCard.vue'
import SparePartUsageFrequencyCard from '@/components/SparePartUsageFrequencyCard.vue'
import SparePartAlertCard from '@/components/SparePartAlertCard.vue'
import EquipmentMaintenanceBusinessFlowCard from '@/components/EquipmentMaintenanceBusinessFlowCard.vue'
import ProductionScheduleBusinessFlowCard from '@/components/ProductionScheduleBusinessFlowCard.vue'
import CoalQualityManagementBusinessFlowCard from '@/components/CoalQualityManagementBusinessFlowCard.vue'
import SafetyManagementBusinessFlowCard from '@/components/SafetyManagementBusinessFlowCard.vue'
import EnergyManagementBusinessFlowCard from '@/components/EnergyManagementBusinessFlowCard.vue'

defineOptions({ name: 'Index' })

</script>
