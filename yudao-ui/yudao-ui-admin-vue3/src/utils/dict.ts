/**
 * 数据字典工具类
 */
import { useDictStoreWithOut } from '@/store/modules/dict'
import { ElementPlusInfoType } from '@/types/elementPlus'

const dictStore = useDictStoreWithOut()

/**
 * 获取 dictType 对应的数据字典数组
 *
 * @param dictType 数据类型
 * @returns {*|Array} 数据字典数组
 */
export interface DictDataType {
  dictType: string
  label: string
  value: string | number | boolean
  colorType: ElementPlusInfoType | ''
  cssClass: string
}

export interface NumberDictDataType extends DictDataType {
  value: number
}

export interface StringDictDataType extends DictDataType {
  value: string
}

export const getDictOptions = (dictType: string) => {
  return dictStore.getDictByType(dictType) || []
}

export const getIntDictOptions = (dictType: string): NumberDictDataType[] => {
  // 获得通用的 DictDataType 列表
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  // 转换成 number 类型的 NumberDictDataType 类型
  // why 需要特殊转换：避免 IDEA 在 v-for="dict in getIntDictOptions(...)" 时，el-option 的 key 会告警
  const dictOption: NumberDictDataType[] = []
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: parseInt(dict.value + '')
    })
  })
  return dictOption
}

export const getStrDictOptions = (dictType: string) => {
  // 获得通用的 DictDataType 列表
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  // 转换成 string 类型的 StringDictDataType 类型
  // why 需要特殊转换：避免 IDEA 在 v-for="dict in getStrDictOptions(...)" 时，el-option 的 key 会告警
  const dictOption: StringDictDataType[] = []
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: dict.value + ''
    })
  })
  return dictOption
}

export const getBoolDictOptions = (dictType: string) => {
  const dictOption: DictDataType[] = []
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: dict.value + '' === 'true'
    })
  })
  return dictOption
}

/**
 * 获取指定字典类型的指定值对应的字典对象
 * @param dictType 字典类型
 * @param value 字典值
 * @return DictDataType 字典对象
 */
export const getDictObj = (dictType: string, value: any): DictDataType | undefined => {
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  for (const dict of dictOptions) {
    if (dict.value === value + '') {
      return dict
    }
  }
}

/**
 * 获得字典数据的文本展示
 *
 * @param dictType 字典类型
 * @param value 字典数据的值
 * @return 字典名称
 */
export const getDictLabel = (dictType: string, value: any): string => {
  const dictOptions: DictDataType[] = getDictOptions(dictType)
  const dictLabel = ref('')
  dictOptions.forEach((dict: DictDataType) => {
    if (dict.value === value + '') {
      dictLabel.value = dict.label
    }
  })
  return dictLabel.value
}

export enum DICT_TYPE {
  USER_TYPE = 'user_type',
  COMMON_STATUS = 'common_status',
  TERMINAL = 'terminal', // 终端
  DATE_INTERVAL = 'date_interval', // 数据间隔

  // ========== SYSTEM 模块 ==========
  SYSTEM_USER_SEX = 'system_user_sex',
  SYSTEM_MENU_TYPE = 'system_menu_type',
  SYSTEM_ROLE_TYPE = 'system_role_type',
  SYSTEM_DATA_SCOPE = 'system_data_scope',
  SYSTEM_NOTICE_TYPE = 'system_notice_type',
  SYSTEM_LOGIN_TYPE = 'system_login_type',
  SYSTEM_LOGIN_RESULT = 'system_login_result',
  SYSTEM_SMS_CHANNEL_CODE = 'system_sms_channel_code',
  SYSTEM_SMS_TEMPLATE_TYPE = 'system_sms_template_type',
  SYSTEM_SMS_SEND_STATUS = 'system_sms_send_status',
  SYSTEM_SMS_RECEIVE_STATUS = 'system_sms_receive_status',
  SYSTEM_OAUTH2_GRANT_TYPE = 'system_oauth2_grant_type',
  SYSTEM_MAIL_SEND_STATUS = 'system_mail_send_status',
  SYSTEM_NOTIFY_TEMPLATE_TYPE = 'system_notify_template_type',
  SYSTEM_SOCIAL_TYPE = 'system_social_type',

