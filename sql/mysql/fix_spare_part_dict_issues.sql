-- 修复备件管理模块字典配置问题
-- 执行时间：2024-01-XX

-- ========== 添加缺失的字典类型 ==========

-- 分类层级字典 (备件分类表需要)
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('备件分类层级', 'spare_part_category_level', 0, '备件分类的层级', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '大类', '1', 'spare_part_category_level', 0, '1', NOW(), '1', NOW()),
(2, '中类', '2', 'spare_part_category_level', 0, '1', NOW(), '1', NOW()),
(3, '小类', '3', 'spare_part_category_level', 0, '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 通用状态字典 (分类表状态字段复用)
-- 清理并重新插入 common_status 字典数据
DELETE FROM `system_dict_data` WHERE `dict_type` = 'common_status';
INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '启用', '1', 'common_status', 0, 'success', '1', NOW(), '1', NOW()),
(2, '禁用', '0', 'common_status', 0, 'danger', '1', NOW(), '1', NOW());

-- 是否关键备件字典 (备件基础信息表需要)
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('是否关键备件', 'is_critical_spare_part', 0, '标识是否为关键备件', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '是', '1', 'is_critical_spare_part', 0, 'danger', '1', NOW(), '1', NOW()),
(2, '否', '0', 'is_critical_spare_part', 0, 'success', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 是否已发送通知字典 (备件预警记录表需要)
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('是否已发送', 'is_sent', 0, '是否已发送通知', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '是', '1', 'is_sent', 0, 'success', '1', NOW(), '1', NOW()),
(2, '否', '0', 'is_sent', 0, 'warning', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- ========== 确保现有字典数据完整性 ==========

-- 备件类型字典 (确保存在)
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('备件类型', 'spare_part_type', 0, '备件分类类型', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '易损件', '1', 'spare_part_type', 0, 'warning', '1', NOW(), '1', NOW()),
(2, '标准件', '2', 'spare_part_type', 0, 'primary', '1', NOW(), '1', NOW()),
(3, '专用件', '3', 'spare_part_type', 0, 'success', '1', NOW(), '1', NOW()),
(4, '工具', '4', 'spare_part_type', 0, 'info', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 使用频率字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('使用频率', 'usage_frequency', 0, '备件使用频率', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '很低', '1', 'usage_frequency', 0, '1', NOW(), '1', NOW()),
(2, '低', '2', 'usage_frequency', 0, '1', NOW(), '1', NOW()),
(3, '中等', '3', 'usage_frequency', 0, '1', NOW(), '1', NOW()),
(4, '高', '4', 'usage_frequency', 0, '1', NOW(), '1', NOW()),
(5, '很高', '5', 'usage_frequency', 0, '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 备件状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('备件状态', 'spare_part_status', 0, '备件状态', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '正常', '1', 'spare_part_status', 0, 'success', '1', NOW(), '1', NOW()),
(2, '停用', '2', 'spare_part_status', 0, 'warning', '1', NOW(), '1', NOW()),
(3, '淘汰', '3', 'spare_part_status', 0, 'danger', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 库存类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('库存类型', 'stock_type', 0, '库存类型', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '可用库存', '1', 'stock_type', 0, 'success', '1', NOW(), '1', NOW()),
(2, '锁定库存', '2', 'stock_type', 0, 'warning', '1', NOW(), '1', NOW()),
(3, '待检库存', '3', 'stock_type', 0, 'info', '1', NOW(), '1', NOW()),
(4, '报废库存', '4', 'stock_type', 0, 'danger', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 操作类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('出入库操作类型', 'operation_type', 0, '出入库操作类型', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '入库', '1', 'operation_type', 0, 'success', '1', NOW(), '1', NOW()),
(2, '出库', '2', 'operation_type', 0, 'primary', '1', NOW(), '1', NOW()),
(3, '调拨', '3', 'operation_type', 0, 'info', '1', NOW(), '1', NOW()),
(4, '盘点', '4', 'operation_type', 0, 'warning', '1', NOW(), '1', NOW()),
(5, '报废', '5', 'operation_type', 0, 'danger', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 预警类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('预警类型', 'alert_type', 0, '备件预警类型', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '库存不足', '1', 'alert_type', 0, 'danger', '1', NOW(), '1', NOW()),
(2, '库存过多', '2', 'alert_type', 0, 'warning', '1', NOW(), '1', NOW()),
(3, '更换提醒', '3', 'alert_type', 0, 'primary', '1', NOW(), '1', NOW()),
(4, '过期提醒', '4', 'alert_type', 0, 'info', '1', NOW(), '1', NOW()),
(5, '停产预警', '5', 'alert_type', 0, 'danger', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 预警级别字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('预警级别', 'alert_level', 0, '预警级别', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '低', '1', 'alert_level', 0, 'info', '1', NOW(), '1', NOW()),
(2, '中', '2', 'alert_level', 0, 'warning', '1', NOW(), '1', NOW()),
(3, '高', '3', 'alert_level', 0, 'danger', '1', NOW(), '1', NOW()),
(4, '紧急', '4', 'alert_level', 0, 'danger', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 预警状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('预警状态', 'alert_status', 0, '预警处理状态', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '待处理', '1', 'alert_status', 0, 'warning', '1', NOW(), '1', NOW()),
(2, '已处理', '2', 'alert_status', 0, 'success', '1', NOW(), '1', NOW()),
(3, '已忽略', '3', 'alert_status', 0, 'info', '1', NOW(), '1', NOW()),
(4, '已关闭', '4', 'alert_status', 0, 'default', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 使用类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('使用类型', 'usage_type', 0, '备件使用类型', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '计划更换', '1', 'usage_type', 0, 'success', '1', NOW(), '1', NOW()),
(2, '故障更换', '2', 'usage_type', 0, 'danger', '1', NOW(), '1', NOW()),
(3, '预防性更换', '3', 'usage_type', 0, 'primary', '1', NOW(), '1', NOW()),
(4, '临时更换', '4', 'usage_type', 0, 'warning', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 更换难度字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('更换难度', 'replacement_difficulty', 0, '备件更换难度', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '容易', '1', 'replacement_difficulty', 0, 'success', '1', NOW(), '1', NOW()),
(2, '一般', '2', 'replacement_difficulty', 0, 'primary', '1', NOW(), '1', NOW()),
(3, '困难', '3', 'replacement_difficulty', 0, 'warning', '1', NOW(), '1', NOW()),
(4, '很困难', '4', 'replacement_difficulty', 0, 'danger', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 旧件状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('旧件状态', 'old_part_condition', 0, '旧备件状态', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '正常', '1', 'old_part_condition', 0, 'success', '1', NOW(), '1', NOW()),
(2, '磨损', '2', 'old_part_condition', 0, 'warning', '1', NOW(), '1', NOW()),
(3, '损坏', '3', 'old_part_condition', 0, 'danger', '1', NOW(), '1', NOW()),
(4, '报废', '4', 'old_part_condition', 0, 'danger', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 性能评级字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('性能评级', 'performance_rating', 0, '备件性能评级', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '优秀', '1', 'performance_rating', 0, 'success', '1', NOW(), '1', NOW()),
(2, '良好', '2', 'performance_rating', 0, 'primary', '1', NOW(), '1', NOW()),
(3, '一般', '3', 'performance_rating', 0, 'info', '1', NOW(), '1', NOW()),
(4, '较差', '4', 'performance_rating', 0, 'warning', '1', NOW(), '1', NOW()),
(5, '很差', '5', 'performance_rating', 0, 'danger', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 审批状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('审批状态', 'approve_status', 0, '审批状态', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '待审批', '1', 'approve_status', 0, 'warning', '1', NOW(), '1', NOW()),
(2, '已审批', '2', 'approve_status', 0, 'success', '1', NOW(), '1', NOW()),
(3, '已拒绝', '3', 'approve_status', 0, 'danger', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);

-- 是否必需字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('是否必需', 'is_required', 0, '是否必需备件', '1', NOW(), '1', NOW(), 0)
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '必需', '1', 'is_required', 0, 'danger', '1', NOW(), '1', NOW()),
(2, '非必需', '0', 'is_required', 0, 'success', '1', NOW(), '1', NOW())
ON DUPLICATE KEY UPDATE `label` = VALUES(`label`);
