<!-- 备件管理 -->
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
      <el-form-item label="备件名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入备件名称"
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
      <el-form-item label="关联设备" prop="equipmentId">
        <el-select
          v-model="queryParams.equipmentId"
          placeholder="请选择关联设备"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="equipment in equipmentList"
            :key="equipment.id"
            :label="equipment.equipmentName"
            :value="equipment.id"
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
          v-hasPermi="['erp:product:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增备件
        </el-button>
        <el-button
          type="warning"
          plain
          @click="openAlertPage"
        >
          <Icon icon="ep:warning" class="mr-5px" /> 预警管理
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="备件编号" align="center" prop="barCode" width="120" />
      <el-table-column label="备件名称" align="center" prop="name" width="150" />
      <el-table-column label="备件类型" align="center" prop="sparePartType" width="100">
        <template #default="scope">
          <dict-tag v-if="scope.row.sparePartType" :type="DICT_TYPE.SPARE_PART_TYPE" :value="scope.row.sparePartType" />
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="关联设备" align="center" prop="equipmentName" width="150">
        <template #default="scope">
          <span v-if="scope.row.equipmentName">{{ scope.row.equipmentName }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="当前库存" align="center" prop="currentStock" width="100">
        <template #default="scope">
          <span v-if="scope.row.currentStock !== undefined">{{ scope.row.currentStock }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="最低库存" align="center" prop="minStock" width="100">
        <template #default="scope">
          <span v-if="scope.row.minStock">{{ scope.row.minStock }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="库存状态" align="center" width="120">
        <template #default="scope">
          <el-tag v-if="scope.row.minStock && scope.row.currentStock !== undefined" 
                  :type="scope.row.currentStock < scope.row.minStock ? 'danger' : 'success'">
            {{ scope.row.currentStock < scope.row.minStock ? '库存不足' : '库存正常' }}
          </el-tag>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="更换周期" align="center" prop="replacementCycle" width="100">
        <template #default="scope">
          <span v-if="scope.row.replacementCycle">{{ scope.row.replacementCycle }}天</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="下次更换" align="center" prop="nextReplacementDate" width="120">
        <template #default="scope">
          <span v-if="scope.row.nextReplacementDate">{{ scope.row.nextReplacementDate }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COMMON_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['erp:product:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="info"
            @click="openStockPage(scope.row)"
          >
            库存管理
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['erp:product:delete']"
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
  <ProductForm ref="formRef" @success="getList" />
  
  <!-- 库存管理弹窗 -->
  <!-- <StockDialog ref="stockDialogRef" @success="getList" /> -->
  
  <!-- 预警管理弹窗 -->
  <!-- <AlertDialog ref="alertDialogRef" @success="getList" /> -->
</template>

<script setup lang="ts">
import { ProductApi, ProductVO, SparePartQueryParams } from '@/api/erp/product/product'
import { EquipmentInfoApi } from '@/api/coal/equipmentinfo'
import ProductForm from '@/views/erp/product/product/ProductForm.vue'
// import StockDialog from './components/StockDialog.vue'
// import AlertDialog from './components/AlertDialog.vue'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { ref, reactive, onMounted } from 'vue'
import { useMessage } from '@/hooks/web/useMessage'
import { useI18n } from '@/hooks/web/useI18n'

/** 备件管理 */
defineOptions({ name: 'CoalSparePart' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<ProductVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive<SparePartQueryParams>({
  pageNo: 1,
  pageSize: 10,
  name: undefined,
  sparePartType: undefined,
  equipmentId: undefined,
  hasSparePartType: true // 备件管理页面只查询有备件类型的产品
})
const queryFormRef = ref() // 搜索的表单
const equipmentList = ref<any[]>([]) // 设备列表

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await ProductApi.getSparePartPage(queryParams)
    list.value = data.list || []
    total.value = data.total || 0
  } catch (error) {
    console.error('获取备件列表失败:', error)
    list.value = []
    total.value = 0
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
    await ProductApi.deleteProduct(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 打开库存管理 */
// const stockDialogRef = ref()
const openStockPage = (row: ProductVO) => {
  message.info('库存管理功能开发中...')
  // stockDialogRef.value.open(row)
}

/** 打开预警管理 */
// const alertDialogRef = ref()
const openAlertPage = () => {
  message.info('预警管理功能开发中...')
  // alertDialogRef.value.open()
}

/** 初始化 **/
onMounted(async () => {
  await getList()
  // 获取设备列表
  try {
    const equipmentData = await EquipmentInfoApi.getEquipmentInfoList({})
    equipmentList.value = equipmentData || []
  } catch (error) {
    console.warn('获取设备列表失败:', error)
    equipmentList.value = []
  }
})
</script>
