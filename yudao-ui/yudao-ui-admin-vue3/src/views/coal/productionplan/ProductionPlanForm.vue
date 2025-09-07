<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      v-loading="formLoading"
    >
      <el-form-item label="计划名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入计划名称" />
      </el-form-item>
      <el-form-item label="父计划ID" prop="parentId">
        <el-tree-select
          v-model="formData.parentId"
          :data="productionPlanTree"
          :props="defaultProps"
          check-strictly
          default-expand-all
          placeholder="请选择父计划ID"
          :disabled="formType === 'generate'"
        />
      </el-form-item>
      <el-form-item label="计划类型" prop="planType">
        <el-select v-model="formData.planType" placeholder="请选择计划类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.PLAN_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="计划年度" prop="planYear">
        <el-input v-model="formData.planYear" placeholder="请输入计划年度" />
      </el-form-item>
      <el-form-item label="计划月份" prop="planMonth">
        <el-input v-model="formData.planMonth" placeholder="请输入计划月份" />
      </el-form-item>
      <el-form-item label="计划日期" prop="planDate">
        <el-date-picker
          v-model="formData.planDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择计划日期"
        />
      </el-form-item>
      <el-form-item label="计划状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.PLAN_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="计划入洗原煤量(吨)" prop="rawCoalPlan">
        <el-input v-model="formData.rawCoalPlan" placeholder="请输入计划入洗原煤量(吨)" />
      </el-form-item>
      <el-form-item label="计划末煤产量(吨)" prop="fineCoalPlan">
        <el-input v-model="formData.fineCoalPlan" placeholder="请输入计划末煤产量(吨)" />
      </el-form-item>
      <el-form-item label="计划粒煤产量(吨)" prop="granularCoalPlan">
        <el-input v-model="formData.granularCoalPlan" placeholder="请输入计划粒煤产量(吨)" />
      </el-form-item>
      <el-form-item label="计划大块煤产量(吨)" prop="largeBlockCoalPlan">
        <el-input v-model="formData.largeBlockCoalPlan" placeholder="请输入计划大块煤产量(吨)" />
      </el-form-item>
      <el-form-item label="计划中块煤产量(吨)" prop="mediumBlockCoalPlan">
        <el-input v-model="formData.mediumBlockCoalPlan" placeholder="请输入计划中块煤产量(吨)" />
      </el-form-item>
      <el-form-item label="计划小块煤产量(吨)" prop="smallBlockCoalPlan">
        <el-input v-model="formData.smallBlockCoalPlan" placeholder="请输入计划小块煤产量(吨)" />
      </el-form-item>
      <el-form-item label="计划中煤产量(吨)" prop="middlingCoalPlan">
        <el-input v-model="formData.middlingCoalPlan" placeholder="请输入计划中煤产量(吨)" />
      </el-form-item>
      <el-form-item label="计划煤泥产量(吨)" prop="slimePlan">
        <el-input v-model="formData.slimePlan" placeholder="请输入计划煤泥产量(吨)" />
      </el-form-item>
      <el-form-item label="计划矸石产量(吨)" prop="ganguePlan">
        <el-input v-model="formData.ganguePlan" placeholder="请输入计划矸石产量(吨)" />
      </el-form-item>
      <el-form-item label="末煤灰分(%)" prop="fineCoalAsh">
        <el-input v-model="formData.fineCoalAsh" placeholder="请输入末煤灰分(%)" />
      </el-form-item>
      <el-form-item label="粒煤灰分(%)" prop="granularCoalAsh">
        <el-input v-model="formData.granularCoalAsh" placeholder="请输入粒煤灰分(%)" />
      </el-form-item>
      <el-form-item label="大块煤灰分(%)" prop="largeBlockCoalAsh">
        <el-input v-model="formData.largeBlockCoalAsh" placeholder="请输入大块煤灰分(%)" />
      </el-form-item>
      <el-form-item label="中块煤灰分(%)" prop="mediumBlockCoalAsh">
        <el-input v-model="formData.mediumBlockCoalAsh" placeholder="请输入中块煤灰分(%)" />
      </el-form-item>
      <el-form-item label="小块煤灰分(%)" prop="smallBlockCoalAsh">
        <el-input v-model="formData.smallBlockCoalAsh" placeholder="请输入小块煤灰分(%)" />
      </el-form-item>
      <el-form-item label="中煤灰分(%)" prop="middlingCoalAsh">
        <el-input v-model="formData.middlingCoalAsh" placeholder="请输入中煤灰分(%)" />
      </el-form-item>
