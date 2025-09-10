package cn.iocoder.yudao.module.coal.controller.admin.sparepartusagerecord.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import cn.iocoder.yudao.framework.common.util.date.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 备件使用记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SparePartUsageRecordRespVO {

    @Schema(description = "使用记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24996")
    @ExcelProperty("使用记录ID")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27252")
    @ExcelProperty("备件ID")
    private Long sparePartId;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4662")
    @ExcelProperty("设备ID")
    private Long equipmentId;

    @Schema(description = "使用类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "使用类型", converter = DictConvert.class)
    @DictFormat("usage_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer usageType;

    @Schema(description = "使用日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("使用日期")
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime usageDate;

    @Schema(description = "使用数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("使用数量")
    private BigDecimal quantity;

    @Schema(description = "操作人ID", example = "7402")
    @ExcelProperty("操作人ID")
    private Long operatorId;

    @Schema(description = "监督人ID", example = "26755")
    @ExcelProperty("监督人ID")
    private Long supervisorId;

    @Schema(description = "作业班组")
    @ExcelProperty("作业班组")
    private String workTeam;

    @Schema(description = "工单ID(关联检修单)", example = "2598")
    @ExcelProperty("工单ID(关联检修单)")
    private Long workOrderId;

    @Schema(description = "故障ID(关联故障记录)", example = "21949")
    @ExcelProperty("故障ID(关联故障记录)")
    private Long faultId;

    @Schema(description = "维护计划ID", example = "26337")
    @ExcelProperty("维护计划ID")
    private Long maintenancePlanId;

    @Schema(description = "旧件状态")
    @ExcelProperty(value = "旧件状态", converter = DictConvert.class)
    @DictFormat("old_part_condition") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer oldPartCondition;

    @Schema(description = "失效模式")
    @ExcelProperty("失效模式")
    private String failureMode;

    @Schema(description = "使用时长(天)")
    @ExcelProperty("使用时长(天)")
    private Integer usageDuration;

    @Schema(description = "更换原因", example = "不喜欢")
    @ExcelProperty("更换原因")
    private String replacementReason;

    @Schema(description = "预计寿命(天)")
    @ExcelProperty("预计寿命(天)")
    private Integer predictedLifespan;

    @Schema(description = "预计下次更换日期")
    @ExcelProperty("预计下次更换日期")
    private LocalDate nextReplacementDate;

    @Schema(description = "性能评级")
    @ExcelProperty(value = "性能评级", converter = DictConvert.class)
    @DictFormat("performance_rating") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer performanceRating;

    @Schema(description = "单位成本")
    @ExcelProperty("单位成本")
    private BigDecimal unitCost;

    @Schema(description = "总成本")
    @ExcelProperty("总成本")
    private BigDecimal totalCost;

    @Schema(description = "人工成本")
    @ExcelProperty("人工成本")
    private BigDecimal laborCost;

    @Schema(description = "停机成本")
    @ExcelProperty("停机成本")
    private BigDecimal downtimeCost;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
