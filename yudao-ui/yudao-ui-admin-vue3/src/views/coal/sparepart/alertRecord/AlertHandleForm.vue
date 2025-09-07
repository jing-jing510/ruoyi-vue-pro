<!-- 预警处理表单 -->
<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      v-loading="formLoading"
    >
      <el-form-item label="预警信息">
        <el-input
          v-model="alertInfo.alertMessage"
          readonly
          type="textarea"
          :rows="2"
        />
      </el-form-item>
      <el-form-item label="备件名称">
        <el-input v-model="alertInfo.sparePartName" readonly />
      </el-form-item>
      <el-form-item label="当前库存">
        <el-input v-model="alertInfo.currentStock" readonly />
      </el-form-item>
      <el-form-item label="阈值">
        <el-input v-model="alertInfo.thresholdValue" readonly />
      </el-form-item>
      <el-form-item label="处理备注" prop="handleRemark">
        <el-input
          v-model="formData.handleRemark"
          type="textarea"
          :rows="4"
          :placeholder="handleType === 'handle' ? '请输入处理措施和结果' : '请输入忽略原因'"
          maxlength="500"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">
        {{ handleType === 'handle' ? '确认处理' : '确认忽略' }}
      </el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { ref, reactive, computed } from 'vue'
import { useMessage } from '@/hooks/web/useMessage'
import { useUserStore } from '@/store/modules/user'
import { SparePartAlertApi } from '@/api/coal/sparepart/alert'

/** 预警处理表单 */
defineOptions({ name: 'AlertHandleForm' })

const emit = defineEmits(['success'])
const message = useMessage()
const userStore = useUserStore()

const dialogVisible = ref(false)
const formLoading = ref(false)
const handleType = ref('') // 'handle' 或 'ignore'
const alertId = ref(0)

const dialogTitle = computed(() => {
  return handleType.value === 'handle' ? '处理预警' : '忽略预警'
})

const formData = ref({
  handleRemark: ''
})

const alertInfo = ref({
  alertMessage: '',
  sparePartName: '',
  currentStock: '',
  thresholdValue: ''
})

const formRef = ref()
const formRules = reactive({
  handleRemark: [{ required: true, message: '处理备注不能为空', trigger: 'blur' }]
})

/** 打开弹窗 */
const open = async (type: string, id: number) => {
  dialogVisible.value = true
  handleType.value = type
  alertId.value = id
  resetForm()
  
  // 获取预警详情
  formLoading.value = true
  try {
    const data = await SparePartAlertApi.get(id)
    alertInfo.value = {
      alertMessage: data.alertMessage,
      sparePartName: data.sparePartName || '未知备件',
      currentStock: data.currentStock?.toString() || '0',
      thresholdValue: data.thresholdValue?.toString() || '0'
    }
  } catch (error) {
    console.error('获取预警详情失败:', error)
  } finally {
    formLoading.value = false
  }
}

/** 提交表单 */
const submitForm = async () => {
  const valid = await formRef.value.validate()
  if (!valid) return
  
  formLoading.value = true
  try {
    const handlerId = userStore.getUser.id
    
    if (handleType.value === 'handle') {
      await SparePartAlertApi.handle(alertId.value, handlerId, formData.value.handleRemark)
      message.success('预警处理成功')
    } else {
      await SparePartAlertApi.ignore(alertId.value, handlerId, formData.value.handleRemark)
      message.success('预警忽略成功')
    }
    
    dialogVisible.value = false
    emit('success')
  } catch (error) {
    console.error('预警处理失败:', error)
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    handleRemark: ''
  }
  alertInfo.value = {
    alertMessage: '',
    sparePartName: '',
    currentStock: '',
    thresholdValue: ''
  }
  formRef.value?.resetFields()
}

defineExpose({ open })
</script>
