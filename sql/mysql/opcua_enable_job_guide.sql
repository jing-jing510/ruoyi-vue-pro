-- 修复 opcua_alarm_event 表的 tag_id 字段
-- 将 tag_id 改为可空，因为手动新增报警时可能不关联具体点位

ALTER TABLE `opcua_alarm_event` 
MODIFY COLUMN `tag_id` bigint NULL COMMENT '点位ID';

-- 验证修改
SHOW COLUMNS FROM `opcua_alarm_event` LIKE 'tag_id';