<!-- 库存管理弹窗 -->
<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="800px">
    <el-tabs v-model="activeTab" type="card">
      <!-- 库存信息 -->
      <el-tab-pane label="库存信息" name="stock">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="备件名称">{{ sparePartInfo.name }}</el-descriptions-item>
          <el-descriptions-item label="备件编号">{{ sparePartInfo.barCode }}</el-descriptions-item>
          <el-descriptions-item label="当前库存">{{ currentStock || 0 }}</el-descriptions-item>
          <el-descriptions-item label="最低库存">{{ sparePartInfo.minStock || '-' }}</el-descriptions-item>
          <el-descriptions-item label="最高库存">{{ sparePartInfo.maxStock || '-' }}</el-descriptions-item>
          <el-descriptions-item label="安全库存">{{ sparePartInfo.safetyStock || '-' }}</el-descriptions-item>
          <el-descriptions-item label="库存状态">
            <el-tag :type="getStockStatusType()">
              {{ getStockStatusText() }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="关联设备">{{ sparePartInfo.equipmentName || '-' }}</el-descriptions-item>
        </el-descriptions>
        
        <div class="mt-4">
          <el-button type="primary" @click="activeTab = 'record'">
            <Icon icon="ep:list" class="mr-5px" /> 查看库存记录
          </el-button>
          <el-button type="success" @click="activeTab = 'adjust'">
            <Icon icon="ep:edit" class="mr-5px" /> 库存调整
          </el-button>
        </div>
      </el-tab-pane>
      
      <!-- 库存记录 -->
      <el-tab-pane label="库存记录" name="record">
        <el-table :data="stockRecords" v-loading="stockLoading" :stripe="true">
          <el-table-column label="业务类型" align="center" prop="bizType" width="120">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.ERP_STOCK_RECORD_BIZ_TYPE" :value="scope.row.bizType" />
            </template>
          </el-table-column>
          <el-table-column label="变动数量" align="center" prop="count" width="100">
            <template #default="scope">
              <span :class="scope.row.count > 0 ? 'text-green-600' : 'text-red-600'">
                {{ scope.row.count > 0 ? '+' : '' }}{{ scope.row.count }}
              </span>
            </template>
          </el-table-column>
          <el-table-column label="变动后库存" align="center" prop="totalCount" width="120" />
          <el-table-column label="单价" align="center" prop="productPrice" width="100">
            <template #default="scope">
              <span v-if="scope.row.productPrice">¥{{ scope.row.productPrice }}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="总价" align="center" prop="totalPrice" width="100">
            <template #default="scope">
              <span v-if="scope.row.totalPrice">¥{{ scope.row.totalPrice }}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
          <el-table-column label="备注" align="center" prop="remark" />
          <el-table-column label="创建时间" align="center" prop="createTime" width="180">
            <template #default="scope">
              {{ dateFormatter(scope.row.createTime) }}
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      
      <!-- 库存调整 -->
      <el-tab-pane label="库存调整" name="adjust">
        <el-form
          ref="adjustFormRef"
          :model="adjustForm"
          :rules="adjustRules"
          label-width="100px"
        >
          <el-form-item label="调整类型" prop="adjustType">
            <el-radio-group v-model="adjustForm.adjustType">
              <el-radio :label="1">入库</el-radio>
              <el-radio :label="2">出库</el-radio>
              <el-radio :label="3">盘点调整</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="调整数量" prop="count">
            <el-input-number
              v-model="adjustForm.count"
              :min="1"
              :precision="2"
              placeholder="请输入调整数量"
              class="!w-200px"
            />
          </el-form-item>
          <el-form-item label="单价" prop="productPrice">
            <el-input-number
              v-model="adjustForm.productPrice"
              :min="0"
              :precision="2"
              placeholder="请输入单价"
              class="!w-200px"
            />
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model="adjustForm.remark"
              type="textarea"
              placeholder="请输入调整备注"
              :rows="3"
            />
          </el-form-item>
        </el-form>
        
        <div class="text-center mt-4">
          <el-button type="primary" @click="submitAdjust" :loading="adjustLoading">
            确认调整
          </el-button>
          <el-button @click="resetAdjustForm">重置</el-button>
        </div>
      </el-tab-pane>
    </el-tabs>
  </Dialog>
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { ProductApi } from '@/api/erp/product/product'
import { StockRecordApi } from '@/api/erp/stock/record'
import { DICT_TYPE } from '@/utils/dict'

/** 库存管理弹窗 */
defineOptions({ name: 'StockDialog' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('库存管理') // 弹窗的标题
const activeTab = ref('stock') // 当前激活的标签页

const sparePartInfo = ref<any>({}) // 备件信息
const currentStock = ref(0) // 当前库存
const stockRecords = ref<any[]>([]) // 库存记录
const stockLoading = ref(false) // 库存记录加载中

// 库存调整表单
const adjustForm = ref({
  adjustType: 1,
  count: undefined,
  productPrice: undefined,
  remark: undefined
})
const adjustRules = reactive({
  adjustType: [{ required: true, message: '请选择调整类型', trigger: 'change' }],
  count: [{ required: true, message: '请输入调整数量', trigger: 'blur' }],
  productPrice: [{ required: true, message: '请输入单价', trigger: 'blur' }]
})
const adjustFormRef = ref() // 调整表单 Ref
const adjustLoading = ref(false) // 调整提交加载中

/** 打开弹窗 */
const open = async (sparePart: any) => {
  dialogVisible.value = true
  sparePartInfo.value = sparePart
  activeTab.value = 'stock'
  
  // 获取库存信息
  await getStockInfo()
  // 获取库存记录
  await getStockRecords()
}

/** 获取库存信息 */
const getStockInfo = async () => {
  try {
    // TODO: 调用库存API获取当前库存
    // const stockInfo = await StockApi.getStockByProductId(sparePartInfo.value.id)
    // currentStock.value = stockInfo.totalCount || 0
    currentStock.value = Math.floor(Math.random() * 100) // 模拟数据
  } catch (error) {
    console.error('获取库存信息失败:', error)
  }
}

/** 获取库存记录 */
const getStockRecords = async () => {
  stockLoading.value = true
  try {
    // TODO: 调用库存记录API
    // const data = await StockRecordApi.getStockRecordPage({
    //   productId: sparePartInfo.value.id,
    //   pageNo: 1,
    //   pageSize: 100
    // })
    // stockRecords.value = data.list || []
    
    // 模拟数据
    stockRecords.value = [
      {
        id: 1,
        bizType: 1,
        count: 50,
        totalCount: 50,
        productPrice: 100.00,
        totalPrice: 5000.00,
        remark: '初始入库',
        createTime: new Date().toISOString()
      },
      {
        id: 2,
        bizType: 2,
        count: -10,
        totalCount: 40,
        productPrice: 100.00,
        totalPrice: 1000.00,
        remark: '设备维修出库',
        createTime: new Date(Date.now() - 86400000).toISOString()
      }
    ]
  } finally {
    stockLoading.value = false
  }
}

/** 获取库存状态类型 */
const getStockStatusType = () => {
  if (!sparePartInfo.value.minStock) return 'info'
  if (currentStock.value < sparePartInfo.value.minStock) return 'danger'
  if (currentStock.value < (sparePartInfo.value.safetyStock || sparePartInfo.value.minStock)) return 'warning'
  return 'success'
}

/** 获取库存状态文本 */
const getStockStatusText = () => {
  if (!sparePartInfo.value.minStock) return '未设置预警'
  if (currentStock.value < sparePartInfo.value.minStock) return '库存不足'
  if (currentStock.value < (sparePartInfo.value.safetyStock || sparePartInfo.value.minStock)) return '库存偏低'
  return '库存正常'
}

/** 提交库存调整 */
const submitAdjust = async () => {
  await adjustFormRef.value.validate()
  adjustLoading.value = true
  try {
    // TODO: 调用库存调整API
    // await StockRecordApi.createStockRecord({
    //   productId: sparePartInfo.value.id,
    //   bizType: adjustForm.value.adjustType,
    //   count: adjustForm.value.count,
    //   productPrice: adjustForm.value.productPrice,
    //   remark: adjustForm.value.remark
    // })
    
    message.success('库存调整成功')
    resetAdjustForm()
    await getStockInfo()
    await getStockRecords()
  } catch (error) {
    console.error('库存调整失败:', error)
  } finally {
    adjustLoading.value = false
  }
}

/** 重置调整表单 */
const resetAdjustForm = () => {
  adjustForm.value = {
    adjustType: 1,
    count: undefined,
    productPrice: undefined,
    remark: undefined
  }
  adjustFormRef.value?.resetFields()
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
