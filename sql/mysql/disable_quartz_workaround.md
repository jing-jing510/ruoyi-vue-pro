# Quartz 表找不到问题 - 临时解决方案

## 问题原因
Linux MySQL 默认区分表名大小写，但 Quartz 期望大写表名（QRTZ_TRIGGERS）。
如果数据库中的表是小写（qrtz_triggers），就会报错。

## 快速解决方案（3选1）

### 方案1：临时禁用 Quartz（最快，适合紧急启动）
修改 `application-dev.yaml` 文件，在 Quartz 配置中添加：

```yaml
spring:
  quartz:
    auto-startup: false  # 改为 false，禁用定时任务
```

这样应用可以正常启动，但定时任务不会执行。

### 方案2：检查并修复表名（推荐）
1. 登录 MySQL：
```bash
mysql -h 192.168.10.24 -u root -p123456
use `ruoyi-vue-pro`;
```

2. 检查表名：
```sql
SHOW TABLES LIKE 'QRTZ%';  -- 查看大写表
SHOW TABLES LIKE 'qrtz%';  -- 查看小写表
```

3. 如果表是小写的，执行重命名：
```sql
-- 先删除外键约束的表
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
DROP TABLE IF EXISTS `qrtz_fired_triggers`;

-- 重命名主表
RENAME TABLE `qrtz_job_details` TO `QRTZ_JOB_DETAILS`;
RENAME TABLE `qrtz_triggers` TO `QRTZ_TRIGGERS`;
RENAME TABLE `qrtz_calendars` TO `QRTZ_CALENDARS`;
RENAME TABLE `qrtz_locks` TO `QRTZ_LOCKS`;
RENAME TABLE `qrtz_paused_trigger_grps` TO `QRTZ_PAUSED_TRIGGER_GRPS`;
RENAME TABLE `qrtz_scheduler_state` TO `QRTZ_SCHEDULER_STATE`;

-- 然后重新执行 quartz.sql 创建外键表
```

### 方案3：重新导入 Quartz 表（最彻底）
1. 删除所有 Quartz 表：
```bash
mysql -h 192.168.10.24 -u root -p123456 ruoyi-vue-pro < sql/mysql/fix_quartz_tables.sql
```

2. 重新导入：
```bash
mysql -h 192.168.10.24 -u root -p123456 ruoyi-vue-pro < sql/mysql/quartz.sql
```

## 验证
启动应用后，检查日志中是否还有 QRTZ_TRIGGERS 相关错误。

## 注意事项
- 如果选择方案2或3，会丢失现有的定时任务数据
- 方案1最安全，不会丢失数据，但定时任务不会执行
- 生产环境建议使用方案2或3彻底解决
