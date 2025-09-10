package cn.iocoder.yudao.module.coal.controller.admin.sparepartalert.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 备件预警记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SparePartAlertRespVO {

    @Schema(description = "预警ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18433")
    @ExcelProperty("预警ID")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12198")
    @ExcelProperty("备件ID")
    private Long sparePartId;

    @Schema(description = "预警类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "预警类型", converter = DictConvert.class)
    @DictFormat("alert_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer alertType;

    @Schema(description = "预警级别", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "预警级别", converter = DictConvert.class)
    @DictFormat("alert_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer alertLevel;

    @Schema(description = "预警状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "预警状态", converter = DictConvert.class)
    @DictFormat("alert_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer alertStatus;

    @Schema(description = "预警标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预警标题")
    private String alertTitle;

    @Schema(description = "预警信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预警信息")
    private String alertMessage;

    @Schema(description = "当前库存")
    @ExcelProperty("当前库存")
    private BigDecimal currentStock;

    @Schema(description = "阈值")
    @ExcelProperty("阈值")
    private BigDecimal thresholdValue;

    @Schema(description = "关联设备ID(更换提醒时)", example = "30416")
    @ExcelProperty("关联设备ID(更换提醒时)")
    private Long equipmentId;

    @Schema(description = "处理人ID", example = "8864")
    @ExcelProperty("处理人ID")
    private Long handlerId;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "处理措施")
    @ExcelProperty("处理措施")
    private String handleAction;

    @Schema(description = "处理备注", example = "随便")
    @ExcelProperty("处理备注")
    private String handleRemark;

    @Schema(description = "是否已发送通知：1是 0否", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否已发送通知：1是 0否")
    private Boolean isSent;

    @Schema(description = "发送时间")
    @ExcelProperty("发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "接收人列表(逗号分隔)")
    @ExcelProperty("接收人列表(逗号分隔)")
    private String recipients;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}