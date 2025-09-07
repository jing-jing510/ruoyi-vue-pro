package cn.iocoder.yudao.module.coal.dal.dataobject.schedule;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

/**
 * 人员安排 (子表) DO
 *
 * @author 京京
 */
@TableName("coal_schedule_staff")
@KeySequence("coal_schedule_staff_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleStaffDO extends BaseDO {

    /**
     * 人员安排ID
     */
    @TableId
    private Long id;
    /**
     * 排班ID (关联主表)
     */
    private Long scheduleId;
    /**
     * 班次ID (关联coal_shift_system的子节点)
     */
    private Long shiftId;
    /**
     * 员工ID
     */
    private Long userId;
    /**
     * 岗位类型
     *
     * 枚举 {@link TODO position_type 对应的类}
     */
    private Integer positionType;
    /**
     * 是否班组长
     */
    private Boolean isLeader;
    /**
     * 是否替班
     */
    private Boolean isSubstitute;
    /**
     * 替班原因
     */
    private String substituteReason;
    /**
     * 工作状态
     *
     * 枚举 {@link TODO work_status 对应的类}
     */
    private Integer workStatus;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 紧急联系人
     */
    private String emergencyContact;
    /**
     * 紧急联系电话
     */
    private String emergencyPhone;


}
