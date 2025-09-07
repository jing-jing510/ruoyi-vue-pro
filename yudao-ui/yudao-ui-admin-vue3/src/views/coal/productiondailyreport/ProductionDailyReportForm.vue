<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="日期" prop="reportDate">
        <el-date-picker
          v-model="formData.reportDate"
          type="date"
          value-format="x"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="班次ID" prop="shiftId">
        <el-input v-model="formData.shiftId" placeholder="请输入班次ID" />
      </el-form-item>
      <el-form-item label="集控员（操作人）" prop="operatorId">
        <el-select v-model="formData.operatorId" placeholder="请选择集控员（操作人）" clearable filterable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="带班主任/班长" prop="shiftLeaderId">
        <el-select v-model="formData.shiftLeaderId" placeholder="请选择带班主任/班长" clearable filterable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="启车时间" prop="startTime">
        <el-date-picker
          v-model="formData.startTime"
          type="datetime"
          value-format="x"
          placeholder="选择启车时间"
        />
      </el-form-item>
      <el-form-item label="带煤时间(分钟)" prop="coalFeedingTime">
        <el-input v-model="formData.coalFeedingTime" placeholder="请输入带煤时间(分钟)" />
      </el-form-item>
      <el-form-item label="停车时间" prop="stopTime">
        <el-date-picker
          v-model="formData.stopTime"
          type="datetime"
          value-format="x"
          placeholder="选择停车时间"
        />
      </el-form-item>
      <el-form-item label="有效带煤时间(分钟)" prop="effectiveFeedingTime">
        <el-input v-model="formData.effectiveFeedingTime" placeholder="请输入有效带煤时间(分钟)" />
      </el-form-item>
      <el-form-item label="故障影响时间(分钟)" prop="faultImpactTime">
        <el-input v-model="formData.faultImpactTime" placeholder="请输入故障影响时间(分钟)" />
      </el-form-item>
      <el-form-item label="入洗煤量(吨)" prop="rawCoalInput">
        <el-input v-model="formData.rawCoalInput" placeholder="请输入入洗煤量(吨)" />
      </el-form-item>
      <el-form-item label="小时处理量(吨/小时)" prop="hourlyCapacity">
        <el-input v-model="formData.hourlyCapacity" placeholder="请输入小时处理量(吨/小时)" />
      </el-form-item>
      <el-form-item label="块精煤产量(吨)" prop="blockCleanCoalOutput">
        <el-input v-model="formData.blockCleanCoalOutput" placeholder="请输入块精煤产量(吨)" />
      </el-form-item>
      <el-form-item label="末精煤产量(吨)" prop="fineCleanCoalOutput">
        <el-input v-model="formData.fineCleanCoalOutput" placeholder="请输入末精煤产量(吨)" />
      </el-form-item>
      <el-form-item label="矸石产量(吨)" prop="gangueOutput">
        <el-input v-model="formData.gangueOutput" placeholder="请输入矸石产量(吨)" />
      </el-form-item>
      <el-form-item label="中煤产量(吨)" prop="middlingCoalOutput">
        <el-input v-model="formData.middlingCoalOutput" placeholder="请输入中煤产量(吨)" />
      </el-form-item>
      <el-form-item label="压滤板次" prop="filterPressCycles">
        <el-input v-model="formData.filterPressCycles" placeholder="请输入压滤板次" />
      </el-form-item>
      <el-form-item label="压滤煤量(吨)" prop="filterPressCoalAmount">
        <el-input v-model="formData.filterPressCoalAmount" placeholder="请输入压滤煤量(吨)" />
      </el-form-item>
      <el-form-item label="滤布使用量(张)" prop="filterClothUsage">
        <el-input v-model="formData.filterClothUsage" placeholder="请输入滤布使用量(张)" />
      </el-form-item>
      <el-form-item label="放舱记录" prop="dischargeRecord">
        <el-input v-model="formData.dischargeRecord" placeholder="请输入放舱记录" />
      </el-form-item>
      <el-form-item label="挡板添加介质量(kg)" prop="baffleMediumAddition">
        <el-input v-model="formData.baffleMediumAddition" placeholder="请输入挡板添加介质量(kg)" />
      </el-form-item>
      <el-form-item label="CaO量(kg)" prop="caoAmount">
        <el-input v-model="formData.caoAmount" placeholder="请输入CaO量(kg)" />
      </el-form-item>
      <el-form-item label="絮凝剂(kg)" prop="flocculantAmount">
        <el-input v-model="formData.flocculantAmount" placeholder="请输入絮凝剂(kg)" />
      </el-form-item>
      <el-form-item label="317密度(kg/L)" prop="densityMd317">
        <el-input v-model="formData.densityMd317" placeholder="请输入317密度(kg/L)" />
      </el-form-item>
      <el-form-item label="第一次块精煤灰分(%)" prop="firstAshBlockClean">
        <el-input v-model="formData.firstAshBlockClean" placeholder="请输入第一次块精煤灰分(%)" />
      </el-form-item>
      <el-form-item label="第一次末精煤灰分(%)" prop="firstAshFineClean">
        <el-input v-model="formData.firstAshFineClean" placeholder="请输入第一次末精煤灰分(%)" />
      </el-form-item>
      <el-form-item label="第一次中煤灰分(%)" prop="firstAshMiddling">
        <el-input v-model="formData.firstAshMiddling" placeholder="请输入第一次中煤灰分(%)" />
      </el-form-item>
      <el-form-item label="第一次煤泥灰分(%)" prop="firstAshSlime">
        <el-input v-model="formData.firstAshSlime" placeholder="请输入第一次煤泥灰分(%)" />
      </el-form-item>
      <el-form-item label="第一次矸石灰分(%)" prop="firstAshGangue">
        <el-input v-model="formData.firstAshGangue" placeholder="请输入第一次矸石灰分(%)" />
      </el-form-item>
      <el-form-item label="第二次块精煤灰分(%)" prop="secondAshBlockClean">
        <el-input v-model="formData.secondAshBlockClean" placeholder="请输入第二次块精煤灰分(%)" />
      </el-form-item>
      <el-form-item label="第二次末精煤灰分(%)" prop="secondAshFineClean">
        <el-input v-model="formData.secondAshFineClean" placeholder="请输入第二次末精煤灰分(%)" />
      </el-form-item>
      <el-form-item label="第二次中煤灰分(%)" prop="secondAshMiddling">
        <el-input v-model="formData.secondAshMiddling" placeholder="请输入第二次中煤灰分(%)" />
      </el-form-item>
      <el-form-item label="第二次煤泥灰分(%)" prop="secondAshSlime">
        <el-input v-model="formData.secondAshSlime" placeholder="请输入第二次煤泥灰分(%)" />
      </el-form-item>
      <el-form-item label="第二次矸石灰分(%)" prop="secondAshGangue">
        <el-input v-model="formData.secondAshGangue" placeholder="请输入第二次矸石灰分(%)" />
      </el-form-item>
      <el-form-item label="影响时间记录详情" prop="impactTimeRecord">
        <el-input v-model="formData.impactTimeRecord" placeholder="请输入影响时间记录详情" />
      </el-form-item>
      <el-form-item label="交办事项" prop="assignedTasks">
        <el-input v-model="formData.assignedTasks" placeholder="请输入交办事项" />
      </el-form-item>
      <el-form-item label="启车循环水池液位" prop="startCirculatingWaterPool">
        <el-input v-model="formData.startCirculatingWaterPool" placeholder="请输入启车循环水池液位" />
      </el-form-item>
      <el-form-item label="启车清水桶液位" prop="startCleanWaterTank">
        <el-input v-model="formData.startCleanWaterTank" placeholder="请输入启车清水桶液位" />
      </el-form-item>
      <el-form-item label="启车末煤仓位" prop="startFineCoalLevel">
        <el-input v-model="formData.startFineCoalLevel" placeholder="请输入启车末煤仓位" />
      </el-form-item>
      <el-form-item label="启车粒煤仓位" prop="startGranularCoalLevel">
        <el-input v-model="formData.startGranularCoalLevel" placeholder="请输入启车粒煤仓位" />
      </el-form-item>
      <el-form-item label="启车大块仓位" prop="startLargeBlockLevel">
        <el-input v-model="formData.startLargeBlockLevel" placeholder="请输入启车大块仓位" />
      </el-form-item>
      <el-form-item label="启车中块仓位" prop="startMediumBlockLevel">
        <el-input v-model="formData.startMediumBlockLevel" placeholder="请输入启车中块仓位" />
      </el-form-item>
      <el-form-item label="启车小块仓位" prop="startSmallBlockLevel">
        <el-input v-model="formData.startSmallBlockLevel" placeholder="请输入启车小块仓位" />
      </el-form-item>
      <el-form-item label="启车中煤仓位" prop="startMiddlingCoalLevel">
        <el-input v-model="formData.startMiddlingCoalLevel" placeholder="请输入启车中煤仓位" />
      </el-form-item>
      <el-form-item label="启车矸石仓位" prop="startGangueLevel">
        <el-input v-model="formData.startGangueLevel" placeholder="请输入启车矸石仓位" />
      </el-form-item>
      <el-form-item label="停车循环水池液位" prop="stopCirculatingWaterPool">
        <el-input v-model="formData.stopCirculatingWaterPool" placeholder="请输入停车循环水池液位" />
      </el-form-item>
      <el-form-item label="停车清水桶液位" prop="stopCleanWaterTank">
        <el-input v-model="formData.stopCleanWaterTank" placeholder="请输入停车清水桶液位" />
      </el-form-item>
      <el-form-item label="停车末煤仓位" prop="stopFineCoalLevel">
        <el-input v-model="formData.stopFineCoalLevel" placeholder="请输入停车末煤仓位" />
      </el-form-item>
      <el-form-item label="停车粒煤仓位" prop="stopGranularCoalLevel">
        <el-input v-model="formData.stopGranularCoalLevel" placeholder="请输入停车粒煤仓位" />
      </el-form-item>
      <el-form-item label="停车大块仓位" prop="stopLargeBlockLevel">
        <el-input v-model="formData.stopLargeBlockLevel" placeholder="请输入停车大块仓位" />
      </el-form-item>
      <el-form-item label="停车中块仓位" prop="stopMediumBlockLevel">
        <el-input v-model="formData.stopMediumBlockLevel" placeholder="请输入停车中块仓位" />
      </el-form-item>
      <el-form-item label="停车小块仓位" prop="stopSmallBlockLevel">
        <el-input v-model="formData.stopSmallBlockLevel" placeholder="请输入停车小块仓位" />
      </el-form-item>
      <el-form-item label="停车中煤仓位" prop="stopMiddlingCoalLevel">
        <el-input v-model="formData.stopMiddlingCoalLevel" placeholder="请输入停车中煤仓位" />
      </el-form-item>
      <el-form-item label="停车矸石仓位" prop="stopGangueLevel">
        <el-input v-model="formData.stopGangueLevel" placeholder="请输入停车矸石仓位" />
      </el-form-item>
      <el-form-item label="备注信息" prop="remarks">
        <el-input v-model="formData.remarks" placeholder="请输入备注信息" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { ProductionDailyReportApi, ProductionDailyReport } from '@/api/coal/productiondailyreport'

