<!-- 备件设备关联表单 -->
<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      v-loading="formLoading"
    >
      <el-form-item label="备件名称" prop="sparePartId">
        <el-select
          v-model="formData.sparePartId"
          placeholder="请选择备件"
          class="w-full"
        >
          <el-option
            v-for="product in sparePartList"
            :key="product.id"
            :label="product.name"
            :value="product.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联设备" prop="equipmentId">
        <el-select
          v-model="formData.equipmentId"
          placeholder="请选择设备"
          class="w-full"
        >
          <el-option
            v-for="equipment in equipmentList"
            :key="equipment.id"
            :label="equipment.equipmentName"
            :value="equipment.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否必需" prop="isRequired">
        <el-radio-group v-model="formData.isRequired">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="用量" prop="quantity">
        <el-input-number
          v-model="formData.quantity"
          :min="0"
          :precision="2"
          placeholder="请输入用量"
          class="w-full"
        />
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <el-input
          v-model="formData.unit"
          placeholder="请输入单位，如：个、套、米等"
          maxlength="10"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="formData.remark"
          type="textarea"
          :rows="3"
          placeholder="请输入备注"
          maxlength="200"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useMessage } from '@/hooks/web/useMessage'
import { ProductApi } from '@/api/erp/product/product'
import { EquipmentInfoApi } from '@/api/coal/equipmentinfo'
import { SparePartEquipmentApi, SparePartEquipmentVO } from '@/api/coal/sparepart/equipment'

/** 备件设备关联表单 */
defineOptions({ name: 'EquipmentRelationForm' })

const emit = defineEmits(['success'])
const message = useMessage()

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formLoading = ref(false)
const formType = ref('')
const formData = ref<SparePartEquipmentVO>({
  id: undefined,
  sparePartId: 0,
  equipmentId: 0,
  isRequired: 1,
  quantity: 1,
  unit: '个',
  remark: ''
})
const formRef = ref()
const formRules = reactive({
  sparePartId: [{ required: true, message: '备件名称不能为空', trigger: 'change' }],
  equipmentId: [{ required: true, message: '关联设备不能为空', trigger: 'change' }],
  isRequired: [{ required: true, message: '是否必需不能为空', trigger: 'change' }],
  quantity: [{ required: true, message: '用量不能为空', trigger: 'blur' }]
})

const sparePartList = ref<any[]>([]) // 备件列表
const equipmentList = ref<any[]>([]) // 设备列表

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = type === 'create' ? '添加备件设备关联' : '修改备件设备关联'
  formType.value = type
  resetForm()
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      const data = await SparePartEquipmentApi.get(id)
      formData.value = data
    } catch (error) {
      console.error('获取备件设备关联详情失败:', error)
    } finally {
      formLoading.value = false
    }
  }
}

/** 提交表单 */
const submitForm = async () => {
  const valid = await formRef.value.validate()
  if (!valid) return
  
  formLoading.value = true
  try {
    if (formType.value === 'create') {
      await SparePartEquipmentApi.create(formData.value)
      message.success('新增成功')
    } else {
      await SparePartEquipmentApi.update(formData.value)
      message.success('修改成功')
    }
    dialogVisible.value = false
    emit('success')
  } catch (error) {
    console.error('保存备件设备关联失败:', error)
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    sparePartId: 0,
    equipmentId: 0,
    isRequired: 1,
    quantity: 1,
    unit: '个',
    remark: ''
  }
  formRef.value?.resetFields()
}

/** 初始化数据 */
const initData = async () => {
  // 获取备件列表
  try {
    const sparePartData = await ProductApi.getProductPage({ 
      pageNo: 1, 
      pageSize: 1000,
      sparePartType: 1 // 假设1表示有备件类型
    })
    sparePartList.value = sparePartData.list || []
  } catch (error) {
    console.warn('获取备件列表失败:', error)
    sparePartList.value = []
  }
  
  // 获取设备列表
  try {
    const equipmentData = await EquipmentInfoApi.getEquipmentInfoList({})
    equipmentList.value = equipmentData || []
  } catch (error) {
    console.warn('获取设备列表失败:', error)
    equipmentList.value = []
  }
}

onMounted(() => {
  initData()
})

defineExpose({ open })
</script>
