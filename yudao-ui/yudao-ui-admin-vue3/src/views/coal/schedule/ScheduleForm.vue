<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="排班日期" prop="scheduleDate">
        <el-date-picker
          v-model="formData.scheduleDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择排班日期"
        />
      </el-form-item>
      <el-form-item label="班制名称" prop="shiftSystemId">
        <el-select v-model="formData.shiftSystemId" placeholder="请选择班制" filterable>
          <el-option
            v-for="shift in shiftSystemList"
            :key="shift.id"
            :label="shift.name"
            :value="shift.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="排班状态" prop="scheduleStatus">
        <el-radio-group v-model="formData.scheduleStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.SCHEDULE_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否生产日" prop="isProductionDay">
        <el-radio-group v-model="formData.isProductionDay">
          <el-radio :label="true">是</el-radio>
          <el-radio :label="false">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="当日生产目标(吨)" prop="productionTarget">
        <el-input v-model="formData.productionTarget" placeholder="请输入当日生产目标(吨)" />
      </el-form-item>
      <el-form-item label="排班类型" prop="scheduleType">
        <el-select v-model="formData.scheduleType" placeholder="请选择排班类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SCHEDULE_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批人" prop="approverId">
        <el-select v-model="formData.approverId" placeholder="请选择审批人" filterable>
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
          format="YYYY-MM-DD HH:mm:ss"
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
import { getIntDictOptions, getBoolDictOptions, DICT_TYPE } from '@/utils/dict'
import { ScheduleApi, Schedule } from '@/api/coal/schedule'
import { getSimpleUserList, UserVO } from '@/api/system/user'
import { ShiftSystemApi, ShiftSystem } from '@/api/coal/shiftsystem'

/** 排班管理 (主表) 表单 */
defineOptions({ name: 'ScheduleForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const userList = ref<UserVO[]>([]) // 用户列表
const shiftSystemList = ref<ShiftSystem[]>([]) // 班制列表
const formData = ref({
  id: undefined,
  scheduleDate: undefined,
  shiftSystemId: undefined,
  scheduleStatus: undefined,
  isProductionDay: undefined,
  productionTarget: undefined,
  scheduleType: undefined,
  approverId: undefined,
  approveTime: undefined,
  remark: undefined,
})
const formRules = reactive({
  scheduleDate: [{ required: true, message: '排班日期不能为空', trigger: 'blur' }],
  shiftSystemId: [{ required: true, message: '班制ID不能为空', trigger: 'blur' }],
  scheduleStatus: [{ required: true, message: '排班状态不能为空', trigger: 'blur' }],
  isProductionDay: [{ required: true, message: '是否生产日不能为空', trigger: 'blur' }],
  scheduleType: [{ required: true, message: '排班类型不能为空', trigger: 'change' }],
})
const formRef = ref() // 表单 Ref

/** 初始化基础数据 */
const initData = async () => {
  try {
    // 获取用户精简列表
    userList.value = await getSimpleUserList()
    // 获取班制列表（只获取父级班制）
    const shiftData = await ShiftSystemApi.getShiftSystemList({})
    shiftSystemList.value = shiftData.filter(item => item.parentId === 0)
  } catch (error) {
    console.error('初始化数据失败:', error)
  }
}

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  
  // 初始化基础数据
  await initData()
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await ScheduleApi.getSchedule(id)
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
    const data = formData.value as unknown as Schedule
    if (formType.value === 'create') {
      await ScheduleApi.createSchedule(data)
      message.success(t('common.createSuccess'))
    } else {
      await ScheduleApi.updateSchedule(data)
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
    scheduleDate: undefined,
    shiftSystemId: undefined,
    scheduleStatus: undefined,
    isProductionDay: undefined,
    productionTarget: undefined,
    scheduleType: undefined,
    approverId: undefined,
    approveTime: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>