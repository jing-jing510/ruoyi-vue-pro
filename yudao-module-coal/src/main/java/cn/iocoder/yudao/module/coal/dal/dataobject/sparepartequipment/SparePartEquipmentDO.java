package cn.iocoder.yudao.module.coal.dal.dataobject.sparepartequipment;

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
public class SparePartEquipmentDO extends BaseDO {

    /**
     * 关联ID
     */
    @TableId
    private Long id;
    /**
     * 备件ID
     */
    private Long sparePartId;
    /**
     * 设备ID
     */
    private Long equipmentId;
    /**
     * 单次使用数量
     */
    private Integer usageCount;
    /**
     * 安装位置
     */
    private String installPosition;
    /**
     * 是否必需：1必需 0非必需
     */
    private Integer isRequired;
    /**
     * 更换难度：1容易 2一般 3困难 4很困难
     */
    private Integer replacementDifficulty;
    /**
     * 更换时间(分钟)
     */
    private Integer replacementTime;
    /**
     * 所需工具
     */
    private String toolsRequired;
    /**
     * 安全要求
     */
    private String safetyRequirements;
    /**
     * 备注
     */
    private String remark;

}
