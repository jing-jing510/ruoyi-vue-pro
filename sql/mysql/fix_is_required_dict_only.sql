-- 只插入缺失的 is_required 字典数据
-- replacement_difficulty 字典数据已存在，不需要重复插入

-- 插入字典数据：是否必需
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(3190, 1, '必需', '1', 'is_required', 0, 'danger', '', '备件是必需的', '1', NOW(), '1', NOW(), b'0'),
(3191, 2, '非必需', '0', 'is_required', 0, 'info', '', '备件不是必需的', '1', NOW(), '1', NOW(), b'0');
