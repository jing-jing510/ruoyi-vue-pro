package cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 生产计划进展统计响应 VO
 *
 * @author 京京
 */
@Schema(description = "管理后台 - 生产计划进展统计响应 VO")
@Data
public class ProductionPlanProgressRespVO {

    @Schema(description = "当月计划(吨)", requiredMode = Schema.RequiredMode.REQUIRED, example = "10000.00")
    private BigDecimal monthlyPlan;

    @Schema(description = "当月实际产量(吨)", requiredMode = Schema.RequiredMode.REQUIRED, example = "8500.00")
    private BigDecimal monthlyActual;

    @Schema(description = "当月计划完成百分比(%)", requiredMode = Schema.RequiredMode.REQUIRED, example = "85.00")
    private BigDecimal monthlyProgressPercentage;

    @Schema(description = "当年计划(吨)", requiredMode = Schema.RequiredMode.REQUIRED, example = "120000.00")
    private BigDecimal yearlyPlan;

    @Schema(description = "当年实际产量(吨)", requiredMode = Schema.RequiredMode.REQUIRED, example = "95000.00")
    private BigDecimal yearlyActual;

    @Schema(description = "当年计划完成百分比(%)", requiredMode = Schema.RequiredMode.REQUIRED, example = "79.17")
    private BigDecimal yearlyProgressPercentage;

    @Schema(description = "统计年份", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025")
    private Integer year;

    @Schema(description = "统计月份", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer month;

}
