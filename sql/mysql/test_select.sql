-- 测试 SELECT 语句
SELECT id, config_id, device_name, name, node_id, data_type, is_alarm, alarm_level, alarm_content, last_value, enabled, remark, create_time, update_time, creator, updater, deleted 
FROM opcua_tag_config 
WHERE deleted = 0 AND node_id = 'ns=3;i=1009' AND tenant_id = 1;
