-- 修复 opcua_tag_config 表的 last_value 字段
-- 先检查表结构，如果有问题则删除后重新添加

-- 方案1：如果字段已存在但有问题，先删除再添加
-- ALTER TABLE `opcua_tag_config` DROP COLUMN IF EXISTS `last_value`;
-- ALTER TABLE `opcua_tag_config` 
-- ADD COLUMN `last_value` varchar(100) DEFAULT NULL COMMENT '上次采集值（用于判断0→1）' 
-- AFTER `alarm_content`;

-- 方案2：直接修改字段定义
ALTER TABLE `opcua_tag_config` 
MODIFY COLUMN `last_value` varchar(100) DEFAULT NULL COMMENT '上次采集值（用于判断0→1）' 
AFTER `alarm_content`;

-- 如果上面的语句报错说字段不存在，则执行下面的添加语句
-- ALTER TABLE `opcua_tag_config` 
-- ADD COLUMN `last_value` varchar(100) DEFAULT NULL COMMENT '上次采集值（用于判断0→1）' 
-- AFTER `alarm_content`;
