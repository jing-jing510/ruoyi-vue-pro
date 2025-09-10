package cn.iocoder.yudao.module.coal.dal.dataobject.energyconsumption;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 能源消耗记录 DO
 *
 * @author 京京
 */
@TableName("coal_energy_consumption")
@KeySequence("coal_energy_consumption_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyConsumptionDO extends BaseDO {

    /**
     * 消耗记录ID
     */
    @TableId
    private Long id;
    /**
     * 记录编号
     */
    private String recordCode;
    /**
     * 能源类型ID
     */
    private Long energyTypeId;
    /**
     * 消耗日期
     */
    private LocalDate consumptionDate;
    /**
     * 班次ID
     */
    private Long shiftId;
    /**
     * 消耗量
     */
    private BigDecimal consumptionValue;
    /**
     * 单位成本(元/单位)
     */
    private BigDecimal unitCost;
    /**
     * 总成本(元)
     */
    private BigDecimal totalCost;
    /**
     * 数据来源
     *
     * 枚举 {@link TODO coal_energy_data_source 对应的类}
     */
    private Integer dataSource;
    /**
     * 采集时间
     */
    private LocalDateTime collectionTime;
    /**
     * 记录人ID
     */
    private Long recorderId;
    /**
     * 记录人姓名
     */
    private String recorderName;
    /**
     * 验证状态
     *
     * 枚举 {@link TODO coal_energy_verification_status 对应的类}
     */
    private Integer verificationStatus;
    /**
     * 验证人ID
     */
    private Long verifierId;
    /**
     * 验证时间
     */
    private LocalDateTime verificationTime;
    /**
     * 验证备注
     */
    private String verificationRemark;
    /**
     * 备注
     */
    private String remark;


}
