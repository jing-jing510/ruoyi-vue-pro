-- 给质量预警记录表添加接收人列表字段
-- 参考备件预警记录表的结构

ALTER TABLE `coal_quality_alert` 
ADD COLUMN `recipients` VARCHAR(500) DEFAULT NULL COMMENT '接收人列表(逗号分隔)' 
AFTER `notification_time`;
