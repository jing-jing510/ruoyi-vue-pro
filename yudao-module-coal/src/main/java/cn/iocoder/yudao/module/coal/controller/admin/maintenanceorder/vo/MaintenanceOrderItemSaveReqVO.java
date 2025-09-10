package cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 检修项目明细新增/修改 Request VO")
@Data
public class MaintenanceOrderItemSaveReqVO {

    @Schema(description = "明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19169")
    private Long id;

    @Schema(description = "检修单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31721")
    @NotNull(message = "检修单ID不能为空")
    private Long orderId;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "项目名称不能为空")
    private String itemName;

    @Schema(description = "项目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "项目类型不能为空")
    private Integer itemType;

    @Schema(description = "项目描述", example = "你猜")
    private String itemDescription;

    @Schema(description = "作业标准")
    private String workStandard;

    @Schema(description = "安全要求")
    private String safetyRequirements;

    @Schema(description = "所需工具")
    private String requiredTools;

    @Schema(description = "所需材料")
    private String requiredMaterials;

    @Schema(description = "预计工时(小时)")
    private Integer estimatedDuration;

    @Schema(description = "实际工时(小时)")
    private Integer actualDuration;

    @Schema(description = "项目状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "项目状态不能为空")
    private Integer itemStatus;

    @Schema(description = "完成时间")
    private LocalDateTime completionTime;

    @Schema(description = "完成质量")
    private Integer completionQuality;

    @Schema(description = "完成说明")
    private String completionNotes;

    @Schema(description = "检查人")
    private String inspector;

    @Schema(description = "检查时间")
    private LocalDateTime inspectionTime;

    @Schema(description = "检查结果")
    private String inspectionResult;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}