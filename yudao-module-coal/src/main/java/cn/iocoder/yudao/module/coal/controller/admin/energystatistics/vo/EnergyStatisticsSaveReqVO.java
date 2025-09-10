package cn.iocoder.yudao.module.coal.controller.admin.energystatistics.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 能源消耗统计新增/修改 Request VO")
@Data
public class EnergyStatisticsSaveReqVO {

    @Schema(description = "统计ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27400")
    private Long id;

    @Schema(description = "统计日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "统计日期不能为空")
    private LocalDate statisticsDate;

    @Schema(description = "统计类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "统计类型不能为空")
    private Integer statisticsType;

    @Schema(description = "能源类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6180")
    @NotNull(message = "能源类型ID不能为空")
    private Long energyTypeId;

    @Schema(description = "总消耗量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总消耗量不能为空")
    private BigDecimal totalConsumption;

    @Schema(description = "总成本(元)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "总成本(元)不能为空")
    private BigDecimal totalCost;

    @Schema(description = "平均消耗量")
    private BigDecimal averageConsumption;

    @Schema(description = "峰值消耗量")
    private BigDecimal peakConsumption;

    @Schema(description = "谷值消耗量")
    private BigDecimal valleyConsumption;

    @Schema(description = "消耗效率(%)")
    private BigDecimal consumptionEfficiency;

    @Schema(description = "单位成本(元/单位)")
    private BigDecimal costPerUnit;

    @Schema(description = "与计划对比(%)")
    private BigDecimal comparisonWithPlan;

    @Schema(description = "与上期对比(%)")
    private BigDecimal comparisonWithLastPeriod;

    @Schema(description = "统计状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "统计状态不能为空")
    private Integer statisticsStatus;

}