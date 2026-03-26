<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="能源类型编码" prop="typeCode">
        <el-input v-model="formData.typeCode" placeholder="请输入能源类型编码" />
      </el-form-item>
      <el-form-item label="能源类型名称" prop="typeName">
        <el-input v-model="formData.typeName" placeholder="请输入能源类型名称" />
      </el-form-item>
      <el-form-item label="计量单位" prop="unit">
        <el-input v-model="formData.unit" placeholder="请输入计量单位" />
      </el-form-item>
      <el-form-item label="单价(元/单位)" prop="unitPrice">
        <el-input v-model="formData.unitPrice" placeholder="请输入单价(元/单位)" />
      </el-form-item>
      <el-form-item label="是否实时采集" prop="isRealTime">
        <el-radio-group v-model="formData.isRealTime">
          <el-radio
            v-for="dict in getBoolDictOptions(DICT_TYPE.COAL_ENERGY_IS_REAL_TIME)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="数据来源" prop="dataSource">
        <el-input v-model="formData.dataSource" placeholder="请输入数据来源" />
      </el-form-item>
      <el-form-item label="采集间隔(分钟)" prop="collectionInterval">
        <el-input v-model="formData.collectionInterval" placeholder="请输入采集间隔(分钟)" />
      </el-form-item>
      <el-form-item label="预警阈值" prop="warningThreshold">
        <el-input v-model="formData.warningThreshold" placeholder="请输入预警阈值" />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入排序" />
      </el-form-item>
      <el-form-item label="状态：0禁用 1启用" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
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
import { getIntDictOptions, getBoolDictOptions, DICT_TYPE } from '@/utils/dict'
import { EnergyTypeApi, EnergyType } from '@/api/coal/energytype'

/** 能源类型配置 表单 */
defineOptions({ name: 'EnergyTypeForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  typeCode: undefined,
  typeName: undefined,
  unit: undefined,
  unitPrice: undefined,
  isRealTime: undefined,
  dataSource: undefined,
  collectionInterval: undefined,
  warningThreshold: undefined,
  sort: undefined,
  status: undefined,
  remark: undefined,
})
const formRules = reactive({
  typeCode: [{ required: true, message: '能源类型编码不能为空', trigger: 'blur' }],
  typeName: [{ required: true, message: '能源类型名称不能为空', trigger: 'blur' }],
  unit: [{ required: true, message: '计量单位不能为空', trigger: 'blur' }],
  isRealTime: [{ required: true, message: '是否实时采集不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '排序不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态：0禁用 1启用不能为空', trigger: 'blur' }],
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
      formData.value = await EnergyTypeApi.getEnergyType(id)
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
    const data = formData.value as unknown as EnergyType
    if (formType.value === 'create') {
      await EnergyTypeApi.createEnergyType(data)
      message.success(t('common.createSuccess'))
    } else {
      await EnergyTypeApi.updateEnergyType(data)
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
    typeCode: undefined,
    typeName: undefined,
    unit: undefined,
    unitPrice: undefined,
    isRealTime: undefined,
    dataSource: undefined,
    collectionInterval: undefined,
    warningThreshold: undefined,
    sort: undefined,
    status: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>