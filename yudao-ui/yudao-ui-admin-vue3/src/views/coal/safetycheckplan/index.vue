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
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_PLAN_TYPE)"
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
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_PLAN_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
          v-hasPermi="['coal:safety-check-plan:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:safety-check-plan:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:safety-check-plan:delete']"
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
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_PLAN_TYPE" :value="scope.row.planType" />
        </template>
      </el-table-column>
      <el-table-column label="检查分类ID" align="center" prop="checkCategoryId" />
      <el-table-column label="检查周期" align="center" prop="checkCycle">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_CHECK_CYCLE" :value="scope.row.checkCycle" />
        </template>
      </el-table-column>
      <el-table-column label="检查频次" align="center" prop="checkFrequency" />
      <el-table-column label="负责人ID" align="center" prop="responsiblePersonId" />
      <el-table-column label="负责人姓名" align="center" prop="responsiblePersonName" />
      <el-table-column label="检查区域" align="center" prop="checkArea" />
      <el-table-column label="检查内容" align="center" prop="checkContent" />
      <el-table-column label="检查标准" align="center" prop="checkStandard" />
      <el-table-column label="开始日期" align="center" prop="startDate" />
      <el-table-column label="结束日期" align="center" prop="endDate" />
      <el-table-column label="计划状态" align="center" prop="planStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_PLAN_STATUS" :value="scope.row.planStatus" />
        </template>
      </el-table-column>
      <el-table-column label="审批状态" align="center" prop="approvalStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_APPROVAL_STATUS" :value="scope.row.approvalStatus" />
        </template>
      </el-table-column>
      <el-table-column label="审批人ID" align="center" prop="approverId" />
      <el-table-column label="审批人姓名" align="center" prop="approverName" />
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
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['coal:safety-check-plan:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:safety-check-plan:delete']"
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
  <SafetyCheckPlanForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { SafetyCheckPlanApi, SafetyCheckPlan } from '@/api/coal/safetycheckplan'
import SafetyCheckPlanForm from './SafetyCheckPlanForm.vue'

/** 安全检查计划 列表 */
defineOptions({ name: 'SafetyCheckPlan' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<SafetyCheckPlan[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  planCode: undefined,
  planName: undefined,
  planType: undefined,
  planStatus: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SafetyCheckPlanApi.getSafetyCheckPlanPage(queryParams)
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
    await SafetyCheckPlanApi.deleteSafetyCheckPlan(id)
    message.success(t('common.delSuccess'))
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除安全检查计划 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await SafetyCheckPlanApi.deleteSafetyCheckPlanList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: SafetyCheckPlan[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await SafetyCheckPlanApi.exportSafetyCheckPlan(queryParams)
    download.excel(data, '安全检查计划.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>