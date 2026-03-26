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
      <el-form-item label="预警类型" prop="alertType">
        <el-select v-model="formData.alertType" placeholder="请选择预警类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_ALERT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警级别" prop="alertLevel">
        <el-select v-model="formData.alertLevel" placeholder="请选择预警级别">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_ALERT_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检测项目" prop="qualityItemId">
        <el-select 
          v-model="formData.qualityItemId" 
          placeholder="请选择检测项目"
          clearable
        >
          <el-option
            v-for="item in qualityItemList"
            :key="item.id"
            :label="item.itemName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联检测记录" prop="inspectionId">
        <el-select 
          v-model="formData.inspectionId" 
          placeholder="请选择关联检测记录"
          clearable
        >
          <el-option
            v-for="inspection in inspectionList"
            :key="inspection.id"
            :label="inspection.inspectionCode"
            :value="inspection.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="产品类型" prop="productType">
        <el-select v-model="formData.productType" placeholder="请选择产品类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_PRODUCT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检测值" prop="measuredValue">
        <el-input v-model="formData.measuredValue" placeholder="请输入检测值" />
      </el-form-item>
      <el-form-item label="标准值" prop="standardValue">
        <el-input v-model="formData.standardValue" placeholder="请输入标准值" />
      </el-form-item>
      <el-form-item label="预警阈值" prop="thresholdValue">
        <el-input v-model="formData.thresholdValue" placeholder="请输入预警阈值" />
      </el-form-item>
      <el-form-item label="偏差值" prop="deviation">
        <el-input v-model="formData.deviation" placeholder="请输入偏差值" />
      </el-form-item>
      <el-form-item label="预警信息" prop="alertMessage">
        <el-input v-model="formData.alertMessage" placeholder="请输入预警信息" />
      </el-form-item>
      <el-form-item label="预警时间" prop="alertTime">
        <el-date-picker
          v-model="formData.alertTime"
          type="date"
          value-format="x"
          placeholder="选择预警时间"
        />
      </el-form-item>
      <el-form-item label="预警状态" prop="alertStatus">
        <el-radio-group v-model="formData.alertStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_ALERT_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="处理人" prop="handlerId">
        <el-select 
          v-model="formData.handlerId" 
          placeholder="请选择处理人"
          @change="handleHandlerChange"
          clearable
        >
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
          type="date"
          value-format="x"
          placeholder="选择处理时间"
        />
      </el-form-item>
      <el-form-item label="处理措施" prop="handleMethod">
        <el-input v-model="formData.handleMethod" placeholder="请输入处理措施" />
      </el-form-item>
      <el-form-item label="处理结果" prop="handleResult">
        <el-input v-model="formData.handleResult" placeholder="请输入处理结果" />
      </el-form-item>
      <el-form-item label="是否自动预警" prop="isAutoAlert">
        <el-select v-model="formData.isAutoAlert" placeholder="请选择是否自动预警">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_AUTO_ALERT)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否已发送通知" prop="notificationSent">
        <el-select v-model="formData.notificationSent" placeholder="请选择是否已发送通知">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_NOTIFICATION_SENT)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="通知发送时间" prop="notificationTime">
        <el-date-picker
          v-model="formData.notificationTime"
          type="date"
          value-format="x"
          placeholder="选择通知发送时间"
        />
      </el-form-item>
      <el-form-item label="接收人列表" prop="recipients">
        <el-select 
          v-model="formData.recipientIds" 
          multiple 
          placeholder="请选择接收人" 
          style="width: 100%"
        >
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
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
import { QualityAlertApi, QualityAlert } from '@/api/coal/qualityalert'
import { QualityItemApi, QualityItem } from '@/api/coal/qualityitem'
import { QualityInspectionApi, QualityInspection } from '@/api/coal/qualityinspection'
import { getSimpleUserList, UserVO } from '@/api/system/user'

