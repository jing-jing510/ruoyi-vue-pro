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
      <el-form-item label="点位名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入点位名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="是否报警" prop="isAlarm">
        <el-select v-model="queryParams.isAlarm" placeholder="请选择" clearable class="!w-240px">
          <el-option label="是" :value="true" />
          <el-option label="否" :value="false" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['opcua:tag:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增点位
        </el-button>
        <el-button type="success" plain @click="handleBrowseNodes">
          <Icon icon="ep:connection" class="mr-5px" /> 浏览OPC UA节点
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="点位名称" prop="name" />
      <el-table-column label="设备名称" prop="deviceName" />
      <el-table-column label="NodeId" prop="nodeId" min-width="200" />
      <el-table-column label="数据类型" prop="dataType" />
      <el-table-column label="是否报警" prop="isAlarm">
        <template #default="scope">
          <el-tag v-if="scope.row.isAlarm" type="danger">是</el-tag>
          <el-tag v-else type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="报警级别" prop="alarmLevel">
        <template #default="scope">
          <span v-if="scope.row.alarmLevel === 1">提示</span>
          <span v-else-if="scope.row.alarmLevel === 2">警告</span>
          <span v-else-if="scope.row.alarmLevel === 3">错误</span>
          <span v-else-if="scope.row.alarmLevel === 4">严重</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="enabled">
        <template #default="scope">
          <el-tag v-if="scope.row.enabled" type="success">启用</el-tag>
          <el-tag v-else type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['opcua:tag:update']"
          >
            修改
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['opcua:tag:delete']"
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

  <!-- 浏览节点对话框 -->
  <Dialog title="浏览 OPC UA 节点" v-model="browseVisible" width="1200px">
    <el-row :gutter="20">
      <!-- 左侧：文件夹树 -->
      <el-col :span="8">
        <div class="mb-4">
          <el-select
            v-model="selectedConfigId"
            placeholder="请选择OPC UA配置"
            @change="loadFolders"
            class="!w-full"
          >
            <el-option
              v-for="config in configList"
              :key="config.id"
              :label="config.name"
              :value="config.id"
            />
          </el-select>
        </div>
        <el-card shadow="never" style="max-height: 500px; overflow-y: auto">
          <template #header>
            <div style="display: flex; align-items: center">
              <Icon icon="ep:folder" class="mr-2" />
              <span>文件夹结构</span>
            </div>
          </template>
          <el-tree
            :data="folderTree"
            :props="{ label: 'displayName', children: 'children' }"
            node-key="nodeId"
            :expand-on-click-node="false"
            @node-click="handleFolderClick"
            v-loading="folderLoading"
          >
            <template #default="{ node }">
              <span style="display: flex; align-items: center">
                <Icon icon="ep:folder" class="mr-1" />
                <span>{{ node.label }}</span>
              </span>
            </template>
          </el-tree>
        </el-card>
      </el-col>

      <!-- 右侧：变量列表 -->
      <el-col :span="16">
        <div class="mb-4">
          <el-input
            v-model="nodeSearchText"
            placeholder="搜索节点名称"
            clearable
            class="!w-full"
          >
            <template #prefix>
              <Icon icon="ep:search" />
            </template>
          </el-input>
        </div>
        <el-table
          :data="filteredNodeList"
          v-loading="browseLoading"
          @selection-change="handleSelectionChange"
          max-height="500"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column label="节点名称" prop="displayName" min-width="120" />
          <el-table-column label="节点ID" prop="nodeId" min-width="150" show-overflow-tooltip />
          <el-table-column label="数据类型" prop="dataType" width="100" />
          <el-table-column label="当前值" prop="value" width="100" show-overflow-tooltip />
        </el-table>
      </el-col>
    </el-row>

    <template #footer>
      <div style="text-align: left; margin-bottom: 10px">
        <el-text type="info">已选择 {{ selectedNodes.length }} 个节点</el-text>
      </div>
      <el-button @click="browseVisible = false">取消</el-button>
      <el-button type="primary" @click="handleImportNodes" :disabled="selectedNodes.length === 0">
        导入选中节点
      </el-button>
    </template>
  </Dialog>

  <!-- 表单弹窗：添加/修改 -->
  <TagForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts" name="OpcuaTag">
import { getTagPage, deleteTag, batchImportTags } from '@/api/opcua/tag'
import { getConfigPage, browseNodes, browseFolders } from '@/api/opcua/config'
import TagForm from './TagForm.vue'

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: undefined,
  isAlarm: undefined
})
const queryFormRef = ref() // 搜索的表单
const formRef = ref() // 表单 Ref

