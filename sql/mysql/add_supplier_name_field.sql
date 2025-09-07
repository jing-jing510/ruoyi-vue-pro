-- =============================================
-- 添加supplier_name字段到erp_product表
-- 用于备件管理模块存储供应商名称
-- =============================================

-- 添加supplier_name字段
ALTER TABLE erp_product ADD COLUMN supplier_name VARCHAR(255) COMMENT '供应商名称';

-- 验证字段添加
DESCRIBE erp_product;

-- 可选：从现有的supplier表同步数据（如果有的话）
-- UPDATE erp_product p 
-- LEFT JOIN erp_supplier s ON p.supplier_id = s.id 
-- SET p.supplier_name = s.name 
-- WHERE p.supplier_id IS NOT NULL;
