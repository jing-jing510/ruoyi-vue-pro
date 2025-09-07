<!-- 备件设备关联管理 -->
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
      <el-form-item label="备件名称" prop="sparePartId">
        <el-select
          v-model="queryParams.sparePartId"
          placeholder="请选择备件"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="product in sparePartList"
            :key="product.id"
            :label="product.name"
            :value="product.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联设备" prop="equipmentId">
        <el-select
          v-model="queryParams.equipmentId"
          placeholder="请选择设备"
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
      <el-form-item label="是否必需" prop="isRequired">
        <el-select
          v-model="queryParams.isRequired"
          placeholder="请选择"
          clearable
          class="!w-240px"
        >
          <el-option label="是" :value="1" />
          <el-option label="否" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" />搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" />重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['coal:spare-part-equipment:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" />新增
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="备件名称" align="center" prop="sparePartName" min-width="120" />
      <el-table-column label="设备名称" align="center" prop="equipmentName" min-width="120" />
      <el-table-column label="是否必需" align="center" prop="isRequired" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.isRequired ? 'success' : 'info'">
            {{ scope.row.isRequired ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="用量" align="center" prop="quantity" width="80" />
      <el-table-column label="单位" align="center" prop="unit" width="80" />
      <el-table-column label="备注" align="center" prop="remark" min-width="150" show-overflow-tooltip />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="180" fixed="right">
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
  <EquipmentRelationForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useMessage } from '@/hooks/web/useMessage'
import { useI18n } from '@/hooks/web/useI18n'
import { dateFormatter } from '@/utils/formatTime'
import { ProductApi } from '@/api/erp/product/product'
import { EquipmentInfoApi } from '@/api/coal/equipmentinfo'
import { SparePartEquipmentApi, SparePartEquipmentVO } from '@/api/coal/sparepart/equipment'
import EquipmentRelationForm from './EquipmentRelationForm.vue'

/** 备件设备关联管理 */
defineOptions({ name: 'CoalSparePartEquipment' })

const message = useMessage()
const { t } = useI18n()

const loading = ref(true)
const list = ref<SparePartEquipmentVO[]>([])
const total = ref(0)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  sparePartId: undefined,
  equipmentId: undefined,
  isRequired: undefined
})
const queryFormRef = ref()
const sparePartList = ref<any[]>([]) // 备件列表
const equipmentList = ref<any[]>([]) // 设备列表

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SparePartEquipmentApi.getPage(queryParams)
    list.value = data.list || []
    total.value = data.total || 0
  } catch (error) {
    console.error('获取备件设备关联列表失败:', error)
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
    await message.delConfirm()
    await SparePartEquipmentApi.delete(id)
    message.success(t('common.delSuccess'))
    await getList()
  } catch (error) {
    console.error('删除备件设备关联失败:', error)
  }
}

/** 初始化 */
onMounted(async () => {
  await getList()
  
  // 获取备件列表（有备件类型的产品）
  try {
    const sparePartData = await ProductApi.getProductPage({ 
      pageNo: 1, 
      pageSize: 1000,
      sparePartType: 1 // 假设1表示有备件类型
    })
    sparePartList.value = sparePartData.list || []
  } catch (error) {
    console.warn('获取备件列表失败:', error)
    sparePartList.value = []
  }
  
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
