# 积木报表API数据集配置指南

## 概述

积木报表是一个强大的数据可视化工具，支持通过API接口获取数据。在使用API数据集时，需要特别注意权限验证、多租户处理和数据结构格式等问题。

## 配置要点

### 1. 权限验证处理

积木报表无法携带认证信息，因此需要取消API接口的权限验证：

```java
@TenantIgnore
@PermitAll
@GetMapping("/your-api-endpoint")
@Operation(summary = "API接口描述")
public CommonResult<YourResponseVO> yourApiMethod() {
    // 接口实现
    return success(data);
}
```

**关键注解：**
- `@PermitAll`：允许所有用户访问，无需权限验证
- `@TenantIgnore`：忽略多租户处理

### 2. 多租户配置

在 `application.yaml` 文件中添加忽略路径配置：

```yaml
# 多租户配置
tenant:
  ignore-urls:
    - /jmreport/*                    # 积木报表相关路径
    - /admin-api/coal/production-daily-report/plan-progress  # 具体API路径
```

**说明：**
- `/jmreport/*`：积木报表系统路径，无法携带租户编号
- 具体的API路径：根据实际接口路径添加

### 3. 数据结构要求

积木报表要求API返回的数据结构必须是**强一致**的数组格式：

#### ❌ 错误格式（对象结构）
```json
{
  "code": 0,
  "msg": "",
  "data": {
    "field1": "value1",
    "field2": "value2"
  }
}
```

#### ✅ 正确格式（数组结构）
```json
{
  "code": 0,
  "msg": "",
  "data": [
    {
      "field1": "value1",
      "field2": "value2"
    }
  ]
}
```

#### 代码实现示例

```java
// 返回单个对象时，需要包装成数组
@GetMapping("/plan-progress")
public CommonResult<List<ProductionPlanProgressRespVO>> getProductionPlanProgress() {
    ProductionPlanProgressRespVO progress = service.getData();
    return success(Arrays.asList(progress)); // 包装成数组
}
```

## 完整配置示例

### Controller层配置

```java
@RestController
@RequestMapping("/coal/production-daily-report")
@Validated
public class ProductionDailyReportController {

    @Resource
    private ProductionDailyReportService productionDailyReportService;

    @TenantIgnore
    @PermitAll
    @GetMapping("/plan-progress")
    @Operation(summary = "获得生产计划进展统计信息")
    public CommonResult<List<ProductionPlanProgressRespVO>> getProductionPlanProgress() {
        ProductionPlanProgressRespVO progress = productionDailyReportService.getProductionPlanProgress();
        return success(Arrays.asList(progress));
    }
}
```

### application.yaml配置

```yaml
# 多租户配置
tenant:
  ignore-urls:
    - /jmreport/*
    - /admin-api/coal/production-daily-report/plan-progress
    - /admin-api/your-module/your-endpoint  # 添加其他需要的API路径
```

### 积木报表配置

1. **数据集配置**
   - 数据集名称：自定义名称
   - 数据类型：API
   - 请求方式：GET/POST
   - API接口：`http://localhost:48080/admin-api/coal/production-daily-report/plan-progress`

2. **字段配置**
   - 点击"查询解析"自动获取字段
   - 或手动添加字段映射

## 常见问题

### 1. 401未登录错误
**原因：** API接口仍有权限验证
**解决：** 添加 `@PermitAll` 注解

### 2. 多租户错误
**原因：** 未配置租户忽略路径
**解决：** 在 `application.yaml` 中添加 `ignore-urls` 配置

### 3. 字段解析失败
**原因：** 数据结构不是数组格式
**解决：** 将返回数据包装成 `List` 格式

### 4. 积木报表无法显示数据
**原因：** API返回格式不符合要求
**解决：** 确保返回格式为 `{"data": [...]}` 结构

## 最佳实践

1. **安全性考虑**
   - 仅对必要的报表接口取消权限验证
   - 考虑添加IP白名单或其他安全措施

2. **性能优化**
   - 对报表接口进行缓存处理
   - 避免复杂的数据查询逻辑

3. **维护性**
   - 统一API接口命名规范
   - 添加详细的接口文档注释

4. **测试验证**
   - 使用浏览器直接访问API验证返回格式
   - 在积木报表中测试字段解析功能

## 总结

使用积木报表API数据集时，核心要点：

1. ✅ 取消权限验证：`@PermitAll`
2. ✅ 忽略多租户：`@TenantIgnore`
3. ✅ 配置忽略路径：`application.yaml` 中的 `ignore-urls`
4. ✅ 数据结构强一致：返回数组格式 `{"data": [...]}`

遵循这些要点，可以确保积木报表能够正确获取和显示API数据。
