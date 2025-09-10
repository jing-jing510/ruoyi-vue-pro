package cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 质量检测记录 DO
 *
 * @author 京京
 */
@TableName("coal_quality_inspection")
@KeySequence("coal_quality_inspection_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityInspectionDO extends BaseDO {

    /**
     * 检测记录ID
     */
    @TableId
    private Long id;
    /**
     * 检测编号
     */
    private String inspectionCode;
    /**
     * 检测时间
     */
    private LocalDateTime inspectionDate;
    /**
     * 班次ID
     */
    private Long shiftId;
    /**
     * 检测人员ID
     */
    private Long inspectorId;
    /**
     * 检测人员姓名
     */
    private String inspectorName;
    /**
     * 产品类型
     */
    private Integer productType;
    /**
     * 产品规格
     */
    private String productSpecification;
    /**
     * 采样地点
     */
    private String samplingLocation;
    /**
     * 采样方法
     *
     * 枚举 {@link TODO coal_quality_sampling_method 对应的类}
     */
    private Integer samplingMethod;
    /**
     * 样品编号
     */
    private String sampleCode;
    /**
     * 检测类型
     *
     * 枚举 {@link TODO coal_quality_detection_type 对应的类}
     */
    private Integer detectionType;
    /**
     * 系统位置
     */
    private String systemLocation;
    /**
     * 批次号
     */
    private String batchNumber;
    /**
     * 检测状态
     *
     * 枚举 {@link TODO coal_quality_inspection_status 对应的类}
     */
    private Integer inspectionStatus;
    /**
     * 审核人员ID
     */
    private Long reviewerId;
    /**
     * 审核人员姓名
     */
    private String reviewerName;
    /**
     * 审核时间
     */
    private LocalDateTime reviewTime;
    /**
     * 审核状态
     *
     * 枚举 {@link TODO coal_quality_review_status 对应的类}
     */
    private Integer reviewStatus;
    /**
     * 审核备注
     */
    private String reviewRemark;
    /**
     * 环境温度(℃)
     */
    private BigDecimal temperature;
    /**
     * 环境湿度(%)
     */
    private BigDecimal humidity;
    /**
     * 天气情况
     */
    private String weatherCondition;
    /**
     * 备注信息
     */
    private String remark;


}
