<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="检修单编号" prop="orderCode">
        <el-input
          v-model="queryParams.orderCode"
          placeholder="请输入检修单编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="关联计划" prop="planId">
        <el-select
          v-model="queryParams.planId"
          placeholder="请选择关联计划"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="plan in planList"
            :key="plan.id"
            :label="plan.planName"
            :value="plan.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备" prop="equipmentId">
        <el-select
          v-model="queryParams.equipmentId"
          placeholder="请选择设备"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="equipment in equipmentList"
            :key="equipment.id"
            :label="equipment.equipmentName"
            :value="equipment.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检修类型" prop="maintenanceType">
        <el-select
          v-model="queryParams.maintenanceType"
          placeholder="请选择检修类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检修级别" prop="maintenanceLevel">
        <el-select
          v-model="queryParams.maintenanceLevel"
          placeholder="请选择检修级别"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检修单状态" prop="orderStatus">
        <el-select
          v-model="queryParams.orderStatus"
          placeholder="请选择检修单状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_ORDER_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="优先级" prop="priorityLevel">
        <el-select
          v-model="queryParams.priorityLevel"
          placeholder="请选择优先级"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.PRIORITY_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="负责人" prop="responsiblePerson">
        <el-select
          v-model="queryParams.responsiblePerson"
          placeholder="请选择负责人"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.nickname"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="实际开始时间" prop="startTime">
        <el-date-picker
          v-model="queryParams.startTime"
          value-format="YYYY-MM-DD"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['coal:maintenance-order:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:maintenance-order:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:maintenance-order:delete']"
        >
          <Icon icon="ep:delete" class="mr-5px" /> 批量删除
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 主表列表 -->
  <ContentWrap>
    <el-table
        row-key="id"
        v-loading="loading"
        :data="list"
        :stripe="true"
        :show-overflow-tooltip="true"
        highlight-current-row
        @current-change="handleCurrentChange"
        @selection-change="handleRowCheckboxChange"
    >
    <el-table-column type="selection" width="55" />
      <el-table-column label="检修单ID" align="center" prop="id" />
      <el-table-column label="检修单编号" align="center" prop="orderCode" />
      <el-table-column label="关联计划" align="center" prop="planId">
        <template #default="scope">
          {{ getPlanName(scope.row.planId) }}
        </template>
      </el-table-column>
      <el-table-column label="关联报修单" align="center" prop="repairRequestId">
        <template #default="scope">
          {{ getRepairRequestName(scope.row.repairRequestId) }}
        </template>
      </el-table-column>
      <el-table-column label="设备" align="center" prop="equipmentId">
        <template #default="scope">
          {{ getEquipmentName(scope.row.equipmentId) }}
        </template>
      </el-table-column>
      <el-table-column label="检修类型" align="center" prop="maintenanceType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.MAINTENANCE_TYPE" :value="scope.row.maintenanceType" />
        </template>
      </el-table-column>
      <el-table-column label="检修级别" align="center" prop="maintenanceLevel">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.MAINTENANCE_LEVEL" :value="scope.row.maintenanceLevel" />
        </template>
      </el-table-column>
      <el-table-column label="检修单状态" align="center" prop="orderStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.MAINTENANCE_ORDER_STATUS" :value="scope.row.orderStatus" />
        </template>
      </el-table-column>
      <el-table-column label="优先级" align="center" prop="priorityLevel">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.PRIORITY_LEVEL" :value="scope.row.priorityLevel" />
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="responsiblePerson" />
      <el-table-column label="负责班组" align="center" prop="responsibleTeam" />
      <el-table-column
        label="实际开始时间"
        align="center"
        prop="startTime"
        width="180px"
      >
        <template #default="scope">
          {{ formatDateTime(scope.row.startTime) }}
        </template>
      </el-table-column>
      <el-table-column
        label="实际结束时间"
        align="center"
        prop="endTime"
        width="180px"
      >
        <template #default="scope">
          {{ formatDateTime(scope.row.endTime) }}
        </template>
      </el-table-column>
      <el-table-column label="实际工期(小时)" align="center" prop="actualDuration" />
      <el-table-column label="完成进度(%)" align="center" prop="completionRate" />
      <el-table-column label="质量评级" align="center" prop="qualityRating">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.QUALITY_RATING" :value="scope.row.qualityRating" />
        </template>
      </el-table-column>
      <el-table-column label="安全评级" align="center" prop="safetyRating">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.SAFETY_RATING" :value="scope.row.safetyRating" />
        </template>
      </el-table-column>
      <el-table-column label="总费用" align="center" prop="totalCost" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" width="280px" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['coal:maintenance-order:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="success"
            @click="handleManageSpareParts(scope.row)"
            v-hasPermi="['coal:spare-part-usage-record:create']"
          >
            备件使用记录
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:maintenance-order:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <MaintenanceOrderForm ref="formRef" @success="getList" />
  
  <!-- 子表的列表 -->
  <ContentWrap>
    <el-tabs model-value="maintenanceOrderItem">
      <el-tab-pane label="检修项目明细 (子表)" name="maintenanceOrderItem">
        <MaintenanceOrderItemList :order-id="currentRow.id" />
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { MaintenanceOrderApi, MaintenanceOrder } from '@/api/coal/maintenanceorder'
import { SparePartUsageRecordApi } from '@/api/coal/sparepartusagerecord'
import { EquipmentInfoApi } from '@/api/coal/equipmentinfo'
import { MaintenancePlanApi } from '@/api/coal/maintenanceplan'
import { RepairRequestApi } from '@/api/coal/repairrequest'
import { getSimpleUserList } from '@/api/system/user'
import MaintenanceOrderForm from './MaintenanceOrderForm.vue'
import MaintenanceOrderItemList from './components/MaintenanceOrderItemList.vue'
import { useRouter } from 'vue-router'

