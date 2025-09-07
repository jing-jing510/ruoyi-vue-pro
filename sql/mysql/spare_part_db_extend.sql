-- =============================================
-- 备件管理模块数据库扩展脚本
-- 基于芋道ERP模块扩展
-- =============================================

-- 1. 扩展ERP产品表，添加备件特有字段
ALTER TABLE erp_product ADD COLUMN equipment_id BIGINT COMMENT '关联设备ID (关联coal_equipment_info.id)';
ALTER TABLE erp_product ADD COLUMN spare_part_type TINYINT COMMENT '备件类型：1易损件 2关键件 3标准件';
ALTER TABLE erp_product ADD COLUMN min_stock DECIMAL(10,2) COMMENT '最低库存预警数量';
ALTER TABLE erp_product ADD COLUMN max_stock DECIMAL(10,2) COMMENT '最高库存数量';
ALTER TABLE erp_product ADD COLUMN safety_stock DECIMAL(10,2) COMMENT '安全库存数量';
ALTER TABLE erp_product ADD COLUMN supplier_id BIGINT COMMENT '主要供应商ID (关联erp_supplier.id)';
ALTER TABLE erp_product ADD COLUMN replacement_cycle INT COMMENT '更换周期(天)';
ALTER TABLE erp_product ADD COLUMN last_replacement_date DATE COMMENT '最后更换日期';
ALTER TABLE erp_product ADD COLUMN next_replacement_date DATE COMMENT '下次更换日期';

-- 2. 创建备件设备关联表
DROP TABLE IF EXISTS `coal_spare_part_equipment`;
CREATE TABLE `coal_spare_part_equipment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `spare_part_id` BIGINT NOT NULL COMMENT '备件ID (关联erp_product.id)',
    `equipment_id` BIGINT NOT NULL COMMENT '设备ID (关联coal_equipment_info.id)',
    `usage_count` INT NOT NULL DEFAULT 0 COMMENT '使用数量',
    `is_required` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否必需：1是 0否',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_spare_part` (`spare_part_id`) USING BTREE,
    INDEX `idx_equipment` (`equipment_id`) USING BTREE,
    UNIQUE KEY `uk_spare_equipment` (`spare_part_id`, `equipment_id`, `deleted`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '备件设备关联表';

-- 3. 创建备件预警记录表
DROP TABLE IF EXISTS `coal_spare_part_alert`;
CREATE TABLE `coal_spare_part_alert` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '预警ID',
    `spare_part_id` BIGINT NOT NULL COMMENT '备件ID (关联erp_product.id)',
    `warehouse_id` BIGINT NOT NULL COMMENT '仓库ID (关联erp_warehouse.id)',
    `alert_type` TINYINT NOT NULL COMMENT '预警类型：1库存不足 2库存过多 3到期提醒 4更换提醒',
    `current_stock` DECIMAL(10,2) NOT NULL COMMENT '当前库存',
    `threshold_value` DECIMAL(10,2) NOT NULL COMMENT '阈值',
    `alert_level` TINYINT NOT NULL DEFAULT 1 COMMENT '预警级别：1低 2中 3高',
    `alert_status` TINYINT NOT NULL DEFAULT 1 COMMENT '预警状态：1待处理 2已处理 3已忽略',
    `alert_message` VARCHAR(500) NOT NULL COMMENT '预警信息',
    `handler_id` BIGINT NULL COMMENT '处理人ID',
    `handle_time` DATETIME NULL COMMENT '处理时间',
    `handle_remark` VARCHAR(500) NULL COMMENT '处理备注',
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_spare_part` (`spare_part_id`) USING BTREE,
    INDEX `idx_warehouse` (`warehouse_id`) USING BTREE,
    INDEX `idx_alert_type` (`alert_type`) USING BTREE,
    INDEX `idx_alert_status` (`alert_status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '备件预警记录表';

-- 4. 验证表结构
-- 查看erp_product表结构
DESCRIBE erp_product;

-- 查看新增表结构
DESCRIBE coal_spare_part_equipment;
DESCRIBE coal_spare_part_alert;
