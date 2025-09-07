<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="设备编号" prop="equipmentCode">
        <el-input v-model="formData.equipmentCode" placeholder="请输入设备编号" />
      </el-form-item>
      <el-form-item label="设备名称" prop="equipmentName">
        <el-input v-model="formData.equipmentName" placeholder="请输入设备名称" />
      </el-form-item>
      <el-form-item label="设备分类" prop="categoryId">
        <el-tree-select
          v-model="formData.categoryId"
          :data="equipmentCategoryTree"
          :props="{...defaultProps, label: 'categoryName'}"
          check-strictly
          default-expand-all
          placeholder="请选择设备分类"
        />
      </el-form-item>
      <el-form-item label="父设备ID" prop="parentEquipmentId">
        <el-tree-select
          v-model="formData.parentEquipmentId"
          :data="equipmentInfoTree"
          :props="{...defaultProps, label: 'equipmentName'}"
          check-strictly
          default-expand-all
          placeholder="请选择父设备ID"
        />
      </el-form-item>
      <el-form-item label="设备型号" prop="model">
        <el-input v-model="formData.model" placeholder="请输入设备型号" />
      </el-form-item>
      <el-form-item label="制造厂商" prop="manufacturer">
        <el-input v-model="formData.manufacturer" placeholder="请输入制造厂商" />
      </el-form-item>
      <el-form-item label="制造日期" prop="manufactureDate">
        <el-date-picker
          v-model="formData.manufactureDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择制造日期"
        />
      </el-form-item>
      <el-form-item label="安装日期" prop="installDate">
        <el-date-picker
          v-model="formData.installDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择安装日期"
        />
      </el-form-item>
      <el-form-item label="投产日期" prop="commissionDate">
        <el-date-picker
          v-model="formData.commissionDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择投产日期"
        />
      </el-form-item>
      <el-form-item label="资产编号" prop="assetNumber">
        <el-input v-model="formData.assetNumber" placeholder="请输入资产编号" />
      </el-form-item>
      <el-form-item label="额定功率(kW)" prop="ratedPower">
        <el-input v-model="formData.ratedPower" placeholder="请输入额定功率(kW)" />
      </el-form-item>
      <el-form-item label="额定处理能力(t/h)" prop="ratedCapacity">
        <el-input v-model="formData.ratedCapacity" placeholder="请输入额定处理能力(t/h)" />
      </el-form-item>
      <el-form-item label="设备重量(t)" prop="weight">
        <el-input v-model="formData.weight" placeholder="请输入设备重量(t)" />
      </el-form-item>
      <el-form-item label="外形尺寸(长x宽x高)" prop="dimensions">
        <el-input v-model="formData.dimensions" placeholder="请输入外形尺寸(长x宽x高)" />
      </el-form-item>
      <el-form-item label="电压等级" prop="voltageLevel">
        <el-input v-model="formData.voltageLevel" placeholder="请输入电压等级" />
      </el-form-item>
      <el-form-item label="防护等级" prop="protectionLevel">
        <el-input v-model="formData.protectionLevel" placeholder="请输入防护等级" />
      </el-form-item>
      <el-form-item label="所属车间" prop="workshop">
        <el-input v-model="formData.workshop" placeholder="请输入所属车间" />
      </el-form-item>
      <el-form-item label="安装位置" prop="location">
        <el-input v-model="formData.location" placeholder="请输入安装位置" />
      </el-form-item>
      <el-form-item label="X坐标" prop="coordinateX">
        <el-input v-model="formData.coordinateX" placeholder="请输入X坐标" />
      </el-form-item>
      <el-form-item label="Y坐标" prop="coordinateY">
        <el-input v-model="formData.coordinateY" placeholder="请输入Y坐标" />
      </el-form-item>
      <el-form-item label="设备状态" prop="equipmentStatus">
        <el-radio-group v-model="formData.equipmentStatus">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.EQUIPMENT_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="健康等级" prop="healthLevel">
        <el-select v-model="formData.healthLevel" placeholder="请选择健康等级" clearable>
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.EQUIPMENT_HEALTH_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="重要度" prop="importanceLevel">
        <el-select v-model="formData.importanceLevel" placeholder="请选择重要度" clearable>
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.EQUIPMENT_IMPORTANCE_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="责任人" prop="responsiblePersonId">
        <el-select v-model="formData.responsiblePersonId" placeholder="请选择责任人" clearable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="维护人" prop="maintenancePersonId">
        <el-select v-model="formData.maintenancePersonId" placeholder="请选择维护人" clearable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="操作人" prop="operatorPersonId">
        <el-select v-model="formData.operatorPersonId" placeholder="请选择操作人" clearable>
          <el-option
            v-for="user in userList"
            :key="user.id"
            :label="user.nickname"
            :value="user.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="二维码内容" prop="qrCode">
        <el-input v-model="formData.qrCode" placeholder="请输入二维码内容" />
      </el-form-item>
      <el-form-item label="二维码图片URL" prop="qrCodeUrl">
        <el-input v-model="formData.qrCodeUrl" placeholder="请输入二维码图片URL" />
      </el-form-item>
      <el-form-item label="说明书文件URL" prop="manualUrl">
        <el-input v-model="formData.manualUrl" placeholder="请输入说明书文件URL" />
      </el-form-item>
      <el-form-item label="图纸文件URL" prop="drawingUrl">
        <el-input v-model="formData.drawingUrl" placeholder="请输入图纸文件URL" />
      </el-form-item>
      <el-form-item label="供应商" prop="supplier">
        <el-input v-model="formData.supplier" placeholder="请输入供应商" />
      </el-form-item>
      <el-form-item label="采购日期" prop="purchaseDate">
        <el-date-picker
          v-model="formData.purchaseDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择采购日期"
        />
      </el-form-item>
      <el-form-item label="采购价格" prop="purchasePrice">
        <el-input v-model="formData.purchasePrice" placeholder="请输入采购价格" />
      </el-form-item>
      <el-form-item label="保修期(月)" prop="warrantyPeriod">
        <el-input v-model="formData.warrantyPeriod" placeholder="请输入保修期(月)" />
      </el-form-item>
      <el-form-item label="保修到期日期" prop="warrantyExpireDate">
        <el-date-picker
          v-model="formData.warrantyExpireDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="选择保修到期日期"
        />
      </el-form-item>
      <el-form-item label="二维码" prop="qrCode">
        <div class="flex items-center gap-4">
          <el-input v-model="formData.qrCode" placeholder="点击生成按钮自动生成设备信息二维码" readonly />
          <el-button @click="generateQRCode" type="primary" size="small">生成二维码</el-button>
        </div>
        <div v-if="qrCodeUrl" class="mt-2">
          <img :src="qrCodeUrl" alt="二维码" class="w-32 h-32" />
          <p class="text-sm text-gray-500 mt-1">二维码预览（仅前端显示，不保存到数据库）</p>
        </div>
      </el-form-item>
      <el-form-item label="说明书" prop="manualUrl">
        <UploadFile v-model="formData.manualUrl" />
      </el-form-item>
      <el-form-item label="图纸" prop="drawingUrl">
        <UploadFile v-model="formData.drawingUrl" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { EquipmentInfoApi, EquipmentInfo } from '@/api/coal/equipmentinfo'
