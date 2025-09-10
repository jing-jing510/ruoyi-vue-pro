package cn.iocoder.yudao.module.coal.controller.admin.safetycheckplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 安全检查计划新增/修改 Request VO")
@Data
public class SafetyCheckPlanSaveReqVO {

    @Schema(description = "计划ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15507")
    private Long id;

    @Schema(description = "计划编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "计划编号不能为空")
    private String planCode;

    @Schema(description = "计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "计划名称不能为空")
    private String planName;

    @Schema(description = "计划类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "计划类型不能为空")
    private Integer planType;

    @Schema(description = "检查分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18173")
    @NotNull(message = "检查分类ID不能为空")
    private Long checkCategoryId;

    @Schema(description = "检查周期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检查周期不能为空")
    private Integer checkCycle;

    @Schema(description = "检查频次", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检查频次不能为空")
    private Integer checkFrequency;

    @Schema(description = "负责人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22739")
    @NotNull(message = "负责人ID不能为空")
    private Long responsiblePersonId;

    @Schema(description = "负责人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "负责人姓名不能为空")
    private String responsiblePersonName;

    @Schema(description = "检查区域")
    private String checkArea;

    @Schema(description = "检查内容")
    private String checkContent;

    @Schema(description = "检查标准")
    private String checkStandard;

    @Schema(description = "开始日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @Schema(description = "结束日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结束日期不能为空")
    private LocalDate endDate;

    @Schema(description = "计划状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "计划状态不能为空")
    private Integer planStatus;

    @Schema(description = "审批状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "审批状态不能为空")
    private Integer approvalStatus;

    @Schema(description = "审批人ID", example = "10847")
    private Long approverId;

    @Schema(description = "审批人姓名", example = "张三")
    private String approverName;

    @Schema(description = "审批时间")
    private LocalDateTime approvalTime;

    @Schema(description = "审批备注", example = "你说的对")
    private String approvalRemark;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}