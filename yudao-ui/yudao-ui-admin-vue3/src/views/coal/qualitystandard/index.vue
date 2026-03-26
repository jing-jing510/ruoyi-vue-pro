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
      <el-form-item label="标准编码" prop="standardCode">
        <el-input
          v-model="queryParams.standardCode"
          placeholder="请输入标准编码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="标准名称" prop="standardName">
        <el-input
          v-model="queryParams.standardName"
          placeholder="请输入标准名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="产品类型" prop="productType">
        <el-select
          v-model="queryParams.productType"
          placeholder="请选择产品类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_PRODUCT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检测项目" prop="qualityItemId">
        <el-select
          v-model="queryParams.qualityItemId"
          placeholder="请选择检测项目"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="item in qualityItemList"
            :key="item.id"
            :label="item.itemName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标准类型" prop="standardType">
        <el-select
          v-model="queryParams.standardType"
          placeholder="请选择标准类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_STANDARD_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
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
          v-hasPermi="['coal:quality-standard:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:quality-standard:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:quality-standard:delete']"
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
      <el-table-column label="质量标准ID" align="center" prop="id" />
      <el-table-column label="标准编码" align="center" prop="standardCode" />
      <el-table-column label="标准名称" align="center" prop="standardName" />
      <el-table-column label="产品类型" align="center" prop="productType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_PRODUCT_TYPE" :value="scope.row.productType" />
        </template>
      </el-table-column>
      <el-table-column label="产品规格" align="center" prop="productSpecification" />
      <el-table-column label="检测项目" align="center" prop="qualityItemId">
        <template #default="scope">
          {{ getQualityItemName(scope.row.qualityItemId) }}
        </template>
      </el-table-column>
      <el-table-column label="标准值" align="center" prop="standardValue" />
      <el-table-column label="最大允许值" align="center" prop="maxValue" />
      <el-table-column label="最小允许值" align="center" prop="minValue" />
      <el-table-column label="允许公差" align="center" prop="tolerance" />
      <el-table-column label="标准类型" align="center" prop="standardType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_STANDARD_TYPE" :value="scope.row.standardType" />
        </template>
      </el-table-column>
      <el-table-column label="标准来源（如：GB/T标准号）" align="center" prop="standardSource" />
      <el-table-column label="生效日期" align="center" prop="effectiveDate" />
      <el-table-column label="失效日期" align="center" prop="expiryDate" />
      <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_STANDARD_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="审批人ID" align="center" prop="approverId" />
      <el-table-column
        label="审批时间"
        align="center"
        prop="approveTime"
        :formatter="dateFormatter"
        width="180px"
      />
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
            v-hasPermi="['coal:quality-standard:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:quality-standard:delete']"
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
  <QualityStandardForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { QualityStandardApi, QualityStandard } from '@/api/coal/qualitystandard'
import { QualityItemApi, QualityItem } from '@/api/coal/qualityitem'
import QualityStandardForm from './QualityStandardForm.vue'

/** 质量标准 列表 */
defineOptions({ name: 'QualityStandard' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

// 检测项目列表
const qualityItemList = ref<QualityItem[]>([])

const loading = ref(true) // 列表的加载中
const list = ref<QualityStandard[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  standardCode: undefined,
  standardName: undefined,
  productType: undefined,
  qualityItemId: undefined,
  standardType: undefined,
  status: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await QualityStandardApi.getQualityStandardPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 加载检测项目列表 */
const loadQualityItemList = async () => {
  try {
    const data = await QualityItemApi.getQualityItemPage({ pageNo: 1, pageSize: 100 })
    qualityItemList.value = data.list || []
  } catch (error) {
    console.error('加载检测项目列表失败:', error)
  }
}

/** 获取检测项目名称 */
const getQualityItemName = (itemId: number) => {
  const item = qualityItemList.value.find(i => i.id === itemId)
  return item ? item.itemName : '-'
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
    await QualityStandardApi.deleteQualityStandard(id)
    message.success(t('common.delSuccess'))
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除质量标准 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await QualityStandardApi.deleteQualityStandardList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: QualityStandard[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await QualityStandardApi.exportQualityStandard(queryParams)
    download.excel(data, '质量标准.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  loadQualityItemList()
  getList()
})
</script>