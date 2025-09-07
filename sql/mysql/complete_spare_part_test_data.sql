-- =============================================
-- 选煤厂备件管理完整测试数据
-- 基于现有设备档案生成对应备件
-- =============================================

-- 1. 补充ERP产品单位（如果不存在）
INSERT IGNORE INTO erp_product_unit (name, status) VALUES
('个', 0),
('套', 0),
('米', 0),
('公斤', 0),
('升', 0);

-- 2. 补充ERP仓库（备件仓库）
INSERT IGNORE INTO erp_warehouse (name, address, sort, remark, creator, create_time, updater, update_time) VALUES
('主备件库', '选煤厂主备件仓库', 1, '主要备件存储区域', 'admin', NOW(), 'admin', NOW()),
('紧急备件库', '选煤厂紧急备件库', 2, '紧急备件存储区域', 'admin', NOW(), 'admin', NOW()),
('易损件库', '选煤厂易损件库', 3, '易损件专用仓库', 'admin', NOW(), 'admin', NOW());

-- 3. 插入完整的备件信息（与现有设备对应）
-- 删除现有测试数据，重新插入完整数据
DELETE FROM erp_product WHERE spare_part_type IS NOT NULL;

-- 破碎设备备件（对应颚式破碎机 PC001 和圆锥破碎机 PC002）
INSERT INTO erp_product (name, bar_code, category_id, unit_id, status, standard, remark, purchase_price, sale_price, min_price, equipment_id, spare_part_type, min_stock, max_stock, safety_stock, supplier_name, replacement_cycle, last_replacement_date, next_replacement_date, creator, create_time, updater, update_time) VALUES
-- 颚式破碎机备件
('颚板（动颚）', 'PC001-001', 2, 1, 0, '高锰钢Mn13', '颚式破碎机动颚板，易损件', 2800.00, 3500.00, 2600.00, 1, 1, 2, 8, 4, '河南红星机器有限公司', 90, '2024-09-01', '2024-11-30', 'admin', NOW(), 'admin', NOW()),
('颚板（定颚）', 'PC001-002', 2, 1, 0, '高锰钢Mn13', '颚式破碎机定颚板，易损件', 2500.00, 3200.00, 2300.00, 1, 1, 2, 6, 3, '河南红星机器有限公司', 120, '2024-08-15', '2024-12-13', 'admin', NOW(), 'admin', NOW()),
('主轴承6324', 'PC001-003', 2, 1, 0, 'Φ120×260×55mm', '颚式破碎机主轴承', 1800.00, 2200.00, 1700.00, 1, 2, 1, 4, 2, 'SKF中国有限公司', 365, '2024-03-01', '2025-03-01', 'admin', NOW(), 'admin', NOW()),
('肘板', 'PC001-004', 2, 1, 0, '铸钢件', '颚式破碎机肘板，保护件', 800.00, 1000.00, 750.00, 1, 1, 2, 10, 5, '上海建工配件厂', 60, '2024-10-01', '2024-11-30', 'admin', NOW(), 'admin', NOW()),
('飞轮', 'PC001-005', 2, 1, 0, 'HT200铸铁', '颚式破碎机飞轮', 3200.00, 4000.00, 3000.00, 1, 3, 1, 2, 1, '上海建工配件厂', 1095, '2024-01-15', '2027-01-15', 'admin', NOW(), 'admin', NOW()),

