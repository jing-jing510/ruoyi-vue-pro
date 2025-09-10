-- 创建备件预警通知站内信模板

-- 1. 添加备件管理模板类型到字典表
INSERT IGNORE INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(2000, 3, '备件管理', '3', 'system_notify_template_type', 0, 'warning', '', '站内信模版的类型 - 备件管理', '1', NOW(), '1', NOW(), b'0');

-- 2. 创建备件预警通知模板
INSERT INTO `system_notify_template` (`id`, `name`, `code`, `nickname`, `content`, `type`, `params`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(1001, '备件预警通知', 'spare_part_alert', '系统管理员', 
'【备件预警通知】

预警标题：{alertTitle}
备件名称：{sparePartName}
预警类型：{alertType}
预警级别：{alertLevel}
预警信息：{alertMessage}
当前库存：{currentStock}
阈值：{thresholdValue}

请及时处理该预警信息！

发送时间：{sendTime}', 
3, 
'["alertTitle", "sparePartName", "alertType", "alertLevel", "alertMessage", "currentStock", "thresholdValue", "sendTime"]', 
0, 
'备件预警通知模板', 
'1', NOW(), '1', NOW(), b'0');