const browseVisible = ref(false)
const browseLoading = ref(false)
const folderLoading = ref(false)
const nodeList = ref([])
const nodeSearchText = ref('')
const selectedNodes = ref([])
const selectedConfigId = ref<number>()
const configList = ref([])
const folderTree = ref([])
const currentFolderNodeId = ref<string>()

/** 过滤节点列表 */
const filteredNodeList = computed(() => {
  if (!nodeSearchText.value) {
    return nodeList.value
  }
  return nodeList.value.filter((node: any) =>
    node.displayName.toLowerCase().includes(nodeSearchText.value.toLowerCase())
  )
})

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await getTagPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 加载配置列表 */
const loadConfigList = async () => {
  const data = await getConfigPage({ pageNo: 1, pageSize: 100 })
  configList.value = data.list
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
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    await message.delConfirm()
    await deleteTag(id)
    message.success(t('common.delSuccess'))
    await getList()
  } catch {}
}

/** 浏览节点 */
const handleBrowseNodes = () => {
  if (configList.value.length === 0) {
    message.warning('请先创建OPC UA配置')
    return
  }
  browseVisible.value = true
  selectedConfigId.value = configList.value[0].id
  loadFolders()
}

/** 加载文件夹树 */
const loadFolders = async (parentNodeId?: string) => {
  if (!selectedConfigId.value) return

  folderLoading.value = true
  try {
    const data = await browseFolders(selectedConfigId.value, parentNodeId)
    
    if (!parentNodeId) {
      // 根节点，构建初始树
      folderTree.value = data.map((folder: any) => ({
        ...folder,
        children: [] // 懒加载子节点
      }))
    }
    
    // 默认加载第一个文件夹的变量
    if (data.length > 0 && !parentNodeId) {
      await loadNodesInFolder(data[0].nodeId)
    }
  } finally {
    folderLoading.value = false
  }
}

/** 点击文件夹 */
const handleFolderClick = async (folder: any) => {
  currentFolderNodeId.value = folder.nodeId
  await loadNodesInFolder(folder.nodeId)
  
  // 懒加载子文件夹
  if (!folder.children || folder.children.length === 0) {
    folderLoading.value = true
    try {
      const subFolders = await browseFolders(selectedConfigId.value!, folder.nodeId)
      folder.children = subFolders.map((f: any) => ({
        ...f,
        children: []
      }))
    } finally {
      folderLoading.value = false
    }
  }
}

/** 加载文件夹中的变量节点 */
const loadNodesInFolder = async (folderNodeId: string) => {
  if (!selectedConfigId.value) return

  browseLoading.value = true
  try {
    const data = await browseNodes(selectedConfigId.value, folderNodeId)
    nodeList.value = data
  } finally {
    browseLoading.value = false
  }
}

/** 选择变化 */
const handleSelectionChange = (selection: any[]) => {
  selectedNodes.value = selection
}

/** 导入节点 */
const handleImportNodes = async () => {
  if (selectedNodes.value.length === 0) {
    message.warning('请选择要导入的节点')
    return
  }

  const tags = selectedNodes.value.map((node: any) => ({
    configId: selectedConfigId.value,
    deviceName: '请填写设备名称',
    name: node.displayName,
    nodeId: node.nodeId,
    dataType: node.dataType,
    isAlarm: true,
    alarmLevel: 2,
    alarmContent: `${node.displayName}报警`,
    enabled: true
  }))

  try {
    await batchImportTags(tags)
    message.success('导入成功')
    browseVisible.value = false
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(() => {
  loadConfigList()
  getList()
})
</script>
