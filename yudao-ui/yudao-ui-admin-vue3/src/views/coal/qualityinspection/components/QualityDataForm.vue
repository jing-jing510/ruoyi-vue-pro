<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
       <el-form-item label="检测项目ID" prop="qualityItemId">
        <el-input v-model="formData.qualityItemId" placeholder="请输入检测项目ID" />
      </el-form-item>
      <el-form-item label="检测项目编码" prop="qualityItemCode">
        <el-input v-model="formData.qualityItemCode" placeholder="请输入检测项目编码" />
      </el-form-item>
      <el-form-item label="检测项目名称" prop="qualityItemName">
        <el-input v-model="formData.qualityItemName" placeholder="请输入检测项目名称" />
      </el-form-item>
      <el-form-item label="检测值" prop="measuredValue">
        <el-input v-model="formData.measuredValue" placeholder="请输入检测值" />
      </el-form-item>
      <el-form-item label="计量单位" prop="unit">
        <el-input v-model="formData.unit" placeholder="请输入计量单位" />
      </el-form-item>
      <el-form-item label="标准值" prop="standardValue">
        <el-input v-model="formData.standardValue" placeholder="请输入标准值" />
      </el-form-item>
      <el-form-item label="偏差值" prop="deviation">
        <el-input v-model="formData.deviation" placeholder="请输入偏差值" />
      </el-form-item>
      <el-form-item label="偏差率(%)" prop="deviationRate">
        <el-input v-model="formData.deviationRate" placeholder="请输入偏差率(%)" />
      </el-form-item>
      <el-form-item label="是否合格" prop="isQualified">
        <el-select v-model="formData.isQualified" placeholder="请选择是否合格">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_QUALIFIED)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检测方法" prop="detectionMethod">
        <el-input v-model="formData.detectionMethod" placeholder="请输入检测方法" />
      </el-form-item>
      <el-form-item label="检测设备" prop="detectionEquipment">
        <el-input v-model="formData.detectionEquipment" placeholder="请输入检测设备" />
      </el-form-item>
      <el-form-item label="检测时间" prop="detectionTime">
        <el-date-picker
          v-model="formData.detectionTime"
          type="date"
          value-format="x"
          placeholder="选择检测时间"
        />
      </el-form-item>
      <el-form-item label="操作员" prop="operatorId">
        <el-select 
          v-model="formData.operatorId" 
          placeholder="请选择操作员"
          @change="handleOperatorChange"
          clearable
        >
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作员姓名" prop="operatorName">
        <el-input v-model="formData.operatorName" placeholder="操作员姓名" readonly />
      </el-form-item>
      <el-form-item label="复检次数" prop="retestCount">
        <el-input v-model="formData.retestCount" placeholder="请输入复检次数" />
      </el-form-item>
      <el-form-item label="是否复检" prop="isRetest">
        <el-select v-model="formData.isRetest" placeholder="请选择是否复检">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_RECHECK)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="原始检测值（复检时记录）" prop="originalValue">
        <el-input v-model="formData.originalValue" placeholder="请输入原始检测值（复检时记录）" />
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
import { QualityInspectionApi, QualityData } from '@/api/coal/qualityinspection'
import { getSimpleUserList, UserVO } from '@/api/system/user'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

// 用户列表
const userList = ref<UserVO[]>([])

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  inspectionId: undefined,
  qualityItemId: undefined,
  qualityItemCode: undefined,
  qualityItemName: undefined,
  measuredValue: undefined,
  unit: undefined,
  standardValue: undefined,
  deviation: undefined,
  deviationRate: undefined,
  isQualified: undefined,
  detectionMethod: undefined,
  detectionEquipment: undefined,
  detectionTime: undefined,
  operatorId: undefined,
  operatorName: undefined,
  retestCount: undefined,
  isRetest: undefined,
  originalValue: undefined,
  remark: undefined,
})
const formRules = reactive({
  inspectionId: [{ required: true, message: '检测记录ID不能为空', trigger: 'blur' }],
  qualityItemId: [{ required: true, message: '检测项目ID不能为空', trigger: 'blur' }],
  qualityItemCode: [{ required: true, message: '检测项目编码不能为空', trigger: 'blur' }],
  qualityItemName: [{ required: true, message: '检测项目名称不能为空', trigger: 'blur' }],
  measuredValue: [{ required: true, message: '检测值不能为空', trigger: 'blur' }],
  unit: [{ required: true, message: '计量单位不能为空', trigger: 'blur' }],
  isQualified: [{ required: true, message: '是否合格不能为空', trigger: 'blur' }],
  detectionTime: [{ required: true, message: '检测时间不能为空', trigger: 'blur' }],
  isRetest: [{ required: true, message: '是否复检不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number, inspectionId?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  formData.value.inspectionId = inspectionId  as any
  // 加载用户列表
  await loadUserList()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await QualityInspectionApi.getQualityData(id)
    } finally {
      formLoading.value = false
    }
  }
}

/** 加载用户列表 */
const loadUserList = async () => {
  try {
    userList.value = await getSimpleUserList()
  } catch (error) {
    console.error('加载用户列表失败:', error)
  }
}

/** 操作员选择变化 */
const handleOperatorChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.operatorName = user.nickname
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
    const data = formData.value as unknown as QualityData
    if (formType.value === 'create') {
      await QualityInspectionApi.createQualityData(data)
      message.success(t('common.createSuccess'))
    } else {
      await QualityInspectionApi.updateQualityData(data)
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
    inspectionId: undefined,
    qualityItemId: undefined,
    qualityItemCode: undefined,
    qualityItemName: undefined,
    measuredValue: undefined,
    unit: undefined,
    standardValue: undefined,
    deviation: undefined,
    deviationRate: undefined,
    isQualified: undefined,
    detectionMethod: undefined,
    detectionEquipment: undefined,
    detectionTime: undefined,
    operatorId: undefined,
    operatorName: undefined,
    retestCount: undefined,
    isRetest: undefined,
    originalValue: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>