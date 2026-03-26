<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="90%">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      v-loading="formLoading"
      label-width="0"
    >
      <!-- 标题 -->
      <div class="form-title">红林选煤厂-调度室(生产记录表)</div>
      
      <!-- 表格样式布局 -->
      <table class="production-table">
        <!-- 第一行 -->
        <tr>
          <td class="label-cell">日期</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="reportDate">
              <el-date-picker
                v-model="formData.reportDate"
                type="date"
                value-format="x"
                placeholder="选择日期"
                style="width: 100%"
              />
            </el-form-item>
          </td>
          <td class="label-cell">集控员</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="operatorId">
              <el-select v-model="formData.operatorId" placeholder="请选择" clearable filterable style="width: 100%">
                <el-option
                  v-for="user in userList"
                  :key="user.id"
                  :label="user.nickname"
                  :value="user.id"
                />
              </el-select>
            </el-form-item>
          </td>
          <td class="label-cell">带班主任/班长</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="shiftLeaderId">
              <el-select v-model="formData.shiftLeaderId" placeholder="请选择" clearable filterable style="width: 100%">
                <el-option
                  v-for="user in userList"
                  :key="user.id"
                  :label="user.nickname"
                  :value="user.id"
                />
              </el-select>
            </el-form-item>
          </td>
        </tr>
        
        <!-- 第二行 -->
        <tr>
          <td class="label-cell">启车时间</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="startTime">
              <el-date-picker
                v-model="formData.startTime"
                type="datetime"
                value-format="x"
                placeholder="选择时间"
                style="width: 100%"
              />
            </el-form-item>
          </td>
          <td class="label-cell">带煤时间</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="coalFeedingTime">
              <el-input v-model="formData.coalFeedingTime" placeholder="请输入" />
            </el-form-item>
          </td>
          <td class="label-cell">停车时间</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="stopTime">
              <el-date-picker
                v-model="formData.stopTime"
                type="datetime"
                value-format="x"
                placeholder="选择时间"
                style="width: 100%"
              />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 第三行 -->
        <tr>
          <td class="label-cell">入洗煤量</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="rawCoalInput">
              <el-input v-model="formData.rawCoalInput" placeholder="请输入" />
            </el-form-item>
          </td>
          <td class="label-cell">小时处理量</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="hourlyCapacity">
              <el-input v-model="formData.hourlyCapacity" placeholder="请输入" />
            </el-form-item>
          </td>
          <td class="label-cell">故障影响时间</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="faultImpactTime">
              <el-input v-model="formData.faultImpactTime" placeholder="请输入" />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 第四行 -->
        <tr>
          <td class="label-cell">块精煤产量</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="blockCleanCoalOutput">
              <el-input v-model="formData.blockCleanCoalOutput" placeholder="请输入" />
            </el-form-item>
          </td>
          <td class="label-cell">末精煤产量</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="fineCleanCoalOutput">
              <el-input v-model="formData.fineCleanCoalOutput" placeholder="请输入" />
            </el-form-item>
          </td>
          <td class="label-cell">矸石产量</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="gangueOutput">
              <el-input v-model="formData.gangueOutput" placeholder="请输入" />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 第五行 -->
        <tr>
          <td class="label-cell">中煤产量</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="middlingCoalOutput">
              <el-input v-model="formData.middlingCoalOutput" placeholder="请输入" />
            </el-form-item>
          </td>
          <td class="label-cell">压滤</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="filterPressCycles">
              <el-input v-model="formData.filterPressCycles" placeholder="板次" />
            </el-form-item>
          </td>
          <td class="label-cell">滤布使用量</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="filterClothUsage">
              <el-input v-model="formData.filterClothUsage" placeholder="张" />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 第六行 -->
        <tr>
          <td class="label-cell">有效带煤时间</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="effectiveFeedingTime">
              <el-input v-model="formData.effectiveFeedingTime" placeholder="请输入" />
            </el-form-item>
          </td>
          <td class="label-cell">放舱记录</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="dischargeRecord">
              <el-input v-model="formData.dischargeRecord" placeholder="请输入" />
            </el-form-item>
          </td>
          <td class="label-cell">当班添加介质量</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="baffleMediumAddition">
              <el-input v-model="formData.baffleMediumAddition" placeholder="kg" />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 第七行 -->
        <tr>
          <td class="label-cell">CaO:</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="caoAmount">
              <el-input v-model="formData.caoAmount" placeholder="kg" />
            </el-form-item>
          </td>
          <td class="label-cell">絮凝剂:</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="flocculantAmount">
              <el-input v-model="formData.flocculantAmount" placeholder="kg" />
            </el-form-item>
          </td>
          <td class="label-cell">317密度:</td>
          <td class="input-cell" colspan="2">
            <el-form-item prop="densityMd317">
              <el-input v-model="formData.densityMd317" placeholder="kg/L" />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 第一次灰分 -->
        <tr>
          <td class="label-cell" rowspan="2">第一次灰分</td>
          <td class="label-cell-small">块精煤:</td>
          <td class="input-cell-small">
            <el-form-item prop="firstAshBlockClean">
              <el-input v-model="formData.firstAshBlockClean" />
            </el-form-item>
          </td>
          <td class="label-cell-small">末精煤:</td>
          <td class="input-cell-small">
            <el-form-item prop="firstAshFineClean">
              <el-input v-model="formData.firstAshFineClean" />
            </el-form-item>
          </td>
          <td class="label-cell-small">中煤:</td>
          <td class="input-cell-small">
            <el-form-item prop="firstAshMiddling">
              <el-input v-model="formData.firstAshMiddling" />
            </el-form-item>
          </td>
          <td class="label-cell-small">煤泥:</td>
          <td class="input-cell-small">
            <el-form-item prop="firstAshSlime">
              <el-input v-model="formData.firstAshSlime" />
            </el-form-item>
          </td>
          <td class="label-cell-small">矸石:</td>
          <td class="input-cell-small">
            <el-form-item prop="firstAshGangue">
              <el-input v-model="formData.firstAshGangue" />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 第二次灰分 -->
        <tr>
          <td class="label-cell-small">块精煤:</td>
          <td class="input-cell-small">
            <el-form-item prop="secondAshBlockClean">
              <el-input v-model="formData.secondAshBlockClean" />
            </el-form-item>
          </td>
          <td class="label-cell-small">末精煤:</td>
          <td class="input-cell-small">
            <el-form-item prop="secondAshFineClean">
              <el-input v-model="formData.secondAshFineClean" />
            </el-form-item>
          </td>
          <td class="label-cell-small">中煤:</td>
          <td class="input-cell-small">
            <el-form-item prop="secondAshMiddling">
              <el-input v-model="formData.secondAshMiddling" />
            </el-form-item>
          </td>
          <td class="label-cell-small">煤泥:</td>
          <td class="input-cell-small">
            <el-form-item prop="secondAshSlime">
              <el-input v-model="formData.secondAshSlime" />
            </el-form-item>
          </td>
          <td class="label-cell-small">矸石:</td>
          <td class="input-cell-small">
            <el-form-item prop="secondAshGangue">
              <el-input v-model="formData.secondAshGangue" />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 影响时间记录 -->
        <tr>
          <td class="label-cell">影响时间记录</td>
          <td colspan="10" class="input-cell">
            <el-form-item prop="impactTimeRecord">
              <el-input 
                v-model="formData.impactTimeRecord" 
                type="textarea" 
                :rows="4"
                placeholder="请输入影响时间记录详情"
              />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 机械巡检记录 -->
        <tr>
          <td class="label-cell" rowspan="2">机械巡检记录</td>
          <td class="label-cell-small">机械班长:</td>
          <td colspan="2" class="input-cell">
            <el-form-item prop="mechanicalInspector">
              <el-input v-model="formData.mechanicalInspector" placeholder="巡检人" />
            </el-form-item>
          </td>
          <td colspan="7"></td>
        </tr>
        <tr>
          <td class="label-cell-small">巡检时间</td>
          <td colspan="9" class="input-cell">
            <el-form-item prop="inspectionTime">
              <el-date-picker
                v-model="formData.inspectionTime"
                type="datetime"
                value-format="x"
                placeholder="选择巡检时间"
                style="width: 100%"
              />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 交办事项 -->
        <tr>
          <td class="label-cell">交办事项</td>
          <td colspan="10" class="input-cell">
            <el-form-item prop="assignedTasks">
              <el-input 
                v-model="formData.assignedTasks" 
                type="textarea" 
                :rows="4"
                placeholder="请输入交办事项"
              />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 启车仓位 -->
        <tr>
          <td class="label-cell" rowspan="2">启车</td>
          <td class="label-cell-small">循环水池</td>
          <td class="input-cell-small">
            <el-form-item prop="startCirculatingWaterPool">
              <el-input v-model="formData.startCirculatingWaterPool" />
            </el-form-item>
          </td>
          <td class="label-cell-small">清水桶</td>
          <td class="input-cell-small">
            <el-form-item prop="startCleanWaterTank">
              <el-input v-model="formData.startCleanWaterTank" />
            </el-form-item>
          </td>
          <td class="label-cell-small">末煤</td>
          <td class="input-cell-small">
            <el-form-item prop="startFineCoalLevel">
              <el-input v-model="formData.startFineCoalLevel" />
            </el-form-item>
          </td>
          <td class="label-cell-small">粒煤</td>
          <td class="input-cell-small">
            <el-form-item prop="startGranularCoalLevel">
              <el-input v-model="formData.startGranularCoalLevel" />
            </el-form-item>
          </td>
          <td class="label-cell-small">大块</td>
          <td class="input-cell-small">
            <el-form-item prop="startLargeBlockLevel">
              <el-input v-model="formData.startLargeBlockLevel" />
            </el-form-item>
          </td>
        </tr>
        <tr>
          <td class="label-cell-small">中块</td>
          <td class="input-cell-small">
            <el-form-item prop="startMediumBlockLevel">
              <el-input v-model="formData.startMediumBlockLevel" />
            </el-form-item>
          </td>
          <td class="label-cell-small">小块</td>
          <td class="input-cell-small">
            <el-form-item prop="startSmallBlockLevel">
              <el-input v-model="formData.startSmallBlockLevel" />
            </el-form-item>
          </td>
          <td class="label-cell-small">中煤</td>
          <td class="input-cell-small">
            <el-form-item prop="startMiddlingCoalLevel">
              <el-input v-model="formData.startMiddlingCoalLevel" />
            </el-form-item>
          </td>
          <td class="label-cell-small">矸石</td>
          <td class="input-cell-small">
            <el-form-item prop="startGangueLevel">
              <el-input v-model="formData.startGangueLevel" />
            </el-form-item>
          </td>
          <td colspan="2"></td>
        </tr>
        
        <!-- 停车仓位 -->
        <tr>
          <td class="label-cell" rowspan="2">停车</td>
          <td class="label-cell-small">循环水池</td>
          <td class="input-cell-small">
            <el-form-item prop="stopCirculatingWaterPool">
              <el-input v-model="formData.stopCirculatingWaterPool" />
            </el-form-item>
          </td>
          <td class="label-cell-small">清水桶</td>
          <td class="input-cell-small">
            <el-form-item prop="stopCleanWaterTank">
              <el-input v-model="formData.stopCleanWaterTank" />
            </el-form-item>
          </td>
          <td class="label-cell-small">末煤</td>
          <td class="input-cell-small">
            <el-form-item prop="stopFineCoalLevel">
              <el-input v-model="formData.stopFineCoalLevel" />
            </el-form-item>
          </td>
          <td class="label-cell-small">粒煤</td>
          <td class="input-cell-small">
            <el-form-item prop="stopGranularCoalLevel">
              <el-input v-model="formData.stopGranularCoalLevel" />
            </el-form-item>
          </td>
          <td class="label-cell-small">大块</td>
          <td class="input-cell-small">
            <el-form-item prop="stopLargeBlockLevel">
              <el-input v-model="formData.stopLargeBlockLevel" />
            </el-form-item>
          </td>
        </tr>
        <tr>
          <td class="label-cell-small">中块</td>
          <td class="input-cell-small">
            <el-form-item prop="stopMediumBlockLevel">
              <el-input v-model="formData.stopMediumBlockLevel" />
            </el-form-item>
          </td>
          <td class="label-cell-small">小块</td>
          <td class="input-cell-small">
            <el-form-item prop="stopSmallBlockLevel">
              <el-input v-model="formData.stopSmallBlockLevel" />
            </el-form-item>
          </td>
          <td class="label-cell-small">中煤</td>
          <td class="input-cell-small">
            <el-form-item prop="stopMiddlingCoalLevel">
              <el-input v-model="formData.stopMiddlingCoalLevel" />
            </el-form-item>
          </td>
          <td class="label-cell-small">矸石</td>
          <td class="input-cell-small">
            <el-form-item prop="stopGangueLevel">
              <el-input v-model="formData.stopGangueLevel" />
            </el-form-item>
          </td>
          <td colspan="2"></td>
        </tr>
        
        <!-- 备注 -->
        <tr>
          <td class="label-cell">备注</td>
          <td colspan="10" class="input-cell">
            <el-form-item prop="remarks">
              <el-input 
                v-model="formData.remarks" 
                type="textarea" 
                :rows="3"
                placeholder="请输入备注信息"
              />
            </el-form-item>
          </td>
        </tr>
        
        <!-- 签名 -->
        <tr>
          <td colspan="5" class="signature-cell">
            <span>调度值长</span>
            <el-form-item prop="reservedField1" style="display: inline-block; margin-left: 20px;">
              <el-input v-model="formData.reservedField1" placeholder="签名" style="width: 150px" />
            </el-form-item>
          </td>
          <td colspan="6" class="signature-cell">
            <span>副值长</span>
            <el-form-item prop="reservedField2" style="display: inline-block; margin-left: 20px;">
              <el-input v-model="formData.reservedField2" placeholder="签名" style="width: 150px" />
            </el-form-item>
          </td>
        </tr>
      </table>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { HLProductionDailyReportApi, HLProductionDailyReport } from '@/api/coal/HLproductiondailyreport'
