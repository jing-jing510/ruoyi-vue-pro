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
      <el-form-item label="设备编号" prop="equipmentCode">
        <el-input
          v-model="queryParams.equipmentCode"
          placeholder="请输入设备编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备名称" prop="equipmentName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备分类" prop="categoryId">
        <el-tree-select
          v-model="queryParams.categoryId"
          :data="equipmentCategoryTree"
          :props="{...defaultProps, label: 'categoryName'}"
          check-strictly
          default-expand-all
          placeholder="请选择设备分类"
          clearable
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="父设备ID" prop="parentEquipmentId">
        <el-input
          v-model="queryParams.parentEquipmentId"
          placeholder="请输入父设备ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备型号" prop="model">
        <el-input
          v-model="queryParams.model"
          placeholder="请输入设备型号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="制造厂商" prop="manufacturer">
        <el-input
          v-model="queryParams.manufacturer"
          placeholder="请输入制造厂商"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="制造日期" prop="manufactureDate">
        <el-date-picker
          v-model="queryParams.manufactureDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="安装日期" prop="installDate">
        <el-date-picker
          v-model="queryParams.installDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="投产日期" prop="commissionDate">
        <el-date-picker
          v-model="queryParams.commissionDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="资产编号" prop="assetNumber">
        <el-input
          v-model="queryParams.assetNumber"
          placeholder="请输入资产编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="所属车间" prop="workshop">
        <el-input
          v-model="queryParams.workshop"
          placeholder="请输入所属车间"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="安装位置" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入安装位置"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="X坐标" prop="coordinateX">
        <el-input
          v-model="queryParams.coordinateX"
          placeholder="请输入X坐标"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="Y坐标" prop="coordinateY">
        <el-input
          v-model="queryParams.coordinateY"
          placeholder="请输入Y坐标"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备状态" prop="equipmentStatus">
        <el-select
          v-model="queryParams.equipmentStatus"
          placeholder="请选择设备状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.EQUIPMENT_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="健康等级" prop="healthLevel">
        <el-select
          v-model="queryParams.healthLevel"
          placeholder="请选择健康等级"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.EQUIPMENT_HEALTH_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="重要度" prop="importanceLevel">
        <el-select
          v-model="queryParams.importanceLevel"
          placeholder="请选择重要度"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.EQUIPMENT_IMPORTANCE_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="责任人ID" prop="responsiblePersonId">
        <el-input
          v-model="queryParams.responsiblePersonId"
          placeholder="请输入责任人ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="维护人ID" prop="maintenancePersonId">
        <el-input
          v-model="queryParams.maintenancePersonId"
          placeholder="请输入维护人ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="操作人ID" prop="operatorPersonId">
        <el-input
          v-model="queryParams.operatorPersonId"
          placeholder="请输入操作人ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="供应商" prop="supplier">
        <el-input
          v-model="queryParams.supplier"
          placeholder="请输入供应商"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="采购日期" prop="purchaseDate">
        <el-date-picker
          v-model="queryParams.purchaseDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item label="保修期(月)" prop="warrantyPeriod">
        <el-input
          v-model="queryParams.warrantyPeriod"
          placeholder="请输入保修期(月)"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="保修到期日期" prop="warrantyExpireDate">
        <el-date-picker
          v-model="queryParams.warrantyExpireDate"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
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
          v-hasPermi="['coal:equipment-info:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:equipment-info:export']"
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
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      row-key="id"
      :default-expand-all="isExpandAll"
      v-if="refreshTable"
    >
      <el-table-column label="设备编号" align="center" prop="equipmentCode" width="120px" />
      <el-table-column label="设备名称" align="center" prop="equipmentName" width="150px" />
      <el-table-column label="设备型号" align="center" prop="model" width="120px" />
      <el-table-column label="制造厂商" align="center" prop="manufacturer" width="120px" />
      <el-table-column label="所属车间" align="center" prop="workshop" width="100px" />
      <el-table-column label="安装位置" align="center" prop="location" width="120px" />
      <el-table-column label="资产编号" align="center" prop="assetNumber" width="120px" />
      <el-table-column label="设备状态" align="center" prop="equipmentStatus" width="100px">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.EQUIPMENT_STATUS" :value="scope.row.equipmentStatus" />
        </template>
      </el-table-column>
      <el-table-column label="健康等级" align="center" prop="healthLevel" width="100px">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.EQUIPMENT_HEALTH_LEVEL" :value="scope.row.healthLevel" />
        </template>
      </el-table-column>
      <el-table-column label="重要度" align="center" prop="importanceLevel" width="100px">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.EQUIPMENT_IMPORTANCE_LEVEL" :value="scope.row.importanceLevel" />
        </template>
      </el-table-column>
      <el-table-column label="责任人" align="center" prop="responsiblePersonId" width="100px">
        <template #default="scope">
          {{ getUserNickname(scope.row.responsiblePersonId) }}
        </template>
      </el-table-column>
      <el-table-column label="维护人" align="center" prop="maintenancePersonId" width="100px">
        <template #default="scope">
          {{ getUserNickname(scope.row.maintenancePersonId) }}
        </template>
      </el-table-column>
      <el-table-column label="操作人" align="center" prop="operatorPersonId" width="100px">
        <template #default="scope">
          {{ getUserNickname(scope.row.operatorPersonId) }}
        </template>
      </el-table-column>
      <el-table-column label="说明书/图纸" align="center" width="120px">
        <template #default="scope">
          <div v-if="scope.row.manualUrl || scope.row.drawingUrl">
            <div v-if="scope.row.manualUrl" class="mb-1">
              <el-link :href="scope.row.manualUrl" target="_blank" type="primary" size="small">
                说明书
              </el-link>
            </div>
            <div v-if="scope.row.drawingUrl">
              <el-link :href="scope.row.drawingUrl" target="_blank" type="success" size="small">
                图纸
              </el-link>
            </div>
          </div>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="供应商" align="center" prop="supplier" width="120px" />
      <el-table-column label="采购价格" align="center" prop="purchasePrice" width="100px" />
      <el-table-column label="保修期(月)" align="center" prop="warrantyPeriod" width="100px" />
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
            v-hasPermi="['coal:equipment-info:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:equipment-info:delete']"
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
  <EquipmentInfoForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import { handleTree, defaultProps } from '@/utils/tree'
