<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="统计日期" prop="statisticsDate">
        <el-date-picker
          v-model="formData.statisticsDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择统计日期"
        />
      </el-form-item>
      <el-form-item label="统计类型" prop="statisticsType">
        <el-select v-model="formData.statisticsType" placeholder="请选择统计类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_STATISTICS_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
      <el-form-item label="总消耗量" prop="totalConsumption">
        <el-input v-model="formData.totalConsumption" placeholder="请输入总消耗量" />
      </el-form-item>
      <el-form-item label="总成本(元)" prop="totalCost">
        <el-input v-model="formData.totalCost" placeholder="请输入总成本(元)" />
      </el-form-item>
      <el-form-item label="平均消耗量" prop="averageConsumption">
        <el-input v-model="formData.averageConsumption" placeholder="请输入平均消耗量" />
      </el-form-item>
      <el-form-item label="峰值消耗量" prop="peakConsumption">
        <el-input v-model="formData.peakConsumption" placeholder="请输入峰值消耗量" />
      </el-form-item>
      <el-form-item label="谷值消耗量" prop="valleyConsumption">
        <el-input v-model="formData.valleyConsumption" placeholder="请输入谷值消耗量" />
      </el-form-item>
      <el-form-item label="消耗效率(%)" prop="consumptionEfficiency">
        <el-input v-model="formData.consumptionEfficiency" placeholder="请输入消耗效率(%)" />
      </el-form-item>
      <el-form-item label="单位成本(元/单位)" prop="costPerUnit">
        <el-input v-model="formData.costPerUnit" placeholder="请输入单位成本(元/单位)" />
      </el-form-item>
      <el-form-item label="与计划对比(%)" prop="comparisonWithPlan">
        <el-input v-model="formData.comparisonWithPlan" placeholder="请输入与计划对比(%)" />
      </el-form-item>
      <el-form-item label="与上期对比(%)" prop="comparisonWithLastPeriod">
        <el-input v-model="formData.comparisonWithLastPeriod" placeholder="请输入与上期对比(%)" />
      </el-form-item>
      <el-form-item label="统计状态" prop="statisticsStatus">
        <el-radio-group v-model="formData.statisticsStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_ENERGY_STATISTICS_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
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
import { EnergyStatisticsApi, EnergyStatistics } from '@/api/coal/energystatistics'
import { EnergyTypeApi } from '@/api/coal/energytype'

/** 能源消耗统计 表单 */
defineOptions({ name: 'EnergyStatisticsForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  statisticsDate: undefined,
  statisticsType: undefined,
  energyTypeId: undefined,
  totalConsumption: undefined,
  totalCost: undefined,
  averageConsumption: undefined,
  peakConsumption: undefined,
  valleyConsumption: undefined,
  consumptionEfficiency: undefined,
  costPerUnit: undefined,
  comparisonWithPlan: undefined,
  comparisonWithLastPeriod: undefined,
  statisticsStatus: undefined,
})
const formRules = reactive({
  statisticsDate: [{ required: true, message: '统计日期不能为空', trigger: 'blur' }],
  statisticsType: [{ required: true, message: '统计类型不能为空', trigger: 'change' }],
  energyTypeId: [{ required: true, message: '能源类型ID不能为空', trigger: 'blur' }],
  totalConsumption: [{ required: true, message: '总消耗量不能为空', trigger: 'blur' }],
  totalCost: [{ required: true, message: '总成本(元)不能为空', trigger: 'blur' }],
  statisticsStatus: [{ required: true, message: '统计状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

// 能源类型列表
const energyTypeList = ref([])

// 获取能源类型列表
const getEnergyTypeList = async () => {
  try {
    const data = await EnergyTypeApi.getSimpleEnergyTypeList()
    energyTypeList.value = data
  } catch (error) {
    console.error('获取能源类型列表失败:', error)
  }
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  
  // 加载基础数据
  await getEnergyTypeList()
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await EnergyStatisticsApi.getEnergyStatistics(id)
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
    const data = formData.value as unknown as EnergyStatistics
    if (formType.value === 'create') {
      await EnergyStatisticsApi.createEnergyStatistics(data)
      message.success(t('common.createSuccess'))
    } else {
      await EnergyStatisticsApi.updateEnergyStatistics(data)
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
    statisticsDate: undefined,
    statisticsType: undefined,
    energyTypeId: undefined,
    totalConsumption: undefined,
    totalCost: undefined,
    averageConsumption: undefined,
    peakConsumption: undefined,
    valleyConsumption: undefined,
    consumptionEfficiency: undefined,
    costPerUnit: undefined,
    comparisonWithPlan: undefined,
    comparisonWithLastPeriod: undefined,
    statisticsStatus: undefined,
  }
  formRef.value?.resetFields()
}
</script>