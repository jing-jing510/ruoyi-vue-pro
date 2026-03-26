-- 删除小写的 Quartz 表（保留大写的表）
-- 数据库：ruoyi-vue-pro

USE `ruoyi-vue-pro`;

-- 删除所有小写的 Quartz 表
-- 注意：先删除有外键约束的子表
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;

-- 删除主表
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
DROP TABLE IF EXISTS `qrtz_triggers`;
DROP TABLE IF EXISTS `qrtz_calendars`;
DROP TABLE IF EXISTS `qrtz_job_details`;
DROP TABLE IF EXISTS `qrtz_locks`;
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
DROP TABLE IF EXISTS `qrtz_scheduler_state`;

-- 完成！小写的 Quartz 表已删除，保留大写的表
SELECT '✓ 小写 Quartz 表已删除，现在只有大写表' AS status;

-- 验证：查看剩余的 Quartz 表（应该只有大写的）
SHOW TABLES LIKE 'QRTZ%';
