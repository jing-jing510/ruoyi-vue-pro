-- =============================================
-- 备件管理模块字典数据配置脚本
-- =============================================

-- 1. 备件类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('备件类型', 'spare_part_type', 0, '备件类型分类', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '易损件', '1', 'spare_part_type', 0, '1', NOW(), '1', NOW()),
(2, '关键件', '2', 'spare_part_type', 0, '1', NOW(), '1', NOW()),
(3, '标准件', '3', 'spare_part_type', 0, '1', NOW(), '1', NOW());

-- 2. 预警类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('预警类型', 'alert_type', 0, '备件预警类型', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '库存不足', '1', 'alert_type', 0, '1', NOW(), '1', NOW()),
(2, '库存过多', '2', 'alert_type', 0, '1', NOW(), '1', NOW()),
(3, '到期提醒', '3', 'alert_type', 0, '1', NOW(), '1', NOW()),
(4, '更换提醒', '4', 'alert_type', 0, '1', NOW(), '1', NOW());

-- 3. 预警级别字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('预警级别', 'alert_level', 0, '备件预警级别', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '低', '1', 'alert_level', 0, '1', NOW(), '1', NOW()),
(2, '中', '2', 'alert_level', 0, '1', NOW(), '1', NOW()),
(3, '高', '3', 'alert_level', 0, '1', NOW(), '1', NOW());

-- 4. 预警状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('预警状态', 'alert_status', 0, '备件预警状态', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '待处理', '1', 'alert_status', 0, '1', NOW(), '1', NOW()),
(2, '已处理', '2', 'alert_status', 0, '1', NOW(), '1', NOW()),
(3, '已忽略', '3', 'alert_status', 0, '1', NOW(), '1', NOW());

-- 5. 验证字典数据
SELECT * FROM system_dict_type WHERE type LIKE '%spare_part%' OR type LIKE '%alert%';
SELECT * FROM system_dict_data WHERE dict_type IN ('spare_part_type', 'alert_type', 'alert_level', 'alert_status');
