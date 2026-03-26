<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      v-loading="formLoading"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item label="配置名称" prop="name">
            <el-input v-model="formData.name" placeholder="请输入配置名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否启用" prop="enabled">
            <el-radio-group v-model="formData.enabled">
              <el-radio :label="true">启用</el-radio>
              <el-radio :label="false">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="服务器地址" prop="serverUrl">
        <el-input v-model="formData.serverUrl" placeholder="例如：opc.tcp://192.168.1.100:4840" />
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="formData.username" placeholder="请输入用户名" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="formData.password"
              type="password"
              placeholder="请输入密码"
              show-password
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="安全策略" prop="securityPolicy">
            <el-select v-model="formData.securityPolicy" placeholder="请选择安全策略">
              <el-option label="None" value="None" />
              <el-option label="Basic128Rsa15" value="Basic128Rsa15" />
              <el-option label="Basic256" value="Basic256" />
              <el-option label="Basic256Sha256" value="Basic256Sha256" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="安全模式" prop="securityMode">
            <el-select v-model="formData.securityMode" placeholder="请选择安全模式">
              <el-option label="None" value="None" />
              <el-option label="Sign" value="Sign" />
              <el-option label="SignAndEncrypt" value="SignAndEncrypt" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="连接超时(ms)" prop="timeout">
        <el-input-number v-model="formData.timeout" :min="1000" :max="60000" :step="1000" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" type="textarea" :rows="3" placeholder="请输入备注" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { getConfig, createConfig, updateConfig } from '@/api/opcua/config'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: undefined,
  serverUrl: undefined,
  username: undefined,
  password: undefined,
  securityPolicy: 'None',
  securityMode: 'None',
  timeout: 5000,
  enabled: true,
  remark: undefined
})
const formRules = reactive({
  name: [{ required: true, message: '配置名称不能为空', trigger: 'blur' }],
  serverUrl: [{ required: true, message: '服务器地址不能为空', trigger: 'blur' }],
  timeout: [{ required: true, message: '连接超时不能为空', trigger: 'blur' }],
  enabled: [{ required: true, message: '是否启用不能为空', trigger: 'change' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await getConfig(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as any
    if (formType.value === 'create') {
      await createConfig(data)
      message.success(t('common.createSuccess'))
    } else {
      await updateConfig(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    name: undefined,
    serverUrl: undefined,
    username: undefined,
    password: undefined,
    securityPolicy: 'None',
    securityMode: 'None',
    timeout: 5000,
    enabled: true,
    remark: undefined
  }
  formRef.value?.resetFields()
}
</script>
