<!-- 预警管理弹窗 -->
<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible" width="1000px">
    <el-tabs v-model="activeTab" type="card">
      <!-- 预警列表 -->
      <el-tab-pane label="预警列表" name="list">
        <el-form
          class="-mb-15px"
          :model="queryParams"
          ref="queryFormRef"
          :inline="true"
          label-width="80px"
        >
          <el-form-item label="预警类型" prop="alertType">
            <el-select
              v-model="queryParams.alertType"
              placeholder="请选择预警类型"
              clearable
              class="!w-200px"
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
              class="!w-200px"
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
              class="!w-200px"
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
            <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
            <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
          </el-form-item>
        </el-form>
        
        <el-table :data="alertList" v-loading="loading" :stripe="true">
          <el-table-column label="备件名称" align="center" prop="sparePartName" width="150" />
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
          <el-table-column label="预警信息" align="center" prop="alertMessage" />
          <el-table-column label="预警状态" align="center" prop="alertStatus" width="100">
            <template #default="scope">
              <dict-tag :type="DICT_TYPE.ALERT_STATUS" :value="scope.row.alertStatus" />
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width="180">
            <template #default="scope">
              {{ dateFormatter(scope.row.createTime) }}
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="200">
            <template #default="scope">
              <el-button
                v-if="scope.row.alertStatus === 1"
                link
                type="success"
                @click="handleAlert(scope.row)"
              >
                处理
              </el-button>
              <el-button
                v-if="scope.row.alertStatus === 1"
                link
                type="warning"
                @click="ignoreAlert(scope.row)"
              >
                忽略
              </el-button>
              <el-button
                link
                type="danger"
                @click="deleteAlert(scope.row)"
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
          @pagination="getAlertList"
        />
      </el-tab-pane>
      
      <!-- 预警设置 -->
      <el-tab-pane label="预警设置" name="settings">
        <el-form
          ref="settingsFormRef"
          :model="settingsForm"
          :rules="settingsRules"
          label-width="120px"
        >
          <el-form-item label="自动检查库存" prop="autoCheckStock">
            <el-switch v-model="settingsForm.autoCheckStock" />
            <span class="ml-2 text-gray-500">每天8点自动检查库存预警</span>
          </el-form-item>
          <el-form-item label="自动检查更换" prop="autoCheckReplacement">
            <el-switch v-model="settingsForm.autoCheckReplacement" />
            <span class="ml-2 text-gray-500">每天9点自动检查更换提醒</span>
          </el-form-item>
          <el-form-item label="预警通知方式" prop="notificationMethods">
            <el-checkbox-group v-model="settingsForm.notificationMethods">
              <el-checkbox label="email">邮件通知</el-checkbox>
              <el-checkbox label="sms">短信通知</el-checkbox>
              <el-checkbox label="system">系统通知</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="通知接收人" prop="receivers">
            <el-select
              v-model="settingsForm.receivers"
              multiple
              placeholder="请选择通知接收人"
              class="!w-400px"
            >
              <el-option
                v-for="user in userList"
                :key="user.id"
                :label="user.nickname"
                :value="user.id"
              />
            </el-select>
          </el-form-item>
        </el-form>
        
        <div class="text-center mt-4">
          <el-button type="primary" @click="saveSettings" :loading="settingsLoading">
            保存设置
          </el-button>
        </div>
      </el-tab-pane>
    </el-tabs>
  </Dialog>
  
  <!-- 处理预警弹窗 -->
  <Dialog title="处理预警" v-model="handleDialogVisible" width="500px">
    <el-form
      ref="handleFormRef"
      :model="handleForm"
      :rules="handleRules"
      label-width="100px"
    >
      <el-form-item label="处理方式" prop="handleType">
        <el-radio-group v-model="handleForm.handleType">
          <el-radio :label="1">补充库存</el-radio>
          <el-radio :label="2">调整阈值</el-radio>
          <el-radio :label="3">其他处理</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="处理备注" prop="handleRemark">
        <el-input
          v-model="handleForm.handleRemark"
          type="textarea"
          placeholder="请输入处理备注"
          :rows="3"
        />
      </el-form-item>
    </el-form>
    
    <template #footer>
      <el-button type="primary" @click="submitHandle" :loading="handleLoading">
        确认处理
      </el-button>
      <el-button @click="handleDialogVisible = false">取消</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { getUserSimpleList } from '@/api/system/user'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dict'

