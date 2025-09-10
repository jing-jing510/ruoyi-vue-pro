package cn.iocoder.yudao.module.coal.dal.dataobject.energyalert;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 能源预警记录 DO
 *
 * @author 芋道源码
 */
@TableName("coal_energy_alert")
@KeySequence("coal_energy_alert_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyAlertDO extends BaseDO {

    /**
     * 预警ID
     */
    @TableId
    private Long id;
    /**
     * 预警编号
     */
    private String alertCode;
    /**
     * 能源类型ID
     */
    private Long energyTypeId;
    /**
     * 预警类型
     *
     * 枚举 {@link TODO coal_energy_alert_type 对应的类}
     */
    private Integer alertType;
    /**
     * 预警级别
     *
     * 枚举 {@link TODO coal_energy_alert_level 对应的类}
     */
    private Integer alertLevel;
    /**
     * 预警标题
     */
    private String alertTitle;
    /**
     * 预警内容
     */
    private String alertContent;
    /**
     * 当前值
     */
    private BigDecimal currentValue;
    /**
     * 阈值
     */
    private BigDecimal thresholdValue;
    /**
     * 偏差率(%)
     */
    private BigDecimal deviationRate;
    /**
     * 预警时间
     */
    private LocalDateTime alertTime;
    /**
     * 预警状态
     *
     * 枚举 {@link TODO coal_energy_alert_status 对应的类}
     */
    private Integer alertStatus;
    /**
     * 处理人ID
     */
    private Long handlerId;
    /**
     * 处理人姓名
     */
    private String handlerName;
    /**
     * 处理时间
     */
    private LocalDateTime handleTime;
    /**
     * 处理结果
     */
    private String handleResult;
    /**
     * 处理备注
     */
    private String handleRemark;


}
