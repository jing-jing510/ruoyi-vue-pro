-- 设备维护业务流程模拟数据（简化版）
-- 执行前请备份数据库

-- 1. 插入报修单模拟数据
INSERT INTO `coal_repair_request` (
    `request_code`, `equipment_id`, `equipment_name`, `equipment_location`,
    `fault_type`, `fault_level`, `fault_description`, `fault_symptoms`,
    `fault_cause`, `impact_assessment`, `urgency_level`, `request_status`,
    `reporter_id`, `reporter_name`, `reporter_phone`, `report_time`,
    `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`
) VALUES
-- 待处理报修单
('RR202401001', 1, '破碎机PE-400×600', '选煤车间A区', 1, 2, '破碎机轴承异响', '设备运行时发出异常噪音，轴承温度升高', '轴承磨损严重，需要更换', '影响破碎效率，可能导致设备停机', 2, 1, 100, '张三', '13800138001', '2024-01-15 08:30:00', 'system', '2024-01-15 08:30:00', 'system', '2024-01-15 08:30:00', 0x00, 1),

('RR202401002', 2, '振动筛2YK2460', '选煤车间B区', 2, 3, '筛网破损', '筛分效果下降，大颗粒物料混入细料', '筛网长期使用磨损', '影响产品质量，需要立即处理', 3, 1, 101, '李四', '13800138002', '2024-01-15 09:15:00', 'system', '2024-01-15 09:15:00', 'system', '2024-01-15 09:15:00', 0x00, 1),

('RR202401003', 3, '皮带输送机B800', '选煤车间C区', 1, 1, '皮带跑偏', '皮带运行时向一侧偏移', '托辊调整不当', '轻微影响输送效率', 1, 1, 102, '王五', '13800138003', '2024-01-15 10:00:00', 'system', '2024-01-15 10:00:00', 'system', '2024-01-15 10:00:00', 0x00, 1),

-- 已派单报修单
('RR202401004', 4, '磁选机CTB-1024', '选煤车间D区', 3, 2, '磁选效果下降', '磁性物料回收率降低', '磁系老化，磁场强度不足', '影响精煤回收率', 2, 2, 103, '赵六', '13800138004', '2024-01-14 14:20:00', 'system', '2024-01-14 14:20:00', 'system', '2024-01-14 15:00:00', 0x00, 1),

-- 已完成报修单
('RR202401005', 5, '离心脱水机LW-450', '选煤车间E区', 2, 1, '脱水效果不佳', '产品水分含量偏高', '筛篮磨损', '影响产品质量', 1, 4, 104, '孙七', '13800138005', '2024-01-13 16:30:00', 'system', '2024-01-13 16:30:00', 'system', '2024-01-14 12:30:00', 0x00, 1);

-- 2. 插入检修单模拟数据
INSERT INTO `coal_maintenance_order` (
    `order_code`, `plan_id`, `repair_request_id`, `equipment_id`, `equipment_name`,
    `maintenance_type`, `maintenance_level`, `order_status`, `priority_level`,
    `fault_description`, `maintenance_content`, `safety_measures`, `start_time`,
    `responsible_person`, `responsible_team`, `remark`,
    `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`
) VALUES
-- 计划性检修单
('MO202401001', 1, NULL, 1, '破碎机PE-400×600', 2, 2, 2, 2, '定期检修', '检查轴承、更换润滑油、调整间隙', '断电挂牌、设置安全围栏', '2024-01-16 08:00:00', '维修班组长', '维修班组', '定期检修计划', 'system', '2024-01-15 10:00:00', 'system', '2024-01-15 10:00:00', 0x00, 1),

-- 故障性检修单（关联报修单）
('MO202401002', NULL, 1, 1, '破碎机PE-400×600', 3, 2, 2, 3, '破碎机轴承异响', '更换轴承、检查润滑系统', '断电挂牌、设置安全围栏', '2024-01-16 09:00:00', '维修班组长', '维修班组', '故障检修', 'system', '2024-01-15 11:00:00', 'system', '2024-01-15 11:00:00', 0x00, 1),

('MO202401003', NULL, 2, 2, '振动筛2YK2460', 3, 1, 2, 3, '筛网破损', '更换筛网、调整筛分参数', '断电挂牌、设置安全围栏', '2024-01-16 10:00:00', '维修班组长', '维修班组', '故障检修', 'system', '2024-01-15 12:00:00', 'system', '2024-01-15 12:00:00', 0x00, 1),

-- 已完成检修单
('MO202401004', NULL, 5, 5, '离心脱水机LW-450', 3, 1, 5, 2, '脱水效果不佳', '更换筛篮、调整转速', '断电挂牌、设置安全围栏', '2024-01-14 08:30:00', '维修班组长', '维修班组', '检修完成', 'system', '2024-01-13 17:00:00', 'system', '2024-01-14 12:00:00', 0x00, 1);

-- 验证数据插入结果
SELECT '报修单数据' as table_name, COUNT(*) as count FROM coal_repair_request WHERE tenant_id = 1
UNION ALL
SELECT '检修单数据' as table_name, COUNT(*) as count FROM coal_maintenance_order WHERE tenant_id = 1;
