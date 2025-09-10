package cn.iocoder.yudao.module.coal.controller.admin.qualityalert.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 质量预警记录新增/修改 Request VO")
@Data
public class QualityAlertSaveReqVO {

    @Schema(description = "预警记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6622")
    private Long id;

    @Schema(description = "预警编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "预警编号不能为空")
    private String alertCode;

    @Schema(description = "预警类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "预警类型不能为空")
    private Integer alertType;

    @Schema(description = "预警级别", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "预警级别不能为空")
    private Integer alertLevel;

    @Schema(description = "检测项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "28743")
    @NotNull(message = "检测项目ID不能为空")
    private Long qualityItemId;

    @Schema(description = "关联检测记录ID", example = "1430")
    private Long inspectionId;

    @Schema(description = "产品类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "产品类型不能为空")
    private Integer productType;

    @Schema(description = "检测值")
    private BigDecimal measuredValue;

    @Schema(description = "标准值")
    private BigDecimal standardValue;

    @Schema(description = "预警阈值")
    private BigDecimal thresholdValue;

    @Schema(description = "偏差值")
    private BigDecimal deviation;

    @Schema(description = "预警信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "预警信息不能为空")
    private String alertMessage;

    @Schema(description = "预警时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "预警时间不能为空")
    private LocalDateTime alertTime;

    @Schema(description = "预警状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "预警状态不能为空")
    private Integer alertStatus;

    @Schema(description = "处理人ID", example = "29538")
    private Long handlerId;

    @Schema(description = "处理人姓名", example = "李四")
    private String handlerName;

    @Schema(description = "处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "处理措施")
    private String handleMethod;

    @Schema(description = "处理结果")
    private String handleResult;

    @Schema(description = "是否自动预警：0手动 1自动", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否自动预警：0手动 1自动不能为空")
    private Integer isAutoAlert;

    @Schema(description = "是否已发送通知：0否 1是", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否已发送通知：0否 1是不能为空")
    private Integer notificationSent;

    @Schema(description = "通知发送时间")
    private LocalDateTime notificationTime;

    @Schema(description = "接收人列表(逗号分隔)")
    private String recipients;

    @Schema(description = "备注", example = "随便")
    private String remark;

}