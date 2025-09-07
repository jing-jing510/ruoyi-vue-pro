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
      <el-form-item label="排班日期" prop="scheduleDate">
        <el-date-picker
          v-model="queryParams.scheduleDate"
          value-format="YYYY-MM-DD"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="班制ID" prop="shiftSystemId">
        <el-input
          v-model="queryParams.shiftSystemId"
          placeholder="请输入班制ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="排班状态" prop="scheduleStatus">
        <el-select
          v-model="queryParams.scheduleStatus"
          placeholder="请选择排班状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SCHEDULE_STATUS)"
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
          v-hasPermi="['coal:schedule:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:schedule:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:schedule:delete']"
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
      :show-overflow-tooltip="true"
      highlight-current-row
      @current-change="handleCurrentChange"
      @selection-change="handleRowCheckboxChange"
    >
    <el-table-column type="selection" width="55" />
      <el-table-column label="排班ID" align="center" prop="id" />
      <el-table-column label="排班日期" align="center" prop="scheduleDate" />
      <el-table-column label="班制名称" align="center" prop="shiftSystemId">
        <template #default="scope">
          {{ getShiftSystemName(scope.row.shiftSystemId) }}
        </template>
      </el-table-column>
      <el-table-column label="排班状态" align="center" prop="scheduleStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.SCHEDULE_STATUS" :value="scope.row.scheduleStatus" />
        </template>
      </el-table-column>
      <el-table-column label="是否生产日" align="center" prop="isProductionDay">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.IS_PRODUCTION_DAY" :value="scope.row.isProductionDay ? 1 : 0" />
        </template>
      </el-table-column>
      <el-table-column label="当日生产目标(吨)" align="center" prop="productionTarget" />
      <el-table-column label="排班类型" align="center" prop="scheduleType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.SCHEDULE_TYPE" :value="scope.row.scheduleType" />
        </template>
      </el-table-column>
      <el-table-column label="审批人" align="center" prop="approverId">
        <template #default="scope">
          {{ getUserNickname(scope.row.approverId) }}
        </template>
      </el-table-column>
      <el-table-column
        label="审批时间"
        align="center"
        prop="approveTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="备注" align="center" prop="remark" />
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
            v-hasPermi="['coal:schedule:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:schedule:delete']"
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
  <ScheduleForm ref="formRef" @success="getList" />
  <!-- 子表的列表 -->
  <ContentWrap>
    <el-tabs model-value="scheduleStaff">
      <el-tab-pane label="人员安排 (子表)" name="scheduleStaff">
        <ScheduleStaffList :schedule-id="currentRow.id" :shift-system-id="currentRow.shiftSystemId" />
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { ScheduleApi, Schedule } from '@/api/coal/schedule'
import { getSimpleUserList, UserVO } from '@/api/system/user'
import { ShiftSystemApi, ShiftSystem } from '@/api/coal/shiftsystem'
import ScheduleForm from './ScheduleForm.vue'
import ScheduleStaffList from './components/ScheduleStaffList.vue'

/** 排班管理 (主表) 列表 */
defineOptions({ name: 'Schedule' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<Schedule[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const userList = ref<UserVO[]>([]) // 用户列表
const shiftSystemList = ref<ShiftSystem[]>([]) // 班制列表
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  scheduleDate: [],
  shiftSystemId: undefined,
  isProductionDay: undefined,
  scheduleStatus: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

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

/** 根据用户ID获取用户昵称 */
const getUserNickname = (userId: number) => {
  if (!userId) return ''
  const user = userList.value.find(u => u.id === userId)
  return user ? user.nickname : `用户ID:${userId}`
}

/** 根据班制ID获取班制名称 */
const getShiftSystemName = (shiftSystemId: number) => {
  if (!shiftSystemId) return ''
  const shiftSystem = shiftSystemList.value.find(s => s.id === shiftSystemId)
  return shiftSystem ? shiftSystem.name : `班制ID:${shiftSystemId}`
}

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ScheduleApi.getSchedulePage(queryParams)
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
    await ScheduleApi.deleteSchedule(id)
    message.success(t('common.delSuccess'))
         currentRow.value = {} as Schedule
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除排班管理 (主表) */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await ScheduleApi.deleteScheduleList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: Schedule[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await ScheduleApi.exportSchedule(queryParams)
    download.excel(data, '排班管理 (主表).xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 选中行操作 */
const currentRow = ref<Schedule>({} as Schedule) // 选中行
const handleCurrentChange = (row: Schedule) => {
  currentRow.value = row
}

/** 初始化 **/
onMounted(() => {
  initData() // 初始化基础数据
  getList()
})
</script>
