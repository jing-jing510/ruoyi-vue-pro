<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="检测编号" prop="inspectionCode">
        <el-input v-model="formData.inspectionCode" placeholder="请输入检测编号" />
      </el-form-item>
      <el-form-item label="检测时间" prop="inspectionDate">
        <el-date-picker
          v-model="formData.inspectionDate"
          type="date"
          value-format="x"
          placeholder="选择检测时间"
        />
      </el-form-item>
      <el-form-item label="班次ID" prop="shiftId">
        <el-input v-model="formData.shiftId" placeholder="请输入班次ID" />
      </el-form-item>
      <el-form-item label="检测人员" prop="inspectorId">
        <el-select 
          v-model="formData.inspectorId" 
          placeholder="请选择检测人员"
          @change="handleInspectorChange"
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
      <el-form-item label="检测人员姓名" prop="inspectorName">
        <el-input v-model="formData.inspectorName" placeholder="检测人员姓名" readonly />
      </el-form-item>
      <el-form-item label="产品类型" prop="productType">
        <el-select v-model="formData.productType" placeholder="请选择产品类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_PRODUCT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="产品规格" prop="productSpecification">
        <el-select v-model="formData.productSpecification" placeholder="请选择产品规格">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_PRODUCT_SPEC)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="采样地点" prop="samplingLocation">
        <el-input v-model="formData.samplingLocation" placeholder="请输入采样地点" />
      </el-form-item>
      <el-form-item label="采样方法" prop="samplingMethod">
        <el-select v-model="formData.samplingMethod" placeholder="请选择采样方法">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_SAMPLING_METHOD)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="样品编号" prop="sampleCode">
        <el-input v-model="formData.sampleCode" placeholder="请输入样品编号" />
      </el-form-item>
      <el-form-item label="检测类型" prop="detectionType">
        <el-select v-model="formData.detectionType" placeholder="请选择检测类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_DETECTION_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="系统位置" prop="systemLocation">
        <el-input v-model="formData.systemLocation" placeholder="请输入系统位置" />
      </el-form-item>
      <el-form-item label="批次号" prop="batchNumber">
        <el-input v-model="formData.batchNumber" placeholder="请输入批次号" />
      </el-form-item>
      <el-form-item label="检测状态" prop="inspectionStatus">
        <el-radio-group v-model="formData.inspectionStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_INSPECTION_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审核人员" prop="reviewerId">
        <el-select 
          v-model="formData.reviewerId" 
          placeholder="请选择审核人员"
          @change="handleReviewerChange"
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
      <el-form-item label="审核人员姓名" prop="reviewerName">
        <el-input v-model="formData.reviewerName" placeholder="审核人员姓名" readonly />
      </el-form-item>
      <el-form-item label="审核时间" prop="reviewTime">
        <el-date-picker
          v-model="formData.reviewTime"
          type="date"
          value-format="x"
          placeholder="选择审核时间"
        />
      </el-form-item>
      <el-form-item label="审核状态" prop="reviewStatus">
        <el-radio-group v-model="formData.reviewStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_REVIEW_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审核备注" prop="reviewRemark">
        <el-input v-model="formData.reviewRemark" placeholder="请输入审核备注" />
      </el-form-item>
      <el-form-item label="环境温度(℃)" prop="temperature">
        <el-input v-model="formData.temperature" placeholder="请输入环境温度(℃)" />
      </el-form-item>
      <el-form-item label="环境湿度(%)" prop="humidity">
        <el-input v-model="formData.humidity" placeholder="请输入环境湿度(%)" />
      </el-form-item>
      <el-form-item label="天气情况" prop="weatherCondition">
        <el-input v-model="formData.weatherCondition" placeholder="请输入天气情况" />
      </el-form-item>
      <el-form-item label="备注信息" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注信息" />
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
import { QualityInspectionApi, QualityInspection } from '@/api/coal/qualityinspection'
import { getSimpleUserList, UserVO } from '@/api/system/user'

/** 质量检测记录 表单 */
defineOptions({ name: 'QualityInspectionForm' })

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
  inspectionCode: undefined,
  inspectionDate: undefined,
  shiftId: undefined,
  inspectorId: undefined,
  inspectorName: undefined,
  productType: undefined,
  productSpecification: undefined,
  samplingLocation: undefined,
  samplingMethod: undefined,
  sampleCode: undefined,
  detectionType: undefined,
  systemLocation: undefined,
  batchNumber: undefined,
  inspectionStatus: undefined,
  reviewerId: undefined,
  reviewerName: undefined,
  reviewTime: undefined,
  reviewStatus: undefined,
  reviewRemark: undefined,
  temperature: undefined,
  humidity: undefined,
  weatherCondition: undefined,
  remark: undefined,
})
const formRules = reactive({
  inspectionCode: [{ required: true, message: '检测编号不能为空', trigger: 'blur' }],
  inspectionDate: [{ required: true, message: '检测时间不能为空', trigger: 'blur' }],
  inspectorId: [{ required: true, message: '检测人员ID不能为空', trigger: 'blur' }],
  inspectorName: [{ required: true, message: '检测人员姓名不能为空', trigger: 'blur' }],
  productType: [{ required: true, message: '产品类型不能为空', trigger: 'change' }],
  samplingLocation: [{ required: true, message: '采样地点不能为空', trigger: 'blur' }],
  samplingMethod: [{ required: true, message: '采样方法不能为空', trigger: 'blur' }],
  detectionType: [{ required: true, message: '检测类型不能为空', trigger: 'change' }],
  inspectionStatus: [{ required: true, message: '检测状态不能为空', trigger: 'blur' }],
  reviewStatus: [{ required: true, message: '审核状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 加载用户列表
  await loadUserList()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await QualityInspectionApi.getQualityInspection(id)
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

/** 检测人员选择变化 */
const handleInspectorChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.inspectorName = user.nickname
  }
}

/** 审核人员选择变化 */
const handleReviewerChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.reviewerName = user.nickname
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
    const data = formData.value as unknown as QualityInspection
    if (formType.value === 'create') {
      await QualityInspectionApi.createQualityInspection(data)
      message.success(t('common.createSuccess'))
    } else {
      await QualityInspectionApi.updateQualityInspection(data)
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
    inspectionCode: undefined,
    inspectionDate: undefined,
    shiftId: undefined,
    inspectorId: undefined,
    inspectorName: undefined,
    productType: undefined,
    productSpecification: undefined,
    samplingLocation: undefined,
    samplingMethod: undefined,
    sampleCode: undefined,
    detectionType: undefined,
    systemLocation: undefined,
    batchNumber: undefined,
    inspectionStatus: undefined,
    reviewerId: undefined,
    reviewerName: undefined,
    reviewTime: undefined,
    reviewStatus: undefined,
    reviewRemark: undefined,
    temperature: undefined,
    humidity: undefined,
    weatherCondition: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>