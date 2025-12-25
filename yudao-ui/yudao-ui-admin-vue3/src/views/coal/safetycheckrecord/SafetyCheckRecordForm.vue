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
      <el-form-item label="计划ID" prop="planId">
        <el-input v-model="formData.planId" placeholder="请输入计划ID" />
      </el-form-item>
      <el-form-item label="检查类型" prop="checkType">
        <el-select v-model="formData.checkType" placeholder="请选择检查类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_CHECK_TYPE)"
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
      <el-form-item label="检查日期" prop="checkDate">
        <el-date-picker
          v-model="formData.checkDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择检查日期"
        />
      </el-form-item>
      <el-form-item label="检查时间" prop="checkTime">
        <el-date-picker
          v-model="formData.checkTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择检查时间"
        />
      </el-form-item>
      <el-form-item label="检查人" prop="checkerId">
        <el-select v-model="formData.checkerId" placeholder="请选择检查人" clearable @change="handleCheckerChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="检查人姓名" prop="checkerName">
        <el-input v-model="formData.checkerName" placeholder="检查人姓名" readonly />
      </el-form-item>
      <el-form-item label="检查区域" prop="checkArea">
        <el-input v-model="formData.checkArea" placeholder="请输入检查区域" />
      </el-form-item>
      <el-form-item label="天气情况" prop="weatherCondition">
        <el-input v-model="formData.weatherCondition" placeholder="请输入天气情况" />
      </el-form-item>
      <el-form-item label="温度(℃)" prop="temperature">
        <el-input v-model="formData.temperature" placeholder="请输入温度(℃)" />
      </el-form-item>
      <el-form-item label="湿度(%)" prop="humidity">
        <el-input v-model="formData.humidity" placeholder="请输入湿度(%)" />
      </el-form-item>
      <el-form-item label="检查总结" prop="checkSummary">
        <el-input v-model="formData.checkSummary" placeholder="请输入检查总结" />
      </el-form-item>
      <el-form-item label="检查项目总数" prop="totalItems">
        <el-input-number v-model="formData.totalItems" placeholder="请输入检查项目总数" :min="0" :precision="0" style="width: 100%" />
      </el-form-item>
      <el-form-item label="合格项目数" prop="qualifiedItems">
        <el-input-number v-model="formData.qualifiedItems" placeholder="请输入合格项目数" :min="0" :precision="0" style="width: 100%" />
      </el-form-item>
      <el-form-item label="不合格项目数" prop="unqualifiedItems">
        <el-input-number v-model="formData.unqualifiedItems" placeholder="请输入不合格项目数" :min="0" :precision="0" style="width: 100%" />
      </el-form-item>
      <el-form-item label="合格率(%)" prop="qualifiedRate">
        <el-input v-model="formData.qualifiedRate" placeholder="请输入合格率(%)" />
      </el-form-item>
      <el-form-item label="记录状态" prop="recordStatus">
        <el-radio-group v-model="formData.recordStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_RECORD_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="提交时间" prop="submitTime">
        <el-date-picker
          v-model="formData.submitTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择提交时间"
        />
      </el-form-item>
      <el-form-item label="审核人" prop="auditorId">
        <el-select v-model="formData.auditorId" placeholder="请选择审核人" clearable @change="handleAuditorChange">
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审核人姓名" prop="auditorName">
        <el-input v-model="formData.auditorName" placeholder="审核人姓名" readonly />
      </el-form-item>
      <el-form-item label="审核时间" prop="auditTime">
        <el-date-picker
          v-model="formData.auditTime"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择审核时间"
        />
      </el-form-item>
      <el-form-item label="审核备注" prop="auditRemark">
        <el-input v-model="formData.auditRemark" placeholder="请输入审核备注" />
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
import { SafetyCheckRecordApi, SafetyCheckRecord } from '@/api/coal/safetycheckrecord'
import { SafetyCheckCategoryApi } from '@/api/coal/safetycheckcategory'
import { SafetyCheckItemApi } from '@/api/coal/safetycheckrecord'
import { getSimpleUserList } from '@/api/system/user'