  // ========== INFRA 模块 ==========
  INFRA_BOOLEAN_STRING = 'infra_boolean_string',
  INFRA_JOB_STATUS = 'infra_job_status',
  INFRA_JOB_LOG_STATUS = 'infra_job_log_status',
  INFRA_API_ERROR_LOG_PROCESS_STATUS = 'infra_api_error_log_process_status',
  INFRA_CONFIG_TYPE = 'infra_config_type',
  INFRA_CODEGEN_TEMPLATE_TYPE = 'infra_codegen_template_type',
  INFRA_CODEGEN_FRONT_TYPE = 'infra_codegen_front_type',
  INFRA_CODEGEN_SCENE = 'infra_codegen_scene',
  INFRA_FILE_STORAGE = 'infra_file_storage',
  INFRA_OPERATE_TYPE = 'infra_operate_type',

  // ========== BPM 模块 ==========
  BPM_MODEL_TYPE = 'bpm_model_type',
  BPM_MODEL_FORM_TYPE = 'bpm_model_form_type',
  BPM_TASK_CANDIDATE_STRATEGY = 'bpm_task_candidate_strategy',
  BPM_PROCESS_INSTANCE_STATUS = 'bpm_process_instance_status',
  BPM_TASK_STATUS = 'bpm_task_status',
  BPM_OA_LEAVE_TYPE = 'bpm_oa_leave_type',
  BPM_PROCESS_LISTENER_TYPE = 'bpm_process_listener_type',
  BPM_PROCESS_LISTENER_VALUE_TYPE = 'bpm_process_listener_value_type',

  // ========== PAY 模块 ==========
  PAY_CHANNEL_CODE = 'pay_channel_code', // 支付渠道编码类型
  PAY_ORDER_STATUS = 'pay_order_status', // 商户支付订单状态
  PAY_REFUND_STATUS = 'pay_refund_status', // 退款订单状态
  PAY_NOTIFY_STATUS = 'pay_notify_status', // 商户支付回调状态
  PAY_NOTIFY_TYPE = 'pay_notify_type', // 商户支付回调状态
  PAY_TRANSFER_STATUS = 'pay_transfer_status', // 转账订单状态

  // ========== MP 模块 ==========
  MP_AUTO_REPLY_REQUEST_MATCH = 'mp_auto_reply_request_match', // 自动回复请求匹配类型
  MP_MESSAGE_TYPE = 'mp_message_type', // 消息类型

  // ========== Member 会员模块 ==========
  MEMBER_POINT_BIZ_TYPE = 'member_point_biz_type', // 积分的业务类型
  MEMBER_EXPERIENCE_BIZ_TYPE = 'member_experience_biz_type', // 会员经验业务类型

  // ========== MALL - 商品模块 ==========
  PRODUCT_SPU_STATUS = 'product_spu_status', //商品状态

  // ========== MALL - 交易模块 ==========
  EXPRESS_CHARGE_MODE = 'trade_delivery_express_charge_mode', //快递的计费方式
  TRADE_AFTER_SALE_STATUS = 'trade_after_sale_status', // 售后 - 状态
  TRADE_AFTER_SALE_WAY = 'trade_after_sale_way', // 售后 - 方式
  TRADE_AFTER_SALE_TYPE = 'trade_after_sale_type', // 售后 - 类型
  TRADE_ORDER_TYPE = 'trade_order_type', // 订单 - 类型
  TRADE_ORDER_STATUS = 'trade_order_status', // 订单 - 状态
  TRADE_ORDER_ITEM_AFTER_SALE_STATUS = 'trade_order_item_after_sale_status', // 订单项 - 售后状态
  TRADE_DELIVERY_TYPE = 'trade_delivery_type', // 配送方式
  BROKERAGE_ENABLED_CONDITION = 'brokerage_enabled_condition', // 分佣模式
  BROKERAGE_BIND_MODE = 'brokerage_bind_mode', // 分销关系绑定模式
  BROKERAGE_BANK_NAME = 'brokerage_bank_name', // 佣金提现银行
  BROKERAGE_WITHDRAW_TYPE = 'brokerage_withdraw_type', // 佣金提现类型
  BROKERAGE_RECORD_BIZ_TYPE = 'brokerage_record_biz_type', // 佣金业务类型
  BROKERAGE_RECORD_STATUS = 'brokerage_record_status', // 佣金状态
  BROKERAGE_WITHDRAW_STATUS = 'brokerage_withdraw_status', // 佣金提现状态

