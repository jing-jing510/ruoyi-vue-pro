package cn.iocoder.yudao.module.coal.dal.dataobject.qualityalert;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 质量预警记录 DO
 *
 * @author 京京
 */
@TableName("coal_quality_alert")
@KeySequence("coal_quality_alert_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityAlertDO extends BaseDO {

    /**
     * 预警记录ID
     */
    @TableId
    private Long id;
    /**
     * 预警编号
     */
    private String alertCode;
    /**
     * 预警类型
     *
     * 枚举 {@link TODO coal_quality_alert_type 对应的类}
     */
    private Integer alertType;
    /**
     * 预警级别
     *
     * 枚举 {@link TODO coal_quality_alert_level 对应的类}
     */
    private Integer alertLevel;
    /**
     * 检测项目ID
     */
    private Long qualityItemId;
    /**
     * 关联检测记录ID
     */
    private Long inspectionId;
    /**
     * 产品类型
     *
     * 枚举 {@link TODO coal_quality_product_type 对应的类}
     */
    private Integer productType;
    /**
     * 检测值
     */
    private BigDecimal measuredValue;
    /**
     * 标准值
     */
    private BigDecimal standardValue;
    /**
     * 预警阈值
     */
    private BigDecimal thresholdValue;
    /**
     * 偏差值
     */
    private BigDecimal deviation;
    /**
     * 预警信息
     */
    private String alertMessage;
    /**
     * 预警时间
     */
    private LocalDateTime alertTime;
    /**
     * 预警状态
     *
     * 枚举 {@link TODO coal_quality_alert_status 对应的类}
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
     * 处理措施
     */
    private String handleMethod;
    /**
     * 处理结果
     */
    private String handleResult;
    /**
     * 是否自动预警：0手动 1自动
     */
    private Integer isAutoAlert;
    /**
     * 是否已发送通知：0否 1是
     */
    private Integer notificationSent;
    /**
     * 通知发送时间
     */
    private LocalDateTime notificationTime;
    /**
     * 接收人列表(逗号分隔)
     */
    private String recipients;
    /**
     * 备注
     */
    private String remark;


}
