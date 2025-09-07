<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="排班ID" prop="scheduleId" style="display: none;">
        <el-input v-model="formData.scheduleId" placeholder="排班ID（自动填入）" disabled />
      </el-form-item>
      <el-form-item label="班次名称" prop="shiftId">
        <el-select v-model="formData.shiftId" placeholder="请选择班次" filterable>
          <el-option
            v-for="shift in shiftList"
            :key="shift.id"
            :label="shift.name"
            :value="shift.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="员工姓名" prop="userId">
        <el-select v-model="formData.userId" placeholder="请选择员工" filterable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="岗位类型" prop="positionType">
        <el-select v-model="formData.positionType" placeholder="请选择岗位类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.POSITION_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否班组长" prop="isLeader">
        <el-radio-group v-model="formData.isLeader">
          <el-radio :label="true">是</el-radio>
          <el-radio :label="false">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="是否替班" prop="isSubstitute">
        <el-radio-group v-model="formData.isSubstitute">
          <el-radio :label="true">是</el-radio>
          <el-radio :label="false">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="替班原因" prop="substituteReason">
        <el-input v-model="formData.substituteReason" placeholder="请输入替班原因" />
      </el-form-item>
      <el-form-item label="工作状态" prop="workStatus">
        <el-radio-group v-model="formData.workStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.WORK_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="联系电话" prop="contactPhone">
        <el-input v-model="formData.contactPhone" placeholder="请输入联系电话" />
      </el-form-item>
      <el-form-item label="紧急联系人" prop="emergencyContact">
        <el-input v-model="formData.emergencyContact" placeholder="请输入紧急联系人" />
      </el-form-item>
      <el-form-item label="紧急联系电话" prop="emergencyPhone">
        <el-input v-model="formData.emergencyPhone" placeholder="请输入紧急联系电话" />
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
import { ScheduleStaffApi, ScheduleStaff } from '@/api/coal/schedule'
import { getSimpleUserList, UserVO } from '@/api/system/user'
import { ShiftSystemApi, ShiftSystem } from '@/api/coal/shiftsystem'

/** 人员安排 (子表) 表单 */
defineOptions({ name: 'ScheduleStaffForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const userList = ref<UserVO[]>([]) // 用户列表
const shiftList = ref<ShiftSystem[]>([]) // 班次列表
const formData = ref({
  id: undefined,
  scheduleId: undefined,
  shiftId: undefined,
  userId: undefined,
  positionType: undefined,
  isLeader: undefined,
  isSubstitute: undefined,
  substituteReason: undefined,
  workStatus: undefined,
  contactPhone: undefined,
  emergencyContact: undefined,
  emergencyPhone: undefined,
})
const formRules = reactive({
  scheduleId: [{ required: true, message: '排班ID (关联主表)不能为空', trigger: 'blur' }],
  shiftId: [{ required: true, message: '班次ID (关联coal_shift_system的子节点)不能为空', trigger: 'blur' }],
  userId: [{ required: true, message: '员工ID不能为空', trigger: 'blur' }],
  positionType: [{ required: true, message: '岗位类型不能为空', trigger: 'change' }],
  isLeader: [{ required: true, message: '是否班组长不能为空', trigger: 'blur' }],
  isSubstitute: [{ required: true, message: '是否替班不能为空', trigger: 'blur' }],
  workStatus: [{ required: true, message: '工作状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 初始化基础数据 */
const initData = async () => {
  try {
    userList.value = await getSimpleUserList()
    const shiftData = await ShiftSystemApi.getShiftSystemList({})
    // 获取所有班次（子节点）
    shiftList.value = shiftData.filter(item => item.parentId > 0)
  } catch (error) {
    console.error('初始化数据失败:', error)
  }
}

/** 打开弹窗 */
const open = async (type: string, id?: number, scheduleId?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  
  // 初始化基础数据
  await initData()
  
  // 设置排班ID
  if (scheduleId) {
    formData.value.scheduleId = scheduleId
  }
  
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await ScheduleStaffApi.getScheduleStaff(id)
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
    const data = formData.value as unknown as ScheduleStaff
    if (formType.value === 'create') {
      await ScheduleStaffApi.createScheduleStaff(data)
      message.success(t('common.createSuccess'))
    } else {
      await ScheduleStaffApi.updateScheduleStaff(data)
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
    scheduleId: undefined,
    shiftId: undefined,
    userId: undefined,
    positionType: undefined,
    isLeader: undefined,
    isSubstitute: undefined,
    substituteReason: undefined,
    workStatus: undefined,
    contactPhone: undefined,
    emergencyContact: undefined,
    emergencyPhone: undefined,
  }
  formRef.value?.resetFields()
}
</script>