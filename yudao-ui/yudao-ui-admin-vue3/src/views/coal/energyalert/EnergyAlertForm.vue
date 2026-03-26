<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="预警编号" prop="alertCode">
        <el-input v-model="formData.alertCode" placeholder="请输入预警编号" />
      </el-form-item>
      <el-form-item label="能源类型" prop="energyTypeId">
        <el-select v-model="formData.energyTypeId" placeholder="请选择能源类型" clearable>
          <el-option
            v-for="item in energyTypeList"
            :key="item.id"
            :label="item.typeName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警类型" prop="alertType">
        <el-select v-model="formData.alertType" placeholder="请选择预警类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_ALERT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警级别" prop="alertLevel">
        <el-select v-model="formData.alertLevel" placeholder="请选择预警级别">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_ALERT_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警标题" prop="alertTitle">
        <el-input v-model="formData.alertTitle" placeholder="请输入预警标题" />
      </el-form-item>
      <el-form-item label="预警内容" prop="alertContent">
        <Editor v-model="formData.alertContent" height="150px" />
      </el-form-item>
      <el-form-item label="当前值" prop="currentValue">
        <el-input v-model="formData.currentValue" placeholder="请输入当前值" />
      </el-form-item>
      <el-form-item label="阈值" prop="thresholdValue">
        <el-input v-model="formData.thresholdValue" placeholder="请输入阈值" />
      </el-form-item>
      <el-form-item label="偏差率(%)" prop="deviationRate">
        <el-input v-model="formData.deviationRate" placeholder="请输入偏差率(%)" />
      </el-form-item>
      <el-form-item label="预警时间" prop="alertTime">
        <el-date-picker
          v-model="formData.alertTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择预警时间"
        />
      </el-form-item>
      <el-form-item label="预警状态" prop="alertStatus">
        <el-radio-group v-model="formData.alertStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_ALERT_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="处理人" prop="handlerId">
        <el-select v-model="formData.handlerId" placeholder="请选择处理人" clearable @change="handleHandlerChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处理人姓名" prop="handlerName">
        <el-input v-model="formData.handlerName" placeholder="处理人姓名" readonly />
      </el-form-item>
      <el-form-item label="处理时间" prop="handleTime">
        <el-date-picker
          v-model="formData.handleTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择处理时间"
        />
      </el-form-item>
      <el-form-item label="处理结果" prop="handleResult">
        <el-input v-model="formData.handleResult" placeholder="请输入处理结果" />
      </el-form-item>
      <el-form-item label="处理备注" prop="handleRemark">
        <el-input v-model="formData.handleRemark" placeholder="请输入处理备注" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { EnergyAlertApi, EnergyAlert } from '@/api/coal/energyalert'
import { EnergyTypeApi } from '@/api/coal/energytype'
import { getSimpleUserList } from '@/api/system/user'

/** 能源预警记录 表单 */
defineOptions({ name: 'EnergyAlertForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  alertCode: undefined,
  energyTypeId: undefined,
  alertType: undefined,
  alertLevel: undefined,
  alertTitle: undefined,
  alertContent: undefined,
  currentValue: undefined,
  thresholdValue: undefined,
  deviationRate: undefined,
  alertTime: undefined,
  alertStatus: undefined,
  handlerId: undefined,
  handlerName: undefined,
  handleTime: undefined,
  handleResult: undefined,
  handleRemark: undefined,
})
const formRules = reactive({
  alertCode: [{ required: true, message: '预警编号不能为空', trigger: 'blur' }],
  energyTypeId: [{ required: true, message: '能源类型ID不能为空', trigger: 'blur' }],
  alertType: [{ required: true, message: '预警类型不能为空', trigger: 'change' }],
  alertLevel: [{ required: true, message: '预警级别不能为空', trigger: 'blur' }],
  alertTitle: [{ required: true, message: '预警标题不能为空', trigger: 'blur' }],
  alertContent: [{ required: true, message: '预警内容不能为空', trigger: 'blur' }],
  alertTime: [{ required: true, message: '预警时间不能为空', trigger: 'blur' }],
  alertStatus: [{ required: true, message: '预警状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

// 能源类型列表
const energyTypeList = ref([])
// 用户列表
const userList = ref([])

// 获取能源类型列表
const getEnergyTypeList = async () => {
  try {
    const data = await EnergyTypeApi.getSimpleEnergyTypeList()
    energyTypeList.value = data
  } catch (error) {
    console.error('获取能源类型列表失败:', error)
  }
}

// 获取用户列表
const getUserList = async () => {
  try {
    const data = await getSimpleUserList()
    userList.value = data
  } catch (error) {
    console.error('获取用户列表失败:', error)
  }
}

// 处理人选择变化
const handleHandlerChange = (userId) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.handlerName = user.nickname
  } else {
    formData.value.handlerName = ''
  }
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  
  // 加载基础数据
  await Promise.all([
    getEnergyTypeList(),
    getUserList()
  ])
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await EnergyAlertApi.getEnergyAlert(id)
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
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as EnergyAlert
    if (formType.value === 'create') {
      await EnergyAlertApi.createEnergyAlert(data)
      message.success(t('common.createSuccess'))
    } else {
      await EnergyAlertApi.updateEnergyAlert(data)
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
    alertCode: undefined,
    energyTypeId: undefined,
    alertType: undefined,
    alertLevel: undefined,
    alertTitle: undefined,
    alertContent: undefined,
    currentValue: undefined,
    thresholdValue: undefined,
    deviationRate: undefined,
    alertTime: undefined,
    alertStatus: undefined,
    handlerId: undefined,
    handlerName: undefined,
    handleTime: undefined,
    handleResult: undefined,
    handleRemark: undefined,
  }
  formRef.value?.resetFields()
}
</script>