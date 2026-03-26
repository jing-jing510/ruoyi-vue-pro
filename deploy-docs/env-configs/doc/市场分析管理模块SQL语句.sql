-- ========================================
-- 市场分析管理模块 - SQL建表语句
-- 创建日期: 2025-12-26
-- 说明: 参考coal_daily_report_attach表结构创建
-- 包含三个子模块：市场分析、综合成本核算、洗选分析报告
-- ========================================

-- 1. 市场分析表
DROP TABLE IF EXISTS `coal_market_analysis`;
CREATE TABLE `coal_market_analysis` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `analysis_date` date NOT NULL COMMENT '分析日期',
  `report_file_url` varchar(512) NOT NULL COMMENT '市场分析文件URL（单文件）',
  `image_urls` varchar(2000) DEFAULT NULL COMMENT '图片URL，多个用英文逗号分隔',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_date` (`tenant_id`,`analysis_date`),
  KEY `idx_analysis_date` (`analysis_date`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='市场分析-市场分析';

-- 2. 综合成本核算表
DROP TABLE IF EXISTS `coal_market_cost_analysis`;
CREATE TABLE `coal_market_cost_analysis` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `analysis_date` date NOT NULL COMMENT '分析日期',
  `report_file_url` varchar(512) NOT NULL COMMENT '成本核算文件URL（单文件）',
  `image_urls` varchar(2000) DEFAULT NULL COMMENT '图片URL，多个用英文逗号分隔',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_date` (`tenant_id`,`analysis_date`),
  KEY `idx_analysis_date` (`analysis_date`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='市场分析-综合成本核算';

-- 3. 洗选分析报告表
DROP TABLE IF EXISTS `coal_market_washing_report`;
CREATE TABLE `coal_market_washing_report` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `report_date` date NOT NULL COMMENT '报告日期',
  `report_file_url` varchar(512) NOT NULL COMMENT '洗选报告文件URL（单文件）',
  `image_urls` varchar(2000) DEFAULT NULL COMMENT '图片URL，多个用英文逗号分隔',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_date` (`tenant_id`,`report_date`),
  KEY `idx_report_date` (`report_date`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='市场分析-洗选分析报告';

-- ========================================
-- 使用说明
-- ========================================
-- 1. 在数据库中执行以上SQL语句创建表
-- 2. 使用芋道框架的代码生成器生成基础代码：
--    - 表名: coal_market_analysis (市场分析)
--    - 表名: coal_market_cost_analysis (综合成本核算)
--    - 表名: coal_market_washing_report (洗选分析报告)
-- 3. 生成代码后，参考coal_daily_report_attach的前后端实现进行调整
-- 4. 前端日期格式使用: YYYY-MM-DD (对应LocalDate类型)
-- 5. 文件上传字段: report_file_url (单文件)
-- 6. 图片上传字段: image_urls (多图片，逗号分隔)
-- 7. 三个表结构完全一致，只是业务含义不同

-- 菜单 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status, component_name
)
VALUES (
    '市场分析管理', '', 2, 0, 5310,
    'market-analysis', '', 'coal/marketanalysis/index', 0, 'MarketAnalysis'
);

-- 按钮父菜单ID
-- 暂时只支持 MySQL。如果你是 Oracle、PostgreSQL、SQLServer 的话，需要手动修改 @parentId 的部分的代码
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '市场分析查询', 'coal:market-analysis:query', 3, 1, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '市场分析创建', 'coal:market-analysis:create', 3, 2, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '市场分析更新', 'coal:market-analysis:update', 3, 3, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '市场分析删除', 'coal:market-analysis:delete', 3, 4, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '市场分析导出', 'coal:market-analysis:export', 3, 5, @parentId,
    '', '', '', 0
);
-- 菜单 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status, component_name
)
VALUES (
    '洗选分析报告管理', '', 2, 0, 5310,
    'market-washing-report', '', 'coal/marketwashingreport/index', 0, 'MarketWashingReport'
);

-- 按钮父菜单ID
-- 暂时只支持 MySQL。如果你是 Oracle、PostgreSQL、SQLServer 的话，需要手动修改 @parentId 的部分的代码
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '洗选分析报告查询', 'coal:market-washing-report:query', 3, 1, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '洗选分析报告创建', 'coal:market-washing-report:create', 3, 2, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '洗选分析报告更新', 'coal:market-washing-report:update', 3, 3, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '洗选分析报告删除', 'coal:market-washing-report:delete', 3, 4, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '洗选分析报告导出', 'coal:market-washing-report:export', 3, 5, @parentId,
    '', '', '', 0
);
-- 菜单 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status, component_name
)
VALUES (
    '综合成本核算管理', '', 2, 0, 5310,
    'market-cost-analysis', '', 'coal/marketcostanalysis/index', 0, 'MarketCostAnalysis'
);

-- 按钮父菜单ID
-- 暂时只支持 MySQL。如果你是 Oracle、PostgreSQL、SQLServer 的话，需要手动修改 @parentId 的部分的代码
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '综合成本核算查询', 'coal:market-cost-analysis:query', 3, 1, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '综合成本核算创建', 'coal:market-cost-analysis:create', 3, 2, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '综合成本核算更新', 'coal:market-cost-analysis:update', 3, 3, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '综合成本核算删除', 'coal:market-cost-analysis:delete', 3, 4, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '综合成本核算导出', 'coal:market-cost-analysis:export', 3, 5, @parentId,
    '', '', '', 0
);

