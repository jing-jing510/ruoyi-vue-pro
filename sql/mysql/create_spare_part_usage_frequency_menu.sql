-- 备件使用频率分析菜单
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (2130, '使用频率分析', '', 2, 6, 2100, 'usage-frequency', 'ep:trend-charts', 'coal/sparepartinfo/usage-frequency', 'SparePartUsageFrequency', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 菜单权限
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (2131, '备件使用频率分析查询', 'coal:spare-part-info:usage-frequency:query', 3, 1, 2130, '', '', '', '', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');
