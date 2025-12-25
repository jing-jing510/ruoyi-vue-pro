<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="标准编码" prop="standardCode">
        <el-input v-model="formData.standardCode" placeholder="请输入标准编码" />
      </el-form-item>
      <el-form-item label="标准名称" prop="standardName">
        <el-input v-model="formData.standardName" placeholder="请输入标准名称" />
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
        <el-input v-model="formData.productSpecification" placeholder="请输入产品规格" />
      </el-form-item>
      <el-form-item label="检测项目" prop="qualityItemId">
        <el-select 
          v-model="formData.qualityItemId" 
          placeholder="请选择检测项目"
          clearable
        >
          <el-option
            v-for="item in qualityItemList"
            :key="item.id"
            :label="item.itemName"
            :value="item.id"
          />
        </el-select>
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
      <el-form-item label="允许公差" prop="tolerance">
        <el-input v-model="formData.tolerance" placeholder="请输入允许公差" />
      </el-form-item>
      <el-form-item label="标准类型" prop="standardType">
        <el-select v-model="formData.standardType" placeholder="请选择标准类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_STANDARD_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标准来源（如：GB/T标准号）" prop="standardSource">
        <el-input v-model="formData.standardSource" placeholder="请输入标准来源（如：GB/T标准号）" />
      </el-form-item>
      <el-form-item label="生效日期" prop="effectiveDate">
        <el-date-picker
          v-model="formData.effectiveDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择生效日期"
        />
      </el-form-item>
      <el-form-item label="失效日期" prop="expiryDate">
        <el-date-picker
          v-model="formData.expiryDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择失效日期"
        />
      </el-form-item>
      <el-form-item label="版本号" prop="version">
        <el-input v-model="formData.version" placeholder="请输入版本号" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_QUALITY_STANDARD_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审批人" prop="approverId">
        <el-select 
          v-model="formData.approverId" 
          placeholder="请选择审批人"
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
      <el-form-item label="审批时间" prop="approveTime">
        <el-date-picker
          v-model="formData.approveTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择审批时间"
        />
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
import { QualityStandardApi, QualityStandard } from '@/api/coal/qualitystandard'
import { QualityItemApi, QualityItem } from '@/api/coal/qualityitem'
import { getSimpleUserList, UserVO } from '@/api/system/user'

/** 质量标准 表单 */
defineOptions({ name: 'QualityStandardForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

// 检测项目列表
const qualityItemList = ref<QualityItem[]>([])
// 用户列表
const userList = ref<UserVO[]>([])

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  standardCode: undefined,
  standardName: undefined,
  productType: undefined,
  productSpecification: undefined,
  qualityItemId: undefined,
  standardValue: undefined,
  maxValue: undefined,
  minValue: undefined,
  tolerance: undefined,
  standardType: undefined,
  standardSource: undefined,
  effectiveDate: undefined,
  expiryDate: undefined,
  version: undefined,
  status: undefined,
  approverId: undefined,
  approveTime: undefined,
  remark: undefined,
})
const formRules = reactive({
  standardCode: [{ required: true, message: '标准编码不能为空', trigger: 'blur' }],
  standardName: [{ required: true, message: '标准名称不能为空', trigger: 'blur' }],
  productType: [{ required: true, message: '产品类型不能为空', trigger: 'change' }],
  qualityItemId: [{ required: true, message: '检测项目ID不能为空', trigger: 'blur' }],
  standardValue: [{ required: true, message: '标准值不能为空', trigger: 'blur' }],
  standardType: [{ required: true, message: '标准类型不能为空', trigger: 'change' }],
  effectiveDate: [{ required: true, message: '生效日期不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 加载检测项目列表和用户列表
  await Promise.all([loadQualityItemList(), loadUserList()])
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await QualityStandardApi.getQualityStandard(id)
    } finally {
      formLoading.value = false
    }
  }
}

/** 加载检测项目列表 */
const loadQualityItemList = async () => {
  try {
    const data = await QualityItemApi.getQualityItemPage({ pageNo: 1, pageSize: 100 })
    qualityItemList.value = data.list || []
  } catch (error) {
    console.error('加载检测项目列表失败:', error)
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
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as QualityStandard
    if (formType.value === 'create') {
      await QualityStandardApi.createQualityStandard(data)
      message.success(t('common.createSuccess'))
    } else {
      await QualityStandardApi.updateQualityStandard(data)
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
    standardCode: undefined,
    standardName: undefined,
    productType: undefined,
    productSpecification: undefined,
    qualityItemId: undefined,
    standardValue: undefined,
    maxValue: undefined,
    minValue: undefined,
    tolerance: undefined,
    standardType: undefined,
    standardSource: undefined,
    effectiveDate: undefined,
    expiryDate: undefined,
    version: undefined,
    status: undefined,
    approverId: undefined,
    approveTime: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>