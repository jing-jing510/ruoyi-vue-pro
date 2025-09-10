-- 为检修单表添加关联报修单字段
-- 执行前请备份数据库

-- 1. 添加 repair_request_id 字段
ALTER TABLE `coal_maintenance_order` 
ADD COLUMN `repair_request_id` bigint DEFAULT NULL COMMENT '关联报修单ID' AFTER `plan_id`;

-- 2. 添加索引以提高查询性能
CREATE INDEX `idx_repair_request_id` ON `coal_maintenance_order` (`repair_request_id`);

-- 3. 添加外键约束（可选，如果需要强制引用完整性）
-- ALTER TABLE `coal_maintenance_order` 
-- ADD CONSTRAINT `fk_maintenance_order_repair_request` 
-- FOREIGN KEY (`repair_request_id`) REFERENCES `coal_repair_request` (`id`) 
-- ON DELETE SET NULL ON UPDATE CASCADE;

-- 验证字段添加成功
DESCRIBE `coal_maintenance_order`;
