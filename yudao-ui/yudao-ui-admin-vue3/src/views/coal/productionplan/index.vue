<template>
  <ContentWrap>
    <!-- 操作提示 -->
    <el-alert
      title="搜索提示：对于“一键生成”的计划，建议使用“计划名称”进行精确搜索。搜索格式示例：'2025年-12月-31日-晚班' 或 '2025年-12月-31日'。"
      type="success"
      show-icon
      :closable="true"
      style="margin-bottom: 20px; border:1px green solid;"
    />
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="计划名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入计划名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
<!--      <el-form-item label="父计划ID" prop="parentId">-->
<!--        <el-input-->
<!--          v-model="queryParams.parentId"-->
<!--          placeholder="请输入父计划ID"-->
<!--          clearable-->
<!--          @keyup.enter="handleQuery"-->
<!--          class="!w-240px"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="计划类型" prop="planType">
        <el-select
          v-model="queryParams.planType"
          placeholder="请选择计划类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.PLAN_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="计划年度" prop="planYear">
        <el-input
          v-model="queryParams.planYear"
          placeholder="请输入计划年度"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="计划月份" prop="planMonth">
        <el-input
          v-model="queryParams.planMonth"
          placeholder="请输入计划月份"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="计划日期" prop="planDate">
        <el-date-picker
          v-model="queryParams.planDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="计划状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择计划状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.PLAN_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['coal:production-plan:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:production-plan:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
          type="primary"
          @click="openForm('generate')"
          v-hasPermi="['coal:production-plan:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 一键生成年度计划
        </el-button>
        <el-button
          type="danger"
          plain
          @click="handleDeleteByYear"
          v-hasPermi="['coal:production-plan:delete']"
        >
          <Icon icon="ep:delete" class="mr-5px" /> 按年删除
        </el-button>
        <el-button type="danger" plain @click="toggleExpandAll">
          <Icon icon="ep:sort" class="mr-5px" /> 展开/折叠
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      row-key="id"
      :default-expand-all="isExpandAll"
      v-if="refreshTable"
    >
      <!--      <el-table-column label="计划ID" align="center" prop="id" />-->
      <el-table-column label="计划名称" prop="name" min-width="250" />
      <!--      <el-table-column label="父计划ID" align="center" prop="parentId" />-->
      <el-table-column label="计划类型" align="center" prop="planType" min-width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.PLAN_TYPE" :value="scope.row.planType" />
        </template>
      </el-table-column>
      <el-table-column label="计划年度" align="center" prop="planYear" min-width="100" />
      <el-table-column label="计划月份" align="center" prop="planMonth" min-width="100" />
      <el-table-column label="计划日期" align="center" prop="planDate"  min-width="180" :formatter="dateFormatter" />
      <el-table-column label="计划状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.PLAN_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="计划入洗原煤量(吨)" align="center" prop="rawCoalPlan" />
      <el-table-column label="计划末煤产量(吨)" align="center" prop="fineCoalPlan" />
      <el-table-column label="计划粒煤产量(吨)" align="center" prop="granularCoalPlan" />
      <el-table-column label="计划大块煤产量(吨)" align="center" prop="largeBlockCoalPlan" />
      <el-table-column label="计划中块煤产量(吨)" align="center" prop="mediumBlockCoalPlan" />
      <el-table-column label="计划小块煤产量(吨)" align="center" prop="smallBlockCoalPlan" />
      <el-table-column label="计划中煤产量(吨)" align="center" prop="middlingCoalPlan" />
      <el-table-column label="计划煤泥产量(吨)" align="center" prop="slimePlan" />
      <el-table-column label="计划矸石产量(吨)" align="center" prop="ganguePlan" />
      <el-table-column label="末煤灰分(%)" align="center" prop="fineCoalAsh" />
      <el-table-column label="粒煤灰分(%)" align="center" prop="granularCoalAsh" />
      <el-table-column label="大块煤灰分(%)" align="center" prop="largeBlockCoalAsh" />
      <el-table-column label="中块煤灰分(%)" align="center" prop="mediumBlockCoalAsh" />
      <el-table-column label="小块煤灰分(%)" align="center" prop="smallBlockCoalAsh" />
      <el-table-column label="中煤灰分(%)" align="center" prop="middlingCoalAsh" />
<!--      <el-table-column label="制定人ID" align="center" prop="creatorId" />-->
<!--      <el-table-column label="审批人ID" align="center" prop="approverId" />-->
<!--      <el-table-column-->
<!--        label="审批时间"-->
<!--        align="center"-->
<!--        prop="approveTime"-->
<!--        :formatter="dateFormatter"-->
<!--        width="180px"-->
<!--      />-->
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
            v-hasPermi="['coal:production-plan:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:production-plan:delete']"
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
  <ProductionPlanForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
// import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import { handleTree } from '@/utils/tree'
import download from '@/utils/download'
import { ProductionPlanApi, ProductionPlan } from '@/api/coal/productionplan'
import ProductionPlanForm from './ProductionPlanForm.vue'
import {ElMessageBox} from "element-plus";

/** 生产计划 列表 */
defineOptions({ name: 'ProductionPlan' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const total = ref(0) // 列表的总页数
const loading = ref(true) // 列表的加载中
const list = ref<ProductionPlan[]>([]) // 列表的数据
const queryParams = reactive({
  name: undefined,
  parentId: undefined,
  planType: undefined,
  planYear: undefined,
  planMonth: undefined,
  planDate: [],
  status: undefined,
  createTime: [],
  pageNo:1,
  pageSize:100
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ProductionPlanApi.getProductionPlanList(queryParams)
    list.value = handleTree(data, 'id', 'parentId')
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
    await ProductionPlanApi.deleteProductionPlan(id)
    message.success(t('common.delSuccess'))
    let currentRow;
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 按年份删除按钮操作 */
const handleDeleteByYear = async () => {
  try {
    const { value } = await ElMessageBox.prompt('请输入要删除的年份', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputPattern: /^\d{4}$/,
      inputErrorMessage: '请输入有效的四位年份'
    })
    if (value) {
      await message.delConfirm('确认要删除 ' + value + ' 年的所有计划数据吗？')
      await ProductionPlanApi.deleteProductionPlanByYear(Number(value))
      message.success(t('common.delSuccess'))
      await getList()
    }
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await ProductionPlanApi.exportProductionPlan(queryParams)
    download.excel(data, '生产计划.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 展开/折叠操作 */
const isExpandAll = ref(true) // 是否展开，默认全部展开
const refreshTable = ref(true) // 重新渲染表格状态
const toggleExpandAll = async () => {
  refreshTable.value = false
  isExpandAll.value = !isExpandAll.value
  await nextTick()
  refreshTable.value = true
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
