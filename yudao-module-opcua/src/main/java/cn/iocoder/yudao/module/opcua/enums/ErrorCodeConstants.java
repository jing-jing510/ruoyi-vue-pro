package cn.iocoder.yudao.module.opcua.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * OPC UA 错误码枚举类
 * 
 * opcua 系统，使用 1-003-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== OPC UA 配置 1-003-001-000 ==========
    ErrorCode OPCUA_CONFIG_NOT_EXISTS = new ErrorCode(1_003_001_000, "OPC UA 配置不存在");
    ErrorCode OPCUA_CONFIG_NAME_DUPLICATE = new ErrorCode(1_003_001_001, "OPC UA 配置名称已存在");
    ErrorCode OPCUA_CONFIG_CONNECTION_FAILED = new ErrorCode(1_003_001_002, "OPC UA 连接失败");

    // ========== 报警事件 1-003-002-000 ==========
    ErrorCode ALARM_EVENT_NOT_EXISTS = new ErrorCode(1_003_002_000, "报警事件不存在");
    ErrorCode ALARM_EVENT_STATUS_ERROR = new ErrorCode(1_003_002_001, "报警事件状态错误");

    // ========== 设备管理 1-003-003-000 ==========
    ErrorCode DEVICE_NOT_EXISTS = new ErrorCode(1_003_003_000, "设备不存在");
    ErrorCode DEVICE_CODE_DUPLICATE = new ErrorCode(1_003_003_001, "设备编码已存在");

    // ========== 点位配置 1-003-004-000 ==========
    ErrorCode OPCUA_TAG_NOT_EXISTS = new ErrorCode(1_003_004_000, "点位配置不存在");
    ErrorCode OPCUA_TAG_NODE_ID_DUPLICATE = new ErrorCode(1_003_004_001, "点位 NodeId 已存在");

}
