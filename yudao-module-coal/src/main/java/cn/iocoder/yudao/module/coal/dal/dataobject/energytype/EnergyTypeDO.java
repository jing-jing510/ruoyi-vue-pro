package cn.iocoder.yudao.module.coal.dal.dataobject.energytype;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 能源类型配置 DO
 *
 * @author 京京
 */
@TableName("coal_energy_type")
@KeySequence("coal_energy_type_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyTypeDO extends BaseDO {

    /**
     * 能源类型ID
     */
    @TableId
    private Long id;
    /**
     * 能源类型编码
     */
    private String typeCode;
    /**
     * 能源类型名称
     */
    private String typeName;
    /**
     * 计量单位
     */
    private String unit;
    /**
     * 单价(元/单位)
     */
    private BigDecimal unitPrice;
    /**
     * 是否实时采集
     *
     * 枚举 {@link TODO coal_energy_is_real_time 对应的类}
     */
    private Boolean isRealTime;
    /**
     * 数据来源
     *
     * 枚举 {@link TODO coal_energy_data_source 对应的类}
     */
    private String dataSource;
    /**
     * 采集间隔(分钟)
     */
    private Integer collectionInterval;
    /**
     * 预警阈值
     */
    private BigDecimal warningThreshold;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态：0禁用 1启用
     *
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;


}
