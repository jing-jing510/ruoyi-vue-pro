-- 为备件预警记录添加发送权限
-- 需要先找到备件预警记录的主菜单ID，然后添加发送权限

-- 添加发送权限（假设备件预警记录的主菜单ID是某个值，需要根据实际情况调整）
INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(5191, '备件预警记录发送', 'coal:spare-part-alert:send', 3, 6, (SELECT id FROM system_menu WHERE permission = 'coal:spare-part-alert:query' LIMIT 1), '', '', '', '', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 如果上面的查询方式不工作，可以手动指定parent_id
-- 请根据实际的备件预警记录菜单ID来调整下面的值
-- INSERT INTO `system_menu` (`id`, `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
-- (5191, '备件预警记录发送', 'coal:spare-part-alert:send', 3, 6, 备件预警记录主菜单ID, '', '', '', '', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');
