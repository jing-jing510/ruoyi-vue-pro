package cn.iocoder.yudao.module.coal.controller.admin.energyalert.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 能源预警记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyAlertRespVO {

    @Schema(description = "预警ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12014")
    @ExcelProperty("预警ID")
    private Long id;

    @Schema(description = "预警编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预警编号")
    private String alertCode;

    @Schema(description = "能源类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15910")
    @ExcelProperty("能源类型ID")
    private Long energyTypeId;

    @Schema(description = "预警类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "预警类型", converter = DictConvert.class)
    @DictFormat("coal_energy_alert_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer alertType;

    @Schema(description = "预警级别", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "预警级别", converter = DictConvert.class)
    @DictFormat("coal_energy_alert_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer alertLevel;

    @Schema(description = "预警标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预警标题")
    private String alertTitle;

    @Schema(description = "预警内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预警内容")
    private String alertContent;

    @Schema(description = "当前值")
    @ExcelProperty("当前值")
    private BigDecimal currentValue;

    @Schema(description = "阈值")
    @ExcelProperty("阈值")
    private BigDecimal thresholdValue;

    @Schema(description = "偏差率(%)")
    @ExcelProperty("偏差率(%)")
    private BigDecimal deviationRate;

    @Schema(description = "预警时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预警时间")
    private LocalDateTime alertTime;

    @Schema(description = "预警状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "预警状态", converter = DictConvert.class)
    @DictFormat("coal_energy_alert_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer alertStatus;

    @Schema(description = "处理人ID", example = "21306")
    @ExcelProperty("处理人ID")
    private Long handlerId;

    @Schema(description = "处理人姓名", example = "赵六")
    @ExcelProperty("处理人姓名")
    private String handlerName;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "处理结果")
    @ExcelProperty("处理结果")
    private String handleResult;

    @Schema(description = "处理备注", example = "你猜")
    @ExcelProperty("处理备注")
    private String handleRemark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}