/** 质量预警记录 表单 */
defineOptions({ name: 'QualityAlertForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

// 检测项目列表
const qualityItemList = ref<QualityItem[]>([])
// 检测记录列表
const inspectionList = ref<QualityInspection[]>([])
// 用户列表
const userList = ref<UserVO[]>([])

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref<any>({
  id: undefined,
  alertCode: undefined,
  alertType: undefined,
  alertLevel: undefined,
  qualityItemId: undefined,
  inspectionId: undefined,
  productType: undefined,
  measuredValue: undefined,
  standardValue: undefined,
  thresholdValue: undefined,
  deviation: undefined,
  alertMessage: undefined,
  alertTime: undefined,
  alertStatus: undefined,
  handlerId: undefined,
  handlerName: undefined,
  handleTime: undefined,
  handleMethod: undefined,
  handleResult: undefined,
  isAutoAlert: undefined,
  notificationSent: undefined,
  notificationTime: undefined,
  recipientIds: [],
  remark: undefined,
})
const formRules = reactive({
  alertCode: [{ required: true, message: '预警编号不能为空', trigger: 'blur' }],
  alertType: [{ required: true, message: '预警类型不能为空', trigger: 'change' }],
  alertLevel: [{ required: true, message: '预警级别不能为空', trigger: 'blur' }],
  qualityItemId: [{ required: true, message: '检测项目ID不能为空', trigger: 'blur' }],
  productType: [{ required: true, message: '产品类型不能为空', trigger: 'change' }],
  alertMessage: [{ required: true, message: '预警信息不能为空', trigger: 'blur' }],
  alertTime: [{ required: true, message: '预警时间不能为空', trigger: 'blur' }],
  alertStatus: [{ required: true, message: '预警状态不能为空', trigger: 'blur' }],
  isAutoAlert: [{ required: true, message: '是否自动预警：0手动 1自动不能为空', trigger: 'blur' }],
  notificationSent: [{ required: true, message: '是否已发送通知：0否 1是不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 加载相关数据列表
  await Promise.all([loadQualityItemList(), loadInspectionList(), loadUserList()])
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      const data = await QualityAlertApi.getQualityAlert(id)
      formData.value = data
      // 处理接收人列表
      if (data.recipients) {
        formData.value.recipientIds = data.recipients.split(',').map(id => parseInt(id.trim())).filter(id => !isNaN(id))
      } else {
        formData.value.recipientIds = []
      }
    } finally {
      formLoading.value = false
    }
  }
}

/** 加载检测项目列表 */
const loadQualityItemList = async () => {
  try {
    const data = await QualityItemApi.getQualityItemPage({ pageNo: 1, pageSize: 100 })
    qualityItemList.value = data.list || []
  } catch (error) {
    console.error('加载检测项目列表失败:', error)
  }
}

/** 加载检测记录列表 */
const loadInspectionList = async () => {
  try {
    const data = await QualityInspectionApi.getQualityInspectionPage({ pageNo: 1, pageSize: 100 })
    inspectionList.value = data.list || []
  } catch (error) {
    console.error('加载检测记录列表失败:', error)
  }
}

/** 加载用户列表 */
const loadUserList = async () => {
  try {
    userList.value = await getSimpleUserList()
  } catch (error) {
    console.error('加载用户列表失败:', error)
  }
}

/** 处理人选择变化 */
const handleHandlerChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.handlerName = user.nickname
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
    const data = { ...formData.value } as any
    // 处理接收人列表
    if (data.recipientIds && data.recipientIds.length > 0) {
      data.recipients = data.recipientIds.join(',')
    } else {
      data.recipients = ''
    }
    // 删除recipientIds字段，因为后端不需要
    delete data.recipientIds
    
    if (formType.value === 'create') {
      await QualityAlertApi.createQualityAlert(data)
      message.success(t('common.createSuccess'))
    } else {
      await QualityAlertApi.updateQualityAlert(data)
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
    alertType: undefined,
    alertLevel: undefined,
    qualityItemId: undefined,
    inspectionId: undefined,
    productType: undefined,
    measuredValue: undefined,
    standardValue: undefined,
    thresholdValue: undefined,
    deviation: undefined,
    alertMessage: undefined,
    alertTime: undefined,
    alertStatus: undefined,
    handlerId: undefined,
    handlerName: undefined,
    handleTime: undefined,
    handleMethod: undefined,
    handleResult: undefined,
    isAutoAlert: undefined,
    notificationSent: undefined,
    notificationTime: undefined,
    recipientIds: [],
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>