<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="报修单编号" prop="requestCode">
        <el-input v-model="formData.requestCode" placeholder="请输入报修单编号" />
      </el-form-item>
      <el-form-item label="设备" prop="equipmentId">
        <el-select v-model="formData.equipmentId" placeholder="请选择设备" filterable @change="handleEquipmentChange">
          <el-option
            v-for="equipment in equipmentList"
            :key="equipment.id"
            :label="equipment.equipmentName"
            :value="equipment.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备位置" prop="equipmentLocation">
        <el-input v-model="formData.equipmentLocation" placeholder="设备位置将自动填充" readonly />
      </el-form-item>
      <el-form-item label="故障类型" prop="faultType">
        <el-select v-model="formData.faultType" placeholder="请选择故障类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.FAULT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="故障级别" prop="faultLevel">
        <el-select v-model="formData.faultLevel" placeholder="请选择故障级别">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.FAULT_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="故障描述" prop="faultDescription">
        <Editor v-model="formData.faultDescription" height="150px" />
      </el-form-item>
      <el-form-item label="故障现象" prop="faultSymptoms">
        <el-input v-model="formData.faultSymptoms" placeholder="请输入故障现象" />
      </el-form-item>
      <el-form-item label="故障原因分析" prop="faultCause">
        <el-input v-model="formData.faultCause" placeholder="请输入故障原因分析" />
      </el-form-item>
      <el-form-item label="影响评估" prop="impactAssessment">
        <el-input v-model="formData.impactAssessment" placeholder="请输入影响评估" />
      </el-form-item>
      <el-form-item label="紧急程度" prop="urgencyLevel">
        <el-select v-model="formData.urgencyLevel" placeholder="请选择紧急程度">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.URGENCY_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="报修状态" prop="requestStatus">
        <el-radio-group v-model="formData.requestStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.REPAIR_REQUEST_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="报修人" prop="reporterId">
        <el-select v-model="formData.reporterId" placeholder="请选择报修人" filterable @change="handleReporterChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="报修人电话" prop="reporterPhone">
        <el-input v-model="formData.reporterPhone" placeholder="请输入报修人电话" />
      </el-form-item>
      <el-form-item label="报修时间" prop="reportTime">
        <el-date-picker
          v-model="formData.reportTime"
          type="date"
          value-format="x"
          placeholder="选择报修时间"
        />
      </el-form-item>
      <el-form-item label="故障照片" prop="faultPhotos">
        <UploadImg v-model="formData.faultPhotos" :limit="5" />
      </el-form-item>
      <el-form-item label="故障视频" prop="faultVideos">
        <UploadFile v-model="formData.faultVideos" :limit="3" :file-type="['mp4', 'avi', 'mov']" />
      </el-form-item>
      <el-form-item label="故障语音" prop="faultAudio">
        <UploadFile v-model="formData.faultAudio" :limit="3" :file-type="['mp3', 'wav', 'm4a']" />
      </el-form-item>
      <el-form-item label="指派处理人" prop="assignedPerson">
        <el-input v-model="formData.assignedPerson" placeholder="请输入指派处理人" />
      </el-form-item>
      <el-form-item label="指派班组" prop="assignedTeam">
        <el-input v-model="formData.assignedTeam" placeholder="请输入指派班组" />
      </el-form-item>
      <el-form-item label="派单时间" prop="assignmentTime">
        <el-date-picker
          v-model="formData.assignmentTime"
          type="date"
          value-format="x"
          placeholder="选择派单时间"
        />
      </el-form-item>
      <el-form-item label="预计修复时间" prop="expectedRepairTime">
        <el-date-picker
          v-model="formData.expectedRepairTime"
          type="date"
          value-format="x"
          placeholder="选择预计修复时间"
        />
      </el-form-item>
      <el-form-item label="实际开始时间" prop="actualStartTime">
        <el-date-picker
          v-model="formData.actualStartTime"
          type="date"
          value-format="x"
          placeholder="选择实际开始时间"
        />
      </el-form-item>
      <el-form-item label="实际结束时间" prop="actualEndTime">
        <el-date-picker
          v-model="formData.actualEndTime"
          type="date"
          value-format="x"
          placeholder="选择实际结束时间"
        />
      </el-form-item>
      <el-form-item label="修复耗时(小时)" prop="repairDuration">
        <el-input v-model="formData.repairDuration" placeholder="请输入修复耗时(小时)" />
      </el-form-item>
      <el-form-item label="修复方法" prop="repairMethod">
        <el-input v-model="formData.repairMethod" placeholder="请输入修复方法" />
      </el-form-item>
      <el-form-item label="更换部件(JSON格式)" prop="replacedParts">
        <el-input v-model="formData.replacedParts" placeholder="请输入更换部件(JSON格式)" />
      </el-form-item>
      <el-form-item label="修复费用" prop="repairCost">
        <el-input v-model="formData.repairCost" placeholder="请输入修复费用" />
      </el-form-item>
      <el-form-item label="修复质量" prop="repairQuality">
        <el-select v-model="formData.repairQuality" placeholder="请选择修复质量">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.REPAIR_QUALITY)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="测试结果" prop="testResult">
        <el-input v-model="formData.testResult" placeholder="请输入测试结果" />
      </el-form-item>
      <el-form-item label="预防措施" prop="preventionMeasures">
        <el-input v-model="formData.preventionMeasures" placeholder="请输入预防措施" />
      </el-form-item>
      <el-form-item label="满意度评价" prop="satisfactionRating">
        <el-select v-model="formData.satisfactionRating" placeholder="请选择满意度评价">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SATISFACTION_RATING)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="反馈意见" prop="feedbackNotes">
        <el-input v-model="formData.feedbackNotes" placeholder="请输入反馈意见" />
      </el-form-item>
      <el-form-item label="关闭时间" prop="closeTime">
        <el-date-picker
          v-model="formData.closeTime"
          type="date"
          value-format="x"
          placeholder="选择关闭时间"
        />
      </el-form-item>
      <el-form-item label="关闭原因" prop="closeReason">
        <el-input v-model="formData.closeReason" placeholder="请输入关闭原因" />
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
import { RepairRequestApi, RepairRequest } from '@/api/coal/repairrequest'
import { EquipmentInfoApi } from '@/api/coal/equipmentinfo'
import { getSimpleUserList } from '@/api/system/user'
import UploadImg from '@/components/UploadFile/src/UploadImg.vue'
import UploadFile from '@/components/UploadFile/src/UploadFile.vue'

