package cn.iocoder.yudao.module.coal.controller.admin.sparepartalert.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 备件预警记录新增/修改 Request VO")
@Data
public class SparePartAlertSaveReqVO {

    @Schema(description = "预警ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18433")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12198")
    @NotNull(message = "备件ID不能为空")
    private Long sparePartId;

    @Schema(description = "预警类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "预警类型不能为空")
    private Integer alertType;

    @Schema(description = "预警级别", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "预警级别不能为空")
    private Integer alertLevel;

    @Schema(description = "预警状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "预警状态不能为空")
    private Integer alertStatus;

    @Schema(description = "预警标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "预警标题不能为空")
    private String alertTitle;

    @Schema(description = "预警信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "预警信息不能为空")
    private String alertMessage;

    @Schema(description = "当前库存")
    private BigDecimal currentStock;

    @Schema(description = "阈值")
    private BigDecimal thresholdValue;

    @Schema(description = "关联设备ID(更换提醒时)", example = "30416")
    private Long equipmentId;

    @Schema(description = "处理人ID", example = "8864")
    private Long handlerId;

    @Schema(description = "处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "处理措施")
    private String handleAction;

    @Schema(description = "处理备注", example = "随便")
    private String handleRemark;

    @Schema(description = "是否已发送通知：1是 0否", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否已发送通知：1是 0否不能为空")
    private Boolean isSent;

    @Schema(description = "发送时间")
    private LocalDateTime sendTime;

    @Schema(description = "接收人列表(逗号分隔)")
    private String recipients;

}