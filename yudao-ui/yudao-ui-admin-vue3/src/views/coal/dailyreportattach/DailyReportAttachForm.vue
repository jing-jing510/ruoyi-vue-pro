<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="日报日期" prop="reportDate">
        <el-date-picker
          v-model="formData.reportDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择日报日期"
        />
      </el-form-item>
      <el-form-item label="日报文件" prop="reportFileUrl">
        <UploadFile
          v-model="reportFileModel"
          :limit="1"
          :fileType="['pdf','doc','docx','xls','xlsx']"
          :fileSize="20"
          :autoUpload="true"
          :drag="false"
          directory="coal_daily_report_file"
        />
      </el-form-item>
      <el-form-item label="图片上传" prop="imageUrls">
        <UploadImgs
          v-model="imageUrlsModel"
          :limit="9"
          :fileSize="10"
          directory="coal_daily_report_image"
        />
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
import { DailyReportAttachApi, DailyReportAttach } from '@/api/coal/dailyreportattach'
import UploadFile from '@/components/UploadFile/src/UploadFile.vue'
import UploadImgs from '@/components/UploadFile/src/UploadImgs.vue'

/** 生产日报附件上传 表单 */
defineOptions({ name: 'DailyReportAttachForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
type FormVO = {
  id?: number
  reportDate?: number | string
  reportFileUrl?: string
  imageUrls?: string
  remark?: string
}

const formData = ref<FormVO>({
  id: undefined,
  reportDate: undefined,
  reportFileUrl: undefined,
  imageUrls: undefined,
  remark: undefined,
})
const formRules = reactive({
  reportDate: [{ required: true, message: '日报日期不能为空', trigger: 'blur' }],
  reportFileUrl: [{ required: true, message: '请上传日报文件', trigger: 'change' }],
})
// UploadImgs 组件使用数组，和表字段（英文逗号分隔字符串）之间做双向转换
const imageUrlsModel = computed<string[]>({
  get: () => {
    const val = formData.value.imageUrls as unknown as string | undefined
    return val ? val.split(',').filter((s) => !!s) : []
  },
  set: (arr: string[]) => {
    formData.value.imageUrls = (arr && arr.length) ? arr.join(',') : undefined
  }
})

// UploadFile 支持 v-model string|string[]，我们统一为单文件字符串
const reportFileModel = computed<string>({
  get: () => (formData.value.reportFileUrl as unknown as string) || '',
  set: (val: string) => {
    formData.value.reportFileUrl = val || undefined
  }
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
      formData.value = await DailyReportAttachApi.getDailyReportAttach(id) as unknown as FormVO
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
    const data = formData.value as unknown as DailyReportAttach
    if (formType.value === 'create') {
      await DailyReportAttachApi.createDailyReportAttach(data)
      message.success(t('common.createSuccess'))
    } else {
      await DailyReportAttachApi.updateDailyReportAttach(data)
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
    reportDate: undefined,
    reportFileUrl: undefined,
    imageUrls: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>