/** 安全检查记录 表单 */
defineOptions({ name: 'SafetyCheckRecordForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  recordCode: undefined,
  planId: undefined,
  checkType: undefined,
  checkCategoryId: undefined,
  checkDate: undefined,
  checkTime: undefined,
  checkerId: undefined,
  checkerName: undefined,
  checkArea: undefined,
  weatherCondition: undefined,
  temperature: undefined,
  humidity: undefined,
  checkSummary: undefined,
  totalItems: 0,
  qualifiedItems: 0,
  unqualifiedItems: 0,
  qualifiedRate: undefined,
  recordStatus: undefined,
  submitTime: undefined,
  auditorId: undefined,
  auditorName: undefined,
  auditTime: undefined,
  auditRemark: undefined,
  remark: undefined,
})
const formRules = reactive({
  recordCode: [{ required: true, message: '记录编号不能为空', trigger: 'blur' }],
  checkType: [{ required: true, message: '检查类型不能为空', trigger: 'change' }],
  checkCategoryId: [{ required: true, message: '检查分类ID不能为空', trigger: 'blur' }],
  checkDate: [{ required: true, message: '检查日期不能为空', trigger: 'blur' }],
  checkTime: [{ required: true, message: '检查时间不能为空', trigger: 'blur' }],
  checkerId: [{ required: true, message: '检查人ID不能为空', trigger: 'blur' }],
  checkerName: [{ required: true, message: '检查人姓名不能为空', trigger: 'blur' }],
  checkArea: [{ required: true, message: '检查区域不能为空', trigger: 'blur' }],
  totalItems: [{ required: true, message: '检查项目总数不能为空', trigger: 'blur' }],
  qualifiedItems: [{ required: true, message: '合格项目数不能为空', trigger: 'blur' }],
  unqualifiedItems: [{ required: true, message: '不合格项目数不能为空', trigger: 'blur' }],
  recordStatus: [{ required: true, message: '记录状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const safetyCheckCategoryList = ref([]) // 安全检查分类列表
const userList = ref([]) // 用户列表

/** 检查人选择变化处理 */
const handleCheckerChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.checkerName = user.nickname
  } else {
    formData.value.checkerName = ''
  }
}

/** 审核人选择变化处理 */
const handleAuditorChange = (userId: number) => {
  const user = userList.value.find(u => u.id === userId)
  if (user) {
    formData.value.auditorName = user.nickname
  } else {
    formData.value.auditorName = ''
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

/** 计算检查项目统计 */
const calculateCheckItemStatistics = async (recordId: number) => {
  try {
    const items = await SafetyCheckItemApi.getSafetyCheckItemListByRecordId(recordId)
    const totalItems = items.length
    const qualifiedItems = items.filter(item => item.checkResult === 1).length
    const unqualifiedItems = items.filter(item => item.checkResult === 2).length
    const qualifiedRate = totalItems > 0 ? Math.round((qualifiedItems / totalItems) * 100 * 100) / 100 : 0
    
    formData.value.totalItems = totalItems
    formData.value.qualifiedItems = qualifiedItems
    formData.value.unqualifiedItems = unqualifiedItems
    formData.value.qualifiedRate = qualifiedRate
  } catch (error) {
    console.error('计算检查项目统计失败:', error)
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
      formData.value = await SafetyCheckRecordApi.getSafetyCheckRecord(id)
      // 计算检查项目统计
      await calculateCheckItemStatistics(id)
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
    const data = formData.value as unknown as SafetyCheckRecord
    if (formType.value === 'create') {
      await SafetyCheckRecordApi.createSafetyCheckRecord(data)
      message.success(t('common.createSuccess'))
    } else {
      await SafetyCheckRecordApi.updateSafetyCheckRecord(data)
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
    planId: undefined,
    checkType: undefined,
    checkCategoryId: undefined,
    checkDate: undefined,
    checkTime: undefined,
    checkerId: undefined,
    checkerName: undefined,
    checkArea: undefined,
    weatherCondition: undefined,
    temperature: undefined,
    humidity: undefined,
    checkSummary: undefined,
    totalItems: 0,
    qualifiedItems: 0,
    unqualifiedItems: 0,
    qualifiedRate: undefined,
    recordStatus: undefined,
    submitTime: undefined,
    auditorId: undefined,
    auditorName: undefined,
    auditTime: undefined,
    auditRemark: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>