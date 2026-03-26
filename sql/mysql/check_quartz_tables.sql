-- 检查 Quartz 表是否存在
-- 在 MySQL 命令行执行以下命令查看实际的表名

-- 查看所有 QRTZ 开头的表（大写）
SHOW TABLES LIKE 'QRTZ%';

-- 查看所有 qrtz 开头的表（小写）
SHOW TABLES LIKE 'qrtz%';

-- 查看所有表
SHOW TABLES;

-- 查看 MySQL 表名大小写设置
SHOW VARIABLES LIKE 'lower_case_table_names';
