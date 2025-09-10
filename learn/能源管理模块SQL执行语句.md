# 能源管理模块SQL执行语句

## 1. 数据库表结构

### 1.1 能源类型配置表 (coal_energy_type) - 单表
```sql
DROP TABLE IF EXISTS `coal_energy_type`;
CREATE TABLE `coal_energy_type` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '能源类型ID',
    `type_code` VARCHAR(20) NOT NULL COMMENT '能源类型编码',
    `type_name` VARCHAR(50) NOT NULL COMMENT '能源类型名称',
    `unit` VARCHAR(20) NOT NULL COMMENT '计量单位',
    `unit_price` DECIMAL(10,4) DEFAULT NULL COMMENT '单价(元/单位)',
    `is_real_time` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否实时采集：1是 0否',
    `data_source` VARCHAR(50) DEFAULT NULL COMMENT '数据来源：PLC/人工录入',
    `collection_interval` INT DEFAULT NULL COMMENT '采集间隔(分钟)',
    `warning_threshold` DECIMAL(10,2) DEFAULT NULL COMMENT '预警阈值',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_type_code` (`type_code`, `deleted`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '能源类型配置表';
```

### 1.2 能源消耗记录表 (coal_energy_consumption) - 单表
```sql
DROP TABLE IF EXISTS `coal_energy_consumption`;
CREATE TABLE `coal_energy_consumption` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消耗记录ID',
    `record_code` VARCHAR(50) NOT NULL COMMENT '记录编号',
    `energy_type_id` BIGINT NOT NULL COMMENT '能源类型ID',
    `consumption_date` DATE NOT NULL COMMENT '消耗日期',
    `shift_id` BIGINT DEFAULT NULL COMMENT '班次ID',
    `consumption_value` DECIMAL(12,4) NOT NULL COMMENT '消耗量',
    `unit_cost` DECIMAL(10,4) DEFAULT NULL COMMENT '单位成本(元/单位)',
    `total_cost` DECIMAL(12,2) DEFAULT NULL COMMENT '总成本(元)',
    `data_source` TINYINT NOT NULL DEFAULT 1 COMMENT '数据来源：1实时采集 2人工录入',
    `collection_time` DATETIME DEFAULT NULL COMMENT '采集时间',
    `recorder_id` BIGINT DEFAULT NULL COMMENT '记录人ID',
    `recorder_name` VARCHAR(50) DEFAULT NULL COMMENT '记录人姓名',
    `verification_status` TINYINT NOT NULL DEFAULT 1 COMMENT '验证状态：1待验证 2已验证 3异常',
    `verifier_id` BIGINT DEFAULT NULL COMMENT '验证人ID',
    `verification_time` DATETIME DEFAULT NULL COMMENT '验证时间',
    `verification_remark` VARCHAR(500) DEFAULT NULL COMMENT '验证备注',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_record_code` (`record_code`, `deleted`) USING BTREE,
    INDEX `idx_energy_type` (`energy_type_id`) USING BTREE,
    INDEX `idx_consumption_date` (`consumption_date`) USING BTREE,
    INDEX `idx_shift` (`shift_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '能源消耗记录表';
```

### 1.3 能源消耗统计表 (coal_energy_statistics) - 单表
```sql
DROP TABLE IF EXISTS `coal_energy_statistics`;
CREATE TABLE `coal_energy_statistics` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '统计ID',
    `statistics_date` DATE NOT NULL COMMENT '统计日期',
    `statistics_type` TINYINT NOT NULL COMMENT '统计类型：1日统计 2月统计 3年统计 4班统计',
    `energy_type_id` BIGINT NOT NULL COMMENT '能源类型ID',
    `total_consumption` DECIMAL(12,4) NOT NULL COMMENT '总消耗量',
    `total_cost` DECIMAL(12,2) NOT NULL COMMENT '总成本(元)',
    `average_consumption` DECIMAL(10,4) DEFAULT NULL COMMENT '平均消耗量',
    `peak_consumption` DECIMAL(10,4) DEFAULT NULL COMMENT '峰值消耗量',
    `valley_consumption` DECIMAL(10,4) DEFAULT NULL COMMENT '谷值消耗量',
    `consumption_efficiency` DECIMAL(5,2) DEFAULT NULL COMMENT '消耗效率(%)',
    `cost_per_unit` DECIMAL(10,4) DEFAULT NULL COMMENT '单位成本(元/单位)',
    `comparison_with_plan` DECIMAL(5,2) DEFAULT NULL COMMENT '与计划对比(%)',
    `comparison_with_last_period` DECIMAL(5,2) DEFAULT NULL COMMENT '与上期对比(%)',
    `statistics_status` TINYINT NOT NULL DEFAULT 1 COMMENT '统计状态：1正常 2异常 3需关注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_statistics` (`statistics_date`, `statistics_type`, `energy_type_id`, `deleted`) USING BTREE,
    INDEX `idx_energy_type` (`energy_type_id`) USING BTREE,
    INDEX `idx_statistics_date` (`statistics_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '能源消耗统计表';
```

### 1.4 能源预警记录表 (coal_energy_alert) - 单表
```sql
DROP TABLE IF EXISTS `coal_energy_alert`;
CREATE TABLE `coal_energy_alert` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '预警ID',
    `alert_code` VARCHAR(50) NOT NULL COMMENT '预警编号',
    `energy_type_id` BIGINT NOT NULL COMMENT '能源类型ID',
    `alert_type` TINYINT NOT NULL COMMENT '预警类型：1消耗异常 2成本超支 3效率低下 4设备故障',
    `alert_level` TINYINT NOT NULL DEFAULT 1 COMMENT '预警级别：1低 2中 3高',
    `alert_title` VARCHAR(100) NOT NULL COMMENT '预警标题',
    `alert_content` TEXT NOT NULL COMMENT '预警内容',
    `current_value` DECIMAL(12,4) DEFAULT NULL COMMENT '当前值',
    `threshold_value` DECIMAL(12,4) DEFAULT NULL COMMENT '阈值',
    `deviation_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '偏差率(%)',
    `alert_time` DATETIME NOT NULL COMMENT '预警时间',
    `alert_status` TINYINT NOT NULL DEFAULT 1 COMMENT '预警状态：1待处理 2处理中 3已处理 4已忽略',
    `handler_id` BIGINT DEFAULT NULL COMMENT '处理人ID',
    `handler_name` VARCHAR(50) DEFAULT NULL COMMENT '处理人姓名',
    `handle_time` DATETIME DEFAULT NULL COMMENT '处理时间',
    `handle_result` TEXT DEFAULT NULL COMMENT '处理结果',
    `handle_remark` VARCHAR(500) DEFAULT NULL COMMENT '处理备注',
    `creator` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_alert_code` (`alert_code`, `deleted`) USING BTREE,
    INDEX `idx_energy_type` (`energy_type_id`) USING BTREE,
    INDEX `idx_alert_type` (`alert_type`) USING BTREE,
    INDEX `idx_alert_status` (`alert_status`) USING BTREE,
    INDEX `idx_alert_time` (`alert_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '能源预警记录表';
```

## 2. 字典配置

### 2.1 能源类型字典
```sql
-- 能源类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('能源类型', 'coal_energy_type', 0, '能源类型', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '电力', '1', 'coal_energy_type', 0, '1', NOW(), '1', NOW()),
(2, '水资源', '2', 'coal_energy_type', 0, '1', NOW(), '1', NOW()),
(3, '药剂', '3', 'coal_energy_type', 0, '1', NOW(), '1', NOW()),
(4, '介质', '4', 'coal_energy_type', 0, '1', NOW(), '1', NOW()),
(5, '燃油', '5', 'coal_energy_type', 0, '1', NOW(), '1', NOW());
```

### 2.2 能源数据来源字典
```sql
-- 能源数据来源字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('能源数据来源', 'coal_energy_data_source', 0, '能源数据来源', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '实时采集', '1', 'coal_energy_data_source', 0, '1', NOW(), '1', NOW()),
(2, '人工录入', '2', 'coal_energy_data_source', 0, '1', NOW(), '1', NOW());
```

### 2.3 能源验证状态字典
```sql
-- 能源验证状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('能源验证状态', 'coal_energy_verification_status', 0, '能源验证状态', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '待验证', '1', 'coal_energy_verification_status', 0, '1', NOW(), '1', NOW()),
(2, '已验证', '2', 'coal_energy_verification_status', 0, '1', NOW(), '1', NOW()),
(3, '异常', '3', 'coal_energy_verification_status', 0, '1', NOW(), '1', NOW());
```

### 2.4 能源统计类型字典
```sql
-- 能源统计类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('能源统计类型', 'coal_energy_statistics_type', 0, '能源统计类型', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '日统计', '1', 'coal_energy_statistics_type', 0, '1', NOW(), '1', NOW()),
(2, '月统计', '2', 'coal_energy_statistics_type', 0, '1', NOW(), '1', NOW()),
(3, '年统计', '3', 'coal_energy_statistics_type', 0, '1', NOW(), '1', NOW()),
(4, '班统计', '4', 'coal_energy_statistics_type', 0, '1', NOW(), '1', NOW());
```

### 2.5 能源统计状态字典
```sql
-- 能源统计状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('能源统计状态', 'coal_energy_statistics_status', 0, '能源统计状态', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '正常', '1', 'coal_energy_statistics_status', 0, '1', NOW(), '1', NOW()),
(2, '异常', '2', 'coal_energy_statistics_status', 0, '1', NOW(), '1', NOW()),
(3, '需关注', '3', 'coal_energy_statistics_status', 0, '1', NOW(), '1', NOW());
```

### 2.6 能源预警类型字典
```sql
-- 能源预警类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('能源预警类型', 'coal_energy_alert_type', 0, '能源预警类型', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '消耗异常', '1', 'coal_energy_alert_type', 0, '1', NOW(), '1', NOW()),
(2, '成本超支', '2', 'coal_energy_alert_type', 0, '1', NOW(), '1', NOW()),
(3, '效率低下', '3', 'coal_energy_alert_type', 0, '1', NOW(), '1', NOW()),
(4, '设备故障', '4', 'coal_energy_alert_type', 0, '1', NOW(), '1', NOW());
```

### 2.7 能源预警级别字典
```sql
-- 能源预警级别字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('能源预警级别', 'coal_energy_alert_level', 0, '能源预警级别', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '低', '1', 'coal_energy_alert_level', 0, '1', NOW(), '1', NOW()),
(2, '中', '2', 'coal_energy_alert_level', 0, '1', NOW(), '1', NOW()),
(3, '高', '3', 'coal_energy_alert_level', 0, '1', NOW(), '1', NOW());
```

### 2.8 能源预警状态字典
```sql
-- 能源预警状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('能源预警状态', 'coal_energy_alert_status', 0, '能源预警状态', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '待处理', '1', 'coal_energy_alert_status', 0, '1', NOW(), '1', NOW()),
(2, '处理中', '2', 'coal_energy_alert_status', 0, '1', NOW(), '1', NOW()),
(3, '已处理', '3', 'coal_energy_alert_status', 0, '1', NOW(), '1', NOW()),
(4, '已忽略', '4', 'coal_energy_alert_status', 0, '1', NOW(), '1', NOW());
```

## 3. 测试数据

### 3.1 能源类型测试数据
```sql
INSERT INTO `coal_energy_type` (`type_code`, `type_name`, `unit`, `unit_price`, `is_real_time`, `data_source`, `collection_interval`, `warning_threshold`, `sort`, `status`, `remark`) VALUES
('ELECTRICITY', '电力', 'kWh', 0.65, 1, 'PLC', 15, 1000.00, 1, 1, '电力消耗实时监控'),
('WATER', '水资源', 'm³', 3.20, 1, 'PLC', 30, 50.00, 2, 1, '水资源消耗监控'),
('CHEMICAL', '药剂', 'kg', 15.00, 0, '人工录入', NULL, 100.00, 3, 1, '药剂消耗人工记录'),
('MEDIUM', '介质', 't', 200.00, 0, '人工录入', NULL, 10.00, 4, 1, '介质消耗人工记录'),
('FUEL', '燃油', 'L', 6.50, 0, '人工录入', NULL, 500.00, 5, 1, '燃油消耗人工记录');
```

### 3.2 能源消耗记录测试数据
```sql
INSERT INTO `coal_energy_consumption` (`record_code`, `energy_type_id`, `consumption_date`, `shift_id`, `consumption_value`, `unit_cost`, `total_cost`, `data_source`, `collection_time`, `recorder_id`, `recorder_name`, `verification_status`, `remark`) VALUES
('EC20250101001', 1, '2025-01-01', 1, 1250.50, 0.65, 812.83, 1, '2025-01-01 08:00:00', NULL, NULL, 2, '早班电力消耗'),
('EC20250101002', 2, '2025-01-01', 1, 45.20, 3.20, 144.64, 1, '2025-01-01 08:00:00', NULL, NULL, 2, '早班水资源消耗'),
('EC20250101003', 3, '2025-01-01', 1, 85.50, 15.00, 1282.50, 2, '2025-01-01 08:30:00', 1, '张三', 1, '早班药剂消耗'),
('EC20250101004', 4, '2025-01-01', 1, 8.50, 200.00, 1700.00, 2, '2025-01-01 08:30:00', 1, '张三', 1, '早班介质消耗'),
('EC20250101005', 5, '2025-01-01', 1, 120.00, 6.50, 780.00, 2, '2025-01-01 08:30:00', 1, '张三', 1, '早班燃油消耗');
```

## 4. 代码生成器配置说明

### 4.1 表结构类型
- **coal_energy_type** - 单表结构
- **coal_energy_consumption** - 单表结构  
- **coal_energy_statistics** - 单表结构
- **coal_energy_alert** - 单表结构

### 4.2 生成顺序建议
1. **coal_energy_type** - 能源类型配置（基础数据）
2. **coal_energy_consumption** - 能源消耗记录（核心业务）
3. **coal_energy_statistics** - 能源统计分析（统计功能）
4. **coal_energy_alert** - 能源预警管理（预警功能）

### 4.3 注意事项
- 所有表都是单表结构，使用芋道代码生成器的"单表"模板
- 字典前缀统一使用"coal_energy_"开头
- 确保所有字典配置完整，避免前端显示问题
- 测试数据可以根据实际需要调整
