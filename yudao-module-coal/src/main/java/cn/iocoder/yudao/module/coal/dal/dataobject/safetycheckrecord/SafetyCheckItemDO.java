package cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckrecord;

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
 * 安全检查项目 DO
 *
 * @author 京京
 */
@TableName("coal_safety_check_item")
@KeySequence("coal_safety_check_item_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SafetyCheckItemDO extends BaseDO {

    /**
     * 项目ID
     */
    @TableId
    private Long id;
    /**
     * 检查记录ID
     */
    private Long recordId;
    /**
     * 检查项目名称
     */
    private String itemName;
    /**
     * 检查项目编码
     */
    private String itemCode;
    /**
     * 检查标准
     */
    private String checkStandard;
    /**
     * 检查结果
     *
     * 枚举 {@link TODO coal_safety_check_result 对应的类}
     */
    private Integer checkResult;
    /**
     * 检查得分
     */
    private BigDecimal checkScore;
    /**
     * 满分
     */
    private BigDecimal maxScore;
    /**
     * 问题描述
     */
    private String problemDescription;
    /**
     * 风险等级
     *
     * 枚举 {@link TODO coal_safety_risk_level 对应的类}
     */
    private Integer riskLevel;
    /**
     * 是否需要整改
     *
     * 枚举 {@link TODO coal_safety_rectification_status 对应的类}
     */
    private Boolean rectificationRequired;
    /**
     * 整改期限
     */
    private LocalDate rectificationDeadline;
    /**
     * 整改负责人ID
     */
    private Long responsiblePersonId;
    /**
     * 整改负责人姓名
     */
    private String responsiblePersonName;
    /**
     * 整改状态
     *
     * 枚举 {@link TODO coal_safety_rectification_status 对应的类}
     */
    private Integer rectificationStatus;
    /**
     * 整改结果
     */
    private String rectificationResult;
    /**
     * 整改完成时间
     */
    private LocalDateTime rectificationTime;
    /**
     * 验证人ID
     */
    private Long verifierId;
    /**
     * 验证人姓名
     */
    private String verifierName;
    /**
     * 验证时间
     */
    private LocalDateTime verificationTime;
    /**
     * 验证结果
     */
    private String verificationResult;
    /**
     * 备注
     */
    private String remark;

}