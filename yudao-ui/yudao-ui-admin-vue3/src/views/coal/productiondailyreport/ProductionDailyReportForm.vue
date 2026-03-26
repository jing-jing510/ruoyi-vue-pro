<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="1200px">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      v-loading="formLoading"
    >
      <!-- 基本信息 -->
      <el-card class="mb-16px">
        <template #header>
          <span class="font-bold">基本信息</span>
        </template>
        <el-row :gutter="16">
          <el-col :span="8">
            <el-form-item label="日期" prop="reportDate">
              <el-date-picker
                v-model="formData.reportDate"
                type="date"
                value-format="x"
                placeholder="选择日期"
                class="w-full"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="启车时间" prop="startTime">
              <el-date-picker
                v-model="formData.startTime"
                type="datetime"
                value-format="x"
                placeholder="选择启车时间"
                class="w-full"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="停车时间" prop="stopTime">
              <el-date-picker
                v-model="formData.stopTime"
                type="datetime"
                value-format="x"
                placeholder="选择停车时间"
                class="w-full"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="8">
            <el-form-item label="故障时间(h)" prop="faultTimeHours">
              <el-input v-model="formData.faultTimeHours" placeholder="请输入故障时间(小时)" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="运行时间(h)" prop="runningTimeHours">
              <el-input v-model="formData.runningTimeHours" placeholder="请输入运行时间(小时)" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="入洗原煤量(吨)" prop="rawCoalInput">
              <el-input v-model="formData.rawCoalInput" placeholder="请输入入洗原煤量(吨)" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="8">
            <el-form-item label="集控员" prop="centralController">
              <el-select v-model="formData.centralController" placeholder="请选择集控员" clearable filterable class="w-full">
                <el-option
                  v-for="user in userList"
                  :key="user.id"
                  :label="user.nickname"
                  :value="user.nickname"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产班长" prop="productionShiftLeader">
              <el-select v-model="formData.productionShiftLeader" placeholder="请选择生产班长" clearable filterable class="w-full">
                <el-option
                  v-for="user in userList"
                  :key="user.id"
                  :label="user.nickname"
                  :value="user.nickname"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="调度主任" prop="dispatchDirector">
              <el-select v-model="formData.dispatchDirector" placeholder="请选择调度主任" clearable filterable class="w-full">
                <el-option
                  v-for="user in userList"
                  :key="user.id"
                  :label="user.nickname"
                  :value="user.nickname"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="24">
            <el-form-item label="停车原因" prop="shutdownReason">
              <el-input v-model="formData.shutdownReason" type="textarea" placeholder="请输入停车原因" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>

      <!-- 皮带秤及产量统计 -->
      <el-card class="mb-16px">
        <template #header>
          <span class="font-bold">皮带秤及产量统计</span>
        </template>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="201原煤皮带当班量(吨)" prop="beltScale201RawCoalCurrentShift">
              <el-input v-model="formData.beltScale201RawCoalCurrentShift" placeholder="请输入当班量" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="701块精煤当班量(吨)" prop="beltScale701BlockCleanCurrentShift">
              <el-input v-model="formData.beltScale701BlockCleanCurrentShift" placeholder="请输入当班量" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="701块精煤当班产率(%)" prop="beltScale701BlockCleanCurrentYield">
              <el-input v-model="formData.beltScale701BlockCleanCurrentYield" placeholder="请输入当班产率" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="702末精煤当班量(吨)" prop="beltScale702FineCleanCurrentShift">
              <el-input v-model="formData.beltScale702FineCleanCurrentShift" placeholder="请输入当班量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="702末精煤当班产率(%)" prop="beltScale702FineCleanCurrentYield">
              <el-input v-model="formData.beltScale702FineCleanCurrentYield" placeholder="请输入当班产率" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="压滤煤泥当班量(吨)" prop="filterPressSlimeCurrentShift">
              <el-input v-model="formData.filterPressSlimeCurrentShift" placeholder="请输入当班量" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="压滤煤泥当班产率(%)" prop="filterPressSlimeCurrentYield">
              <el-input v-model="formData.filterPressSlimeCurrentYield" placeholder="请输入当班产率" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="901矸石当班量(吨)" prop="gangue901CurrentShift">
              <el-input v-model="formData.gangue901CurrentShift" placeholder="请输入当班量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="901矸石当班产率(%)" prop="gangue901CurrentYield">
              <el-input v-model="formData.gangue901CurrentYield" placeholder="请输入当班产率" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="商品煤总重当班量(吨)" prop="commercialCoalTotalCurrentShift">
              <el-input v-model="formData.commercialCoalTotalCurrentShift" placeholder="请输入当班量" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="商品煤总重当班产率(%)" prop="commercialCoalTotalCurrentYield">
              <el-input v-model="formData.commercialCoalTotalCurrentYield" placeholder="请输入当班产率" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>

      <!-- 煤质情况 -->
      <el-card class="mb-16px">
        <template #header>
          <span class="font-bold">煤质情况</span>
        </template>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="原煤灰分(%)" prop="rawCoalAshContent">
              <el-input v-model="formData.rawCoalAshContent" placeholder="请输入原煤灰分" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="筒仓大块灰分(%)" prop="siloLargeLumpsAshContent">
              <el-input v-model="formData.siloLargeLumpsAshContent" placeholder="请输入筒仓大块灰分" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="筒仓三八块灰分(%)" prop="silo38LumpsAshContent">
              <el-input v-model="formData.silo38LumpsAshContent" placeholder="请输入筒仓三八块灰分" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="筒仓籽煤灰分(%)" prop="siloSeedCoalAshContent">
              <el-input v-model="formData.siloSeedCoalAshContent" placeholder="请输入筒仓籽煤灰分" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="筒仓沫煤灰分(%)" prop="siloFineCoalAshContent">
              <el-input v-model="formData.siloFineCoalAshContent" placeholder="请输入筒仓沫煤灰分" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="煤泥灰分(%)" prop="slimeAshContent">
              <el-input v-model="formData.slimeAshContent" placeholder="请输入煤泥灰分" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="矸石灰分(%)" prop="gangueAshContent">
              <el-input v-model="formData.gangueAshContent" placeholder="请输入矸石灰分" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>

      <!-- 生产设置参数 -->
      <el-card class="mb-16px">
        <template #header>
          <span class="font-bold">生产设置参数</span>
        </template>
        <el-row :gutter="16">
          <el-col :span="8">
            <el-form-item label="305浅槽密度最小值(g/cm³)" prop="shallowTrough305DensityMin">
              <el-input v-model="formData.shallowTrough305DensityMin" placeholder="请输入最小值" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="305浅槽密度最大值(g/cm³)" prop="shallowTrough305DensityMax">
              <el-input v-model="formData.shallowTrough305DensityMax" placeholder="请输入最大值" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="321旋流器压力(Mpa)" prop="cyclone321Pressure">
              <el-input v-model="formData.cyclone321Pressure" placeholder="请输入压力" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="8">
            <el-form-item label="333TSS密度最小值(g/cm³)" prop="tss333DensityMin">
              <el-input v-model="formData.tss333DensityMin" placeholder="请输入最小值" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="333TSS密度最大值(g/cm³)" prop="tss333DensityMax">
              <el-input v-model="formData.tss333DensityMax" placeholder="请输入最大值" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>

      <!-- 生产耗材统计 -->
      <el-card class="mb-16px">
        <template #header>
          <span class="font-bold">生产耗材统计</span>
        </template>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="电耗损(kw*h)" prop="electricityConsumptionCurrentShift">
              <el-input v-model="formData.electricityConsumptionCurrentShift" placeholder="请输入电耗损" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="污水处理站补水量(m³)" prop="wastewaterTreatmentReplenishmentWater">
              <el-input v-model="formData.wastewaterTreatmentReplenishmentWater" placeholder="请输入补水量" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="磁铁矿粉(吨)" prop="magnetitePowderCurrentShift">
              <el-input v-model="formData.magnetitePowderCurrentShift" placeholder="请输入磁铁矿粉" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="白药-阴离子(kg)" prop="whiteReagentAnionicCurrentShift">
              <el-input v-model="formData.whiteReagentAnionicCurrentShift" placeholder="请输入白药-阴离子" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="黄药-阳离子(kg)" prop="yellowReagentCationicCurrentShift">
              <el-input v-model="formData.yellowReagentCationicCurrentShift" placeholder="请输入黄药-阳离子" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>

      <!-- 筒仓仓位统计 -->
      <el-card class="mb-16px">
        <template #header>
          <span class="font-bold">筒仓仓位统计</span>
        </template>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="原煤仓开机仓位(%)" prop="rawCoalSiloStartupPosition">
              <el-input v-model="formData.rawCoalSiloStartupPosition" placeholder="请输入开机仓位" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="原煤仓停机仓位(%)" prop="rawCoalSiloShutdownPosition">
              <el-input v-model="formData.rawCoalSiloShutdownPosition" placeholder="请输入停机仓位" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="大块煤仓开机仓位(%)" prop="largeLumpCoalSiloStartupPosition">
              <el-input v-model="formData.largeLumpCoalSiloStartupPosition" placeholder="请输入开机仓位" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="大块煤仓停机仓位(%)" prop="largeLumpCoalSiloShutdownPosition">
              <el-input v-model="formData.largeLumpCoalSiloShutdownPosition" placeholder="请输入停机仓位" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="三八块煤仓开机仓位(%)" prop="lump38CoalSiloStartupPosition">
              <el-input v-model="formData.lump38CoalSiloStartupPosition" placeholder="请输入开机仓位" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="三八块煤仓停机仓位(%)" prop="lump38CoalSiloShutdownPosition">
              <el-input v-model="formData.lump38CoalSiloShutdownPosition" placeholder="请输入停机仓位" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="籽煤仓开机仓位(%)" prop="seedCoalSiloStartupPosition">
              <el-input v-model="formData.seedCoalSiloStartupPosition" placeholder="请输入开机仓位" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="籽煤仓停机仓位(%)" prop="seedCoalSiloShutdownPosition">
              <el-input v-model="formData.seedCoalSiloShutdownPosition" placeholder="请输入停机仓位" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item label="沫煤仓开机仓位(%)" prop="fineCoalSiloStartupPosition">
              <el-input v-model="formData.fineCoalSiloStartupPosition" placeholder="请输入开机仓位" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="沫煤仓停机仓位(%)" prop="fineCoalSiloShutdownPosition">
              <el-input v-model="formData.fineCoalSiloShutdownPosition" placeholder="请输入停机仓位" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>

      <!-- 备注信息 -->
      <el-card>
        <template #header>
          <span class="font-bold">备注信息</span>
        </template>
        <el-row :gutter="16">
          <el-col :span="24">
            <el-form-item label="备注信息" prop="remarks">
              <el-input v-model="formData.remarks" type="textarea" placeholder="请输入备注信息" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { ProductionDailyReportApi, ProductionDailyReport } from '@/api/coal/productiondailyreport'
