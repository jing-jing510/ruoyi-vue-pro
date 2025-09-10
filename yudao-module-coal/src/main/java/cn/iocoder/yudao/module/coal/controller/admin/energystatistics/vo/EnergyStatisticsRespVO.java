package cn.iocoder.yudao.module.coal.controller.admin.energystatistics.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 能源消耗统计 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyStatisticsRespVO {

    @Schema(description = "统计ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27400")
    @ExcelProperty("统计ID")
    private Long id;

    @Schema(description = "统计日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("统计日期")
    private LocalDate statisticsDate;

    @Schema(description = "统计类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "统计类型", converter = DictConvert.class)
    @DictFormat("coal_energy_statistics_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer statisticsType;

    @Schema(description = "能源类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6180")
    @ExcelProperty("能源类型ID")
    private Long energyTypeId;

    @Schema(description = "总消耗量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("总消耗量")
    private BigDecimal totalConsumption;

    @Schema(description = "总成本(元)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("总成本(元)")
    private BigDecimal totalCost;

    @Schema(description = "平均消耗量")
    @ExcelProperty("平均消耗量")
    private BigDecimal averageConsumption;

    @Schema(description = "峰值消耗量")
    @ExcelProperty("峰值消耗量")
    private BigDecimal peakConsumption;

    @Schema(description = "谷值消耗量")
    @ExcelProperty("谷值消耗量")
    private BigDecimal valleyConsumption;

    @Schema(description = "消耗效率(%)")
    @ExcelProperty("消耗效率(%)")
    private BigDecimal consumptionEfficiency;

    @Schema(description = "单位成本(元/单位)")
    @ExcelProperty("单位成本(元/单位)")
    private BigDecimal costPerUnit;

    @Schema(description = "与计划对比(%)")
    @ExcelProperty("与计划对比(%)")
    private BigDecimal comparisonWithPlan;

    @Schema(description = "与上期对比(%)")
    @ExcelProperty("与上期对比(%)")
    private BigDecimal comparisonWithLastPeriod;

    @Schema(description = "统计状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "统计状态", converter = DictConvert.class)
    @DictFormat("coal_energy_statistics_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer statisticsStatus;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
