<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="事故编号" prop="accidentCode">
        <el-input v-model="formData.accidentCode" placeholder="请输入事故编号" />
      </el-form-item>
      <el-form-item label="事故类型" prop="accidentType">
        <el-select v-model="formData.accidentType" placeholder="请选择事故类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_ACCIDENT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="事故等级" prop="accidentLevel">
        <el-select v-model="formData.accidentLevel" placeholder="请选择事故等级">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_ACCIDENT_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="事故标题" prop="accidentTitle">
        <el-input v-model="formData.accidentTitle" placeholder="请输入事故标题" />
      </el-form-item>
      <el-form-item label="事故发生时间" prop="accidentTime">
        <el-date-picker
          v-model="formData.accidentTime"
          type="date"
          value-format="x"
          placeholder="选择事故发生时间"
        />
      </el-form-item>
      <el-form-item label="事故地点" prop="accidentLocation">
        <el-input v-model="formData.accidentLocation" placeholder="请输入事故地点" />
      </el-form-item>
      <el-form-item label="天气情况" prop="weatherCondition">
        <el-input v-model="formData.weatherCondition" placeholder="请输入天气情况" />
      </el-form-item>
      <el-form-item label="事故描述" prop="accidentDescription">
        <Editor v-model="formData.accidentDescription" height="150px" />
      </el-form-item>
      <el-form-item label="事故原因" prop="accidentCause">
        <el-input v-model="formData.accidentCause" placeholder="请输入事故原因" />
      </el-form-item>
      <el-form-item label="伤亡人数" prop="casualtiesCount">
        <el-input v-model="formData.casualtiesCount" placeholder="请输入伤亡人数" />
      </el-form-item>
      <el-form-item label="经济损失(元)" prop="economicLoss">
        <el-input v-model="formData.economicLoss" placeholder="请输入经济损失(元)" />
      </el-form-item>
      <el-form-item label="设备损坏情况" prop="equipmentDamage">
        <el-input v-model="formData.equipmentDamage" placeholder="请输入设备损坏情况" />
      </el-form-item>
      <el-form-item label="环境影响" prop="environmentalImpact">
        <el-input v-model="formData.environmentalImpact" placeholder="请输入环境影响" />
      </el-form-item>
      <el-form-item label="应急响应措施" prop="emergencyResponse">
        <el-input v-model="formData.emergencyResponse" placeholder="请输入应急响应措施" />
      </el-form-item>
      <el-form-item label="救援过程" prop="rescueProcess">
        <el-input v-model="formData.rescueProcess" placeholder="请输入救援过程" />
      </el-form-item>
      <el-form-item label="报告人" prop="reporterId">
        <el-select v-model="formData.reporterId" placeholder="请选择报告人" clearable @change="handleReporterChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="报告人姓名" prop="reporterName">
        <el-input v-model="formData.reporterName" placeholder="报告人姓名" readonly />
      </el-form-item>
      <el-form-item label="报告时间" prop="reportTime">
        <el-date-picker
          v-model="formData.reportTime"
          type="date"
          value-format="x"
          placeholder="选择报告时间"
        />
      </el-form-item>
      <el-form-item label="调查人" prop="investigatorId">
        <el-select v-model="formData.investigatorId" placeholder="请选择调查人" clearable @change="handleInvestigatorChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="调查人姓名" prop="investigatorName">
        <el-input v-model="formData.investigatorName" placeholder="调查人姓名" readonly />
      </el-form-item>
      <el-form-item label="调查开始时间" prop="investigationStartTime">
        <el-date-picker
          v-model="formData.investigationStartTime"
          type="date"
          value-format="x"
          placeholder="选择调查开始时间"
        />
      </el-form-item>
      <el-form-item label="调查结束时间" prop="investigationEndTime">
        <el-date-picker
          v-model="formData.investigationEndTime"
          type="date"
          value-format="x"
          placeholder="选择调查结束时间"
        />
      </el-form-item>
      <el-form-item label="调查结果" prop="investigationResult">
        <el-input v-model="formData.investigationResult" placeholder="请输入调查结果" />
      </el-form-item>
      <el-form-item label="预防措施" prop="preventiveMeasures">
        <el-input v-model="formData.preventiveMeasures" placeholder="请输入预防措施" />
      </el-form-item>
      <el-form-item label="责任人" prop="responsiblePersonId">
        <el-select v-model="formData.responsiblePersonId" placeholder="请选择责任人" clearable @change="handleResponsiblePersonChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="责任人姓名" prop="responsiblePersonName">
        <el-input v-model="formData.responsiblePersonName" placeholder="责任人姓名" readonly />
      </el-form-item>
      <el-form-item label="处罚措施" prop="punishmentMeasures">
        <el-input v-model="formData.punishmentMeasures" placeholder="请输入处罚措施" />
      </el-form-item>
      <el-form-item label="事故状态" prop="accidentStatus">
        <el-radio-group v-model="formData.accidentStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_ACCIDENT_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审批状态" prop="approvalStatus">
        <el-radio-group v-model="formData.approvalStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_APPROVAL_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审批人" prop="approverId">
        <el-select v-model="formData.approverId" placeholder="请选择审批人" clearable @change="handleApproverChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批人姓名" prop="approverName">
        <el-input v-model="formData.approverName" placeholder="审批人姓名" readonly />
      </el-form-item>
      <el-form-item label="审批时间" prop="approvalTime">
        <el-date-picker
          v-model="formData.approvalTime"
          type="date"
          value-format="x"
          placeholder="选择审批时间"
        />
      </el-form-item>
      <el-form-item label="审批备注" prop="approvalRemark">
        <el-input v-model="formData.approvalRemark" placeholder="请输入审批备注" />
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
import { SafetyAccidentApi, SafetyAccident } from '@/api/coal/safetyaccident'
import { getSimpleUserList } from '@/api/system/user'

