package cn.iocoder.yudao.module.coal.dal.dataobject.sparepartusagerecord;

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
 * 备件使用记录 DO
 *
 * @author 芋道源码
 */
@TableName("coal_spare_part_usage_record")
@KeySequence("coal_spare_part_usage_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparePartUsageRecordDO extends BaseDO {

    /**
     * 使用记录ID
     */
    @TableId
    private Long id;
    /**
     * 备件ID
     */
    private Long sparePartId;
    /**
     * 设备ID
     */
    private Long equipmentId;
    /**
     * 使用类型
     *
     * 枚举 {@link TODO usage_type 对应的类}
     */
    private Integer usageType;
    /**
     * 使用日期
     */
    private LocalDateTime usageDate;
    /**
     * 使用数量
     */
    private BigDecimal quantity;
    /**
     * 操作人ID
     */
    private Long operatorId;
    /**
     * 监督人ID
     */
    private Long supervisorId;
    /**
     * 作业班组
     */
    private String workTeam;
    /**
     * 工单ID(关联检修单)
     */
    private Long workOrderId;
    /**
     * 故障ID(关联故障记录)
     */
    private Long faultId;
    /**
     * 维护计划ID
     */
    private Long maintenancePlanId;
    /**
     * 旧件状态
     *
     * 枚举 {@link TODO old_part_condition 对应的类}
     */
    private Integer oldPartCondition;
    /**
     * 失效模式
     */
    private String failureMode;
    /**
     * 使用时长(天)
     */
    private Integer usageDuration;
    /**
     * 更换原因
     */
    private String replacementReason;
    /**
     * 预计寿命(天)
     */
    private Integer predictedLifespan;
    /**
     * 预计下次更换日期
     */
    private LocalDate nextReplacementDate;
    /**
     * 性能评级
     *
     * 枚举 {@link TODO performance_rating 对应的类}
     */
    private Integer performanceRating;
    /**
     * 单位成本
     */
    private BigDecimal unitCost;
    /**
     * 总成本
     */
    private BigDecimal totalCost;
    /**
     * 人工成本
     */
    private BigDecimal laborCost;
    /**
     * 停机成本
     */
    private BigDecimal downtimeCost;
    /**
     * 备注
     */
    private String remark;


}
