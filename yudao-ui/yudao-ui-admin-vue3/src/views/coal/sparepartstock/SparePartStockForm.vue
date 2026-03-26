<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="备件名称" prop="sparePartId">
        <el-select v-model="formData.sparePartId" placeholder="请选择备件" filterable>
          <el-option
            v-for="sparePart in sparePartList"
            :key="sparePart.id"
            :label="sparePart.sparePartName"
            :value="sparePart.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库位置" prop="warehouseLocation">
        <el-input v-model="formData.warehouseLocation" placeholder="请输入仓库位置" />
      </el-form-item>
      <el-form-item label="库存类型" prop="stockType">
        <el-select v-model="formData.stockType" placeholder="请选择库存类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.STOCK_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="库存数量" prop="quantity">
        <el-input v-model="formData.quantity" placeholder="请输入库存数量" />
      </el-form-item>
      <el-form-item label="单位成本" prop="unitCost">
        <el-input v-model="formData.unitCost" placeholder="请输入单位成本" />
      </el-form-item>
      <el-form-item label="总成本" prop="totalCost">
        <el-input v-model="formData.totalCost" placeholder="请输入总成本" />
      </el-form-item>
      <el-form-item label="批次号" prop="batchNo">
        <el-input v-model="formData.batchNo" placeholder="请输入批次号" />
      </el-form-item>
      <el-form-item label="生产日期" prop="productionDate">
        <el-date-picker
          v-model="formData.productionDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择生产日期"
        />
      </el-form-item>
      <el-form-item label="有效期至" prop="expiryDate">
        <el-date-picker
          v-model="formData.expiryDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择有效期至"
        />
      </el-form-item>
      <el-form-item label="最后入库时间" prop="lastInDate">
        <el-date-picker
          v-model="formData.lastInDate"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择最后入库时间"
        />
      </el-form-item>
      <el-form-item label="最后出库时间" prop="lastOutDate">
        <el-date-picker
          v-model="formData.lastOutDate"
          type="datetime"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="选择最后出库时间"
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
import { SparePartStockApi, SparePartStock } from '@/api/coal/sparepartstock'
import { SparePartInfoApi, SparePartInfo } from '@/api/coal/sparepartinfo'

/** 备件库存记录 表单 */
defineOptions({ name: 'SparePartStockForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  sparePartId: undefined,
  warehouseLocation: undefined,
  stockType: undefined,
  quantity: undefined,
  unitCost: undefined,
  totalCost: undefined,
  batchNo: undefined,
  productionDate: undefined,
  expiryDate: undefined,
  lastInDate: undefined,
  lastOutDate: undefined,
  remark: undefined,
})
const formRules = reactive({
  sparePartId: [{ required: true, message: '备件名称不能为空', trigger: 'change' }],
  warehouseLocation: [{ required: true, message: '仓库位置不能为空', trigger: 'blur' }],
  stockType: [{ required: true, message: '库存类型不能为空', trigger: 'change' }],
  quantity: [{ required: true, message: '库存数量不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const sparePartList = ref<SparePartInfo[]>([]) // 备件列表

/** 获取备件列表 */
const getSparePartList = async () => {
  try {
    const data = await SparePartInfoApi.getSparePartInfoPage({ pageNo: 1, pageSize: 100 })
    sparePartList.value = data.list || []
  } catch (error) {
    console.error('获取备件列表失败:', error)
  }
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  
  // 加载备件列表
  await getSparePartList()
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await SparePartStockApi.getSparePartStock(id)
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
    const data = formData.value as unknown as SparePartStock
    if (formType.value === 'create') {
      await SparePartStockApi.createSparePartStock(data)
      message.success(t('common.createSuccess'))
    } else {
      await SparePartStockApi.updateSparePartStock(data)
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
    warehouseLocation: undefined,
    stockType: undefined,
    quantity: undefined,
    unitCost: undefined,
    totalCost: undefined,
    batchNo: undefined,
    productionDate: undefined,
    expiryDate: undefined,
    lastInDate: undefined,
    lastOutDate: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>