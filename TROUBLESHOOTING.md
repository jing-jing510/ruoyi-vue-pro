# 报警事件页面加载问题排查指南

## 问题现象
报警事件页面打开后加载不出来

## 排查步骤

### 1. 检查浏览器控制台错误
打开浏览器开发者工具（F12），查看：
- **Console标签**：是否有JavaScript错误
- **Network标签**：查看API请求是否成功
  - 找到 `/admin-api/opcua/alarm-event/page` 请求
  - 查看状态码（应该是200）
  - 查看响应内容

### 2. 常见错误及解决方案

#### 错误1：Cannot find module './AlarmForm.vue'
**原因**：AlarmForm.vue文件未创建或路径错误
**解决**：确认文件存在于 `yudao-ui/yudao-ui-admin-vue3/src/views/opcua/alarm/AlarmForm.vue`

#### 错误2：AlarmEventVO is not defined
**原因**：类型导入错误
**解决**：已修复，确保 AlarmForm.vue 中有：
```typescript
import { createAlarmEvent, updateAlarmEvent, getAlarmEvent, type AlarmEventVO } from '@/api/opcua/alarm'
```

#### 错误3：API请求404或500
**原因**：后端服务未启动或接口路径错误
**解决**：
- 确认后端服务已启动
- 检查后端日志是否有错误
- 确认接口路径：`POST /admin-api/opcua/alarm-event/page`

#### 错误4：权限错误
**原因**：用户没有查询权限
**解决**：检查用户是否有 `opcua:alarm:query` 权限

### 3. 清除缓存重试

#### 前端缓存清除
```bash
# 在 yudao-ui/yudao-ui-admin-vue3 目录下执行
npm run clean
npm install
npm run dev
```

#### 浏览器缓存清除
- Chrome：Ctrl+Shift+Delete → 清除缓存
- 或者使用无痕模式测试

#### 后端缓存清除
- IDEA：Build → Rebuild Project
- 重启后端服务

### 4. 逐步测试

#### 测试1：API是否正常
在浏览器控制台执行：
```javascript
fetch('/admin-api/opcua/alarm-event/page?pageNo=1&pageSize=10', {
  headers: {
    'Authorization': 'Bearer ' + localStorage.getItem('token')
  }
}).then(r => r.json()).then(console.log)
```

#### 测试2：组件是否正常加载
检查 Vue DevTools 中是否有 OpcuaAlarm 组件

#### 测试3：数据是否正常
在 Vue DevTools 中查看组件的 data：
- loading 状态
- list 数据
- total 数量

### 5. 回退方案

如果问题无法解决，可以临时注释掉新增的功能：

在 `index.vue` 中：
```vue
<!-- 临时注释掉新增按钮 -->
<!--
<el-button
  type="primary"
  plain
  @click="openForm('create')"
  v-hasPermi="['opcua:alarm:create']"
>
  <Icon icon="ep:plus" class="mr-5px" /> 新增
</el-button>
-->
```

在操作列中：
```vue
<!-- 临时注释掉编辑按钮 -->
<!--
<el-button
  link
  type="primary"
  @click="openForm('update', scope.row.id)"
  v-hasPermi="['opcua:alarm:update']"
>
  编辑
</el-button>
-->
```

注释掉表单组件引用：
```vue
<!-- 临时注释掉 -->
<!-- <AlarmForm ref="formRef" @success="getList" /> -->
```

```typescript
// 临时注释掉
// import AlarmForm from './AlarmForm.vue'
```

### 6. 获取详细错误信息

如果以上步骤都无法解决，请提供：
1. 浏览器控制台的完整错误信息（截图）
2. Network标签中API请求的详细信息（截图）
3. 后端日志中的错误信息（如果有）

## 快速修复命令

```bash
# 1. 清理前端
cd yudao-ui/yudao-ui-admin-vue3
npm run clean
npm install

# 2. 重新编译后端
# 在IDEA中：Build → Rebuild Project

# 3. 重启服务
# 停止后端服务，重新启动

# 4. 清除浏览器缓存
# Ctrl+Shift+Delete 或使用无痕模式

# 5. 重新访问页面
```

## 预期正常状态

页面正常加载后应该看到：
- 搜索表单（设备名称、报警级别、报警状态、报警时间）
- 新增按钮
- 报警事件列表
- 操作列包含：详情、编辑、处理、删除按钮
