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
      <el-form-item label="统计日期" prop="statisticsDate">
        <el-date-picker
          v-model="queryParams.statisticsDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="统计类型" prop="statisticsType">
        <el-select
          v-model="queryParams.statisticsType"
          placeholder="请选择统计类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_STATISTICS_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="能源类型" prop="energyTypeId">
        <el-select
          v-model="queryParams.energyTypeId"
          placeholder="请选择能源类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="item in energyTypeList"
            :key="item.id"
            :label="item.typeName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="统计状态" prop="statisticsStatus">
        <el-select
          v-model="queryParams.statisticsStatus"
          placeholder="请选择统计状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_STATISTICS_STATUS)"
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
          v-hasPermi="['coal:energy-statistics:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:energy-statistics:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:energy-statistics:delete']"
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
      <el-table-column label="统计ID" align="center" prop="id" />
      <el-table-column label="统计日期" align="center" prop="statisticsDate" />
      <el-table-column label="统计类型" align="center" prop="statisticsType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_ENERGY_STATISTICS_TYPE" :value="scope.row.statisticsType" />
        </template>
      </el-table-column>
      <el-table-column label="能源类型" align="center" prop="energyTypeId">
        <template #default="scope">
          {{ getEnergyTypeName(scope.row.energyTypeId) }}
        </template>
      </el-table-column>
      <el-table-column label="总消耗量" align="center" prop="totalConsumption" />
      <el-table-column label="总成本(元)" align="center" prop="totalCost" />
      <el-table-column label="平均消耗量" align="center" prop="averageConsumption" />
      <el-table-column label="峰值消耗量" align="center" prop="peakConsumption" />
      <el-table-column label="谷值消耗量" align="center" prop="valleyConsumption" />
      <el-table-column label="消耗效率(%)" align="center" prop="consumptionEfficiency" />
      <el-table-column label="单位成本(元/单位)" align="center" prop="costPerUnit" />
      <el-table-column label="与计划对比(%)" align="center" prop="comparisonWithPlan" />
      <el-table-column label="与上期对比(%)" align="center" prop="comparisonWithLastPeriod" />
      <el-table-column label="统计状态" align="center" prop="statisticsStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_ENERGY_STATISTICS_STATUS" :value="scope.row.statisticsStatus" />
        </template>
      </el-table-column>
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
            v-hasPermi="['coal:energy-statistics:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:energy-statistics:delete']"
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
  <EnergyStatisticsForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { EnergyStatisticsApi, EnergyStatistics } from '@/api/coal/energystatistics'
import { EnergyTypeApi } from '@/api/coal/energytype'
import EnergyStatisticsForm from './EnergyStatisticsForm.vue'

/** 能源消耗统计 列表 */
defineOptions({ name: 'EnergyStatistics' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<EnergyStatistics[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  statisticsDate: [],
  statisticsType: undefined,
  energyTypeId: undefined,
  statisticsStatus: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

// 能源类型列表
const energyTypeList = ref([])

// 获取能源类型列表
const getEnergyTypeList = async () => {
  try {
    const data = await EnergyTypeApi.getSimpleEnergyTypeList()
    energyTypeList.value = data
  } catch (error) {
    console.error('获取能源类型列表失败:', error)
  }
}

// 获取能源类型名称
const getEnergyTypeName = (energyTypeId) => {
  const energyType = energyTypeList.value.find(item => item.id === energyTypeId)
  return energyType ? energyType.typeName : energyTypeId
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await EnergyStatisticsApi.getEnergyStatisticsPage(queryParams)
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
    await EnergyStatisticsApi.deleteEnergyStatistics(id)
    message.success(t('common.delSuccess'))
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除能源消耗统计 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await EnergyStatisticsApi.deleteEnergyStatisticsList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: EnergyStatistics[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await EnergyStatisticsApi.exportEnergyStatistics(queryParams)
    download.excel(data, '能源消耗统计.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(async () => {
  // 加载基础数据
  await getEnergyTypeList()
  // 加载列表数据
  getList()
})
</script>