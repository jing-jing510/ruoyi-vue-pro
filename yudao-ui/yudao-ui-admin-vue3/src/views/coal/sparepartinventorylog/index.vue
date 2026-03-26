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
      <el-form-item label="操作类型" prop="operationType">
        <el-select
          v-model="queryParams.operationType"
          placeholder="请选择操作类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.OPERATION_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库位置" prop="warehouseLocation">
        <el-input
          v-model="queryParams.warehouseLocation"
          placeholder="请输入仓库位置"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="审批人ID" prop="approverId">
        <el-input
          v-model="queryParams.approverId"
          placeholder="请输入审批人ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
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
          v-hasPermi="['coal:spare-part-inventory-log:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:spare-part-inventory-log:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:spare-part-inventory-log:delete']"
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
      <el-table-column label="记录ID" align="center" prop="id" />
      <el-table-column label="备件名称" align="center" prop="sparePartId">
        <template #default="scope">
          {{ getSparePartName(scope.row.sparePartId) }}
        </template>
      </el-table-column>
      <el-table-column label="操作类型" align="center" prop="operationType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.OPERATION_TYPE" :value="scope.row.operationType" />
        </template>
      </el-table-column>
      <el-table-column
        label="操作时间"
        align="center"
        prop="operationDate"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="数量(正数入库，负数出库)" align="center" prop="quantity" />
      <el-table-column label="单价" align="center" prop="unitPrice" />
      <el-table-column label="总金额" align="center" prop="totalAmount" />
      <el-table-column label="操作人ID" align="center" prop="operatorId" />
      <el-table-column label="仓库位置" align="center" prop="warehouseLocation" />
      <el-table-column label="批次号" align="center" prop="batchNo" />
      <el-table-column label="关联设备ID(出库时)" align="center" prop="equipmentId" />
      <el-table-column label="关联工单ID" align="center" prop="workOrderId" />
      <el-table-column label="供应商ID(入库时)" align="center" prop="supplierId" />
      <el-table-column label="采购单号" align="center" prop="purchaseOrderNo" />
      <el-table-column label="审批人ID" align="center" prop="approverId" />
      <el-table-column
        label="审批时间"
        align="center"
        prop="approveTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="审批状态" align="center" prop="approveStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.APPROVE_STATUS" :value="scope.row.approveStatus" />
        </template>
      </el-table-column>
      <el-table-column label="操作原因" align="center" prop="operationReason" />
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
            v-hasPermi="['coal:spare-part-inventory-log:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:spare-part-inventory-log:delete']"
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
  <SparePartInventoryLogForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { SparePartInventoryLogApi, SparePartInventoryLog } from '@/api/coal/sparepartinventorylog'
import { SparePartInfoApi, SparePartInfo } from '@/api/coal/sparepartinfo'
import SparePartInventoryLogForm from './SparePartInventoryLogForm.vue'

/** 备件出入库记录 列表 */
defineOptions({ name: 'SparePartInventoryLog' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<SparePartInventoryLog[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  operationType: undefined,
  warehouseLocation: undefined,
  approverId: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

// 备件基础信息列表
const sparePartList = ref<SparePartInfo[]>([])

/** 获取备件名称 */
const getSparePartName = (sparePartId: number) => {
  const sparePart = sparePartList.value.find(item => item.id === sparePartId)
  return sparePart ? sparePart.sparePartName : `备件ID: ${sparePartId}`
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

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SparePartInventoryLogApi.getSparePartInventoryLogPage(queryParams)
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
    await SparePartInventoryLogApi.deleteSparePartInventoryLog(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除备件出入库记录 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await SparePartInventoryLogApi.deleteSparePartInventoryLogList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: SparePartInventoryLog[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await SparePartInventoryLogApi.exportSparePartInventoryLog(queryParams)
    download.excel(data, '备件出入库记录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getSparePartList()
  getList()
})
</script>