-- 圆锥破碎机备件
('破碎壁', 'PC002-001', 2, 1, 0, '高锰钢Mn18Cr2', '圆锥破碎机破碎壁', 4500.00, 5500.00, 4200.00, 2, 1, 1, 4, 2, '山特维克配件中心', 45, '2024-10-15', '2024-11-29', 'admin', NOW(), 'admin', NOW()),
('轧臼壁', 'PC002-002', 2, 1, 0, '高锰钢Mn18Cr2', '圆锥破碎机轧臼壁', 4200.00, 5200.00, 4000.00, 2, 1, 1, 4, 2, '山特维克配件中心', 60, '2024-09-20', '2024-11-19', 'admin', NOW(), 'admin', NOW()),
('主轴承22324', 'PC002-003', 2, 1, 0, 'Φ120×260×86mm', '圆锥破主轴承', 2500.00, 3000.00, 2300.00, 2, 2, 1, 3, 2, 'SKF中国有限公司', 730, '2024-02-01', '2026-02-01', 'admin', NOW(), 'admin', NOW()),
('液压油', 'PC002-004', 3, 5, 0, '46#抗磨液压油', '圆锥破液压系统用油', 25.00, 35.00, 22.00, 2, 1, 50, 200, 100, '中石化润滑油公司', 180, '2024-08-01', '2025-02-01', 'admin', NOW(), 'admin', NOW()),

-- 洗选设备备件（对应跳汰机 XS001 和重介分选机 XS002）
('筛板', 'XS001-001', 3, 1, 0, '不锈钢304', '跳汰机筛板', 1200.00, 1500.00, 1100.00, 3, 1, 3, 12, 6, '唐山选煤配件厂', 120, '2024-07-01', '2024-11-01', 'admin', NOW(), 'admin', NOW()),
('风阀橡胶膜', 'XS001-002', 3, 1, 0, '耐油橡胶', '跳汰机风阀膜片', 180.00, 250.00, 160.00, 3, 1, 10, 50, 20, '河北橡胶制品厂', 30, '2024-10-20', '2024-11-19', 'admin', NOW(), 'admin', NOW()),
('重介桶', 'XS002-001', 3, 1, 0, '不锈钢316L', '重介分选机重介桶', 3800.00, 4500.00, 3600.00, 4, 2, 1, 3, 2, '山东矿机配件部', 365, '2024-06-01', '2025-06-01', 'admin', NOW(), 'admin', NOW()),
('磁选辊', 'XS002-002', 3, 1, 0, '永磁材料', '重介分选机磁选辊', 2200.00, 2800.00, 2000.00, 4, 2, 1, 4, 2, '山东矿机配件部', 730, '2024-04-01', '2026-04-01', 'admin', NOW(), 'admin', NOW()),

-- 脱水设备备件（对应离心脱水机 TS001 和压滤机 TS002）
('离心机筛篮', 'TS001-001', 3, 1, 0, '不锈钢316', '离心脱水机筛篮', 2800.00, 3500.00, 2600.00, 5, 1, 1, 4, 2, '江苏华宏配件部', 180, '2024-08-01', '2025-02-01', 'admin', NOW(), 'admin', NOW()),
('离心机轴承', 'TS001-002', 2, 1, 0, 'Φ85×150×28mm', '离心机主轴承', 1200.00, 1500.00, 1100.00, 5, 2, 2, 8, 4, 'NSK中国有限公司', 365, '2024-05-01', '2025-05-01', 'admin', NOW(), 'admin', NOW()),
('滤布', 'TS002-001', 3, 4, 0, '聚丙烯滤布', '压滤机滤布', 45.00, 60.00, 40.00, 6, 1, 20, 100, 50, '浙江滤布制造厂', 90, '2024-09-15', '2024-12-14', 'admin', NOW(), 'admin', NOW()),
('液压油缸密封件', 'TS002-002', 3, 2, 0, 'NBR橡胶', '压滤机液压缸密封圈套装', 320.00, 400.00, 300.00, 6, 1, 5, 20, 10, '景津配件中心', 180, '2024-07-01', '2025-01-01', 'admin', NOW(), 'admin', NOW()),

