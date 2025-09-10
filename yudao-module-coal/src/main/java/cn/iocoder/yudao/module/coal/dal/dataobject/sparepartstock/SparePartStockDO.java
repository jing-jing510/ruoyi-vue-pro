package cn.iocoder.yudao.module.coal.dal.dataobject.sparepartstock;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 备件库存记录 DO
 *
 * @author 京京
 */
@TableName("coal_spare_part_stock")
@KeySequence("coal_spare_part_stock_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SparePartStockDO extends BaseDO {

    /**
     * 库存记录ID
     */
    @TableId
    private Long id;
    /**
     * 备件ID
     */
    private Long sparePartId;
    /**
     * 仓库位置
     */
    private String warehouseLocation;
    /**
     * 库存类型
     *
     * 枚举 {@link TODO stock_type 对应的类}
     */
    private Integer stockType;
    /**
     * 库存数量
     */
    private BigDecimal quantity;
    /**
     * 单位成本
     */
    private BigDecimal unitCost;
    /**
     * 总成本
     */
    private BigDecimal totalCost;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 生产日期
     */
    private LocalDate productionDate;
    /**
     * 有效期至
     */
    private LocalDate expiryDate;
    /**
     * 最后入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastInDate;
    /**
     * 最后出库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastOutDate;
    /**
     * 备注
     */
    private String remark;


}