  // ========== MALL - 营销模块 ==========
  PROMOTION_DISCOUNT_TYPE = 'promotion_discount_type', // 优惠类型
  PROMOTION_PRODUCT_SCOPE = 'promotion_product_scope', // 营销的商品范围
  PROMOTION_COUPON_TEMPLATE_VALIDITY_TYPE = 'promotion_coupon_template_validity_type', // 优惠劵模板的有限期类型
  PROMOTION_COUPON_STATUS = 'promotion_coupon_status', // 优惠劵的状态
  PROMOTION_COUPON_TAKE_TYPE = 'promotion_coupon_take_type', // 优惠劵的领取方式
  PROMOTION_CONDITION_TYPE = 'promotion_condition_type', // 营销的条件类型枚举
  PROMOTION_BARGAIN_RECORD_STATUS = 'promotion_bargain_record_status', // 砍价记录的状态
  PROMOTION_COMBINATION_RECORD_STATUS = 'promotion_combination_record_status', // 拼团记录的状态
  PROMOTION_BANNER_POSITION = 'promotion_banner_position', // banner 定位

  // ========== CRM - 客户管理模块 ==========
  CRM_AUDIT_STATUS = 'crm_audit_status', // CRM 审批状态
  CRM_BIZ_TYPE = 'crm_biz_type', // CRM 业务类型
  CRM_BUSINESS_END_STATUS_TYPE = 'crm_business_end_status_type', // CRM 商机结束状态类型
  CRM_RECEIVABLE_RETURN_TYPE = 'crm_receivable_return_type', // CRM 回款的还款方式
  CRM_CUSTOMER_INDUSTRY = 'crm_customer_industry', // CRM 客户所属行业
  CRM_CUSTOMER_LEVEL = 'crm_customer_level', // CRM 客户级别
  CRM_CUSTOMER_SOURCE = 'crm_customer_source', // CRM 客户来源
  CRM_PRODUCT_STATUS = 'crm_product_status', // CRM 商品状态
  CRM_PERMISSION_LEVEL = 'crm_permission_level', // CRM 数据权限的级别
  CRM_PRODUCT_UNIT = 'crm_product_unit', // CRM 产品单位
  CRM_FOLLOW_UP_TYPE = 'crm_follow_up_type', // CRM 跟进方式

  // ========== ERP - 企业资源计划模块  ==========
  ERP_AUDIT_STATUS = 'erp_audit_status', // ERP 审批状态
  ERP_STOCK_RECORD_BIZ_TYPE = 'erp_stock_record_biz_type', // 库存明细的业务类型
  
  // ========== SPARE_PART - 备件管理模块 ==========
  SPARE_PART_TYPE = 'spare_part_type', // 备件类型
  SPARE_PART_CATEGORY_LEVEL = 'spare_part_category_level', // 备件分类层级
  SPARE_PART_STATUS = 'spare_part_status', // 备件状态
  IS_CRITICAL_SPARE_PART = 'is_critical_spare_part', // 是否关键备件
  USAGE_FREQUENCY = 'usage_frequency', // 使用频率
  STOCK_TYPE = 'stock_type', // 库存类型
  OPERATION_TYPE = 'operation_type', // 出入库操作类型
  ALERT_TYPE = 'alert_type', // 预警类型
  ALERT_LEVEL = 'alert_level', // 预警级别
  ALERT_STATUS = 'alert_status', // 预警状态
  IS_SENT = 'is_sent', // 是否已发送
  USAGE_TYPE = 'usage_type', // 使用类型
  REPLACEMENT_DIFFICULTY = 'replacement_difficulty', // 更换难度
  OLD_PART_CONDITION = 'old_part_condition', // 旧件状态
  PERFORMANCE_RATING = 'performance_rating', // 性能评级
  APPROVE_STATUS = 'approve_status', // 审批状态
  IS_REQUIRED = 'is_required', // 是否必需

  // ========== AI - 人工智能模块  ==========
  AI_PLATFORM = 'ai_platform', // AI 平台
  AI_MODEL_TYPE = 'ai_model_type', // AI 模型类型
  AI_IMAGE_STATUS = 'ai_image_status', // AI 图片状态
  AI_MUSIC_STATUS = 'ai_music_status', // AI 音乐状态
  AI_GENERATE_MODE = 'ai_generate_mode', // AI 生成模式
  AI_WRITE_TYPE = 'ai_write_type', // AI 写作类型
  AI_WRITE_LENGTH = 'ai_write_length', // AI 写作长度
  AI_WRITE_FORMAT = 'ai_write_format', // AI 写作格式
  AI_WRITE_TONE = 'ai_write_tone', // AI 写作语气
  AI_WRITE_LANGUAGE = 'ai_write_language', // AI 写作语言

