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
 * 安全检查记录 DO
 *
 * @author 京京
 */
@TableName("coal_safety_check_record")
@KeySequence("coal_safety_check_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SafetyCheckRecordDO extends BaseDO {

    /**
     * 记录ID
     */
    @TableId
    private Long id;
    /**
     * 记录编号
     */
    private String recordCode;
    /**
     * 计划ID
     */
    private Long planId;
    /**
     * 检查类型
     *
     * 枚举 {@link TODO coal_safety_check_type 对应的类}
     */
    private Integer checkType;
    /**
     * 检查分类ID
     */
    private Long checkCategoryId;
    /**
     * 检查日期
     */
    private LocalDate checkDate;
    /**
     * 检查时间
     */
    private LocalDateTime checkTime;
    /**
     * 检查人ID
     */
    private Long checkerId;
    /**
     * 检查人姓名
     */
    private String checkerName;
    /**
     * 检查区域
     */
    private String checkArea;
    /**
     * 天气情况
     */
    private String weatherCondition;
    /**
     * 温度(℃)
     */
    private BigDecimal temperature;
    /**
     * 湿度(%)
     */
    private BigDecimal humidity;
    /**
     * 检查总结
     */
    private String checkSummary;
    /**
     * 检查项目总数
     */
    private Integer totalItems;
    /**
     * 合格项目数
     */
    private Integer qualifiedItems;
    /**
     * 不合格项目数
     */
    private Integer unqualifiedItems;
    /**
     * 合格率(%)
     */
    private BigDecimal qualifiedRate;
    /**
     * 记录状态
     *
     * 枚举 {@link TODO coal_safety_record_status 对应的类}
     */
    private Integer recordStatus;
    /**
     * 提交时间
     */
    private LocalDateTime submitTime;
    /**
     * 审核人ID
     */
    private Long auditorId;
    /**
     * 审核人姓名
     */
    private String auditorName;
    /**
     * 审核时间
     */
    private LocalDateTime auditTime;
    /**
     * 审核备注
     */
    private String auditRemark;
    /**
     * 备注
     */
    private String remark;


}
