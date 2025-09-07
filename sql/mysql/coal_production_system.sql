-- ========================================
-- 选煤厂生产管理系统数据库脚本
-- 基于芋道开源框架标准
-- 数据库：MySQL 8.0+
-- ========================================

-- ----------------------------
-- 1. 生产计划表（树表结构）
-- ----------------------------
DROP TABLE IF EXISTS `coal_production_plan`;
CREATE TABLE `coal_production_plan` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '计划ID',
    `name` VARCHAR(100) NOT NULL COMMENT '计划名称',
    `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父计划ID',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '显示顺序',
    `plan_code` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '计划编号',
    `plan_type` TINYINT NOT NULL COMMENT '计划类型：1年度 2月度 3日计划 4班计划',
    `plan_year` INT NULL COMMENT '计划年度',
    `plan_month` TINYINT NULL COMMENT '计划月份',
    `plan_date` DATE NULL COMMENT '计划日期',
    `shift_id` BIGINT NULL COMMENT '班次ID',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '计划状态：0停用 1正常',
    
    -- 计划产量数据（基础）
    `raw_coal_plan` DECIMAL(10,2) NULL COMMENT '计划入洗原煤量(吨)',
    
    -- 精煤产量计划（按粒度分类）
    `fine_coal_plan` DECIMAL(10,2) NULL COMMENT '计划末煤产量(吨)',
    `granular_coal_plan` DECIMAL(10,2) NULL COMMENT '计划粒煤产量(吨)',
    `large_block_coal_plan` DECIMAL(10,2) NULL COMMENT '计划大块煤产量(吨)',
    `medium_block_coal_plan` DECIMAL(10,2) NULL COMMENT '计划中块煤产量(吨)',
    `small_block_coal_plan` DECIMAL(10,2) NULL COMMENT '计划小块煤产量(吨)',
    
    -- 其他产品计划产量
    `middling_coal_plan` DECIMAL(10,2) NULL COMMENT '计划中煤产量(吨)',
    `slime_plan` DECIMAL(10,2) NULL COMMENT '计划煤泥产量(吨)',
    `gangue_plan` DECIMAL(10,2) NULL COMMENT '计划矸石产量(吨)',
    
    -- 预留计划产量字段
    `reserved_product_plan1` DECIMAL(10,2) NULL COMMENT '预留计划产量字段1(吨)',
    `reserved_product_plan2` DECIMAL(10,2) NULL COMMENT '预留计划产量字段2(吨)',
    
    -- 质量指标
    `fine_coal_ash` DECIMAL(5,2) NULL COMMENT '末煤灰分(%)',
    `granular_coal_ash` DECIMAL(5,2) NULL COMMENT '粒煤灰分(%)',
    `large_block_coal_ash` DECIMAL(5,2) NULL COMMENT '大块煤灰分(%)',
    `medium_block_coal_ash` DECIMAL(5,2) NULL COMMENT '中块煤灰分(%)',
    `small_block_coal_ash` DECIMAL(5,2) NULL COMMENT '小块煤灰分(%)',
    `middling_coal_ash` DECIMAL(5,2) NULL COMMENT '中煤灰分(%)',
    
    -- 审批信息
    `creator_id` BIGINT NULL COMMENT '制定人ID',
    `approver_id` BIGINT NULL COMMENT '审批人ID',
    `approve_time` DATETIME NULL COMMENT '审批时间',
    
    -- 基础字段（芋道框架标准）
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '生产计划表（树表结构）';

-- ----------------------------
-- 2. 现场生产日报表
-- ----------------------------
DROP TABLE IF EXISTS `coal_production_daily_report`;
CREATE TABLE `coal_production_daily_report` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日报ID',
    `report_date` DATE NOT NULL COMMENT '日期',
    `shift_id` BIGINT NOT NULL COMMENT '班次ID',

    -- 人员信息
    `operator_id` BIGINT NULL COMMENT '集控员（操作人）ID',
    `shift_leader_id` BIGINT NULL COMMENT '带班主任/班长ID',

    -- 时间记录
    `start_time` TIME NULL COMMENT '启车时间',
    `coal_feeding_time` INT NULL COMMENT '带煤时间(分钟)',
    `stop_time` TIME NULL COMMENT '停车时间',
    `effective_feeding_time` INT NULL COMMENT '有效带煤时间(分钟)',
    `fault_impact_time` INT NULL COMMENT '故障影响时间(分钟)',

    -- 产量数据
    `raw_coal_input` DECIMAL(10,2) NULL COMMENT '入洗煤量(吨)',
    `hourly_capacity` DECIMAL(8,2) NULL COMMENT '小时处理量(吨/小时)',
    `block_clean_coal_output` DECIMAL(10,2) NULL COMMENT '块精煤产量(吨)',
    `fine_clean_coal_output` DECIMAL(10,2) NULL COMMENT '末精煤产量(吨)',
    `gangue_output` DECIMAL(10,2) NULL COMMENT '矸石产量(吨)',
    `middling_coal_output` DECIMAL(10,2) NULL COMMENT '中煤产量(吨)',

    -- 压滤相关
    `filter_press_cycles` INT NULL COMMENT '压滤板次',
    `filter_press_coal_amount` DECIMAL(8,2) NULL COMMENT '压滤煤量(吨)',
    `filter_cloth_usage` INT NULL COMMENT '滤布使用量(张)',

    -- 放舱记录
    `discharge_record` VARCHAR(500) NULL COMMENT '放舱记录',

    -- 介质添加
    `baffle_medium_addition` DECIMAL(8,2) NULL COMMENT '挡板添加介质量(kg)',
    `cao_amount` DECIMAL(8,2) NULL COMMENT 'CaO量(kg)',
    `flocculant_amount` DECIMAL(8,2) NULL COMMENT '絮凝剂(kg)',
    `density_317` DECIMAL(5,2) NULL COMMENT '317密度(kg/L)',

    -- 第一次灰分检测内容
    `first_ash_block_clean` DECIMAL(5,2) NULL COMMENT '第一次块精煤灰分(%)',
    `first_ash_fine_clean` DECIMAL(5,2) NULL COMMENT '第一次末精煤灰分(%)',
    `first_ash_middling` DECIMAL(5,2) NULL COMMENT '第一次中煤灰分(%)',
    `first_ash_slime` DECIMAL(5,2) NULL COMMENT '第一次煤泥灰分(%)',
    `first_ash_gangue` DECIMAL(5,2) NULL COMMENT '第一次矸石灰分(%)',

    -- 第二次灰分检测内容
    `second_ash_block_clean` DECIMAL(5,2) NULL COMMENT '第二次块精煤灰分(%)',
    `second_ash_fine_clean` DECIMAL(5,2) NULL COMMENT '第二次末精煤灰分(%)',
    `second_ash_middling` DECIMAL(5,2) NULL COMMENT '第二次中煤灰分(%)',
    `second_ash_slime` DECIMAL(5,2) NULL COMMENT '第二次煤泥灰分(%)',
    `second_ash_gangue` DECIMAL(5,2) NULL COMMENT '第二次矸石灰分(%)',

    -- 影响时间记录
    `impact_time_record` TEXT NULL COMMENT '影响时间记录详情',

    -- 交办事项
    `assigned_tasks` TEXT NULL COMMENT '交办事项',

    -- 启车时的仓位和液位
    `start_circulating_water_pool` DECIMAL(6,2) NULL COMMENT '启车循环水池液位',
    `start_clean_water_tank` DECIMAL(6,2) NULL COMMENT '启车清水桶液位',
    `start_fine_coal_level` DECIMAL(6,2) NULL COMMENT '启车末煤仓位',
    `start_granular_coal_level` DECIMAL(6,2) NULL COMMENT '启车粒煤仓位',
    `start_large_block_level` DECIMAL(6,2) NULL COMMENT '启车大块仓位',
    `start_medium_block_level` DECIMAL(6,2) NULL COMMENT '启车中块仓位',
    `start_small_block_level` DECIMAL(6,2) NULL COMMENT '启车小块仓位',
    `start_middling_coal_level` DECIMAL(6,2) NULL COMMENT '启车中煤仓位',
    `start_gangue_level` DECIMAL(6,2) NULL COMMENT '启车矸石仓位',

    -- 停车时的仓位和液位
    `stop_circulating_water_pool` DECIMAL(6,2) NULL COMMENT '停车循环水池液位',
    `stop_clean_water_tank` DECIMAL(6,2) NULL COMMENT '停车清水桶液位',
    `stop_fine_coal_level` DECIMAL(6,2) NULL COMMENT '停车末煤仓位',
    `stop_granular_coal_level` DECIMAL(6,2) NULL COMMENT '停车粒煤仓位',
    `stop_large_block_level` DECIMAL(6,2) NULL COMMENT '停车大块仓位',
    `stop_medium_block_level` DECIMAL(6,2) NULL COMMENT '停车中块仓位',
    `stop_small_block_level` DECIMAL(6,2) NULL COMMENT '停车小块仓位',
    `stop_middling_coal_level` DECIMAL(6,2) NULL COMMENT '停车中煤仓位',
    `stop_gangue_level` DECIMAL(6,2) NULL COMMENT '停车矸石仓位',

    -- 备注
    `remarks` TEXT NULL COMMENT '备注信息',

    -- 预留字段（用于后续扩展新字段）
    `reserved_field1` VARCHAR(200) NULL COMMENT '预留字段1',
    `reserved_field2` VARCHAR(200) NULL COMMENT '预留字段2',
    `reserved_field3` DECIMAL(10,2) NULL COMMENT '预留字段3',
    `reserved_field4` DECIMAL(10,2) NULL COMMENT '预留字段4',
    `reserved_field5` TEXT NULL COMMENT '预留字段5',

    -- 基础字段（芋道框架标准）
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_report_date` (`report_date`) USING BTREE,
    INDEX `idx_shift_id` (`shift_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '现场生产日报表';

-- ----------------------------
-- 3. 班次配置表
-- ----------------------------
DROP TABLE IF EXISTS `coal_shift_config`;
CREATE TABLE `coal_shift_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '班次ID',
    `name` VARCHAR(50) NOT NULL COMMENT '班次名称',
    `code` VARCHAR(20) NOT NULL COMMENT '班次编码',
    `start_time` TIME NOT NULL COMMENT '开始时间',
    `end_time` TIME NOT NULL COMMENT '结束时间',
    `shift_type` TINYINT NOT NULL COMMENT '班次类型：1生产班 2检修班 3值班',
    `is_production` BIT(1) NOT NULL DEFAULT b'1' COMMENT '是否生产班',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
    
    -- 基础字段（芋道框架标准）
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_code` (`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '班次配置表';

-- ----------------------------
-- 初始化班次数据
-- ----------------------------
INSERT INTO `coal_shift_config` (`name`, `code`, `start_time`, `end_time`, `shift_type`, `is_production`, `sort`, `status`, `creator`) VALUES
('早班', 'MORNING', '08:00:00', '16:00:00', 1, b'1', 1, 1, 'system'),
('中班', 'AFTERNOON', '16:00:00', '00:00:00', 1, b'1', 2, 1, 'system'),
('夜班', 'NIGHT', '00:00:00', '08:00:00', 1, b'1', 3, 1, 'system'),
('检修班', 'MAINTENANCE', '08:00:00', '17:00:00', 2, b'0', 4, 1, 'system'),
('值班', 'DUTY', '08:00:00', '08:00:00', 3, b'0', 5, 1, 'system');

-- ----------------------------
-- 4. 班制与班次设置表（树表结构）
-- ----------------------------
DROP TABLE IF EXISTS `coal_shift_system`;
CREATE TABLE `coal_shift_system` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父ID (0=班制, >0=班次)',
    `name` VARCHAR(50) NOT NULL COMMENT '名称 (班制或班次名)',
    `code` VARCHAR(20) NULL COMMENT '编码 (班次编码)',
    `is_production` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否生产班制 (仅班制层级有效)',
    `start_time` TIME NULL COMMENT '开始时间 (仅班次层级有效) - 格式：HH:mm:ss',
    `end_time` TIME NULL COMMENT '结束时间 (仅班次层级有效) - 格式：HH:mm:ss',
    `shift_type` TINYINT NULL COMMENT '班次类型：1生产班 2检修班 3值班 (仅班次层级有效)',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0禁用 1启用',
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_parent_id` (`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '班制与班次设置 (树表)';

-- ----------------------------
-- 5. 排班管理表（主表）
-- ----------------------------
DROP TABLE IF EXISTS `coal_schedule`;
CREATE TABLE `coal_schedule` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '排班ID',
    `schedule_date` DATE NOT NULL COMMENT '排班日期',
    `shift_system_id` BIGINT NOT NULL COMMENT '班制ID (关联coal_shift_system)',
    `schedule_status` TINYINT NOT NULL DEFAULT 1 COMMENT '排班状态：1正常 2调整 3取消',
    `is_production_day` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否生产日：1是 0否',
    `production_target` DECIMAL(10,2) NULL COMMENT '当日生产目标(吨)',
    `schedule_type` TINYINT NOT NULL DEFAULT 1 COMMENT '排班类型：1正常排班 2节假日排班 3应急排班',
    `approver_id` BIGINT NULL COMMENT '审批人ID',
    `approve_time` DATETIME NULL COMMENT '审批时间',
    `remark` VARCHAR(500) NULL COMMENT '备注',
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_date` (`schedule_date`, `deleted`) USING BTREE,
    INDEX `idx_shift_system` (`shift_system_id`) USING BTREE,
    INDEX `idx_schedule_date` (`schedule_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '排班管理 (主表)';

-- ----------------------------
-- 6. 人员安排表（子表）
-- ----------------------------
DROP TABLE IF EXISTS `coal_schedule_staff`;
CREATE TABLE `coal_schedule_staff` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '人员安排ID',
    `schedule_id` BIGINT NOT NULL COMMENT '排班ID (关联主表)',
    `shift_id` BIGINT NOT NULL COMMENT '班次ID (关联coal_shift_system的子节点)',
    `user_id` BIGINT NOT NULL COMMENT '员工ID',
    `position_type` TINYINT NOT NULL COMMENT '岗位类型：1调度员 2操作工 3检修工 4安全员 5质检员',
    `is_leader` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否班组长',
    `is_substitute` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否替班：1是 0否',
    `substitute_reason` VARCHAR(200) NULL COMMENT '替班原因',
    `work_status` TINYINT NOT NULL DEFAULT 1 COMMENT '工作状态：1正常 2请假 3调休 4替班',
    `contact_phone` VARCHAR(20) NULL COMMENT '联系电话',
    `emergency_contact` VARCHAR(50) NULL COMMENT '紧急联系人',
    `emergency_phone` VARCHAR(20) NULL COMMENT '紧急联系电话',
    `creator` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` BIGINT NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_schedule` (`schedule_id`) USING BTREE,
    INDEX `idx_shift` (`shift_id`) USING BTREE,
    INDEX `idx_user` (`user_id`) USING BTREE,
    INDEX `idx_position` (`position_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '人员安排 (子表)';

-- ----------------------------
-- 初始化班制与班次数据
-- ----------------------------
-- 1. 三班两倒班制
INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES (0, '三班两倒', 'THREE_SHIFT', 1, NULL, NULL, NULL, 1, 1, 'system', NOW(), 'system', NOW(), 0, 1);

SET @three_shift_id = LAST_INSERT_ID();

INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES 
(@three_shift_id, '早班', 'MORNING', 0, '08:00:00', '16:00:00', 1, 1, 1, 'system', NOW(), 'system', NOW(), 0, 1),
(@three_shift_id, '中班', 'AFTERNOON', 0, '16:00:00', '00:00:00', 1, 2, 1, 'system', NOW(), 'system', NOW(), 0, 1),
(@three_shift_id, '夜班', 'NIGHT', 0, '00:00:00', '08:00:00', 1, 3, 1, 'system', NOW(), 'system', NOW(), 0, 1);

-- 2. 四班三倒班制
INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES (0, '四班三倒', 'FOUR_SHIFT', 1, NULL, NULL, NULL, 2, 1, 'system', NOW(), 'system', NOW(), 0, 1);

SET @four_shift_id = LAST_INSERT_ID();

INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES 
(@four_shift_id, '甲班', 'SHIFT_A', 0, '08:00:00', '16:00:00', 1, 1, 1, 'system', NOW(), 'system', NOW(), 0, 1),
(@four_shift_id, '乙班', 'SHIFT_B', 0, '16:00:00', '00:00:00', 1, 2, 1, 'system', NOW(), 'system', NOW(), 0, 1),
(@four_shift_id, '丙班', 'SHIFT_C', 0, '00:00:00', '08:00:00', 1, 3, 1, 'system', NOW(), 'system', NOW(), 0, 1),
(@four_shift_id, '丁班', 'SHIFT_D', 0, '08:00:00', '16:00:00', 1, 4, 1, 'system', NOW(), 'system', NOW(), 0, 1);

-- 3. 两班倒班制
INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES (0, '两班倒', 'TWO_SHIFT', 1, NULL, NULL, NULL, 3, 1, 'system', NOW(), 'system', NOW(), 0, 1);

SET @two_shift_id = LAST_INSERT_ID();

INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES 
(@two_shift_id, '白班', 'DAY_SHIFT', 0, '08:00:00', '20:00:00', 1, 1, 1, 'system', NOW(), 'system', NOW(), 0, 1),
(@two_shift_id, '夜班', 'NIGHT_SHIFT', 0, '20:00:00', '08:00:00', 1, 2, 1, 'system', NOW(), 'system', NOW(), 0, 1);

-- 4. 长白班班制
INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES (0, '长白班', 'DAY_WORK', 1, NULL, NULL, NULL, 4, 1, 'system', NOW(), 'system', NOW(), 0, 1);

SET @day_work_id = LAST_INSERT_ID();

INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES 
(@day_work_id, '白班', 'DAY_SHIFT', 0, '08:00:00', '17:00:00', 1, 1, 1, 'system', NOW(), 'system', NOW(), 0, 1);

-- 5. 检修班制（非生产班）
INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES (0, '检修班制', 'MAINTENANCE', 0, NULL, NULL, NULL, 5, 1, 'system', NOW(), 'system', NOW(), 0, 1);

SET @maintenance_id = LAST_INSERT_ID();

INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES 
(@maintenance_id, '检修班', 'MAINTENANCE_SHIFT', 0, '08:00:00', '17:00:00', 2, 1, 1, 'system', NOW(), 'system', NOW(), 0, 1);

-- 6. 值班班制（非生产班）
INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES (0, '值班班制', 'DUTY', 0, NULL, NULL, NULL, 6, 1, 'system', NOW(), 'system', NOW(), 0, 1);

SET @duty_id = LAST_INSERT_ID();

INSERT INTO coal_shift_system (parent_id, name, code, is_production, start_time, end_time, shift_type, sort, status, creator, create_time, updater, update_time, deleted, tenant_id) 
VALUES 
(@duty_id, '值班', 'DUTY_SHIFT', 0, '08:00:00', '08:00:00', 3, 1, 1, 'system', NOW(), 'system', NOW(), 0, 1);

-- ----------------------------
-- 字典数据配置
-- ----------------------------

-- 班次类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('班次类型', 'shift_type', 0, '班次类型', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '生产班', '1', 'shift_type', 0, '1', NOW(), '1', NOW()),
(2, '检修班', '2', 'shift_type', 0, '1', NOW(), '1', NOW()),
(3, '值班', '3', 'shift_type', 0, '1', NOW(), '1', NOW());

-- 是否生产班制字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('是否生产班制', 'is_production', 0, '是否生产班制', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '否', '0', 'is_production', 0, '1', NOW(), '1', NOW()),
(2, '是', '1', 'is_production', 0, '1', NOW(), '1', NOW());

-- 岗位类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('岗位类型', 'position_type', 0, '排班岗位类型', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '调度员', '1', 'position_type', 0, '1', NOW(), '1', NOW()),
(2, '操作工', '2', 'position_type', 0, '1', NOW(), '1', NOW()),
(3, '检修工', '3', 'position_type', 0, '1', NOW(), '1', NOW()),
(4, '安全员', '4', 'position_type', 0, '1', NOW(), '1', NOW()),
(5, '质检员', '5', 'position_type', 0, '1', NOW(), '1', NOW());

-- 排班状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('排班状态', 'schedule_status', 0, '排班状态', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '正常', '1', 'schedule_status', 0, '1', NOW(), '1', NOW()),
(2, '调整', '2', 'schedule_status', 0, '1', NOW(), '1', NOW()),
(3, '取消', '3', 'schedule_status', 0, '1', NOW(), '1', NOW());

-- 排班类型字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('排班类型', 'schedule_type', 0, '排班类型', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '正常排班', '1', 'schedule_type', 0, '1', NOW(), '1', NOW()),
(2, '节假日排班', '2', 'schedule_type', 0, '1', NOW(), '1', NOW()),
(3, '应急排班', '3', 'schedule_type', 0, '1', NOW(), '1', NOW());

-- 工作状态字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('工作状态', 'work_status', 0, '员工工作状态', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '正常', '1', 'work_status', 0, '1', NOW(), '1', NOW()),
(2, '请假', '2', 'work_status', 0, '1', NOW(), '1', NOW()),
(3, '调休', '3', 'work_status', 0, '1', NOW(), '1', NOW()),
(4, '替班', '4', 'work_status', 0, '1', NOW(), '1', NOW());

-- 是否生产日字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('是否生产日', 'is_production_day', 0, '是否生产日', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '否', '0', 'is_production_day', 0, '1', NOW(), '1', NOW()),
(2, '是', '1', 'is_production_day', 0, '1', NOW(), '1', NOW());

-- 是否班组长字典
INSERT INTO `system_dict_type` (`name`, `type`, `status`, `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES 
('是否班组长', 'is_leader', 0, '是否班组长', '1', NOW(), '1', NOW(), 0);

INSERT INTO `system_dict_data` (`sort`, `label`, `value`, `dict_type`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '否', '0', 'is_leader', 0, '1', NOW(), '1', NOW()),
(2, '是', '1', 'is_leader', 0, '1', NOW(), '1', NOW());
