-- ========================================
-- 市场分析管理 - 新增表SQL语句
-- 创建日期: 2025-12-29
-- 说明: 基于coal_daily_report_attach表结构创建
-- ========================================

-- 1. 综合成本核算附件表
DROP TABLE IF EXISTS `coal_cost_accounting_attach`;
CREATE TABLE `coal_cost_accounting_attach` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `report_date` date NOT NULL COMMENT '报告日期',
  `report_file_url` varchar(512) NOT NULL COMMENT '报告文件URL（单文件）',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='市场分析-综合成本核算附件';

-- 2. 洗选分析报告附件表
DROP TABLE IF EXISTS `coal_washing_analysis_report_attach`;
CREATE TABLE `coal_washing_analysis_report_attach` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `report_date` date NOT NULL COMMENT '报告日期',
  `report_file_url` varchar(512) NOT NULL COMMENT '报告文件URL（单文件）',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='市场分析-洗选分析报告附件';

-- ========================================
-- 使用说明:
-- 1. 在数据库中执行以上SQL语句创建表
-- 2. 使用芋道框架的代码生成器生成基础代码
--    - 模块名: coal
--    - 业务名: costAccountingAttach (综合成本核算附件)
--    - 业务名: washingAnalysisReportAttach (洗选分析报告附件)
-- 3. 生成后参考 coal_daily_report_attach 模块进行调整
-- 4. 前端日期字段使用: type="date" value-format="YYYY-MM-DD"
-- ========================================
