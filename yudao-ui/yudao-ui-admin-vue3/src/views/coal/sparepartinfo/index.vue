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
      <el-form-item label="备件编号" prop="sparePartCode">
        <el-input
          v-model="queryParams.sparePartCode"
          placeholder="请输入备件编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="备件名称" prop="sparePartName">
        <el-input
          v-model="queryParams.sparePartName"
          placeholder="请输入备件名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="备件分类ID" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入备件分类ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="备件类型" prop="sparePartType">
        <el-select
          v-model="queryParams.sparePartType"
          placeholder="请选择备件类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SPARE_PART_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SPARE_PART_STATUS)"
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
          v-hasPermi="['coal:spare-part-info:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:spare-part-info:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:spare-part-info:delete']"
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
        @selection-change="handleRowCheckboxChange"
    >
    <el-table-column type="selection" width="55" />
      <el-table-column label="备件ID" align="center" prop="id" />
      <el-table-column label="备件编号" align="center" prop="sparePartCode" />
      <el-table-column label="备件名称" align="center" prop="sparePartName" />
      <el-table-column label="备件分类ID" align="center" prop="categoryId" />
      <el-table-column label="规格型号" align="center" prop="specification" />
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="计量单位" align="center" prop="unit" />
      <el-table-column label="备件类型" align="center" prop="sparePartType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.SPARE_PART_TYPE" :value="scope.row.sparePartType" />
        </template>
      </el-table-column>
      <el-table-column label="材质" align="center" prop="material" />
      <el-table-column label="重量(kg)" align="center" prop="weight" />
      <el-table-column label="外形尺寸" align="center" prop="dimensions" />
      <el-table-column label="最低库存预警数量" align="center" prop="minStock" />
      <el-table-column label="最高库存数量" align="center" prop="maxStock" />
      <el-table-column label="安全库存数量" align="center" prop="safetyStock" />
      <el-table-column label="当前库存数量" align="center" prop="currentStock" />
      <el-table-column label="主要供应商" align="center" prop="supplier" />
      <el-table-column label="供应商零件号" align="center" prop="supplierPartNo" />
      <el-table-column label="单价" align="center" prop="unitPrice" />
      <el-table-column label="采购周期(天)" align="center" prop="leadTime" />
      <el-table-column label="建议更换周期(天)" align="center" prop="replacementCycle" />
      <el-table-column label="平均使用寿命(天)" align="center" prop="averageLifespan" />
      <el-table-column label="使用频率" align="center" prop="usageFrequency">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.USAGE_FREQUENCY" :value="scope.row.usageFrequency" />
        </template>
      </el-table-column>
      <el-table-column label="存储位置" align="center" prop="storageLocation" />
      <el-table-column label="存储条件要求" align="center" prop="storageCondition" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.SPARE_PART_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="是否关键备件" align="center" prop="isCritical">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.IS_CRITICAL_SPARE_PART" :value="scope.row.isCritical" />
        </template>
      </el-table-column>
      <el-table-column label="备件图片" align="center" prop="imageUrl" width="100">
        <template #default="scope">
          <el-image
            v-if="scope.row.imageUrl"
            :src="scope.row.imageUrl"
            :preview-src-list="[scope.row.imageUrl]"
            fit="cover"
            style="width: 60px; height: 60px; border-radius: 4px;"
            :preview-teleported="true"
          />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="说明书文件" align="center" prop="manualUrl" width="120">
        <template #default="scope">
          <el-button
            v-if="scope.row.manualUrl"
            type="primary"
            link
            @click="downloadFile(scope.row.manualUrl, '说明书')"
          >
            <Icon icon="ep:download" class="mr-1" />
            下载
          </el-button>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="图纸文件" align="center" prop="drawingUrl" width="120">
        <template #default="scope">
          <el-button
            v-if="scope.row.drawingUrl"
            type="primary"
            link
            @click="downloadFile(scope.row.drawingUrl, '图纸')"
          >
            <Icon icon="ep:download" class="mr-1" />
            下载
          </el-button>
          <span v-else>-</span>
        </template>
      </el-table-column>
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
            v-hasPermi="['coal:spare-part-info:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:spare-part-info:delete']"
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
  <SparePartInfoForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { SparePartInfoApi, SparePartInfo } from '@/api/coal/sparepartinfo'
import SparePartInfoForm from './SparePartInfoForm.vue'

/** 备件基础信息 列表 */
defineOptions({ name: 'SparePartInfo' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<SparePartInfo[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  sparePartCode: undefined,
  sparePartName: undefined,
  categoryId: undefined,
  sparePartType: undefined,
  status: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SparePartInfoApi.getSparePartInfoPage(queryParams)
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
    await SparePartInfoApi.deleteSparePartInfo(id)
    message.success(t('common.delSuccess'))
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除备件基础信息 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await SparePartInfoApi.deleteSparePartInfoList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: SparePartInfo[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await SparePartInfoApi.exportSparePartInfo(queryParams)
    download.excel(data, '备件基础信息.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 下载文件 */
const downloadFile = (url: string, fileName: string) => {
  if (url) {
    const link = document.createElement('a')
    link.href = url
    link.download = fileName
    link.target = '_blank'
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
