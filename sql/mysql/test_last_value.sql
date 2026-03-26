-- 测试 last_value 字段是否是保留关键字问题
-- 尝试直接查询
SELECT id, config_id, device_name, name, node_id, data_type, is_alarm, alarm_level, alarm_content, last_value, enabled, remark 
FROM opcua_tag_config 
WHERE deleted = 0 
LIMIT 1;

-- 尝试用反引号
SELECT id, config_id, device_name, name, node_id, data_type, is_alarm, alarm_level, alarm_content, `last_value`, enabled, remark 
FROM opcua_tag_config 
WHERE deleted = 0 
LIMIT 1;
