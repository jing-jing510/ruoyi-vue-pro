package cn.iocoder.yudao.module.coal.dal.dataobject.qualitystandard;

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
 * 质量标准 DO
 *
 * @author 京京
 */
@TableName("coal_quality_standard")
@KeySequence("coal_quality_standard_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityStandardDO extends BaseDO {

    /**
     * 质量标准ID
     */
    @TableId
    private Long id;
    /**
     * 标准编码
     */
    private String standardCode;
    /**
     * 标准名称
     */
    private String standardName;
    /**
     * 产品类型
     *
     * 枚举 {@link TODO coal_quality_product_type 对应的类}
     */
    private Integer productType;
    /**
     * 产品规格
     */
    private String productSpecification;
    /**
     * 检测项目ID
     */
    private Long qualityItemId;
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
     * 允许公差
     */
    private BigDecimal tolerance;
    /**
     * 标准类型
     *
     * 枚举 {@link TODO coal_quality_standard_type 对应的类}
     */
    private Integer standardType;
    /**
     * 标准来源（如：GB/T标准号）
     */
    private String standardSource;
    /**
     * 生效日期
     */
    private LocalDate effectiveDate;
    /**
     * 失效日期
     */
    private LocalDate expiryDate;
    /**
     * 版本号
     */
    private String version;
    /**
     * 状态
     */
    private Integer status;
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
