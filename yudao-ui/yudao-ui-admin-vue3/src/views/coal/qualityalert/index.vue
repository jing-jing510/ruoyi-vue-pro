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
      <el-form-item label="预警类型" prop="alertType">
        <el-select
          v-model="queryParams.alertType"
          placeholder="请选择预警类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_ALERT_TYPE)"
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
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_ALERT_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检测项目ID" prop="qualityItemId">
        <el-input
          v-model="queryParams.qualityItemId"
          placeholder="请输入检测项目ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="关联检测记录ID" prop="inspectionId">
        <el-input
          v-model="queryParams.inspectionId"
          placeholder="请输入关联检测记录ID"
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
      <el-form-item label="预警状态" prop="alertStatus">
        <el-select
          v-model="queryParams.alertStatus"
          placeholder="请选择预警状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_ALERT_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否自动预警：0手动 1自动" prop="isAutoAlert">
        <el-input
          v-model="queryParams.isAutoAlert"
          placeholder="请输入是否自动预警：0手动 1自动"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="是否已发送通知：0否 1是" prop="notificationSent">
        <el-input
          v-model="queryParams.notificationSent"
          placeholder="请输入是否已发送通知：0否 1是"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['coal:quality-alert:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:quality-alert:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:quality-alert:delete']"
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
      <el-table-column label="预警记录ID" align="center" prop="id" />
      <el-table-column label="预警编号" align="center" prop="alertCode" />
      <el-table-column label="预警类型" align="center" prop="alertType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_ALERT_TYPE" :value="scope.row.alertType" />
        </template>
      </el-table-column>
      <el-table-column label="预警级别" align="center" prop="alertLevel">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_ALERT_LEVEL" :value="scope.row.alertLevel" />
        </template>
      </el-table-column>
      <el-table-column label="检测项目" align="center" prop="qualityItemId">
        <template #default="scope">
          {{ getQualityItemName(scope.row.qualityItemId) }}
        </template>
      </el-table-column>
      <el-table-column label="关联检测记录" align="center" prop="inspectionId">
        <template #default="scope">
          {{ getInspectionCode(scope.row.inspectionId) }}
        </template>
      </el-table-column>
      <el-table-column label="产品类型" align="center" prop="productType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_PRODUCT_TYPE" :value="scope.row.productType" />
        </template>
      </el-table-column>
      <el-table-column label="检测值" align="center" prop="measuredValue" />
      <el-table-column label="标准值" align="center" prop="standardValue" />
      <el-table-column label="预警阈值" align="center" prop="thresholdValue" />
      <el-table-column label="偏差值" align="center" prop="deviation" />
      <el-table-column label="预警信息" align="center" prop="alertMessage" />
      <el-table-column
        label="预警时间"
        align="center"
        prop="alertTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="预警状态" align="center" prop="alertStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_ALERT_STATUS" :value="scope.row.alertStatus" />
        </template>
      </el-table-column>
      <el-table-column label="处理人ID" align="center" prop="handlerId" />
      <el-table-column label="处理人姓名" align="center" prop="handlerName" />
      <el-table-column
        label="处理时间"
        align="center"
        prop="handleTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="处理措施" align="center" prop="handleMethod" />
      <el-table-column label="处理结果" align="center" prop="handleResult" />
      <el-table-column label="是否自动预警" align="center" prop="isAutoAlert">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_AUTO_ALERT" :value="scope.row.isAutoAlert" />
        </template>
      </el-table-column>
      <el-table-column label="是否已发送通知" align="center" prop="notificationSent">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_QUALITY_NOTIFICATION_SENT" :value="scope.row.notificationSent" />
        </template>
      </el-table-column>
      <el-table-column
        label="通知发送时间"
        align="center"
        prop="notificationTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="接收人列表" align="center" prop="recipients" width="200px">
        <template #default="scope">
          <span v-if="scope.row.recipients">{{ scope.row.recipients }}</span>
          <span v-else class="text-gray-400">未设置</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" min-width="180px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['coal:quality-alert:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="success"
            @click="handleSendNotification(scope.row)"
            v-hasPermi="['coal:quality-alert:update']"
            :disabled="scope.row.notificationSent === 1"
          >
            {{ scope.row.notificationSent === 1 ? '已发送' : '发送通知' }}
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:quality-alert:delete']"
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
  <QualityAlertForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { QualityAlertApi, QualityAlert } from '@/api/coal/qualityalert'
import { QualityItemApi, QualityItem } from '@/api/coal/qualityitem'
import { QualityInspectionApi, QualityInspection } from '@/api/coal/qualityinspection'
import QualityAlertForm from './QualityAlertForm.vue'

/** 质量预警记录 列表 */
defineOptions({ name: 'QualityAlert' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

// 检测项目列表
const qualityItemList = ref<QualityItem[]>([])
// 检测记录列表
const inspectionList = ref<QualityInspection[]>([])

const loading = ref(true) // 列表的加载中
const list = ref<QualityAlert[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  alertCode: undefined,
  alertType: undefined,
  alertLevel: undefined,
  qualityItemId: undefined,
  inspectionId: undefined,
  productType: undefined,
  alertStatus: undefined,
  isAutoAlert: undefined,
  notificationSent: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await QualityAlertApi.getQualityAlertPage(queryParams)
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

/** 加载检测记录列表 */
const loadInspectionList = async () => {
  try {
    const data = await QualityInspectionApi.getQualityInspectionPage({ pageNo: 1, pageSize: 100 })
    inspectionList.value = data.list || []
  } catch (error) {
    console.error('加载检测记录列表失败:', error)
  }
}

/** 获取检测项目名称 */
const getQualityItemName = (itemId: number) => {
  const item = qualityItemList.value.find(i => i.id === itemId)
  return item ? item.itemName : '-'
}

/** 获取检测记录编号 */
const getInspectionCode = (inspectionId: number) => {
  const inspection = inspectionList.value.find(i => i.id === inspectionId)
  return inspection ? inspection.inspectionCode : '-'
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
    await QualityAlertApi.deleteQualityAlert(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除质量预警记录 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await QualityAlertApi.deleteQualityAlertList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: QualityAlert[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await QualityAlertApi.exportQualityAlert(queryParams)
    download.excel(data, '质量预警记录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 发送通知按钮操作 */
const handleSendNotification = async (row: QualityAlert) => {
  try {
    // 检查是否有接收人
    if (!row.recipients || row.recipients.trim() === '') {
      message.warning('该预警没有设置接收人，请先设置接收人')
      return
    }

    // 发送通知的二次确认
    await message.confirm('确定要发送该质量预警的站内信通知吗？')
    
    // 发送站内信通知
    await QualityAlertApi.sendQualityAlertNotification(row.id)
    message.success('站内信通知发送成功')
    
    // 刷新列表
    await getList()
  } catch (error) {
    console.error('发送站内信通知失败:', error)
    message.error('发送站内信通知失败')
  }
}

/** 初始化 **/
onMounted(() => {
  Promise.all([loadQualityItemList(), loadInspectionList()]).then(() => {
    getList()
  })
})
</script>