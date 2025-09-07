-- 备件管理菜单添加脚本 (修正版)
-- 分步执行，避免子查询问题

-- 第一步：查找产品管理菜单ID
SELECT id, name, path FROM system_menu WHERE name = '产品管理' AND deleted = 0;

-- 第二步：手动添加备件管理主菜单 (请将下面的XXXX替换为第一步查询到的产品管理菜单ID)
-- INSERT INTO `system_menu` (
--     `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, 
--     `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, 
--     `deleted`
-- ) VALUES (
--     '备件管理', 'erp:product:spare-part', 2, 5, XXXX, 'spare-part', 'ep:box', 'coal/sparepart/index', 'CoalSparePart',
--     0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'
-- );

-- 第三步：查找备件管理菜单ID
-- SELECT id, name FROM system_menu WHERE name = '备件管理' AND deleted = 0;

-- 第四步：添加备件管理权限菜单 (请将下面的YYYY替换为第三步查询到的备件管理菜单ID)
-- INSERT INTO `system_menu` (
--     `name`, `permission`, `type`, `sort`, `parent_id`, `path`, `icon`, `component`, `component_name`, 
--     `status`, `visible`, `keep_alive`, `always_show`, `creator`, `create_time`, `updater`, `update_time`, 
--     `deleted`
-- ) VALUES 
-- ('备件查询', 'erp:product:spare-part:query', 3, 1, YYYY, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
-- ('备件新增', 'erp:product:spare-part:create', 3, 2, YYYY, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
-- ('备件修改', 'erp:product:spare-part:update', 3, 3, YYYY, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
-- ('备件删除', 'erp:product:spare-part:delete', 3, 4, YYYY, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
-- ('库存管理', 'erp:product:spare-part:stock', 3, 5, YYYY, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0'),
-- ('预警管理', 'erp:product:spare-part:alert', 3, 6, YYYY, '', '', '', '', 0, b'1', b'1', b'1', 'admin', NOW(), 'admin', NOW(), b'0');

-- 第五步：验证菜单添加结果
-- SELECT 
--     m1.id, m1.name, m1.path, m1.component,
--     m2.name as parent_name
-- FROM system_menu m1
-- LEFT JOIN system_menu m2 ON m1.parent_id = m2.id
-- WHERE m1.name LIKE '%备件%' AND m1.deleted = 0
-- ORDER BY m1.sort;
