package cn.iocoder.yudao.module.coal.dal.dataobject.sparepartalert;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 备件预警记录 DO
 *
 * @author 京京
 */
@TableName("coal_spare_part_alert")
@KeySequence("coal_spare_part_alert_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparePartAlertDO extends BaseDO {

    /**
     * 预警ID
     */
    @TableId
    private Long id;
    /**
     * 备件ID
     */
    private Long sparePartId;
    /**
     * 预警类型
     *
     * 枚举 {@link TODO alert_type 对应的类}
     */
    private Integer alertType;
    /**
     * 预警级别
     *
     * 枚举 {@link TODO alert_level 对应的类}
     */
    private Integer alertLevel;
    /**
     * 预警状态
     *
     * 枚举 {@link TODO alert_status 对应的类}
     */
    private Integer alertStatus;
    /**
     * 预警标题
     */
    private String alertTitle;
    /**
     * 预警信息
     */
    private String alertMessage;
    /**
     * 当前库存
     */
    private BigDecimal currentStock;
    /**
     * 阈值
     */
    private BigDecimal thresholdValue;
    /**
     * 关联设备ID(更换提醒时)
     */
    private Long equipmentId;
    /**
     * 处理人ID
     */
    private Long handlerId;
    /**
     * 处理时间
     */
    private LocalDateTime handleTime;
    /**
     * 处理措施
     */
    private String handleAction;
    /**
     * 处理备注
     */
    private String handleRemark;
    /**
     * 是否已发送通知：1是 0否
     */
    private Boolean isSent;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 接收人列表(逗号分隔)
     */
    private String recipients;


}
