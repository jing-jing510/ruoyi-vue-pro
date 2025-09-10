-- 修复备件设备关联表缺失的字典数据

-- 1. 插入字典类型：是否必需
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(120, '是否必需', 'is_required', 0, '备件是否必需字典', '1', NOW(), '1', NOW(), b'0');

-- 2. 插入字典数据：是否必需
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(1201, 1, '必需', '1', 'is_required', 0, 'danger', '', '备件是必需的', '1', NOW(), '1', NOW(), b'0'),
(1202, 2, '非必需', '0', 'is_required', 0, 'info', '', '备件不是必需的', '1', NOW(), '1', NOW(), b'0');

-- 3. 插入字典类型：更换难度
INSERT INTO `system_dict_type` (`id`, `name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(121, '更换难度', 'replacement_difficulty', 0, '备件更换难度字典', '1', NOW(), '1', NOW(), b'0');

-- 4. 插入字典数据：更换难度
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES
(1211, 1, '容易', '1', 'replacement_difficulty', 0, 'success', '', '更换容易', '1', NOW(), '1', NOW(), b'0'),
(1212, 2, '一般', '2', 'replacement_difficulty', 0, 'warning', '', '更换一般', '1', NOW(), '1', NOW(), b'0'),
(1213, 3, '困难', '3', 'replacement_difficulty', 0, 'danger', '', '更换困难', '1', NOW(), '1', NOW(), b'0'),
(1214, 4, '很困难', '4', 'replacement_difficulty', 0, 'danger', '', '更换很困难', '1', NOW(), '1', NOW(), b'0');
