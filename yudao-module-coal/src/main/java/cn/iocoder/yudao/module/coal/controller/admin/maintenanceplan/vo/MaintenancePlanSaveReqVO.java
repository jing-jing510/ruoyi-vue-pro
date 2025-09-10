package cn.iocoder.yudao.module.coal.controller.admin.maintenanceplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 检修计划新增/修改 Request VO")
@Data
public class MaintenancePlanSaveReqVO {

    @Schema(description = "计划ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24465")
    private Long id;

    @Schema(description = "计划编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "计划编号不能为空")
    private String planCode;

    @Schema(description = "计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "计划名称不能为空")
    private String planName;

    @Schema(description = "计划类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "计划类型不能为空")
    private Integer planType;

    @Schema(description = "计划状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "计划状态不能为空")
    private Integer planStatus;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26812")
    @NotNull(message = "设备ID不能为空")
    private Long equipmentId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String equipmentName;

    @Schema(description = "检修类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "检修类型不能为空")
    private Integer maintenanceType;

    @Schema(description = "检修级别", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检修级别不能为空")
    private Integer maintenanceLevel;

    @Schema(description = "计划开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "计划开始时间不能为空")
    private LocalDateTime plannedStartTime;

    @Schema(description = "计划结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "计划结束时间不能为空")
    private LocalDateTime plannedEndTime;

    @Schema(description = "预计工期(小时)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "预计工期(小时)不能为空")
    private Integer estimatedDuration;

    @Schema(description = "负责人", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "负责人不能为空")
    private String responsiblePerson;

    @Schema(description = "负责班组")
    private String responsibleTeam;

    @Schema(description = "检修内容")
    private String maintenanceContent;

    @Schema(description = "安全要求")
    private String safetyRequirements;

    @Schema(description = "所需工具")
    private String requiredTools;

    @Schema(description = "所需材料")
    private String requiredMaterials;

    @Schema(description = "预算费用")
    private BigDecimal budgetCost;

    @Schema(description = "实际费用")
    private BigDecimal actualCost;

    @Schema(description = "审批状态", example = "1")
    private Integer approvalStatus;

    @Schema(description = "审批人ID", example = "29047")
    private Long approverId;

    @Schema(description = "审批时间")
    private LocalDateTime approvalTime;

    @Schema(description = "审批备注", example = "你说的对")
    private String approvalRemark;

    @Schema(description = "备注", example = "随便")
    private String remark;

}