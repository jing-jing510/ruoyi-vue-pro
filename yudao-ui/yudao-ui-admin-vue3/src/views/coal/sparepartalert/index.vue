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
      <el-form-item label="预警类型" prop="alertType">
        <el-select
          v-model="queryParams.alertType"
          placeholder="请选择预警类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.ALERT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警级别" prop="alertLevel">
        <el-input
          v-model="queryParams.alertLevel"
          placeholder="请输入预警级别"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="预警状态" prop="alertStatus">
        <el-select
          v-model="queryParams.alertStatus"
          placeholder="请选择预警状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.ALERT_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警标题" prop="alertTitle">
        <el-input
          v-model="queryParams.alertTitle"
          placeholder="请输入预警标题"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="预警信息" prop="alertMessage">
        <el-input
          v-model="queryParams.alertMessage"
          placeholder="请输入预警信息"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="发送时间" prop="sendTime">
        <el-date-picker
          v-model="queryParams.sendTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="接收人列表(逗号分隔)" prop="recipients">
        <el-input
          v-model="queryParams.recipients"
          placeholder="请输入接收人列表(逗号分隔)"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
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
          v-hasPermi="['coal:spare-part-alert:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:spare-part-alert:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:spare-part-alert:delete']"
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
      <el-table-column label="备件名称" align="center" prop="sparePartId">
        <template #default="scope">
          {{ getSparePartName(scope.row.sparePartId) }}
        </template>
      </el-table-column>
      <el-table-column label="预警类型" align="center" prop="alertType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.ALERT_TYPE" :value="scope.row.alertType" />
        </template>
      </el-table-column>
      <el-table-column label="预警级别" align="center" prop="alertLevel">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.ALERT_LEVEL" :value="scope.row.alertLevel" />
        </template>
      </el-table-column>
      <el-table-column label="预警状态" align="center" prop="alertStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.ALERT_STATUS" :value="scope.row.alertStatus" />
        </template>
      </el-table-column>
      <el-table-column label="预警标题" align="center" prop="alertTitle" />
      <el-table-column label="预警信息" align="center" prop="alertMessage" />
      <el-table-column label="当前库存" align="center" prop="currentStock" />
      <el-table-column label="阈值" align="center" prop="thresholdValue" />
      <el-table-column label="关联设备" align="center" prop="equipmentId">
        <template #default="scope">
          {{ getEquipmentName(scope.row.equipmentId) }}
        </template>
      </el-table-column>
      <el-table-column label="处理人ID" align="center" prop="handlerId" />
      <el-table-column
        label="处理时间"
        align="center"
        prop="handleTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="处理措施" align="center" prop="handleAction" />
      <el-table-column label="处理备注" align="center" prop="handleRemark" />
      <el-table-column label="是否已发送通知：1是 0否" align="center" prop="isSent" />
      <el-table-column
        label="发送时间"
        align="center"
        prop="sendTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="接收人列表(逗号分隔)" align="center" prop="recipients" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" min-width="250px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['coal:spare-part-alert:update']"
            size="small"
          >
            编辑
          </el-button>
          <el-button
            link
            type="success"
            @click="handleSendNotification(scope.row)"
            :disabled="scope.row.isSent === 1"
            size="small"
          >
            {{ scope.row.isSent === 1 ? '已发送' : '发送' }}
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:spare-part-alert:delete']"
            size="small"
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
  <SparePartAlertForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { SparePartAlertApi, SparePartAlert } from '@/api/coal/sparepartalert'
import { SparePartInfoApi, SparePartInfo } from '@/api/coal/sparepartinfo'
import { EquipmentInfoApi, EquipmentInfo } from '@/api/coal/equipmentinfo'
import SparePartAlertForm from './SparePartAlertForm.vue'

/** 备件预警记录 列表 */
defineOptions({ name: 'SparePartAlert' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<SparePartAlert[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  alertType: undefined,
  alertLevel: undefined,
  alertStatus: undefined,
  alertTitle: undefined,
  alertMessage: undefined,
  sendTime: [],
  recipients: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

// 备件基础信息列表
const sparePartList = ref<SparePartInfo[]>([])
// 设备档案列表
const equipmentList = ref<EquipmentInfo[]>([])

/** 获取备件名称 */
const getSparePartName = (sparePartId: number) => {
  const sparePart = sparePartList.value.find(item => item.id === sparePartId)
  return sparePart ? sparePart.sparePartName : `备件ID: ${sparePartId}`
}

/** 获取设备名称 */
const getEquipmentName = (equipmentId: number) => {
  if (!equipmentId) return '-'
  const equipment = equipmentList.value.find(item => item.id === equipmentId)
  return equipment ? equipment.equipmentName : `设备ID: ${equipmentId}`
}

/** 获取备件基础信息列表 */
const getSparePartList = async () => {
  try {
    const data = await SparePartInfoApi.getSparePartInfoPage({ pageNo: 1, pageSize: 100 })
    sparePartList.value = data.list || []
  } catch (error) {
    console.error('获取备件列表失败:', error)
  }
}

/** 获取设备档案列表 */
const getEquipmentList = async () => {
  try {
    const data = await EquipmentInfoApi.getEquipmentInfoPage({ pageNo: 1, pageSize: 100 })
    equipmentList.value = data.list || []
  } catch (error) {
    console.error('获取设备列表失败:', error)
  }
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SparePartAlertApi.getSparePartAlertPage(queryParams)
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
    await SparePartAlertApi.deleteSparePartAlert(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除备件预警记录 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await SparePartAlertApi.deleteSparePartAlertList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: SparePartAlert[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 发送通知按钮操作 */
const handleSendNotification = async (row: SparePartAlert) => {
  try {
    // 检查是否有接收人
    if (!row.recipients || row.recipients.trim() === '') {
      message.warning('该预警记录没有设置接收人，请先编辑添加接收人')
      return
    }
    
    // 发送确认
    await message.confirm('确认发送该预警通知吗？')
    
    // 调用发送通知API
    await SparePartAlertApi.sendNotification(row.id)
    message.success('预警通知发送成功')
    
    // 刷新列表
    await getList()
  } catch (error) {
    console.error('发送通知失败:', error)
    message.error('发送通知失败')
  }
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await SparePartAlertApi.exportSparePartAlert(queryParams)
    download.excel(data, '备件预警记录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getSparePartList()
  getEquipmentList()
  getList()
})
</script>