package cn.iocoder.yudao.module.coal.dal.dataobject.repairrequest;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 报修单 DO
 *
 * @author 芋道源码
 */
@TableName("coal_repair_request")
@KeySequence("coal_repair_request_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepairRequestDO extends BaseDO {

    /**
     * 报修单ID
     */
    @TableId
    private Long id;
    /**
     * 报修单编号
     */
    private String requestCode;
    /**
     * 设备ID
     */
    private Long equipmentId;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 设备位置
     */
    private String equipmentLocation;
    /**
     * 故障类型
     *
     * 枚举 {@link TODO fault_type 对应的类}
     */
    private Integer faultType;
    /**
     * 故障级别
     *
     * 枚举 {@link TODO fault_level 对应的类}
     */
    private Integer faultLevel;
    /**
     * 故障描述
     */
    private String faultDescription;
    /**
     * 故障现象
     */
    private String faultSymptoms;
    /**
     * 故障原因分析
     */
    private String faultCause;
    /**
     * 影响评估
     */
    private String impactAssessment;
    /**
     * 紧急程度
     *
     * 枚举 {@link TODO urgency_level 对应的类}
     */
    private Integer urgencyLevel;
    /**
     * 报修状态
     *
     * 枚举 {@link TODO repair_request_status 对应的类}
     */
    private Integer requestStatus;
    /**
     * 报修人ID
     */
    private Long reporterId;
    /**
     * 报修人姓名
     */
    private String reporterName;
    /**
     * 报修人电话
     */
    private String reporterPhone;
    /**
     * 报修时间
     */
    private LocalDateTime reportTime;
    /**
     * 故障照片(JSON格式)
     */
    private String faultPhotos;
    /**
     * 故障视频(JSON格式)
     */
    private String faultVideos;
    /**
     * 故障语音(JSON格式)
     */
    private String faultAudio;
    /**
     * 指派处理人
     */
    private String assignedPerson;
    /**
     * 指派班组
     */
    private String assignedTeam;
    /**
     * 派单时间
     */
    private LocalDateTime assignmentTime;
    /**
     * 预计修复时间
     */
    private LocalDateTime expectedRepairTime;
    /**
     * 实际开始时间
     */
    private LocalDateTime actualStartTime;
    /**
     * 实际结束时间
     */
    private LocalDateTime actualEndTime;
    /**
     * 修复耗时(小时)
     */
    private Integer repairDuration;
    /**
     * 修复方法
     */
    private String repairMethod;
    /**
     * 更换部件(JSON格式)
     */
    private String replacedParts;
    /**
     * 修复费用
     */
    private BigDecimal repairCost;
    /**
     * 修复质量
     *
     * 枚举 {@link TODO repair_quality 对应的类}
     */
    private Integer repairQuality;
    /**
     * 测试结果
     */
    private String testResult;
    /**
     * 预防措施
     */
    private String preventionMeasures;
    /**
     * 满意度评价
     *
     * 枚举 {@link TODO satisfaction_rating 对应的类}
     */
    private Integer satisfactionRating;
    /**
     * 反馈意见
     */
    private String feedbackNotes;
    /**
     * 关闭时间
     */
    private LocalDateTime closeTime;
    /**
     * 关闭原因
     */
    private String closeReason;
    /**
     * 备注
     */
    private String remark;


}