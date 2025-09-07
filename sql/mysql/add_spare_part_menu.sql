-- 添加备件管理菜单到ERP产品信息下
-- 首先查找ERP产品管理的菜单ID
SELECT id, name, path FROM system_menu WHERE name LIKE '%产品%' AND deleted = 0;

-- 添加备件管理菜单
INSERT INTO `system_menu` (
    `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, 
    `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, 
    `deleted`
) VALUES (
    '备件管理', 'erp:product:spare-part', 2, 5, 
    (SELECT id FROM system_menu WHERE name = '产品管理' AND deleted = 0 LIMIT 1),
    'spare-part', 'ep:box', 'coal/sparepart/index', 'CoalSparePart',
    0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'
);

-- 添加备件管理相关权限
INSERT INTO `system_menu` (
    `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, 
    `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, 
    `deleted`
) VALUES 
('备件查询', 'erp:product:spare-part:query', 3, 1, 
 (SELECT id FROM system_menu WHERE name = '备件管理' AND deleted = 0 LIMIT 1),
 '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('备件新增', 'erp:product:spare-part:create', 3, 2, 
 (SELECT id FROM system_menu WHERE name = '备件管理' AND deleted = 0 LIMIT 1),
 '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('备件修改', 'erp:product:spare-part:update', 3, 3, 
 (SELECT id FROM system_menu WHERE name = '备件管理' AND deleted = 0 LIMIT 1),
 '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('备件删除', 'erp:product:spare-part:delete', 3, 4, 
 (SELECT id FROM system_menu WHERE name = '备件管理' AND deleted = 0 LIMIT 1),
 '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('库存管理', 'erp:product:spare-part:stock', 3, 5, 
 (SELECT id FROM system_menu WHERE name = '备件管理' AND deleted = 0 LIMIT 1),
 '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
('预警管理', 'erp:product:spare-part:alert', 3, 6, 
 (SELECT id FROM system_menu WHERE name = '备件管理' AND deleted = 0 LIMIT 1),
 '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 验证菜单添加结果
SELECT 
    m1.id, m1.name, m1.path, m1.component,
    m2.name as parent_name
FROM system_menu m1
LEFT JOIN system_menu m2 ON m1.parent_id = m2.id
WHERE m1.name LIKE '%备件%' AND m1.deleted = 0
ORDER BY m1.sort;