<!--      <el-form-item label="制定人ID" prop="creatorId">-->
<!--        <el-input v-model="formData.creatorId" placeholder="请输入制定人ID" />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="审批人ID" prop="approverId">-->
<!--        <el-input v-model="formData.approverId" placeholder="请输入审批人ID" />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="审批时间" prop="approveTime">-->
<!--        <el-date-picker-->
<!--          v-model="formData.approveTime"-->
<!--          type="date"-->
<!--          value-format="x"-->
<!--          placeholder="选择审批时间"-->
<!--        />-->
<!--      </el-form-item>-->
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { ProductionPlanApi, ProductionPlan } from '@/api/coal/productionplan'
import { defaultProps, handleTree } from '@/utils/tree'

/** 生产计划 表单 */
defineOptions({ name: 'ProductionPlanForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改 generation - 生成
const formData = ref({
  id: undefined,
  name: undefined,
  parentId: undefined as number | undefined,
  planType: undefined as number | undefined,
  planYear: undefined,
  planMonth: undefined,
  planDate: undefined,
  status: undefined,
  rawCoalPlan: undefined,
  fineCoalPlan: undefined,
  granularCoalPlan: undefined,
  largeBlockCoalPlan: undefined,
  mediumBlockCoalPlan: undefined,
  smallBlockCoalPlan: undefined,
  middlingCoalPlan: undefined,
  slimePlan: undefined,
  ganguePlan: undefined,
  fineCoalAsh: undefined,
  granularCoalAsh: undefined,
  largeBlockCoalAsh: undefined,
  mediumBlockCoalAsh: undefined,
  smallBlockCoalAsh: undefined,
  middlingCoalAsh: undefined,
  creatorId: undefined,
  approverId: undefined,
  approveTime: undefined,
})
const formRules = reactive({
  name: [{ required: true, message: '计划名称不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '计划状态不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const productionPlanTree = ref() // 树形结构

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
      formData.value = await ProductionPlanApi.getProductionPlan(id)
    } finally {
      formLoading.value = false
    }
  }
  // 一键生成年度计划时，设置默认值
  if (type === 'generate') {
    formData.value.planType = 1 // 1 代表年度计划
    formData.value.parentId = 0 // 0 代表顶级
  }
  await getProductionPlanTree()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  console.log(formData.value)
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as ProductionPlan
    if (formType.value === 'generate') {
      await ProductionPlanApi.generateYearlyPlan(data)
      message.success('一键生成年度计划成功')
    } else if (formType.value === 'create') {
      await ProductionPlanApi.createProductionPlan(data)
      message.success(t('common.createSuccess'))
    } else {
      await ProductionPlanApi.updateProductionPlan(data)
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
    name: undefined,
    parentId: undefined as number | undefined,
    planType: undefined as number | undefined,
    planYear: undefined,
    planMonth: undefined,
    planDate: undefined,
    status: undefined,
    rawCoalPlan: undefined,
    fineCoalPlan: undefined,
    granularCoalPlan: undefined,
    largeBlockCoalPlan: undefined,
    mediumBlockCoalPlan: undefined,
    smallBlockCoalPlan: undefined,
    middlingCoalPlan: undefined,
    slimePlan: undefined,
    ganguePlan: undefined,
    fineCoalAsh: undefined,
    granularCoalAsh: undefined,
    largeBlockCoalAsh: undefined,
    mediumBlockCoalAsh: undefined,
    smallBlockCoalAsh: undefined,
    middlingCoalAsh: undefined,
    creatorId: undefined,
    approverId: undefined,
    approveTime: undefined,
  }
  formRef.value?.resetFields()
}

/** 获得生产计划树 */
const getProductionPlanTree = async () => {
  productionPlanTree.value = []
  const data = await ProductionPlanApi.getProductionPlanList()
  const root: Tree = { id: 0, name: '顶级生产计划', children: [] }
  root.children = handleTree(data, 'id', 'parentId')
  productionPlanTree.value.push(root)
}
</script>
