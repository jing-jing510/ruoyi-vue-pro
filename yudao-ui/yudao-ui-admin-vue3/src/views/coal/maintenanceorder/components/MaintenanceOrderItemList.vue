<template>
  <!-- 列表 -->
  <ContentWrap>
    <el-button
      type="primary"
      plain
      @click="openForm('create')"
      v-hasPermi="['coal:maintenance-order:create']"
    >
      <Icon icon="ep:plus" class="mr-5px" /> 新增
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
    <el-table
        row-key="id"
        v-loading="loading"
        :data="list"
        :stripe="true"
        :show-overflow-tooltip="true"
        @selection-change="handleRowCheckboxChange"
    >
          <el-table-column type="selection" width="55" />
      <el-table-column label="明细ID" align="center" prop="id" />
       <el-table-column label="项目名称" align="center" prop="itemName" />
      <el-table-column label="项目类型" align="center" prop="itemType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.MAINTENANCE_ITEM_TYPE" :value="scope.row.itemType" />
        </template>
      </el-table-column>
      <el-table-column label="项目描述" align="center" prop="itemDescription" />
      <el-table-column label="作业标准" align="center" prop="workStandard" />
      <el-table-column label="安全要求" align="center" prop="safetyRequirements" />
      <el-table-column label="所需工具" align="center" prop="requiredTools" />
      <el-table-column label="所需材料" align="center" prop="requiredMaterials" />
      <el-table-column label="预计工时(小时)" align="center" prop="estimatedDuration" />
      <el-table-column label="实际工时(小时)" align="center" prop="actualDuration" />
      <el-table-column label="项目状态" align="center" prop="itemStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.MAINTENANCE_ITEM_STATUS" :value="scope.row.itemStatus" />
        </template>
      </el-table-column>
      <el-table-column
        label="完成时间"
        align="center"
        prop="completionTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="完成质量" align="center" prop="completionQuality">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COMPLETION_QUALITY" :value="scope.row.completionQuality" />
        </template>
      </el-table-column>
      <el-table-column label="完成说明" align="center" prop="completionNotes" />
      <el-table-column label="检查人" align="center" prop="inspector" />
      <el-table-column
        label="检查时间"
        align="center"
        prop="inspectionTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="检查结果" align="center" prop="inspectionResult" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center">
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
    <MaintenanceOrderItemForm ref="formRef" @success="getList" />
</template>
<script setup lang="ts">
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import { isEmpty } from '@/utils/is'
import { MaintenanceOrderItemApi, MaintenanceOrderItem } from '@/api/coal/maintenanceorder'
import MaintenanceOrderItemForm from './MaintenanceOrderItemForm.vue'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const props = defineProps<{
  orderId?: number // 检修单ID（主表的关联字段）
}>()
const loading = ref(false) // 列表的加载中
const list = ref([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  orderId: undefined as unknown
})

/** 监听主表的关联字段的变化，加载对应的子表数据 */
watch(
  () => props.orderId,
  (val: number) => {
    if (!val) {
      list.value = [] // 清空列表
      return
    }
    queryParams.orderId = val
    handleQuery()
  },
    { immediate: true, deep: true }
)

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MaintenanceOrderItemApi.getMaintenanceOrderItemPage(queryParams)
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

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  if (!props.orderId) {
    message.error('请选择一个检修单')
    return
  }
  formRef.value.open(type, id, props.orderId)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await MaintenanceOrderItemApi.deleteMaintenanceOrderItem(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除检修项目明细 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await MaintenanceOrderItemApi.deleteMaintenanceOrderItemList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: MaintenanceOrderItem[]) => {
  checkedIds.value = records.map((item) => item.id);
}
</script>