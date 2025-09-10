package cn.iocoder.yudao.module.coal.dal.dataobject.energystatistics;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 能源消耗统计 DO
 *
 * @author 京京
 */
@TableName("coal_energy_statistics")
@KeySequence("coal_energy_statistics_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyStatisticsDO extends BaseDO {

    /**
     * 统计ID
     */
    @TableId
    private Long id;
    /**
     * 统计日期
     */
    private
    LocalDate statisticsDate;
    /**
     * 统计类型
     *
     * 枚举 {@link TODO coal_energy_statistics_type 对应的类}
     */
    private Integer statisticsType;
    /**
     * 能源类型ID
     */
    private Long energyTypeId;
    /**
     * 总消耗量
     */
    private BigDecimal totalConsumption;
    /**
     * 总成本(元)
     */
    private BigDecimal totalCost;
    /**
     * 平均消耗量
     */
    private BigDecimal averageConsumption;
    /**
     * 峰值消耗量
     */
    private BigDecimal peakConsumption;
    /**
     * 谷值消耗量
     */
    private BigDecimal valleyConsumption;
    /**
     * 消耗效率(%)
     */
    private BigDecimal consumptionEfficiency;
    /**
     * 单位成本(元/单位)
     */
    private BigDecimal costPerUnit;
    /**
     * 与计划对比(%)
     */
    private BigDecimal comparisonWithPlan;
    /**
     * 与上期对比(%)
     */
    private BigDecimal comparisonWithLastPeriod;
    /**
     * 统计状态
     *
     * 枚举 {@link TODO coal_energy_statistics_status 对应的类}
     */
    private Integer statisticsStatus;


}
