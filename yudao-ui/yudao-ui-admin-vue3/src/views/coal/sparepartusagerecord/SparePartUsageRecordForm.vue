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
      <el-form-item label="关联设备" prop="equipmentId">
        <el-select v-model="formData.equipmentId" placeholder="请选择设备" clearable filterable>
          <el-option
            v-for="equipment in equipmentList"
            :key="equipment.id"
            :label="equipment.equipmentName"
            :value="equipment.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="使用类型" prop="usageType">
        <el-select v-model="formData.usageType" placeholder="请选择使用类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.USAGE_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="使用日期" prop="usageDate">
        <el-date-picker
          v-model="formData.usageDate"
          type="datetime"
          value-format="x"
          placeholder="选择使用日期"
        />
      </el-form-item>
      <el-form-item label="使用数量" prop="quantity">
        <el-input v-model="formData.quantity" placeholder="请输入使用数量" />
      </el-form-item>
      <el-form-item label="操作人" prop="operatorId">
        <el-select v-model="formData.operatorId" placeholder="请选择操作人" clearable filterable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="监督人" prop="supervisorId">
        <el-select v-model="formData.supervisorId" placeholder="请选择监督人" clearable filterable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="作业班组" prop="workTeam">
        <el-input v-model="formData.workTeam" placeholder="请输入作业班组" />
      </el-form-item>
      <el-form-item label="关联检修单" prop="workOrderId">
        <el-select v-model="formData.workOrderId" placeholder="请选择检修单" clearable filterable>
          <el-option
            v-for="order in maintenanceOrderList"
            :key="order.id"
            :label="order.orderCode"
            :value="order.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="故障ID(关联故障记录)" prop="faultId">
        <el-input v-model="formData.faultId" placeholder="请输入故障ID(关联故障记录)" />
      </el-form-item>
      <el-form-item label="关联维护计划" prop="maintenancePlanId">
        <el-select v-model="formData.maintenancePlanId" placeholder="请选择维护计划" clearable filterable>
          <el-option
            v-for="plan in maintenancePlanList"
            :key="plan.id"
            :label="plan.planName"
            :value="plan.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="旧件状态" prop="oldPartCondition">
        <el-select v-model="formData.oldPartCondition" placeholder="请选择旧件状态">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.OLD_PART_CONDITION)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="失效模式" prop="failureMode">
        <el-input v-model="formData.failureMode" placeholder="请输入失效模式" />
      </el-form-item>
      <el-form-item label="使用时长(天)" prop="usageDuration">
        <el-input v-model="formData.usageDuration" placeholder="请输入使用时长(天)" />
      </el-form-item>
      <el-form-item label="更换原因" prop="replacementReason">
        <el-input v-model="formData.replacementReason" placeholder="请输入更换原因" />
      </el-form-item>
      <el-form-item label="预计寿命(天)" prop="predictedLifespan">
        <el-input v-model="formData.predictedLifespan" placeholder="请输入预计寿命(天)" />
      </el-form-item>
      <el-form-item label="预计下次更换日期" prop="nextReplacementDate">
        <el-date-picker
          v-model="formData.nextReplacementDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择预计下次更换日期"
        />
      </el-form-item>
      <el-form-item label="性能评级" prop="performanceRating">
        <el-select v-model="formData.performanceRating" placeholder="请选择性能评级">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.PERFORMANCE_RATING)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="单位成本" prop="unitCost">
        <el-input v-model="formData.unitCost" placeholder="请输入单位成本" />
      </el-form-item>
      <el-form-item label="总成本" prop="totalCost">
        <el-input v-model="formData.totalCost" placeholder="请输入总成本" />
      </el-form-item>
      <el-form-item label="人工成本" prop="laborCost">
        <el-input v-model="formData.laborCost" placeholder="请输入人工成本" />
      </el-form-item>
      <el-form-item label="停机成本" prop="downtimeCost">
        <el-input v-model="formData.downtimeCost" placeholder="请输入停机成本" />
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
import { SparePartUsageRecordApi, SparePartUsageRecord } from '@/api/coal/sparepartusagerecord'
import { SparePartInfoApi, SparePartInfo } from '@/api/coal/sparepartinfo'
import { getSimpleUserList, UserVO } from '@/api/system/user'
import { MaintenanceOrderApi, MaintenanceOrder } from '@/api/coal/maintenanceorder'
import { MaintenancePlanApi, MaintenancePlan } from '@/api/coal/maintenanceplan'
import { EquipmentInfoApi, EquipmentInfo } from '@/api/coal/equipmentinfo'