import { getSimpleUserList, UserVO } from '@/api/system/user'

const { t } = useI18n()

/** 表单属性 */
const formRef = ref()
const formLoading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('')

/** 表单数据 */
const formData = ref({
  id: undefined,
  reportDate: '',
  startTime: '',
  stopTime: '',
  faultTimeHours: undefined,
  runningTimeHours: undefined,
  rawCoalInput: undefined,
  centralController: '',
  productionShiftLeader: '',
  dispatchDirector: '',
  shutdownReason: '',
  
  // 皮带秤及产量统计
  beltScale201RawCoalCurrentShift: undefined,
  beltScale701BlockCleanCurrentShift: undefined,
  beltScale701BlockCleanCurrentYield: undefined,
  beltScale702FineCleanCurrentShift: undefined,
  beltScale702FineCleanCurrentYield: undefined,
  filterPressSlimeCurrentShift: undefined,
  filterPressSlimeCurrentYield: undefined,
  gangue901CurrentShift: undefined,
  gangue901CurrentYield: undefined,
  commercialCoalTotalCurrentShift: undefined,
  commercialCoalTotalCurrentYield: undefined,
  
  // 煤质情况
  rawCoalAshContent: undefined,
  siloLargeLumpsAshContent: undefined,
  silo38LumpsAshContent: undefined,
  siloSeedCoalAshContent: undefined,
  siloFineCoalAshContent: undefined,
  slimeAshContent: undefined,
  gangueAshContent: undefined,
  
  // 生产设置参数
  shallowTrough305DensityMin: undefined,
  shallowTrough305DensityMax: undefined,
  cyclone321Pressure: undefined,
  tss333DensityMin: undefined,
  tss333DensityMax: undefined,
  
  // 生产耗材统计
  electricityConsumptionCurrentShift: undefined,
  wastewaterTreatmentReplenishmentWater: undefined,
  magnetitePowderCurrentShift: undefined,
  whiteReagentAnionicCurrentShift: undefined,
  yellowReagentCationicCurrentShift: undefined,
  
  // 筒仓仓位统计
  rawCoalSiloStartupPosition: undefined,
  rawCoalSiloShutdownPosition: undefined,
  largeLumpCoalSiloStartupPosition: undefined,
  largeLumpCoalSiloShutdownPosition: undefined,
  lump38CoalSiloStartupPosition: undefined,
  lump38CoalSiloShutdownPosition: undefined,
  seedCoalSiloStartupPosition: undefined,
  seedCoalSiloShutdownPosition: undefined,
  fineCoalSiloStartupPosition: undefined,
  fineCoalSiloShutdownPosition: undefined,
  
  remarks: ''
})

