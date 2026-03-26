-- OPC UA 模块菜单权限更新脚本
-- 如果之前已经执行过 opcua_tables.sql，请先删除旧的菜单，再执行此脚本

-- 删除旧的 OPC UA 菜单（如果存在）
DELETE FROM `system_menu` WHERE `name` LIKE 'OPC UA%' OR `name` LIKE '%OPC UA%' OR `name` LIKE '点位配置%' OR `name` LIKE '报警事件%' OR `name` LIKE '报警统计%';

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
SELECT '报警事件创建', 'opcua:alarm:create', 3, 2, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '报警事件' AND `deleted` = b'0' LIMIT 1;

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '报警事件更新', 'opcua:alarm:update', 3, 3, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '报警事件' AND `deleted` = b'0' LIMIT 1;

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '报警事件删除', 'opcua:alarm:delete', 3, 4, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '报警事件' AND `deleted` = b'0' LIMIT 1;

INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '报警事件处理', 'opcua:alarm:handle', 3, 5, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '报警事件' AND `deleted` = b'0' LIMIT 1;

-- 子菜单：报警统计
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
SELECT '报警统计', '', 2, 4, id, 'statistics', 'ep:data-analysis', 'opcua/statistics/index', 'OpcuaStatistics', 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = 'OPC UA' AND `deleted` = b'0' LIMIT 1;

-- 报警统计的操作按钮
INSERT INTO `system_menu` (`name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
SELECT '报警统计查询', 'opcua:alarm:statistics', 3, 1, id, '', '', '', NULL, 0, b'1', b'1', '1', NOW(), '1', NOW(), b'0' FROM `system_menu` WHERE `name` = '报警统计' AND `deleted` = b'0' LIMIT 1;
