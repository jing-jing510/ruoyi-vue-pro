<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="父ID" prop="parentId">
        <el-tree-select
          v-model="formData.parentId"
          :data="shiftSystemTree"
          :props="defaultProps"
          check-strictly
          default-expand-all
          placeholder="请选择父ID"
        />
      </el-form-item>
      <el-form-item label="名称 " prop="name">
        <el-input v-model="formData.name" placeholder="请输入名称 " />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model="formData.code" placeholder="请输入编码" />
      </el-form-item>
      <el-form-item label="是否生产班制" prop="isProduction">
        <el-radio-group v-model="formData.isProduction">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.IS_PRODUCTION)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-time-picker
          v-model="formData.startTime"
          value-format="HH:mm:ss"
          placeholder="选择开始时间"
          :disabled="formData.parentId === 0"
        />
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-time-picker
          v-model="formData.endTime"
          value-format="HH:mm:ss"
          placeholder="选择结束时间"
          :disabled="formData.parentId === 0"
        />
        <div v-if="formData.parentId && formData.parentId > 0 && formData.startTime && formData.endTime" class="mt-2 text-sm text-gray-500">
          班次时长：{{ calculateShiftDuration(formData.startTime, formData.endTime) }} 分钟
        </div>
      </el-form-item>
      <el-form-item label="班次类型" prop="shiftType">
        <el-select v-model="formData.shiftType" placeholder="请选择班次类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SHIFT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入排序" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
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
import { ShiftSystemApi, ShiftSystem } from '@/api/coal/shiftsystem'
import { defaultProps, handleTree } from '@/utils/tree'

/** 班制与班次设置 (树表) 表单 */
defineOptions({ name: 'ShiftSystemForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  parentId: undefined,
  name: undefined,
  code: undefined,
  isProduction: undefined,
  startTime: undefined,
  endTime: undefined,
  shiftType: undefined,
  sort: undefined,
  status: undefined,
})
const formRules = reactive({
  parentId: [{ required: true, message: '父ID不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '名称不能为空', trigger: 'blur' }],
  startTime: [
    { 
      validator: (_rule: any, value: any, callback: any) => {
        // 只有班次层级（parentId > 0）才需要验证时间
        if (formData.value.parentId && formData.value.parentId > 0 && !value) {
          callback(new Error('开始时间不能为空'))
        } else {
          callback()
        }
      }, 
      trigger: 'blur' 
    }
  ],
  endTime: [
    { 
      validator: (_rule: any, value: any, callback: any) => {
        // 只有班次层级（parentId > 0）才需要验证时间
        if (formData.value.parentId && formData.value.parentId > 0 && !value) {
          callback(new Error('结束时间不能为空'))
        } else {
          callback()
        }
      }, 
      trigger: 'blur' 
    }
  ],
  shiftType: [
    { 
      validator: (_rule: any, value: any, callback: any) => {
        // 只有班次层级（parentId > 0）才需要验证班次类型
        if (formData.value.parentId && formData.value.parentId > 0 && !value) {
          callback(new Error('班次类型不能为空'))
        } else {
          callback()
        }
      }, 
      trigger: 'blur' 
    }
  ],
  sort: [{ required: true, message: '排序不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const shiftSystemTree = ref() // 树形结构

// 时间处理工具函数（备用）
// const formatShiftTime = (time: string): string => {
//   if (!time) return ''
//   return formatDate(new Date(`2000-01-01 ${time}`), 'HH:mm:ss')
// }

// 计算班次时长（支持跨天）
const calculateShiftDuration = (startTime: string, endTime: string): number => {
  const start = new Date(`2000-01-01 ${startTime}`)
  let end = new Date(`2000-01-01 ${endTime}`)
  
  // 如果结束时间小于开始时间，说明跨天了
  if (end < start) {
    end = new Date(`2000-01-02 ${endTime}`)
  }
  
  return Math.floor((end.getTime() - start.getTime()) / (1000 * 60)) // 返回分钟数
}

// 监听父ID变化，清空时间字段
watch(() => formData.value.parentId, (newVal) => {
  if (newVal === 0) {
    // 班制层级，清空时间字段
    formData.value.startTime = undefined
    formData.value.endTime = undefined
    formData.value.shiftType = undefined
  }
})

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
      formData.value = await ShiftSystemApi.getShiftSystem(id)
    } finally {
      formLoading.value = false
    }
  }
  await getShiftSystemTree()
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
    const data = formData.value as unknown as ShiftSystem
    if (formType.value === 'create') {
      await ShiftSystemApi.createShiftSystem(data)
      message.success(t('common.createSuccess'))
    } else {
      await ShiftSystemApi.updateShiftSystem(data)
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
    parentId: undefined,
    name: undefined,
    code: undefined,
    isProduction: undefined,
    startTime: undefined,
    endTime: undefined,
    shiftType: undefined,
    sort: undefined,
    status: undefined,
  }
  formRef.value?.resetFields()
}

/** 获得班制与班次设置 (树表)树 */
const getShiftSystemTree = async () => {
  shiftSystemTree.value = []
  const data = await ShiftSystemApi.getShiftSystemList({})
  const root: Tree = { id: 0, name: '顶级班制与班次设置 (树表)', children: [] }
  root.children = handleTree(data, 'id', 'parentId')
  shiftSystemTree.value.push(root)
}
</script>
