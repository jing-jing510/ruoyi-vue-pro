# 调度看板管理模块SQL语句

## 1. 建表语句

### 1.1 看板配置表 (coal_dashboard_config) - 单表
```sql
DROP TABLE IF EXISTS `coal_dashboard_config`;
CREATE TABLE `coal_dashboard_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '看板配置ID',
    `dashboard_name` VARCHAR(100) NOT NULL COMMENT '看板名称',
    `dashboard_type` TINYINT NOT NULL COMMENT '看板类型：1生产看板 2任务看板 3报警看板',
    `dashboard_code` VARCHAR(50) NOT NULL COMMENT '看板编码',
    `dashboard_title` VARCHAR(200) NOT NULL COMMENT '看板标题',
    `dashboard_description` TEXT COMMENT '看板描述',
    `layout_config` JSON COMMENT '布局配置（JSON格式）',
    `widget_config` JSON COMMENT '组件配置（JSON格式）',
    `refresh_interval` INT NOT NULL DEFAULT 30 COMMENT '刷新间隔（秒）',
    `auto_refresh` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否自动刷新：1是 0否',
    `is_public` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否公开：1是 0否',
    `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_dashboard_code` (`dashboard_code`, `deleted`) USING BTREE,
    INDEX `idx_dashboard_type` (`dashboard_type`) USING BTREE,
    INDEX `idx_status` (`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '看板配置表';
```

### 1.2 看板组件表 (coal_dashboard_widget) - 单表
```sql
DROP TABLE IF EXISTS `coal_dashboard_widget`;
CREATE TABLE `coal_dashboard_widget` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '组件ID',
    `widget_name` VARCHAR(100) NOT NULL COMMENT '组件名称',
    `widget_type` TINYINT NOT NULL COMMENT '组件类型：1图表 2表格 3指标 4文本 5图片',
    `widget_code` VARCHAR(50) NOT NULL COMMENT '组件编码',
    `widget_title` VARCHAR(200) NOT NULL COMMENT '组件标题',
    `widget_description` TEXT COMMENT '组件描述',
    `data_source_type` TINYINT NOT NULL COMMENT '数据源类型：1API接口 2SQL查询 3静态数据',
    `data_source_config` JSON COMMENT '数据源配置（JSON格式）',
    `display_config` JSON COMMENT '显示配置（JSON格式）',
    `chart_config` JSON COMMENT '图表配置（JSON格式）',
    `refresh_interval` INT NOT NULL DEFAULT 60 COMMENT '刷新间隔（秒）',
    `cache_duration` INT NOT NULL DEFAULT 300 COMMENT '缓存时长（秒）',
    `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_widget_code` (`widget_code`, `deleted`) USING BTREE,
    INDEX `idx_widget_type` (`widget_type`) USING BTREE,
    INDEX `idx_status` (`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '看板组件表';
```

### 1.3 看板布局表 (coal_dashboard_layout) - 单表
```sql
DROP TABLE IF EXISTS `coal_dashboard_layout`;
CREATE TABLE `coal_dashboard_layout` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '布局ID',
    `dashboard_id` BIGINT NOT NULL COMMENT '看板ID',
    `widget_id` BIGINT NOT NULL COMMENT '组件ID',
    `layout_position` VARCHAR(20) NOT NULL COMMENT '布局位置（如：1,1表示第1行第1列）',
    `layout_size` VARCHAR(20) NOT NULL COMMENT '布局大小（如：2x2表示2行2列）',
    `layout_config` JSON COMMENT '布局配置（JSON格式）',
    `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_dashboard` (`dashboard_id`) USING BTREE,
    INDEX `idx_widget` (`widget_id`) USING BTREE,
    INDEX `idx_status` (`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '看板布局表';
```

### 1.4 看板数据缓存表 (coal_dashboard_cache) - 单表
```sql
DROP TABLE IF EXISTS `coal_dashboard_cache`;
CREATE TABLE `coal_dashboard_cache` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '缓存ID',
    `cache_key` VARCHAR(200) NOT NULL COMMENT '缓存键',
    `cache_data` LONGTEXT COMMENT '缓存数据（JSON格式）',
    `cache_type` TINYINT NOT NULL COMMENT '缓存类型：1看板数据 2组件数据 3统计数据',
    `expire_time` DATETIME NOT NULL COMMENT '过期时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_cache_key` (`cache_key`) USING BTREE,
    INDEX `idx_expire_time` (`expire_time`) USING BTREE,
    INDEX `idx_cache_type` (`cache_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '看板数据缓存表';
```

### 1.5 看板访问记录表 (coal_dashboard_access_log) - 单表
```sql
DROP TABLE IF EXISTS `coal_dashboard_access_log`;
CREATE TABLE `coal_dashboard_access_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '访问记录ID',
    `dashboard_id` BIGINT NOT NULL COMMENT '看板ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `access_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
    `access_duration` INT DEFAULT NULL COMMENT '访问时长（秒）',
    `access_ip` VARCHAR(50) DEFAULT NULL COMMENT '访问IP',
    `user_agent` VARCHAR(500) DEFAULT NULL COMMENT '用户代理',
    `access_type` TINYINT NOT NULL DEFAULT 1 COMMENT '访问类型：1查看 2编辑 3导出',
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_dashboard` (`dashboard_id`) USING BTREE,
    INDEX `idx_user` (`user_id`) USING BTREE,
    INDEX `idx_access_time` (`access_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '看板访问记录表';
```

## 2. 字典数据

### 2.1 看板类型字典
```sql
-- 看板类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('看板类型', 'dashboard_type', 0, '看板类型', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '生产看板', '1', 'dashboard_type', 0, '1', NOW(), '1', NOW()),
(2, '任务看板', '2', 'dashboard_type', 0, '1', NOW(), '1', NOW()),
(3, '报警看板', '3', 'dashboard_type', 0, '1', NOW(), '1', NOW());
```

### 2.2 组件类型字典
```sql
-- 组件类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('组件类型', 'widget_type', 0, '组件类型', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '图表', '1', 'widget_type', 0, '1', NOW(), '1', NOW()),
(2, '表格', '2', 'widget_type', 0, '1', NOW(), '1', NOW()),
(3, '指标', '3', 'widget_type', 0, '1', NOW(), '1', NOW()),
(4, '文本', '4', 'widget_type', 0, '1', NOW(), '1', NOW()),
(5, '图片', '5', 'widget_type', 0, '1', NOW(), '1', NOW());
```

### 2.3 数据源类型字典
```sql
-- 数据源类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('数据源类型', 'data_source_type', 0, '数据源类型', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, 'API接口', '1', 'data_source_type', 0, '1', NOW(), '1', NOW()),
(2, 'SQL查询', '2', 'data_source_type', 0, '1', NOW(), '1', NOW()),
(3, '静态数据', '3', 'data_source_type', 0, '1', NOW(), '1', NOW());
```

### 2.4 访问类型字典
```sql
-- 访问类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('访问类型', 'access_type', 0, '访问类型', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '查看', '1', 'access_type', 0, '1', NOW(), '1', NOW()),
(2, '编辑', '2', 'access_type', 0, '1', NOW(), '1', NOW()),
(3, '导出', '3', 'access_type', 0, '1', NOW(), '1', NOW());
```

## 3. 测试数据

### 3.1 看板配置测试数据
```sql
-- 生产看板
INSERT INTO `coal_dashboard_config` (`dashboard_name`, `dashboard_type`, `dashboard_code`, `dashboard_title`, `dashboard_description`, `refresh_interval`, `auto_refresh`, `is_public`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
('生产看板', 1, 'PRODUCTION_DASHBOARD', '生产看板', '实时展示生产数据、产量完成情况、设备运行状态等', 30, 1, 1, 1, 1, '1', NOW(), '1', NOW(), 0, 0);

-- 任务看板
INSERT INTO `coal_dashboard_config` (`dashboard_name`, `dashboard_type`, `dashboard_code`, `dashboard_title`, `dashboard_description`, `refresh_interval`, `auto_refresh`, `is_public`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
('任务看板', 2, 'TASK_DASHBOARD', '任务看板', '展示生产任务分配、执行进度、完成情况等', 60, 1, 1, 2, 1, '1', NOW(), '1', NOW(), 0, 0);

-- 报警看板
INSERT INTO `coal_dashboard_config` (`dashboard_name`, `dashboard_type`, `dashboard_code`, `dashboard_title`, `dashboard_description`, `refresh_interval`, `auto_refresh`, `is_public`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
('报警看板', 3, 'ALERT_DASHBOARD', '报警看板', '实时展示报警信息、处理状态、统计分析等', 10, 1, 1, 3, 1, '1', NOW(), '1', NOW(), 0, 0);
```

### 3.2 组件配置测试数据
```sql
-- 生产指标组件
INSERT INTO `coal_dashboard_widget` (`widget_name`, `widget_type`, `widget_code`, `widget_title`, `widget_description`, `data_source_type`, `refresh_interval`, `cache_duration`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
('生产指标', 3, 'PRODUCTION_INDICATORS', '生产指标', '展示当日产量、完成率、设备运行率等关键指标', 1, 30, 300, 1, 1, '1', NOW(), '1', NOW(), 0, 0);

-- 产量趋势图
INSERT INTO `coal_dashboard_widget` (`widget_name`, `widget_type`, `widget_code`, `widget_title`, `widget_description`, `data_source_type`, `refresh_interval`, `cache_duration`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
('产量趋势图', 1, 'PRODUCTION_TREND_CHART', '产量趋势图', '展示近7天产量变化趋势', 1, 60, 600, 2, 1, '1', NOW(), '1', NOW(), 0, 0);

-- 设备状态表
INSERT INTO `coal_dashboard_widget` (`widget_name`, `widget_type`, `widget_code`, `widget_title`, `widget_description`, `data_source_type`, `refresh_interval`, `cache_duration`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
('设备状态表', 2, 'EQUIPMENT_STATUS_TABLE', '设备状态表', '展示主要设备运行状态', 1, 30, 300, 3, 1, '1', NOW(), '1', NOW(), 0, 0);

-- 任务进度表
INSERT INTO `coal_dashboard_widget` (`widget_name`, `widget_type`, `widget_code`, `widget_title`, `widget_description`, `data_source_type`, `refresh_interval`, `cache_duration`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
('任务进度表', 2, 'TASK_PROGRESS_TABLE', '任务进度表', '展示当前任务执行进度', 1, 60, 600, 4, 1, '1', NOW(), '1', NOW(), 0, 0);

-- 报警统计图
INSERT INTO `coal_dashboard_widget` (`widget_name`, `widget_type`, `widget_code`, `widget_title`, `widget_description`, `data_source_type`, `refresh_interval`, `cache_duration`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
('报警统计图', 1, 'ALERT_STATISTICS_CHART', '报警统计图', '展示报警类型分布和趋势', 1, 30, 300, 5, 1, '1', NOW(), '1', NOW(), 0, 0);

-- 实时报警列表
INSERT INTO `coal_dashboard_widget` (`widget_name`, `widget_type`, `widget_code`, `widget_title`, `widget_description`, `data_source_type`, `refresh_interval`, `cache_duration`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
('实时报警列表', 2, 'REALTIME_ALERT_LIST', '实时报警列表', '展示当前未处理的报警信息', 1, 10, 60, 6, 1, '1', NOW(), '1', NOW(), 0, 0);
```

### 3.3 看板布局测试数据
```sql
-- 生产看板布局
INSERT INTO `coal_dashboard_layout` (`dashboard_id`, `widget_id`, `layout_position`, `layout_size`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 1, '1,1', '2x1', 1, 1, '1', NOW(), '1', NOW(), 0, 0),
(1, 2, '1,2', '2x2', 2, 1, '1', NOW(), '1', NOW(), 0, 0),
(1, 3, '2,1', '2x2', 3, 1, '1', NOW(), '1', NOW(), 0, 0);

-- 任务看板布局
INSERT INTO `coal_dashboard_layout` (`dashboard_id`, `widget_id`, `layout_position`, `layout_size`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(2, 4, '1,1', '2x2', 1, 1, '1', NOW(), '1', NOW(), 0, 0);

-- 报警看板布局
INSERT INTO `coal_dashboard_layout` (`dashboard_id`, `widget_id`, `layout_position`, `layout_size`, `sort_order`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(3, 5, '1,1', '1x2', 1, 1, '1', NOW(), '1', NOW(), 0, 0),
(3, 6, '1,2', '1x2', 2, 1, '1', NOW(), '1', NOW(), 0, 0);
```

## 4. 使用说明

### 4.1 代码生成器配置
1. **看板配置表**：使用单表模板生成
2. **看板组件表**：使用单表模板生成
3. **看板布局表**：使用单表模板生成
4. **看板数据缓存表**：使用单表模板生成
5. **看板访问记录表**：使用单表模板生成

### 4.2 业务逻辑说明
1. **看板管理**：支持看板的创建、编辑、删除、发布等操作
2. **组件管理**：支持组件的配置、数据源设置、样式调整等
3. **布局管理**：支持拖拽式布局、响应式设计、权限控制
4. **数据缓存**：支持数据缓存、定时更新、实时推送
5. **访问控制**：支持用户权限、访问记录、性能监控

### 4.3 技术特点
1. **JSON字段**：使用MySQL 8.0+的JSON字段存储配置信息
2. **缓存机制**：支持多级缓存，提升数据访问性能
3. **实时更新**：支持WebSocket实时数据推送
4. **响应式设计**：支持多种屏幕尺寸的自适应布局
5. **权限控制**：支持细粒度的用户权限管理
