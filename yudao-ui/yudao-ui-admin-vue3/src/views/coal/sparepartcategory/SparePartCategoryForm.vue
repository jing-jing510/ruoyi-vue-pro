<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="父分类ID (0=根分类)" prop="parentId">
        <el-tree-select
          v-model="formData.parentId"
          :data="sparePartCategoryTree"
          :props="{...defaultProps, label: 'categoryName'}"
          check-strictly
          default-expand-all
          placeholder="请选择父分类ID (0=根分类)"
        />
      </el-form-item>
      <el-form-item label="分类名称" prop="categoryName">
        <el-input v-model="formData.categoryName" placeholder="请输入分类名称" />
      </el-form-item>
      <el-form-item label="分类编码" prop="categoryCode">
        <el-input v-model="formData.categoryCode" placeholder="请输入分类编码" />
      </el-form-item>
      <el-form-item label="分类层级" prop="categoryLevel">
        <el-select v-model="formData.categoryLevel" placeholder="请选择分类层级">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.SPARE_PART_CATEGORY_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model="formData.sort" placeholder="请输入排序" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
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
import { SparePartCategoryApi, SparePartCategory } from '@/api/coal/sparepartcategory'
import { defaultProps, handleTree } from '@/utils/tree'

/** 备件分类表 (树表) 表单 */
defineOptions({ name: 'SparePartCategoryForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  parentId: undefined,
  categoryName: undefined,
  categoryCode: undefined,
  categoryLevel: undefined,
  sort: undefined,
  status: undefined,
  remark: undefined,
})
const formRules = reactive({
  parentId: [{ required: true, message: '父分类ID (0=根分类)不能为空', trigger: 'blur' }],
  categoryName: [{ required: true, message: '分类名称不能为空', trigger: 'blur' }],
  categoryCode: [{ required: true, message: '分类编码不能为空', trigger: 'blur' }],
  categoryLevel: [{ required: true, message: '分类层级：1大类 2中类 3小类不能为空', trigger: 'blur' }],
  sort: [{ required: true, message: '排序不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态：0禁用 1启用不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref
const sparePartCategoryTree = ref() // 树形结构

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
      formData.value = await SparePartCategoryApi.getSparePartCategory(id)
    } finally {
      formLoading.value = false
    }
  }
  await getSparePartCategoryTree()
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
    const data = formData.value as unknown as SparePartCategory
    if (formType.value === 'create') {
      await SparePartCategoryApi.createSparePartCategory(data)
      message.success(t('common.createSuccess'))
    } else {
      await SparePartCategoryApi.updateSparePartCategory(data)
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
    parentId: undefined,
    categoryName: undefined,
    categoryCode: undefined,
    categoryLevel: undefined,
    sort: undefined,
    status: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}

/** 获得备件分类表 (树表)树 */
const getSparePartCategoryTree = async () => {
  sparePartCategoryTree.value = []
  const data = await SparePartCategoryApi.getSparePartCategoryList()
  const root: Tree = { id: 0, name: '顶级备件分类表 (树表)', children: [] }
  root.children = handleTree(data, 'id', 'parentId')
  sparePartCategoryTree.value.push(root)
}
</script>