/** 检修单 列表 */
defineOptions({ name: 'MaintenanceOrder' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const router = useRouter() // 路由

const loading = ref(true) // 列表的加载中
const list = ref<MaintenanceOrder[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数

// 设备列表、用户列表、计划列表和报修单列表
const equipmentList = ref<any[]>([])
const userList = ref<any[]>([])
const planList = ref<any[]>([])
const repairRequestList = ref<any[]>([])

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orderCode: undefined,
  planId: undefined,
  equipmentId: undefined,
  maintenanceType: undefined,
  maintenanceLevel: undefined,
  orderStatus: undefined,
  priorityLevel: undefined,
  responsiblePerson: undefined,
  startTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 获取设备列表 */
const getEquipmentList = async () => {
  try {
    const response = await EquipmentInfoApi.getEquipmentInfoList({})
    equipmentList.value = response.data || response || []
  } catch (error) {
    console.error('获取设备列表失败:', error)
  }
}

/** 获取用户列表 */
const getUserList = async () => {
  try {
    const response = await getSimpleUserList()
    userList.value = response || []
  } catch (error) {
    console.error('获取用户列表失败:', error)
  }
}

/** 获取计划列表 */
const getPlanList = async () => {
  try {
    const response = await MaintenancePlanApi.getMaintenancePlanPage({ pageNo: 1, pageSize: 100 })
    planList.value = response.list || []
  } catch (error) {
    console.error('获取计划列表失败:', error)
  }
}

/** 获取报修单列表 */
const getRepairRequestList = async () => {
  try {
    const response = await RepairRequestApi.getRepairRequestPage({ pageNo: 1, pageSize: 100 })
    repairRequestList.value = response.list || []
  } catch (error) {
    console.error('获取报修单列表失败:', error)
  }
}

/** 根据设备ID获取设备名称 */
const getEquipmentName = (equipmentId: number) => {
  const equipment = equipmentList.value.find(eq => eq.id === equipmentId)
  return equipment ? equipment.equipmentName : `设备ID:${equipmentId}`
}

/** 根据计划ID获取计划名称 */
const getPlanName = (planId: number) => {
  const plan = planList.value.find(p => p.id === planId)
  return plan ? plan.planName : `计划ID:${planId}`
}

/** 根据报修单ID获取报修单编号 */
const getRepairRequestName = (repairRequestId: number) => {
  if (!repairRequestId) return '-'
  const repair = repairRequestList.value.find(r => r.id === repairRequestId)
  if (repair) {
    return repair.requestCode || `报修单ID:${repairRequestId}`
  }
  // 如果找不到，返回ID，不打印日志避免控制台噪音
  return `报修单ID:${repairRequestId}`
}

/** 格式化日期时间显示 */
const formatDateTime = (dateTime: string | Date) => {
  if (!dateTime) return '-'
  const date = new Date(dateTime)
  if (isNaN(date.getTime())) return '-'
  
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  const seconds = String(date.getSeconds()).padStart(2, '0')
  
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    // 确保基础数据已加载
    if (equipmentList.value.length === 0) {
      await getEquipmentList()
    }
    if (userList.value.length === 0) {
      await getUserList()
    }
    if (planList.value.length === 0) {
      await getPlanList()
    }
    if (repairRequestList.value.length === 0) {
      await getRepairRequestList()
    }
    
    const data = await MaintenanceOrderApi.getMaintenanceOrderPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await MaintenanceOrderApi.deleteMaintenanceOrder(id)
    message.success(t('common.delSuccess'))
    currentRow.value = {} as MaintenanceOrder
    // 刷新列表
    await getList()
  } catch {}
}

/** 管理备件使用记录 */
const handleManageSpareParts = (maintenanceOrder: MaintenanceOrder) => {
  // 跳转到备件使用记录页面，并传递检修单ID作为查询条件
  console.log('跳转到备件使用记录页面，检修单ID:', maintenanceOrder.id, '设备ID:', maintenanceOrder.equipmentId)
  // 使用name方式跳转，避免路径问题
  router.push({
    name: 'SparePartUsageRecord',
    query: {
      workOrderId: maintenanceOrder.id,
      equipmentId: maintenanceOrder.equipmentId
    }
  })
}

/** 批量删除检修单 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await MaintenanceOrderApi.deleteMaintenanceOrderList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: MaintenanceOrder[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await MaintenanceOrderApi.exportMaintenanceOrder(queryParams)
    download.excel(data, '检修单.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 选中行操作 */
const currentRow = ref<MaintenanceOrder>({} as MaintenanceOrder) // 选中行
const handleCurrentChange = (row: MaintenanceOrder) => {
  currentRow.value = row
}

/** 初始化 **/
onMounted(async () => {
  // 先获取基础数据
  await Promise.all([
    getEquipmentList(),
    getUserList(),
    getPlanList(),
    getRepairRequestList()
  ])
  // 再获取列表数据
  await getList()
})
</script>