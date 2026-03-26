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
      <el-form-item label="检测编号" prop="inspectionCode">
        <el-input
          v-model="queryParams.inspectionCode"
          placeholder="请输入检测编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="检测时间" prop="inspectionDate">
        <el-date-picker
          v-model="queryParams.inspectionDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="检测状态" prop="inspectionStatus">
        <el-select
          v-model="queryParams.inspectionStatus"
          placeholder="请选择检测状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_INSPECTION_STATUS)"
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
          v-hasPermi="['coal:quality-inspection:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:quality-inspection:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
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
      <el-table-column label="检测记录ID" align="center" prop="id" />
      <el-table-column label="检测编号" align="center" prop="inspectionCode" />
      <el-table-column
        label="检测时间"
        align="center"
        prop="inspectionDate"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="班次ID" align="center" prop="shiftId" />
      <el-table-column label="检测人员ID" align="center" prop="inspectorId" />
      <el-table-column label="检测人员姓名" align="center" prop="inspectorName" />
      <el-table-column label="产品类型" align="center" prop="productType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_PRODUCT_TYPE" :value="scope.row.productType" />
        </template>
      </el-table-column>
      <el-table-column label="产品规格" align="center" prop="productSpecification">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_PRODUCT_SPEC" :value="scope.row.productSpecification" />
        </template>
      </el-table-column>
      <el-table-column label="采样地点" align="center" prop="samplingLocation" />
      <el-table-column label="采样方法" align="center" prop="samplingMethod">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_SAMPLING_METHOD" :value="scope.row.samplingMethod" />
        </template>
      </el-table-column>
      <el-table-column label="样品编号" align="center" prop="sampleCode" />
      <el-table-column label="检测类型" align="center" prop="detectionType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_DETECTION_TYPE" :value="scope.row.detectionType" />
        </template>
      </el-table-column>
      <el-table-column label="系统位置" align="center" prop="systemLocation" />
      <el-table-column label="批次号" align="center" prop="batchNumber" />
      <el-table-column label="检测状态" align="center" prop="inspectionStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_INSPECTION_STATUS" :value="scope.row.inspectionStatus" />
        </template>
      </el-table-column>
      <el-table-column label="审核人员ID" align="center" prop="reviewerId" />
      <el-table-column label="审核人员姓名" align="center" prop="reviewerName" />
      <el-table-column
        label="审核时间"
        align="center"
        prop="reviewTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="审核状态" align="center" prop="reviewStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_REVIEW_STATUS" :value="scope.row.reviewStatus" />
        </template>
      </el-table-column>
      <el-table-column label="审核备注" align="center" prop="reviewRemark" />
      <el-table-column label="环境温度(℃)" align="center" prop="temperature" />
      <el-table-column label="环境湿度(%)" align="center" prop="humidity" />
      <el-table-column label="天气情况" align="center" prop="weatherCondition" />
      <el-table-column label="备注信息" align="center" prop="remark" />
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
  <QualityInspectionForm ref="formRef" @success="getList" />
  <!-- 子表的列表 -->
  <ContentWrap>
    <el-tabs model-value="qualityData">
      <el-tab-pane label="质量检测数据" name="qualityData">
        <QualityDataList :inspection-id="currentRow.id" />
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { QualityInspectionApi, QualityInspection } from '@/api/coal/qualityinspection'
import QualityInspectionForm from './QualityInspectionForm.vue'
import QualityDataList from './components/QualityDataList.vue'

/** 质量检测记录 列表 */
defineOptions({ name: 'QualityInspection' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<QualityInspection[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  inspectionCode: undefined,
  inspectionDate: [],
  inspectionStatus: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await QualityInspectionApi.getQualityInspectionPage(queryParams)
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
    await QualityInspectionApi.deleteQualityInspection(id)
    message.success(t('common.delSuccess'))
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除质量检测记录 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await QualityInspectionApi.deleteQualityInspectionList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: QualityInspection[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await QualityInspectionApi.exportQualityInspection(queryParams)
    download.excel(data, '质量检测记录.xls')
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