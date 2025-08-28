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


}
