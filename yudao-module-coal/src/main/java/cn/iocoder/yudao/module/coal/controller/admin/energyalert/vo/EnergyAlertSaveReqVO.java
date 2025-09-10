package cn.iocoder.yudao.module.coal.controller.admin.energyalert.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 能源预警记录新增/修改 Request VO")
@Data
public class EnergyAlertSaveReqVO {

    @Schema(description = "预警ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12014")
    private Long id;

    @Schema(description = "预警编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "预警编号不能为空")
    private String alertCode;

    @Schema(description = "能源类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15910")
    @NotNull(message = "能源类型ID不能为空")
    private Long energyTypeId;

    @Schema(description = "预警类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "预警类型不能为空")
    private Integer alertType;

    @Schema(description = "预警级别", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "预警级别不能为空")
    private Integer alertLevel;

    @Schema(description = "预警标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "预警标题不能为空")
    private String alertTitle;

    @Schema(description = "预警内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "预警内容不能为空")
    private String alertContent;

    @Schema(description = "当前值")
    private BigDecimal currentValue;

    @Schema(description = "阈值")
    private BigDecimal thresholdValue;

    @Schema(description = "偏差率(%)")
    private BigDecimal deviationRate;

    @Schema(description = "预警时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "预警时间不能为空")
    private LocalDateTime alertTime;

    @Schema(description = "预警状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "预警状态不能为空")
    private Integer alertStatus;

    @Schema(description = "处理人ID", example = "21306")
    private Long handlerId;

    @Schema(description = "处理人姓名", example = "赵六")
    private String handlerName;

    @Schema(description = "处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "处理结果")
    private String handleResult;

    @Schema(description = "处理备注", example = "你猜")
    private String handleRemark;

}