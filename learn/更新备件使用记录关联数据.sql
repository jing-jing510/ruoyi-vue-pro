-- 更新备件使用记录，关联到检修单
-- 首先查看现有的检修单数据
SELECT id, order_code, equipment_id FROM coal_maintenance_order LIMIT 5;

-- 更新备件使用记录，关联到第一个检修单
UPDATE coal_spare_part_usage_record 
SET work_order_id = (SELECT id FROM coal_maintenance_order LIMIT 1)
WHERE id = 3;

-- 更新备件使用记录，关联到第二个检修单（如果存在）
UPDATE coal_spare_part_usage_record 
SET work_order_id = (SELECT id FROM coal_maintenance_order LIMIT 1 OFFSET 1)
WHERE id = 4;

-- 查看更新后的数据
SELECT id, work_order_id, equipment_id, spare_part_id FROM coal_spare_part_usage_record WHERE work_order_id IS NOT NULL;