-- 输送设备备件（对应皮带输送机 SS001 和刮板输送机 SS002）
('输送带', 'SS001-001', 2, 3, 0, 'EP200-1000mm', '皮带输送机输送带', 280.00, 350.00, 260.00, 7, 1, 100, 500, 200, '青岛橡胶集团', 365, '2024-06-01', '2025-06-01', 'admin', NOW(), 'admin', NOW()),
('托辊', 'SS001-002', 2, 1, 0, 'Φ108×465mm', '皮带输送机托辊', 85.00, 120.00, 80.00, 7, 1, 50, 200, 100, '山东华机配件部', 730, '2024-03-01', '2026-03-01', 'admin', NOW(), 'admin', NOW()),
('滚筒', 'SS001-003', 2, 1, 0, 'Φ500×1000mm', '皮带输送机传动滚筒', 2800.00, 3500.00, 2600.00, 7, 2, 1, 3, 2, '山东华机配件部', 1095, '2024-01-01', '2027-01-01', 'admin', NOW(), 'admin', NOW()),
('刮板链条', 'SS002-001', 2, 3, 0, '42CrMo合金钢', '刮板输送机链条', 450.00, 600.00, 420.00, 8, 1, 10, 50, 20, '山西煤机配件厂', 180, '2024-08-01', '2025-02-01', 'admin', NOW(), 'admin', NOW()),
('刮板', 'SS002-002', 2, 1, 0, '高分子聚乙烯', '刮板输送机刮板', 120.00, 150.00, 110.00, 8, 1, 30, 150, 60, '山西煤机配件厂', 120, '2024-09-01', '2024-12-30', 'admin', NOW(), 'admin', NOW()),

-- 电气设备备件（对应主电机 DQ001 和配电柜 DQ002）
('电机碳刷', 'DQ001-001', 5, 1, 0, 'D374N', '主电机碳刷', 45.00, 60.00, 42.00, 9, 1, 20, 100, 40, '上海电机配件厂', 90, '2024-10-01', '2024-12-30', 'admin', NOW(), 'admin', NOW()),
('电机轴承6322', 'DQ001-002', 2, 1, 0, 'Φ110×240×50mm', '主电机轴承', 800.00, 1000.00, 750.00, 9, 2, 2, 8, 4, 'FAG中国有限公司', 365, '2024-04-01', '2025-04-01', 'admin', NOW(), 'admin', NOW()),
('绝缘漆', 'DQ001-003', 5, 5, 0, 'F级绝缘漆', '电机绕组绝缘漆', 120.00, 150.00, 110.00, 9, 1, 5, 20, 10, '上海绝缘材料厂', 365, '2024-06-01', '2025-06-01', 'admin', NOW(), 'admin', NOW()),
('断路器', 'DQ002-001', 6, 1, 0, 'NSX400N', '配电柜主断路器', 2800.00, 3500.00, 2600.00, 10, 2, 1, 4, 2, '施耐德电气', 1825, '2024-01-01', '2029-01-01', 'admin', NOW(), 'admin', NOW()),
('接触器', 'DQ002-002', 6, 1, 0, 'LC1D80M7C', '配电柜接触器', 380.00, 480.00, 350.00, 10, 2, 3, 15, 6, '施耐德电气', 1095, '2024-03-01', '2027-03-01', 'admin', NOW(), 'admin', NOW()),
('熔断器', 'DQ002-003', 6, 1, 0, 'RT18-32', '配电柜熔断器', 25.00, 35.00, 22.00, 10, 1, 20, 100, 50, '德力西电气', 365, '2024-08-01', '2025-08-01', 'admin', NOW(), 'admin', NOW());

-- 4. 插入库存数据（模拟当前库存状态）
-- 获取仓库ID
SET @main_warehouse = (SELECT id FROM erp_warehouse WHERE name = '主备件库' LIMIT 1);
SET @emergency_warehouse = (SELECT id FROM erp_warehouse WHERE name = '紧急备件库' LIMIT 1);
SET @consumable_warehouse = (SELECT id FROM erp_warehouse WHERE name = '易损件库' LIMIT 1);

