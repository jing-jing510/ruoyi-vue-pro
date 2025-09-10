-- 创建备件库存统计分析菜单
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (2100, '库存统计分析', '', 1, 4, 2000, 'statistics', 'chart', 'coal/sparepartinfo/statistics', 'SparePartStatistics', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');

-- 设置菜单权限
INSERT INTO system_menu (id, name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted)
VALUES (2101, '库存统计查看', 'coal:spare-part-info:statistics', 3, 1, 2100, '', '', '', '', 0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0');
