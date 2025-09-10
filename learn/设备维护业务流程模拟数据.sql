-- 设备维护业务流程模拟数据
-- 执行前请备份数据库

-- 1. 插入报修单模拟数据
INSERT INTO `coal_repair_request` (
    `request_code`, `equipment_id`, `equipment_name`, `equipment_location`,
    `fault_type`, `fault_level`, `fault_description`, `fault_symptoms`,
    `fault_cause`, `impact_assessment`, `urgency_level`, `request_status`,
    `reporter_id`, `reporter_name`, `reporter_phone`, `report_time`,
    `fault_photos`, `fault_videos`, `fault_audio`, `assigned_person`,
    `assigned_team`, `assignment_time`, `expected_repair_time`,
    `actual_start_time`, `actual_end_time`, `repair_duration`,
    `repair_method`, `replaced_parts`, `repair_cost`, `repair_quality`,
    `test_result`, `prevention_measures`, `satisfaction_rating`,
    `feedback_notes`, `close_time`, `close_reason`, `remark`,
    `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`
) VALUES
-- 待处理报修单
('RR202401001', 1, '破碎机PE-400×600', '选煤车间A区', 1, 2, '破碎机轴承异响', '设备运行时发出异常噪音，轴承温度升高', '轴承磨损严重，需要更换', '影响破碎效率，可能导致设备停机', 2, 1, 100, '张三', '13800138001', '2024-01-15 08:30:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'system', '2024-01-15 08:30:00', 'system', '2024-01-15 08:30:00', 0x00, 1),

('RR202401002', 2, '振动筛2YK2460', '选煤车间B区', 2, 3, '筛网破损', '筛分效果下降，大颗粒物料混入细料', '筛网长期使用磨损', '影响产品质量，需要立即处理', 3, 1, 101, '李四', '13800138002', '2024-01-15 09:15:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'system', '2024-01-15 09:15:00', 'system', '2024-01-15 09:15:00', 0x00, 1),

('RR202401003', 3, '皮带输送机B800', '选煤车间C区', 1, 1, '皮带跑偏', '皮带运行时向一侧偏移', '托辊调整不当', '轻微影响输送效率', 1, 1, 102, '王五', '13800138003', '2024-01-15 10:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'system', '2024-01-15 10:00:00', 'system', '2024-01-15 10:00:00', 0x00, 1),

-- 已派单报修单
('RR202401004', 4, '磁选机CTB-1024', '选煤车间D区', 3, 2, '磁选效果下降', '磁性物料回收率降低', '磁系老化，磁场强度不足', '影响精煤回收率', 2, 2, 103, '赵六', '13800138004', '2024-01-14 14:20:00', NULL, NULL, NULL, '维修班组长', '维修班组', '2024-01-14 15:00:00', '2024-01-15 09:00:00', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'system', '2024-01-14 14:20:00', 'system', '2024-01-14 15:00:00', 0x00, 1),

-- 已完成报修单
('RR202401005', 5, '离心脱水机LW-450', '选煤车间E区', 2, 1, '脱水效果不佳', '产品水分含量偏高', '筛篮磨损', '影响产品质量', 1, 4, 104, '孙七', '13800138005', '2024-01-13 16:30:00', NULL, NULL, NULL, '维修班组长', '维修班组', '2024-01-13 17:00:00', '2024-01-14 08:00:00', '2024-01-14 08:30:00', '2024-01-14 12:00:00', 210, '更换筛篮，调整转速', '筛篮1个', 1500.00, 4, '脱水效果恢复正常', '定期检查筛篮磨损情况', 5, '维修及时，效果良好', '2024-01-14 12:30:00', '故障已修复', 'system', '2024-01-13 16:30:00', 'system', '2024-01-14 12:30:00', 0x00, 1);

-- 2. 插入检修单模拟数据
INSERT INTO `coal_maintenance_order` (
    `order_code`, `plan_id`, `repair_request_id`, `equipment_id`, `equipment_name`,
    `maintenance_type`, `maintenance_level`, `order_status`, `priority_level`,
    `fault_description`, `maintenance_content`, `safety_measures`, `start_time`,
    `end_time`, `actual_duration`, `responsible_person`, `responsible_team`,
    `participants`, `work_environment`, `weather_condition`, `completion_rate`,
    `quality_rating`, `safety_rating`, `maintenance_result`, `problems_found`,
    `improvement_suggestions`, `next_maintenance_date`, `total_cost`,
    `labor_cost`, `material_cost`, `other_cost`, `remark`,
    `creator`, `create_time`, `updater`, `update_time`, `deleted`, `tenant_id`
) VALUES
-- 计划性检修单
('MO202401001', 1, NULL, 1, '破碎机PE-400×600', 2, 2, 2, 2, '定期检修', '检查轴承、更换润滑油、调整间隙', '断电挂牌、设置安全围栏', '2024-01-16 08:00:00', NULL, NULL, '维修班组长', '维修班组', '张三,李四', '车间内', '晴天', 0, NULL, NULL, NULL, NULL, NULL, '2024-04-16', NULL, NULL, NULL, NULL, '定期检修计划', 'system', '2024-01-15 10:00:00', 'system', '2024-01-15 10:00:00', 0x00, 1),

-- 故障性检修单（关联报修单）
('MO202401002', NULL, 1, 1, '破碎机PE-400×600', 3, 2, 2, 3, '破碎机轴承异响', '更换轴承、检查润滑系统', '断电挂牌、设置安全围栏', '2024-01-16 09:00:00', NULL, NULL, '维修班组长', '维修班组', '王五,赵六', '车间内', '晴天', 0, NULL, NULL, NULL, NULL, NULL, '2024-02-16', NULL, NULL, NULL, NULL, '故障检修', 'system', '2024-01-15 11:00:00', 'system', '2024-01-15 11:00:00', 0x00, 1),

('MO202401003', NULL, 2, 2, '振动筛2YK2460', 3, 1, 2, 3, '筛网破损', '更换筛网、调整筛分参数', '断电挂牌、设置安全围栏', '2024-01-16 10:00:00', NULL, NULL, '维修班组长', '维修班组', '孙七,周八', '车间内', '晴天', 0, NULL, NULL, NULL, NULL, NULL, '2024-02-16', NULL, NULL, NULL, NULL, '故障检修', 'system', '2024-01-15 12:00:00', 'system', '2024-01-15 12:00:00', 0x00, 1),

-- 已完成检修单
('MO202401004', NULL, 5, 5, '离心脱水机LW-450', 3, 1, 5, 2, '脱水效果不佳', '更换筛篮、调整转速', '断电挂牌、设置安全围栏', '2024-01-14 08:30:00', '2024-01-14 12:00:00', 210, '维修班组长', '维修班组', '张三,李四', '车间内', '晴天', 100, 4, 5, '脱水效果恢复正常', '筛篮磨损严重', '建议增加筛篮检查频次', '2024-02-14', 1500.00, 800.00, 600.00, 100.00, '检修完成', 'system', '2024-01-13 17:00:00', 'system', '2024-01-14 12:00:00', 0x00, 1);

-- 3. 插入备件使用记录模拟数据
INSERT INTO `coal_spare_part_usage_record` (
    `usage_code`, `spare_part_id`, `equipment_id`, `work_order_id`, `fault_id`,
    `usage_type`, `usage_reason`, `usage_quantity`, `unit_price`, `total_cost`,
    `usage_date`, `operator_id`, `operator_name`, `supervisor_id`, `supervisor_name`,
    `usage_status`, `return_quantity`, `return_reason`, `return_date`,
    `quality_check`, `quality_result`, `remark`, `creator`, `create_time`,
    `updater`, `update_time`, `deleted`, `tenant_id`
) VALUES
-- 关联检修单的备件使用记录
('UR202401001', 1, 1, 4, 5, 1, '更换磨损轴承', 2, 150.00, 300.00, '2024-01-14 09:00:00', 100, '张三', 1, '维修班组长', 2, 0, NULL, NULL, 1, '质量合格', '轴承更换完成', 'system', '2024-01-14 09:00:00', 'system', '2024-01-14 09:00:00', 0x00, 1),

('UR202401002', 2, 5, 4, 5, 1, '更换筛篮', 1, 600.00, 600.00, '2024-01-14 10:30:00', 101, '李四', 1, '维修班组长', 2, 0, NULL, NULL, 1, '质量合格', '筛篮更换完成', 'system', '2024-01-14 10:30:00', 'system', '2024-01-14 10:30:00', 0x00, 1),

-- 关联报修单的备件使用记录
('UR202401003', 3, 1, 2, 1, 1, '更换异响轴承', 2, 150.00, 300.00, '2024-01-16 09:30:00', 102, '王五', 1, '维修班组长', 1, 0, NULL, NULL, 0, NULL, '待使用', 'system', '2024-01-15 11:30:00', 'system', '2024-01-15 11:30:00', 0x00, 1),

('UR202401004', 4, 2, 3, 2, 1, '更换破损筛网', 1, 200.00, 200.00, '2024-01-16 10:30:00', 103, '赵六', 1, '维修班组长', 1, 0, NULL, NULL, 0, NULL, '待使用', 'system', '2024-01-15 12:30:00', 'system', '2024-01-15 12:30:00', 0x00, 1),

-- 其他备件使用记录
('UR202401005', 5, 3, NULL, NULL, 2, '日常维护更换', 1, 50.00, 50.00, '2024-01-15 14:00:00', 104, '孙七', 1, '维修班组长', 2, 0, NULL, NULL, 1, '质量合格', '日常维护', 'system', '2024-01-15 14:00:00', 'system', '2024-01-15 14:00:00', 0x00, 1),

('UR202401006', 6, 4, NULL, NULL, 2, '预防性更换', 1, 80.00, 80.00, '2024-01-15 15:00:00', 105, '周八', 1, '维修班组长', 2, 0, NULL, NULL, 1, '质量合格', '预防性维护', 'system', '2024-01-15 15:00:00', 'system', '2024-01-15 15:00:00', 0x00, 1);

-- 4. 更新备件库存记录（模拟备件使用后的库存变化）
UPDATE `coal_spare_part_stock_record` SET 
    `current_stock` = `current_stock` - 2,
    `update_time` = NOW()
WHERE `spare_part_id` = 1 AND `tenant_id` = 1;

UPDATE `coal_spare_part_stock_record` SET 
    `current_stock` = `current_stock` - 1,
    `update_time` = NOW()
WHERE `spare_part_id` = 2 AND `tenant_id` = 1;

UPDATE `coal_spare_part_stock_record` SET 
    `current_stock` = `current_stock` - 1,
    `update_time` = NOW()
WHERE `spare_part_id` = 5 AND `tenant_id` = 1;

UPDATE `coal_spare_part_stock_record` SET 
    `current_stock` = `current_stock` - 1,
    `update_time` = NOW()
WHERE `spare_part_id` = 6 AND `tenant_id` = 1;

-- 5. 插入备件出入库记录（模拟备件使用）
INSERT INTO `coal_spare_part_inout_record` (
    `record_code`, `spare_part_id`, `record_type`, `quantity`, `unit_price`,
    `total_amount`, `supplier_id`, `supplier_name`, `operator_id`, `operator_name`,
    `operation_date`, `purpose`, `remark`, `creator`, `create_time`,
    `updater`, `update_time`, `deleted`, `tenant_id`
) VALUES
-- 出库记录（备件使用）
('IR202401001', 1, 2, 2, 150.00, 300.00, NULL, NULL, 100, '张三', '2024-01-14 09:00:00', '检修使用', '破碎机轴承更换', 'system', '2024-01-14 09:00:00', 'system', '2024-01-14 09:00:00', 0x00, 1),

('IR202401002', 2, 2, 1, 600.00, 600.00, NULL, NULL, 101, '李四', '2024-01-14 10:30:00', '检修使用', '离心脱水机筛篮更换', 'system', '2024-01-14 10:30:00', 'system', '2024-01-14 10:30:00', 0x00, 1),

('IR202401003', 5, 2, 1, 50.00, 50.00, NULL, NULL, 104, '孙七', '2024-01-15 14:00:00', '日常维护', '皮带输送机维护', 'system', '2024-01-15 14:00:00', 'system', '2024-01-15 14:00:00', 0x00, 1),

('IR202401004', 6, 2, 1, 80.00, 80.00, NULL, NULL, 105, '周八', '2024-01-15 15:00:00', '预防性维护', '磁选机预防性更换', 'system', '2024-01-15 15:00:00', 'system', '2024-01-15 15:00:00', 0x00, 1);

-- 验证数据插入结果
SELECT '报修单数据' as table_name, COUNT(*) as count FROM coal_repair_request WHERE tenant_id = 1
UNION ALL
SELECT '检修单数据' as table_name, COUNT(*) as count FROM coal_maintenance_order WHERE tenant_id = 1
UNION ALL
SELECT '备件使用记录' as table_name, COUNT(*) as count FROM coal_spare_part_usage_record WHERE tenant_id = 1
UNION ALL
SELECT '备件出入库记录' as table_name, COUNT(*) as count FROM coal_spare_part_inout_record WHERE tenant_id = 1;
