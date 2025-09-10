package cn.iocoder.yudao.module.coal.dal.dataobject.qualityitem;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 质量检测项目 DO
 *
 * @author 京京
 */
@TableName("coal_quality_item")
@KeySequence("coal_quality_item_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityItemDO extends BaseDO {

    /**
     * 检测项目ID
     */
    @TableId
    private Long id;
    /**
     * 检测项目编码
     */
    private String itemCode;
    /**
     * 检测项目名称
     */
    private String itemName;
    /**
     * 检测类型
     *
     * 枚举 {@link TODO coal_quality_detection_type 对应的类}
     */
    private Integer itemType;
    /**
     * 计量单位
     */
    private String unit;
    /**
     * 检测方法
     */
    private String detectionMethod;
    /**
     * 所需设备
     */
    private String equipmentRequired;
    /**
     * 标准值
     */
    private BigDecimal standardValue;
    /**
     * 最大允许值
     */
    private BigDecimal maxValue;
    /**
     * 最小允许值
     */
    private BigDecimal minValue;
    /**
     * 预警上限
     */
    private BigDecimal warningUpperLimit;
    /**
     * 预警下限
     */
    private BigDecimal warningLowerLimit;
    /**
     * 精度位数
     */
    private Integer precisionDigits;
    /**
     * 显示顺序
     */
    private Integer sort;
    /**
     * 状态
     *
     * 枚举 {@link TODO coal_quality_inspection_status 对应的类}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;


}
