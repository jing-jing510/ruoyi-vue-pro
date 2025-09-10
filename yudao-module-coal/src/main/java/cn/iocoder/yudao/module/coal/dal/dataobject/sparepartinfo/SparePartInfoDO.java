package cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinfo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 备件基础信息 DO
 *
 * @author 芋道源码
 */
@TableName("coal_spare_part_info")
@KeySequence("coal_spare_part_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparePartInfoDO extends BaseDO {

    /**
     * 备件ID
     */
    @TableId
    private Long id;
    /**
     * 备件编号
     */
    private String sparePartCode;
    /**
     * 备件名称
     */
    private String sparePartName;
    /**
     * 备件分类ID
     */
    private Long categoryId;
    /**
     * 规格型号
     */
    private String specification;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 计量单位
     */
    private String unit;
    /**
     * 备件类型
     *
     * 枚举 {@link TODO spare_part_type 对应的类}
     */
    private Integer sparePartType;
    /**
     * 材质
     */
    private String material;
    /**
     * 重量(kg)
     */
    private BigDecimal weight;
    /**
     * 外形尺寸
     */
    private String dimensions;
    /**
     * 最低库存预警数量
     */
    private BigDecimal minStock;
    /**
     * 最高库存数量
     */
    private BigDecimal maxStock;
    /**
     * 安全库存数量
     */
    private BigDecimal safetyStock;
    /**
     * 当前库存数量
     */
    private BigDecimal currentStock;
    /**
     * 主要供应商
     */
    private String supplier;
    /**
     * 供应商零件号
     */
    private String supplierPartNo;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 采购周期(天)
     */
    private Integer leadTime;
    /**
     * 建议更换周期(天)
     */
    private Integer replacementCycle;
    /**
     * 平均使用寿命(天)
     */
    private Integer averageLifespan;
    /**
     * 使用频率
     *
     * 枚举 {@link TODO usage_frequency 对应的类}
     */
    private Integer usageFrequency;
    /**
     * 存储位置
     */
    private String storageLocation;
    /**
     * 存储条件要求
     */
    private String storageCondition;
    /**
     * 状态
     *
     * 枚举 {@link TODO spare_part_status 对应的类}
     */
    private Integer status;
    /**
     * 是否关键备件
     */
    private Integer isCritical;
    /**
     * 备件图片URL
     */
    private String imageUrl;
    /**
     * 说明书文件URL
     */
    private String manualUrl;
    /**
     * 图纸文件URL
     */
    private String drawingUrl;
    /**
     * 备注
     */
    private String remark;


}
