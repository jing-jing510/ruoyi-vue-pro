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
      <el-form-item label="父ID" prop="parentId">
        <el-input
          v-model="queryParams.parentId"
          placeholder="请输入父ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="名称 " prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称 "
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入编码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="班次类型" prop="shiftType">
        <el-select
          v-model="queryParams.shiftType"
          placeholder="请选择班次类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SHIFT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['coal:shift-system:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:shift-system:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button type="danger" plain @click="toggleExpandAll">
          <Icon icon="ep:sort" class="mr-5px" /> 展开/折叠
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <div v-if="!loading && list.length === 0" class="text-center py-8 text-gray-500">
      暂无数据
    </div>
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      row-key="id"
      :default-expand-all="isExpandAll"
      v-if="refreshTable && list.length > 0"
    >
      <!-- <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="父ID" align="center" prop="parentId" /> -->
      <el-table-column label="名称 " align="center" prop="name" />
      <el-table-column label="编码" align="center" prop="code" />
      <el-table-column label="是否生产班制" align="center" prop="isProduction">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.IS_PRODUCTION" :value="scope.row.isProduction" />
        </template>
      </el-table-column>
      <el-table-column label="开始时间 " align="center" prop="startTime" />
      <el-table-column label="结束时间 " align="center" prop="endTime" />
      <el-table-column label="班次类型" align="center" prop="shiftType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.SHIFT_TYPE" :value="scope.row.shiftType" />
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
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
            v-hasPermi="['coal:shift-system:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:shift-system:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
<!--    <Pagination-->
<!--      :total="total"-->
<!--      v-model:page="queryParams.pageNo"-->
<!--      v-model:limit="queryParams.pageSize"-->
<!--      @pagination="getList"-->
<!--    />-->
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <ShiftSystemForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import { handleTree } from '@/utils/tree'
import download from '@/utils/download'
import { ShiftSystemApi, ShiftSystem } from '@/api/coal/shiftsystem'
import ShiftSystemForm from './ShiftSystemForm.vue'

/** 班制与班次设置 (树表) 列表 */
defineOptions({ name: 'ShiftSystem' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<ShiftSystem[]>([]) // 列表的数据
const queryParams = reactive({
  parentId: undefined,
  name: undefined,
  code: undefined,
  shiftType: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    // 清空查询参数，获取所有数据
    const params = {}
    console.log('查询参数:', params)
    const data = await ShiftSystemApi.getShiftSystemList(params)
    console.log('API返回的原始数据:', data)
    if (data && data.length > 0) {
      // 确保数据有正确的字段名
      console.log('第一条数据字段:', Object.keys(data[0]))
      console.log('第一条数据内容:', data[0])
      list.value = handleTree(data, 'id', 'parentId')
      console.log('处理后的树形数据:', list.value)
    } else {
      list.value = []
      console.log('没有数据返回')
    }
  } catch (error) {
    console.error('获取数据失败:', error)
    list.value = []
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
    await ShiftSystemApi.deleteShiftSystem(id)
    message.success(t('common.delSuccess'))
    let currentRow;
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}


/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await ShiftSystemApi.exportShiftSystem(queryParams)
    download.excel(data, '班制与班次设置 (树表).xls')
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
