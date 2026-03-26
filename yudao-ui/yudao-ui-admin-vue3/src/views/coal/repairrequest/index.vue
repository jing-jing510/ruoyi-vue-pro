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
      <el-form-item label="报修单编号" prop="requestCode">
        <el-input
          v-model="queryParams.requestCode"
          placeholder="请输入报修单编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备" prop="equipmentId">
        <el-select
          v-model="queryParams.equipmentId"
          placeholder="请选择设备"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="equipment in equipmentList"
            :key="equipment.id"
            :label="equipment.equipmentName"
            :value="equipment.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="故障类型" prop="faultType">
        <el-select
          v-model="queryParams.faultType"
          placeholder="请选择故障类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.FAULT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="故障级别" prop="faultLevel">
        <el-input
          v-model="queryParams.faultLevel"
          placeholder="请输入故障级别"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="紧急程度" prop="urgencyLevel">
        <el-input
          v-model="queryParams.urgencyLevel"
          placeholder="请输入紧急程度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="报修状态" prop="requestStatus">
        <el-select
          v-model="queryParams.requestStatus"
          placeholder="请选择报修状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.REPAIR_REQUEST_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="修复质量" prop="repairQuality">
        <el-input
          v-model="queryParams.repairQuality"
          placeholder="请输入修复质量"
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
          v-hasPermi="['coal:repair-request:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:repair-request:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:repair-request:delete']"
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
      <el-table-column label="报修单ID" align="center" prop="id" />
      <el-table-column label="报修单编号" align="center" prop="requestCode" />
      <el-table-column label="设备" align="center" prop="equipmentId">
        <template #default="scope">
          {{ getEquipmentName(scope.row.equipmentId) }}
        </template>
      </el-table-column>
      <el-table-column label="设备位置" align="center" prop="equipmentLocation" />
      <el-table-column label="故障类型" align="center" prop="faultType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.FAULT_TYPE" :value="scope.row.faultType" />
        </template>
      </el-table-column>
      <el-table-column label="故障级别" align="center" prop="faultLevel">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.FAULT_LEVEL" :value="scope.row.faultLevel" />
        </template>
      </el-table-column>
      <el-table-column label="故障描述" align="center" prop="faultDescription" />
      <el-table-column label="故障现象" align="center" prop="faultSymptoms" />
      <el-table-column label="故障原因分析" align="center" prop="faultCause" />
      <el-table-column label="影响评估" align="center" prop="impactAssessment" />
      <el-table-column label="紧急程度" align="center" prop="urgencyLevel">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.URGENCY_LEVEL" :value="scope.row.urgencyLevel" />
        </template>
      </el-table-column>
      <el-table-column label="报修状态" align="center" prop="requestStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.REPAIR_REQUEST_STATUS" :value="scope.row.requestStatus" />
        </template>
      </el-table-column>
      <el-table-column label="报修人" align="center" prop="reporterId">
        <template #default="scope">
          {{ getReporterName(scope.row.reporterId) }}
        </template>
      </el-table-column>
      <el-table-column label="报修人电话" align="center" prop="reporterPhone" />
      <el-table-column
        label="报修时间"
        align="center"
        prop="reportTime"
        width="180px"
      >
        <template #default="scope">
          {{ formatDateTime(scope.row.reportTime) }}
        </template>
      </el-table-column>
      <el-table-column label="故障照片" align="center" prop="faultPhotos" width="120">
        <template #default="scope">
          <div v-if="scope.row.faultPhotos" class="file-display">
            <el-image
              v-for="(photo, index) in getFileList(scope.row.faultPhotos)"
              :key="index"
              :src="photo"
              :preview-src-list="getFileList(scope.row.faultPhotos)"
              :initial-index="index"
              class="file-thumbnail"
              fit="cover"
            />
          </div>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="故障视频" align="center" prop="faultVideos" width="120">
        <template #default="scope">
          <div v-if="scope.row.faultVideos" class="file-display">
            <el-button
              v-for="(video, index) in getFileList(scope.row.faultVideos)"
              :key="index"
              type="primary"
              size="small"
              @click="downloadFile(video, '故障视频')"
            >
              视频{{ index + 1 }}
            </el-button>
          </div>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="故障语音" align="center" prop="faultAudio" width="120">
        <template #default="scope">
          <div v-if="scope.row.faultAudio" class="file-display">
            <el-button
              v-for="(audio, index) in getFileList(scope.row.faultAudio)"
              :key="index"
              type="success"
              size="small"
              @click="downloadFile(audio, '故障语音')"
            >
              语音{{ index + 1 }}
            </el-button>
          </div>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="指派处理人" align="center" prop="assignedPerson" />
      <el-table-column label="指派班组" align="center" prop="assignedTeam" />
      <el-table-column
        label="派单时间"
        align="center"
        prop="assignmentTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="预计修复时间"
        align="center"
        prop="expectedRepairTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="实际开始时间"
        align="center"
        prop="actualStartTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column
        label="实际结束时间"
        align="center"
        prop="actualEndTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="修复耗时(小时)" align="center" prop="repairDuration" />
      <el-table-column label="修复方法" align="center" prop="repairMethod" />
      <el-table-column label="更换部件(JSON格式)" align="center" prop="replacedParts" />
      <el-table-column label="修复费用" align="center" prop="repairCost" />
      <el-table-column label="修复质量" align="center" prop="repairQuality">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.REPAIR_QUALITY" :value="scope.row.repairQuality" />
        </template>
      </el-table-column>
      <el-table-column label="测试结果" align="center" prop="testResult" />
      <el-table-column label="预防措施" align="center" prop="preventionMeasures" />
      <el-table-column label="满意度评价" align="center" prop="satisfactionRating">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.SATISFACTION_RATING" :value="scope.row.satisfactionRating" />
        </template>
      </el-table-column>
      <el-table-column label="反馈意见" align="center" prop="feedbackNotes" />
      <el-table-column
        label="关闭时间"
        align="center"
        prop="closeTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="关闭原因" align="center" prop="closeReason" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" width="280px" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['coal:repair-request:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="success"
            @click="handleCreateMaintenanceOrder(scope.row)"
            v-hasPermi="['coal:maintenance-order:create']"
            v-if="scope.row.requestStatus === 1"
          >
            生成检修单
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:repair-request:delete']"
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
  <RepairRequestForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { RepairRequestApi, RepairRequest } from '@/api/coal/repairrequest'
import { MaintenanceOrderApi } from '@/api/coal/maintenanceorder'
import { EquipmentInfoApi } from '@/api/coal/equipmentinfo'
import { getSimpleUserList } from '@/api/system/user'
import RepairRequestForm from './RepairRequestForm.vue'

/** 报修单 列表 */
defineOptions({ name: 'RepairRequest' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<RepairRequest[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数

// 设备列表和用户列表
const equipmentList = ref<any[]>([])
const userList = ref<any[]>([])
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  requestCode: undefined,
  equipmentId: undefined,
  faultType: undefined,
  faultLevel: undefined,
  urgencyLevel: undefined,
  requestStatus: undefined,
  repairQuality: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 获取设备列表 */
const getEquipmentList = async () => {
  try {
    const response = await EquipmentInfoApi.getEquipmentInfoList({})
    equipmentList.value = response.data || response || []
  } catch (error) {
    console.error('获取设备列表失败:', error)
  }
}

/** 获取用户列表 */
const getUserList = async () => {
  try {
    const response = await getSimpleUserList()
    userList.value = response || []
  } catch (error) {
    console.error('获取用户列表失败:', error)
  }
}

/** 根据设备ID获取设备名称 */
const getEquipmentName = (equipmentId: number) => {
  const equipment = equipmentList.value.find(eq => eq.id === equipmentId)
  return equipment ? equipment.equipmentName : `设备ID:${equipmentId}`
}

/** 根据报修人ID获取报修人姓名 */
const getReporterName = (reporterId: number) => {
  const user = userList.value.find(u => u.id === reporterId)
  return user ? user.nickname : `用户ID:${reporterId}`
}

/** 解析文件列表 */
const getFileList = (fileStr: string) => {
  if (!fileStr) return []
  try {
    // 如果是JSON格式，解析为数组
    if (fileStr.startsWith('[') || fileStr.startsWith('{')) {
      return JSON.parse(fileStr)
    }
    // 如果是逗号分隔的字符串，分割为数组
    return fileStr.split(',').filter(item => item.trim())
  } catch (error) {
    console.error('解析文件列表失败:', error)
    return []
  }
}

/** 下载文件 */
const downloadFile = (fileUrl: string, fileName: string) => {
  if (!fileUrl) return
  // 创建一个临时的a标签来下载文件
  const link = document.createElement('a')
  link.href = fileUrl
  link.download = fileName
  link.target = '_blank'
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await RepairRequestApi.getRepairRequestPage(queryParams)
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
    await RepairRequestApi.deleteRepairRequest(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除报修单 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await RepairRequestApi.deleteRepairRequestList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

/** 生成检修单 */
const handleCreateMaintenanceOrder = async (repairRequest: RepairRequest) => {
  try {
    // 确认生成检修单
    await ElMessageBox.confirm(
      `是否确认为报修单"${repairRequest.requestCode}"生成检修单？`,
      '系统提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'info'
      }
    )

    // 构建检修单数据
    const maintenanceOrderData = {
      orderCode: `MO${new Date().getFullYear()}${String(new Date().getMonth() + 1).padStart(2, '0')}${String(new Date().getDate()).padStart(2, '0')}${String(Date.now()).slice(-4)}`,
      repairRequestId: repairRequest.id,
      equipmentId: repairRequest.equipmentId,
      equipmentName: repairRequest.equipmentName,
      maintenanceType: 3, // 故障检修
      maintenanceLevel: repairRequest.faultLevel === 3 ? 3 : repairRequest.faultLevel === 2 ? 2 : 1, // 根据故障级别确定检修级别
      orderStatus: 1, // 待执行
      priorityLevel: repairRequest.urgencyLevel,
      faultDescription: repairRequest.faultDescription,
      maintenanceContent: `根据报修单${repairRequest.requestCode}进行故障检修`,
      safetyMeasures: '断电挂牌、设置安全围栏、穿戴防护用品',
      responsiblePerson: '维修班组长',
      responsibleTeam: '维修班组',
      remark: `由报修单${repairRequest.requestCode}生成`
    }

    // 创建检修单
    await MaintenanceOrderApi.createMaintenanceOrder(maintenanceOrderData)
    
    ElMessage.success('检修单生成成功')
    
    // 刷新列表
    await getList()
  } catch (error: any) {
    if (error !== 'cancel') {
      console.error('生成检修单失败:', error)
      ElMessage.error('生成检修单失败')
    }
  }
}

/** 格式化日期时间显示 */
const formatDateTime = (dateTime: string | Date) => {
  if (!dateTime) return '-'
  const date = new Date(dateTime)
  if (isNaN(date.getTime())) return '-'
  
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  const seconds = String(date.getSeconds()).padStart(2, '0')
  
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: RepairRequest[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await RepairRequestApi.exportRepairRequest(queryParams)
    download.excel(data, '报修单.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(async () => {
  // 先获取基础数据
  await Promise.all([
    getEquipmentList(),
    getUserList()
  ])
  // 再获取列表数据
  await getList()
})
</script>

<style scoped>
.file-display {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
  justify-content: center;
}

.file-thumbnail {
  width: 40px;
  height: 40px;
  border-radius: 4px;
  cursor: pointer;
}
</style>