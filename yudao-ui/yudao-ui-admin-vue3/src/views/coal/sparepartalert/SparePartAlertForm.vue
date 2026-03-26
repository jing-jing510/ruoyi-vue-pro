<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="备件名称" prop="sparePartId">
        <el-select v-model="formData.sparePartId" placeholder="请选择备件" filterable>
          <el-option
            v-for="sparePart in sparePartList"
            :key="sparePart.id"
            :label="sparePart.sparePartName"
            :value="sparePart.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警类型" prop="alertType">
        <el-select v-model="formData.alertType" placeholder="请选择预警类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.ALERT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警级别" prop="alertLevel">
        <el-select v-model="formData.alertLevel" placeholder="请选择预警级别">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.ALERT_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警状态" prop="alertStatus">
        <el-radio-group v-model="formData.alertStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.ALERT_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="预警标题" prop="alertTitle">
        <el-input v-model="formData.alertTitle" placeholder="请输入预警标题" />
      </el-form-item>
      <el-form-item label="预警信息" prop="alertMessage">
        <el-input v-model="formData.alertMessage" placeholder="请输入预警信息" />
      </el-form-item>
      <el-form-item label="当前库存" prop="currentStock">
        <el-input v-model="formData.currentStock" placeholder="请输入当前库存" />
      </el-form-item>
      <el-form-item label="阈值" prop="thresholdValue">
        <el-input v-model="formData.thresholdValue" placeholder="请输入阈值" />
      </el-form-item>
      <el-form-item label="关联设备" prop="equipmentId">
        <el-select v-model="formData.equipmentId" placeholder="请选择设备" filterable clearable>
          <el-option
            v-for="equipment in equipmentList"
            :key="equipment.id"
            :label="equipment.equipmentName"
            :value="equipment.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处理人" prop="handlerId">
        <el-select v-model="formData.handlerId" placeholder="请选择处理人" clearable filterable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处理时间" prop="handleTime">
        <el-date-picker
          v-model="formData.handleTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择处理时间"
        />
      </el-form-item>
      <el-form-item label="处理措施" prop="handleAction">
        <el-input v-model="formData.handleAction" placeholder="请输入处理措施" />
      </el-form-item>
      <el-form-item label="处理备注" prop="handleRemark">
        <el-input v-model="formData.handleRemark" placeholder="请输入处理备注" />
      </el-form-item>
      <el-form-item label="是否已发送通知" prop="isSent">
        <el-radio-group v-model="formData.isSent">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.IS_SENT)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="发送时间" prop="sendTime">
        <el-date-picker
          v-model="formData.sendTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择发送时间"
        />
      </el-form-item>
      <el-form-item label="接收人列表" prop="recipients">
        <el-select v-model="formData.recipientIds" multiple placeholder="请选择接收人" style="width: 100%">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
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
import { SparePartAlertApi, SparePartAlert } from '@/api/coal/sparepartalert'
import { SparePartInfoApi, SparePartInfo } from '@/api/coal/sparepartinfo'
import { EquipmentInfoApi, EquipmentInfo } from '@/api/coal/equipmentinfo'
import { getSimpleUserList, UserVO } from '@/api/system/user'

/** 备件预警记录 表单 */
defineOptions({ name: 'SparePartAlertForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  sparePartId: undefined,
  alertType: undefined,
  alertLevel: undefined,
  alertStatus: undefined,
  alertTitle: undefined,
  alertMessage: undefined,
  currentStock: undefined,
  thresholdValue: undefined,
  equipmentId: undefined,
  handlerId: undefined,
  handleTime: undefined,
  handleAction: undefined,
  handleRemark: undefined,
  isSent: undefined,
  sendTime: undefined,
  recipients: undefined,
  recipientIds: [], // 接收人ID列表
})
const formRules = reactive({
  sparePartId: [{ required: true, message: '备件名称不能为空', trigger: 'change' }],
  alertType: [{ required: true, message: '预警类型不能为空', trigger: 'change' }],
  alertLevel: [{ required: true, message: '预警级别不能为空', trigger: 'blur' }],
  alertStatus: [{ required: true, message: '预警状态不能为空', trigger: 'blur' }],
  alertTitle: [{ required: true, message: '预警标题不能为空', trigger: 'blur' }],
  alertMessage: [{ required: true, message: '预警信息不能为空', trigger: 'blur' }],
  isSent: [{ required: true, message: '是否已发送通知：1是 0否不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const userList = ref<UserVO[]>([]) // 用户列表
const sparePartList = ref<SparePartInfo[]>([]) // 备件列表
const equipmentList = ref<EquipmentInfo[]>([]) // 设备列表

/** 获取用户列表 */
const getUserList = async () => {
  const users = await getSimpleUserList()
  userList.value = users
}

/** 获取备件列表 */
const getSparePartList = async () => {
  try {
    const data = await SparePartInfoApi.getSparePartInfoPage({ pageNo: 1, pageSize: 100 })
    sparePartList.value = data.list || []
  } catch (error) {
    console.error('获取备件列表失败:', error)
  }
}

/** 获取设备列表 */
const getEquipmentList = async () => {
  try {
    const data = await EquipmentInfoApi.getEquipmentInfoPage({ pageNo: 1, pageSize: 100 })
    equipmentList.value = data.list || []
  } catch (error) {
    console.error('获取设备列表失败:', error)
  }
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  
  // 加载用户列表和备件列表
  await getUserList()
  await getSparePartList()
  await getEquipmentList()
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await SparePartAlertApi.getSparePartAlert(id)
      
      // 将接收人字符串转换为ID数组
      if (formData.value.recipients) {
        formData.value.recipientIds = formData.value.recipients.split(',').map(id => parseInt(id.trim())).filter(id => !isNaN(id))
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
    const data = formData.value as unknown as SparePartAlert
    
    // 将接收人ID列表转换为逗号分隔的字符串
    if (data.recipientIds && data.recipientIds.length > 0) {
      data.recipients = data.recipientIds.join(',')
    }
    
    if (formType.value === 'create') {
      await SparePartAlertApi.createSparePartAlert(data)
      message.success(t('common.createSuccess'))
    } else {
      await SparePartAlertApi.updateSparePartAlert(data)
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
    sparePartId: undefined,
    alertType: undefined,
    alertLevel: undefined,
    alertStatus: undefined,
    alertTitle: undefined,
    alertMessage: undefined,
    currentStock: undefined,
    thresholdValue: undefined,
    equipmentId: undefined,
    handlerId: undefined,
    handleTime: undefined,
    handleAction: undefined,
    handleRemark: undefined,
    isSent: undefined,
    sendTime: undefined,
    recipients: undefined,
    recipientIds: [], // 重置接收人ID列表
  }
  formRef.value?.resetFields()
}
</script>