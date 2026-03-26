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
      <el-form-item label="备件" prop="sparePartId">
        <el-select
          v-model="queryParams.sparePartId"
          placeholder="请选择备件"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="item in sparePartList"
            :key="item.id"
            :label="item.sparePartName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="设备" prop="equipmentId">
        <el-select
          v-model="queryParams.equipmentId"
          placeholder="请选择设备"
          clearable
          filterable
          class="!w-240px"
        >
          <el-option
            v-for="item in equipmentList"
            :key="item.id"
            :label="item.equipmentName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否必需" prop="isRequired">
        <el-select
          v-model="queryParams.isRequired"
          placeholder="请选择是否必需"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.IS_REQUIRED)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="更换难度" prop="replacementDifficulty">
        <el-select
          v-model="queryParams.replacementDifficulty"
          placeholder="请选择更换难度"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.REPLACEMENT_DIFFICULTY)"
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
          v-hasPermi="['coal:spare-part-equipment:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:spare-part-equipment:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="关联ID" align="center" prop="id" />
      <el-table-column label="备件名称" align="center" prop="sparePartName" />
      <el-table-column label="设备名称" align="center" prop="equipmentName" />
      <el-table-column label="单次使用数量" align="center" prop="usageCount" />
      <el-table-column label="安装位置" align="center" prop="installPosition" />
      <el-table-column label="是否必需" align="center" prop="isRequired">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.IS_REQUIRED" :value="scope.row.isRequired" />
        </template>
      </el-table-column>
      <el-table-column label="更换难度" align="center" prop="replacementDifficulty">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.REPLACEMENT_DIFFICULTY" :value="scope.row.replacementDifficulty" />
        </template>
      </el-table-column>
      <el-table-column label="更换时间(分钟)" align="center" prop="replacementTime" />
      <el-table-column label="所需工具" align="center" prop="toolsRequired" />
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
            v-hasPermi="['coal:spare-part-equipment:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:spare-part-equipment:delete']"
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
  <SparePartEquipmentForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import * as SparePartEquipmentApi from '@/api/coal/sparepartequipment'
import SparePartEquipmentForm from './SparePartEquipmentForm.vue'

defineOptions({ name: 'SparePartEquipment' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  sparePartId: undefined,
  equipmentId: undefined,
  isRequired: undefined,
  replacementDifficulty: undefined,
  createTime: []
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

// 下拉选择数据
const sparePartList = ref([]) // 备件列表
const equipmentList = ref([]) // 设备列表

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SparePartEquipmentApi.getSparePartEquipmentPage(queryParams)
    // 为每个关联记录添加名称映射
    const listWithNames = data.list.map(item => {
      const sparePart = sparePartList.value.find(sp => sp.id === item.sparePartId)
      const equipment = equipmentList.value.find(eq => eq.id === item.equipmentId)
      return {
        ...item,
        sparePartName: sparePart ? sparePart.sparePartName : `备件ID:${item.sparePartId}`,
        equipmentName: equipment ? equipment.equipmentName : `设备ID:${item.equipmentId}`
      }
    })
    list.value = listWithNames
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
    await SparePartEquipmentApi.deleteSparePartEquipment(id)
    message.success(t('common.delSuccess'))
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
    const data = await SparePartEquipmentApi.exportSparePartEquipment(queryParams)
    download.excel(data, '备件设备关联.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化数据 */
const initData = async () => {
  // 获取备件列表
  sparePartList.value = await SparePartEquipmentApi.getSimpleSparePartList()
  // 获取设备列表  
  equipmentList.value = await SparePartEquipmentApi.getSimpleEquipmentList()
}

/** 初始化 **/
onMounted(async () => {
  await initData() // 先获取备件和设备列表
  await getList()  // 再获取关联列表（此时可以映射名称）
})
</script>
