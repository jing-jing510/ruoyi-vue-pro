<!-- 备件预警记录管理 -->
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
      <el-form-item label="预警类型" prop="alertType">
        <el-select
          v-model="queryParams.alertType"
          placeholder="请选择预警类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.ALERT_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警级别" prop="alertLevel">
        <el-select
          v-model="queryParams.alertLevel"
          placeholder="请选择预警级别"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.ALERT_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="预警状态" prop="alertStatus">
        <el-select
          v-model="queryParams.alertStatus"
          placeholder="请选择预警状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.ALERT_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" />搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" />重置</el-button>
        <el-button
          type="success"
          plain
          @click="handlePendingAlerts"
        >
          <Icon icon="ep:warning" class="mr-5px" />待处理预警
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" stripe>
      <el-table-column label="备件名称" align="center" prop="sparePartName" min-width="120" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" width="100" />
      <el-table-column label="预警类型" align="center" prop="alertType" width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.ALERT_TYPE" :value="scope.row.alertType" />
        </template>
      </el-table-column>
      <el-table-column label="预警级别" align="center" prop="alertLevel" width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.ALERT_LEVEL" :value="scope.row.alertLevel" />
        </template>
      </el-table-column>
      <el-table-column label="当前库存" align="center" prop="currentStock" width="100" />
      <el-table-column label="阈值" align="center" prop="thresholdValue" width="100" />
      <el-table-column label="预警状态" align="center" prop="alertStatus" width="100">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.ALERT_STATUS" :value="scope.row.alertStatus" />
        </template>
      </el-table-column>
      <el-table-column label="预警信息" align="center" prop="alertMessage" min-width="150" show-overflow-tooltip />
      <el-table-column label="处理人" align="center" prop="handlerName" width="100" />
      <el-table-column label="处理时间" align="center" prop="handleTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" :formatter="dateFormatter" />
      <el-table-column label="操作" align="center" width="200" fixed="right">
        <template #default="scope">
          <el-button
            v-if="scope.row.alertStatus === 1"
            link
            type="success"
            @click="handleAlert(scope.row.id)"
            v-hasPermi="['coal:spare-part-alert:handle']"
          >
            处理
          </el-button>
          <el-button
            v-if="scope.row.alertStatus === 1"
            link
            type="warning"
            @click="ignoreAlert(scope.row.id)"
            v-hasPermi="['coal:spare-part-alert:handle']"
          >
            忽略
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:spare-part-alert:delete']"
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

  <!-- 处理预警弹窗 -->
  <AlertHandleForm ref="handleFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useMessage } from '@/hooks/web/useMessage'
import { useI18n } from '@/hooks/web/useI18n'
import { dateFormatter } from '@/utils/formatTime'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'
import { ProductApi } from '@/api/erp/product/product'
import { SparePartAlertApi, SparePartAlertVO } from '@/api/coal/sparepart/alert'
import AlertHandleForm from './AlertHandleForm.vue'

/** 备件预警记录管理 */
defineOptions({ name: 'CoalSparePartAlert' })

const message = useMessage()
const { t } = useI18n()

const loading = ref(true)
const list = ref<SparePartAlertVO[]>([])
const total = ref(0)
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  sparePartId: undefined,
  alertType: undefined,
  alertLevel: undefined,
  alertStatus: undefined
})
const queryFormRef = ref()
const sparePartList = ref<any[]>([]) // 备件列表

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SparePartAlertApi.getPage(queryParams)
    list.value = data.list || []
    total.value = data.total || 0
  } catch (error) {
    console.error('获取备件预警记录列表失败:', error)
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

/** 查看待处理预警 */
const handlePendingAlerts = () => {
  queryParams.alertStatus = 1 // 1=待处理
  handleQuery()
}

/** 处理预警 */
const handleFormRef = ref()
const handleAlert = (id: number) => {
  handleFormRef.value.open('handle', id)
}

/** 忽略预警 */
const ignoreAlert = (id: number) => {
  handleFormRef.value.open('ignore', id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await SparePartAlertApi.delete(id)
    message.success(t('common.delSuccess'))
    await getList()
  } catch (error) {
    console.error('删除备件预警记录失败:', error)
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
})
</script>
