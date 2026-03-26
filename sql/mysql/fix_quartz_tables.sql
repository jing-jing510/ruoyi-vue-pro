-- 解决方案：重新创建 Quartz 表（如果表名大小写不匹配）
-- 
-- 问题：Linux MySQL 默认区分表名大小写，但 Quartz 期望大写表名
-- 
-- 执行步骤：
-- 1. 先执行 check_quartz_tables.sql 查看当前表名
-- 2. 如果表是小写的（qrtz_xxx），需要删除后重新创建
-- 3. 如果表不存在，直接执行 quartz.sql

-- ============================================
-- 方案1：删除现有的小写表（如果存在）
-- ============================================
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
DROP TABLE IF EXISTS `qrtz_calendars`;
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
DROP TABLE IF EXISTS `qrtz_job_details`;
DROP TABLE IF EXISTS `qrtz_locks`;
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
DROP TABLE IF EXISTS `qrtz_triggers`;

-- 然后执行 quartz.sql 重新创建大写表名的表

-- ============================================
-- 方案2：如果不想重新导入，可以重命名表（从小写改为大写）
-- ============================================
-- 注意：需要按照依赖顺序重命名，先重命名被依赖的表

-- RENAME TABLE `qrtz_job_details` TO `QRTZ_JOB_DETAILS`;
-- RENAME TABLE `qrtz_triggers` TO `QRTZ_TRIGGERS`;
-- RENAME TABLE `qrtz_blob_triggers` TO `QRTZ_BLOB_TRIGGERS`;
-- RENAME TABLE `qrtz_calendars` TO `QRTZ_CALENDARS`;
-- RENAME TABLE `qrtz_cron_triggers` TO `QRTZ_CRON_TRIGGERS`;
-- RENAME TABLE `qrtz_fired_triggers` TO `QRTZ_FIRED_TRIGGERS`;
-- RENAME TABLE `qrtz_locks` TO `QRTZ_LOCKS`;
-- RENAME TABLE `qrtz_paused_trigger_grps` TO `QRTZ_PAUSED_TRIGGER_GRPS`;
-- RENAME TABLE `qrtz_scheduler_state` TO `QRTZ_SCHEDULER_STATE`;
-- RENAME TABLE `qrtz_simple_triggers` TO `QRTZ_SIMPLE_TRIGGERS`;
-- RENAME TABLE `qrtz_simprop_triggers` TO `QRTZ_SIMPROP_TRIGGERS`;
