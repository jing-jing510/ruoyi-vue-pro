<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="计划编号" prop="planCode">
        <el-input v-model="formData.planCode" placeholder="请输入计划编号" />
      </el-form-item>
      <el-form-item label="计划名称" prop="planName">
        <el-input v-model="formData.planName" placeholder="请输入计划名称" />
      </el-form-item>
      <el-form-item label="计划类型" prop="planType">
        <el-select v-model="formData.planType" placeholder="请选择计划类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_PLAN_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="计划状态" prop="planStatus">
        <el-radio-group v-model="formData.planStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_PLAN_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
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
      <el-form-item label="计划开始时间" prop="plannedStartTime">
        <el-date-picker
          v-model="formData.plannedStartTime"
          type="date"
          value-format="x"
          placeholder="选择计划开始时间"
        />
      </el-form-item>
      <el-form-item label="计划结束时间" prop="plannedEndTime">
        <el-date-picker
          v-model="formData.plannedEndTime"
          type="date"
          value-format="x"
          placeholder="选择计划结束时间"
        />
      </el-form-item>
      <el-form-item label="预计工期(小时)" prop="estimatedDuration">
        <el-input v-model="formData.estimatedDuration" placeholder="请输入预计工期(小时)" />
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
      <el-form-item label="检修内容" prop="maintenanceContent">
        <Editor v-model="formData.maintenanceContent" height="150px" />
      </el-form-item>
      <el-form-item label="安全要求" prop="safetyRequirements">
        <el-input v-model="formData.safetyRequirements" placeholder="请输入安全要求" />
      </el-form-item>
      <el-form-item label="所需工具" prop="requiredTools">
        <el-input v-model="formData.requiredTools" placeholder="请输入所需工具" />
      </el-form-item>
      <el-form-item label="所需材料" prop="requiredMaterials">
        <el-input v-model="formData.requiredMaterials" placeholder="请输入所需材料" />
      </el-form-item>
      <el-form-item label="预算费用" prop="budgetCost">
        <el-input v-model="formData.budgetCost" placeholder="请输入预算费用" />
      </el-form-item>
      <el-form-item label="实际费用" prop="actualCost">
        <el-input v-model="formData.actualCost" placeholder="请输入实际费用" />
      </el-form-item>
      <el-form-item label="审批状态" prop="approvalStatus">
        <el-radio-group v-model="formData.approvalStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.APPROVE_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审批人" prop="approverId">
        <el-select v-model="formData.approverId" placeholder="请选择审批人" filterable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
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
import { MaintenancePlanApi, MaintenancePlan } from '@/api/coal/maintenanceplan'
import { EquipmentInfoApi } from '@/api/coal/equipmentinfo'
import { getSimpleUserList } from '@/api/system/user'

/** 检修计划 表单 */
defineOptions({ name: 'MaintenancePlanForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改

// 设备列表和用户列表
const equipmentList = ref([])
const userList = ref([])
const formData = ref({
  id: undefined,
  planCode: undefined,
  planName: undefined,
  planType: undefined,
  planStatus: undefined,
  equipmentId: undefined,
  maintenanceType: undefined,
  maintenanceLevel: undefined,
  plannedStartTime: undefined,
  plannedEndTime: undefined,
  estimatedDuration: undefined,
  responsiblePerson: undefined,
  responsibleTeam: undefined,
  maintenanceContent: undefined,
  safetyRequirements: undefined,
  requiredTools: undefined,
  requiredMaterials: undefined,
  budgetCost: undefined,
  actualCost: undefined,
  approvalStatus: undefined,
  approverId: undefined,
  approvalTime: undefined,
  approvalRemark: undefined,
  remark: undefined,
})
const formRules = reactive({
  planCode: [{ required: true, message: '计划编号不能为空', trigger: 'blur' }],
  planName: [{ required: true, message: '计划名称不能为空', trigger: 'blur' }],
  planType: [{ required: true, message: '计划类型不能为空', trigger: 'change' }],
  planStatus: [{ required: true, message: '计划状态不能为空', trigger: 'blur' }],
  equipmentId: [{ required: true, message: '设备不能为空', trigger: 'change' }],
  maintenanceType: [{ required: true, message: '检修类型不能为空', trigger: 'change' }],
  maintenanceLevel: [{ required: true, message: '检修级别不能为空', trigger: 'blur' }],
  plannedStartTime: [{ required: true, message: '计划开始时间不能为空', trigger: 'blur' }],
  plannedEndTime: [{ required: true, message: '计划结束时间不能为空', trigger: 'blur' }],
  estimatedDuration: [{ required: true, message: '预计工期(小时)不能为空', trigger: 'blur' }],
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
    userList.value = response.data || response || []
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
  
  // 获取基础数据
  await Promise.all([
    getEquipmentList(),
    getUserList()
  ])
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await MaintenancePlanApi.getMaintenancePlan(id)
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
    const data = formData.value as unknown as MaintenancePlan
    if (formType.value === 'create') {
      await MaintenancePlanApi.createMaintenancePlan(data)
      message.success(t('common.createSuccess'))
    } else {
      await MaintenancePlanApi.updateMaintenancePlan(data)
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
    planCode: undefined,
    planName: undefined,
    planType: undefined,
    planStatus: undefined,
    equipmentId: undefined,
    maintenanceType: undefined,
    maintenanceLevel: undefined,
    plannedStartTime: undefined,
    plannedEndTime: undefined,
    estimatedDuration: undefined,
    responsiblePerson: undefined,
    responsibleTeam: undefined,
    maintenanceContent: undefined,
    safetyRequirements: undefined,
    requiredTools: undefined,
    requiredMaterials: undefined,
    budgetCost: undefined,
    actualCost: undefined,
    approvalStatus: undefined,
    approverId: undefined,
    approvalTime: undefined,
    approvalRemark: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>