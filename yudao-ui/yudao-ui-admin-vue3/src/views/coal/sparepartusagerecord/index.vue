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
      <el-form-item label="使用类型" prop="usageType">
        <el-select
          v-model="queryParams.usageType"
          placeholder="请选择使用类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.USAGE_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="使用日期" prop="usageDate">
        <el-date-picker
          v-model="queryParams.usageDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="性能评级" prop="performanceRating">
        <el-input
          v-model="queryParams.performanceRating"
          placeholder="请输入性能评级"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="关联检修单" prop="workOrderId">
        <el-select
          v-model="queryParams.workOrderId"
          placeholder="请选择检修单"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="order in maintenanceOrderList"
            :key="order.id"
            :label="order.orderCode"
            :value="order.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联设备" prop="equipmentId">
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
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
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
          v-hasPermi="['coal:spare-part-usage-record:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:spare-part-usage-record:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:spare-part-usage-record:delete']"
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
      <el-table-column label="使用记录ID" align="center" prop="id" />
      <el-table-column label="备件名称" align="center" prop="sparePartId">
        <template #default="scope">
          {{ getSparePartName(scope.row.sparePartId) }}
        </template>
      </el-table-column>
      <el-table-column label="关联设备" align="center" prop="equipmentId">
        <template #default="scope">
          {{ getEquipmentName(scope.row.equipmentId) }}
        </template>
      </el-table-column>
      <el-table-column label="使用类型" align="center" prop="usageType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.USAGE_TYPE" :value="scope.row.usageType" />
        </template>
      </el-table-column>
      <el-table-column
        label="使用日期"
        align="center"
        prop="usageDate"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="使用数量" align="center" prop="quantity" />
      <el-table-column label="操作人" align="center" prop="operatorId">
        <template #default="scope">
          {{ getUserName(scope.row.operatorId) }}
        </template>
      </el-table-column>
      <el-table-column label="监督人" align="center" prop="supervisorId">
        <template #default="scope">
          {{ getUserName(scope.row.supervisorId) }}
        </template>
      </el-table-column>
      <el-table-column label="作业班组" align="center" prop="workTeam" />
      <el-table-column label="关联检修单" align="center" prop="workOrderId">
        <template #default="scope">
          {{ getMaintenanceOrderName(scope.row.workOrderId) }}
        </template>
      </el-table-column>
      <el-table-column label="故障ID(关联故障记录)" align="center" prop="faultId" />
      <el-table-column label="关联维护计划" align="center" prop="maintenancePlanId">
        <template #default="scope">
          {{ getMaintenancePlanName(scope.row.maintenancePlanId) }}
        </template>
      </el-table-column>
      <el-table-column label="旧件状态" align="center" prop="oldPartCondition">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.OLD_PART_CONDITION" :value="scope.row.oldPartCondition" />
        </template>
      </el-table-column>
      <el-table-column label="失效模式" align="center" prop="failureMode" />
      <el-table-column label="使用时长(天)" align="center" prop="usageDuration" />
      <el-table-column label="更换原因" align="center" prop="replacementReason" />
      <el-table-column label="预计寿命(天)" align="center" prop="predictedLifespan" />
      <el-table-column label="预计下次更换日期" align="center" prop="nextReplacementDate" />
      <el-table-column label="性能评级" align="center" prop="performanceRating">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.PERFORMANCE_RATING" :value="scope.row.performanceRating" />
        </template>
      </el-table-column>
      <el-table-column label="单位成本" align="center" prop="unitCost" />
      <el-table-column label="总成本" align="center" prop="totalCost" />
      <el-table-column label="人工成本" align="center" prop="laborCost" />
      <el-table-column label="停机成本" align="center" prop="downtimeCost" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['coal:spare-part-usage-record:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:spare-part-usage-record:delete']"
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
  <SparePartUsageRecordForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { SparePartUsageRecordApi, SparePartUsageRecord } from '@/api/coal/sparepartusagerecord'
import { SparePartInfoApi, SparePartInfo } from '@/api/coal/sparepartinfo'
import { getSimpleUserList, UserVO } from '@/api/system/user'
import SparePartUsageRecordForm from './SparePartUsageRecordForm.vue'
import { useRoute } from 'vue-router'
import { MaintenanceOrderApi, MaintenanceOrder } from '@/api/coal/maintenanceorder'
import { MaintenancePlanApi, MaintenancePlan } from '@/api/coal/maintenanceplan'
import { EquipmentInfoApi, EquipmentInfo } from '@/api/coal/equipmentinfo'

