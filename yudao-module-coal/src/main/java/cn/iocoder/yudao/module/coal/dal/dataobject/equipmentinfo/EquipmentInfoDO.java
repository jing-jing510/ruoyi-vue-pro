package cn.iocoder.yudao.module.coal.dal.dataobject.equipmentinfo;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 设备档案 DO
 *
 * @author 京京
 */
@TableName("coal_equipment_info")
@KeySequence("coal_equipment_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentInfoDO extends BaseDO {

    public static final Long PARENT_EQUIPMENT_ID_ROOT = 0L;

    /**
     * 设备ID
     */
    @TableId
    private Long id;
    /**
     * 设备编号
     */
    private String equipmentCode;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 设备分类ID
     */
    private Long categoryId;
    /**
     * 父设备ID
     */
    private Long parentEquipmentId;
    /**
     * 设备型号
     */
    private String model;
    /**
     * 制造厂商
     */
    private String manufacturer;
    /**
     * 制造日期
     */
    private LocalDate manufactureDate;
    /**
     * 安装日期
     */
    private LocalDate installDate;
    /**
     * 投产日期
     */
    private LocalDate commissionDate;
    /**
     * 资产编号
     */
    private String assetNumber;
    /**
     * 额定功率(kW)
     */
    private BigDecimal ratedPower;
    /**
     * 额定处理能力(t/h)
     */
    private BigDecimal ratedCapacity;
    /**
     * 设备重量(t)
     */
    private BigDecimal weight;
    /**
     * 外形尺寸(长x宽x高)
     */
    private String dimensions;
    /**
     * 电压等级
     */
    private String voltageLevel;
    /**
     * 防护等级
     */
    private String protectionLevel;
    /**
     * 所属车间
     */
    private String workshop;
    /**
     * 安装位置
     */
    private String location;
    /**
     * X坐标
     */
    private BigDecimal coordinateX;
    /**
     * Y坐标
     */
    private BigDecimal coordinateY;
    /**
     * 设备状态
     *
     * 枚举 {@link TODO equipment_status 对应的类}
     */
    private Integer equipmentStatus;
    /**
     * 健康等级
     *
     * 枚举 {@link TODO equipment_health_level 对应的类}
     */
    private Integer healthLevel;
    /**
     * 重要度
     *
     * 枚举 {@link TODO equipment_importance_level 对应的类}
     */
    private Integer importanceLevel;
    /**
     * 责任人ID
     */
    private Long responsiblePersonId;
    /**
     * 维护人ID
     */
    private Long maintenancePersonId;
    /**
     * 操作人ID
     */
    private Long operatorPersonId;
    /**
     * 二维码内容
     */
    private String qrCode;
    /**
     * 二维码图片URL
     */
    private String qrCodeUrl;
    /**
     * 说明书文件URL
     */
    private String manualUrl;
    /**
     * 图纸文件URL
     */
    private String drawingUrl;
    /**
     * 供应商
     */
    private String supplier;
    /**
     * 采购日期
     */
    private LocalDate purchaseDate;
    /**
     * 采购价格
     */
    private BigDecimal purchasePrice;
    /**
     * 保修期(月)
     */
    private Integer warrantyPeriod;
    /**
     * 保修到期日期
     */
    private LocalDate warrantyExpireDate;
    /**
     * 备注
     */
    private String remark;


}
