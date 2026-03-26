<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
       <el-form-item label="检查项目名称" prop="itemName">
        <el-input v-model="formData.itemName" placeholder="请输入检查项目名称" />
      </el-form-item>
      <el-form-item label="检查项目编码" prop="itemCode">
        <el-input v-model="formData.itemCode" placeholder="请输入检查项目编码" />
      </el-form-item>
      <el-form-item label="检查标准" prop="checkStandard">
        <el-input v-model="formData.checkStandard" placeholder="请输入检查标准" />
      </el-form-item>
      <el-form-item label="检查结果" prop="checkResult">
        <el-input v-model="formData.checkResult" placeholder="请输入检查结果" />
      </el-form-item>
      <el-form-item label="检查得分" prop="checkScore">
        <el-input v-model="formData.checkScore" placeholder="请输入检查得分" />
      </el-form-item>
      <el-form-item label="满分" prop="maxScore">
        <el-input v-model="formData.maxScore" placeholder="请输入满分" />
      </el-form-item>
      <el-form-item label="问题描述" prop="problemDescription">
        <Editor v-model="formData.problemDescription" height="150px" />
      </el-form-item>
      <el-form-item label="风险等级" prop="riskLevel">
        <el-input v-model="formData.riskLevel" placeholder="请输入风险等级" />
      </el-form-item>
      <el-form-item label="是否需要整改" prop="rectificationRequired">
        <el-radio-group v-model="formData.rectificationRequired">
          <el-radio
            v-for="dict in getBoolDictOptions(DICT_TYPE.COAL_SAFETY_RECTIFICATION_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="整改期限" prop="rectificationDeadline">
        <el-input v-model="formData.rectificationDeadline" placeholder="请输入整改期限" />
      </el-form-item>
      <el-form-item label="整改负责人ID" prop="responsiblePersonId">
        <el-input v-model="formData.responsiblePersonId" placeholder="请输入整改负责人ID" />
      </el-form-item>
      <el-form-item label="整改负责人姓名" prop="responsiblePersonName">
        <el-input v-model="formData.responsiblePersonName" placeholder="请输入整改负责人姓名" />
      </el-form-item>
      <el-form-item label="整改状态" prop="rectificationStatus">
        <el-radio-group v-model="formData.rectificationStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COAL_SAFETY_RECTIFICATION_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="整改结果" prop="rectificationResult">
        <el-input v-model="formData.rectificationResult" placeholder="请输入整改结果" />
      </el-form-item>
      <el-form-item label="整改完成时间" prop="rectificationTime">
        <el-date-picker
          v-model="formData.rectificationTime"
          type="date"
          value-format="x"
          placeholder="选择整改完成时间"
        />
      </el-form-item>
      <el-form-item label="验证人ID" prop="verifierId">
        <el-input v-model="formData.verifierId" placeholder="请输入验证人ID" />
      </el-form-item>
      <el-form-item label="验证人姓名" prop="verifierName">
        <el-input v-model="formData.verifierName" placeholder="请输入验证人姓名" />
      </el-form-item>
      <el-form-item label="验证时间" prop="verificationTime">
        <el-date-picker
          v-model="formData.verificationTime"
          type="date"
          value-format="x"
          placeholder="选择验证时间"
        />
      </el-form-item>
      <el-form-item label="验证结果" prop="verificationResult">
        <el-input v-model="formData.verificationResult" placeholder="请输入验证结果" />
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
import { SafetyCheckRecordApi, SafetyCheckItem } from '@/api/coal/safetycheckrecord'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  recordId: undefined,
  itemName: undefined,
  itemCode: undefined,
  checkStandard: undefined,
  checkResult: undefined,
  checkScore: undefined,
  maxScore: undefined,
  problemDescription: undefined,
  riskLevel: undefined,
  rectificationRequired: undefined,
  rectificationDeadline: undefined,
  responsiblePersonId: undefined,
  responsiblePersonName: undefined,
  rectificationStatus: undefined,
  rectificationResult: undefined,
  rectificationTime: undefined,
  verifierId: undefined,
  verifierName: undefined,
  verificationTime: undefined,
  verificationResult: undefined,
  remark: undefined,
})
const formRules = reactive({
  recordId: [{ required: true, message: '检查记录ID不能为空', trigger: 'blur' }],
  itemName: [{ required: true, message: '检查项目名称不能为空', trigger: 'blur' }],
  checkResult: [{ required: true, message: '检查结果不能为空', trigger: 'blur' }],
  rectificationRequired: [{ required: true, message: '是否需要整改不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number, recordId?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  formData.value.recordId = recordId  as any
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await SafetyCheckRecordApi.getSafetyCheckItem(id)
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
    const data = formData.value as unknown as SafetyCheckItem
    if (formType.value === 'create') {
      await SafetyCheckRecordApi.createSafetyCheckItem(data)
      message.success(t('common.createSuccess'))
    } else {
      await SafetyCheckRecordApi.updateSafetyCheckItem(data)
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
    recordId: undefined,
    itemName: undefined,
    itemCode: undefined,
    checkStandard: undefined,
    checkResult: undefined,
    checkScore: undefined,
    maxScore: undefined,
    problemDescription: undefined,
    riskLevel: undefined,
    rectificationRequired: undefined,
    rectificationDeadline: undefined,
    responsiblePersonId: undefined,
    responsiblePersonName: undefined,
    rectificationStatus: undefined,
    rectificationResult: undefined,
    rectificationTime: undefined,
    verifierId: undefined,
    verifierName: undefined,
    verificationTime: undefined,
    verificationResult: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>