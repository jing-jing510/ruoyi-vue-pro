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
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_PLAN_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检查分类" prop="checkCategoryId">
        <el-select v-model="formData.checkCategoryId" placeholder="请选择检查分类" clearable>
          <el-option
            v-for="category in safetyCheckCategoryList"
            :key="category.id"
            :label="category.categoryName"
            :value="category.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检查周期" prop="checkCycle">
        <el-select v-model="formData.checkCycle" placeholder="请选择检查周期">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_CHECK_CYCLE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检查频次" prop="checkFrequency">
        <el-input v-model="formData.checkFrequency" placeholder="请输入检查频次" />
      </el-form-item>
      <el-form-item label="负责人" prop="responsiblePersonId">
        <el-select v-model="formData.responsiblePersonId" placeholder="请选择负责人" clearable @change="handleResponsiblePersonChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="负责人姓名" prop="responsiblePersonName">
        <el-input v-model="formData.responsiblePersonName" placeholder="负责人姓名" readonly />
      </el-form-item>
      <el-form-item label="检查区域" prop="checkArea">
        <el-input v-model="formData.checkArea" placeholder="请输入检查区域" />
      </el-form-item>
      <el-form-item label="检查内容" prop="checkContent">
        <Editor v-model="formData.checkContent" height="150px" />
      </el-form-item>
      <el-form-item label="检查标准" prop="checkStandard">
        <el-input v-model="formData.checkStandard" placeholder="请输入检查标准" />
      </el-form-item>
      <el-form-item label="开始日期" prop="startDate">
        <el-date-picker
          v-model="formData.startDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择开始日期"
        />
      </el-form-item>
      <el-form-item label="结束日期" prop="endDate">
        <el-date-picker
          v-model="formData.endDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择结束日期"
        />
      </el-form-item>
      <el-form-item label="计划状态" prop="planStatus">
        <el-radio-group v-model="formData.planStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_PLAN_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审批状态" prop="approvalStatus">
        <el-radio-group v-model="formData.approvalStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_APPROVAL_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="审批人" prop="approverId">
        <el-select v-model="formData.approverId" placeholder="请选择审批人" clearable @change="handleApproverChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批人姓名" prop="approverName">
        <el-input v-model="formData.approverName" placeholder="审批人姓名" readonly />
      </el-form-item>
      <el-form-item label="审批时间" prop="approvalTime">
        <el-date-picker
          v-model="formData.approvalTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
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
import { SafetyCheckPlanApi, SafetyCheckPlan } from '@/api/coal/safetycheckplan'
import { SafetyCheckCategoryApi } from '@/api/coal/safetycheckcategory'
import { getSimpleUserList } from '@/api/system/user'

/** 安全检查计划 表单 */
defineOptions({ name: 'SafetyCheckPlanForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  planCode: undefined,
  planName: undefined,
  planType: undefined,
  checkCategoryId: undefined,
  checkCycle: undefined,
  checkFrequency: undefined,
  responsiblePersonId: undefined,
  responsiblePersonName: undefined,
  checkArea: undefined,
  checkContent: undefined,
  checkStandard: undefined,
  startDate: undefined,
  endDate: undefined,
  planStatus: undefined,
  approvalStatus: undefined,
  approverId: undefined,
  approverName: undefined,
  approvalTime: undefined,
  approvalRemark: undefined,
  remark: undefined,
})
const formRules = reactive({
  planCode: [{ required: true, message: '计划编号不能为空', trigger: 'blur' }],
  planName: [{ required: true, message: '计划名称不能为空', trigger: 'blur' }],
  planType: [{ required: true, message: '计划类型不能为空', trigger: 'change' }],
  checkCategoryId: [{ required: true, message: '检查分类ID不能为空', trigger: 'blur' }],
  checkCycle: [{ required: true, message: '检查周期不能为空', trigger: 'blur' }],
  checkFrequency: [{ required: true, message: '检查频次不能为空', trigger: 'blur' }],
  responsiblePersonId: [{ required: true, message: '负责人ID不能为空', trigger: 'blur' }],
  responsiblePersonName: [{ required: true, message: '负责人姓名不能为空', trigger: 'blur' }],
  startDate: [{ required: true, message: '开始日期不能为空', trigger: 'blur' }],
  endDate: [{ required: true, message: '结束日期不能为空', trigger: 'blur' }],
  planStatus: [{ required: true, message: '计划状态不能为空', trigger: 'blur' }],
  approvalStatus: [{ required: true, message: '审批状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const safetyCheckCategoryList = ref([]) // 安全检查分类列表
const userList = ref([]) // 用户列表

/** 负责人选择变化处理 */
const handleResponsiblePersonChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.responsiblePersonName = user.nickname
  } else {
    formData.value.responsiblePersonName = ''
  }
}

/** 审批人选择变化处理 */
const handleApproverChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.approverName = user.nickname
  } else {
    formData.value.approverName = ''
  }
}

/** 获取安全检查分类列表 */
const getSafetyCheckCategoryList = async () => {
  try {
    const data = await SafetyCheckCategoryApi.getSimpleSafetyCheckCategoryList()
    safetyCheckCategoryList.value = data
  } catch (error) {
    console.error('获取安全检查分类列表失败:', error)
  }
}

/** 获取用户列表 */
const getUserList = async () => {
  try {
    const data = await getSimpleUserList()
    userList.value = data
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
  
  // 加载基础数据
  await Promise.all([
    getSafetyCheckCategoryList(),
    getUserList()
  ])
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await SafetyCheckPlanApi.getSafetyCheckPlan(id)
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
    const data = formData.value as unknown as SafetyCheckPlan
    if (formType.value === 'create') {
      await SafetyCheckPlanApi.createSafetyCheckPlan(data)
      message.success(t('common.createSuccess'))
    } else {
      await SafetyCheckPlanApi.updateSafetyCheckPlan(data)
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
    checkCategoryId: undefined,
    checkCycle: undefined,
    checkFrequency: undefined,
    responsiblePersonId: undefined,
    responsiblePersonName: undefined,
    checkArea: undefined,
    checkContent: undefined,
    checkStandard: undefined,
    startDate: undefined,
    endDate: undefined,
    planStatus: undefined,
    approvalStatus: undefined,
    approverId: undefined,
    approverName: undefined,
    approvalTime: undefined,
    approvalRemark: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>