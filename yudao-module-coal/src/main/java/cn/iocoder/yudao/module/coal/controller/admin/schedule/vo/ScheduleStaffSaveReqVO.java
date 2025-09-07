package cn.iocoder.yudao.module.coal.controller.admin.schedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 人员安排 (子表)新增/修改 Request VO")
@Data
public class ScheduleStaffSaveReqVO {

    @Schema(description = "人员安排ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "221")
    private Long id;

    @Schema(description = "排班ID (关联主表)", requiredMode = Schema.RequiredMode.REQUIRED, example = "27718")
    @NotNull(message = "排班ID (关联主表)不能为空")
    private Long scheduleId;

    @Schema(description = "班次ID (关联coal_shift_system的子节点)", requiredMode = Schema.RequiredMode.REQUIRED, example = "3370")
    @NotNull(message = "班次ID (关联coal_shift_system的子节点)不能为空")
    private Long shiftId;

    @Schema(description = "员工ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "272")
    @NotNull(message = "员工ID不能为空")
    private Long userId;

    @Schema(description = "岗位类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "岗位类型不能为空")
    private Integer positionType;

    @Schema(description = "是否班组长", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否班组长不能为空")
    private Boolean isLeader;

    @Schema(description = "是否替班", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否替班不能为空")
    private Boolean isSubstitute;

    @Schema(description = "替班原因", example = "不好")
    private String substituteReason;

    @Schema(description = "工作状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "工作状态不能为空")
    private Integer workStatus;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "紧急联系人")
    private String emergencyContact;

    @Schema(description = "紧急联系电话")
    private String emergencyPhone;

}