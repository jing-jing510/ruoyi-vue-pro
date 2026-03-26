<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="600px">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="配置" prop="configId">
        <el-select v-model="formData.configId" placeholder="请选择">
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
      <el-form-item label="点位名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入点位名称" />
      </el-form-item>
      <el-form-item label="NodeId" prop="nodeId">
        <el-input v-model="formData.nodeId" placeholder="例如：ns=2;s=Temperature" />
      </el-form-item>
      <el-form-item label="数据类型" prop="dataType">
        <el-select v-model="formData.dataType" placeholder="请选择">
          <el-option label="Boolean" value="Boolean" />
          <el-option label="Int16" value="Int16" />
          <el-option label="Int32" value="Int32" />
          <el-option label="Float" value="Float" />
          <el-option label="Double" value="Double" />
          <el-option label="String" value="String" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否报警" prop="isAlarm">
        <el-radio-group v-model="formData.isAlarm">
          <el-radio :label="true">是</el-radio>
          <el-radio :label="false">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="报警级别" prop="alarmLevel" v-if="formData.isAlarm">
        <el-select v-model="formData.alarmLevel" placeholder="请选择">
          <el-option label="提示" :value="1" />
          <el-option label="警告" :value="2" />
          <el-option label="错误" :value="3" />
          <el-option label="严重" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item label="报警内容" prop="alarmContent" v-if="formData.isAlarm">
        <el-input v-model="formData.alarmContent" placeholder="请输入报警内容" />
      </el-form-item>
      <el-form-item label="是否启用" prop="enabled">
        <el-radio-group v-model="formData.enabled">
          <el-radio :label="true">启用</el-radio>
          <el-radio :label="false">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { getTag, createTag, updateTag } from '@/api/opcua/tag'
import { getConfigPage } from '@/api/opcua/config'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const configList = ref([])
const formData = ref({
  id: undefined,
  configId: undefined,
  deviceName: undefined,
  name: undefined,
  nodeId: undefined,
  dataType: 'Boolean',
  isAlarm: true,
  alarmLevel: 2,
  alarmContent: undefined,
  enabled: true
})
const formRules = reactive({
  configId: [{ required: true, message: '请选择配置', trigger: 'change' }],
  name: [{ required: true, message: '点位名称不能为空', trigger: 'blur' }],
  nodeId: [{ required: true, message: 'NodeId不能为空', trigger: 'blur' }],
  isAlarm: [{ required: true, message: '请选择是否报警', trigger: 'change' }],
  enabled: [{ required: true, message: '请选择是否启用', trigger: 'change' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 加载配置列表
  await loadConfigList()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await getTag(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 加载配置列表 */
const loadConfigList = async () => {
  const data = await getConfigPage({ pageNo: 1, pageSize: 100 })
  configList.value = data.list
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件
const submitForm = async () => {
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  formLoading.value = true
  try {
    const data = formData.value as unknown as any
    if (formType.value === 'create') {
      await createTag(data)
      message.success(t('common.createSuccess'))
    } else {
      await updateTag(data)
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
    deviceName: undefined,
    name: undefined,
    nodeId: undefined,
    dataType: 'Boolean',
    isAlarm: true,
    alarmLevel: 2,
    alarmContent: undefined,
    enabled: true
  }
  formRef.value?.resetFields()
}
</script>
