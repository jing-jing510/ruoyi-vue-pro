<template>
  <!-- 列表 -->
  <ContentWrap>
    <el-button
      type="primary"
      plain
      @click="openForm('create')"
      v-hasPermi="['coal:quality-inspection:create']"
    >
      <Icon icon="ep:plus" class="mr-5px" /> 新增
    </el-button>
      <el-button
          type="danger"
          plain
          :disabled="isEmpty(checkedIds)"
          @click="handleDeleteBatch"
          v-hasPermi="['coal:quality-inspection:delete']"
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
      <el-table-column label="检测数据ID" align="center" prop="id" />
       <el-table-column label="检测项目ID" align="center" prop="qualityItemId" />
      <el-table-column label="检测项目编码" align="center" prop="qualityItemCode" />
      <el-table-column label="检测项目名称" align="center" prop="qualityItemName" />
      <el-table-column label="检测值" align="center" prop="measuredValue" />
      <el-table-column label="计量单位" align="center" prop="unit" />
      <el-table-column label="标准值" align="center" prop="standardValue" />
      <el-table-column label="偏差值" align="center" prop="deviation" />
      <el-table-column label="偏差率(%)" align="center" prop="deviationRate" />
      <el-table-column label="是否合格" align="center" prop="isQualified">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_QUALIFIED" :value="scope.row.isQualified" />
        </template>
      </el-table-column>
      <el-table-column label="检测方法" align="center" prop="detectionMethod" />
      <el-table-column label="检测设备" align="center" prop="detectionEquipment" />
      <el-table-column
        label="检测时间"
        align="center"
        prop="detectionTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作员ID" align="center" prop="operatorId" />
      <el-table-column label="操作员姓名" align="center" prop="operatorName" />
      <el-table-column label="复检次数" align="center" prop="retestCount" />
      <el-table-column label="是否复检" align="center" prop="isRetest">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_RECHECK" :value="scope.row.isRetest" />
        </template>
      </el-table-column>
      <el-table-column label="原始检测值（复检时记录）" align="center" prop="originalValue" />
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
            v-hasPermi="['coal:quality-inspection:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:quality-inspection:delete']"
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
    <QualityDataForm ref="formRef" @success="getList" />
</template>
<script setup lang="ts">
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import { isEmpty } from '@/utils/is'
import { QualityInspectionApi, QualityData } from '@/api/coal/qualityinspection'
import QualityDataForm from './QualityDataForm.vue'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const props = defineProps<{
  inspectionId?: number // 检测记录ID（主表的关联字段）
}>()
const loading = ref(false) // 列表的加载中
const list = ref([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  inspectionId: undefined as unknown
})

/** 监听主表的关联字段的变化，加载对应的子表数据 */
watch(
  () => props.inspectionId,
  (val: number) => {
    if (!val) {
      list.value = [] // 清空列表
      return
    }
    queryParams.inspectionId = val
    handleQuery()
  },
    { immediate: true, deep: true }
)

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await QualityInspectionApi.getQualityDataPage(queryParams)
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
  if (!props.inspectionId) {
    message.error('请选择一个质量检测记录')
    return
  }
  formRef.value.open(type, id, props.inspectionId)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await QualityInspectionApi.deleteQualityData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除质量检测数据 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await QualityInspectionApi.deleteQualityDataList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: QualityData[]) => {
  checkedIds.value = records.map((item) => item.id);
}
</script>