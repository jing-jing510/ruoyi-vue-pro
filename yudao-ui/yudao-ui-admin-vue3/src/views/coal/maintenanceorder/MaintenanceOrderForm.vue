<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="检修单编号" prop="orderCode">
        <el-input v-model="formData.orderCode" placeholder="请输入检修单编号" />
      </el-form-item>
      <el-form-item label="关联计划" prop="planId">
        <el-select v-model="formData.planId" placeholder="请选择关联计划" clearable filterable>
          <el-option
            v-for="plan in planList"
            :key="plan.id"
            :label="plan.planName"
            :value="plan.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联报修单" prop="repairRequestId">
        <el-select v-model="formData.repairRequestId" placeholder="请选择关联报修单" clearable filterable>
          <el-option
            v-for="repair in repairRequestList"
            :key="repair.id"
            :label="repair.requestCode"
            :value="repair.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备" prop="equipmentId">
        <el-select v-model="formData.equipmentId" placeholder="请选择设备" filterable>
          <el-option
            v-for="equipment in equipmentList"
            :key="equipment.id"
            :label="equipment.equipmentName"
            :value="equipment.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检修类型" prop="maintenanceType">
        <el-select v-model="formData.maintenanceType" placeholder="请选择检修类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检修级别" prop="maintenanceLevel">
        <el-select v-model="formData.maintenanceLevel" placeholder="请选择检修级别">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检修单状态" prop="orderStatus">
        <el-radio-group v-model="formData.orderStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_ORDER_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="优先级" prop="priorityLevel">
        <el-select v-model="formData.priorityLevel" placeholder="请选择优先级">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.PRIORITY_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="故障描述" prop="faultDescription">
        <Editor v-model="formData.faultDescription" height="150px" />
      </el-form-item>
      <el-form-item label="检修内容" prop="maintenanceContent">
        <Editor v-model="formData.maintenanceContent" height="150px" />
      </el-form-item>
      <el-form-item label="安全措施" prop="safetyMeasures">
        <el-input v-model="formData.safetyMeasures" placeholder="请输入安全措施" />
      </el-form-item>
      <el-form-item label="实际开始时间" prop="startTime">
        <el-date-picker
          v-model="formData.startTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择实际开始时间"
        />
      </el-form-item>
      <el-form-item label="实际结束时间" prop="endTime">
        <el-date-picker
          v-model="formData.endTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择实际结束时间"
        />
      </el-form-item>
      <el-form-item label="实际工期(小时)" prop="actualDuration">
        <el-input v-model="formData.actualDuration" placeholder="请输入实际工期(小时)" />
      </el-form-item>
      <el-form-item label="负责人" prop="responsiblePerson">
        <el-select v-model="formData.responsiblePerson" placeholder="请选择负责人" filterable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.nickname"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="负责班组" prop="responsibleTeam">
        <el-input v-model="formData.responsibleTeam" placeholder="请输入负责班组" />
      </el-form-item>
      <el-form-item label="参与人员" prop="participants">
        <el-input v-model="formData.participants" placeholder="请输入参与人员" />
      </el-form-item>
      <el-form-item label="作业环境" prop="workEnvironment">
        <el-input v-model="formData.workEnvironment" placeholder="请输入作业环境" />
      </el-form-item>
      <el-form-item label="天气条件" prop="weatherCondition">
        <el-input v-model="formData.weatherCondition" placeholder="请输入天气条件" />
      </el-form-item>
      <el-form-item label="完成进度(%)" prop="completionRate">
        <el-input v-model="formData.completionRate" placeholder="请输入完成进度(%)" />
      </el-form-item>
      <el-form-item label="质量评级" prop="qualityRating">
        <el-select v-model="formData.qualityRating" placeholder="请选择质量评级">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.QUALITY_RATING)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="安全评级" prop="safetyRating">
        <el-select v-model="formData.safetyRating" placeholder="请选择安全评级">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SAFETY_RATING)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检修结果" prop="maintenanceResult">
        <el-input v-model="formData.maintenanceResult" placeholder="请输入检修结果" />
      </el-form-item>
      <el-form-item label="发现问题" prop="problemsFound">
        <el-input v-model="formData.problemsFound" placeholder="请输入发现问题" />
      </el-form-item>
      <el-form-item label="改进建议" prop="improvementSuggestions">
        <el-input v-model="formData.improvementSuggestions" placeholder="请输入改进建议" />
      </el-form-item>
      <el-form-item label="下次检修日期" prop="nextMaintenanceDate">
        <el-date-picker
          v-model="formData.nextMaintenanceDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择下次检修日期"
        />
      </el-form-item>
      <el-form-item label="总费用" prop="totalCost">
        <el-input v-model="formData.totalCost" placeholder="请输入总费用" />
      </el-form-item>
      <el-form-item label="人工费用" prop="laborCost">
        <el-input v-model="formData.laborCost" placeholder="请输入人工费用" />
      </el-form-item>
      <el-form-item label="材料费用" prop="materialCost">
        <el-input v-model="formData.materialCost" placeholder="请输入材料费用" />
      </el-form-item>
      <el-form-item label="其他费用" prop="otherCost">
        <el-input v-model="formData.otherCost" placeholder="请输入其他费用" />
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
import { MaintenanceOrderApi, MaintenanceOrder } from '@/api/coal/maintenanceorder'
import { EquipmentInfoApi } from '@/api/coal/equipmentinfo'
import { MaintenancePlanApi } from '@/api/coal/maintenanceplan'
import { RepairRequestApi } from '@/api/coal/repairrequest'
import { getSimpleUserList } from '@/api/system/user'

