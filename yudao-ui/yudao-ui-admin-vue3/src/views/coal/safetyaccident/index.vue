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
      <el-form-item label="事故编号" prop="accidentCode">
        <el-input
          v-model="queryParams.accidentCode"
          placeholder="请输入事故编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="事故类型" prop="accidentType">
        <el-select
          v-model="queryParams.accidentType"
          placeholder="请选择事故类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_ACCIDENT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="事故等级" prop="accidentLevel">
        <el-input
          v-model="queryParams.accidentLevel"
          placeholder="请输入事故等级"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="事故状态" prop="accidentStatus">
        <el-select
          v-model="queryParams.accidentStatus"
          placeholder="请选择事故状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_ACCIDENT_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批状态" prop="approvalStatus">
        <el-select
          v-model="queryParams.approvalStatus"
          placeholder="请选择审批状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_APPROVAL_STATUS)"
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
          v-hasPermi="['coal:safety-accident:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:safety-accident:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:safety-accident:delete']"
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
      <el-table-column label="事故ID" align="center" prop="id" />
      <el-table-column label="事故编号" align="center" prop="accidentCode" />
      <el-table-column label="事故类型" align="center" prop="accidentType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_ACCIDENT_TYPE" :value="scope.row.accidentType" />
        </template>
      </el-table-column>
      <el-table-column label="事故等级" align="center" prop="accidentLevel">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_ACCIDENT_LEVEL" :value="scope.row.accidentLevel" />
        </template>
      </el-table-column>
      <el-table-column label="事故标题" align="center" prop="accidentTitle" />
      <el-table-column
        label="事故发生时间"
        align="center"
        prop="accidentTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="事故地点" align="center" prop="accidentLocation" />
      <el-table-column label="天气情况" align="center" prop="weatherCondition" />
      <el-table-column label="事故描述" align="center" prop="accidentDescription" />
      <el-table-column label="事故原因" align="center" prop="accidentCause" />
      <el-table-column label="伤亡人数" align="center" prop="casualtiesCount" />
      <el-table-column label="经济损失(元)" align="center" prop="economicLoss" />
      <el-table-column label="设备损坏情况" align="center" prop="equipmentDamage" />
      <el-table-column label="环境影响" align="center" prop="environmentalImpact" />
      <el-table-column label="应急响应措施" align="center" prop="emergencyResponse" />
      <el-table-column label="救援过程" align="center" prop="rescueProcess" />
      <el-table-column label="报告人ID" align="center" prop="reporterId" />
      <el-table-column label="报告人姓名" align="center" prop="reporterName" />
      <el-table-column
        label="报告时间"
        align="center"
        prop="reportTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="调查人ID" align="center" prop="investigatorId" />
      <el-table-column label="调查人姓名" align="center" prop="investigatorName" />
      <el-table-column
        label="调查开始时间"
        align="center"
        prop="investigationStartTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="调查结束时间"
        align="center"
        prop="investigationEndTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="调查结果" align="center" prop="investigationResult" />
      <el-table-column label="预防措施" align="center" prop="preventiveMeasures" />
      <el-table-column label="责任人ID" align="center" prop="responsiblePersonId" />
      <el-table-column label="责任人姓名" align="center" prop="responsiblePersonName" />
      <el-table-column label="处罚措施" align="center" prop="punishmentMeasures" />
      <el-table-column label="事故状态" align="center" prop="accidentStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_ACCIDENT_STATUS" :value="scope.row.accidentStatus" />
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
            v-hasPermi="['coal:safety-accident:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:safety-accident:delete']"
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
  <SafetyAccidentForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { SafetyAccidentApi, SafetyAccident } from '@/api/coal/safetyaccident'
import SafetyAccidentForm from './SafetyAccidentForm.vue'

/** 安全事故记录 列表 */
defineOptions({ name: 'SafetyAccident' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<SafetyAccident[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  accidentCode: undefined,
  accidentType: undefined,
  accidentLevel: undefined,
  accidentStatus: undefined,
  approvalStatus: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SafetyAccidentApi.getSafetyAccidentPage(queryParams)
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
    await SafetyAccidentApi.deleteSafetyAccident(id)
    message.success(t('common.delSuccess'))
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除安全事故记录 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await SafetyAccidentApi.deleteSafetyAccidentList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: SafetyAccident[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await SafetyAccidentApi.exportSafetyAccident(queryParams)
    download.excel(data, '安全事故记录.xls')
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