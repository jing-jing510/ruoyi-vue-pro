-- OPC UA 报警采集定时任务配置
-- 使用方式：在管理后台 "系统管理 → 定时任务" 中手动添加，或执行此SQL直接插入

-- 注意：需要根据实际情况修改 creator 和 tenant_id
INSERT INTO infra_job (name, status, handler_name, handler_param, cron_expression, 
                       retry_count, retry_interval, monitor_timeout, 
                       creator, create_time, updater, update_time, deleted)
VALUES (
    '报警采集任务',                    -- 任务名称
    2,                                  -- 状态：1-开启 2-暂停（建议先暂停，测试后再开启）
    'alarmCollectJob',                  -- 处理器名称（Spring Bean名称）
    '',                                 -- 处理器参数（留空）
    '0/30 * * * * ?',                   -- CRON表达式：每30秒执行一次
    0,                                  -- 重试次数：0-不重试
    0,                                  -- 重试间隔：0毫秒
    30000,                              -- 监控超时时间：30秒
    '1',                                -- 创建者（根据实际情况修改）
    NOW(),                              -- 创建时间
    '1',                                -- 更新者
    NOW(),                              -- 更新时间
    0                                   -- 是否删除：0-未删除
);

-- 查询刚插入的任务
SELECT * FROM infra_job WHERE handler_name = 'alarmCollectJob';

-- 常用 CRON 表达式参考：
-- 每10秒：  0/10 * * * * ?
-- 每30秒：  0/30 * * * * ?
-- 每1分钟： 0 * * * * ?
-- 每5分钟： 0 0/5 * * * ?
-- 每小时：  0 0 * * * ?

-- 启用任务（测试通过后执行）
-- UPDATE infra_job SET status = 1 WHERE handler_name = 'alarmCollectJob';

-- 暂停任务
-- UPDATE infra_job SET status = 2 WHERE handler_name = 'alarmCollectJob';

-- 删除任务
-- DELETE FROM infra_job WHERE handler_name = 'alarmCollectJob';