import { EquipmentCategoryApi } from '@/api/coal/equipmentcategory'
import { getSimpleUserList } from '@/api/system/user'
import { defaultProps, handleTree } from '@/utils/tree'
import UploadFile from '@/components/UploadFile/src/UploadFile.vue'
import QRCode from 'qrcode'

/** 设备档案 表单 */
defineOptions({ name: 'EquipmentInfoForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  equipmentCode: undefined,
  equipmentName: undefined,
  categoryId: undefined,
  parentEquipmentId: undefined,
  model: undefined,
  manufacturer: undefined,
  manufactureDate: undefined,
  installDate: undefined,
  commissionDate: undefined,
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
  qrCode: '',
  qrCodeUrl: '',
  manualUrl: '',
  drawingUrl: '',
  supplier: undefined,
  purchaseDate: undefined,
  purchasePrice: undefined,
  warrantyPeriod: undefined,
  warrantyExpireDate: undefined,
  remark: undefined,
})
const formRules = reactive({
  equipmentCode: [{ required: true, message: '设备编号不能为空', trigger: 'blur' }],
  equipmentName: [{ required: true, message: '设备名称不能为空', trigger: 'blur' }],
  categoryId: [{ required: true, message: '设备分类ID不能为空', trigger: 'blur' }],
  equipmentStatus: [{ required: true, message: '设备状态不能为空', trigger: 'blur' }],
  healthLevel: [{ required: true, message: '健康等级不能为空', trigger: 'blur' }],
  importanceLevel: [{ required: true, message: '重要度不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const equipmentInfoTree = ref() // 树形结构
const equipmentCategoryTree = ref() // 设备分类树形结构
const qrCodeUrl = ref('') // 二维码图片URL
const userList = ref<any[]>([]) // 用户列表

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      const data = await EquipmentInfoApi.getEquipmentInfo(id)
      formData.value = data
      // 转换日期字段为字符串格式，以便 el-date-picker 正确显示
      if (data.manufactureDate) {
        formData.value.manufactureDate = data.manufactureDate.toString()
      }
      if (data.installDate) {
        formData.value.installDate = data.installDate.toString()
      }
      if (data.commissionDate) {
        formData.value.commissionDate = data.commissionDate.toString()
      }
      if (data.purchaseDate) {
        formData.value.purchaseDate = data.purchaseDate.toString()
      }
      if (data.warrantyExpireDate) {
        formData.value.warrantyExpireDate = data.warrantyExpireDate.toString()
      }
    } finally {
      formLoading.value = false
    }
  }
  await getEquipmentInfoTree()
  await getEquipmentCategoryTree()
  await getUserList()
}

/** 生成二维码 */
const generateQRCode = async () => {
  // 生成简洁的设备信息二维码内容
  const qrText = `设备编号: ${formData.value.equipmentCode || ''}
设备名称: ${formData.value.equipmentName || ''}
型号: ${formData.value.model || ''}
厂商: ${formData.value.manufacturer || ''}
车间: ${formData.value.workshop || ''}
位置: ${formData.value.location || ''}
状态: ${formData.value.equipmentStatus || ''}
健康等级: ${formData.value.healthLevel || ''}
重要度: ${formData.value.importanceLevel || ''}
责任人: ${getUserNickname(formData.value.responsiblePersonId || 0) || ''}
维护人: ${getUserNickname(formData.value.maintenancePersonId || 0) || ''}
操作人: ${getUserNickname(formData.value.operatorPersonId || 0) || ''}
生成时间: ${new Date().toLocaleString()}`
  
  formData.value.qrCode = qrText
  
  try {
    const url = await QRCode.toDataURL(qrText, {
      width: 200,
      margin: 2
    })
    qrCodeUrl.value = url
    // 不存储base64数据到数据库，只存储二维码内容
    formData.value.qrCodeUrl = ''
    message.success('设备信息二维码生成成功')
  } catch (error) {
    message.error('二维码生成失败')
  }
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
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = { ...formData.value } as unknown as EquipmentInfo
    
    // 确保日期字段格式正确
    if (data.manufactureDate && typeof data.manufactureDate === 'string') {
      data.manufactureDate = data.manufactureDate.replace(/,/g, '-')
    }
    if (data.installDate && typeof data.installDate === 'string') {
      data.installDate = data.installDate.replace(/,/g, '-')
    }
    if (data.commissionDate && typeof data.commissionDate === 'string') {
      data.commissionDate = data.commissionDate.replace(/,/g, '-')
    }
    if (data.purchaseDate && typeof data.purchaseDate === 'string') {
      data.purchaseDate = data.purchaseDate.replace(/,/g, '-')
    }
    if (data.warrantyExpireDate && typeof data.warrantyExpireDate === 'string') {
      data.warrantyExpireDate = data.warrantyExpireDate.replace(/,/g, '-')
    }
    
    if (formType.value === 'create') {
      await EquipmentInfoApi.createEquipmentInfo(data)
      message.success(t('common.createSuccess'))
    } else {
      await EquipmentInfoApi.updateEquipmentInfo(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    equipmentCode: undefined,
    equipmentName: undefined,
    categoryId: undefined,
    parentEquipmentId: undefined,
    model: undefined,
    manufacturer: undefined,
    manufactureDate: undefined,
    installDate: undefined,
    commissionDate: undefined,
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
    qrCode: '',
    qrCodeUrl: '',
    manualUrl: '',
    drawingUrl: '',
    supplier: undefined,
    purchaseDate: undefined,
    purchasePrice: undefined,
    warrantyPeriod: undefined,
    warrantyExpireDate: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}

/** 获得设备档案树 */
const getEquipmentInfoTree = async () => {
  equipmentInfoTree.value = []
  const data = await EquipmentInfoApi.getEquipmentInfoList({})
  const root: Tree = { id: 0, name: '顶级设备档案', children: [] }
  root.children = handleTree(data, 'id', 'parentEquipmentId')
  equipmentInfoTree.value.push(root)
}
</script>