/** 表单校验 */
const formRules = reactive({
  reportDate: [{ required: true, message: '日期不能为空', trigger: 'blur' }],
  startTime: [{ required: true, message: '启车时间不能为空', trigger: 'blur' }],
  stopTime: [{ required: true, message: '停车时间不能为空', trigger: 'blur' }]
})

/** 用户列表 */
const userList = ref<UserVO[]>([])

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

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t(type === 'create' ? 'action.create' : 'action.update')
  resetForm()
  await loadUserList()
  
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

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    reportDate: '',
    startTime: '',
    stopTime: '',
    faultTimeHours: undefined,
    runningTimeHours: undefined,
    rawCoalInput: undefined,
    centralController: '',
    productionShiftLeader: '',
    dispatchDirector: '',
    shutdownReason: '',
    
    // 皮带秤及产量统计
    beltScale201RawCoalCurrentShift: undefined,
    beltScale701BlockCleanCurrentShift: undefined,
    beltScale701BlockCleanCurrentYield: undefined,
    beltScale702FineCleanCurrentShift: undefined,
    beltScale702FineCleanCurrentYield: undefined,
    filterPressSlimeCurrentShift: undefined,
    filterPressSlimeCurrentYield: undefined,
    gangue901CurrentShift: undefined,
    gangue901CurrentYield: undefined,
    commercialCoalTotalCurrentShift: undefined,
    commercialCoalTotalCurrentYield: undefined,
    
    // 煤质情况
    rawCoalAshContent: undefined,
    siloLargeLumpsAshContent: undefined,
    silo38LumpsAshContent: undefined,
    siloSeedCoalAshContent: undefined,
    siloFineCoalAshContent: undefined,
    slimeAshContent: undefined,
    gangueAshContent: undefined,
    
    // 生产设置参数
    shallowTrough305DensityMin: undefined,
    shallowTrough305DensityMax: undefined,
    cyclone321Pressure: undefined,
    tss333DensityMin: undefined,
    tss333DensityMax: undefined,
    
    // 生产耗材统计
    electricityConsumptionCurrentShift: undefined,
    wastewaterTreatmentReplenishmentWater: undefined,
    magnetitePowderCurrentShift: undefined,
    whiteReagentAnionicCurrentShift: undefined,
    yellowReagentCationicCurrentShift: undefined,
    
    // 筒仓仓位统计
    rawCoalSiloStartupPosition: undefined,
    rawCoalSiloShutdownPosition: undefined,
    largeLumpCoalSiloStartupPosition: undefined,
    largeLumpCoalSiloShutdownPosition: undefined,
    lump38CoalSiloStartupPosition: undefined,
    lump38CoalSiloShutdownPosition: undefined,
    seedCoalSiloStartupPosition: undefined,
    seedCoalSiloShutdownPosition: undefined,
    fineCoalSiloStartupPosition: undefined,
    fineCoalSiloShutdownPosition: undefined,
    
    remarks: ''
  }
  formRef.value?.resetFields()
}

/** 提交表单 */
const submitForm = async () => {
  // 校验表单
  if (!formRef.value) return
  const valid = await formRef.value.validate()
  if (!valid) return
  
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as ProductionDailyReport
    if (data.id) {
      await ProductionDailyReportApi.updateProductionDailyReport(data)
      ElMessage.success(t('common.updateSuccess'))
    } else {
      await ProductionDailyReportApi.createProductionDailyReport(data)
      ElMessage.success(t('common.createSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 定义 emit 事件 */
const emit = defineEmits(['success'])

/** 暴露给父组件的方法 */
defineExpose({ open })
</script>