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
      <el-form-item label="预警编号" prop="alertCode">
        <el-input
          v-model="queryParams.alertCode"
          placeholder="请输入预警编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
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
      <el-form-item label="预警类型" prop="alertType">
        <el-select
          v-model="queryParams.alertType"
          placeholder="请选择预警类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_ALERT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警级别" prop="alertLevel">
        <el-select
          v-model="queryParams.alertLevel"
          placeholder="请选择预警级别"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_ALERT_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警状态" prop="alertStatus">
        <el-select
          v-model="queryParams.alertStatus"
          placeholder="请选择预警状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_ALERT_STATUS)"
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
          v-hasPermi="['coal:energy-alert:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:energy-alert:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:energy-alert:delete']"
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
      <el-table-column label="预警ID" align="center" prop="id" />
      <el-table-column label="预警编号" align="center" prop="alertCode" />
      <el-table-column label="能源类型" align="center" prop="energyTypeId">
        <template #default="scope">
          {{ getEnergyTypeName(scope.row.energyTypeId) }}
        </template>
      </el-table-column>
      <el-table-column label="预警类型" align="center" prop="alertType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_ENERGY_ALERT_TYPE" :value="scope.row.alertType" />
        </template>
      </el-table-column>
      <el-table-column label="预警级别" align="center" prop="alertLevel">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_ENERGY_ALERT_LEVEL" :value="scope.row.alertLevel" />
        </template>
      </el-table-column>
      <el-table-column label="预警标题" align="center" prop="alertTitle" />
      <el-table-column label="预警内容" align="center" prop="alertContent" />
      <el-table-column label="当前值" align="center" prop="currentValue" />
      <el-table-column label="阈值" align="center" prop="thresholdValue" />
      <el-table-column label="偏差率(%)" align="center" prop="deviationRate" />
      <el-table-column
        label="预警时间"
        align="center"
        prop="alertTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="预警状态" align="center" prop="alertStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_ENERGY_ALERT_STATUS" :value="scope.row.alertStatus" />
        </template>
      </el-table-column>
      <el-table-column label="处理人" align="center" prop="handlerName" />
      <el-table-column
        label="处理时间"
        align="center"
        prop="handleTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="处理结果" align="center" prop="handleResult" />
      <el-table-column label="处理备注" align="center" prop="handleRemark" />
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
            v-hasPermi="['coal:energy-alert:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:energy-alert:delete']"
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
  <EnergyAlertForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { EnergyAlertApi, EnergyAlert } from '@/api/coal/energyalert'
import { EnergyTypeApi } from '@/api/coal/energytype'
import EnergyAlertForm from './EnergyAlertForm.vue'

/** 能源预警记录 列表 */
defineOptions({ name: 'EnergyAlert' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<EnergyAlert[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  alertCode: undefined,
  energyTypeId: undefined,
  alertType: undefined,
  alertLevel: undefined,
  alertStatus: undefined,
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
    const data = await EnergyAlertApi.getEnergyAlertPage(queryParams)
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
    await EnergyAlertApi.deleteEnergyAlert(id)
    message.success(t('common.delSuccess'))
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除能源预警记录 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await EnergyAlertApi.deleteEnergyAlertList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: EnergyAlert[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await EnergyAlertApi.exportEnergyAlert(queryParams)
    download.excel(data, '能源预警记录.xls')
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