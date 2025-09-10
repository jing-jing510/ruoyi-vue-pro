package cn.iocoder.yudao.module.coal.controller.admin.energyconsumption.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 能源消耗记录统计 Response VO")
@Data
public class EnergyConsumptionStatisticsRespVO {

    @Schema(description = "总记录数", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Long totalCount;

    @Schema(description = "待验证记录数", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Long pendingVerificationCount;

    @Schema(description = "已验证记录数", requiredMode = Schema.RequiredMode.REQUIRED, example = "90")
    private Long verifiedCount;

    @Schema(description = "异常记录数", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Long abnormalCount;

    @Schema(description = "今日记录数", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long todayCount;

    @Schema(description = "实时采集记录数", requiredMode = Schema.RequiredMode.REQUIRED, example = "60")
    private Long realTimeCount;

    @Schema(description = "人工录入记录数", requiredMode = Schema.RequiredMode.REQUIRED, example = "40")
    private Long manualCount;

    @Schema(description = "今日总消耗量", requiredMode = Schema.RequiredMode.REQUIRED, example = "1250.50")
    private BigDecimal todayTotalConsumption;

    @Schema(description = "今日总成本", requiredMode = Schema.RequiredMode.REQUIRED, example = "812.83")
    private BigDecimal todayTotalCost;

    @Schema(description = "本月总消耗量", requiredMode = Schema.RequiredMode.REQUIRED, example = "37500.50")
    private BigDecimal monthlyTotalConsumption;

    @Schema(description = "本月总成本", requiredMode = Schema.RequiredMode.REQUIRED, example = "24375.33")
    private BigDecimal monthlyTotalCost;

    @Schema(description = "平均效率", requiredMode = Schema.RequiredMode.REQUIRED, example = "92.5")
    private BigDecimal averageEfficiency;

    @Schema(description = "统计日期", example = "2025-01-01")
    private LocalDate statisticsDate;
}
