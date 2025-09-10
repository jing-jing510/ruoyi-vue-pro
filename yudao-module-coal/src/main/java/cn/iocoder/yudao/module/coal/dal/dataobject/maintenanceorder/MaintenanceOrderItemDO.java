package cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.bind.v2.TODO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 检修项目明细 DO
 *
 * @author 芋道源码
 */
@TableName("coal_maintenance_order_item")
@KeySequence("coal_maintenance_order_item_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceOrderItemDO extends BaseDO {

    /**
     * 明细ID
     */
    @TableId
    private Long id;
    /**
     * 检修单ID
     */
    private Long orderId;
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 项目类型
     *
     * 枚举 {@link TODO maintenance_item_type 对应的类}
     */
    private Integer itemType;
    /**
     * 项目描述
     */
    private String itemDescription;
    /**
     * 作业标准
     */
    private String workStandard;
    /**
     * 安全要求
     */
    private String safetyRequirements;
    /**
     * 所需工具
     */
    private String requiredTools;
    /**
     * 所需材料
     */
    private String requiredMaterials;
    /**
     * 预计工时(小时)
     */
    private Integer estimatedDuration;
    /**
     * 实际工时(小时)
     */
    private Integer actualDuration;
    /**
     * 项目状态
     *
     * 枚举 {@link TODO maintenance_item_status 对应的类}
     */
    private Integer itemStatus;
    /**
     * 完成时间
     */
    private LocalDateTime completionTime;
    /**
     * 完成质量
     *
     * 枚举 {@link TODO completion_quality 对应的类}
     */
    private Integer completionQuality;
    /**
     * 完成说明
     */
    private String completionNotes;
    /**
     * 检查人
     */
    private String inspector;
    /**
     * 检查时间
     */
    private LocalDateTime inspectionTime;
    /**
     * 检查结果
     */
    private String inspectionResult;
    /**
     * 备注
     */
    private String remark;


}