import { getSimpleUserList, UserVO } from '@/api/system/user'

/** 红林生产日报 表单 */
defineOptions({ name: 'HLProductionDailyReportForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const userList = ref<UserVO[]>([]) // 用户列表

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
  mechanicalInspector: undefined,
  inspectionTime: undefined,
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
  reservedField1: undefined,
  reservedField2: undefined,
})

const formRules = reactive({
  reportDate: [{ required: true, message: '日期不能为空', trigger: 'blur' }],
})

const formRef = ref() // 表单 Ref

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
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  await loadUserList()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await HLProductionDailyReportApi.getHLProductionDailyReport(id)
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
    const data = formData.value as unknown as HLProductionDailyReport
    if (formType.value === 'create') {
      await HLProductionDailyReportApi.createHLProductionDailyReport(data)
      message.success(t('common.createSuccess'))
    } else {
      await HLProductionDailyReportApi.updateHLProductionDailyReport(data)
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
    mechanicalInspector: undefined,
    inspectionTime: undefined,
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
    reservedField1: undefined,
    reservedField2: undefined,
  }
  formRef.value?.resetFields()
}
</script>

<style scoped lang="scss">
.form-title {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 20px;
  padding: 10px 0;
}

.production-table {
  width: 100%;
  border-collapse: collapse;
  border: 1px solid #dcdfe6;
  table-layout: fixed;
  
  tr {
    border: 1px solid #dcdfe6;
  }
  
  td {
    border: 1px solid #dcdfe6;
    padding: 8px;
    vertical-align: middle;
  }
  
  .label-cell {
    background-color: #f5f7fa;
    font-weight: 500;
    text-align: center;
    width: 100px;
    min-width: 100px;
    color: #606266;
    white-space: nowrap;
  }
  
  .label-cell-small {
    background-color: #f5f7fa;
    font-weight: 500;
    text-align: center;
    width: 70px;
    min-width: 70px;
    font-size: 13px;
    color: #606266;
    white-space: nowrap;
  }
  
  .input-cell {
    padding: 4px 8px;
    min-width: 150px;
    
    :deep(.el-form-item) {
      margin-bottom: 0;
    }
    
    :deep(.el-form-item__error) {
      position: relative;
      top: 0;
      left: 0;
      padding-top: 2px;
    }
  }
  
  .input-cell-small {
    padding: 2px 4px;
    
    :deep(.el-form-item) {
      margin-bottom: 0;
    }
    
    :deep(.el-input__wrapper) {
      padding: 1px 8px;
    }
    
    :deep(.el-input__inner) {
      font-size: 13px;
    }
  }
  
  .signature-cell {
    text-align: center;
    padding: 15px;
    
    span {
      font-weight: 500;
      color: #606266;
    }
    
    :deep(.el-form-item) {
      margin-bottom: 0;
    }
  }
}
</style>
