package cn.iocoder.yudao.module.coal.dal.dataobject.schedule;

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
 * 排班管理 (主表) DO
 *
 * @author 京京
 */
@TableName("coal_schedule")
@KeySequence("coal_schedule_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDO extends BaseDO {

    /**
     * 排班ID
     */
    @TableId
    private Long id;
    /**
     * 排班日期
     */
    private LocalDate scheduleDate;
    /**
     * 班制ID
     */
    private Long shiftSystemId;
    /**
     * 排班状态
     *
     * 枚举 {@link TODO schedule_status 对应的类}
     */
    private Integer scheduleStatus;
    /**
     * 是否生产日
     *
     * 枚举 {@link TODO is_production_day 对应的类}
     */
    private Boolean isProductionDay;
    /**
     * 当日生产目标(吨)
     */
    private BigDecimal productionTarget;
    /**
     * 排班类型
     *
     * 枚举 {@link TODO schedule_type 对应的类}
     */
    private Integer scheduleType;
    /**
     * 审批人ID
     */
    private Long approverId;
    /**
     * 审批时间
     */
    private LocalDateTime approveTime;
    /**
     * 备注
     */
    private String remark;


}