/** 安全事故记录 表单 */
defineOptions({ name: 'SafetyAccidentForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  accidentCode: undefined,
  accidentType: undefined,
  accidentLevel: undefined,
  accidentTitle: undefined,
  accidentTime: undefined,
  accidentLocation: undefined,
  weatherCondition: undefined,
  accidentDescription: undefined,
  accidentCause: undefined,
  casualtiesCount: undefined,
  economicLoss: undefined,
  equipmentDamage: undefined,
  environmentalImpact: undefined,
  emergencyResponse: undefined,
  rescueProcess: undefined,
  reporterId: undefined,
  reporterName: undefined,
  reportTime: undefined,
  investigatorId: undefined,
  investigatorName: undefined,
  investigationStartTime: undefined,
  investigationEndTime: undefined,
  investigationResult: undefined,
  preventiveMeasures: undefined,
  responsiblePersonId: undefined,
  responsiblePersonName: undefined,
  punishmentMeasures: undefined,
  accidentStatus: undefined,
  approvalStatus: undefined,
  approverId: undefined,
  approverName: undefined,
  approvalTime: undefined,
  approvalRemark: undefined,
  remark: undefined,
})
const formRules = reactive({
  accidentCode: [{ required: true, message: '事故编号不能为空', trigger: 'blur' }],
  accidentType: [{ required: true, message: '事故类型不能为空', trigger: 'change' }],
  accidentLevel: [{ required: true, message: '事故等级不能为空', trigger: 'blur' }],
  accidentTitle: [{ required: true, message: '事故标题不能为空', trigger: 'blur' }],
  accidentTime: [{ required: true, message: '事故发生时间不能为空', trigger: 'blur' }],
  accidentLocation: [{ required: true, message: '事故地点不能为空', trigger: 'blur' }],
  accidentDescription: [{ required: true, message: '事故描述不能为空', trigger: 'blur' }],
  casualtiesCount: [{ required: true, message: '伤亡人数不能为空', trigger: 'blur' }],
  reporterId: [{ required: true, message: '报告人ID不能为空', trigger: 'blur' }],
  reporterName: [{ required: true, message: '报告人姓名不能为空', trigger: 'blur' }],
  reportTime: [{ required: true, message: '报告时间不能为空', trigger: 'blur' }],
  accidentStatus: [{ required: true, message: '事故状态不能为空', trigger: 'blur' }],
  approvalStatus: [{ required: true, message: '审批状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const userList = ref([]) // 用户列表

/** 报告人选择变化处理 */
const handleReporterChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.reporterName = user.nickname
  } else {
    formData.value.reporterName = ''
  }
}

/** 调查人选择变化处理 */
const handleInvestigatorChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.investigatorName = user.nickname
  } else {
    formData.value.investigatorName = ''
  }
}

/** 责任人选择变化处理 */
const handleResponsiblePersonChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.responsiblePersonName = user.nickname
  } else {
    formData.value.responsiblePersonName = ''
  }
}

/** 审批人选择变化处理 */
const handleApproverChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.approverName = user.nickname
  } else {
    formData.value.approverName = ''
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
      formData.value = await SafetyAccidentApi.getSafetyAccident(id)
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
    const data = formData.value as unknown as SafetyAccident
    if (formType.value === 'create') {
      await SafetyAccidentApi.createSafetyAccident(data)
      message.success(t('common.createSuccess'))
    } else {
      await SafetyAccidentApi.updateSafetyAccident(data)
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
    accidentCode: undefined,
    accidentType: undefined,
    accidentLevel: undefined,
    accidentTitle: undefined,
    accidentTime: undefined,
    accidentLocation: undefined,
    weatherCondition: undefined,
    accidentDescription: undefined,
    accidentCause: undefined,
    casualtiesCount: undefined,
    economicLoss: undefined,
    equipmentDamage: undefined,
    environmentalImpact: undefined,
    emergencyResponse: undefined,
    rescueProcess: undefined,
    reporterId: undefined,
    reporterName: undefined,
    reportTime: undefined,
    investigatorId: undefined,
    investigatorName: undefined,
    investigationStartTime: undefined,
    investigationEndTime: undefined,
    investigationResult: undefined,
    preventiveMeasures: undefined,
    responsiblePersonId: undefined,
    responsiblePersonName: undefined,
    punishmentMeasures: undefined,
    accidentStatus: undefined,
    approvalStatus: undefined,
    approverId: undefined,
    approverName: undefined,
    approvalTime: undefined,
    approvalRemark: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>