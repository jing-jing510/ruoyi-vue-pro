package cn.iocoder.yudao.module.coal.controller.admin.equipmentinfo.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备档案 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EquipmentInfoRespVO {

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6025")
    @ExcelProperty("设备ID")
    private Long id;

    @Schema(description = "设备编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("设备编号")
    private String equipmentCode;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("设备名称")
    private String equipmentName;

    @Schema(description = "设备分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22081")
    @ExcelProperty("设备分类ID")
    private Long categoryId;

    @Schema(description = "父设备ID", example = "10456")
    @ExcelProperty("父设备ID")
    private Long parentEquipmentId;

    @Schema(description = "设备型号")
    @ExcelProperty("设备型号")
    private String model;

    @Schema(description = "制造厂商")
    @ExcelProperty("制造厂商")
    private String manufacturer;

    @Schema(description = "制造日期")
    @ExcelProperty("制造日期")
    private LocalDate manufactureDate;

    @Schema(description = "安装日期")
    @ExcelProperty("安装日期")
    private LocalDate installDate;

    @Schema(description = "投产日期")
    @ExcelProperty("投产日期")
    private LocalDate commissionDate;

    @Schema(description = "资产编号")
    @ExcelProperty("资产编号")
    private String assetNumber;

    @Schema(description = "额定功率(kW)")
    @ExcelProperty("额定功率(kW)")
    private BigDecimal ratedPower;

    @Schema(description = "额定处理能力(t/h)")
    @ExcelProperty("额定处理能力(t/h)")
    private BigDecimal ratedCapacity;

    @Schema(description = "设备重量(t)")
    @ExcelProperty("设备重量(t)")
    private BigDecimal weight;

    @Schema(description = "外形尺寸(长x宽x高)")
    @ExcelProperty("外形尺寸(长x宽x高)")
    private String dimensions;

    @Schema(description = "电压等级")
    @ExcelProperty("电压等级")
    private String voltageLevel;

    @Schema(description = "防护等级")
    @ExcelProperty("防护等级")
    private String protectionLevel;

    @Schema(description = "所属车间")
    @ExcelProperty("所属车间")
    private String workshop;

    @Schema(description = "安装位置")
    @ExcelProperty("安装位置")
    private String location;

    @Schema(description = "X坐标")
    @ExcelProperty("X坐标")
    private BigDecimal coordinateX;

    @Schema(description = "Y坐标")
    @ExcelProperty("Y坐标")
    private BigDecimal coordinateY;

    @Schema(description = "设备状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "设备状态", converter = DictConvert.class)
    @DictFormat("equipment_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer equipmentStatus;

    @Schema(description = "健康等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "健康等级", converter = DictConvert.class)
    @DictFormat("equipment_health_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer healthLevel;

    @Schema(description = "重要度", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "重要度", converter = DictConvert.class)
    @DictFormat("equipment_importance_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer importanceLevel;

    @Schema(description = "责任人ID", example = "21622")
    @ExcelProperty("责任人ID")
    private Long responsiblePersonId;

    @Schema(description = "维护人ID", example = "15620")
    @ExcelProperty("维护人ID")
    private Long maintenancePersonId;

    @Schema(description = "操作人ID", example = "10635")
    @ExcelProperty("操作人ID")
    private Long operatorPersonId;

    @Schema(description = "二维码内容")
    @ExcelProperty("二维码内容")
    private String qrCode;

    @Schema(description = "二维码图片URL", example = "https://www.iocoder.cn")
    @ExcelProperty("二维码图片URL")
    private String qrCodeUrl;

    @Schema(description = "说明书文件URL", example = "https://www.iocoder.cn")
    @ExcelProperty("说明书文件URL")
    private String manualUrl;

    @Schema(description = "图纸文件URL", example = "https://www.iocoder.cn")
    @ExcelProperty("图纸文件URL")
    private String drawingUrl;

    @Schema(description = "供应商")
    @ExcelProperty("供应商")
    private String supplier;

    @Schema(description = "采购日期")
    @ExcelProperty("采购日期")
    private LocalDate purchaseDate;

    @Schema(description = "采购价格", example = "27259")
    @ExcelProperty("采购价格")
    private BigDecimal purchasePrice;

    @Schema(description = "保修期(月)")
    @ExcelProperty("保修期(月)")
    private Integer warrantyPeriod;

    @Schema(description = "保修到期日期")
    @ExcelProperty("保修到期日期")
    private LocalDate warrantyExpireDate;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
