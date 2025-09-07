-- =============================================
-- 备件管理模块测试数据脚本
-- =============================================

-- 1. 备件分类测试数据 (基于ERP产品分类表结构)
INSERT INTO `erp_product_category` (`id`, `parent_id`, `name`, `code`, `sort`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 0, '机械备件', 'MECHANICAL', 1, 0, 'admin', NOW(), 'admin', NOW(), 0, 1),
(2, 1, '轴承类', 'BEARING', 1, 0, 'admin', NOW(), 'admin', NOW(), 0, 1),
(3, 1, '密封件', 'SEAL', 2, 0, 'admin', NOW(), 'admin', NOW(), 0, 1),
(4, 0, '电气备件', 'ELECTRICAL', 2, 0, 'admin', NOW(), 'admin', NOW(), 0, 1),
(5, 4, '电机备件', 'MOTOR', 1, 0, 'admin', NOW(), 'admin', NOW(), 0, 1),
(6, 4, '控制备件', 'CONTROL', 2, 0, 'admin', NOW(), 'admin', NOW(), 0, 1);

-- 2. 产品单位测试数据 (基于ERP产品单位表结构)
INSERT INTO `erp_product_unit` (`id`, `name`, `status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, '个', 0, 'admin', NOW(), 'admin', NOW(), 0, 1),
(2, '套', 0, 'admin', NOW(), 'admin', NOW(), 0, 1),
(3, '台', 0, 'admin', NOW(), 'admin', NOW(), 0, 1),
(4, '米', 0, 'admin', NOW(), 'admin', NOW(), 0, 1),
(5, '公斤', 0, 'admin', NOW(), 'admin', NOW(), 0, 1);

-- 3. 仓库测试数据 (基于ERP仓库表结构)
INSERT INTO `erp_warehouse` (`id`, `name`, `address`, `sort`, `remark`, `principal`, `warehouse_price`, `truckage_price`, `status`, `default_status`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, '主仓库', '选煤厂主仓库', 1, '主要备件仓库', '张仓库', 0.00, 0.00, 0, 1, 'admin', NOW(), 'admin', NOW(), 0, 1),
(2, '车间仓库', '车间备件仓库', 2, '车间临时仓库', '李仓库', 0.00, 0.00, 0, 0, 'admin', NOW(), 'admin', NOW(), 0, 1);

-- 4. 备件信息测试数据 (基于ERP产品表结构，添加备件特有字段)
INSERT INTO `erp_product` (`id`, `name`, `bar_code`, `category_id`, `unit_id`, `status`, `standard`, `remark`, `expiry_day`, `weight`, `purchase_price`, `sale_price`, `min_price`, `equipment_id`, `spare_part_type`, `min_stock`, `max_stock`, `safety_stock`, `supplier_id`, `replacement_cycle`, `last_replacement_date`, `next_replacement_date`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, '深沟球轴承6205', 'SP001', 2, 1, 0, '6205-2RS', '深沟球轴承，用于皮带机', 0, 0.50, 120.00, 150.00, 100.00, 1, 1, 10.00, 100.00, 20.00, 1, 365, '2024-01-01', '2025-01-01', 'admin', NOW(), 'admin', NOW(), 0, 1),
(2, 'O型密封圈', 'SP002', 3, 1, 0, 'O-50x3', 'O型密封圈，用于液压系统', 0, 0.01, 5.00, 8.00, 3.00, 2, 1, 50.00, 500.00, 100.00, 1, 180, '2024-06-01', '2024-12-01', 'admin', NOW(), 'admin', NOW(), 0, 1),
(3, '电机碳刷', 'SP003', 5, 1, 0, '25x12x8', '电机碳刷，用于电机维护', 0, 0.02, 25.00, 35.00, 20.00, 3, 2, 20.00, 200.00, 50.00, 1, 90, '2024-03-01', '2024-06-01', 'admin', NOW(), 'admin', NOW(), 0, 1),
(4, '皮带输送机托辊', 'SP004', 2, 1, 0, 'φ108x380', '皮带输送机托辊', 0, 2.50, 45.00, 60.00, 35.00, 1, 1, 20.00, 200.00, 50.00, 1, 180, '2024-02-01', '2024-08-01', 'admin', NOW(), 'admin', NOW(), 0, 1),
(5, '液压油滤芯', 'SP005', 3, 1, 0, 'HF6619', '液压系统滤芯', 0, 0.30, 15.00, 25.00, 12.00, 2, 1, 30.00, 300.00, 60.00, 1, 90, '2024-04-01', '2024-07-01', 'admin', NOW(), 'admin', NOW(), 0, 1);

-- 5. 库存测试数据 (基于ERP库存表结构)
INSERT INTO `erp_stock` (`id`, `product_id`, `warehouse_id`, `count`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 1, 1, 15.00, 'admin', NOW(), 'admin', NOW(), 0, 1),
(2, 2, 1, 80.00, 'admin', NOW(), 'admin', NOW(), 0, 1),
(3, 3, 1, 25.00, 'admin', NOW(), 'admin', NOW(), 0, 1),
(4, 4, 1, 35.00, 'admin', NOW(), 'admin', NOW(), 0, 1),
(5, 5, 1, 45.00, 'admin', NOW(), 'admin', NOW(), 0, 1);

-- 6. 备件设备关联测试数据 (基于新增的备件设备关联表)
INSERT INTO `coal_spare_part_equipment` (`id`, `spare_part_id`, `equipment_id`, `usage_count`, `is_required`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 1, 1, 2, 1, '皮带机主轴承', 'admin', NOW(), 'admin', NOW(), 0, 1),
(2, 2, 2, 4, 1, '液压系统密封', 'admin', NOW(), 'admin', NOW(), 0, 1),
(3, 3, 3, 1, 1, '电机维护备件', 'admin', NOW(), 'admin', NOW(), 0, 1),
(4, 4, 1, 8, 1, '皮带机托辊', 'admin', NOW(), 'admin', NOW(), 0, 1),
(5, 5, 2, 2, 1, '液压系统滤芯', 'admin', NOW(), 'admin', NOW(), 0, 1);

-- 7. 备件预警测试数据 (基于新增的备件预警记录表)
INSERT INTO `coal_spare_part_alert` (`id`, `spare_part_id`, `warehouse_id`, `alert_type`, `current_stock`, `threshold_value`, `alert_level`, `alert_status`, `alert_message`, `handler_id`, `handle_time`, `handle_remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`) VALUES
(1, 1, 1, 1, 5.00, 10.00, 2, 1, '深沟球轴承6205库存不足，当前库存5个，低于最低库存10个', NULL, NULL, NULL, 'admin', NOW(), 'admin', NOW(), 0, 1),
(2, 2, 1, 2, 600.00, 500.00, 1, 1, 'O型密封圈库存过多，当前库存600个，超过最高库存500个', NULL, NULL, NULL, 'admin', NOW(), 'admin', NOW(), 0, 1),
(3, 3, 1, 4, 25.00, 20.00, 3, 1, '电机碳刷需要更换，距离上次更换已超过90天', NULL, NULL, NULL, 'admin', NOW(), 'admin', NOW(), 0, 1);

-- 8. 验证测试数据
-- 查看备件分类
SELECT * FROM erp_product_category WHERE name LIKE '%备件%';

-- 查看备件信息
SELECT p.id, p.name, p.bar_code, c.name as category_name, u.name as unit_name, 
       p.spare_part_type, p.min_stock, p.max_stock, p.safety_stock, p.equipment_id
FROM erp_product p 
LEFT JOIN erp_product_category c ON p.category_id = c.id 
LEFT JOIN erp_product_unit u ON p.unit_id = u.id 
WHERE p.spare_part_type IS NOT NULL;

-- 查看库存信息
SELECT s.id, p.name as product_name, w.name as warehouse_name, s.count
FROM erp_stock s
LEFT JOIN erp_product p ON s.product_id = p.id
LEFT JOIN erp_warehouse w ON s.warehouse_id = w.id
WHERE p.spare_part_type IS NOT NULL;

-- 查看备件设备关联
SELECT spe.id, p.name as spare_part_name, e.equipment_name, spe.usage_count, spe.is_required
FROM coal_spare_part_equipment spe
LEFT JOIN erp_product p ON spe.spare_part_id = p.id
LEFT JOIN coal_equipment_info e ON spe.equipment_id = e.id;

-- 查看预警信息
SELECT spa.id, p.name as spare_part_name, w.name as warehouse_name, 
       spa.alert_type, spa.current_stock, spa.threshold_value, spa.alert_level, spa.alert_status, spa.alert_message
FROM coal_spare_part_alert spa
LEFT JOIN erp_product p ON spa.spare_part_id = p.id
LEFT JOIN erp_warehouse w ON spa.warehouse_id = w.id;
