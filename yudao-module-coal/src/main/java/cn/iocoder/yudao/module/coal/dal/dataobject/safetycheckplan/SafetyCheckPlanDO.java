package cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckplan;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 安全检查计划 DO
 *
 * @author 京京
 */
@TableName("coal_safety_check_plan")
@KeySequence("coal_safety_check_plan_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SafetyCheckPlanDO extends BaseDO {

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
     * 枚举 {@link TODO coal_safety_plan_type 对应的类}
     */
    private Integer planType;
    /**
     * 检查分类ID
     */
    private Long checkCategoryId;
    /**
     * 检查周期
     *
     * 枚举 {@link TODO coal_safety_check_cycle 对应的类}
     */
    private Integer checkCycle;
    /**
     * 检查频次
     */
    private Integer checkFrequency;
    /**
     * 负责人ID
     */
    private Long responsiblePersonId;
    /**
     * 负责人姓名
     */
    private String responsiblePersonName;
    /**
     * 检查区域
     */
    private String checkArea;
    /**
     * 检查内容
     */
    private String checkContent;
    /**
     * 检查标准
     */
    private String checkStandard;
    /**
     * 开始日期
     */
    private LocalDate startDate;
    /**
     * 结束日期
     */
    private LocalDate endDate;
    /**
     * 计划状态
     *
     * 枚举 {@link TODO coal_safety_plan_status 对应的类}
     */
    private Integer planStatus;
    /**
     * 审批状态
     *
     * 枚举 {@link TODO coal_safety_approval_status 对应的类}
     */
    private Integer approvalStatus;
    /**
     * 审批人ID
     */
    private Long approverId;
    /**
     * 审批人姓名
     */
    private String approverName;
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
