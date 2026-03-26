<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      v-loading="formLoading"
    >
      <el-form-item label="备件" prop="sparePartId">
        <el-select
          v-model="formData.sparePartId"
          placeholder="请选择备件"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="item in sparePartList"
            :key="item.id"
            :label="item.sparePartName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备" prop="equipmentId">
        <el-select
          v-model="formData.equipmentId"
          placeholder="请选择设备"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="item in equipmentList"
            :key="item.id"
            :label="item.equipmentName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="单次使用数量" prop="usageCount">
        <el-input-number v-model="formData.usageCount" :min="1" :max="999" placeholder="请输入单次使用数量" />
      </el-form-item>
      <el-form-item label="安装位置" prop="installPosition">
        <el-input v-model="formData.installPosition" placeholder="请输入安装位置" />
      </el-form-item>
      <el-form-item label="是否必需" prop="isRequired">
        <el-radio-group v-model="formData.isRequired">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.IS_REQUIRED)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="更换难度" prop="replacementDifficulty">
        <el-select v-model="formData.replacementDifficulty" placeholder="请选择更换难度">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.REPLACEMENT_DIFFICULTY)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="更换时间(分钟)" prop="replacementTime">
        <el-input-number v-model="formData.replacementTime" :min="1" :max="9999" placeholder="请输入更换时间" />
      </el-form-item>
      <el-form-item label="所需工具" prop="toolsRequired">
        <el-input
          v-model="formData.toolsRequired"
          type="textarea"
          :rows="3"
          placeholder="请输入所需工具，多个工具用逗号分隔"
        />
      </el-form-item>
      <el-form-item label="安全要求" prop="safetyRequirements">
        <el-input
          v-model="formData.safetyRequirements"
          type="textarea"
          :rows="3"
          placeholder="请输入安全要求"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" type="textarea" placeholder="请输入备注" />
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
import * as SparePartEquipmentApi from '@/api/coal/sparepartequipment'

/** 备件设备关联 表单 */
defineOptions({ name: 'SparePartEquipmentForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  sparePartId: undefined,
  equipmentId: undefined,
  usageCount: 1,
  installPosition: undefined,
  isRequired: 1,
  replacementDifficulty: undefined,
  replacementTime: undefined,
  toolsRequired: undefined,
  safetyRequirements: undefined,
  remark: undefined
})
const formRules = reactive({
  sparePartId: [{ required: true, message: '备件不能为空', trigger: 'change' }],
  equipmentId: [{ required: true, message: '设备不能为空', trigger: 'change' }],
  usageCount: [{ required: true, message: '单次使用数量不能为空', trigger: 'blur' }],
  isRequired: [{ required: true, message: '是否必需不能为空', trigger: 'change' }],
})
const formRef = ref() // 表单 Ref

// 下拉选择数据
const sparePartList = ref([]) // 备件列表
const equipmentList = ref([]) // 设备列表

/** 获取下拉选择数据 */
const getSelectData = async () => {
  // 获取备件列表
  sparePartList.value = await SparePartEquipmentApi.getSimpleSparePartList()
  // 获取设备列表  
  equipmentList.value = await SparePartEquipmentApi.getSimpleEquipmentList()
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  
  // 获取下拉选择数据
  await getSelectData()
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await SparePartEquipmentApi.getSparePartEquipment(id)
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
    const data = formData.value as unknown as SparePartEquipmentApi.SparePartEquipmentVO
    if (formType.value === 'create') {
      await SparePartEquipmentApi.createSparePartEquipment(data)
      message.success(t('common.createSuccess'))
    } else {
      await SparePartEquipmentApi.updateSparePartEquipment(data)
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
    sparePartId: undefined,
    equipmentId: undefined,
    usageCount: 1,
    installPosition: undefined,
    isRequired: 1,
    replacementDifficulty: undefined,
    replacementTime: undefined,
    toolsRequired: undefined,
    safetyRequirements: undefined,
    remark: undefined
  }
  formRef.value?.resetFields()
}
</script>
