package cn.iocoder.yudao.module.coal.dal.dataobject.sparepart;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 备件预警记录 DO
 *
 * @author 芋道源码
 */
@TableName("coal_spare_part_alert")
@KeySequence("coal_spare_part_alert_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoalSparePartAlertDO extends BaseDO {

    /**
     * 预警ID
     */
    @TableId
    private Long id;
    
    /**
     * 备件ID (关联erp_product.id)
     */
    private Long sparePartId;
    
    /**
     * 仓库ID (关联erp_warehouse.id)
     */
    private Long warehouseId;
    
    /**
     * 预警类型：1库存不足 2库存过多 3到期提醒 4更换提醒
     */
    private Integer alertType;
    
    /**
     * 当前库存
     */
    private BigDecimal currentStock;
    
    /**
     * 阈值
     */
    private BigDecimal thresholdValue;
    
    /**
     * 预警级别：1低 2中 3高
     */
    private Integer alertLevel;
    
    /**
     * 预警状态：1待处理 2已处理 3已忽略
     */
    private Integer alertStatus;
    
    /**
     * 预警信息
     */
    private String alertMessage;
    
    /**
     * 处理人ID
     */
    private Long handlerId;
    
    /**
     * 处理时间
     */
    private LocalDateTime handleTime;
    
    /**
     * 处理备注
     */
    private String handleRemark;

}