  // ========== IOT - 物联网模块  ==========
  IOT_NET_TYPE = 'iot_net_type', // IOT 联网方式
  IOT_VALIDATE_TYPE = 'iot_validate_type', // IOT 数据校验级别
  IOT_PRODUCT_STATUS = 'iot_product_status', // IOT 产品状态
  IOT_PRODUCT_DEVICE_TYPE = 'iot_product_device_type', // IOT 产品设备类型
  IOT_DATA_FORMAT = 'iot_data_format', // IOT 数据格式
  IOT_PROTOCOL_TYPE = 'iot_protocol_type', // IOT 接入网关协议
  IOT_DEVICE_STATE = 'iot_device_state', // IOT 设备状态
  IOT_THING_MODEL_TYPE = 'iot_thing_model_type', // IOT 产品功能类型
  IOT_DATA_TYPE = 'iot_data_type', // IOT 数据类型
  IOT_THING_MODEL_UNIT = 'iot_thing_model_unit', // IOT 物模型单位
  IOT_RW_TYPE = 'iot_rw_type', // IOT 读写类型
  IOT_PLUGIN_DEPLOY_TYPE = 'iot_plugin_deploy_type', // IOT 插件部署类型
  IOT_PLUGIN_STATUS = 'iot_plugin_status', // IOT 插件状态
  IOT_PLUGIN_TYPE = 'iot_plugin_type', // IOT 插件类型
  IOT_DATA_BRIDGE_DIRECTION_ENUM = 'iot_data_bridge_direction_enum', // 桥梁方向
  IOT_DATA_BRIDGE_TYPE_ENUM = 'iot_data_bridge_type_enum', // 桥梁类型

  // ========== IOT - 生产计划模块  ==========
  PLAN_STATUS= 'plan_status',
  PLAN_LEVEL = 'plan_level',
  PLAN_TYPE = 'plan_type',

  // ========== IOT - 排班模块  ==========
  IS_PRODUCTION = 'is_production',        // 是否生产班制
  IS_PRODUCTION_DAY = 'is_production_day', // 是否生产日
  IS_LEADER = 'is_leader',                // 是否班组长
  IS_SUBSTITUTE = 'is_substitute',        // 是否替班
  SHIFT_TYPE = 'shift_type',              // 班次类型
  POSITION_TYPE = 'position_type',        // 岗位类型
  SCHEDULE_STATUS = 'schedule_status',    // 排班状态
  SCHEDULE_TYPE = 'schedule_type',        // 排班类型
  WORK_STATUS = 'work_status',            // 工作状态

  // ========== COAL - 设备管理模块  ==========
  EQUIPMENT_STATUS = 'equipment_status',                    // 设备状态
  EQUIPMENT_HEALTH_LEVEL = 'equipment_health_level',        // 设备健康等级
  EQUIPMENT_IMPORTANCE_LEVEL = 'equipment_importance_level', // 设备重要度
  EQUIPMENT_CATEGORY_LEVEL = 'equipment_category_level',     // 设备分类层级

  // ========== COAL - 检修管理模块  ==========
  MAINTENANCE_PLAN_TYPE = 'maintenance_plan_type',           // 检修计划类型
  MAINTENANCE_PLAN_STATUS = 'maintenance_plan_status',       // 检修计划状态
  MAINTENANCE_TYPE = 'maintenance_type',                     // 检修类型
  MAINTENANCE_LEVEL = 'maintenance_level',                   // 检修级别
  MAINTENANCE_ORDER_STATUS = 'maintenance_order_status',     // 检修单状态
  MAINTENANCE_ITEM_TYPE = 'maintenance_item_type',           // 检修项目类型
  MAINTENANCE_ITEM_STATUS = 'maintenance_item_status',       // 检修项目状态
  COMPLETION_QUALITY = 'completion_quality',                 // 完成质量
  QUALITY_RATING = 'quality_rating',                         // 质量评级
  SAFETY_RATING = 'safety_rating',                           // 安全评级
  PRIORITY_LEVEL = 'priority_level',                         // 优先级
  FAULT_TYPE = 'fault_type',                                 // 故障类型
  FAULT_LEVEL = 'fault_level',                               // 故障级别
  URGENCY_LEVEL = 'urgency_level',                           // 紧急程度
  REPAIR_REQUEST_STATUS = 'repair_request_status',           // 报修单状态
  REPAIR_QUALITY = 'repair_quality',                         // 修复质量
  SATISFACTION_RATING = 'satisfaction_rating',               // 满意度评价

