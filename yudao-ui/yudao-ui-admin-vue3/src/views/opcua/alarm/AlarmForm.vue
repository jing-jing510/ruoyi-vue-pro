<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="800px">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      v-loading="formLoading"
    >
      <el-form-item label="OPC UA配置" prop="configId">
        <el-select v-model="formData.configId" placeholder="请选择OPC UA配置" class="!w-full">
          <el-option
            v-for="config in configList"
            :key="config.id"
            :label="config.name"
            :value="config.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备名称" prop="deviceName">
        <el-input v-model="formData.deviceName" placeholder="请输入设备名称" />
      </el-form-item>
      <el-form-item label="点位名称" prop="tagName">
        <el-input v-model="formData.tagName" placeholder="请输入点位名称" />
      </el-form-item>
      <el-form-item label="NodeId" prop="nodeId">
        <el-input v-model="formData.nodeId" placeholder="请输入NodeId，如：ns=3;i=1009" />
      </el-form-item>
      <el-form-item label="报警级别" prop="alarmLevel">
        <el-radio-group v-model="formData.alarmLevel">
          <el-radio :label="1">提示</el-radio>
          <el-radio :label="2">警告</el-radio>
          <el-radio :label="3">错误</el-radio>
          <el-radio :label="4">严重</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="报警内容" prop="alarmContent">
        <el-input
          v-model="formData.alarmContent"
          type="textarea"
          :rows="3"
          placeholder="请输入报警内容"
        />
      </el-form-item>
      <el-form-item label="报警时间" prop="alarmTime">
        <el-date-picker
          v-model="formData.alarmTime"
          type="datetime"
          placeholder="选择报警时间"
          value-format="YYYY-MM-DD HH:mm:ss"
          class="!w-full"
        />
      </el-form-item>
      <el-form-item label="报警状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio :label="0">待处理</el-radio>
          <el-radio :label="1">已处理</el-radio>
          <el-radio :label="2">已忽略</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="formData.remark"
          type="textarea"
          :rows="2"
          placeholder="请输入备注"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { getOpcuaConfigList } from '@/api/opcua/config'
import { createAlarmEvent, updateAlarmEvent, getAlarmEvent, type AlarmEventVO } from '@/api/opcua/alarm'

const { t } = useI18n()
const message = useMessage()

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formData = ref({
  id: undefined,
  configId: undefined,
  deviceName: '',
  tagId: undefined,
  tagName: '',
  nodeId: '',
  alarmLevel: 2,
  alarmContent: '',
  alarmTime: undefined,
  status: 0,
  remark: ''
})
const formRules = reactive({
  configId: [{ required: true, message: '请选择OPC UA配置', trigger: 'change' }],
  tagName: [{ required: true, message: '请输入点位名称', trigger: 'blur' }],
  alarmLevel: [{ required: true, message: '请选择报警级别', trigger: 'change' }],
  alarmContent: [{ required: true, message: '请输入报警内容', trigger: 'blur' }],
  alarmTime: [{ required: true, message: '请选择报警时间', trigger: 'change' }]
})
const formRef = ref()
const configList = ref([])

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = type === 'create' ? '新增报警事件' : '修改报警事件'
  formType.value = type
  resetForm()
  
  // 加载配置列表
  await loadConfigList()
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      const data = await getAlarmEvent(id)
      formData.value = data
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open })

/** 加载配置列表 */
const loadConfigList = async () => {
  try {
    const data = await getOpcuaConfigList()
    configList.value = data
  } catch {}
}

/** 提交表单 */
const emit = defineEmits(['success'])
const submitForm = async () => {
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  
  formLoading.value = true
  try {
    const data = formData.value as unknown as AlarmEventVO
    if (formType.value === 'create') {
      await createAlarmEvent(data)
      message.success(t('common.createSuccess'))
    } else {
      await updateAlarmEvent(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    configId: undefined,
    deviceName: '',
    tagId: undefined,
    tagName: '',
    nodeId: '',
    alarmLevel: 2,
    alarmContent: '',
    alarmTime: undefined,
    status: 0,
    remark: ''
  }
  formRef.value?.resetFields()
}
</script>
