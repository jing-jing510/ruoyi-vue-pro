package cn.iocoder.yudao.module.coal.controller.admin.schedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 排班管理 (主表)新增/修改 Request VO")
@Data
public class ScheduleSaveReqVO {

    @Schema(description = "排班ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7783")
    private Long id;

    @Schema(description = "排班日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排班日期不能为空")
    private LocalDate scheduleDate;

    @Schema(description = "班制ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4172")
    @NotNull(message = "班制ID不能为空")
    private Long shiftSystemId;

    @Schema(description = "排班状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "排班状态不能为空")
    private Integer scheduleStatus;

    @Schema(description = "是否生产日", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否生产日不能为空")
    private Boolean isProductionDay;

    @Schema(description = "当日生产目标(吨)")
    private BigDecimal productionTarget;

    @Schema(description = "排班类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "排班类型不能为空")
    private Integer scheduleType;

    @Schema(description = "审批人ID", example = "27681")
    private Long approverId;

    @Schema(description = "审批时间")
    private LocalDateTime approveTime;

    @Schema(description = "备注", example = "随便")
    private String remark;

}