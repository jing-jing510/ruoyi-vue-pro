<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="备件编号" prop="sparePartCode">
        <el-input v-model="formData.sparePartCode" placeholder="请输入备件编号" />
      </el-form-item>
      <el-form-item label="备件名称" prop="sparePartName">
        <el-input v-model="formData.sparePartName" placeholder="请输入备件名称" />
      </el-form-item>
      <el-form-item label="备件分类ID" prop="categoryId">
        <el-input v-model="formData.categoryId" placeholder="请输入备件分类ID" />
      </el-form-item>
      <el-form-item label="规格型号" prop="specification">
        <el-input v-model="formData.specification" placeholder="请输入规格型号" />
      </el-form-item>
      <el-form-item label="品牌" prop="brand">
        <el-input v-model="formData.brand" placeholder="请输入品牌" />
      </el-form-item>
      <el-form-item label="计量单位" prop="unit">
        <el-input v-model="formData.unit" placeholder="请输入计量单位" />
      </el-form-item>
      <el-form-item label="备件类型" prop="sparePartType">
        <el-select v-model="formData.sparePartType" placeholder="请选择备件类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SPARE_PART_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="材质" prop="material">
        <el-input v-model="formData.material" placeholder="请输入材质" />
      </el-form-item>
      <el-form-item label="重量(kg)" prop="weight">
        <el-input v-model="formData.weight" placeholder="请输入重量(kg)" />
      </el-form-item>
      <el-form-item label="外形尺寸" prop="dimensions">
        <el-input v-model="formData.dimensions" placeholder="请输入外形尺寸" />
      </el-form-item>
      <el-form-item label="最低库存预警数量" prop="minStock">
        <el-input v-model="formData.minStock" placeholder="请输入最低库存预警数量" />
      </el-form-item>
      <el-form-item label="最高库存数量" prop="maxStock">
        <el-input v-model="formData.maxStock" placeholder="请输入最高库存数量" />
      </el-form-item>
      <el-form-item label="安全库存数量" prop="safetyStock">
        <el-input v-model="formData.safetyStock" placeholder="请输入安全库存数量" />
      </el-form-item>
      <el-form-item label="当前库存数量" prop="currentStock">
        <el-input v-model="formData.currentStock" placeholder="请输入当前库存数量" />
      </el-form-item>
      <el-form-item label="主要供应商" prop="supplier">
        <el-input v-model="formData.supplier" placeholder="请输入主要供应商" />
      </el-form-item>
      <el-form-item label="供应商零件号" prop="supplierPartNo">
        <el-input v-model="formData.supplierPartNo" placeholder="请输入供应商零件号" />
      </el-form-item>
      <el-form-item label="单价" prop="unitPrice">
        <el-input v-model="formData.unitPrice" placeholder="请输入单价" />
      </el-form-item>
      <el-form-item label="采购周期(天)" prop="leadTime">
        <el-input v-model="formData.leadTime" placeholder="请输入采购周期(天)" />
      </el-form-item>
      <el-form-item label="建议更换周期(天)" prop="replacementCycle">
        <el-input v-model="formData.replacementCycle" placeholder="请输入建议更换周期(天)" />
      </el-form-item>
      <el-form-item label="平均使用寿命(天)" prop="averageLifespan">
        <el-input v-model="formData.averageLifespan" placeholder="请输入平均使用寿命(天)" />
      </el-form-item>
      <el-form-item label="使用频率" prop="usageFrequency">
        <el-select v-model="formData.usageFrequency" placeholder="请选择使用频率">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.USAGE_FREQUENCY)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="存储位置" prop="storageLocation">
        <el-input v-model="formData.storageLocation" placeholder="请输入存储位置" />
      </el-form-item>
      <el-form-item label="存储条件要求" prop="storageCondition">
        <el-input v-model="formData.storageCondition" placeholder="请输入存储条件要求" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.SPARE_PART_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否关键备件" prop="isCritical">
        <el-radio-group v-model="formData.isCritical">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.IS_CRITICAL_SPARE_PART)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备件图片" prop="imageUrl">
        <UploadImg v-model="formData.imageUrl" :limit="1" />
      </el-form-item>
      <el-form-item label="说明书文件" prop="manualUrl">
        <UploadFile v-model="formData.manualUrl" :limit="1" :fileType="['pdf', 'doc', 'docx']" />
      </el-form-item>
      <el-form-item label="图纸文件" prop="drawingUrl">
        <UploadFile v-model="formData.drawingUrl" :limit="1" :fileType="['pdf', 'dwg', 'dxf', 'jpg', 'png']" />
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
import { SparePartInfoApi, SparePartInfo } from '@/api/coal/sparepartinfo'
import UploadImg from '@/components/UploadFile/src/UploadImg.vue'
import UploadFile from '@/components/UploadFile/src/UploadFile.vue'

/** 备件基础信息 表单 */
defineOptions({ name: 'SparePartInfoForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  sparePartCode: undefined,
  sparePartName: undefined,
  categoryId: undefined,
  specification: undefined,
  brand: undefined,
  unit: undefined,
  sparePartType: undefined,
  material: undefined,
  weight: undefined,
  dimensions: undefined,
  minStock: undefined,
  maxStock: undefined,
  safetyStock: undefined,
  currentStock: undefined,
  supplier: undefined,
  supplierPartNo: undefined,
  unitPrice: undefined,
  leadTime: undefined,
  replacementCycle: undefined,
  averageLifespan: undefined,
  usageFrequency: undefined,
  storageLocation: undefined,
  storageCondition: undefined,
  status: undefined,
  isCritical: undefined,
  imageUrl: '',
  manualUrl: '',
  drawingUrl: '',
  remark: undefined,
})
const formRules = reactive({
  sparePartCode: [{ required: true, message: '备件编号不能为空', trigger: 'blur' }],
  sparePartName: [{ required: true, message: '备件名称不能为空', trigger: 'blur' }],
  categoryId: [{ required: true, message: '备件分类ID不能为空', trigger: 'blur' }],
  sparePartType: [{ required: true, message: '备件类型不能为空', trigger: 'change' }],
  minStock: [{ required: true, message: '最低库存预警数量不能为空', trigger: 'blur' }],
  currentStock: [{ required: true, message: '当前库存数量不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
  isCritical: [{ required: true, message: '是否关键备件不能为空', trigger: 'blur' }],
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
      formData.value = await SparePartInfoApi.getSparePartInfo(id)
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
    const data = formData.value as unknown as SparePartInfo
    if (formType.value === 'create') {
      await SparePartInfoApi.createSparePartInfo(data)
      message.success(t('common.createSuccess'))
    } else {
      await SparePartInfoApi.updateSparePartInfo(data)
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
    sparePartCode: undefined,
    sparePartName: undefined,
    categoryId: undefined,
    specification: undefined,
    brand: undefined,
    unit: undefined,
    sparePartType: undefined,
    material: undefined,
    weight: undefined,
    dimensions: undefined,
    minStock: undefined,
    maxStock: undefined,
    safetyStock: undefined,
    currentStock: undefined,
    supplier: undefined,
    supplierPartNo: undefined,
    unitPrice: undefined,
    leadTime: undefined,
    replacementCycle: undefined,
    averageLifespan: undefined,
    usageFrequency: undefined,
    storageLocation: undefined,
    storageCondition: undefined,
    status: undefined,
    isCritical: undefined,
    imageUrl: '',
    manualUrl: '',
    drawingUrl: '',
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>