/** 备件使用记录 列表 */
defineOptions({ name: 'SparePartUsageRecord' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<SparePartUsageRecord[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  usageType: undefined,
  usageDate: [],
  performanceRating: undefined,
  createTime: [],
  workOrderId: undefined,
  equipmentId: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

// 备件基础信息列表
const sparePartList = ref<SparePartInfo[]>([])
// 用户列表
const userList = ref<UserVO[]>([])
// 检修单列表
const maintenanceOrderList = ref<MaintenanceOrder[]>([])
// 维护计划列表
const maintenancePlanList = ref<MaintenancePlan[]>([])
// 设备列表
const equipmentList = ref<EquipmentInfo[]>([])

/** 获取备件名称 */
const getSparePartName = (sparePartId: number) => {
  const sparePart = sparePartList.value.find(item => item.id === sparePartId)
  return sparePart ? sparePart.sparePartName : `备件ID: ${sparePartId}`
}

/** 获取用户名称 */
const getUserName = (userId: number) => {
  const user = userList.value.find(item => item.id === userId)
  return user ? user.nickname : `用户ID: ${userId}`
}

/** 获取检修单名称 */
const getMaintenanceOrderName = (workOrderId: number) => {
  if (!workOrderId) return '-'
  const order = maintenanceOrderList.value.find(item => item.id === workOrderId)
  return order ? order.orderCode : `检修单ID: ${workOrderId}`
}

/** 获取维护计划名称 */
const getMaintenancePlanName = (planId: number) => {
  if (!planId) return '-'
  const plan = maintenancePlanList.value.find(item => item.id === planId)
  return plan ? plan.planName : `计划ID: ${planId}`
}

/** 获取设备名称 */
const getEquipmentName = (equipmentId: number) => {
  if (!equipmentId) return '-'
  const equipment = equipmentList.value.find(item => item.id === equipmentId)
  return equipment ? equipment.equipmentName : `设备ID: ${equipmentId}`
}

/** 获取备件基础信息列表 */
const getSparePartList = async () => {
  try {
    const data = await SparePartInfoApi.getSparePartInfoPage({ pageNo: 1, pageSize: 100 })
    sparePartList.value = data.list || []
  } catch (error) {
    console.error('获取备件列表失败:', error)
  }
}

/** 获取用户列表 */
const getUserList = async () => {
  try {
    const data = await getSimpleUserList()
    userList.value = data || []
  } catch (error) {
    console.error('获取用户列表失败:', error)
  }
}

/** 获取检修单列表 */
const getMaintenanceOrderList = async () => {
  try {
    const data = await MaintenanceOrderApi.getMaintenanceOrderPage({ pageNo: 1, pageSize: 100 })
    maintenanceOrderList.value = data.list || []
  } catch (error) {
    console.error('获取检修单列表失败:', error)
  }
}

/** 获取维护计划列表 */
const getMaintenancePlanList = async () => {
  try {
    const data = await MaintenancePlanApi.getMaintenancePlanPage({ pageNo: 1, pageSize: 100 })
    maintenancePlanList.value = data.list || []
  } catch (error) {
    console.error('获取维护计划列表失败:', error)
  }
}

/** 获取设备列表 */
const getEquipmentList = async () => {
  try {
    const data = await EquipmentInfoApi.getEquipmentInfoList({})
    equipmentList.value = data || []
    console.log('设备列表加载完成:', equipmentList.value.length, '条记录')
  } catch (error) {
    console.error('获取设备列表失败:', error)
  }
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    console.log('查询备件使用记录，参数:', queryParams)
    const data = await SparePartUsageRecordApi.getSparePartUsageRecordPage(queryParams)
    console.log('查询结果:', data)
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
    await SparePartUsageRecordApi.deleteSparePartUsageRecord(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除备件使用记录 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await SparePartUsageRecordApi.deleteSparePartUsageRecordList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: SparePartUsageRecord[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await SparePartUsageRecordApi.exportSparePartUsageRecord(queryParams)
    download.excel(data, '备件使用记录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  // 处理从其他页面传递过来的查询参数
  const route = useRoute()
  console.log('备件使用记录页面接收到的查询参数:', route.query)
  if (route.query.workOrderId) {
    queryParams.workOrderId = Number(route.query.workOrderId)
    console.log('设置工单ID:', queryParams.workOrderId)
  }
  if (route.query.equipmentId) {
    queryParams.equipmentId = Number(route.query.equipmentId)
    console.log('设置设备ID:', queryParams.equipmentId)
  }
  
  // 并行加载所有基础数据
  Promise.all([
    getSparePartList(),
    getUserList(),
    getMaintenanceOrderList(),
    getMaintenancePlanList(),
    getEquipmentList()
  ]).then(() => {
    // 基础数据加载完成后再获取列表数据
    getList()
  })
})
</script>