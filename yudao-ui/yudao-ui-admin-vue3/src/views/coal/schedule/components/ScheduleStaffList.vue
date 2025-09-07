<template>
  <!-- 列表 -->
  <ContentWrap>
    <el-button
      type="primary"
      plain
      @click="openForm('create')"
      v-hasPermi="['coal:schedule:create']"
    >
      <Icon icon="ep:plus" class="mr-5px" /> 新增
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
    <el-table
        row-key="id"
        v-loading="loading"
        :data="list"
        :stripe="true"
        :show-overflow-tooltip="true"
        @selection-change="handleRowCheckboxChange"
    >
          <el-table-column type="selection" width="55" />
      <el-table-column label="人员安排ID" align="center" prop="id" />
       <el-table-column label="班次名称" align="center" prop="shiftId">
         <template #default="scope">
           {{ getShiftName(scope.row.shiftId) }}
         </template>
       </el-table-column>
      <el-table-column label="员工姓名" align="center" prop="userId">
        <template #default="scope">
          {{ getUserNickname(scope.row.userId) }}
        </template>
      </el-table-column>
      <el-table-column label="岗位类型" align="center" prop="positionType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.POSITION_TYPE" :value="scope.row.positionType" />
        </template>
      </el-table-column>
      <el-table-column label="是否班组长" align="center" prop="isLeader">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.IS_LEADER" :value="scope.row.isLeader ? 1 : 0" />
        </template>
      </el-table-column>
      <el-table-column label="是否替班" align="center" prop="isSubstitute">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.IS_SUBSTITUTE" :value="scope.row.isSubstitute ? 1 : 0" />
        </template>
      </el-table-column>
      <el-table-column label="替班原因" align="center" prop="substituteReason" />
      <el-table-column label="工作状态" align="center" prop="workStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.WORK_STATUS" :value="scope.row.workStatus" />
        </template>
      </el-table-column>
      <el-table-column label="联系电话" align="center" prop="contactPhone" />
      <el-table-column label="紧急联系人" align="center" prop="emergencyContact" />
      <el-table-column label="紧急联系电话" align="center" prop="emergencyPhone" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center">
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
    <ScheduleStaffForm ref="formRef" @success="getList" />
</template>
<script setup lang="ts">
import { DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import { isEmpty } from '@/utils/is'
import { ScheduleStaffApi, ScheduleStaff } from '@/api/coal/schedule'
import { getSimpleUserList, UserVO } from '@/api/system/user'
import { ShiftSystemApi, ShiftSystem } from '@/api/coal/shiftsystem'
import ScheduleStaffForm from './ScheduleStaffForm.vue'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const props = defineProps<{
  scheduleId?: number // 排班ID (关联主表)（主表的关联字段）
  shiftSystemId?: number // 班制ID，用于过滤班次选择
}>()
const loading = ref(false) // 列表的加载中
const list = ref([]) // 列表的数据
const total = ref(0) // 列表的总页数
const userList = ref<UserVO[]>([]) // 用户列表
const shiftList = ref<ShiftSystem[]>([]) // 班次列表
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  scheduleId: undefined as unknown
})

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

/** 根据用户ID获取用户昵称 */
const getUserNickname = (userId: number) => {
  if (!userId) return ''
  const user = userList.value.find(u => u.id === userId)
  return user ? user.nickname : `用户ID:${userId}`
}

/** 根据班次ID获取班次名称 */
const getShiftName = (shiftId: number) => {
  if (!shiftId) return ''
  const shift = shiftList.value.find(s => s.id === shiftId)
  return shift ? shift.name : `班次ID:${shiftId}`
}

/** 监听主表的关联字段的变化，加载对应的子表数据 */
watch(
  () => props.scheduleId,
  (val: number) => {
    if (!val) {
      list.value = [] // 清空列表
      return
    }
    queryParams.scheduleId = val
    handleQuery()
  },
    { immediate: true, deep: true }
)

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ScheduleStaffApi.getScheduleStaffPage(queryParams)
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

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  if (!props.scheduleId) {
    message.error('请选择一个排班管理 (主表)')
    return
  }
  formRef.value.open(type, id, props.scheduleId, props.shiftSystemId)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await ScheduleStaffApi.deleteScheduleStaff(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除人员安排 (子表) */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await ScheduleStaffApi.deleteScheduleStaffList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: ScheduleStaff[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 初始化 */
onMounted(() => {
  initData()
})
</script>