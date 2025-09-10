package cn.iocoder.yudao.module.coal.controller.admin.qualityalert.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 质量预警记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class QualityAlertRespVO {

    @Schema(description = "预警记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6622")
    @ExcelProperty("预警记录ID")
    private Long id;

    @Schema(description = "预警编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预警编号")
    private String alertCode;

    @Schema(description = "预警类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "预警类型", converter = DictConvert.class)
    @DictFormat("coal_quality_alert_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer alertType;

    @Schema(description = "预警级别", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "预警级别", converter = DictConvert.class)
    @DictFormat("coal_quality_alert_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer alertLevel;

    @Schema(description = "检测项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28743")
    @ExcelProperty("检测项目ID")
    private Long qualityItemId;

    @Schema(description = "关联检测记录ID", example = "1430")
    @ExcelProperty("关联检测记录ID")
    private Long inspectionId;

    @Schema(description = "产品类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "产品类型", converter = DictConvert.class)
    @DictFormat("coal_quality_product_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer productType;

    @Schema(description = "检测值")
    @ExcelProperty("检测值")
    private BigDecimal measuredValue;

    @Schema(description = "标准值")
    @ExcelProperty("标准值")
    private BigDecimal standardValue;

    @Schema(description = "预警阈值")
    @ExcelProperty("预警阈值")
    private BigDecimal thresholdValue;

    @Schema(description = "偏差值")
    @ExcelProperty("偏差值")
    private BigDecimal deviation;

    @Schema(description = "预警信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预警信息")
    private String alertMessage;

    @Schema(description = "预警时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预警时间")
    private LocalDateTime alertTime;

    @Schema(description = "预警状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "预警状态", converter = DictConvert.class)
    @DictFormat("coal_quality_alert_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer alertStatus;

    @Schema(description = "处理人ID", example = "29538")
    @ExcelProperty("处理人ID")
    private Long handlerId;

    @Schema(description = "处理人姓名", example = "李四")
    @ExcelProperty("处理人姓名")
    private String handlerName;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "处理措施")
    @ExcelProperty("处理措施")
    private String handleMethod;

    @Schema(description = "处理结果")
    @ExcelProperty("处理结果")
    private String handleResult;

    @Schema(description = "是否自动预警：0手动 1自动", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否自动预警：0手动 1自动")
    private Integer isAutoAlert;

    @Schema(description = "是否已发送通知：0否 1是", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否已发送通知：0否 1是")
    private Integer notificationSent;

    @Schema(description = "通知发送时间")
    @ExcelProperty("通知发送时间")
    private LocalDateTime notificationTime;

    @Schema(description = "接收人列表(逗号分隔)")
    @ExcelProperty("接收人列表(逗号分隔)")
    private String recipients;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
