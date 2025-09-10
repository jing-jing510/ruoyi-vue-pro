package cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceplan;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 检修计划 DO
 *
 * @author 京京
 */
@TableName("coal_maintenance_plan")
@KeySequence("coal_maintenance_plan_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenancePlanDO extends BaseDO {

    /**
     * 计划ID
     */
    @TableId
    private Long id;
    /**
     * 计划编号
     */
    private String planCode;
    /**
     * 计划名称
     */
    private String planName;
    /**
     * 计划类型
     *
     * 枚举 {@link TODO plan_type 对应的类}
     */
    private Integer planType;
    /**
     * 计划状态
     *
     * 枚举 {@link TODO plan_status 对应的类}
     */
    private Integer planStatus;
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
     * 计划开始时间
     */
    private LocalDateTime plannedStartTime;
    /**
     * 计划结束时间
     */
    private LocalDateTime plannedEndTime;
    /**
     * 预计工期(小时)
     */
    private Integer estimatedDuration;
    /**
     * 负责人
     */
    private String responsiblePerson;
    /**
     * 负责班组
     */
    private String responsibleTeam;
    /**
     * 检修内容
     */
    private String maintenanceContent;
    /**
     * 安全要求
     */
    private String safetyRequirements;
    /**
     * 所需工具
     */
    private String requiredTools;
    /**
     * 所需材料
     */
    private String requiredMaterials;
    /**
     * 预算费用
     */
    private BigDecimal budgetCost;
    /**
     * 实际费用
     */
    private BigDecimal actualCost;
    /**
     * 审批状态
     *
     * 枚举 {@link TODO approve_status 对应的类}
     */
    private Integer approvalStatus;
    /**
     * 审批人ID
     */
    private Long approverId;
    /**
     * 审批时间
     */
    private LocalDateTime approvalTime;
    /**
     * 审批备注
     */
    private String approvalRemark;
    /**
     * 备注
     */
    private String remark;


}
