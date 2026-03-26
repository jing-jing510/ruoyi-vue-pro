<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="日期" prop="reportDate">
        <el-date-picker
          v-model="queryParams.reportDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>

      <el-form-item label="启车时间" prop="startTime">
        <el-date-picker
          v-model="queryParams.startTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="停车时间" prop="stopTime">
        <el-date-picker
          v-model="queryParams.stopTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['coal:production-daily-report:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:production-daily-report:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:production-daily-report:delete']"
        >
          <Icon icon="ep:delete" class="mr-5px" /> 批量删除
        </el-button>
        <el-button
          type="primary"
          plain
          @click="openOnlineReport"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 在线填报
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table
        row-key="id"
        v-loading="loading"
        :data="list"
        :stripe="true"
        @selection-change="handleRowCheckboxChange"
    >
    <el-table-column type="selection" width="55" />
    <el-table-column
      label="日期"
      align="center"
      prop="reportDate"
      :formatter="dateFormatter2"
      width="120px"
    />
    <el-table-column label="集控员" align="center" prop="centralController" width="100px" />
    <el-table-column label="生产班长" align="center" prop="productionShiftLeader" width="100px" />
    <el-table-column label="调度主任" align="center" prop="dispatchDirector" width="100px" />
    <el-table-column
      label="启车时间"
      align="center"
      prop="startTime"
      :formatter="dateFormatter"
      width="120px"
    />
    <el-table-column
      label="停车时间"
      align="center"
      prop="stopTime"
      :formatter="dateFormatter"
      width="120px"
    />
    <el-table-column label="故障时间(h)" align="center" prop="faultTimeHours" width="100px" />
    <el-table-column label="运行时间(h)" align="center" prop="runningTimeHours" width="100px" />
    <el-table-column label="入洗原煤量(吨)" align="center" prop="rawCoalInput" width="120px" />
    
    <!-- 皮带秤及产量统计 -->
    <el-table-column label="201原煤皮带当班量(吨)" align="center" prop="beltScale201RawCoalCurrentShift" width="140px" />
    <el-table-column label="701块精煤当班量(吨)" align="center" prop="beltScale701BlockCleanCurrentShift" width="140px" />
    <el-table-column label="701块精煤当班产率(%)" align="center" prop="beltScale701BlockCleanCurrentYield" width="140px" />
    <el-table-column label="702末精煤当班量(吨)" align="center" prop="beltScale702FineCleanCurrentShift" width="140px" />
    <el-table-column label="702末精煤当班产率(%)" align="center" prop="beltScale702FineCleanCurrentYield" width="140px" />
    <el-table-column label="压滤煤泥当班量(吨)" align="center" prop="filterPressSlimeCurrentShift" width="140px" />
    <el-table-column label="压滤煤泥当班产率(%)" align="center" prop="filterPressSlimeCurrentYield" width="140px" />
    <el-table-column label="901矸石当班量(吨)" align="center" prop="gangue901CurrentShift" width="120px" />
    <el-table-column label="901矸石当班产率(%)" align="center" prop="gangue901CurrentYield" width="120px" />
    <el-table-column label="商品煤总重当班量(吨)" align="center" prop="commercialCoalTotalCurrentShift" width="140px" />
    <el-table-column label="商品煤总重当班产率(%)" align="center" prop="commercialCoalTotalCurrentYield" width="140px" />
    
    <!-- 煤质情况 -->
    <el-table-column label="原煤灰分(%)" align="center" prop="rawCoalAshContent" width="100px" />
    <el-table-column label="筒仓大块灰分(%)" align="center" prop="siloLargeLumpsAshContent" width="120px" />
    <el-table-column label="筒仓三八块灰分(%)" align="center" prop="silo38LumpsAshContent" width="120px" />
    <el-table-column label="筒仓籽煤灰分(%)" align="center" prop="siloSeedCoalAshContent" width="120px" />
    <el-table-column label="筒仓沫煤灰分(%)" align="center" prop="siloFineCoalAshContent" width="120px" />
    <el-table-column label="煤泥灰分(%)" align="center" prop="slimeAshContent" width="100px" />
    <el-table-column label="矸石灰分(%)" align="center" prop="gangueAshContent" width="100px" />
    
    <!-- 生产设置参数 -->
    <el-table-column label="305浅槽密度(g/cm³)" align="center" prop="shallowTrough305DensityMin" width="140px">
      <template #default="scope">
        {{ scope.row.shallowTrough305DensityMin ? `${scope.row.shallowTrough305DensityMin}-${scope.row.shallowTrough305DensityMax}` : '' }}
      </template>
    </el-table-column>
    <el-table-column label="321旋流器压力(Mpa)" align="center" prop="cyclone321Pressure" width="140px" />
    <el-table-column label="333TSS密度(g/cm³)" align="center" prop="tss333DensityMin" width="140px">
      <template #default="scope">
        {{ scope.row.tss333DensityMin ? `${scope.row.tss333DensityMin}-${scope.row.tss333DensityMax}` : '' }}
      </template>
    </el-table-column>
    
    <!-- 生产耗材统计 -->
    <el-table-column label="电耗损(kw*h)" align="center" prop="electricityConsumptionCurrentShift" width="120px" />
    <el-table-column label="污水处理站补水量(m³)" align="center" prop="wastewaterTreatmentReplenishmentWater" width="140px" />
    <el-table-column label="磁铁矿粉(吨)" align="center" prop="magnetitePowderCurrentShift" width="120px" />
    <el-table-column label="白药-阴离子(kg)" align="center" prop="whiteReagentAnionicCurrentShift" width="120px" />
    <el-table-column label="黄药-阳离子(kg)" align="center" prop="yellowReagentCationicCurrentShift" width="120px" />
    
    <!-- 筒仓仓位统计 -->
    <el-table-column label="原煤仓开机仓位(%)" align="center" prop="rawCoalSiloStartupPosition" width="120px" />
    <el-table-column label="原煤仓停机仓位(%)" align="center" prop="rawCoalSiloShutdownPosition" width="120px" />
    <el-table-column label="大块煤仓开机仓位(%)" align="center" prop="largeLumpCoalSiloStartupPosition" width="140px" />
    <el-table-column label="大块煤仓停机仓位(%)" align="center" prop="largeLumpCoalSiloShutdownPosition" width="140px" />
    <el-table-column label="三八块煤仓开机仓位(%)" align="center" prop="lump38CoalSiloStartupPosition" width="140px" />
    <el-table-column label="三八块煤仓停机仓位(%)" align="center" prop="lump38CoalSiloShutdownPosition" width="140px" />
    <el-table-column label="籽煤仓开机仓位(%)" align="center" prop="seedCoalSiloStartupPosition" width="120px" />
    <el-table-column label="籽煤仓停机仓位(%)" align="center" prop="seedCoalSiloShutdownPosition" width="120px" />
    <el-table-column label="沫煤仓开机仓位(%)" align="center" prop="fineCoalSiloStartupPosition" width="120px" />
    <el-table-column label="沫煤仓停机仓位(%)" align="center" prop="fineCoalSiloShutdownPosition" width="120px" />
    
    <el-table-column label="停车原因" align="center" prop="shutdownReason" width="200px" show-overflow-tooltip />
    <el-table-column label="备注信息" align="center" prop="remarks" width="150px" show-overflow-tooltip />
    <el-table-column
      label="创建时间"
      align="center"
      prop="createTime"
      :formatter="dateFormatter"
      width="120px"
    />
    <el-table-column label="操作" align="center" min-width="120px" fixed="right">
      <template #default="scope">
        <el-button
          link
          type="primary"
          @click="openForm('update', scope.row.id)"
          v-hasPermi="['coal:production-daily-report:update']"
        >
          编辑
        </el-button>
        <el-button
          link
          type="danger"
          @click="handleDelete(scope.row.id)"
          v-hasPermi="['coal:production-daily-report:delete']"
        >
          删除
        </el-button>
      </template>
    </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <ProductionDailyReportForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { isEmpty } from '@/utils/is'