/** 报修单 表单 */
defineOptions({ name: 'RepairRequestForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改

// 设备列表和用户列表
const equipmentList = ref<any[]>([])
const userList = ref<any[]>([])
const formData = ref({
  id: undefined,
  requestCode: undefined,
  equipmentId: undefined,
  equipmentName: undefined,
  equipmentLocation: undefined,
  faultType: undefined,
  faultLevel: undefined,
  faultDescription: undefined,
  faultSymptoms: undefined,
  faultCause: undefined,
  impactAssessment: undefined,
  urgencyLevel: undefined,
  requestStatus: undefined,
  reporterId: undefined,
  reporterName: undefined,
  reporterPhone: undefined,
  reportTime: undefined,
  faultPhotos: '',
  faultVideos: '',
  faultAudio: '',
  assignedPerson: undefined,
  assignedTeam: undefined,
  assignmentTime: undefined,
  expectedRepairTime: undefined,
  actualStartTime: undefined,
  actualEndTime: undefined,
  repairDuration: undefined,
  repairMethod: undefined,
  replacedParts: undefined,
  repairCost: undefined,
  repairQuality: undefined,
  testResult: undefined,
  preventionMeasures: undefined,
  satisfactionRating: undefined,
  feedbackNotes: undefined,
  closeTime: undefined,
  closeReason: undefined,
  remark: undefined,
})
const formRules = reactive({
  requestCode: [{ required: true, message: '报修单编号不能为空', trigger: 'blur' }],
  equipmentId: [{ required: true, message: '设备不能为空', trigger: 'change' }],
  faultType: [{ required: true, message: '故障类型不能为空', trigger: 'change' }],
  faultLevel: [{ required: true, message: '故障级别不能为空', trigger: 'change' }],
  faultDescription: [{ required: true, message: '故障描述不能为空', trigger: 'blur' }],
  urgencyLevel: [{ required: true, message: '紧急程度不能为空', trigger: 'change' }],
  requestStatus: [{ required: true, message: '报修状态不能为空', trigger: 'blur' }],
  reporterId: [{ required: true, message: '报修人不能为空', trigger: 'change' }],
  reportTime: [{ required: true, message: '报修时间不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 获取设备列表 */
const getEquipmentList = async () => {
  try {
    const response = await EquipmentInfoApi.getEquipmentInfoList({})
    equipmentList.value = response.data || response || []
  } catch (error) {
    console.error('获取设备列表失败:', error)
  }
}

/** 获取用户列表 */
const getUserList = async () => {
  try {
    const response = await getSimpleUserList()
    userList.value = response || []
  } catch (error) {
    console.error('获取用户列表失败:', error)
  }
}

/** 处理设备选择变化 */
const handleEquipmentChange = (equipmentId: number) => {
  const equipment = equipmentList.value.find(eq => eq.id === equipmentId)
  if (equipment) {
    formData.value.equipmentName = equipment.equipmentName
    formData.value.equipmentLocation = equipment.location || ''
  }
}

/** 处理报修人选择变化 */
const handleReporterChange = (reporterId: number) => {
  const user = userList.value.find(u => u.id === reporterId)
  if (user) {
    formData.value.reporterName = user.nickname
  }
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  
  // 获取基础数据
  await Promise.all([
    getEquipmentList(),
    getUserList()
  ])
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await RepairRequestApi.getRepairRequest(id)
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
    const data = formData.value as unknown as RepairRequest
    // 根据设备ID自动填充设备名称
    if (data.equipmentId) {
      const equipment = equipmentList.value.find(eq => eq.id === data.equipmentId)
      if (equipment) {
        data.equipmentName = equipment.equipmentName
        data.equipmentLocation = equipment.location || data.equipmentLocation
      }
    }
    // 根据报修人ID自动填充报修人姓名
    if (data.reporterId) {
      const user = userList.value.find(u => u.id === data.reporterId)
      if (user) {
        data.reporterName = user.nickname
      }
    }
    
    if (formType.value === 'create') {
      await RepairRequestApi.createRepairRequest(data)
      message.success(t('common.createSuccess'))
    } else {
      await RepairRequestApi.updateRepairRequest(data)
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
    requestCode: undefined,
    equipmentId: undefined,
    equipmentName: undefined,
    equipmentLocation: undefined,
    faultType: undefined,
    faultLevel: undefined,
    faultDescription: undefined,
    faultSymptoms: undefined,
    faultCause: undefined,
    impactAssessment: undefined,
    urgencyLevel: undefined,
    requestStatus: undefined,
    reporterId: undefined,
    reporterName: undefined,
    reporterPhone: undefined,
    reportTime: undefined,
    faultPhotos: '',
    faultVideos: '',
    faultAudio: '',
    assignedPerson: undefined,
    assignedTeam: undefined,
    assignmentTime: undefined,
    expectedRepairTime: undefined,
    actualStartTime: undefined,
    actualEndTime: undefined,
    repairDuration: undefined,
    repairMethod: undefined,
    replacedParts: undefined,
    repairCost: undefined,
    repairQuality: undefined,
    testResult: undefined,
    preventionMeasures: undefined,
    satisfactionRating: undefined,
    feedbackNotes: undefined,
    closeTime: undefined,
    closeReason: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>