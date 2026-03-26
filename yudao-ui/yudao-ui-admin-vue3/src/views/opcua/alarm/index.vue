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
      <el-form-item label="设备名称" prop="deviceName">
        <el-input
          v-model="queryParams.deviceName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="报警级别" prop="alarmLevel">
        <el-select v-model="queryParams.alarmLevel" placeholder="请选择报警级别" clearable class="!w-240px">
          <el-option label="提示" :value="1" />
          <el-option label="警告" :value="2" />
          <el-option label="错误" :value="3" />
          <el-option label="严重" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item label="报警状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择报警状态" clearable class="!w-240px">
          <el-option label="待处理" :value="0" />
          <el-option label="已处理" :value="1" />
          <el-option label="已忽略" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="报警时间" prop="alarmTime">
        <el-date-picker
          v-model="queryParams.alarmTime"
          type="datetimerange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD HH:mm:ss"
          class="!w-340px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['opcua:alarm:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="报警ID" align="center" prop="id" width="80" />
      <el-table-column label="设备名称" align="center" prop="deviceName" min-width="150" />
      <el-table-column label="点位名称" align="center" prop="tagName" width="120" />
      <el-table-column label="报警级别" align="center" prop="alarmLevel" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.alarmLevel === 1" type="info">提示</el-tag>
          <el-tag v-else-if="scope.row.alarmLevel === 2" type="warning">警告</el-tag>
          <el-tag v-else-if="scope.row.alarmLevel === 3" type="danger">错误</el-tag>
          <el-tag v-else-if="scope.row.alarmLevel === 4" type="danger" effect="dark">严重</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="报警内容" align="center" prop="alarmContent" min-width="200" show-overflow-tooltip />
      <el-table-column
        label="报警时间"
        align="center"
        prop="alarmTime"
        width="180"
        :formatter="dateFormatter"
      />
      <el-table-column label="报警状态" align="center" prop="status" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.status === 0" type="danger">待处理</el-tag>
          <el-tag v-else-if="scope.row.status === 1" type="success">已处理</el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="info">已忽略</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="处理人" align="center" prop="handleUserName" width="100" />
      <el-table-column label="操作" align="center" width="220" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['opcua:alarm:update']"
          >
            修改
          </el-button>
          <el-button
            link
            type="primary"
            @click="handleProcess(scope.row)"
            v-if="scope.row.status === 0"
            v-hasPermi="['opcua:alarm:handle']"
          >
            处理
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['opcua:alarm:delete']"
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

  <!-- 详情对话框 -->
  <Dialog title="报警详情" v-model="detailVisible" width="800px">
    <el-descriptions :column="2" border>
      <el-descriptions-item label="报警ID">{{ detailData.id }}</el-descriptions-item>
      <el-descriptions-item label="设备名称">{{ detailData.deviceName }}</el-descriptions-item>
      <el-descriptions-item label="点位名称">{{ detailData.tagName }}</el-descriptions-item>
      <el-descriptions-item label="NodeId">{{ detailData.nodeId }}</el-descriptions-item>
      <el-descriptions-item label="报警级别">
        <el-tag v-if="detailData.alarmLevel === 1" type="info">提示</el-tag>
        <el-tag v-else-if="detailData.alarmLevel === 2" type="warning">警告</el-tag>
        <el-tag v-else-if="detailData.alarmLevel === 3" type="danger">错误</el-tag>
        <el-tag v-else-if="detailData.alarmLevel === 4" type="danger" effect="dark">严重</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="报警状态">
        <el-tag v-if="detailData.status === 0" type="danger">待处理</el-tag>
        <el-tag v-else-if="detailData.status === 1" type="success">已处理</el-tag>
        <el-tag v-else-if="detailData.status === 2" type="info">已忽略</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="报警内容" :span="2">{{ detailData.alarmContent }}</el-descriptions-item>
      <el-descriptions-item label="报警时间">{{ detailData.alarmTime }}</el-descriptions-item>
      <el-descriptions-item label="处理人">{{ detailData.handleUserName }}</el-descriptions-item>
      <el-descriptions-item label="处理时间">{{ detailData.handleTime }}</el-descriptions-item>
      <el-descriptions-item label="处理意见" :span="2">{{ detailData.handleRemark }}</el-descriptions-item>
    </el-descriptions>
  </Dialog>

  <!-- 处理对话框 -->
  <Dialog title="处理报警" v-model="processVisible" width="600px">
    <el-form ref="processFormRef" :model="processForm" :rules="processRules" label-width="100px">
      <el-form-item label="处理结果" prop="status">
        <el-radio-group v-model="processForm.status">
          <el-radio :label="1">已处理</el-radio>
          <el-radio :label="2">已忽略</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="处理意见" prop="handleRemark">
        <el-input
          v-model="processForm.handleRemark"
          type="textarea"
          :rows="4"
          placeholder="请输入处理意见"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="processVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitProcess">确 定</el-button>
    </template>
  </Dialog>

  <!-- 表单对话框：添加/修改 -->
  <AlarmForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts" name="OpcuaAlarm">
import { dateFormatter } from '@/utils/formatTime'
import { getAlarmEventPage, getAlarmEvent, handleAlarmEvent, deleteAlarmEvent } from '@/api/opcua/alarm'
import AlarmForm from './AlarmForm.vue'

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true)
const list = ref([])
const total = ref(0)
const detailVisible = ref(false)
const processVisible = ref(false)
const detailData = ref<any>({})
const processForm = ref({
  id: undefined,
  status: 1,
  handleRemark: ''
})

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  deviceName: undefined,
  alarmLevel: undefined,
  status: undefined,
  alarmTime: []
})

const queryFormRef = ref()
const processFormRef = ref()
const formRef = ref()

const processRules = reactive({
  status: [{ required: true, message: '请选择处理结果', trigger: 'change' }],
  handleRemark: [{ required: true, message: '请输入处理意见', trigger: 'blur' }]
})

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await getAlarmEventPage(queryParams)
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

/** 详情按钮操作 */
const handleDetail = async (row: any) => {
  detailData.value = await getAlarmEvent(row.id)
  detailVisible.value = true
}

/** 添加/修改操作 */
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 处理按钮操作 */
const handleProcess = (row: any) => {
  processForm.value = {
    id: row.id,
    status: 1,
    handleRemark: ''
  }
  processVisible.value = true
}

/** 提交处理 */
const submitProcess = async () => {
  if (!processFormRef.value) return
  const valid = await processFormRef.value.validate()
  if (!valid) return

  try {
    await handleAlarmEvent(processForm.value)
    message.success('处理成功')
    processVisible.value = false
    await getList()
  } catch {}
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await deleteAlarmEvent(id)
    message.success(t('common.delSuccess'))
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
