package cn.iocoder.yudao.module.coal.dal.dataobject.equipmentcategory;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

/**
 * 设备分类表 (树表) DO
 *
 * @author 芋道源码
 */
@TableName("coal_equipment_category")
@KeySequence("coal_equipment_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentCategoryDO extends BaseDO {

    public static final Long PARENT_ID_ROOT = 0L;

    /**
     * 分类ID
     */
    @TableId
    private Long id;
    /**
     * 父分类ID
     */
    private Long parentId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 分类编码
     */
    private String categoryCode;
    /**
     * 分类层级
     *
     * 枚举 {@link TODO equipment_category_level 对应的类}
     */
    private Integer categoryLevel;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态
     *
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;


}
