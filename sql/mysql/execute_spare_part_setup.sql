-- =============================================
-- 备件管理模块完整安装脚本
-- 按顺序执行以下脚本
-- =============================================

-- 1. 执行数据库扩展
SOURCE spare_part_db_extend.sql;

-- 2. 执行字典配置
SOURCE spare_part_dict_data.sql;

-- 3. 执行测试数据
SOURCE spare_part_test_data.sql;

-- 4. 验证安装结果
SELECT '备件管理模块安装完成！' as message;

-- 查看扩展后的erp_product表结构
DESCRIBE erp_product;

-- 查看新增表
SHOW TABLES LIKE 'coal_spare_part%';

-- 查看字典数据
SELECT dt.name, dt.type, COUNT(dd.id) as data_count 
FROM system_dict_type dt 
LEFT JOIN system_dict_data dd ON dt.type = dd.dict_type 
WHERE dt.type LIKE '%spare_part%' OR dt.type LIKE '%alert%'
GROUP BY dt.name, dt.type;

-- 查看测试数据统计
SELECT '备件分类' as type, COUNT(*) as count FROM erp_product_category WHERE name LIKE '%备件%'
UNION ALL
SELECT '产品单位' as type, COUNT(*) as count FROM erp_product_unit
UNION ALL
SELECT '仓库' as type, COUNT(*) as count FROM erp_warehouse
UNION ALL
SELECT '备件信息' as type, COUNT(*) as count FROM erp_product WHERE spare_part_type IS NOT NULL
UNION ALL
SELECT '库存记录' as type, COUNT(*) as count FROM erp_stock s JOIN erp_product p ON s.product_id = p.id WHERE p.spare_part_type IS NOT NULL
UNION ALL
SELECT '设备关联' as type, COUNT(*) as count FROM coal_spare_part_equipment
UNION ALL
SELECT '预警记录' as type, COUNT(*) as count FROM coal_spare_part_alert;
