-- 备件设备关联管理菜单SQL
-- 设备管理模块下添加备件设备关联管理菜单

-- 1. 主菜单：备件设备关联管理
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(5185, '备件设备关联管理', '', 2, 80, 5135, 'spare-part-equipment', 'ep:connection', 'coal/sparepartequipment/index', 'SparePartEquipment', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 2. 查询权限
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(5186, '备件设备关联查询', 'coal:spare-part-equipment:query', 3, 1, 5185, '', '', '', '', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 3. 创建权限
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(5187, '备件设备关联创建', 'coal:spare-part-equipment:create', 3, 2, 5185, '', '', '', '', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 4. 更新权限
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(5188, '备件设备关联更新', 'coal:spare-part-equipment:update', 3, 3, 5185, '', '', '', '', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 5. 删除权限
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(5189, '备件设备关联删除', 'coal:spare-part-equipment:delete', 3, 4, 5185, '', '', '', '', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 6. 导出权限
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(5190, '备件设备关联导出', 'coal:spare-part-equipment:export', 3, 5, 5185, '', '', '', '', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');