/** 检修单 表单 */
defineOptions({ name: 'MaintenanceOrderForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改

// 设备列表、用户列表、计划列表和报修单列表
const equipmentList = ref<any[]>([])
const userList = ref<any[]>([])
const planList = ref<any[]>([])
const repairRequestList = ref<any[]>([])
const formData = ref({
  id: undefined,
  orderCode: undefined,
  planId: undefined,
  repairRequestId: undefined,
  equipmentId: undefined,
  maintenanceType: undefined,
  maintenanceLevel: undefined,
  orderStatus: undefined,
  priorityLevel: undefined,
  faultDescription: undefined,
  maintenanceContent: undefined,
  safetyMeasures: undefined,
  startTime: undefined,
  endTime: undefined,
  actualDuration: undefined,
  responsiblePerson: undefined,
  responsibleTeam: undefined,
  participants: undefined,
  workEnvironment: undefined,
  weatherCondition: undefined,
  completionRate: undefined,
  qualityRating: undefined,
  safetyRating: undefined,
  maintenanceResult: undefined,
  problemsFound: undefined,
  improvementSuggestions: undefined,
  nextMaintenanceDate: undefined,
  totalCost: undefined,
  laborCost: undefined,
  materialCost: undefined,
  otherCost: undefined,
  remark: undefined,
})
const formRules = reactive({
  orderCode: [{ required: true, message: '检修单编号不能为空', trigger: 'blur' }],
  equipmentId: [{ required: true, message: '设备不能为空', trigger: 'change' }],
  maintenanceType: [{ required: true, message: '检修类型不能为空', trigger: 'change' }],
  maintenanceLevel: [{ required: true, message: '检修级别不能为空', trigger: 'blur' }],
  orderStatus: [{ required: true, message: '检修单状态不能为空', trigger: 'blur' }],
  priorityLevel: [{ required: true, message: '优先级不能为空', trigger: 'blur' }],
  responsiblePerson: [{ required: true, message: '负责人不能为空', trigger: 'blur' }],
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

/** 获取计划列表 */
const getPlanList = async () => {
  try {
    const response = await MaintenancePlanApi.getMaintenancePlanPage({ pageNo: 1, pageSize: 100 })
    planList.value = response.list || []
  } catch (error) {
    console.error('获取计划列表失败:', error)
  }
}

/** 获取报修单列表 */
const getRepairRequestList = async () => {
  try {
    const response = await RepairRequestApi.getRepairRequestPage({ pageNo: 1, pageSize: 100 })
    repairRequestList.value = response.list || []
  } catch (error) {
    console.error('获取报修单列表失败:', error)
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
    getUserList(),
    getPlanList(),
    getRepairRequestList()
  ])
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await MaintenanceOrderApi.getMaintenanceOrder(id)
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
    const data = formData.value as unknown as MaintenanceOrder
    // 根据设备ID自动填充设备名称
    if (data.equipmentId) {
      const equipment = equipmentList.value.find(eq => eq.id === data.equipmentId)
      if (equipment) {
        data.equipmentName = equipment.equipmentName
      }
    }
    
    if (formType.value === 'create') {
      await MaintenanceOrderApi.createMaintenanceOrder(data)
      message.success(t('common.createSuccess'))
    } else {
      await MaintenanceOrderApi.updateMaintenanceOrder(data)
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
    orderCode: undefined,
    planId: undefined,
    repairRequestId: undefined,
    equipmentId: undefined,
    maintenanceType: undefined,
    maintenanceLevel: undefined,
    orderStatus: undefined,
    priorityLevel: undefined,
    faultDescription: undefined,
    maintenanceContent: undefined,
    safetyMeasures: undefined,
    startTime: undefined,
    endTime: undefined,
    actualDuration: undefined,
    responsiblePerson: undefined,
    responsibleTeam: undefined,
    participants: undefined,
    workEnvironment: undefined,
    weatherCondition: undefined,
    completionRate: undefined,
    qualityRating: undefined,
    safetyRating: undefined,
    maintenanceResult: undefined,
    problemsFound: undefined,
    improvementSuggestions: undefined,
    nextMaintenanceDate: undefined,
    totalCost: undefined,
    laborCost: undefined,
    materialCost: undefined,
    otherCost: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>