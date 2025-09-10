# 设备API调用错误修正总结

## 问题描述

在备件使用记录模块中，设备关联功能显示"设备ID: 1"而不是设备名称"颚式破碎机"，原因是使用了错误的API方法。

## 错误原因

**严重错误**: 使用了不存在的API方法 `EquipmentInfoApi.getEquipmentInfoPage()`

**实际情况**: `EquipmentInfoApi` 中只有 `getEquipmentInfoList()` 方法，没有分页查询方法。

## 修正内容

### 1. 修正 index.vue 中的设备列表获取

**错误代码**:
```typescript
const data = await EquipmentInfoApi.getEquipmentInfoPage({ pageNo: 1, pageSize: 100 })
equipmentList.value = data.list || []
```

**正确代码**:
```typescript
const data = await EquipmentInfoApi.getEquipmentInfoList({})
equipmentList.value = data || []
```

### 2. 修正 SparePartUsageRecordForm.vue 中的设备列表获取

**错误代码**:
```typescript
const data = await EquipmentInfoApi.getEquipmentInfoPage({ pageNo: 1, pageSize: 100 })
equipmentList.value = data.list || []
```

**正确代码**:
```typescript
const data = await EquipmentInfoApi.getEquipmentInfoList({})
equipmentList.value = data || []
```

## EquipmentInfoApi 实际结构

```typescript
export const EquipmentInfoApi = {
  // 查询设备档案列表
  getEquipmentInfoList: async (params) => {
    return await request.get({ url: `/coal/equipment-info/list`, params })
  },

  // 查询设备档案详情
  getEquipmentInfo: async (id: number) => {
    return await request.get({ url: `/coal/equipment-info/get?id=` + id })
  },

  // 新增设备档案
  createEquipmentInfo: async (data: EquipmentInfo) => {
    return await request.post({ url: `/coal/equipment-info/create`, data })
  },

  // 修改设备档案
  updateEquipmentInfo: async (data: EquipmentInfo) => {
    return await request.put({ url: `/coal/equipment-info/update`, data })
  },

  // 删除设备档案
  deleteEquipmentInfo: async (id: number) => {
    return await request.delete({ url: `/coal/equipment-info/delete?id=` + id })
  },

  // 导出设备档案 Excel
  exportEquipmentInfo: async (params) => {
    return await request.download({ url: `/coal/equipment-info/export-excel`, params })
  },
}
```

## 数据库验证

根据用户提供的数据：

**设备档案表 (coal_equipment_info)**:
- ID 1: 颚式破碎机 (PE600×900)
- ID 2: 圆锥破碎机 (PYB1750)  
- ID 3: 跳汰机 (LTX-35)
- ID 4: 重介分选机 (HM-25)
- ID 5: 离心脱水机 (LWZ-1200)
- ID 6: 压滤机 (XMZ-500)
- ID 7: 皮带输送机 (DT75-1000)
- ID 8: 刮板输送机 (SGB-630/150)
- ID 9: 主电机 (Y2-315M-4)
- ID 10: 配电柜 (GGD-400A)

**备件使用记录表 (coal_spare_part_usage_record)**:
- 记录3: equipment_id = 1 (应该显示"颚式破碎机")
- 记录4: equipment_id = 2 (应该显示"圆锥破碎机")
- 记录5: equipment_id = 3 (应该显示"跳汰机")
- 记录6: equipment_id = 3 (应该显示"跳汰机")
- 记录7: equipment_id = 2 (应该显示"圆锥破碎机")
- 记录8: equipment_id = 1 (应该显示"颚式破碎机")

## 预期结果

修正后，备件使用记录页面应该：

1. **控制台输出**: `设备列表加载完成: 10 条记录`
2. **搜索栏**: 设备下拉框显示设备名称列表
3. **列表显示**: 关联设备列显示设备名称而不是"设备ID: X"
4. **表单页面**: 设备下拉框显示设备名称列表

## 教训

**重要**: 在使用API方法前，必须先检查API文件的实际结构，不能凭想象使用不存在的方法。这是一个严重的开发错误，会导致功能完全无法正常工作。

## 测试步骤

1. 刷新备件使用记录页面
2. 打开浏览器开发者工具控制台
3. 查看是否显示: `设备列表加载完成: 10 条记录`
4. 检查关联设备列是否显示设备名称
5. 测试搜索栏和表单的设备下拉选择
