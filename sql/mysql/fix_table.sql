-- 修复 opcua_tag_config 表
-- 步骤1：删除旧表
DROP TABLE IF EXISTS `opcua_tag_config`;

-- 步骤2：重新创建表
CREATE TABLE `opcua_tag_config` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '点位ID',
  `config_id` bigint NOT NULL COMMENT 'OPC UA 配置ID',
  `device_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '设备名称（手动填写）',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '点位名称',
  `node_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'OPC UA NodeId',
  `data_type` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT 'Boolean' COMMENT '数据类型',
  `is_alarm` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为报警点位',
  `alarm_level` tinyint DEFAULT 2 COMMENT '报警级别：1-提示 2-警告 3-错误 4-严重',
  `alarm_content` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '报警内容',
  `last_value` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上次采集值（用于判断0→1）',
  `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (`id`),
  KEY `idx_config_id` (`config_id`),
  KEY `idx_is_alarm` (`is_alarm`,`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点位配置表';
