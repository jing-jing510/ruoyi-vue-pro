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
      <el-form-item label="业务类型" prop="businessType">
        <el-select
          v-model="queryParams.businessType"
          placeholder="请选择业务类型"
          clearable
          class="!w-240px"
        >
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="业务ID" prop="businessId">
        <el-input
          v-model="queryParams.businessId"
          placeholder="请输入业务ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
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
          v-hasPermi="['coal:safety-attachment:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['coal:safety-attachment:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button
            type="danger"
            plain
            :disabled="isEmpty(checkedIds)"
            @click="handleDeleteBatch"
            v-hasPermi="['coal:safety-attachment:delete']"
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
      <el-table-column label="附件ID" align="center" prop="id" />
      <el-table-column label="业务类型" align="center" width="120px">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.COAL_SAFETY_ATTACHMENT_BUSINESS_TYPE" :value="scope.row.businessType" />
        </template>
      </el-table-column>
      <el-table-column label="业务记录" align="center" width="200px">
        <template #default="scope">
          <span>{{ getBusinessRecordName(scope.row.businessType, scope.row.businessId) }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="文件名" align="center" prop="fileName" />-->
      <el-table-column label="文件路径" align="center" width="200px">
        <template #default="scope">
          <el-button
            v-if="scope.row.filePath"
            link
            type="primary"
            @click="handleDownload(scope.row.filePath)"
            :title="scope.row.filePath"
          >
            <Icon icon="ep:download" class="mr-5px" />
            下载文件
          </el-button>
          <span v-else>-</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="文件大小(字节)" align="center" prop="fileSize" />-->
<!--      <el-table-column label="文件类型" align="center" prop="fileType" />-->
<!--      <el-table-column label="文件扩展名" align="center" prop="fileExtension" />-->
      <el-table-column
        label="上传时间"
        align="center"
        prop="uploadTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="上传人ID" align="center" prop="uploaderId" />
      <el-table-column label="上传人姓名" align="center" prop="uploaderName" />
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
            v-hasPermi="['coal:safety-attachment:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['coal:safety-attachment:delete']"
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
  <SafetyAttachmentForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { isEmpty } from '@/utils/is'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { SafetyAttachmentApi, SafetyAttachment } from '@/api/coal/safetyattachment'
import { SafetyCheckRecordApi } from '@/api/coal/safetycheckrecord'
import { SafetyAccidentApi } from '@/api/coal/safetyaccident'
import { DICT_TYPE } from '@/utils/dict'
import SafetyAttachmentForm from './SafetyAttachmentForm.vue'

/** 安全附件 列表 */
defineOptions({ name: 'SafetyAttachment' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<SafetyAttachment[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数

// 业务记录缓存
const businessRecordCache = ref<Map<string, string>>(new Map())

// 响应式的业务记录名称映射
const businessRecordNames = ref<Map<string, string>>(new Map())
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  businessType: undefined,
  businessId: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SafetyAttachmentApi.getSafetyAttachmentPage(queryParams)
    list.value = data.list
    total.value = data.total
    
    // 批量加载业务记录名称
    await loadBusinessRecordNames(data.list)
  } finally {
    loading.value = false
  }
}

/** 批量加载业务记录名称 */
const loadBusinessRecordNames = async (attachments: SafetyAttachment[]) => {
  const promises = attachments.map(attachment => {
    if (attachment.businessType && attachment.businessId) {
      return loadBusinessRecordName(attachment.businessType, attachment.businessId)
    }
    return Promise.resolve()
  })
  
  await Promise.allSettled(promises)
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
    await SafetyAttachmentApi.deleteSafetyAttachment(id)
    message.success(t('common.delSuccess'))
    currentRow.value = {}
    // 刷新列表
    await getList()
  } catch {}
}

/** 批量删除安全附件 */
const handleDeleteBatch = async () => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await SafetyAttachmentApi.deleteSafetyAttachmentList(checkedIds.value);
    checkedIds.value = [];
    message.success(t('common.delSuccess'))
    await getList();
  } catch {}
}

const checkedIds = ref<number[]>([])
const handleRowCheckboxChange = (records: SafetyAttachment[]) => {
  checkedIds.value = records.map((item) => item.id);
}

/** 选中行操作 */
const currentRow = ref({}) // 选中行

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await SafetyAttachmentApi.exportSafetyAttachment(queryParams)
    download.excel(data, '安全附件.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 获取业务记录名称 */
const getBusinessRecordName = (businessType: number, businessId: number): string => {
  if (!businessType || !businessId) {
    return '-'
  }
  
  const cacheKey = `${businessType}-${businessId}`
  return businessRecordNames.value.get(cacheKey) || '加载中...'
}

/** 异步加载业务记录名称 */
const loadBusinessRecordName = async (businessType: number, businessId: number) => {
  try {
    let recordName = ''
    const cacheKey = `${businessType}-${businessId}`
    
    switch (businessType) {
      case 1: // 安全检查记录
        const checkRecord = await SafetyCheckRecordApi.getSafetyCheckRecord(businessId)
        recordName = checkRecord?.recordName || `安全检查记录-${businessId}`
        break
      case 2: // 安全检查项目
        // 安全检查项目通常没有独立的名称，使用记录ID
        recordName = `安全检查项目-${businessId}`
        break
      case 3: // 安全事故记录
        const accidentRecord = await SafetyAccidentApi.getSafetyAccident(businessId)
        recordName = accidentRecord?.accidentTitle || `安全事故记录-${businessId}`
        break
      default:
        recordName = `未知业务-${businessId}`
    }
    
    // 更新响应式映射
    businessRecordNames.value.set(cacheKey, recordName)
    businessRecordCache.value.set(cacheKey, recordName)
  } catch (error) {
    console.error('获取业务记录名称失败:', error)
    const cacheKey = `${businessType}-${businessId}`
    const errorName = `获取失败-${businessId}`
    businessRecordNames.value.set(cacheKey, errorName)
    businessRecordCache.value.set(cacheKey, errorName)
  }
}

/** 文件下载操作 */
const handleDownload = (filePath: string) => {
  if (!filePath) {
    message.warning('文件路径为空，无法下载')
    return
  }
  
  try {
    // 创建一个临时的a标签来触发下载
    const link = document.createElement('a')
    link.href = filePath
    link.download = filePath.split('/').pop() || 'download' // 从路径中提取文件名
    link.target = '_blank'
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    
    message.success('文件下载已开始')
  } catch (error) {
    console.error('文件下载失败:', error)
    message.error('文件下载失败，请检查文件路径是否正确')
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
