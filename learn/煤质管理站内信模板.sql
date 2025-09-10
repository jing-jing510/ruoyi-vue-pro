-- 煤质管理模块站内信模板SQL
-- 基于芋道开源框架的站内信模板表结构（严格按照实际表结构）

-- 1. 质量预警通知模板
INSERT INTO `system_notify_template` (
    `id`, 
    `name`, 
    `code`, 
    `nickname`, 
    `content`, 
    `type`, 
    `params`, 
    `status`, 
    `remark`, 
    `creator`, 
    `create_time`, 
    `updater`, 
    `update_time`, 
    `deleted`
) VALUES (
    1002,
    '质量预警通知',
    'coal_quality_alert',
    '煤质管理系统',
    '【质量预警通知】

预警编号：{alertCode}
检测项目：{itemName}
产品类型：{productType}
检测值：{measuredValue}{unit}
标准值：{standardValue}{unit}
偏差值：{deviation}{unit}
预警信息：{alertMessage}
预警级别：{alertLevel}

请及时处理该预警信息！

发送时间：{sendTime}',
    3,
    '["alertCode", "itemName", "productType", "measuredValue", "unit", "standardValue", "deviation", "alertMessage", "alertLevel", "sendTime"]',
    0,
    '煤质管理质量预警通知模板',
    1,
    NOW(),
    1,
    NOW(),
    0
);

-- 2. 检测数据异常通知模板
INSERT INTO `system_notify_template` (
    `id`, 
    `name`, 
    `code`, 
    `nickname`, 
    `content`, 
    `type`, 
    `params`, 
    `status`, 
    `remark`, 
    `creator`, 
    `create_time`, 
    `updater`, 
    `update_time`, 
    `deleted`
) VALUES (
    1003,
    '检测数据异常通知',
    'coal_quality_data_abnormal',
    '煤质管理系统',
    '【检测数据异常通知】

检测记录：{inspectionCode}
检测项目：{itemName}
产品类型：{productType}
检测值：{measuredValue}{unit}
标准值：{standardValue}{unit}
偏差率：{deviationRate}%
检测结果：{isQualified}
检测时间：{detectionTime}

请及时处理该异常数据！

发送时间：{sendTime}',
    3,
    '["inspectionCode", "itemName", "productType", "measuredValue", "unit", "standardValue", "deviationRate", "isQualified", "detectionTime", "sendTime"]',
    0,
    '煤质管理检测数据异常通知模板',
    1,
    NOW(),
    1,
    NOW(),
    0
);

-- 3. 质量标准变更通知模板
INSERT INTO `system_notify_template` (
    `id`, 
    `name`, 
    `code`, 
    `nickname`, 
    `content`, 
    `type`, 
    `params`, 
    `status`, 
    `remark`, 
    `creator`, 
    `create_time`, 
    `updater`, 
    `update_time`, 
    `deleted`
) VALUES (
    1004,
    '质量标准变更通知',
    'coal_quality_standard_change',
    '煤质管理系统',
    '【质量标准变更通知】

标准名称：{standardName}
标准编码：{standardCode}
产品类型：{productType}
检测项目：{itemName}
新标准值：{standardValue}{unit}
生效日期：{effectiveDate}
版本号：{version}

请及时了解并执行新标准！

发送时间：{sendTime}',
    3,
    '["standardName", "standardCode", "productType", "itemName", "standardValue", "unit", "effectiveDate", "version", "sendTime"]',
    0,
    '煤质管理质量标准变更通知模板',
    1,
    NOW(),
    1,
    NOW(),
    0
);

-- 4. 检测任务提醒通知模板
INSERT INTO `system_notify_template` (
    `id`, 
    `name`, 
    `code`, 
    `nickname`, 
    `content`, 
    `type`, 
    `params`, 
    `status`, 
    `remark`, 
    `creator`, 
    `create_time`, 
    `updater`, 
    `update_time`, 
    `deleted`
) VALUES (
    1005,
    '检测任务提醒通知',
    'coal_quality_inspection_reminder',
    '煤质管理系统',
    '【检测任务提醒通知】

检测编号：{inspectionCode}
产品类型：{productType}
产品规格：{productSpecification}
检测项目：{itemName}
计划检测时间：{plannedTime}
采样地点：{samplingLocation}
检测人员：{inspectorName}

请及时完成检测任务！

发送时间：{sendTime}',
    3,
    '["inspectionCode", "productType", "productSpecification", "itemName", "plannedTime", "samplingLocation", "inspectorName", "sendTime"]',
    0,
    '煤质管理检测任务提醒通知模板',
    1,
    NOW(),
    1,
    NOW(),
    0
);

-- 5. 预警处理完成通知模板
INSERT INTO `system_notify_template` (
    `id`, 
    `name`, 
    `code`, 
    `nickname`, 
    `content`, 
    `type`, 
    `params`, 
    `status`, 
    `remark`, 
    `creator`, 
    `create_time`, 
    `updater`, 
    `update_time`, 
    `deleted`
) VALUES (
    1006,
    '预警处理完成通知',
    'coal_quality_alert_handled',
    '煤质管理系统',
    '【预警处理完成通知】

预警编号：{alertCode}
检测项目：{itemName}
产品类型：{productType}
处理人：{handlerName}
处理措施：{handleMethod}
处理结果：{handleResult}
处理时间：{handleTime}

预警已处理完成！

发送时间：{sendTime}',
    3,
    '["alertCode", "itemName", "productType", "handlerName", "handleMethod", "handleResult", "handleTime", "sendTime"]',
    0,
    '煤质管理预警处理完成通知模板',
    1,
    NOW(),
    1,
    NOW(),
    0
);
