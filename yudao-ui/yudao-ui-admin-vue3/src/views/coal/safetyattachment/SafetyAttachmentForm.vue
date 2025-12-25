<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="业务类型" prop="businessType">
        <el-select v-model="formData.businessType" placeholder="请选择业务类型" @change="handleBusinessTypeChange">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_ATTACHMENT_BUSINESS_TYPE).filter(d => d.value !== 2)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="业务记录" prop="businessId">
        <el-select v-model="formData.businessId" placeholder="请选择业务记录" clearable>
          <el-option
            v-for="item in businessList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="附件上传" prop="filePath">
        <UploadImg v-model="formData.filePath" :limit="1" @success="handleUploadSuccess" />
      </el-form-item>
      <el-form-item label="上传时间" prop="uploadTime">
        <el-date-picker
          v-model="formData.uploadTime"
          type="date"
          value-format="x"
          placeholder="选择上传时间"
        />
      </el-form-item>
      <el-form-item label="上传人" prop="uploaderId">
        <el-select v-model="formData.uploaderId" placeholder="请选择上传人" clearable @change="handleUploaderChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上传人姓名" prop="uploaderName">
        <el-input v-model="formData.uploaderName" placeholder="上传人姓名" readonly />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注" />
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
import { SafetyAttachmentApi, SafetyAttachment } from '@/api/coal/safetyattachment'
import { SafetyCheckRecordApi } from '@/api/coal/safetycheckrecord'
import { SafetyAccidentApi } from '@/api/coal/safetyaccident'
import { getSimpleUserList } from '@/api/system/user'

/** 安全附件 表单 */
defineOptions({ name: 'SafetyAttachmentForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  businessType: undefined,
  businessId: undefined,
  filePath: undefined,
  uploadTime: undefined,
  uploaderId: undefined,
  uploaderName: undefined,
  remark: undefined,
})
const formRules = reactive({
  businessType: [{ required: true, message: '业务类型不能为空', trigger: 'change' }],
  businessId: [{ required: true, message: '业务记录不能为空', trigger: 'blur' }],
  filePath: [{ required: true, message: '文件路径不能为空', trigger: 'blur' }],
  uploadTime: [{ required: true, message: '上传时间不能为空', trigger: 'blur' }],
  uploaderId: [{ required: true, message: '上传人不能为空', trigger: 'blur' }],
  uploaderName: [{ required: true, message: '上传人姓名不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const userList = ref([]) // 用户列表
const businessList = ref([]) // 业务记录列表

/** 业务类型变化处理 */
const handleBusinessTypeChange = async (businessType: number) => {
  formData.value.businessId = undefined
  businessList.value = []
  
  try {
    if (businessType === 1) {
      // 安全检查记录
      const data = await SafetyCheckRecordApi.getSafetyCheckRecordPage({ pageNo: 1, pageSize: 100 })
      businessList.value = data.list.map(item => ({
        id: item.id,
        name: item.recordName || item.recordCode
      }))
    } else if (businessType === 3) {
      // 安全事故记录
      const data = await SafetyAccidentApi.getSafetyAccidentPage({ pageNo: 1, pageSize: 100 })
      businessList.value = data.list.map(item => ({
        id: item.id,
        name: item.accidentTitle || item.accidentCode
      }))
    }
  } catch (error) {
    console.error('获取业务记录列表失败:', error)
  }
}

/** 上传人选择变化处理 */
const handleUploaderChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.uploaderName = user.nickname
  } else {
    formData.value.uploaderName = ''
  }
}

/** 文件上传成功处理 */
const handleUploadSuccess = (response: any) => {
  if (response && response.data) {
    const fileInfo = response.data
    formData.value.filePath = fileInfo.url
    formData.value.uploadTime = new Date().toISOString()
  }
}

/** 获取用户列表 */
const getUserList = async () => {
  try {
    const data = await getSimpleUserList()
    userList.value = data
  } catch (error) {
    console.error('获取用户列表失败:', error)
  }
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  
  // 加载基础数据
  await getUserList()
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await SafetyAttachmentApi.getSafetyAttachment(id)
      // 根据业务类型加载对应的业务记录
      if (formData.value.businessType) {
        await handleBusinessTypeChange(formData.value.businessType)
      }
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
    const data = formData.value as unknown as SafetyAttachment
    if (formType.value === 'create') {
      await SafetyAttachmentApi.createSafetyAttachment(data)
      message.success(t('common.createSuccess'))
    } else {
      await SafetyAttachmentApi.updateSafetyAttachment(data)
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
    businessType: undefined,
    businessId: undefined,
    fileName: undefined,
    filePath: undefined,
    fileSize: undefined,
    fileType: undefined,
    fileExtension: undefined,
    uploadTime: undefined,
    uploaderId: undefined,
    uploaderName: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>