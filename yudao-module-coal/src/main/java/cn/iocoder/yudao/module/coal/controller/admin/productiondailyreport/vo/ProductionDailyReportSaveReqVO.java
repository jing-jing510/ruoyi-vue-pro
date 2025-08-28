package cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 现场生产日报新增/修改 Request VO")
@Data
public class ProductionDailyReportSaveReqVO {

    @Schema(description = "日报id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13158")
    private Long id;

    @Schema(description = "日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "日期不能为空")
    private LocalDateTime reportDate;

    @Schema(description = "班次ID", example = "22095")
    private Long shiftId;

    @Schema(description = "集控员（操作人）ID", example = "23661")
    private Long operatorId;

    @Schema(description = "带班主任/班长ID", example = "1797")
    private Long shiftLeaderId;

    @Schema(description = "启车时间")
    private LocalDateTime startTime;

    @Schema(description = "带煤时间(分钟)")
    private Integer coalFeedingTime;

    @Schema(description = "停车时间")
    private LocalDateTime stopTime;

    @Schema(description = "有效带煤时间(分钟)")
    private Integer effectiveFeedingTime;

    @Schema(description = "故障影响时间(分钟)")
    private Integer faultImpactTime;

    @Schema(description = "入洗煤量(吨)")
    private BigDecimal rawCoalInput;

    @Schema(description = "小时处理量(吨/小时)")
    private BigDecimal hourlyCapacity;

    @Schema(description = "块精煤产量(吨)")
    private BigDecimal blockCleanCoalOutput;

    @Schema(description = "末精煤产量(吨)")
    private BigDecimal fineCleanCoalOutput;

    @Schema(description = "矸石产量(吨)")
    private BigDecimal gangueOutput;

    @Schema(description = "中煤产量(吨)")
    private BigDecimal middlingCoalOutput;

    @Schema(description = "压滤板次")
    private Integer filterPressCycles;

    @Schema(description = "压滤煤量(吨)")
    private BigDecimal filterPressCoalAmount;

    @Schema(description = "滤布使用量(张)")
    private Integer filterClothUsage;

    @Schema(description = "放舱记录")
    private String dischargeRecord;

    @Schema(description = "挡板添加介质量(kg)")
    private BigDecimal baffleMediumAddition;

    @Schema(description = "CaO量(kg)")
    private BigDecimal caoAmount;

    @Schema(description = "絮凝剂(kg)")
    private BigDecimal flocculantAmount;

    @Schema(description = "317密度(kg/L)")
    private BigDecimal densityMd317;

    @Schema(description = "第一次块精煤灰分(%)")
    private BigDecimal firstAshBlockClean;

    @Schema(description = "第一次末精煤灰分(%)")
    private BigDecimal firstAshFineClean;

    @Schema(description = "第一次中煤灰分(%)")
    private BigDecimal firstAshMiddling;

    @Schema(description = "第一次煤泥灰分(%)")
    private BigDecimal firstAshSlime;

    @Schema(description = "第一次矸石灰分(%)")
    private BigDecimal firstAshGangue;

    @Schema(description = "第二次块精煤灰分(%)")
    private BigDecimal secondAshBlockClean;

    @Schema(description = "第二次末精煤灰分(%)")
    private BigDecimal secondAshFineClean;

    @Schema(description = "第二次中煤灰分(%)")
    private BigDecimal secondAshMiddling;

    @Schema(description = "第二次煤泥灰分(%)")
    private BigDecimal secondAshSlime;

    @Schema(description = "第二次矸石灰分(%)")
    private BigDecimal secondAshGangue;

    @Schema(description = "影响时间记录详情")
    private String impactTimeRecord;

    @Schema(description = "交办事项")
    private String assignedTasks;

    @Schema(description = "启车循环水池液位")
    private BigDecimal startCirculatingWaterPool;

    @Schema(description = "启车清水桶液位")
    private BigDecimal startCleanWaterTank;

    @Schema(description = "启车末煤仓位")
    private BigDecimal startFineCoalLevel;

    @Schema(description = "启车粒煤仓位")
    private BigDecimal startGranularCoalLevel;

    @Schema(description = "启车大块仓位")
    private BigDecimal startLargeBlockLevel;

    @Schema(description = "启车中块仓位")
    private BigDecimal startMediumBlockLevel;

    @Schema(description = "启车小块仓位")
    private BigDecimal startSmallBlockLevel;

    @Schema(description = "启车中煤仓位")
    private BigDecimal startMiddlingCoalLevel;

    @Schema(description = "启车矸石仓位")
    private BigDecimal startGangueLevel;

    @Schema(description = "停车循环水池液位")
    private BigDecimal stopCirculatingWaterPool;

    @Schema(description = "停车清水桶液位")
    private BigDecimal stopCleanWaterTank;

    @Schema(description = "停车末煤仓位")
    private BigDecimal stopFineCoalLevel;

    @Schema(description = "停车粒煤仓位")
    private BigDecimal stopGranularCoalLevel;

    @Schema(description = "停车大块仓位")
    private BigDecimal stopLargeBlockLevel;

    @Schema(description = "停车中块仓位")
    private BigDecimal stopMediumBlockLevel;

    @Schema(description = "停车小块仓位")
    private BigDecimal stopSmallBlockLevel;

    @Schema(description = "停车中煤仓位")
    private BigDecimal stopMiddlingCoalLevel;

    @Schema(description = "停车矸石仓位")
    private BigDecimal stopGangueLevel;

    @Schema(description = "备注信息")
    private String remarks;

}