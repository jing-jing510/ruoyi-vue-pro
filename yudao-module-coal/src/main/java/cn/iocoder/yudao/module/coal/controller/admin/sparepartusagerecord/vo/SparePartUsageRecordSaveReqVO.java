package cn.iocoder.yudao.module.coal.controller.admin.sparepartusagerecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import cn.iocoder.yudao.framework.common.util.date.DateUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 备件使用记录新增/修改 Request VO")
@Data
public class SparePartUsageRecordSaveReqVO {

    @Schema(description = "使用记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24996")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27252")
    @NotNull(message = "备件ID不能为空")
    private Long sparePartId;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4662")
    @NotNull(message = "设备ID不能为空")
    private Long equipmentId;

    @Schema(description = "使用类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "使用类型不能为空")
    private Integer usageType;

    @Schema(description = "使用日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "使用日期不能为空")
    private LocalDateTime usageDate;

    @Schema(description = "使用数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "使用数量不能为空")
    private BigDecimal quantity;

    @Schema(description = "操作人ID", example = "7402")
    private Long operatorId;

    @Schema(description = "监督人ID", example = "26755")
    private Long supervisorId;

    @Schema(description = "作业班组")
    private String workTeam;

    @Schema(description = "工单ID(关联检修单)", example = "2598")
    private Long workOrderId;

    @Schema(description = "故障ID(关联故障记录)", example = "21949")
    private Long faultId;

    @Schema(description = "维护计划ID", example = "26337")
    private Long maintenancePlanId;

    @Schema(description = "旧件状态")
    private Integer oldPartCondition;

    @Schema(description = "失效模式")
    private String failureMode;

    @Schema(description = "使用时长(天)")
    private Integer usageDuration;

    @Schema(description = "更换原因", example = "不喜欢")
    private String replacementReason;

    @Schema(description = "预计寿命(天)")
    private Integer predictedLifespan;

    @Schema(description = "预计下次更换日期")
    private LocalDate nextReplacementDate;

    @Schema(description = "性能评级")
    private Integer performanceRating;

    @Schema(description = "单位成本")
    private BigDecimal unitCost;

    @Schema(description = "总成本")
    private BigDecimal totalCost;

    @Schema(description = "人工成本")
    private BigDecimal laborCost;

    @Schema(description = "停机成本")
    private BigDecimal downtimeCost;

    @Schema(description = "备注", example = "随便")
    private String remark;

}