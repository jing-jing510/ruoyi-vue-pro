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
      <el-form-item label="操作类型" prop="operationType">
        <el-select v-model="formData.operationType" placeholder="请选择操作类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.OPERATION_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作时间" prop="operationDate">
        <el-date-picker
          v-model="formData.operationDate"
          type="datetime"
          value-format="x"
          placeholder="选择操作时间"
        />
      </el-form-item>
      <el-form-item label="数量(正数入库，负数出库)" prop="quantity">
        <el-input v-model="formData.quantity" placeholder="请输入数量(正数入库，负数出库)" />
      </el-form-item>
      <el-form-item label="单价" prop="unitPrice">
        <el-input v-model="formData.unitPrice" placeholder="请输入单价" />
      </el-form-item>
      <el-form-item label="总金额" prop="totalAmount">
        <el-input v-model="formData.totalAmount" placeholder="请输入总金额" />
      </el-form-item>
      <el-form-item label="操作人" prop="operatorId">
        <el-select v-model="formData.operatorId" placeholder="请选择操作人" clearable filterable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库位置" prop="warehouseLocation">
        <el-input v-model="formData.warehouseLocation" placeholder="请输入仓库位置" />
      </el-form-item>
      <el-form-item label="批次号" prop="batchNo">
        <el-input v-model="formData.batchNo" placeholder="请输入批次号" />
      </el-form-item>
      <el-form-item label="关联设备ID(出库时)" prop="equipmentId">
        <el-input v-model="formData.equipmentId" placeholder="请输入关联设备ID(出库时)" />
      </el-form-item>
      <el-form-item label="关联工单ID" prop="workOrderId">
        <el-input v-model="formData.workOrderId" placeholder="请输入关联工单ID" />
      </el-form-item>
      <el-form-item label="供应商ID(入库时)" prop="supplierId">
        <el-input v-model="formData.supplierId" placeholder="请输入供应商ID(入库时)" />
      </el-form-item>
      <el-form-item label="采购单号" prop="purchaseOrderNo">
        <el-input v-model="formData.purchaseOrderNo" placeholder="请输入采购单号" />
      </el-form-item>
      <el-form-item label="审批人" prop="approverId">
        <el-select v-model="formData.approverId" placeholder="请选择审批人" clearable filterable>
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
          value-format="x"
          placeholder="选择审批时间"
        />
      </el-form-item>
      <el-form-item label="审批状态" prop="approveStatus">
        <el-radio-group v-model="formData.approveStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.APPROVE_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="操作原因" prop="operationReason">
        <el-input v-model="formData.operationReason" placeholder="请输入操作原因" />
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
import { SparePartInventoryLogApi, SparePartInventoryLog } from '@/api/coal/sparepartinventorylog'
import { SparePartInfoApi, SparePartInfo } from '@/api/coal/sparepartinfo'
import { getSimpleUserList, UserVO } from '@/api/system/user'

/** 备件出入库记录 表单 */
defineOptions({ name: 'SparePartInventoryLogForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  sparePartId: undefined,
  operationType: undefined,
  operationDate: undefined as number | undefined,
  quantity: undefined,
  unitPrice: undefined,
  totalAmount: undefined,
  operatorId: undefined,
  warehouseLocation: undefined,
  batchNo: undefined,
  equipmentId: undefined,
  workOrderId: undefined,
  supplierId: undefined,
  purchaseOrderNo: undefined,
  approverId: undefined,
  approveTime: undefined as number | undefined,
  approveStatus: undefined,
  operationReason: undefined,
  remark: undefined,
})
const formRules = reactive({
  sparePartId: [{ required: true, message: '备件名称不能为空', trigger: 'change' }],
  operationType: [{ required: true, message: '操作类型不能为空', trigger: 'change' }],
  operationDate: [{ required: true, message: '操作时间不能为空', trigger: 'blur' }],
  quantity: [{ required: true, message: '数量(正数入库，负数出库)不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const userList = ref<UserVO[]>([]) // 用户列表
const sparePartList = ref<SparePartInfo[]>([]) // 备件列表

/** 获取用户列表 */
const getUserList = async () => {
  const users = await getSimpleUserList()
  userList.value = users
}

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
  
  // 加载用户列表和备件列表
  await getUserList()
  await getSparePartList()
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await SparePartInventoryLogApi.getSparePartInventoryLog(id)
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
    const data = formData.value as unknown as SparePartInventoryLog
    if (formType.value === 'create') {
      await SparePartInventoryLogApi.createSparePartInventoryLog(data)
      message.success(t('common.createSuccess'))
    } else {
      await SparePartInventoryLogApi.updateSparePartInventoryLog(data)
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
    operationType: undefined,
    operationDate: Date.now(), // 默认当前时间戳
    quantity: undefined,
    unitPrice: undefined,
    totalAmount: undefined,
    operatorId: undefined,
    warehouseLocation: undefined,
    batchNo: undefined,
    equipmentId: undefined,
    workOrderId: undefined,
    supplierId: undefined,
    purchaseOrderNo: undefined,
    approverId: undefined,
    approveTime: undefined,
    approveStatus: undefined,
    operationReason: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>