import { getSimpleUserList, UserVO } from '@/api/system/user'
/** 现场生产日报 表单 */
defineOptions({ name: 'ProductionDailyReportForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const userList = ref<UserVO[]>([]) // 用户列表
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  reportDate: undefined,
  shiftId: undefined,
  operatorId: undefined,
  shiftLeaderId: undefined,
  startTime: undefined,
  coalFeedingTime: undefined,
  stopTime: undefined,
  effectiveFeedingTime: undefined,
  faultImpactTime: undefined,
  rawCoalInput: undefined,
  hourlyCapacity: undefined,
  blockCleanCoalOutput: undefined,
  fineCleanCoalOutput: undefined,
  gangueOutput: undefined,
  middlingCoalOutput: undefined,
  filterPressCycles: undefined,
  filterPressCoalAmount: undefined,
  filterClothUsage: undefined,
  dischargeRecord: undefined,
  baffleMediumAddition: undefined,
  caoAmount: undefined,
  flocculantAmount: undefined,
  densityMd317: undefined,
  firstAshBlockClean: undefined,
  firstAshFineClean: undefined,
  firstAshMiddling: undefined,
  firstAshSlime: undefined,
  firstAshGangue: undefined,
  secondAshBlockClean: undefined,
  secondAshFineClean: undefined,
  secondAshMiddling: undefined,
  secondAshSlime: undefined,
  secondAshGangue: undefined,
  impactTimeRecord: undefined,
  assignedTasks: undefined,
  startCirculatingWaterPool: undefined,
  startCleanWaterTank: undefined,
  startFineCoalLevel: undefined,
  startGranularCoalLevel: undefined,
  startLargeBlockLevel: undefined,
  startMediumBlockLevel: undefined,
  startSmallBlockLevel: undefined,
  startMiddlingCoalLevel: undefined,
  startGangueLevel: undefined,
  stopCirculatingWaterPool: undefined,
  stopCleanWaterTank: undefined,
  stopFineCoalLevel: undefined,
  stopGranularCoalLevel: undefined,
  stopLargeBlockLevel: undefined,
  stopMediumBlockLevel: undefined,
  stopSmallBlockLevel: undefined,
  stopMiddlingCoalLevel: undefined,
  stopGangueLevel: undefined,
  remarks: undefined,
})
const formRules = reactive({
  reportDate: [{ required: true, message: '日期不能为空', trigger: 'blur' }],
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
      formData.value = await ProductionDailyReportApi.getProductionDailyReport(id)
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
    const data = formData.value as unknown as ProductionDailyReport
    if (formType.value === 'create') {
      await ProductionDailyReportApi.createProductionDailyReport(data)
      message.success(t('common.createSuccess'))
    } else {
      await ProductionDailyReportApi.updateProductionDailyReport(data)
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
    reportDate: undefined,
    shiftId: undefined,
    operatorId: undefined,
    shiftLeaderId: undefined,
    startTime: undefined,
    coalFeedingTime: undefined,
    stopTime: undefined,
    effectiveFeedingTime: undefined,
    faultImpactTime: undefined,
    rawCoalInput: undefined,
    hourlyCapacity: undefined,
    blockCleanCoalOutput: undefined,
    fineCleanCoalOutput: undefined,
    gangueOutput: undefined,
    middlingCoalOutput: undefined,
    filterPressCycles: undefined,
    filterPressCoalAmount: undefined,
    filterClothUsage: undefined,
    dischargeRecord: undefined,
    baffleMediumAddition: undefined,
    caoAmount: undefined,
    flocculantAmount: undefined,
    densityMd317: undefined,
    firstAshBlockClean: undefined,
    firstAshFineClean: undefined,
    firstAshMiddling: undefined,
    firstAshSlime: undefined,
    firstAshGangue: undefined,
    secondAshBlockClean: undefined,
    secondAshFineClean: undefined,
    secondAshMiddling: undefined,
    secondAshSlime: undefined,
    secondAshGangue: undefined,
    impactTimeRecord: undefined,
    assignedTasks: undefined,
    startCirculatingWaterPool: undefined,
    startCleanWaterTank: undefined,
    startFineCoalLevel: undefined,
    startGranularCoalLevel: undefined,
    startLargeBlockLevel: undefined,
    startMediumBlockLevel: undefined,
    startSmallBlockLevel: undefined,
    startMiddlingCoalLevel: undefined,
    startGangueLevel: undefined,
    stopCirculatingWaterPool: undefined,
    stopCleanWaterTank: undefined,
    stopFineCoalLevel: undefined,
    stopGranularCoalLevel: undefined,
    stopLargeBlockLevel: undefined,
    stopMediumBlockLevel: undefined,
    stopSmallBlockLevel: undefined,
    stopMiddlingCoalLevel: undefined,
    stopGangueLevel: undefined,
    remarks: undefined,
  }
  formRef.value?.resetFields()
}
/** 加载用户列表 */
const loadUserList = async () => {
  try {
    const data = await getSimpleUserList()
    userList.value = data
  } catch (error) {
    console.error('加载用户列表失败:', error)
    userList.value = []
  }
}
/** 初始化 **/
onMounted(() => {
  loadUserList()
})
</script>
