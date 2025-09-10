package cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 备件库存统计 Response VO")
@Data
public class SparePartStockStatisticsRespVO {

    @Schema(description = "库存概览")
    private StockOverview overview;

    @Schema(description = "库存预警统计")
    private StockAlertStatistics alertStatistics;

    @Schema(description = "库存分类统计")
    private List<CategoryStatistics> categoryStatistics;

    @Schema(description = "库存趋势数据")
    private List<TrendData> trendData;

    @Schema(description = "ABC分类统计")
    private List<ABCCategoryStatistics> abcStatistics;

    @Schema(description = "使用频率统计")
    private UsageFrequencyStatistics usageFrequencyStatistics;

    @Schema(description = "库存概览")
    @Data
    public static class StockOverview {
        @Schema(description = "总备件种类数")
        private Integer totalSparePartTypes;

        @Schema(description = "总库存数量")
        private BigDecimal totalStockQuantity;

        @Schema(description = "总库存价值")
        private BigDecimal totalStockValue;

        @Schema(description = "平均库存周转率")
        private BigDecimal averageTurnoverRate;

        @Schema(description = "库存健康度评分")
        private Integer healthScore;
    }

    @Schema(description = "库存预警统计")
    @Data
    public static class StockAlertStatistics {
        @Schema(description = "低库存预警数量")
        private Integer lowStockCount;

        @Schema(description = "零库存数量")
        private Integer zeroStockCount;

        @Schema(description = "超库存数量")
        private Integer overStockCount;

        @Schema(description = "呆滞库存数量")
        private Integer stagnantStockCount;
    }

    @Schema(description = "分类统计")
    @Data
    public static class CategoryStatistics {
        @Schema(description = "分类ID")
        private Long categoryId;

        @Schema(description = "分类名称")
        private String categoryName;

        @Schema(description = "备件数量")
        private Integer sparePartCount;

        @Schema(description = "库存数量")
        private BigDecimal stockQuantity;

        @Schema(description = "库存价值")
        private BigDecimal stockValue;

        @Schema(description = "占比")
        private BigDecimal percentage;
    }

    @Schema(description = "趋势数据")
    @Data
    public static class TrendData {
        @Schema(description = "日期")
        private String date;

        @Schema(description = "入库数量")
        private BigDecimal inQuantity;

        @Schema(description = "出库数量")
        private BigDecimal outQuantity;

        @Schema(description = "库存数量")
        private BigDecimal stockQuantity;

        @Schema(description = "库存价值")
        private BigDecimal stockValue;
    }

    @Schema(description = "ABC分类统计")
    @Data
    public static class ABCCategoryStatistics {
        @Schema(description = "分类")
        private String category;

        @Schema(description = "备件数量")
        private Integer sparePartCount;

        @Schema(description = "库存价值")
        private BigDecimal stockValue;

        @Schema(description = "价值占比")
        private BigDecimal valuePercentage;

        @Schema(description = "数量占比")
        private BigDecimal quantityPercentage;
    }

    @Schema(description = "使用频率分析")
    @Data
    public static class UsageFrequencyAnalysis {
        @Schema(description = "备件ID")
        private Long sparePartId;

        @Schema(description = "备件名称")
        private String sparePartName;

        @Schema(description = "使用次数")
        private Integer usageCount;

        @Schema(description = "使用频率等级")
        private String frequencyLevel;

        @Schema(description = "最后使用时间")
        private LocalDateTime lastUsageDate;

        @Schema(description = "平均使用间隔(天)")
        private BigDecimal averageUsageInterval;

        @Schema(description = "使用趋势")
        private String usageTrend;
    }

    @Schema(description = "使用频率统计")
    @Data
    public static class UsageFrequencyStatistics {
        @Schema(description = "高频使用备件数量")
        private Integer highFrequencyCount;

        @Schema(description = "中频使用备件数量")
        private Integer mediumFrequencyCount;

        @Schema(description = "低频使用备件数量")
        private Integer lowFrequencyCount;

        @Schema(description = "未使用备件数量")
        private Integer unusedCount;

        @Schema(description = "使用频率分析列表")
        private List<UsageFrequencyAnalysis> frequencyAnalysis;
    }
}
