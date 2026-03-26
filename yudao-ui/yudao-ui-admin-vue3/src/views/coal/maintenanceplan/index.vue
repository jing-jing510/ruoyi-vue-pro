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
      <el-form-item label="计划编号" prop="planCode">
        <el-input
          v-model="queryParams.planCode"
          placeholder="请输入计划编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="计划名称" prop="planName">
        <el-input
          v-model="queryParams.planName"
          placeholder="请输入计划名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="计划类型" prop="planType">
        <el-select
          v-model="queryParams.planType"
          placeholder="请选择计划类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_PLAN_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="计划状态" prop="planStatus">
        <el-select
          v-model="queryParams.planStatus"
          placeholder="请选择计划状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_PLAN_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
      <el-form-item label="计划开始时间" prop="plannedStartTime">
        <el-date-picker
          v-model="queryParams.plannedStartTime"
          value-format="YYYY-MM-DD"
          type="date"
          placeholder="选择计划开始时间"
          clearable
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="计划结束时间" prop="plannedEndTime">
        <el-date-picker
          v-model="queryParams.plannedEndTime"
          value-format="YYYY-MM-DD"
          type="date"
          placeholder="选择计划结束时间"
          clearable
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['coal:maintenance-plan:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:maintenance-plan:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:maintenance-plan:delete']"
        >
          <Icon icon="ep:delete" class="mr-5px" /> 批量删除
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table
        row-key="id"
        v-loading="loading"
        :data="list"
        :stripe="true"
        :show-overflow-tooltip="true"
        @selection-change="handleRowCheckboxChange"
    >
    <el-table-column type="selection" width="55" />
      <el-table-column label="计划ID" align="center" prop="id" />
      <el-table-column label="计划编号" align="center" prop="planCode" />
      <el-table-column label="计划名称" align="center" prop="planName" />
      <el-table-column label="计划类型" align="center" prop="planType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.MAINTENANCE_PLAN_TYPE" :value="scope.row.planType" />
        </template>
      </el-table-column>
      <el-table-column label="计划状态" align="center" prop="planStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.MAINTENANCE_PLAN_STATUS" :value="scope.row.planStatus" />
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
      <el-table-column
        label="计划开始时间"
        align="center"
        prop="plannedStartTime"
        width="180px"
      >
        <template #default="scope">
          {{ formatDateTime(scope.row.plannedStartTime) }}
        </template>
      </el-table-column>
      <el-table-column
        label="计划结束时间"
        align="center"
        prop="plannedEndTime"
        width="180px"
      >
        <template #default="scope">
          {{ formatDateTime(scope.row.plannedEndTime) }}
        </template>
      </el-table-column>
      <el-table-column label="预计工期(小时)" align="center" prop="estimatedDuration" />
      <el-table-column label="负责人" align="center" prop="responsiblePerson" />
      <el-table-column label="负责班组" align="center" prop="responsibleTeam" />
      <el-table-column label="检修内容" align="center" prop="maintenanceContent" />
      <el-table-column label="安全要求" align="center" prop="safetyRequirements" />
      <el-table-column label="所需工具" align="center" prop="requiredTools" />
      <el-table-column label="所需材料" align="center" prop="requiredMaterials" />
      <el-table-column label="预算费用" align="center" prop="budgetCost" />
      <el-table-column label="实际费用" align="center" prop="actualCost" />
      <el-table-column label="审批状态" align="center" prop="approvalStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.APPROVE_STATUS" :value="scope.row.approvalStatus" />
        </template>
      </el-table-column>
      <el-table-column label="审批人" align="center" prop="approverId">
        <template #default="scope">
          {{ getApproverName(scope.row.approverId) }}
        </template>
      </el-table-column>
      <el-table-column
        label="审批时间"
        align="center"
        prop="approvalTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="审批备注" align="center" prop="approvalRemark" />
      <el-table-column label="备注" align="center" prop="remark" />
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
            v-hasPermi="['coal:maintenance-plan:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="success"
            @click="handleCreateMaintenanceOrder(scope.row)"
            v-hasPermi="['coal:maintenance-order:create']"
            v-if="scope.row.approvalStatus === 2"
          >
            生成检修单
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:maintenance-plan:delete']"
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
  <MaintenancePlanForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { MaintenancePlanApi, MaintenancePlan } from '@/api/coal/maintenanceplan'
