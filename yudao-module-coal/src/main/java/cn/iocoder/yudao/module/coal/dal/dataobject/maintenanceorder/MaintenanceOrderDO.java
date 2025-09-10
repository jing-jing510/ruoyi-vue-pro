package cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder;

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
 * 检修单 DO
 *
 * @author 京京
 */
@TableName("coal_maintenance_order")
@KeySequence("coal_maintenance_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceOrderDO extends BaseDO {

    /**
     * 检修单ID
     */
    @TableId
    private Long id;
    /**
     * 检修单编号
     */
    private String orderCode;
    /**
     * 关联计划ID
     */
    private Long planId;
    /**
     * 关联报修单ID
     */
    private Long repairRequestId;
    /**
     * 设备ID
     */
    private Long equipmentId;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 检修类型
     *
     * 枚举 {@link TODO maintenance_type 对应的类}
     */
    private Integer maintenanceType;
    /**
     * 检修级别
     *
     * 枚举 {@link TODO maintenance_level 对应的类}
     */
    private Integer maintenanceLevel;
    /**
     * 检修单状态
     *
     * 枚举 {@link TODO maintenance_order_status 对应的类}
     */
    private Integer orderStatus;
    /**
     * 优先级
     *
     * 枚举 {@link TODO priority_level 对应的类}
     */
    private Integer priorityLevel;
    /**
     * 故障描述
     */
    private String faultDescription;
    /**
     * 检修内容
     */
    private String maintenanceContent;
    /**
     * 安全措施
     */
    private String safetyMeasures;
    /**
     * 实际开始时间
     */
    private LocalDateTime startTime;
    /**
     * 实际结束时间
     */
    private LocalDateTime endTime;
    /**
     * 实际工期(小时)
     */
    private Integer actualDuration;
    /**
     * 负责人
     */
    private String responsiblePerson;
    /**
     * 负责班组
     */
    private String responsibleTeam;
    /**
     * 参与人员
     */
    private String participants;
    /**
     * 作业环境
     */
    private String workEnvironment;
    /**
     * 天气条件
     */
    private String weatherCondition;
    /**
     * 完成进度(%)
     */
    private Integer completionRate;
    /**
     * 质量评级
     *
     * 枚举 {@link TODO quality_rating 对应的类}
     */
    private Integer qualityRating;
    /**
     * 安全评级
     *
     * 枚举 {@link TODO safety_rating 对应的类}
     */
    private Integer safetyRating;
    /**
     * 检修结果
     */
    private String maintenanceResult;
    /**
     * 发现问题
     */
    private String problemsFound;
    /**
     * 改进建议
     */
    private String improvementSuggestions;
    /**
     * 下次检修日期
     */
    private LocalDate nextMaintenanceDate;
    /**
     * 总费用
     */
    private BigDecimal totalCost;
    /**
     * 人工费用
     */
    private BigDecimal laborCost;
    /**
     * 材料费用
     */
    private BigDecimal materialCost;
    /**
     * 其他费用
     */
    private BigDecimal otherCost;
    /**
     * 备注
     */
    private String remark;


}