-- 插入库存记录（一些库存不足，一些正常，一些过多）
INSERT INTO erp_stock (product_id, warehouse_id, count) 
SELECT p.id, 
       CASE 
         WHEN p.spare_part_type = 1 THEN @consumable_warehouse  -- 易损件放易损件库
         WHEN p.spare_part_type = 2 THEN @main_warehouse        -- 关键件放主库
         ELSE @emergency_warehouse                              -- 标准件放紧急库
       END as warehouse_id,
       CASE 
         WHEN p.name LIKE '%颚板%' THEN 1.00                   -- 库存不足，触发预警
         WHEN p.name LIKE '%破碎壁%' THEN 1.00                 -- 库存不足，触发预警
         WHEN p.name LIKE '%风阀%' THEN 8.00                   -- 库存不足，触发预警
         WHEN p.name LIKE '%碳刷%' THEN 15.00                  -- 库存不足，触发预警
         WHEN p.name LIKE '%输送带%' THEN 300.00               -- 库存过多
         WHEN p.name LIKE '%托辊%' THEN 180.00                 -- 库存过多
         ELSE ROUND(p.safety_stock * 1.5, 2)                  -- 其他正常库存
       END as count
FROM erp_product p 
WHERE p.spare_part_type IS NOT NULL;

-- 5. 插入库存记录明细（用于追溯）
INSERT INTO erp_stock_record (product_id, warehouse_id, count, total_count, biz_type, biz_id, no, remark)
SELECT s.product_id, s.warehouse_id, s.count, s.count, 1, 1, 
       CONCAT('RK-2024-', LPAD(ROW_NUMBER() OVER (ORDER BY s.product_id), 3, '0')), 
       '初始库存入库'
FROM erp_stock s
JOIN erp_product p ON s.product_id = p.id
WHERE p.spare_part_type IS NOT NULL;

-- 6. 插入备件设备关联数据
INSERT INTO coal_spare_part_equipment (spare_part_id, equipment_id, is_required, quantity, unit, install_position, replacement_difficulty, remark)
SELECT p.id, p.equipment_id, 
       1,  -- 都设为必需
       CASE 
         WHEN p.name LIKE '%轴承%' THEN 2.00
         WHEN p.name LIKE '%碳刷%' THEN 4.00
         WHEN p.name LIKE '%滤布%' THEN 1.00
         WHEN p.name LIKE '%输送带%' THEN 1.00
         WHEN p.name LIKE '%托辊%' THEN 20.00
         ELSE 1.00
       END,
       '个',
       CASE 
         WHEN p.name LIKE '%轴承%' THEN '轴承座'
         WHEN p.name LIKE '%碳刷%' THEN '电刷架'
         WHEN p.name LIKE '%颚板%' THEN '破碎腔'
         WHEN p.name LIKE '%输送带%' THEN '传动系统'
         WHEN p.name LIKE '%滤布%' THEN '过滤室'
         ELSE '设备主体'
       END,
       CASE 
         WHEN p.spare_part_type = 1 THEN '简单'
         WHEN p.spare_part_type = 2 THEN '困难'
         ELSE '中等'
       END,
       CONCAT(e.equipment_name, '专用', p.name)
FROM erp_product p
JOIN coal_equipment_info e ON p.equipment_id = e.id
WHERE p.spare_part_type IS NOT NULL;

-- 验证数据插入结果
SELECT '=== 备件产品统计 ===' as info;
SELECT spare_part_type, COUNT(*) as count FROM erp_product WHERE spare_part_type IS NOT NULL GROUP BY spare_part_type;

SELECT '=== 库存统计 ===' as info;
SELECT w.name as warehouse_name, COUNT(*) as product_count, SUM(s.count) as total_stock
FROM erp_stock s 
JOIN erp_warehouse w ON s.warehouse_id = w.id 
JOIN erp_product p ON s.product_id = p.id 
WHERE p.spare_part_type IS NOT NULL 
GROUP BY w.id, w.name;

SELECT '=== 设备关联统计 ===' as info;
SELECT e.equipment_name, COUNT(*) as spare_part_count 
FROM coal_spare_part_equipment se
JOIN coal_equipment_info e ON se.equipment_id = e.id
GROUP BY e.id, e.equipment_name
ORDER BY spare_part_count DESC;
