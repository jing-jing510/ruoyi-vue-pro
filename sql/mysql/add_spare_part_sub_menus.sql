-- 添加备件设备关联和备件预警记录子菜单

-- 查找备件管理主菜单ID
SET @spare_part_menu_id = (SELECT id FROM system_menu WHERE name = '备件管理' AND deleted = 0 LIMIT 1);

-- 添加备件设备关联菜单
INSERT INTO system_menu (
    name, permission, type, sort, parent_id, path, icon, component, component_name,
    status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted
) VALUES (
    '设备关联管理', 'coal:spare-part-equipment:query', 2, 10, @spare_part_menu_id, 
    'equipment-relation', 'ep:connection', 'coal/sparepart/equipmentRelation/index', 'CoalSparePartEquipment',
    0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'
);

-- 获取设备关联管理菜单ID
SET @equipment_relation_menu_id = LAST_INSERT_ID();

-- 添加设备关联管理权限
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, component, status, visible, creator, create_time, updater, update_time, deleted) VALUES
('设备关联查询', 'coal:spare-part-equipment:query', 3, 1, @equipment_relation_menu_id, '', '', 0, b'0', '1', NOW(), '1', NOW(), b'0'),
('设备关联创建', 'coal:spare-part-equipment:create', 3, 2, @equipment_relation_menu_id, '', '', 0, b'0', '1', NOW(), '1', NOW(), b'0'),
('设备关联更新', 'coal:spare-part-equipment:update', 3, 3, @equipment_relation_menu_id, '', '', 0, b'0', '1', NOW(), '1', NOW(), b'0'),
('设备关联删除', 'coal:spare-part-equipment:delete', 3, 4, @equipment_relation_menu_id, '', '', 0, b'0', '1', NOW(), '1', NOW(), b'0'),
('设备关联导出', 'coal:spare-part-equipment:export', 3, 5, @equipment_relation_menu_id, '', '', 0, b'0', '1', NOW(), '1', NOW(), b'0');

-- 添加备件预警记录菜单
INSERT INTO system_menu (
    name, permission, type, sort, parent_id, path, icon, component, component_name,
    status, visible, keep_alive, always_show, creator, create_time, updater, update_time, deleted
) VALUES (
    '预警记录管理', 'coal:spare-part-alert:query', 2, 20, @spare_part_menu_id, 
    'alert-record', 'ep:warning', 'coal/sparepart/alertRecord/index', 'CoalSparePartAlert',
    0, b'1', b'1', b'1', '1', NOW(), '1', NOW(), b'0'
);

-- 获取预警记录管理菜单ID
SET @alert_record_menu_id = LAST_INSERT_ID();

-- 添加预警记录管理权限
INSERT INTO system_menu (name, permission, type, sort, parent_id, path, component, status, visible, creator, create_time, updater, update_time, deleted) VALUES
('预警记录查询', 'coal:spare-part-alert:query', 3, 1, @alert_record_menu_id, '', '', 0, b'0', '1', NOW(), '1', NOW(), b'0'),
('预警记录创建', 'coal:spare-part-alert:create', 3, 2, @alert_record_menu_id, '', '', 0, b'0', '1', NOW(), '1', NOW(), b'0'),
('预警记录处理', 'coal:spare-part-alert:handle', 3, 3, @alert_record_menu_id, '', '', 0, b'0', '1', NOW(), '1', NOW(), b'0'),
('预警记录删除', 'coal:spare-part-alert:delete', 3, 4, @alert_record_menu_id, '', '', 0, b'0', '1', NOW(), '1', NOW(), b'0'),
('预警记录导出', 'coal:spare-part-alert:export', 3, 5, @alert_record_menu_id, '', '', 0, b'0', '1', NOW(), '1', NOW(), b'0');

SELECT '备件设备关联和预警记录子菜单添加完成！' as message;
