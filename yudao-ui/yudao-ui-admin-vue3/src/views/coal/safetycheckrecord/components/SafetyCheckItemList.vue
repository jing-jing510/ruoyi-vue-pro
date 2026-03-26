<template>
  <!-- 列表 -->
  <ContentWrap>
    <el-button
      type="primary"
      plain
      @click="openForm('create')"
      v-hasPermi="['coal:safety-check-record:create']"
    >
      <Icon icon="ep:plus" class="mr-5px" /> 新增
    </el-button>
      <el-button
          type="danger"
          plain
          :disabled="isEmpty(checkedIds)"
          @click="handleDeleteBatch"
          v-hasPermi="['coal:safety-check-record:delete']"
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
      <el-table-column label="项目ID" align="center" prop="id" />
       <el-table-column label="检查项目名称" align="center" prop="itemName" />
      <el-table-column label="检查项目编码" align="center" prop="itemCode" />
      <el-table-column label="检查标准" align="center" prop="checkStandard" />
      <el-table-column label="检查结果" align="center" prop="checkResult">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_CHECK_RESULT" :value="scope.row.checkResult" />
        </template>
      </el-table-column>
      <el-table-column label="检查得分" align="center" prop="checkScore" />
      <el-table-column label="满分" align="center" prop="maxScore" />
      <el-table-column label="问题描述" align="center" prop="problemDescription" />
      <el-table-column label="风险等级" align="center" prop="riskLevel">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_RISK_LEVEL" :value="scope.row.riskLevel" />
        </template>
      </el-table-column>
      <el-table-column label="是否需要整改" align="center" prop="rectificationRequired">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_RECTIFICATION_STATUS" :value="scope.row.rectificationRequired" />
        </template>
      </el-table-column>
      <el-table-column label="整改期限" align="center" prop="rectificationDeadline" />
      <el-table-column label="整改负责人ID" align="center" prop="responsiblePersonId" />
      <el-table-column label="整改负责人姓名" align="center" prop="responsiblePersonName" />
      <el-table-column label="整改状态" align="center" prop="rectificationStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_RECTIFICATION_STATUS" :value="scope.row.rectificationStatus" />
        </template>
      </el-table-column>
      <el-table-column label="整改结果" align="center" prop="rectificationResult" />
      <el-table-column
        label="整改完成时间"
        align="center"
        prop="rectificationTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="验证人ID" align="center" prop="verifierId" />
      <el-table-column label="验证人姓名" align="center" prop="verifierName" />
      <el-table-column
        label="验证时间"
        align="center"
        prop="verificationTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="验证结果" align="center" prop="verificationResult" />
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
            v-hasPermi="['coal:safety-check-record:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:safety-check-record:delete']"
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
    <SafetyCheckItemForm ref="formRef" @success="getList" />
</template>
<script setup lang="ts">
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import { isEmpty } from '@/utils/is'
import { SafetyCheckItemApi, SafetyCheckItem } from '@/api/coal/safetycheckrecord'
import SafetyCheckItemForm from './SafetyCheckItemForm.vue'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const props = defineProps<{
  recordId?: number // 检查记录ID（主表的关联字段）
}>()
const loading = ref(false) // 列表的加载中
const list = ref([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  recordId: undefined as unknown
})

/** 监听主表的关联字段的变化，加载对应的子表数据 */
watch(
  () => props.recordId,
  (val: number) => {
    if (!val) {
      list.value = [] // 清空列表
      return
    }
    queryParams.recordId = val
    handleQuery()
  },
    { immediate: true, deep: true }
)

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SafetyCheckItemApi.getSafetyCheckItemPage(queryParams)
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
  if (!props.recordId) {
    message.error('请选择一个安全检查记录')
    return
  }
  formRef.value.open(type, id, props.recordId)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await SafetyCheckItemApi.deleteSafetyCheckItem(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除安全检查项目 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await SafetyCheckItemApi.deleteSafetyCheckItemList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: SafetyCheckItem[]) => {
  checkedIds.value = records.map((item) => item.id);
}
</script>