import download from '@/utils/download'
import { EquipmentInfoApi, EquipmentInfo } from '@/api/coal/equipmentinfo'
import { EquipmentCategoryApi } from '@/api/coal/equipmentcategory'
import { getSimpleUserList } from '@/api/system/user'
import EquipmentInfoForm from './EquipmentInfoForm.vue'

/** 设备档案 列表 */
defineOptions({ name: 'EquipmentInfo' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<EquipmentInfo[]>([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  equipmentCode: undefined,
  equipmentName: undefined,
  categoryId: undefined,
  parentEquipmentId: undefined,
  model: undefined,
  manufacturer: undefined,
  manufactureDate: [],
  installDate: [],
  commissionDate: [],
  assetNumber: undefined,
  ratedPower: undefined,
  ratedCapacity: undefined,
  weight: undefined,
  dimensions: undefined,
  voltageLevel: undefined,
  protectionLevel: undefined,
  workshop: undefined,
  location: undefined,
  coordinateX: undefined,
  coordinateY: undefined,
  equipmentStatus: undefined,
  healthLevel: undefined,
  importanceLevel: undefined,
  responsiblePersonId: undefined,
  maintenancePersonId: undefined,
  operatorPersonId: undefined,
  qrCode: undefined,
  qrCodeUrl: undefined,
  manualUrl: undefined,
  drawingUrl: undefined,
  supplier: undefined,
  purchaseDate: [],
  purchasePrice: undefined,
  warrantyPeriod: undefined,
  warrantyExpireDate: [],
  remark: undefined,
  createTime: [],
})
const total = ref(0)
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const equipmentCategoryTree = ref() // 设备分类树形结构
const userList = ref([]) // 用户列表

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await EquipmentInfoApi.getEquipmentInfoList(queryParams)
    list.value = handleTree(data, 'id', 'parentEquipmentId')
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
    await EquipmentInfoApi.deleteEquipmentInfo(id)
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
    const data = await EquipmentInfoApi.exportEquipmentInfo(queryParams)
    download.excel(data, '设备档案.xls')
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

/** 获得设备分类树 */
const getEquipmentCategoryTree = async () => {
  equipmentCategoryTree.value = []
  const data = await EquipmentCategoryApi.getEquipmentCategoryList({})
  const root: Tree = { id: 0, name: '顶级设备分类', children: [] }
  root.children = handleTree(data, 'id', 'parentId')
  equipmentCategoryTree.value.push(root)
}

/** 获得用户列表 */
const getUserList = async () => {
  userList.value = await getSimpleUserList()
}

/** 根据用户ID获取用户昵称 */
const getUserNickname = (userId: number) => {
  const user = userList.value.find((u: any) => u.id === userId)
  return user ? user.nickname : userId
}

/** 初始化 **/
onMounted(() => {
  getList()
  getEquipmentCategoryTree()
  getUserList()
})
</script>
