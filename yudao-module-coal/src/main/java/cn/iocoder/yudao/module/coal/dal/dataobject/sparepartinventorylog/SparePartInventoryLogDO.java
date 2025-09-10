package cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinventorylog;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 备件出入库记录 DO
 *
 * @author 京京
 */
@TableName("coal_spare_part_inventory_log")
@KeySequence("coal_spare_part_inventory_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparePartInventoryLogDO extends BaseDO {

    /**
     * 记录ID
     */
    @TableId
    private Long id;
    /**
     * 备件ID
     */
    private Long sparePartId;
    /**
     * 操作类型
     *
     * 枚举 {@link TODO operation_type 对应的类}
     */
    private Integer operationType;
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operationDate;
    /**
     * 数量(正数入库，负数出库)
     */
    private BigDecimal quantity;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 操作人ID
     */
    private Long operatorId;
    /**
     * 仓库位置
     */
    private String warehouseLocation;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 关联设备ID(出库时)
     */
    private Long equipmentId;
    /**
     * 关联工单ID
     */
    private Long workOrderId;
    /**
     * 供应商ID(入库时)
     */
    private Long supplierId;
    /**
     * 采购单号
     */
    private String purchaseOrderNo;
    /**
     * 审批人ID
     */
    private Long approverId;
    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approveTime;
    /**
     * 审批状态
     *
     * 枚举 {@link TODO approve_status 对应的类}
     */
    private Integer approveStatus;
    /**
     * 操作原因
     */
    private String operationReason;
    /**
     * 备注
     */
    private String remark;


}
