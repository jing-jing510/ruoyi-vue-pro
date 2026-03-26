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
      <el-form-item label="记录编号" prop="recordCode">
        <el-input
          v-model="queryParams.recordCode"
          placeholder="请输入记录编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="计划ID" prop="planId">
        <el-input
          v-model="queryParams.planId"
          placeholder="请输入计划ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="检查类型" prop="checkType">
        <el-select
          v-model="queryParams.checkType"
          placeholder="请选择检查类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_CHECK_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检查分类ID" prop="checkCategoryId">
        <el-input
          v-model="queryParams.checkCategoryId"
          placeholder="请输入检查分类ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="记录状态" prop="recordStatus">
        <el-select
          v-model="queryParams.recordStatus"
          placeholder="请选择记录状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_RECORD_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['coal:safety-check-record:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:safety-check-record:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
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
      highlight-current-row
      @current-change="handleCurrentChange"
      @selection-change="handleRowCheckboxChange"
    >
    <el-table-column type="selection" width="55" />
      <el-table-column label="记录ID" align="center" prop="id" />
      <el-table-column label="记录编号" align="center" prop="recordCode" />
      <el-table-column label="计划ID" align="center" prop="planId" />
      <el-table-column label="检查类型" align="center" prop="checkType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_CHECK_TYPE" :value="scope.row.checkType" />
        </template>
      </el-table-column>
      <el-table-column label="检查分类ID" align="center" prop="checkCategoryId" />
      <el-table-column label="检查日期" align="center" prop="checkDate" />
      <el-table-column
        label="检查时间"
        align="center"
        prop="checkTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="检查人ID" align="center" prop="checkerId" />
      <el-table-column label="检查人姓名" align="center" prop="checkerName" />
      <el-table-column label="检查区域" align="center" prop="checkArea" />
      <el-table-column label="天气情况" align="center" prop="weatherCondition" />
      <el-table-column label="温度(℃)" align="center" prop="temperature" />
      <el-table-column label="湿度(%)" align="center" prop="humidity" />
      <el-table-column label="检查总结" align="center" prop="checkSummary" />
      <el-table-column label="检查项目总数" align="center" prop="totalItems" />
      <el-table-column label="合格项目数" align="center" prop="qualifiedItems" />
      <el-table-column label="不合格项目数" align="center" prop="unqualifiedItems" />
      <el-table-column label="合格率(%)" align="center" prop="qualifiedRate" />
      <el-table-column label="记录状态" align="center" prop="recordStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_RECORD_STATUS" :value="scope.row.recordStatus" />
        </template>
      </el-table-column>
      <el-table-column
        label="提交时间"
        align="center"
        prop="submitTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="审核人ID" align="center" prop="auditorId" />
      <el-table-column label="审核人姓名" align="center" prop="auditorName" />
      <el-table-column
        label="审核时间"
        align="center"
        prop="auditTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="审核备注" align="center" prop="auditRemark" />
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
  <SafetyCheckRecordForm ref="formRef" @success="getList" />
  <!-- 子表的列表 -->
  <ContentWrap>
    <el-tabs model-value="safetyCheckItem">
      <el-tab-pane label="安全检查项目" name="safetyCheckItem">
        <SafetyCheckItemList :record-id="currentRow.id" />
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { SafetyCheckRecordApi, SafetyCheckRecord } from '@/api/coal/safetycheckrecord'
import SafetyCheckRecordForm from './SafetyCheckRecordForm.vue'
import SafetyCheckItemList from './components/SafetyCheckItemList.vue'

/** 安全检查记录 列表 */
defineOptions({ name: 'SafetyCheckRecord' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<SafetyCheckRecord[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  recordCode: undefined,
  planId: undefined,
  checkType: undefined,
  checkCategoryId: undefined,
  recordStatus: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SafetyCheckRecordApi.getSafetyCheckRecordPage(queryParams)
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
    await SafetyCheckRecordApi.deleteSafetyCheckRecord(id)
    message.success(t('common.delSuccess'))
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除安全检查记录 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await SafetyCheckRecordApi.deleteSafetyCheckRecordList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: SafetyCheckRecord[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await SafetyCheckRecordApi.exportSafetyCheckRecord(queryParams)
    download.excel(data, '安全检查记录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 选中行操作 */
const currentRow = ref({}) // 选中行
const handleCurrentChange = (row) => {
  currentRow.value = row
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>