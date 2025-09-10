package cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 生产日报统计 Response VO")
@Data
public class ProductionDailyReportStatisticsRespVO {

    @Schema(description = "今日生产统计")
    private TodayProductionStatistics todayProduction;

    @Schema(description = "月度生产统计")
    private MonthlyProductionStatistics monthlyProduction;

    @Schema(description = "生产概览统计")
    private ProductionOverviewStatistics overview;

    @Schema(description = "今日生产统计")
    @Data
    public static class TodayProductionStatistics {
        @Schema(description = "统计日期")
        private LocalDate statisticsDate;

        @Schema(description = "今日入洗原煤总量(吨)")
        private BigDecimal todayRawCoalInput;

        @Schema(description = "今日精煤产量总量(吨)")
        private BigDecimal todayCleanCoalOutput;

        @Schema(description = "今日运行时间总计(小时)")
        private BigDecimal todayOperatingHours;

        @Schema(description = "今日日报填报数量")
        private Integer todayReportCount;

        @Schema(description = "今日缺失填报数量")
        private Integer todayMissingReportCount;
    }

    @Schema(description = "月度生产统计")
    @Data
    public static class MonthlyProductionStatistics {
        @Schema(description = "统计年份")
        private Integer statisticsYear;

        @Schema(description = "统计月份")
        private Integer statisticsMonth;

        @Schema(description = "月度产量(吨)")
        private BigDecimal monthlyOutput;

        @Schema(description = "月度精煤产量(吨)")
        private BigDecimal monthlyCleanCoalOutput;

        @Schema(description = "月度生产天数")
        private Integer monthlyProductionDays;

        @Schema(description = "月度平均日产量(吨)")
        private BigDecimal monthlyAverageDailyOutput;
    }

    @Schema(description = "生产概览统计")
    @Data
    public static class ProductionOverviewStatistics {
        @Schema(description = "总生产天数(基于日报数量)")
        private Integer totalProductionDays;

        @Schema(description = "年度计划完成率(%)")
        private BigDecimal yearlyPlanCompletionRate;

        @Schema(description = "月度计划完成率(%)")
        private BigDecimal monthlyPlanCompletionRate;

        @Schema(description = "日计划完成率(%)")
        private BigDecimal dailyPlanCompletionRate;

        @Schema(description = "在岗人员数量")
        private Integer activeStaffCount;

        @Schema(description = "系统用户总数")
        private Integer totalUserCount;
    }
}
