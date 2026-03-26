<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="记录编号" prop="recordCode">
        <el-input v-model="formData.recordCode" placeholder="请输入记录编号" />
      </el-form-item>
      <el-form-item label="能源类型" prop="energyTypeId">
        <el-select v-model="formData.energyTypeId" placeholder="请选择能源类型" clearable>
          <el-option
            v-for="item in energyTypeList"
            :key="item.id"
            :label="item.typeName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="消耗日期" prop="consumptionDate">
        <el-date-picker
          v-model="formData.consumptionDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择消耗日期"
        />
      </el-form-item>
      <el-form-item label="班次ID" prop="shiftId">
        <el-input v-model="formData.shiftId" placeholder="请输入班次ID" />
      </el-form-item>
      <el-form-item label="消耗量" prop="consumptionValue">
        <el-input v-model="formData.consumptionValue" placeholder="请输入消耗量" />
      </el-form-item>
      <el-form-item label="单位成本(元/单位)" prop="unitCost">
        <el-input v-model="formData.unitCost" placeholder="请输入单位成本(元/单位)" />
      </el-form-item>
      <el-form-item label="总成本(元)" prop="totalCost">
        <el-input v-model="formData.totalCost" placeholder="请输入总成本(元)" />
      </el-form-item>
      <el-form-item label="数据来源" prop="dataSource">
        <el-radio-group v-model="formData.dataSource">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_DATA_SOURCE)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="采集时间" prop="collectionTime">
        <el-date-picker
          v-model="formData.collectionTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择采集时间"
        />
      </el-form-item>
      <el-form-item label="记录人" prop="recorderId">
        <el-select v-model="formData.recorderId" placeholder="请选择记录人" clearable @change="handleRecorderChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="记录人姓名" prop="recorderName">
        <el-input v-model="formData.recorderName" placeholder="记录人姓名" readonly />
      </el-form-item>
      <el-form-item label="验证状态" prop="verificationStatus">
        <el-radio-group v-model="formData.verificationStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_VERIFICATION_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="验证人" prop="verifierId">
        <el-select v-model="formData.verifierId" placeholder="请选择验证人" clearable @change="handleVerifierChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="验证时间" prop="verificationTime">
        <el-date-picker
          v-model="formData.verificationTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择验证时间"
        />
      </el-form-item>
      <el-form-item label="验证备注" prop="verificationRemark">
        <el-input v-model="formData.verificationRemark" placeholder="请输入验证备注" />
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
import { EnergyConsumptionApi, EnergyConsumption } from '@/api/coal/energyconsumption'
import { EnergyTypeApi } from '@/api/coal/energytype'
import { getSimpleUserList } from '@/api/system/user'

/** 能源消耗记录 表单 */
defineOptions({ name: 'EnergyConsumptionForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  recordCode: undefined,
  energyTypeId: undefined,
  consumptionDate: undefined,
  shiftId: undefined,
  consumptionValue: undefined,
  unitCost: undefined,
  totalCost: undefined,
  dataSource: undefined,
  collectionTime: undefined,
  recorderId: undefined,
  recorderName: undefined,
  verificationStatus: undefined,
  verifierId: undefined,
  verificationTime: undefined,
  verificationRemark: undefined,
  remark: undefined,
})
const formRules = reactive({
  recordCode: [{ required: true, message: '记录编号不能为空', trigger: 'blur' }],
  energyTypeId: [{ required: true, message: '能源类型ID不能为空', trigger: 'blur' }],
  consumptionDate: [{ required: true, message: '消耗日期不能为空', trigger: 'blur' }],
  consumptionValue: [{ required: true, message: '消耗量不能为空', trigger: 'blur' }],
  dataSource: [{ required: true, message: '数据来源不能为空', trigger: 'blur' }],
  verificationStatus: [{ required: true, message: '验证状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

// 能源类型列表
const energyTypeList = ref([])
// 用户列表
const userList = ref([])

// 获取能源类型列表
const getEnergyTypeList = async () => {
  try {
    const data = await EnergyTypeApi.getSimpleEnergyTypeList()
    energyTypeList.value = data
  } catch (error) {
    console.error('获取能源类型列表失败:', error)
  }
}

// 获取用户列表
const getUserList = async () => {
  try {
    const data = await getSimpleUserList()
    userList.value = data
  } catch (error) {
    console.error('获取用户列表失败:', error)
  }
}

// 记录人选择变化
const handleRecorderChange = (userId) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.recorderName = user.nickname
  } else {
    formData.value.recorderName = ''
  }
}

// 验证人选择变化
const handleVerifierChange = (userId) => {
  // 验证人不需要自动填充姓名，因为验证人姓名字段在表单中没有显示
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  
  // 加载基础数据
  await Promise.all([
    getEnergyTypeList(),
    getUserList()
  ])
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await EnergyConsumptionApi.getEnergyConsumption(id)
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
    const data = formData.value as unknown as EnergyConsumption
    if (formType.value === 'create') {
      await EnergyConsumptionApi.createEnergyConsumption(data)
      message.success(t('common.createSuccess'))
    } else {
      await EnergyConsumptionApi.updateEnergyConsumption(data)
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
    recordCode: undefined,
    energyTypeId: undefined,
    consumptionDate: undefined,
    shiftId: undefined,
    consumptionValue: undefined,
    unitCost: undefined,
    totalCost: undefined,
    dataSource: undefined,
    collectionTime: undefined,
    recorderId: undefined,
    recorderName: undefined,
    verificationStatus: undefined,
    verifierId: undefined,
    verificationTime: undefined,
    verificationRemark: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>