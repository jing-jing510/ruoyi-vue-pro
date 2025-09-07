package cn.iocoder.yudao.module.coal.dal.dataobject.sparepart;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 备件设备关联 DO
 *
 * @author 芋道源码
 */
@TableName("coal_spare_part_equipment")
@KeySequence("coal_spare_part_equipment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoalSparePartEquipmentDO extends BaseDO {

    /**
     * 关联ID
     */
    @TableId
    private Long id;
    
    /**
     * 备件ID (关联erp_product.id)
     */
    private Long sparePartId;
    
    /**
     * 设备ID (关联coal_equipment_info.id)
     */
    private Long equipmentId;
    
    /**
     * 使用数量
     */
    private Integer usageCount;
    
    /**
     * 是否必需：1是 0否
     */
    private Integer isRequired;
    
    /**
     * 备注
     */
    private String remark;

}
