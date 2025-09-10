package cn.iocoder.yudao.module.coal.dal.dataobject.safetyaccident;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 安全事故记录 DO
 *
 * @author 芋道源码
 */
@TableName("coal_safety_accident")
@KeySequence("coal_safety_accident_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SafetyAccidentDO extends BaseDO {

    /**
     * 事故ID
     */
    @TableId
    private Long id;
    /**
     * 事故编号
     */
    private String accidentCode;
    /**
     * 事故类型
     *
     * 枚举 {@link TODO coal_safety_accident_type 对应的类}
     */
    private Integer accidentType;
    /**
     * 事故等级
     *
     * 枚举 {@link TODO coal_safety_accident_level 对应的类}
     */
    private Integer accidentLevel;
    /**
     * 事故标题
     */
    private String accidentTitle;
    /**
     * 事故发生时间
     */
    private LocalDateTime accidentTime;
    /**
     * 事故地点
     */
    private String accidentLocation;
    /**
     * 天气情况
     */
    private String weatherCondition;
    /**
     * 事故描述
     */
    private String accidentDescription;
    /**
     * 事故原因
     */
    private String accidentCause;
    /**
     * 伤亡人数
     */
    private Integer casualtiesCount;
    /**
     * 经济损失(元)
     */
    private BigDecimal economicLoss;
    /**
     * 设备损坏情况
     */
    private String equipmentDamage;
    /**
     * 环境影响
     */
    private String environmentalImpact;
    /**
     * 应急响应措施
     */
    private String emergencyResponse;
    /**
     * 救援过程
     */
    private String rescueProcess;
    /**
     * 报告人ID
     */
    private Long reporterId;
    /**
     * 报告人姓名
     */
    private String reporterName;
    /**
     * 报告时间
     */
    private LocalDateTime reportTime;
    /**
     * 调查人ID
     */
    private Long investigatorId;
    /**
     * 调查人姓名
     */
    private String investigatorName;
    /**
     * 调查开始时间
     */
    private LocalDateTime investigationStartTime;
    /**
     * 调查结束时间
     */
    private LocalDateTime investigationEndTime;
    /**
     * 调查结果
     */
    private String investigationResult;
    /**
     * 预防措施
     */
    private String preventiveMeasures;
    /**
     * 责任人ID
     */
    private Long responsiblePersonId;
    /**
     * 责任人姓名
     */
    private String responsiblePersonName;
    /**
     * 处罚措施
     */
    private String punishmentMeasures;
    /**
     * 事故状态
     *
     * 枚举 {@link TODO coal_safety_accident_status 对应的类}
     */
    private Integer accidentStatus;
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
