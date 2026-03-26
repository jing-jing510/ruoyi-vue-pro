<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="检测项目编码" prop="itemCode">
        <el-input v-model="formData.itemCode" placeholder="请输入检测项目编码" />
      </el-form-item>
      <el-form-item label="检测项目名称" prop="itemName">
        <el-input v-model="formData.itemName" placeholder="请输入检测项目名称" />
      </el-form-item>
      <el-form-item label="检测类型" prop="itemType">
        <el-select v-model="formData.itemType" placeholder="请选择检测类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_DETECTION_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="计量单位" prop="unit">
        <el-input v-model="formData.unit" placeholder="请输入计量单位" />
      </el-form-item>
      <el-form-item label="检测方法" prop="detectionMethod">
        <el-input v-model="formData.detectionMethod" placeholder="请输入检测方法" />
      </el-form-item>
      <el-form-item label="所需设备" prop="equipmentRequired">
        <el-input v-model="formData.equipmentRequired" placeholder="请输入所需设备" />
      </el-form-item>
      <el-form-item label="标准值" prop="standardValue">
        <el-input v-model="formData.standardValue" placeholder="请输入标准值" />
      </el-form-item>
      <el-form-item label="最大允许值" prop="maxValue">
        <el-input v-model="formData.maxValue" placeholder="请输入最大允许值" />
      </el-form-item>
      <el-form-item label="最小允许值" prop="minValue">
        <el-input v-model="formData.minValue" placeholder="请输入最小允许值" />
      </el-form-item>
      <el-form-item label="预警上限" prop="warningUpperLimit">
        <el-input v-model="formData.warningUpperLimit" placeholder="请输入预警上限" />
      </el-form-item>
      <el-form-item label="预警下限" prop="warningLowerLimit">
        <el-input v-model="formData.warningLowerLimit" placeholder="请输入预警下限" />
      </el-form-item>
      <el-form-item label="精度位数" prop="precisionDigits">
        <el-input v-model="formData.precisionDigits" placeholder="请输入精度位数" />
      </el-form-item>
      <el-form-item label="显示顺序" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入显示顺序" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_INSPECTION_STATUS)"
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
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { QualityItemApi, QualityItem } from '@/api/coal/qualityitem'

/** 质量检测项目 表单 */
defineOptions({ name: 'QualityItemForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  itemCode: undefined,
  itemName: undefined,
  itemType: undefined,
  unit: undefined,
  detectionMethod: undefined,
  equipmentRequired: undefined,
  standardValue: undefined,
  maxValue: undefined,
  minValue: undefined,
  warningUpperLimit: undefined,
  warningLowerLimit: undefined,
  precisionDigits: undefined,
  sort: undefined,
  status: undefined,
  remark: undefined,
})
const formRules = reactive({
  itemCode: [{ required: true, message: '检测项目编码不能为空', trigger: 'blur' }],
  itemName: [{ required: true, message: '检测项目名称不能为空', trigger: 'blur' }],
  itemType: [{ required: true, message: '检测类型不能为空', trigger: 'change' }],
  unit: [{ required: true, message: '计量单位不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '显示顺序不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
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
      formData.value = await QualityItemApi.getQualityItem(id)
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
    const data = formData.value as unknown as QualityItem
    if (formType.value === 'create') {
      await QualityItemApi.createQualityItem(data)
      message.success(t('common.createSuccess'))
    } else {
      await QualityItemApi.updateQualityItem(data)
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
    itemCode: undefined,
    itemName: undefined,
    itemType: undefined,
    unit: undefined,
    detectionMethod: undefined,
    equipmentRequired: undefined,
    standardValue: undefined,
    maxValue: undefined,
    minValue: undefined,
    warningUpperLimit: undefined,
    warningLowerLimit: undefined,
    precisionDigits: undefined,
    sort: undefined,
    status: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>