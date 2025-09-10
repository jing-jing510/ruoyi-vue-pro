# 业务流程卡片路由跳转修正总结 - 使用name方式

## 问题反思

我之前确实犯了一个严重的错误：
1. **忽略了已有的成功案例**: 检修单页面跳转到备件使用记录页面已经成功使用了 `name` 方式跳转
2. **没有遵循一致性原则**: 在同一个项目中应该使用相同的跳转方式
3. **没有仔细查看组件名称**: 应该使用 `defineOptions({ name: 'ComponentName' })` 中定义的名称

## 正确的跳转方式

### 1. 检修单页面的成功案例
```typescript
// 检修单页面中的正确跳转方式（已验证可用）
router.push({
  name: 'SparePartUsageRecord',
  query: {
    workOrderId: maintenanceOrder.id,
    equipmentId: maintenanceOrder.equipmentId
  }
})
```

### 2. 芋道框架的路由跳转文档
根据您提供的文档：
```typescript
// 简单跳转
this.$router.push({ path: "/system/user" });

// 跳转页面并设置请求参数，使用 `query` 属性
this.$router.push({ path: "/system/user", query: {id: "1", name: "芋道"} });
```

但在Vue 3 Composition API中，应该使用 `router.push` 而不是 `this.$router.push`。

### 3. 组件名称映射

通过查询各个组件的 `defineOptions` 定义，得到正确的组件名称：

| 功能模块 | 组件名称 | 文件路径 |
|---------|---------|----------|
| 检修计划管理 | MaintenancePlan | coal/maintenanceplan/index.vue |
| 报修单管理 | RepairRequest | coal/repairrequest/index.vue |
| 检修单管理 | MaintenanceOrder | coal/maintenanceorder/index.vue |
| 备件使用记录管理 | SparePartUsageRecord | coal/sparepartusagerecord/index.vue |
| 备件库存管理 | SparePartStock | coal/sparepartstock/index.vue |

## 修正后的跳转方法

**修正前（错误的路径方式）**:
```typescript
const goToMaintenancePlan = () => {
  router.push('/coal/maintenance-plan')
}

const goToRepairRequest = () => {
  router.push('/coal/repair-request')
}

const goToMaintenanceOrder = () => {
  router.push('/coal/maintenance-order')
}

const goToSparePartUsageRecord = () => {
  router.push('/coal/spare-part-usage-record')
}

const goToSparePartStock = () => {
  router.push('/coal/spare-part-stock')
}
```

**修正后（正确的name方式）**:
```typescript
const goToMaintenancePlan = () => {
  router.push({ name: 'MaintenancePlan' })
}

const goToRepairRequest = () => {
  router.push({ name: 'RepairRequest' })
}

const goToMaintenanceOrder = () => {
  router.push({ name: 'MaintenanceOrder' })
}

const goToSparePartUsageRecord = () => {
  router.push({ name: 'SparePartUsageRecord' })
}

const goToSparePartStock = () => {
  router.push({ name: 'SparePartStock' })
}
```

## 为什么使用name方式更好

### 1. **一致性**
- 与已有的成功跳转保持一致
- 整个项目使用统一的跳转方式

### 2. **可靠性**
- 不依赖路径配置，直接使用组件名称
- 避免了路径匹配问题

### 3. **可维护性**
- 即使菜单路径发生变化，组件名称通常保持稳定
- 更容易追踪和调试

### 4. **符合Vue Router最佳实践**
- 命名路由是Vue Router推荐的方式
- 提供更好的类型安全性（在TypeScript中）

## 芋道框架中的使用

在芋道框架中，路由跳转有两种方式：

### 1. 路径方式（适用于简单跳转）
```typescript
router.push({ path: "/system/user" })
```

### 2. 名称方式（适用于组件跳转，推荐）
```typescript
router.push({ name: 'ComponentName' })
```

### 3. 带参数跳转
```typescript
// 路径方式
router.push({ 
  path: "/system/user", 
  query: { id: "1", name: "芋道" } 
})

// 名称方式（推荐）
router.push({ 
  name: 'ComponentName', 
  query: { id: "1", name: "芋道" } 
})
```

## 测试验证

修正后，业务流程组件卡片的所有跳转都应该能够正常工作：

1. **检修计划节点** → 跳转到检修计划管理页面
2. **报修单节点** → 跳转到报修单管理页面
3. **检修单节点** → 跳转到检修单管理页面
4. **备件使用记录节点** → 跳转到备件使用记录管理页面
5. **库存更新节点** → 跳转到备件库存管理页面
6. **快速操作按钮** → 所有按钮都应该正确跳转

## 教训总结

1. **保持一致性**: 在同一个项目中，相似的功能应该使用相同的实现方式
2. **学习已有案例**: 先查看项目中已有的成功实现，然后复用相同的模式
3. **仔细阅读文档**: 理解框架的约定和最佳实践
4. **验证组件名称**: 使用name方式跳转时，必须确认组件的实际名称定义
5. **完整测试**: 修改后立即进行完整的功能测试

## 相关文件

- `yudao-ui/yudao-ui-admin-vue3/src/components/EquipmentMaintenanceBusinessFlowCard.vue` - 业务流程组件（已修正）
- `yudao-ui/yudao-ui-admin-vue3/src/views/coal/maintenanceorder/index.vue` - 检修单页面跳转成功案例
- 各个coal模块的index.vue文件 - 组件名称定义
