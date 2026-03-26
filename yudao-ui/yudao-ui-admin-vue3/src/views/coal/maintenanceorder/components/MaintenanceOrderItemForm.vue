<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
       <el-form-item label="项目名称" prop="itemName">
        <el-input v-model="formData.itemName" placeholder="请输入项目名称" />
      </el-form-item>
      <el-form-item label="项目类型" prop="itemType">
        <el-select v-model="formData.itemType" placeholder="请选择项目类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_ITEM_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="项目描述" prop="itemDescription">
        <Editor v-model="formData.itemDescription" height="150px" />
      </el-form-item>
      <el-form-item label="作业标准" prop="workStandard">
        <el-input v-model="formData.workStandard" placeholder="请输入作业标准" />
      </el-form-item>
      <el-form-item label="安全要求" prop="safetyRequirements">
        <el-input v-model="formData.safetyRequirements" placeholder="请输入安全要求" />
      </el-form-item>
      <el-form-item label="所需工具" prop="requiredTools">
        <el-input v-model="formData.requiredTools" placeholder="请输入所需工具" />
      </el-form-item>
      <el-form-item label="所需材料" prop="requiredMaterials">
        <el-input v-model="formData.requiredMaterials" placeholder="请输入所需材料" />
      </el-form-item>
      <el-form-item label="预计工时(小时)" prop="estimatedDuration">
        <el-input v-model="formData.estimatedDuration" placeholder="请输入预计工时(小时)" />
      </el-form-item>
      <el-form-item label="实际工时(小时)" prop="actualDuration">
        <el-input v-model="formData.actualDuration" placeholder="请输入实际工时(小时)" />
      </el-form-item>
      <el-form-item label="项目状态" prop="itemStatus">
        <el-radio-group v-model="formData.itemStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.MAINTENANCE_ITEM_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="完成时间" prop="completionTime">
        <el-date-picker
          v-model="formData.completionTime"
          type="date"
          value-format="x"
          placeholder="选择完成时间"
        />
      </el-form-item>
      <el-form-item label="完成质量" prop="completionQuality">
        <el-input v-model="formData.completionQuality" placeholder="请输入完成质量" />
      </el-form-item>
      <el-form-item label="完成说明" prop="completionNotes">
        <el-input v-model="formData.completionNotes" placeholder="请输入完成说明" />
      </el-form-item>
      <el-form-item label="检查人" prop="inspector">
        <el-input v-model="formData.inspector" placeholder="请输入检查人" />
      </el-form-item>
      <el-form-item label="检查时间" prop="inspectionTime">
        <el-date-picker
          v-model="formData.inspectionTime"
          type="date"
          value-format="x"
          placeholder="选择检查时间"
        />
      </el-form-item>
      <el-form-item label="检查结果" prop="inspectionResult">
        <el-input v-model="formData.inspectionResult" placeholder="请输入检查结果" />
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
import { MaintenanceOrderItemApi, MaintenanceOrderItem } from '@/api/coal/maintenanceorder'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  orderId: undefined,
  itemName: undefined,
  itemType: undefined,
  itemDescription: undefined,
  workStandard: undefined,
  safetyRequirements: undefined,
  requiredTools: undefined,
  requiredMaterials: undefined,
  estimatedDuration: undefined,
  actualDuration: undefined,
  itemStatus: undefined,
  completionTime: undefined,
  completionQuality: undefined,
  completionNotes: undefined,
  inspector: undefined,
  inspectionTime: undefined,
  inspectionResult: undefined,
  remark: undefined,
})
const formRules = reactive({
  orderId: [{ required: true, message: '检修单ID不能为空', trigger: 'blur' }],
  itemName: [{ required: true, message: '项目名称不能为空', trigger: 'blur' }],
  itemType: [{ required: true, message: '项目类型不能为空', trigger: 'change' }],
  itemStatus: [{ required: true, message: '项目状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number, orderId?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  formData.value.orderId = orderId  as any
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await MaintenanceOrderItemApi.getMaintenanceOrderItem(id)
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
    const data = formData.value as unknown as MaintenanceOrderItem
    if (formType.value === 'create') {
      await MaintenanceOrderItemApi.createMaintenanceOrderItem(data)
      message.success(t('common.createSuccess'))
    } else {
      await MaintenanceOrderItemApi.updateMaintenanceOrderItem(data)
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
    orderId: undefined,
    itemName: undefined,
    itemType: undefined,
    itemDescription: undefined,
    workStandard: undefined,
    safetyRequirements: undefined,
    requiredTools: undefined,
    requiredMaterials: undefined,
    estimatedDuration: undefined,
    actualDuration: undefined,
    itemStatus: undefined,
    completionTime: undefined,
    completionQuality: undefined,
    completionNotes: undefined,
    inspector: undefined,
    inspectionTime: undefined,
    inspectionResult: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>