/** 预警管理弹窗 */
defineOptions({ name: 'AlertDialog' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('预警管理') // 弹窗的标题
const activeTab = ref('list') // 当前激活的标签页

// 预警列表
const alertList = ref<any[]>([]) // 预警列表
const loading = ref(false) // 加载中
const total = ref(0) // 总数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  alertType: undefined,
  alertLevel: undefined,
  alertStatus: undefined
})
const queryFormRef = ref() // 查询表单 Ref

// 预警设置
const settingsForm = ref({
  autoCheckStock: true,
  autoCheckReplacement: true,
  notificationMethods: ['system'],
  receivers: []
})
const settingsRules = reactive({
  receivers: [{ required: true, message: '请选择通知接收人', trigger: 'change' }]
})
const settingsFormRef = ref() // 设置表单 Ref
const settingsLoading = ref(false) // 设置保存加载中
const userList = ref<any[]>([]) // 用户列表

// 处理预警
const handleDialogVisible = ref(false) // 处理弹窗显示
const currentAlert = ref<any>({}) // 当前处理的预警
const handleForm = ref({
  handleType: 1,
  handleRemark: undefined
})
const handleRules = reactive({
  handleType: [{ required: true, message: '请选择处理方式', trigger: 'change' }],
  handleRemark: [{ required: true, message: '请输入处理备注', trigger: 'blur' }]
})
const handleFormRef = ref() // 处理表单 Ref
const handleLoading = ref(false) // 处理提交加载中

/** 打开弹窗 */
const open = async () => {
  dialogVisible.value = true
  activeTab.value = 'list'
  await getAlertList()
  await getUserList()
}

/** 获取预警列表 */
const getAlertList = async () => {
  loading.value = true
  try {
    // TODO: 调用预警API
    // const data = await AlertApi.getAlertPage(queryParams)
    // alertList.value = data.list
    // total.value = data.total
    
    // 模拟数据
    alertList.value = [
      {
        id: 1,
        sparePartName: '破碎机轴承',
        alertType: 1,
        alertLevel: 2,
        currentStock: 5,
        thresholdValue: 10,
        alertMessage: '库存不足，当前库存5，低于最低库存10',
        alertStatus: 1,
        createTime: new Date().toISOString()
      },
      {
        id: 2,
        sparePartName: '筛分机筛网',
        alertType: 2,
        alertLevel: 1,
        currentStock: 0,
        thresholdValue: 30,
        alertMessage: '需要更换，已到更换周期',
        alertStatus: 1,
        createTime: new Date(Date.now() - 86400000).toISOString()
      }
    ]
    total.value = alertList.value.length
  } finally {
    loading.value = false
  }
}

/** 获取用户列表 */
const getUserList = async () => {
  try {
    const data = await getUserSimpleList()
    userList.value = data || []
  } catch (error) {
    console.error('获取用户列表失败:', error)
    userList.value = []
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getAlertList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 处理预警 */
const handleAlert = (alert: any) => {
  currentAlert.value = alert
  handleDialogVisible.value = true
  handleForm.value = {
    handleType: 1,
    handleRemark: undefined
  }
}

/** 忽略预警 */
const ignoreAlert = async (alert: any) => {
  try {
    await message.confirm('确认忽略此预警吗？')
    // TODO: 调用忽略API
    // await AlertApi.ignoreAlert(alert.id)
    message.success('预警已忽略')
    await getAlertList()
  } catch {}
}

/** 删除预警 */
const deleteAlert = async (alert: any) => {
  try {
    await message.delConfirm()
    // TODO: 调用删除API
    // await AlertApi.deleteAlert(alert.id)
    message.success('预警已删除')
    await getAlertList()
  } catch {}
}

/** 提交处理 */
const submitHandle = async () => {
  await handleFormRef.value.validate()
  handleLoading.value = true
  try {
    // TODO: 调用处理API
    // await AlertApi.handleAlert(currentAlert.value.id, {
    //   handleType: handleForm.value.handleType,
    //   handleRemark: handleForm.value.handleRemark
    // })
    
    message.success('预警处理成功')
    handleDialogVisible.value = false
    await getAlertList()
  } catch (error) {
    console.error('处理预警失败:', error)
  } finally {
    handleLoading.value = false
  }
}

/** 保存设置 */
const saveSettings = async () => {
  await settingsFormRef.value.validate()
  settingsLoading.value = true
  try {
    // TODO: 调用设置保存API
    // await AlertApi.saveSettings(settingsForm.value)
    
    message.success('设置保存成功')
  } catch (error) {
    console.error('保存设置失败:', error)
  } finally {
    settingsLoading.value = false
  }
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
