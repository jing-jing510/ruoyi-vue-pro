# 备件库存统计API数据库错误修复总结

## 🚨 问题描述

用户调用备件库存统计API时，后端日志显示大量数据库错误：

1. **字段缺失错误**: `Field 'warehouse_location' doesn't have a default value`
2. **重复数据错误**: `Expected one result (or null) to be returned by selectOne(), but found: 2`

## 🔍 问题分析

### 1. 字段缺失错误分析
**错误信息**: `Field 'warehouse_location' doesn't have a default value`

**根本原因**:
- 数据库表 `coal_spare_part_stock` 中的 `warehouse_location` 字段定义为 `NOT NULL`
- 但在创建新库存记录时，`getOrCreateStock` 方法没有设置这个字段的值
- 导致插入操作失败

**数据库表结构**:
```sql
CREATE TABLE `coal_spare_part_stock` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '库存记录ID',
  `spare_part_id` bigint NOT NULL COMMENT '备件ID',
  `warehouse_location` varchar(100) NOT NULL COMMENT '仓库位置', -- 问题字段
  `stock_type` int DEFAULT NULL COMMENT '库存类型',
  `quantity` decimal(10,2) DEFAULT NULL COMMENT '库存数量',
  -- 其他字段...
)
```

### 2. 重复数据错误分析
**错误信息**: `Expected one result (or null) to be returned by selectOne(), but found: 2`

**根本原因**:
- 数据库中存在重复的 `spare_part_id` 记录
- `selectBySparePartId` 方法使用 `selectOne()` 查询，期望返回唯一结果
- 但实际返回了多条记录，导致异常

**重复数据查询结果**:
```sql
SELECT spare_part_id, COUNT(*) as count 
FROM coal_spare_part_stock 
GROUP BY spare_part_id 
HAVING count > 1;

+---------------+-------+
| spare_part_id | count |
+---------------+-------+
|             5 |     2 |
+---------------+-------+
```

## 🔧 修复方案

### 1. 修复字段缺失问题

**文件**: `yudao-module-coal/src/main/java/cn/iocoder/yudao/module/coal/service/sparepartstock/SparePartStockServiceImpl.java`

**修复内容**: 在 `getOrCreateStock` 方法中添加必需字段的默认值

```java
private SparePartStockDO getOrCreateStock(Long sparePartId) {
    // 查找现有库存记录
    SparePartStockDO stock = sparePartStockMapper.selectBySparePartId(sparePartId);
    
    if (stock == null) {
        // 创建新的库存记录
        stock = new SparePartStockDO();
        stock.setSparePartId(sparePartId);
        stock.setQuantity(BigDecimal.ZERO);
        stock.setStockType(1); // 默认库存类型
        stock.setWarehouseLocation("默认仓库"); // 设置默认仓库位置
        stock.setUnitCost(BigDecimal.ZERO); // 设置默认单位成本
        stock.setTotalCost(BigDecimal.ZERO); // 设置默认总成本
        sparePartStockMapper.insert(stock);
    }
    
    return stock;
}
```

### 2. 修复重复数据查询问题

**文件**: `yudao-module-coal/src/main/java/cn/iocoder/yudao/module/coal/dal/mysql/sparepartstock/SparePartStockMapper.java`

**修复内容**: 修改 `selectBySparePartId` 方法，使用 `selectList` 并限制返回结果

```java
default SparePartStockDO selectBySparePartId(Long sparePartId) {
    List<SparePartStockDO> stocks = selectList(new LambdaQueryWrapperX<SparePartStockDO>()
            .eq(SparePartStockDO::getSparePartId, sparePartId)
            .orderByDesc(SparePartStockDO::getId)
            .last("LIMIT 1"));
    return stocks.isEmpty() ? null : stocks.get(0);
}
```

## 📋 修复详情

### 1. 字段默认值设置
- **warehouse_location**: 设置为 "默认仓库"
- **unit_cost**: 设置为 BigDecimal.ZERO
- **total_cost**: 设置为 BigDecimal.ZERO

### 2. 查询逻辑优化
- 使用 `selectList` 替代 `selectOne`
- 添加 `ORDER BY id DESC LIMIT 1` 确保返回最新的记录
- 添加空列表检查，避免索引越界

### 3. 错误处理改进
- 保持原有的 try-catch 机制
- 确保单个备件处理失败不影响整体统计
- 记录详细的错误日志便于调试

## 🧪 测试验证

### 1. 数据库验证
```sql
-- 检查重复数据是否仍然存在
SELECT spare_part_id, COUNT(*) as count 
FROM coal_spare_part_stock 
GROUP BY spare_part_id 
HAVING count > 1;

-- 检查新创建的记录是否包含必需字段
SELECT id, spare_part_id, warehouse_location, unit_cost, total_cost 
FROM coal_spare_part_stock 
WHERE warehouse_location = '默认仓库';
```

### 2. API测试
- 调用 `/admin-api/coal/spare-part-info/stock-statistics` 接口
- 验证返回数据是否正常
- 检查后端日志是否还有错误信息

### 3. 功能测试
- 验证库存统计功能正常工作
- 确认首页统计卡片显示正确
- 测试库存自动更新机制

## 🎯 预期效果

### 1. 错误消除
- ✅ 消除 `warehouse_location` 字段缺失错误
- ✅ 消除重复数据查询错误
- ✅ 统计API正常返回数据

### 2. 功能正常
- ✅ 库存统计功能正常工作
- ✅ 首页统计卡片显示正确数据
- ✅ 库存自动更新机制正常

### 3. 数据完整性
- ✅ 新创建的库存记录包含所有必需字段
- ✅ 查询逻辑处理重复数据情况
- ✅ 统计计算基于正确的库存数据

## 📝 后续优化建议

### 1. 数据清理
- 清理数据库中的重复库存记录
- 建立唯一索引防止重复数据
- 添加数据完整性约束

### 2. 代码优化
- 添加更完善的错误处理
- 优化查询性能
- 添加数据验证逻辑

### 3. 监控改进
- 添加数据库操作监控
- 设置异常告警机制
- 定期检查数据质量

---

**修复时间**: 2025-01-09  
**修复人员**: 开发团队  
**测试状态**: 待测试  
**部署状态**: 待部署
