package cn.iocoder.yudao.module.erp.dal.dataobject.product;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * ERP 产品 DO
 *
 * @author 芋道源码
 */
@TableName("erp_product")
@KeySequence("erp_product_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErpProductDO extends BaseDO {

    /**
     * 产品编号
     */
    @TableId
    private Long id;
    /**
     * 产品名称
     */
    private String name;
    /**
     * 产品条码
     */
    private String barCode;
    /**
     * 产品分类编号
     *
     * 关联 {@link ErpProductCategoryDO#getId()}
     */
    private Long categoryId;
    /**
     * 单位编号
     *
     * 关联 {@link ErpProductUnitDO#getId()}
     */
    private Long unitId;
    /**
     * 产品状态
     *
     * 枚举 {@link cn.iocoder.yudao.framework.common.enums.CommonStatusEnum}
     */
    private Integer status;
    /**
     * 产品规格
     */
    private String standard;
    /**
     * 产品备注
     */
    private String remark;
    /**
     * 保质期天数
     */
    private Integer expiryDay;
    /**
     * 基础重量（kg）
     */
    private BigDecimal weight;
    /**
     * 采购价格，单位：元
     */
    private BigDecimal purchasePrice;
    /**
     * 销售价格，单位：元
     */
    private BigDecimal salePrice;
    /**
     * 最低价格，单位：元
     */
    private BigDecimal minPrice;

    // ========== 备件管理扩展字段 ==========
    
    /**
     * 关联设备ID (关联coal_equipment_info.id)
     */
    @TableField("equipment_id")
    private Long equipmentId;
    
    /**
     * 备件类型：1易损件 2关键件 3标准件
     */
    @TableField("spare_part_type")
    private Integer sparePartType;
    
    /**
     * 最低库存预警数量
     */
    @TableField("min_stock")
    private BigDecimal minStock;
    
    /**
     * 最高库存数量
     */
    @TableField("max_stock")
    private BigDecimal maxStock;
    
    /**
     * 安全库存数量
     */
    @TableField("safety_stock")
    private BigDecimal safetyStock;
    
    /**
     * 主要供应商ID (关联erp_supplier.id)
     */
    @TableField("supplier_id")
    private Long supplierId;
    
    /**
     * 供应商名称
     */
    @TableField("supplier_name")
    private String supplierName;
    
    /**
     * 更换周期(天)
     */
    @TableField("replacement_cycle")
    private Integer replacementCycle;
    
    /**
     * 最后更换日期
     */
    @TableField("last_replacement_date")
    private LocalDate lastReplacementDate;
    
    /**
     * 下次更换日期
     */
    @TableField("next_replacement_date")
    private LocalDate nextReplacementDate;

}