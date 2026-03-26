-- OPC UA 模块数据库表结构
-- 遵循芋道框架规范

-- ========== OPC UA 配置表 ==========
CREATE TABLE IF NOT EXISTS `opcua_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `name` varchar(100) NOT NULL COMMENT '配置名称',
    `server_url` varchar(255) NOT NULL COMMENT 'OPC UA 服务器地址',
    `username` varchar(100) DEFAULT NULL COMMENT '用户名',
    `password` varchar(255) DEFAULT NULL COMMENT '密码',
    `security_policy` varchar(50) DEFAULT 'None' COMMENT '安全策略',
    `security_mode` varchar(50) DEFAULT 'None' COMMENT '安全模式',
    `timeout` int DEFAULT 5000 COMMENT '连接超时时间（毫秒）',
    `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `creator` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OPC UA 配置表';

-- ========== 点位配置表（简化版，去掉设备表） ==========
CREATE TABLE IF NOT EXISTS `opcua_tag_config` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '点位ID',
    `config_id` bigint NOT NULL COMMENT 'OPC UA 配置ID',
    `device_name` varchar(100) DEFAULT NULL COMMENT '设备名称（手动填写）',
    `name` varchar(100) NOT NULL COMMENT '点位名称',
    `node_id` varchar(255) NOT NULL COMMENT 'OPC UA NodeId',
    `data_type` varchar(50) DEFAULT 'Boolean' COMMENT '数据类型',
    `is_alarm` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为报警点位',
    `alarm_level` tinyint DEFAULT 2 COMMENT '报警级别：1-提示 2-警告 3-错误 4-严重',
    `alarm_content` varchar(500) DEFAULT NULL COMMENT '报警内容',
    `last_value` varchar(100) DEFAULT NULL COMMENT '上次采集值（用于判断0→1）',
    `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `creator` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY `idx_config_id` (`config_id`),
    KEY `idx_is_alarm` (`is_alarm`, `enabled`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='点位配置表';

-- ========== 报警事件表 ==========
CREATE TABLE IF NOT EXISTS `opcua_alarm_event` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '报警ID',
    `config_id` bigint NOT NULL COMMENT 'OPC UA 配置ID',
    `device_name` varchar(100) DEFAULT NULL COMMENT '设备名称',
    `tag_id` bigint NOT NULL COMMENT '点位ID',
    `tag_name` varchar(100) NOT NULL COMMENT '点位名称',
    `node_id` varchar(255) NOT NULL COMMENT 'OPC UA NodeId',
    `alarm_level` tinyint NOT NULL COMMENT '报警级别：1-提示 2-警告 3-错误 4-严重',
    `alarm_content` varchar(500) NOT NULL COMMENT '报警内容',
    `alarm_time` datetime NOT NULL COMMENT '报警时间',
    `status` tinyint NOT NULL DEFAULT 0 COMMENT '报警状态：0-待处理 1-已处理',
    `handle_user_id` bigint DEFAULT NULL COMMENT '处理人ID',
    `handle_user_name` varchar(50) DEFAULT NULL COMMENT '处理人姓名',
    `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
    `handle_remark` varchar(500) DEFAULT NULL COMMENT '处理意见',
    `creator` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`),
    KEY `idx_config_id` (`config_id`),
    KEY `idx_tag_id` (`tag_id`),
    KEY `idx_status` (`status`),
    KEY `idx_alarm_time` (`alarm_time`),
    KEY `idx_alarm_level` (`alarm_level`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报警事件表';

-- ========== 菜单数据 ==========
-- 主菜单
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
('OPC UA', '', 1, 100, 0, '/opcua', 'ep:connection', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 子菜单：OPC UA 配置
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
SELECT 'OPC UA 配置', '', 2, 1, id, 'config', 'ep:setting', 'opcua/config/index', 'OpcuaConfig', 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = 'OPC UA' AND `deleted` = b'0' LIMIT 1;

-- OPC UA 配置的操作按钮
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT 'OPC UA 配置查询', 'opcua:config:query', 3, 1, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = 'OPC UA 配置' AND `deleted` = b'0' LIMIT 1;

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT 'OPC UA 配置创建', 'opcua:config:create', 3, 2, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = 'OPC UA 配置' AND `deleted` = b'0' LIMIT 1;

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT 'OPC UA 配置更新', 'opcua:config:update', 3, 3, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = 'OPC UA 配置' AND `deleted` = b'0' LIMIT 1;

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT 'OPC UA 配置删除', 'opcua:config:delete', 3, 4, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = 'OPC UA 配置' AND `deleted` = b'0' LIMIT 1;

-- 子菜单：点位配置
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
SELECT '点位配置', '', 2, 2, id, 'tag', 'ep:data-line', 'opcua/tag/index', 'OpcuaTag', 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = 'OPC UA' AND `deleted` = b'0' LIMIT 1;

-- 点位配置的操作按钮
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '点位配置查询', 'opcua:tag:query', 3, 1, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '点位配置' AND `deleted` = b'0' LIMIT 1;

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '点位配置创建', 'opcua:tag:create', 3, 2, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '点位配置' AND `deleted` = b'0' LIMIT 1;

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '点位配置更新', 'opcua:tag:update', 3, 3, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '点位配置' AND `deleted` = b'0' LIMIT 1;

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '点位配置删除', 'opcua:tag:delete', 3, 4, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '点位配置' AND `deleted` = b'0' LIMIT 1;

-- 子菜单：报警事件
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
SELECT '报警事件', '', 2, 3, id, 'alarm', 'ep:warning', 'opcua/alarm/index', 'OpcuaAlarm', 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = 'OPC UA' AND `deleted` = b'0' LIMIT 1;

-- 报警事件的操作按钮
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '报警事件查询', 'opcua:alarm:query', 3, 1, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '报警事件' AND `deleted` = b'0' LIMIT 1;

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '报警事件删除', 'opcua:alarm:delete', 3, 2, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '报警事件' AND `deleted` = b'0' LIMIT 1;

-- 子菜单：报警统计
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
SELECT '报警统计', '', 2, 4, id, 'statistics', 'ep:data-analysis', 'opcua/statistics/index', 'OpcuaStatistics', 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = 'OPC UA' AND `deleted` = b'0' LIMIT 1;

-- 报警统计的操作按钮
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '报警统计查询', 'opcua:alarm:statistics', 3, 1, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '报警统计' AND `deleted` = b'0' LIMIT 1;