import { dateFormatter,dateFormatter2 } from '@/utils/formatTime'
import download from '@/utils/download'
import { ProductionDailyReportApi, ProductionDailyReport } from '@/api/coal/productiondailyreport'
import ProductionDailyReportForm from './ProductionDailyReportForm.vue'
import { getSimpleUserList, UserVO } from '@/api/system/user'

/** 陕西神木朱盖塔选煤厂生产日报 列表 */
defineOptions({ name: 'ProductionDailyReport' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const userList = ref<UserVO[]>([]) // 用户列表
const loading = ref(true) // 列表的加载中
const list = ref<ProductionDailyReport[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  reportDate: [],
  startTime: [],
  stopTime: [],
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ProductionDailyReportApi.getProductionDailyReportPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await ProductionDailyReportApi.deleteProductionDailyReport(id)
    message.success(t('common.delSuccess'))
    let currentRow;
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除现场生产日报 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await ProductionDailyReportApi.deleteProductionDailyReportList(checkedIds.value);
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: ProductionDailyReport[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await ProductionDailyReportApi.exportProductionDailyReport(queryParams)
    download.excel(data, '现场生产日报.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
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
/** 在线填报按钮操作 */
const openOnlineReport = () => {
  const url = 'http://localhost:48080/jmreport/shareView/1126860765295665152?shareToken=a442709b1e418ae4ed50ae264e18a83d'
  window.open(url, '_blank')
}
/** 初始化 **/
onMounted(() => {
  getList()
  loadUserList()
})
</script>
