package cn.iocoder.yudao.module.coal.dal.dataobject.productiondailyreport;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 现场生产日报 DO
 *
 * @author 京京
 */
@TableName("coal_production_daily_report")
@KeySequence("coal_production_daily_report_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductionDailyReportDO extends BaseDO {

    /**
     * 日报id
     */
    @TableId
    private Long id;
    /**
     * 日期
     */
    private LocalDateTime reportDate;
    /**
     * 班次ID
     */
    private Long shiftId;
    /**
     * 集控员（操作人）ID
     */
    private Long operatorId;
    /**
     * 带班主任/班长ID
     */
    private Long shiftLeaderId;
    /**
     * 启车时间
     */
    private LocalDateTime startTime;
    /**
     * 带煤时间(分钟)
     */
    private Integer coalFeedingTime;
    /**
     * 停车时间
     */
    private LocalDateTime stopTime;
    /**
     * 有效带煤时间(分钟)
     */
    private Integer effectiveFeedingTime;
    /**
     * 故障影响时间(分钟)
     */
    private Integer faultImpactTime;
    /**
     * 入洗煤量(吨)
     */
    private BigDecimal rawCoalInput;
    /**
     * 小时处理量(吨/小时)
     */
    private BigDecimal hourlyCapacity;
    /**
     * 块精煤产量(吨)
     */
    private BigDecimal blockCleanCoalOutput;
    /**
     * 末精煤产量(吨)
     */
    private BigDecimal fineCleanCoalOutput;
    /**
     * 矸石产量(吨)
     */
    private BigDecimal gangueOutput;
    /**
     * 中煤产量(吨)
     */
    private BigDecimal middlingCoalOutput;
    /**
     * 压滤板次
     */
    private Integer filterPressCycles;
    /**
     * 压滤煤量(吨)
     */
    private BigDecimal filterPressCoalAmount;
    /**
     * 滤布使用量(张)
     */
    private Integer filterClothUsage;
    /**
     * 放舱记录
     */
    private String dischargeRecord;
    /**
     * 挡板添加介质量(kg)
     */
    private BigDecimal baffleMediumAddition;
    /**
     * CaO量(kg)
     */
    private BigDecimal caoAmount;
    /**
     * 絮凝剂(kg)
     */
    private BigDecimal flocculantAmount;
    /**
     * 317密度(kg/L)
     */
    private BigDecimal densityMd317;
    /**
     * 第一次块精煤灰分(%)
     */
    private BigDecimal firstAshBlockClean;
    /**
     * 第一次末精煤灰分(%)
     */
    private BigDecimal firstAshFineClean;
    /**
     * 第一次中煤灰分(%)
     */
    private BigDecimal firstAshMiddling;
    /**
     * 第一次煤泥灰分(%)
     */
    private BigDecimal firstAshSlime;
    /**
     * 第一次矸石灰分(%)
     */
    private BigDecimal firstAshGangue;
    /**
     * 第二次块精煤灰分(%)
     */
    private BigDecimal secondAshBlockClean;
    /**
     * 第二次末精煤灰分(%)
     */
    private BigDecimal secondAshFineClean;
    /**
     * 第二次中煤灰分(%)
     */
    private BigDecimal secondAshMiddling;
    /**
     * 第二次煤泥灰分(%)
     */
    private BigDecimal secondAshSlime;
    /**
     * 第二次矸石灰分(%)
     */
    private BigDecimal secondAshGangue;
    /**
     * 影响时间记录详情
     */
    private String impactTimeRecord;
    /**
     * 交办事项
     */
    private String assignedTasks;
    /**
     * 启车循环水池液位
     */
    private BigDecimal startCirculatingWaterPool;
    /**
     * 启车清水桶液位
     */
    private BigDecimal startCleanWaterTank;
    /**
     * 启车末煤仓位
     */
    private BigDecimal startFineCoalLevel;
    /**
     * 启车粒煤仓位
     */
    private BigDecimal startGranularCoalLevel;
    /**
     * 启车大块仓位
     */
    private BigDecimal startLargeBlockLevel;
    /**
     * 启车中块仓位
     */
    private BigDecimal startMediumBlockLevel;
    /**
     * 启车小块仓位
     */
    private BigDecimal startSmallBlockLevel;
    /**
     * 启车中煤仓位
     */
    private BigDecimal startMiddlingCoalLevel;
    /**
     * 启车矸石仓位
     */
    private BigDecimal startGangueLevel;
    /**
     * 停车循环水池液位
     */
    private BigDecimal stopCirculatingWaterPool;
    /**
     * 停车清水桶液位
     */
    private BigDecimal stopCleanWaterTank;
    /**
     * 停车末煤仓位
     */
    private BigDecimal stopFineCoalLevel;
    /**
     * 停车粒煤仓位
     */
    private BigDecimal stopGranularCoalLevel;
    /**
     * 停车大块仓位
     */
    private BigDecimal stopLargeBlockLevel;
    /**
     * 停车中块仓位
     */
    private BigDecimal stopMediumBlockLevel;
    /**
     * 停车小块仓位
     */
    private BigDecimal stopSmallBlockLevel;
    /**
     * 停车中煤仓位
     */
    private BigDecimal stopMiddlingCoalLevel;
    /**
     * 停车矸石仓位
     */
    private BigDecimal stopGangueLevel;
    /**
     * 备注信息
     */
    private String remarks;
    /**
     * 预留字段1
     */
    private String reservedField1;
    /**
     * 预留字段2
     */
    private String reservedField2;
    /**
     * 预留字段3
     */
    private BigDecimal reservedField3;
    /**
     * 预留字段4
     */
    private BigDecimal reservedField4;
    /**
     * 预留字段5
     */
    private String reservedField5;

    // ========== 新选煤厂字段 ==========
    
    // 皮带秤及产量统计字段
    /**
     * 201原煤皮带接班量(吨)
     */
    private BigDecimal beltScale201RawCoalHandoverIn;
    /**
     * 201原煤皮带交班量(吨)
     */
    private BigDecimal beltScale201RawCoalHandoverOut;
    /**
     * 201原煤皮带当班量(吨)
     */
    private BigDecimal beltScale201RawCoalCurrentShift;
    /**
     * 201原煤皮带月累计(吨)
     */
    private BigDecimal beltScale201RawCoalMonthlyTotal;

    /**
     * 701块精煤皮带秤接班量(吨)
     */
    private BigDecimal beltScale701BlockCleanHandoverIn;
    /**
     * 701块精煤皮带秤交班量(吨)
     */
    private BigDecimal beltScale701BlockCleanHandoverOut;
    /**
     * 701块精煤皮带秤当班量(吨)
     */
    private BigDecimal beltScale701BlockCleanCurrentShift;
    /**
     * 701块精煤皮带秤月累计(吨)
     */
    private BigDecimal beltScale701BlockCleanMonthlyTotal;
    /**
     * 701块精煤当班产率(%)
     */
    private BigDecimal beltScale701BlockCleanCurrentYield;
    /**
     * 701块精煤月度产率(%)
     */
    private BigDecimal beltScale701BlockCleanMonthlyYield;

    /**
     * 702末精煤皮带秤接班量(吨)
     */
    private BigDecimal beltScale702FineCleanHandoverIn;
    /**
     * 702末精煤皮带秤交班量(吨)
     */
    private BigDecimal beltScale702FineCleanHandoverOut;
    /**
     * 702末精煤皮带秤当班量(吨)
     */
    private BigDecimal beltScale702FineCleanCurrentShift;
    /**
     * 702末精煤皮带秤月累计(吨)
     */
    private BigDecimal beltScale702FineCleanMonthlyTotal;
    /**
     * 702末精煤当班产率(%)
     */
    private BigDecimal beltScale702FineCleanCurrentYield;
    /**
     * 702末精煤月度产率(%)
     */
    private BigDecimal beltScale702FineCleanMonthlyYield;

    /**
     * 压滤煤泥接班量(吨)
     */
    private BigDecimal filterPressSlimeHandoverIn;
    /**
     * 压滤煤泥交班量(吨)
     */
    private BigDecimal filterPressSlimeHandoverOut;
    /**
     * 压滤煤泥当班量(吨)
     */
    private BigDecimal filterPressSlimeCurrentShift;
    /**
     * 压滤煤泥月累计(吨)
     */
    private BigDecimal filterPressSlimeMonthlyTotal;
    /**
     * 压滤煤泥当班产率(%)
     */
    private BigDecimal filterPressSlimeCurrentYield;
    /**
     * 压滤煤泥月度产率(%)
     */
    private BigDecimal filterPressSlimeMonthlyYield;

    /**
     * 未入洗末原煤秤接班量(吨)
     */
    private BigDecimal unwashedFineRawCoalHandoverIn;
    /**
     * 未入洗末原煤秤交班量(吨)
     */
    private BigDecimal unwashedFineRawCoalHandoverOut;
    /**
     * 未入洗末原煤秤当班量(吨)
     */
    private BigDecimal unwashedFineRawCoalCurrentShift;
    /**
     * 未入洗末原煤秤月累计(吨)
     */
    private BigDecimal unwashedFineRawCoalMonthlyTotal;
    /**
     * 未入洗末原煤当班产率(%)
     */
    private BigDecimal unwashedFineRawCoalCurrentYield;
    /**
     * 未入洗末原煤月度产率(%)
     */
    private BigDecimal unwashedFineRawCoalMonthlyYield;

    /**
     * 901矸石量当班量(吨)
     */
    private BigDecimal gangue901CurrentShift;
    /**
     * 901矸石量月累计(吨)
     */
    private BigDecimal gangue901MonthlyTotal;
    /**
     * 901矸石当班产率(%)
     */
    private BigDecimal gangue901CurrentYield;
    /**
     * 901矸石月度产率(%)
     */
    private BigDecimal gangue901MonthlyYield;

    /**
     * 商品煤总重当班量(吨)
     */
    private BigDecimal commercialCoalTotalCurrentShift;
    /**
     * 商品煤总重月累计(吨)
     */
    private BigDecimal commercialCoalTotalMonthlyTotal;
    /**
     * 商品煤总重当班产率(%)
     */
    private BigDecimal commercialCoalTotalCurrentYield;
    /**
     * 商品煤总重月度产率(%)
     */
    private BigDecimal commercialCoalTotalMonthlyYield;

    /**
     * 电表数接班读数(kw*h)
     */
    private BigDecimal electricityMeterHandoverIn;
    /**
     * 电表数交班读数(kw*h)
     */
    private BigDecimal electricityMeterHandoverOut;
    /**
     * 电表数当班量(kw*h)
     */
    private BigDecimal electricityMeterCurrentShift;
    /**
     * 电表数月累计(kw*h)
     */
    private BigDecimal electricityMeterMonthlyTotal;
    /**
     * 电表数当班产率(%)
     */
    private BigDecimal electricityMeterCurrentYield;
    /**
     * 电表数月度产率(%)
     */
    private BigDecimal electricityMeterMonthlyYield;

    /**
     * 矿井清水表数接班读数(m³)
     */
    private BigDecimal mineCleanWaterHandoverIn;
    /**
     * 矿井清水表数交班读数(m³)
     */
    private BigDecimal mineCleanWaterHandoverOut;
    /**
     * 矿井清水表数当班量(m³)
     */
    private BigDecimal mineCleanWaterCurrentShift;
    /**
     * 矿井清水表数月累计(m³)
     */
    private BigDecimal mineCleanWaterMonthlyTotal;
    /**
     * 矿井清水当班产率(%)
     */
    private BigDecimal mineCleanWaterCurrentYield;
    /**
     * 矿井清水月度产率(%)
     */
    private BigDecimal mineCleanWaterMonthlyYield;

    // 煤质情况字段
    /**
     * 原煤全水(%)
     */
    private BigDecimal rawCoalTotalMoisture;
    /**
     * 原煤灰分(%)
     */
    private BigDecimal rawCoalAshContent;
    /**
     * 原煤全硫(%)
     */
    private BigDecimal rawCoalTotalSulfur;
    /**
     * 原煤挥发分(%)
     */
    private BigDecimal rawCoalVolatileMatter;
    /**
     * 原煤低位发热量(kcal/kg)
     */
    private BigDecimal rawCoalLowerHeatingValue;

    /**
     * 筒仓大块全水(%)
     */
    private BigDecimal siloLargeLumpsTotalMoisture;
    /**
     * 筒仓大块灰分(%)
     */
    private BigDecimal siloLargeLumpsAshContent;
    /**
     * 筒仓大块全硫(%)
     */
    private BigDecimal siloLargeLumpsTotalSulfur;
    /**
     * 筒仓大块挥发分(%)
     */
    private BigDecimal siloLargeLumpsVolatileMatter;
    /**
     * 筒仓大块低位发热量(kcal/kg)
     */
    private BigDecimal siloLargeLumpsLowerHeatingValue;

    /**
     * 筒仓三八块全水(%)
     */
    private BigDecimal silo38LumpsTotalMoisture;
    /**
     * 筒仓三八块灰分(%)
     */
    private BigDecimal silo38LumpsAshContent;
    /**
     * 筒仓三八块全硫(%)
     */
    private BigDecimal silo38LumpsTotalSulfur;
    /**
     * 筒仓三八块挥发分(%)
     */
    private BigDecimal silo38LumpsVolatileMatter;
    /**
     * 筒仓三八块低位发热量(kcal/kg)
     */
    private BigDecimal silo38LumpsLowerHeatingValue;

    /**
     * 筒仓籽煤全水(%)
     */
    private BigDecimal siloSeedCoalTotalMoisture;
    /**
     * 筒仓籽煤灰分(%)
     */
    private BigDecimal siloSeedCoalAshContent;
    /**
     * 筒仓籽煤全硫(%)
     */
    private BigDecimal siloSeedCoalTotalSulfur;
    /**
     * 筒仓籽煤挥发分(%)
     */
    private BigDecimal siloSeedCoalVolatileMatter;
    /**
     * 筒仓籽煤低位发热量(kcal/kg)
     */
    private BigDecimal siloSeedCoalLowerHeatingValue;

    /**
     * 筒仓沫煤全水(%)
     */
    private BigDecimal siloFineCoalTotalMoisture;
    /**
     * 筒仓沫煤灰分(%)
     */
    private BigDecimal siloFineCoalAshContent;
    /**
     * 筒仓沫煤全硫(%)
     */
    private BigDecimal siloFineCoalTotalSulfur;
    /**
     * 筒仓沫煤挥发分(%)
     */
    private BigDecimal siloFineCoalVolatileMatter;
    /**
     * 筒仓沫煤低位发热量(kcal/kg)
     */
    private BigDecimal siloFineCoalLowerHeatingValue;

    /**
     * 煤泥全水(%)
     */
    private BigDecimal slimeTotalMoisture;
    /**
     * 煤泥灰分(%)
     */
    private BigDecimal slimeAshContent;
    /**
     * 煤泥全硫(%)
     */
    private BigDecimal slimeTotalSulfur;
    /**
     * 煤泥挥发分(%)
     */
    private BigDecimal slimeVolatileMatter;
    /**
     * 煤泥低位发热量(kcal/kg)
     */
    private BigDecimal slimeLowerHeatingValue;

    /**
     * 矸石全水(%)
     */
    private BigDecimal gangueTotalMoisture;
    /**
     * 矸石灰分(%)
     */
    private BigDecimal gangueAshContent;
    /**
     * 矸石全硫(%)
     */
    private BigDecimal gangueTotalSulfur;
    /**
     * 矸石挥发分(%)
     */
    private BigDecimal gangueVolatileMatter;
    /**
     * 矸石低位发热量(kcal/kg)
     */
    private BigDecimal gangueLowerHeatingValue;

    // 生产设置参数
    /**
     * 305浅槽密度最小值(g/cm³)
     */
    private BigDecimal shallowTrough305DensityMin;
    /**
     * 305浅槽密度最大值(g/cm³)
     */
    private BigDecimal shallowTrough305DensityMax;
    /**
     * 321旋流器压力(Mpa)
     */
    private BigDecimal cyclone321Pressure;
    /**
     * 333TSS密度最小值(g/cm³)
     */
    private BigDecimal tss333DensityMin;
    /**
     * 333TSS密度最大值(g/cm³)
     */
    private BigDecimal tss333DensityMax;

    // 生产耗材统计
    /**
     * 电耗损当班量(kw*h)
     */
    private BigDecimal electricityConsumptionCurrentShift;
    /**
     * 污水处理站补水量(m³)
     */
    private BigDecimal wastewaterTreatmentReplenishmentWater;
    /**
     * 磁铁矿粉当班量(吨)
     */
    private BigDecimal magnetitePowderCurrentShift;
    /**
     * 白药-阴离子当班量(kg)
     */
    private BigDecimal whiteReagentAnionicCurrentShift;
    /**
     * 黄药-阳离子当班量(kg)
     */
    private BigDecimal yellowReagentCationicCurrentShift;
    /**
     * 609压滤机开机板数
     */
    private Integer filterPress609StartPlates;
    /**
     * 609压滤机停机板数
     */
    private Integer filterPress609EndPlates;
    /**
     * 610压滤机开机板数
     */
    private Integer filterPress610StartPlates;
    /**
     * 610压滤机停机板数
     */
    private Integer filterPress610EndPlates;

    // 筒仓仓位统计
    /**
     * 原煤仓开机仓位(%)
     */
    private BigDecimal rawCoalSiloStartupPosition;
    /**
     * 原煤仓停机仓位(%)
     */
    private BigDecimal rawCoalSiloShutdownPosition;
    /**
     * 大块煤仓开机仓位(%)
     */
    private BigDecimal largeLumpCoalSiloStartupPosition;
    /**
     * 大块煤仓停机仓位(%)
     */
    private BigDecimal largeLumpCoalSiloShutdownPosition;
    /**
     * 三八块煤仓开机仓位(%)
     */
    private BigDecimal lump38CoalSiloStartupPosition;
    /**
     * 三八块煤仓停机仓位(%)
     */
    private BigDecimal lump38CoalSiloShutdownPosition;
    /**
     * 籽煤仓开机仓位(%)
     */
    private BigDecimal seedCoalSiloStartupPosition;
    /**
     * 籽煤仓停机仓位(%)
     */
    private BigDecimal seedCoalSiloShutdownPosition;
    /**
     * 沫煤仓开机仓位(%)
     */
    private BigDecimal fineCoalSiloStartupPosition;
    /**
     * 沫煤仓停机仓位(%)
     */
    private BigDecimal fineCoalSiloShutdownPosition;

    // 浓缩机阻力
    /**
     * 501浓缩机阻力开机1(Mpa)
     */
    private BigDecimal thickener501ResistanceStartup1;
    /**
     * 501浓缩机阻力开机2(Mpa)
     */
    private BigDecimal thickener501ResistanceStartup2;
    /**
     * 501浓缩机阻力开机3(Mpa)
     */
    private BigDecimal thickener501ResistanceStartup3;
    /**
     * 501浓缩机阻力开机4(Mpa)
     */
    private BigDecimal thickener501ResistanceStartup4;
    /**
     * 501浓缩机阻力停机1(Mpa)
     */
    private BigDecimal thickener501ResistanceShutdown1;
    /**
     * 501浓缩机阻力停机2(Mpa)
     */
    private BigDecimal thickener501ResistanceShutdown2;
    /**
     * 501浓缩机阻力停机3(Mpa)
     */
    private BigDecimal thickener501ResistanceShutdown3;
    /**
     * 501浓缩机阻力停机4(Mpa)
     */
    private BigDecimal thickener501ResistanceShutdown4;

    /**
     * 502浓缩机阻力开机1(Mpa)
     */
    private BigDecimal thickener502ResistanceStartup1;
    /**
     * 502浓缩机阻力开机2(Mpa)
     */
    private BigDecimal thickener502ResistanceStartup2;
    /**
     * 502浓缩机阻力开机3(Mpa)
     */
    private BigDecimal thickener502ResistanceStartup3;
    /**
     * 502浓缩机阻力开机4(Mpa)
     */
    private BigDecimal thickener502ResistanceStartup4;
    /**
     * 502浓缩机阻力停机1(Mpa)
     */
    private BigDecimal thickener502ResistanceShutdown1;
    /**
     * 502浓缩机阻力停机2(Mpa)
     */
    private BigDecimal thickener502ResistanceShutdown2;
    /**
     * 502浓缩机阻力停机3(Mpa)
     */
    private BigDecimal thickener502ResistanceShutdown3;
    /**
     * 502浓缩机阻力停机4(Mpa)
     */
    private BigDecimal thickener502ResistanceShutdown4;

    // 生产运行情况
    /**
     * 故障时间(小时)
     */
    private BigDecimal faultTimeHours;
    /**
     * 运行时间(小时)
     */
    private BigDecimal runningTimeHours;
    /**
     * 停车原因
     */
    private String shutdownReason;

    // 人员信息
    /**
     * 集控员
     */
    private String centralController;
    /**
     * 生产班长
     */
    private String productionShiftLeader;
    /**
     * 调度主任
     */
    private String dispatchDirector;

}
