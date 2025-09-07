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
          type="primary"
          plain
          @click="openOnlineReport"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 在线填报
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
<!--      <el-table-column label="日报id" align="center" prop="id" />-->
      <el-table-column
        label="日期"
        align="center"
        prop="reportDate"
        :formatter="dateFormatter2"
        width="180px"
      />
<!--      <el-table-column label="班次ID" align="center" prop="shiftId" />-->
      <el-table-column label="集控员" align="center" prop="operatorId">
        <template #default="scope">
          {{ getUserNickname(scope.row.operatorId) }}
        </template>
      </el-table-column>
      <el-table-column label="带班主任" align="center" prop="shiftLeaderId">
        <template #default="scope">
          {{ getUserNickname(scope.row.shiftLeaderId) }}
        </template>
      </el-table-column>
      <el-table-column
        label="启车时间"
        align="center"
        prop="startTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="带煤时间(分钟)" align="center" prop="coalFeedingTime" />
      <el-table-column
        label="停车时间"
        align="center"
        prop="stopTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="有效带煤时间(分钟)" align="center" prop="effectiveFeedingTime" />
      <el-table-column label="故障影响时间(分钟)" align="center" prop="faultImpactTime" />
      <el-table-column label="入洗煤量(吨)" align="center" prop="rawCoalInput" />
      <el-table-column label="小时处理量(吨/小时)" align="center" prop="hourlyCapacity" />
      <el-table-column label="块精煤产量(吨)" align="center" prop="blockCleanCoalOutput" />
      <el-table-column label="末精煤产量(吨)" align="center" prop="fineCleanCoalOutput" />
      <el-table-column label="矸石产量(吨)" align="center" prop="gangueOutput" />
      <el-table-column label="中煤产量(吨)" align="center" prop="middlingCoalOutput" />
      <el-table-column label="压滤板次" align="center" prop="filterPressCycles" />
      <el-table-column label="压滤煤量(吨)" align="center" prop="filterPressCoalAmount" />
      <el-table-column label="滤布使用量(张)" align="center" prop="filterClothUsage" />
      <el-table-column label="放舱记录" align="center" prop="dischargeRecord" />
      <el-table-column label="挡板添加介质量(kg)" align="center" prop="baffleMediumAddition" />
      <el-table-column label="CaO量(kg)" align="center" prop="caoAmount" />
      <el-table-column label="絮凝剂(kg)" align="center" prop="flocculantAmount" />
      <el-table-column label="317密度(kg/L)" align="center" prop="densityMd317" />
      <el-table-column label="第一次块精煤灰分(%)" align="center" prop="firstAshBlockClean" />
      <el-table-column label="第一次末精煤灰分(%)" align="center" prop="firstAshFineClean" />
      <el-table-column label="第一次中煤灰分(%)" align="center" prop="firstAshMiddling" />
      <el-table-column label="第一次煤泥灰分(%)" align="center" prop="firstAshSlime" />
      <el-table-column label="第一次矸石灰分(%)" align="center" prop="firstAshGangue" />
      <el-table-column label="第二次块精煤灰分(%)" align="center" prop="secondAshBlockClean" />
      <el-table-column label="第二次末精煤灰分(%)" align="center" prop="secondAshFineClean" />
      <el-table-column label="第二次中煤灰分(%)" align="center" prop="secondAshMiddling" />
      <el-table-column label="第二次煤泥灰分(%)" align="center" prop="secondAshSlime" />
      <el-table-column label="第二次矸石灰分(%)" align="center" prop="secondAshGangue" />
      <el-table-column label="影响时间记录详情" align="center" prop="impactTimeRecord" />
      <el-table-column label="交办事项" align="center" prop="assignedTasks" />
      <el-table-column label="启车循环水池液位" align="center" prop="startCirculatingWaterPool" />
      <el-table-column label="启车清水桶液位" align="center" prop="startCleanWaterTank" />
      <el-table-column label="启车末煤仓位" align="center" prop="startFineCoalLevel" />
      <el-table-column label="启车粒煤仓位" align="center" prop="startGranularCoalLevel" />
      <el-table-column label="启车大块仓位" align="center" prop="startLargeBlockLevel" />
      <el-table-column label="启车中块仓位" align="center" prop="startMediumBlockLevel" />
      <el-table-column label="启车小块仓位" align="center" prop="startSmallBlockLevel" />
      <el-table-column label="启车中煤仓位" align="center" prop="startMiddlingCoalLevel" />
      <el-table-column label="启车矸石仓位" align="center" prop="startGangueLevel" />
      <el-table-column label="停车循环水池液位" align="center" prop="stopCirculatingWaterPool" />
      <el-table-column label="停车清水桶液位" align="center" prop="stopCleanWaterTank" />
      <el-table-column label="停车末煤仓位" align="center" prop="stopFineCoalLevel" />
      <el-table-column label="停车粒煤仓位" align="center" prop="stopGranularCoalLevel" />
      <el-table-column label="停车大块仓位" align="center" prop="stopLargeBlockLevel" />
      <el-table-column label="停车中块仓位" align="center" prop="stopMediumBlockLevel" />
      <el-table-column label="停车小块仓位" align="center" prop="stopSmallBlockLevel" />
      <el-table-column label="停车中煤仓位" align="center" prop="stopMiddlingCoalLevel" />
      <el-table-column label="停车矸石仓位" align="center" prop="stopGangueLevel" />
      <el-table-column label="备注信息" align="center" prop="remarks" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" min-width="120px">
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
/** 现场生产日报 列表 */
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
  operatorId: undefined,
  shiftLeaderId: undefined,
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

/** 根据用户ID获取用户昵称 */
const getUserNickname = (userId: number | undefined) => {
  if (!userId) return ''
  const user = userList.value.find(u => u.id === userId)
  return user ? user.nickname : `用户${userId}`
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
  const url = 'http://localhost:48080/jmreport/shareView/1120595319747600384?shareToken=39303adec4dd28332e4ce1f063e17be7'
  window.open(url, '_blank')
}
/** 初始化 **/
onMounted(() => {
  getList()
  loadUserList()
})
</script>
