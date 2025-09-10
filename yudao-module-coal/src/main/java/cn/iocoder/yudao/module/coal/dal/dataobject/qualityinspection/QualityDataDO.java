package cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 质量检测数据 DO
 *
 * @author 京京
 */
@TableName("coal_quality_data")
@KeySequence("coal_quality_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityDataDO extends BaseDO {

    /**
     * 检测数据ID
     */
    @TableId
    private Long id;
    /**
     * 检测记录ID
     */
    private Long inspectionId;
    /**
     * 检测项目ID
     */
    private Long qualityItemId;
    /**
     * 检测项目编码
     */
    private String qualityItemCode;
    /**
     * 检测项目名称
     */
    private String qualityItemName;
    /**
     * 检测值
     */
    private BigDecimal measuredValue;
    /**
     * 计量单位
     */
    private String unit;
    /**
     * 标准值
     */
    private BigDecimal standardValue;
    /**
     * 偏差值
     */
    private BigDecimal deviation;
    /**
     * 偏差率(%)
     */
    private BigDecimal deviationRate;
    /**
     * 是否合格
     */
    private Integer isQualified;
    /**
     * 检测方法
     */
    private String detectionMethod;
    /**
     * 检测设备
     */
    private String detectionEquipment;
    /**
     * 检测时间
     */
    private LocalDateTime detectionTime;
    /**
     * 操作员ID
     */
    private Long operatorId;
    /**
     * 操作员姓名
     */
    private String operatorName;
    /**
     * 复检次数
     */
    private Integer retestCount;
    /**
     * 是否复检
     */
    private Integer isRetest;
    /**
     * 原始检测值（复检时记录）
     */
    private BigDecimal originalValue;
    /**
     * 备注
     */
    private String remark;

}