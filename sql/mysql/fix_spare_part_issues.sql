-- =============================================
-- 备件管理模块问题修复脚本
-- 修复备件类型字典和数据显示问题
-- =============================================

-- 1. 检查并添加备件类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
SELECT '备件类型', 'spare_part_type', 0, '备件类型分类', '1', NOW(), '1', NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_type` WHERE `type` = 'spare_part_type');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 1, '易损件', '1', 'spare_part_type', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_type' AND `value` = '1');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 2, '关键件', '2', 'spare_part_type', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_type' AND `value` = '2');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 3, '标准件', '3', 'spare_part_type', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_type' AND `value` = '3');

-- 2. 检查并添加预警相关字典类型
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
SELECT '备件预警类型', 'spare_part_alert_type', 0, '备件预警类型', '1', NOW(), '1', NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_type` WHERE `type` = 'spare_part_alert_type');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 1, '库存不足', '1', 'spare_part_alert_type', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_alert_type' AND `value` = '1');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 2, '库存过多', '2', 'spare_part_alert_type', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_alert_type' AND `value` = '2');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 3, '到期提醒', '3', 'spare_part_alert_type', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_alert_type' AND `value` = '3');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 4, '更换提醒', '4', 'spare_part_alert_type', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_alert_type' AND `value` = '4');

-- 3. 检查并添加预警级别字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
SELECT '备件预警级别', 'spare_part_alert_level', 0, '备件预警级别', '1', NOW(), '1', NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_type` WHERE `type` = 'spare_part_alert_level');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 1, '低', '1', 'spare_part_alert_level', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_alert_level' AND `value` = '1');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 2, '中', '2', 'spare_part_alert_level', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_alert_level' AND `value` = '2');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 3, '高', '3', 'spare_part_alert_level', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_alert_level' AND `value` = '3');

-- 4. 检查并添加预警状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) 
SELECT '备件预警状态', 'spare_part_alert_status', 0, '备件预警状态', '1', NOW(), '1', NOW(), 0
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_type` WHERE `type` = 'spare_part_alert_status');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 1, '待处理', '1', 'spare_part_alert_status', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_alert_status' AND `value` = '1');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 2, '已处理', '2', 'spare_part_alert_status', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_alert_status' AND `value` = '2');

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) 
SELECT 3, '已忽略', '3', 'spare_part_alert_status', 0, '1', NOW(), '1', NOW()
WHERE NOT EXISTS (SELECT 1 FROM `system_dict_data` WHERE `dict_type` = 'spare_part_alert_status' AND `value` = '3');

-- 5. 验证数据完整性
SELECT 
    'spare_part_type' as dict_type, 
    COUNT(*) as data_count 
FROM system_dict_data 
WHERE dict_type = 'spare_part_type'

UNION ALL

SELECT 
    'spare_part_alert_type' as dict_type, 
    COUNT(*) as data_count 
FROM system_dict_data 
WHERE dict_type = 'spare_part_alert_type'

UNION ALL

SELECT 
    'spare_part_alert_level' as dict_type, 
    COUNT(*) as data_count 
FROM system_dict_data 
WHERE dict_type = 'spare_part_alert_level'

UNION ALL

SELECT 
    'spare_part_alert_status' as dict_type, 
    COUNT(*) as data_count 
FROM system_dict_data 
WHERE dict_type = 'spare_part_alert_status';

-- 6. 检查ERP产品表的备件扩展字段是否存在
-- 注意：这些ALTER TABLE语句只有在字段不存在时才会执行
-- 如果字段已存在，这些语句会报错但不影响数据

-- 检查字段是否存在的方法
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_SCHEMA = DATABASE() 
    AND TABLE_NAME = 'erp_product' 
    AND COLUMN_NAME IN (
        'equipment_id', 'spare_part_type', 'min_stock', 'max_stock', 
        'safety_stock', 'supplier_id', 'supplier_name', 
        'replacement_cycle', 'last_replacement_date', 'next_replacement_date'
    )
ORDER BY COLUMN_NAME;
