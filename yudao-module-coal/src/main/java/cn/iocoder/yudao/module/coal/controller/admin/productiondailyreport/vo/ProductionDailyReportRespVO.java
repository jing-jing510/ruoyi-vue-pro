package cn.iocoder.yudao.module.coal.controller.admin.productiondailyreport.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 现场生产日报 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductionDailyReportRespVO {

    @Schema(description = "日报id", requiredMode = Schema.RequiredMode.REQUIRED, example = "13158")
    @ExcelProperty("日报id")
    private Long id;

    @Schema(description = "日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("日期")
    private LocalDateTime reportDate;

    @Schema(description = "班次ID", example = "22095")
    @ExcelProperty("班次ID")
    private Long shiftId;

    @Schema(description = "集控员（操作人）ID", example = "23661")
    @ExcelProperty("集控员（操作人）ID")
    private Long operatorId;

    @Schema(description = "带班主任/班长ID", example = "1797")
    @ExcelProperty("带班主任/班长ID")
    private Long shiftLeaderId;

    @Schema(description = "启车时间")
    @ExcelProperty("启车时间")
    private LocalDateTime startTime;

    @Schema(description = "带煤时间(分钟)")
    @ExcelProperty("带煤时间(分钟)")
    private Integer coalFeedingTime;

    @Schema(description = "停车时间")
    @ExcelProperty("停车时间")
    private LocalDateTime stopTime;

    @Schema(description = "有效带煤时间(分钟)")
    @ExcelProperty("有效带煤时间(分钟)")
    private Integer effectiveFeedingTime;

    @Schema(description = "故障影响时间(分钟)")
    @ExcelProperty("故障影响时间(分钟)")
    private Integer faultImpactTime;

    @Schema(description = "入洗煤量(吨)")
    @ExcelProperty("入洗煤量(吨)")
    private BigDecimal rawCoalInput;

    @Schema(description = "小时处理量(吨/小时)")
    @ExcelProperty("小时处理量(吨/小时)")
    private BigDecimal hourlyCapacity;

    @Schema(description = "块精煤产量(吨)")
    @ExcelProperty("块精煤产量(吨)")
    private BigDecimal blockCleanCoalOutput;

    @Schema(description = "末精煤产量(吨)")
    @ExcelProperty("末精煤产量(吨)")
    private BigDecimal fineCleanCoalOutput;

    @Schema(description = "矸石产量(吨)")
    @ExcelProperty("矸石产量(吨)")
    private BigDecimal gangueOutput;

    @Schema(description = "中煤产量(吨)")
    @ExcelProperty("中煤产量(吨)")
    private BigDecimal middlingCoalOutput;

    @Schema(description = "压滤板次")
    @ExcelProperty("压滤板次")
    private Integer filterPressCycles;

    @Schema(description = "压滤煤量(吨)")
    @ExcelProperty("压滤煤量(吨)")
    private BigDecimal filterPressCoalAmount;

    @Schema(description = "滤布使用量(张)")
    @ExcelProperty("滤布使用量(张)")
    private Integer filterClothUsage;

    @Schema(description = "放舱记录")
    @ExcelProperty("放舱记录")
    private String dischargeRecord;

    @Schema(description = "挡板添加介质量(kg)")
    @ExcelProperty("挡板添加介质量(kg)")
    private BigDecimal baffleMediumAddition;

    @Schema(description = "CaO量(kg)")
    @ExcelProperty("CaO量(kg)")
    private BigDecimal caoAmount;

    @Schema(description = "絮凝剂(kg)")
    @ExcelProperty("絮凝剂(kg)")
    private BigDecimal flocculantAmount;

    @Schema(description = "317密度(kg/L)")
    @ExcelProperty("317密度(kg/L)")
    private BigDecimal densityMd317;

    @Schema(description = "第一次块精煤灰分(%)")
    @ExcelProperty("第一次块精煤灰分(%)")
    private BigDecimal firstAshBlockClean;

    @Schema(description = "第一次末精煤灰分(%)")
    @ExcelProperty("第一次末精煤灰分(%)")
    private BigDecimal firstAshFineClean;

    @Schema(description = "第一次中煤灰分(%)")
    @ExcelProperty("第一次中煤灰分(%)")
    private BigDecimal firstAshMiddling;

    @Schema(description = "第一次煤泥灰分(%)")
    @ExcelProperty("第一次煤泥灰分(%)")
    private BigDecimal firstAshSlime;

    @Schema(description = "第一次矸石灰分(%)")
    @ExcelProperty("第一次矸石灰分(%)")
    private BigDecimal firstAshGangue;

    @Schema(description = "第二次块精煤灰分(%)")
    @ExcelProperty("第二次块精煤灰分(%)")
    private BigDecimal secondAshBlockClean;

    @Schema(description = "第二次末精煤灰分(%)")
    @ExcelProperty("第二次末精煤灰分(%)")
    private BigDecimal secondAshFineClean;

    @Schema(description = "第二次中煤灰分(%)")
    @ExcelProperty("第二次中煤灰分(%)")
    private BigDecimal secondAshMiddling;

    @Schema(description = "第二次煤泥灰分(%)")
    @ExcelProperty("第二次煤泥灰分(%)")
    private BigDecimal secondAshSlime;

    @Schema(description = "第二次矸石灰分(%)")
    @ExcelProperty("第二次矸石灰分(%)")
    private BigDecimal secondAshGangue;

    @Schema(description = "影响时间记录详情")
    @ExcelProperty("影响时间记录详情")
    private String impactTimeRecord;

    @Schema(description = "交办事项")
    @ExcelProperty("交办事项")
    private String assignedTasks;

    @Schema(description = "启车循环水池液位")
    @ExcelProperty("启车循环水池液位")
    private BigDecimal startCirculatingWaterPool;

    @Schema(description = "启车清水桶液位")
    @ExcelProperty("启车清水桶液位")
    private BigDecimal startCleanWaterTank;

    @Schema(description = "启车末煤仓位")
    @ExcelProperty("启车末煤仓位")
    private BigDecimal startFineCoalLevel;

    @Schema(description = "启车粒煤仓位")
    @ExcelProperty("启车粒煤仓位")
    private BigDecimal startGranularCoalLevel;

    @Schema(description = "启车大块仓位")
    @ExcelProperty("启车大块仓位")
    private BigDecimal startLargeBlockLevel;

    @Schema(description = "启车中块仓位")
    @ExcelProperty("启车中块仓位")
    private BigDecimal startMediumBlockLevel;

    @Schema(description = "启车小块仓位")
    @ExcelProperty("启车小块仓位")
    private BigDecimal startSmallBlockLevel;

    @Schema(description = "启车中煤仓位")
    @ExcelProperty("启车中煤仓位")
    private BigDecimal startMiddlingCoalLevel;

    @Schema(description = "启车矸石仓位")
    @ExcelProperty("启车矸石仓位")
    private BigDecimal startGangueLevel;

    @Schema(description = "停车循环水池液位")
    @ExcelProperty("停车循环水池液位")
    private BigDecimal stopCirculatingWaterPool;

    @Schema(description = "停车清水桶液位")
    @ExcelProperty("停车清水桶液位")
    private BigDecimal stopCleanWaterTank;

    @Schema(description = "停车末煤仓位")
    @ExcelProperty("停车末煤仓位")
    private BigDecimal stopFineCoalLevel;

    @Schema(description = "停车粒煤仓位")
    @ExcelProperty("停车粒煤仓位")
    private BigDecimal stopGranularCoalLevel;

    @Schema(description = "停车大块仓位")
    @ExcelProperty("停车大块仓位")
    private BigDecimal stopLargeBlockLevel;

    @Schema(description = "停车中块仓位")
    @ExcelProperty("停车中块仓位")
    private BigDecimal stopMediumBlockLevel;

    @Schema(description = "停车小块仓位")
    @ExcelProperty("停车小块仓位")
    private BigDecimal stopSmallBlockLevel;

    @Schema(description = "停车中煤仓位")
    @ExcelProperty("停车中煤仓位")
    private BigDecimal stopMiddlingCoalLevel;

    @Schema(description = "停车矸石仓位")
    @ExcelProperty("停车矸石仓位")
    private BigDecimal stopGangueLevel;

    @Schema(description = "备注信息")
    @ExcelProperty("备注信息")
    private String remarks;

    // ========== 新选煤厂字段 ==========
    
    // 皮带秤及产量统计字段
    @Schema(description = "201原煤皮带接班量(吨)")
    @ExcelProperty("201原煤皮带接班量(吨)")
    private BigDecimal beltScale201RawCoalHandoverIn;
    @Schema(description = "201原煤皮带交班量(吨)")
    @ExcelProperty("201原煤皮带交班量(吨)")
    private BigDecimal beltScale201RawCoalHandoverOut;
    @Schema(description = "201原煤皮带当班量(吨)")
    @ExcelProperty("201原煤皮带当班量(吨)")
    private BigDecimal beltScale201RawCoalCurrentShift;
    @Schema(description = "201原煤皮带月累计(吨)")
    @ExcelProperty("201原煤皮带月累计(吨)")
    private BigDecimal beltScale201RawCoalMonthlyTotal;

    @Schema(description = "701块精煤皮带秤接班量(吨)")
    @ExcelProperty("701块精煤皮带秤接班量(吨)")
    private BigDecimal beltScale701BlockCleanHandoverIn;
    @Schema(description = "701块精煤皮带秤交班量(吨)")
    @ExcelProperty("701块精煤皮带秤交班量(吨)")
    private BigDecimal beltScale701BlockCleanHandoverOut;
    @Schema(description = "701块精煤皮带秤当班量(吨)")
    @ExcelProperty("701块精煤皮带秤当班量(吨)")
    private BigDecimal beltScale701BlockCleanCurrentShift;
    @Schema(description = "701块精煤皮带秤月累计(吨)")
    @ExcelProperty("701块精煤皮带秤月累计(吨)")
    private BigDecimal beltScale701BlockCleanMonthlyTotal;
    @Schema(description = "701块精煤当班产率(%)")
    @ExcelProperty("701块精煤当班产率(%)")
    private BigDecimal beltScale701BlockCleanCurrentYield;
    @Schema(description = "701块精煤月度产率(%)")
    @ExcelProperty("701块精煤月度产率(%)")
    private BigDecimal beltScale701BlockCleanMonthlyYield;

    @Schema(description = "702末精煤皮带秤接班量(吨)")
    @ExcelProperty("702末精煤皮带秤接班量(吨)")
    private BigDecimal beltScale702FineCleanHandoverIn;
    @Schema(description = "702末精煤皮带秤交班量(吨)")
    @ExcelProperty("702末精煤皮带秤交班量(吨)")
    private BigDecimal beltScale702FineCleanHandoverOut;
    @Schema(description = "702末精煤皮带秤当班量(吨)")
    @ExcelProperty("702末精煤皮带秤当班量(吨)")
    private BigDecimal beltScale702FineCleanCurrentShift;
    @Schema(description = "702末精煤皮带秤月累计(吨)")
    @ExcelProperty("702末精煤皮带秤月累计(吨)")
    private BigDecimal beltScale702FineCleanMonthlyTotal;
    @Schema(description = "702末精煤当班产率(%)")
    @ExcelProperty("702末精煤当班产率(%)")
    private BigDecimal beltScale702FineCleanCurrentYield;
    @Schema(description = "702末精煤月度产率(%)")
    @ExcelProperty("702末精煤月度产率(%)")
    private BigDecimal beltScale702FineCleanMonthlyYield;

    @Schema(description = "压滤煤泥接班量(吨)")
    @ExcelProperty("压滤煤泥接班量(吨)")
    private BigDecimal filterPressSlimeHandoverIn;
    @Schema(description = "压滤煤泥交班量(吨)")
    @ExcelProperty("压滤煤泥交班量(吨)")
    private BigDecimal filterPressSlimeHandoverOut;
    @Schema(description = "压滤煤泥当班量(吨)")
    @ExcelProperty("压滤煤泥当班量(吨)")
    private BigDecimal filterPressSlimeCurrentShift;
    @Schema(description = "压滤煤泥月累计(吨)")
    @ExcelProperty("压滤煤泥月累计(吨)")
    private BigDecimal filterPressSlimeMonthlyTotal;
    @Schema(description = "压滤煤泥当班产率(%)")
    @ExcelProperty("压滤煤泥当班产率(%)")
    private BigDecimal filterPressSlimeCurrentYield;
    @Schema(description = "压滤煤泥月度产率(%)")
    @ExcelProperty("压滤煤泥月度产率(%)")
    private BigDecimal filterPressSlimeMonthlyYield;

    @Schema(description = "未入洗末原煤秤接班量(吨)")
    @ExcelProperty("未入洗末原煤秤接班量(吨)")
    private BigDecimal unwashedFineRawCoalHandoverIn;
    @Schema(description = "未入洗末原煤秤交班量(吨)")
    @ExcelProperty("未入洗末原煤秤交班量(吨)")
    private BigDecimal unwashedFineRawCoalHandoverOut;
    @Schema(description = "未入洗末原煤秤当班量(吨)")
    @ExcelProperty("未入洗末原煤秤当班量(吨)")
    private BigDecimal unwashedFineRawCoalCurrentShift;
    @Schema(description = "未入洗末原煤秤月累计(吨)")
    @ExcelProperty("未入洗末原煤秤月累计(吨)")
    private BigDecimal unwashedFineRawCoalMonthlyTotal;
    @Schema(description = "未入洗末原煤当班产率(%)")
    @ExcelProperty("未入洗末原煤当班产率(%)")
    private BigDecimal unwashedFineRawCoalCurrentYield;
    @Schema(description = "未入洗末原煤月度产率(%)")
    @ExcelProperty("未入洗末原煤月度产率(%)")
    private BigDecimal unwashedFineRawCoalMonthlyYield;

    @Schema(description = "901矸石量当班量(吨)")
    @ExcelProperty("901矸石量当班量(吨)")
    private BigDecimal gangue901CurrentShift;
    @Schema(description = "901矸石量月累计(吨)")
    @ExcelProperty("901矸石量月累计(吨)")
    private BigDecimal gangue901MonthlyTotal;
    @Schema(description = "901矸石当班产率(%)")
    @ExcelProperty("901矸石当班产率(%)")
    private BigDecimal gangue901CurrentYield;
    @Schema(description = "901矸石月度产率(%)")
    @ExcelProperty("901矸石月度产率(%)")
    private BigDecimal gangue901MonthlyYield;

    @Schema(description = "商品煤总重当班量(吨)")
    @ExcelProperty("商品煤总重当班量(吨)")
    private BigDecimal commercialCoalTotalCurrentShift;
    @Schema(description = "商品煤总重月累计(吨)")
    @ExcelProperty("商品煤总重月累计(吨)")
    private BigDecimal commercialCoalTotalMonthlyTotal;
    @Schema(description = "商品煤总重当班产率(%)")
    @ExcelProperty("商品煤总重当班产率(%)")
    private BigDecimal commercialCoalTotalCurrentYield;
    @Schema(description = "商品煤总重月度产率(%)")
    @ExcelProperty("商品煤总重月度产率(%)")
    private BigDecimal commercialCoalTotalMonthlyYield;

    @Schema(description = "电表数接班读数(kw*h)")
    @ExcelProperty("电表数接班读数(kw*h)")
    private BigDecimal electricityMeterHandoverIn;
    @Schema(description = "电表数交班读数(kw*h)")
    @ExcelProperty("电表数交班读数(kw*h)")
    private BigDecimal electricityMeterHandoverOut;
    @Schema(description = "电表数当班量(kw*h)")
    @ExcelProperty("电表数当班量(kw*h)")
    private BigDecimal electricityMeterCurrentShift;
    @Schema(description = "电表数月累计(kw*h)")
    @ExcelProperty("电表数月累计(kw*h)")
    private BigDecimal electricityMeterMonthlyTotal;
    @Schema(description = "电表数当班产率(%)")
    @ExcelProperty("电表数当班产率(%)")
    private BigDecimal electricityMeterCurrentYield;
    @Schema(description = "电表数月度产率(%)")
    @ExcelProperty("电表数月度产率(%)")
    private BigDecimal electricityMeterMonthlyYield;

    @Schema(description = "矿井清水表数接班读数(m³)")
    @ExcelProperty("矿井清水表数接班读数(m³)")
    private BigDecimal mineCleanWaterHandoverIn;
    @Schema(description = "矿井清水表数交班读数(m³)")
    @ExcelProperty("矿井清水表数交班读数(m³)")
    private BigDecimal mineCleanWaterHandoverOut;
    @Schema(description = "矿井清水表数当班量(m³)")
    @ExcelProperty("矿井清水表数当班量(m³)")
    private BigDecimal mineCleanWaterCurrentShift;
    @Schema(description = "矿井清水表数月累计(m³)")
    @ExcelProperty("矿井清水表数月累计(m³)")
    private BigDecimal mineCleanWaterMonthlyTotal;
    @Schema(description = "矿井清水当班产率(%)")
    @ExcelProperty("矿井清水当班产率(%)")
    private BigDecimal mineCleanWaterCurrentYield;
    @Schema(description = "矿井清水月度产率(%)")
    @ExcelProperty("矿井清水月度产率(%)")
    private BigDecimal mineCleanWaterMonthlyYield;

    // 煤质情况字段
    @Schema(description = "原煤全水(%)")
    @ExcelProperty("原煤全水(%)")
    private BigDecimal rawCoalTotalMoisture;
    @Schema(description = "原煤灰分(%)")
    @ExcelProperty("原煤灰分(%)")
    private BigDecimal rawCoalAshContent;
    @Schema(description = "原煤全硫(%)")
    @ExcelProperty("原煤全硫(%)")
    private BigDecimal rawCoalTotalSulfur;
    @Schema(description = "原煤挥发分(%)")
    @ExcelProperty("原煤挥发分(%)")
    private BigDecimal rawCoalVolatileMatter;
    @Schema(description = "原煤低位发热量(kcal/kg)")
    @ExcelProperty("原煤低位发热量(kcal/kg)")
    private BigDecimal rawCoalLowerHeatingValue;

    @Schema(description = "筒仓大块全水(%)")
    @ExcelProperty("筒仓大块全水(%)")
    private BigDecimal siloLargeLumpsTotalMoisture;
    @Schema(description = "筒仓大块灰分(%)")
    @ExcelProperty("筒仓大块灰分(%)")
    private BigDecimal siloLargeLumpsAshContent;
    @Schema(description = "筒仓大块全硫(%)")
    @ExcelProperty("筒仓大块全硫(%)")
    private BigDecimal siloLargeLumpsTotalSulfur;
    @Schema(description = "筒仓大块挥发分(%)")
    @ExcelProperty("筒仓大块挥发分(%)")
    private BigDecimal siloLargeLumpsVolatileMatter;
    @Schema(description = "筒仓大块低位发热量(kcal/kg)")
    @ExcelProperty("筒仓大块低位发热量(kcal/kg)")
    private BigDecimal siloLargeLumpsLowerHeatingValue;

    @Schema(description = "筒仓三八块全水(%)")
    @ExcelProperty("筒仓三八块全水(%)")
    private BigDecimal silo38LumpsTotalMoisture;
    @Schema(description = "筒仓三八块灰分(%)")
    @ExcelProperty("筒仓三八块灰分(%)")
    private BigDecimal silo38LumpsAshContent;
    @Schema(description = "筒仓三八块全硫(%)")
    @ExcelProperty("筒仓三八块全硫(%)")
    private BigDecimal silo38LumpsTotalSulfur;
    @Schema(description = "筒仓三八块挥发分(%)")
    @ExcelProperty("筒仓三八块挥发分(%)")
    private BigDecimal silo38LumpsVolatileMatter;
    @Schema(description = "筒仓三八块低位发热量(kcal/kg)")
    @ExcelProperty("筒仓三八块低位发热量(kcal/kg)")
    private BigDecimal silo38LumpsLowerHeatingValue;

    @Schema(description = "筒仓籽煤全水(%)")
    @ExcelProperty("筒仓籽煤全水(%)")
    private BigDecimal siloSeedCoalTotalMoisture;
    @Schema(description = "筒仓籽煤灰分(%)")
    @ExcelProperty("筒仓籽煤灰分(%)")
    private BigDecimal siloSeedCoalAshContent;
    @Schema(description = "筒仓籽煤全硫(%)")
    @ExcelProperty("筒仓籽煤全硫(%)")
    private BigDecimal siloSeedCoalTotalSulfur;
    @Schema(description = "筒仓籽煤挥发分(%)")
    @ExcelProperty("筒仓籽煤挥发分(%)")
    private BigDecimal siloSeedCoalVolatileMatter;
    @Schema(description = "筒仓籽煤低位发热量(kcal/kg)")
    @ExcelProperty("筒仓籽煤低位发热量(kcal/kg)")
    private BigDecimal siloSeedCoalLowerHeatingValue;

    @Schema(description = "筒仓沫煤全水(%)")
    @ExcelProperty("筒仓沫煤全水(%)")
    private BigDecimal siloFineCoalTotalMoisture;
    @Schema(description = "筒仓沫煤灰分(%)")
    @ExcelProperty("筒仓沫煤灰分(%)")
    private BigDecimal siloFineCoalAshContent;
    @Schema(description = "筒仓沫煤全硫(%)")
    @ExcelProperty("筒仓沫煤全硫(%)")
    private BigDecimal siloFineCoalTotalSulfur;
    @Schema(description = "筒仓沫煤挥发分(%)")
    @ExcelProperty("筒仓沫煤挥发分(%)")
    private BigDecimal siloFineCoalVolatileMatter;
    @Schema(description = "筒仓沫煤低位发热量(kcal/kg)")
    @ExcelProperty("筒仓沫煤低位发热量(kcal/kg)")
    private BigDecimal siloFineCoalLowerHeatingValue;

    @Schema(description = "煤泥全水(%)")
    @ExcelProperty("煤泥全水(%)")
    private BigDecimal slimeTotalMoisture;
    @Schema(description = "煤泥灰分(%)")
    @ExcelProperty("煤泥灰分(%)")
    private BigDecimal slimeAshContent;
    @Schema(description = "煤泥全硫(%)")
    @ExcelProperty("煤泥全硫(%)")
    private BigDecimal slimeTotalSulfur;
    @Schema(description = "煤泥挥发分(%)")
    @ExcelProperty("煤泥挥发分(%)")
    private BigDecimal slimeVolatileMatter;
    @Schema(description = "煤泥低位发热量(kcal/kg)")
    @ExcelProperty("煤泥低位发热量(kcal/kg)")
    private BigDecimal slimeLowerHeatingValue;

    @Schema(description = "矸石全水(%)")
    @ExcelProperty("矸石全水(%)")
    private BigDecimal gangueTotalMoisture;
    @Schema(description = "矸石灰分(%)")
    @ExcelProperty("矸石灰分(%)")
    private BigDecimal gangueAshContent;
    @Schema(description = "矸石全硫(%)")
    @ExcelProperty("矸石全硫(%)")
    private BigDecimal gangueTotalSulfur;
    @Schema(description = "矸石挥发分(%)")
    @ExcelProperty("矸石挥发分(%)")
    private BigDecimal gangueVolatileMatter;
    @Schema(description = "矸石低位发热量(kcal/kg)")
    @ExcelProperty("矸石低位发热量(kcal/kg)")
    private BigDecimal gangueLowerHeatingValue;

    // 生产设置参数
    @Schema(description = "305浅槽密度最小值(g/cm³)")
    @ExcelProperty("305浅槽密度最小值(g/cm³)")
    private BigDecimal shallowTrough305DensityMin;
    @Schema(description = "305浅槽密度最大值(g/cm³)")
    @ExcelProperty("305浅槽密度最大值(g/cm³)")
    private BigDecimal shallowTrough305DensityMax;
    @Schema(description = "321旋流器压力(Mpa)")
    @ExcelProperty("321旋流器压力(Mpa)")
    private BigDecimal cyclone321Pressure;
    @Schema(description = "333TSS密度最小值(g/cm³)")
    @ExcelProperty("333TSS密度最小值(g/cm³)")
    private BigDecimal tss333DensityMin;
    @Schema(description = "333TSS密度最大值(g/cm³)")
    @ExcelProperty("333TSS密度最大值(g/cm³)")
    private BigDecimal tss333DensityMax;

    // 生产耗材统计
    @Schema(description = "电耗损当班量(kw*h)")
    @ExcelProperty("电耗损当班量(kw*h)")
    private BigDecimal electricityConsumptionCurrentShift;
    @Schema(description = "污水处理站补水量(m³)")
    @ExcelProperty("污水处理站补水量(m³)")
    private BigDecimal wastewaterTreatmentReplenishmentWater;
    @Schema(description = "磁铁矿粉当班量(吨)")
    @ExcelProperty("磁铁矿粉当班量(吨)")
    private BigDecimal magnetitePowderCurrentShift;
    @Schema(description = "白药-阴离子当班量(kg)")
    @ExcelProperty("白药-阴离子当班量(kg)")
    private BigDecimal whiteReagentAnionicCurrentShift;
    @Schema(description = "黄药-阳离子当班量(kg)")
    @ExcelProperty("黄药-阳离子当班量(kg)")
    private BigDecimal yellowReagentCationicCurrentShift;
    @Schema(description = "609压滤机开机板数")
    @ExcelProperty("609压滤机开机板数")
    private Integer filterPress609StartPlates;
    @Schema(description = "609压滤机停机板数")
    @ExcelProperty("609压滤机停机板数")
    private Integer filterPress609EndPlates;
    @Schema(description = "610压滤机开机板数")
    @ExcelProperty("610压滤机开机板数")
    private Integer filterPress610StartPlates;
    @Schema(description = "610压滤机停机板数")
    @ExcelProperty("610压滤机停机板数")
    private Integer filterPress610EndPlates;

    // 筒仓仓位统计
    @Schema(description = "原煤仓开机仓位(%)")
    @ExcelProperty("原煤仓开机仓位(%)")
    private BigDecimal rawCoalSiloStartupPosition;
    @Schema(description = "原煤仓停机仓位(%)")
    @ExcelProperty("原煤仓停机仓位(%)")
    private BigDecimal rawCoalSiloShutdownPosition;
    @Schema(description = "大块煤仓开机仓位(%)")
    @ExcelProperty("大块煤仓开机仓位(%)")
    private BigDecimal largeLumpCoalSiloStartupPosition;
    @Schema(description = "大块煤仓停机仓位(%)")
    @ExcelProperty("大块煤仓停机仓位(%)")
    private BigDecimal largeLumpCoalSiloShutdownPosition;
    @Schema(description = "三八块煤仓开机仓位(%)")
    @ExcelProperty("三八块煤仓开机仓位(%)")
    private BigDecimal lump38CoalSiloStartupPosition;
    @Schema(description = "三八块煤仓停机仓位(%)")
    @ExcelProperty("三八块煤仓停机仓位(%)")
    private BigDecimal lump38CoalSiloShutdownPosition;
    @Schema(description = "籽煤仓开机仓位(%)")
    @ExcelProperty("籽煤仓开机仓位(%)")
    private BigDecimal seedCoalSiloStartupPosition;
    @Schema(description = "籽煤仓停机仓位(%)")
    @ExcelProperty("籽煤仓停机仓位(%)")
    private BigDecimal seedCoalSiloShutdownPosition;
    @Schema(description = "沫煤仓开机仓位(%)")
    @ExcelProperty("沫煤仓开机仓位(%)")
    private BigDecimal fineCoalSiloStartupPosition;
    @Schema(description = "沫煤仓停机仓位(%)")
    @ExcelProperty("沫煤仓停机仓位(%)")
    private BigDecimal fineCoalSiloShutdownPosition;

    // 浓缩机阻力
    @Schema(description = "501浓缩机阻力开机1(Mpa)")
    @ExcelProperty("501浓缩机阻力开机1(Mpa)")
    private BigDecimal thickener501ResistanceStartup1;
    @Schema(description = "501浓缩机阻力开机2(Mpa)")
    @ExcelProperty("501浓缩机阻力开机2(Mpa)")
    private BigDecimal thickener501ResistanceStartup2;
    @Schema(description = "501浓缩机阻力开机3(Mpa)")
    @ExcelProperty("501浓缩机阻力开机3(Mpa)")
    private BigDecimal thickener501ResistanceStartup3;
    @Schema(description = "501浓缩机阻力开机4(Mpa)")
    @ExcelProperty("501浓缩机阻力开机4(Mpa)")
    private BigDecimal thickener501ResistanceStartup4;
    @Schema(description = "501浓缩机阻力停机1(Mpa)")
    @ExcelProperty("501浓缩机阻力停机1(Mpa)")
    private BigDecimal thickener501ResistanceShutdown1;
    @Schema(description = "501浓缩机阻力停机2(Mpa)")
    @ExcelProperty("501浓缩机阻力停机2(Mpa)")
    private BigDecimal thickener501ResistanceShutdown2;
    @Schema(description = "501浓缩机阻力停机3(Mpa)")
    @ExcelProperty("501浓缩机阻力停机3(Mpa)")
    private BigDecimal thickener501ResistanceShutdown3;
    @Schema(description = "501浓缩机阻力停机4(Mpa)")
    @ExcelProperty("501浓缩机阻力停机4(Mpa)")
    private BigDecimal thickener501ResistanceShutdown4;

    @Schema(description = "502浓缩机阻力开机1(Mpa)")
    @ExcelProperty("502浓缩机阻力开机1(Mpa)")
    private BigDecimal thickener502ResistanceStartup1;
    @Schema(description = "502浓缩机阻力开机2(Mpa)")
    @ExcelProperty("502浓缩机阻力开机2(Mpa)")
    private BigDecimal thickener502ResistanceStartup2;
    @Schema(description = "502浓缩机阻力开机3(Mpa)")
    @ExcelProperty("502浓缩机阻力开机3(Mpa)")
    private BigDecimal thickener502ResistanceStartup3;
    @Schema(description = "502浓缩机阻力开机4(Mpa)")
    @ExcelProperty("502浓缩机阻力开机4(Mpa)")
    private BigDecimal thickener502ResistanceStartup4;
    @Schema(description = "502浓缩机阻力停机1(Mpa)")
    @ExcelProperty("502浓缩机阻力停机1(Mpa)")
    private BigDecimal thickener502ResistanceShutdown1;
    @Schema(description = "502浓缩机阻力停机2(Mpa)")
    @ExcelProperty("502浓缩机阻力停机2(Mpa)")
    private BigDecimal thickener502ResistanceShutdown2;
    @Schema(description = "502浓缩机阻力停机3(Mpa)")
    @ExcelProperty("502浓缩机阻力停机3(Mpa)")
    private BigDecimal thickener502ResistanceShutdown3;
    @Schema(description = "502浓缩机阻力停机4(Mpa)")
    @ExcelProperty("502浓缩机阻力停机4(Mpa)")
    private BigDecimal thickener502ResistanceShutdown4;

    // 生产运行情况
    @Schema(description = "故障时间(小时)")
    @ExcelProperty("故障时间(小时)")
    private BigDecimal faultTimeHours;
    @Schema(description = "运行时间(小时)")
    @ExcelProperty("运行时间(小时)")
    private BigDecimal runningTimeHours;
    @Schema(description = "停车原因")
    @ExcelProperty("停车原因")
    private String shutdownReason;

    // 人员信息
    @Schema(description = "集控员")
    @ExcelProperty("集控员")
    private String centralController;
    @Schema(description = "生产班长")
    @ExcelProperty("生产班长")
    private String productionShiftLeader;
    @Schema(description = "调度主任")
    @ExcelProperty("调度主任")
    private String dispatchDirector;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