  // ========== COAL - 煤质管理模块  ==========
  COAL_QUALITY_ITEM_TYPE = 'coal_quality_item_type',                 // 煤质检测项目类型
  COAL_QUALITY_PRODUCT_TYPE = 'coal_quality_product_type',           // 煤质产品类型
  COAL_QUALITY_PRODUCT_SPEC = 'coal_quality_product_spec',           // 煤质产品规格
  COAL_QUALITY_SAMPLING_METHOD = 'coal_quality_sampling_method',     // 煤质采样方法
  COAL_QUALITY_DETECTION_TYPE = 'coal_quality_detection_type',       // 煤质检测类型
  COAL_QUALITY_INSPECTION_STATUS = 'coal_quality_inspection_status', // 煤质检测状态
  COAL_QUALITY_REVIEW_STATUS = 'coal_quality_review_status',         // 煤质审核状态
  COAL_QUALITY_QUALIFIED = 'coal_quality_qualified',                 // 煤质合格状态
  COAL_QUALITY_RECHECK = 'coal_quality_recheck',                     // 煤质是否复检
  COAL_QUALITY_STANDARD_TYPE = 'coal_quality_standard_type',         // 煤质标准类型
  COAL_QUALITY_STANDARD_STATUS = 'coal_quality_standard_status',     // 煤质标准状态
  COAL_QUALITY_ALERT_TYPE = 'coal_quality_alert_type',               // 煤质预警类型
  COAL_QUALITY_ALERT_LEVEL = 'coal_quality_alert_level',             // 煤质预警级别
  COAL_QUALITY_ALERT_STATUS = 'coal_quality_alert_status',           // 煤质预警状态
  COAL_QUALITY_AUTO_ALERT = 'coal_quality_auto_alert',               // 煤质是否自动预警
  COAL_QUALITY_NOTIFICATION_SENT = 'coal_quality_notification_sent', // 煤质是否已发送通知
  COAL_QUALITY_PERFORMANCE_RATING = 'coal_quality_performance_rating', // 煤质性能评级

  // ========== COAL - 安全管理模块  ==========
  COAL_SAFETY_CATEGORY_TYPE = 'coal_safety_category_type',             // 安全分类类型
  COAL_SAFETY_PLAN_TYPE = 'coal_safety_plan_type',                     // 安全计划类型
  COAL_SAFETY_CHECK_CYCLE = 'coal_safety_check_cycle',                 // 安全检查周期
  COAL_SAFETY_PLAN_STATUS = 'coal_safety_plan_status',                 // 安全计划状态
  COAL_SAFETY_APPROVAL_STATUS = 'coal_safety_approval_status',         // 安全审批状态
  COAL_SAFETY_CHECK_TYPE = 'coal_safety_check_type',                   // 安全检查类型
  COAL_SAFETY_CHECK_RESULT = 'coal_safety_check_result',               // 安全检查结果
  COAL_SAFETY_RISK_LEVEL = 'coal_safety_risk_level',                   // 安全风险等级
  COAL_SAFETY_RECTIFICATION_STATUS = 'coal_safety_rectification_status', // 安全整改状态
  COAL_SAFETY_RECORD_STATUS = 'coal_safety_record_status',             // 安全记录状态
  COAL_SAFETY_ACCIDENT_TYPE = 'coal_safety_accident_type',             // 安全事故类型
  COAL_SAFETY_ACCIDENT_LEVEL = 'coal_safety_accident_level',           // 安全事故等级
  COAL_SAFETY_ACCIDENT_STATUS = 'coal_safety_accident_status',         // 安全事故状态
  COAL_SAFETY_ATTACHMENT_TYPE = 'coal_safety_attachment_type',          // 安全附件类型
  COAL_SAFETY_ATTACHMENT_BUSINESS_TYPE = 'coal_safety_attachment_business_type', // 安全附件业务类型

  // ========== COAL - 能源管理模块  ==========
  COAL_ENERGY_TYPE = 'coal_energy_type',                               // 能源类型
  COAL_ENERGY_DATA_SOURCE = 'coal_energy_data_source',                 // 能源数据来源
  COAL_ENERGY_VERIFICATION_STATUS = 'coal_energy_verification_status', // 能源验证状态
  COAL_ENERGY_STATISTICS_TYPE = 'coal_energy_statistics_type',         // 能源统计类型
  COAL_ENERGY_STATISTICS_STATUS = 'coal_energy_statistics_status',     // 能源统计状态
  COAL_ENERGY_ALERT_TYPE = 'coal_energy_alert_type',                   // 能源预警类型
  COAL_ENERGY_ALERT_LEVEL = 'coal_energy_alert_level',                 // 能源预警级别
  COAL_ENERGY_ALERT_STATUS = 'coal_energy_alert_status',               // 能源预警状态
  COAL_ENERGY_IS_REAL_TIME = 'coal_energy_is_real_time'                // 是否实时采集

}
