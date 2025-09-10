package cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 检修单新增/修改 Request VO")
@Data
public class MaintenanceOrderSaveReqVO {

    @Schema(description = "检修单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27381")
    private Long id;

    @Schema(description = "检修单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "检修单编号不能为空")
    private String orderCode;

    @Schema(description = "关联计划ID", example = "6584")
    private Long planId;

    @Schema(description = "关联报修单ID", example = "6585")
    private Long repairRequestId;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24923")
    @NotNull(message = "设备ID不能为空")
    private Long equipmentId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
//    @NotEmpty(message = "设备名称不能为空")
    private String equipmentName;

    @Schema(description = "检修类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "检修类型不能为空")
    private Integer maintenanceType;

    @Schema(description = "检修级别", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检修级别不能为空")
    private Integer maintenanceLevel;

    @Schema(description = "检修单状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "检修单状态不能为空")
    private Integer orderStatus;

    @Schema(description = "优先级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "优先级不能为空")
    private Integer priorityLevel;

    @Schema(description = "故障描述", example = "你说的对")
    private String faultDescription;

    @Schema(description = "检修内容")
    private String maintenanceContent;

    @Schema(description = "安全措施")
    private String safetyMeasures;

    @Schema(description = "实际开始时间")
    private LocalDateTime startTime;

    @Schema(description = "实际结束时间")
    private LocalDateTime endTime;

    @Schema(description = "实际工期(小时)")
    private Integer actualDuration;

    @Schema(description = "负责人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "负责人不能为空")
    private String responsiblePerson;

    @Schema(description = "负责班组")
    private String responsibleTeam;

    @Schema(description = "参与人员")
    private String participants;

    @Schema(description = "作业环境")
    private String workEnvironment;

    @Schema(description = "天气条件")
    private String weatherCondition;

    @Schema(description = "完成进度(%)")
    private Integer completionRate;

    @Schema(description = "质量评级")
    private Integer qualityRating;

    @Schema(description = "安全评级")
    private Integer safetyRating;

    @Schema(description = "检修结果")
    private String maintenanceResult;

    @Schema(description = "发现问题")
    private String problemsFound;

    @Schema(description = "改进建议")
    private String improvementSuggestions;

    @Schema(description = "下次检修日期")
    private LocalDate nextMaintenanceDate;

    @Schema(description = "总费用")
    private BigDecimal totalCost;

    @Schema(description = "人工费用")
    private BigDecimal laborCost;

    @Schema(description = "材料费用")
    private BigDecimal materialCost;

    @Schema(description = "其他费用")
    private BigDecimal otherCost;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}