/** 备件使用记录 表单 */
defineOptions({ name: 'SparePartUsageRecordForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  sparePartId: undefined,
  equipmentId: undefined,
  usageType: undefined,
  usageDate: undefined,
  quantity: undefined,
  operatorId: undefined,
  supervisorId: undefined,
  workTeam: undefined,
  workOrderId: undefined,
  faultId: undefined,
  maintenancePlanId: undefined,
  oldPartCondition: undefined,
  failureMode: undefined,
  usageDuration: undefined,
  replacementReason: undefined,
  predictedLifespan: undefined,
  nextReplacementDate: undefined,
  performanceRating: undefined,
  unitCost: undefined,
  totalCost: undefined,
  laborCost: undefined,
  downtimeCost: undefined,
  remark: undefined,
})
const formRules = reactive({
  sparePartId: [{ required: true, message: '备件ID不能为空', trigger: 'blur' }],
  equipmentId: [{ required: true, message: '设备ID不能为空', trigger: 'blur' }],
  usageType: [{ required: true, message: '使用类型不能为空', trigger: 'change' }],
  usageDate: [{ required: true, message: '使用日期不能为空', trigger: 'blur' }],
  quantity: [{ required: true, message: '使用数量不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const userList = ref<UserVO[]>([]) // 用户列表
const sparePartList = ref<SparePartInfo[]>([]) // 备件列表
const maintenanceOrderList = ref<MaintenanceOrder[]>([]) // 检修单列表
const maintenancePlanList = ref<MaintenancePlan[]>([]) // 维护计划列表
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

/** 获取检修单列表 */
const getMaintenanceOrderList = async () => {
  try {
    const data = await MaintenanceOrderApi.getMaintenanceOrderPage({ pageNo: 1, pageSize: 100 })
    maintenanceOrderList.value = data.list || []
  } catch (error) {
    console.error('获取检修单列表失败:', error)
  }
}

/** 获取维护计划列表 */
const getMaintenancePlanList = async () => {
  try {
    const data = await MaintenancePlanApi.getMaintenancePlanPage({ pageNo: 1, pageSize: 100 })
    maintenancePlanList.value = data.list || []
  } catch (error) {
    console.error('获取维护计划列表失败:', error)
  }
}

/** 获取设备列表 */
const getEquipmentList = async () => {
  try {
    const data = await EquipmentInfoApi.getEquipmentInfoList({})
    equipmentList.value = data || []
    console.log('表单页面设备列表加载完成:', equipmentList.value.length, '条记录')
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
  
  // 加载用户列表、备件列表、检修单列表、维护计划列表和设备列表
  await Promise.all([
    getUserList(),
    getSparePartList(),
    getMaintenanceOrderList(),
    getMaintenancePlanList(),
    getEquipmentList()
  ])
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await SparePartUsageRecordApi.getSparePartUsageRecord(id)
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
    const data = formData.value as unknown as SparePartUsageRecord
    if (formType.value === 'create') {
      await SparePartUsageRecordApi.createSparePartUsageRecord(data)
      message.success(t('common.createSuccess'))
    } else {
      await SparePartUsageRecordApi.updateSparePartUsageRecord(data)
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
    equipmentId: undefined,
    usageType: undefined,
    usageDate: undefined,
    quantity: undefined,
    operatorId: undefined,
    supervisorId: undefined,
    workTeam: undefined,
    workOrderId: undefined,
    faultId: undefined,
    maintenancePlanId: undefined,
    oldPartCondition: undefined,
    failureMode: undefined,
    usageDuration: undefined,
    replacementReason: undefined,
    predictedLifespan: undefined,
    nextReplacementDate: undefined,
    performanceRating: undefined,
    unitCost: undefined,
    totalCost: undefined,
    laborCost: undefined,
    downtimeCost: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>