import { MaintenanceOrderApi } from '@/api/coal/maintenanceorder'
import { EquipmentInfoApi } from '@/api/coal/equipmentinfo'
import { getSimpleUserList } from '@/api/system/user'
import MaintenancePlanForm from './MaintenancePlanForm.vue'

/** 检修计划 列表 */
defineOptions({ name: 'MaintenancePlan' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<MaintenancePlan[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数

// 设备列表和用户列表
const equipmentList = ref([])
const userList = ref([])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  planCode: undefined,
  planName: undefined,
  planType: undefined,
  planStatus: undefined,
  equipmentId: undefined,
  maintenanceType: undefined,
  maintenanceLevel: undefined,
  plannedStartTime: undefined,
  plannedStartTime: [],
  plannedEndTime: undefined,
  plannedEndTime: [],
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
    userList.value = response.data || response || []
  } catch (error) {
    console.error('获取用户列表失败:', error)
  }
}

/** 根据设备ID获取设备名称 */
const getEquipmentName = (equipmentId: number) => {
  const equipment = equipmentList.value.find(eq => eq.id === equipmentId)
  return equipment ? equipment.equipmentName : `设备ID:${equipmentId}`
}

/** 根据用户ID获取用户昵称 */
const getApproverName = (userId: number) => {
  const user = userList.value.find(user => user.id === userId)
  return user ? user.nickname : `用户ID:${userId}`
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MaintenancePlanApi.getMaintenancePlanPage(queryParams)
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
    await MaintenancePlanApi.deleteMaintenancePlan(id)
    message.success(t('common.delSuccess'))
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除检修计划 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await MaintenancePlanApi.deleteMaintenancePlanList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: MaintenancePlan[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await MaintenancePlanApi.exportMaintenancePlan(queryParams)
    download.excel(data, '检修计划.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 生成检修单 */
const handleCreateMaintenanceOrder = async (maintenancePlan: MaintenancePlan) => {
  try {
    // 确认生成检修单
    await ElMessageBox.confirm(
      `是否确认为检修计划"${maintenancePlan.planName}"生成检修单？`,
      '系统提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }
    )

    // 构建检修单数据
    const maintenanceOrderData = {
      orderCode: `MO${new Date().getFullYear()}${String(new Date().getMonth() + 1).padStart(2, '0')}${String(new Date().getDate()).padStart(2, '0')}${String(Date.now()).slice(-4)}`,
      planId: maintenancePlan.id,
      equipmentId: maintenancePlan.equipmentId,
      equipmentName: maintenancePlan.equipmentName,
      maintenanceType: maintenancePlan.maintenanceType,
      maintenanceLevel: maintenancePlan.maintenanceLevel,
      orderStatus: 1, // 待执行
      priorityLevel: 2, // 计划性检修优先级设为中等
      faultDescription: maintenancePlan.maintenanceContent,
      maintenanceContent: maintenancePlan.maintenanceContent,
      safetyMeasures: maintenancePlan.safetyRequirements,
      startTime: maintenancePlan.plannedStartTime,
      responsiblePerson: maintenancePlan.responsiblePerson,
      responsibleTeam: maintenancePlan.responsibleTeam,
      remark: `由检修计划${maintenancePlan.planName}生成`
    }

    // 创建检修单
    await MaintenanceOrderApi.createMaintenanceOrder(maintenanceOrderData)
    
    ElMessage.success('检修单生成成功')
    
    // 刷新列表
    await getList()
  } catch (error: any) {
    if (error !== 'cancel') {
      console.error('生成检修单失败:', error)
      ElMessage.error('生成检修单失败')
    }
  }
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

/** 初始化 **/
onMounted(async () => {
  // 先获取基础数据
  await Promise.all([
    getEquipmentList(),
    getUserList()
  ])
  // 再获取列表数据
  await getList()
})
</script>