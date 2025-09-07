package cn.iocoder.yudao.module.coal.controller.admin.equipmentinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "管理后台 - 设备档案新增/修改 Request VO")
@Data
public class EquipmentInfoSaveReqVO {

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6025")
    private Long id;

    @Schema(description = "设备编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "设备编号不能为空")
    private String equipmentCode;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "设备名称不能为空")
    private String equipmentName;

    @Schema(description = "设备分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22081")
    @NotNull(message = "设备分类ID不能为空")
    private Long categoryId;

    @Schema(description = "父设备ID", example = "10456")
    private Long parentEquipmentId;

    @Schema(description = "设备型号")
    private String model;

    @Schema(description = "制造厂商")
    private String manufacturer;

    @Schema(description = "制造日期")
    private LocalDate manufactureDate;

    @Schema(description = "安装日期")
    private LocalDate installDate;

    @Schema(description = "投产日期")
    private LocalDate commissionDate;

    @Schema(description = "资产编号")
    private String assetNumber;

    @Schema(description = "额定功率(kW)")
    private BigDecimal ratedPower;

    @Schema(description = "额定处理能力(t/h)")
    private BigDecimal ratedCapacity;

    @Schema(description = "设备重量(t)")
    private BigDecimal weight;

    @Schema(description = "外形尺寸(长x宽x高)")
    private String dimensions;

    @Schema(description = "电压等级")
    private String voltageLevel;

    @Schema(description = "防护等级")
    private String protectionLevel;

    @Schema(description = "所属车间")
    private String workshop;

    @Schema(description = "安装位置")
    private String location;

    @Schema(description = "X坐标")
    private BigDecimal coordinateX;

    @Schema(description = "Y坐标")
    private BigDecimal coordinateY;

    @Schema(description = "设备状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "设备状态不能为空")
    private Integer equipmentStatus;

    @Schema(description = "健康等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "健康等级不能为空")
    private Integer healthLevel;

    @Schema(description = "重要度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "重要度不能为空")
    private Integer importanceLevel;

    @Schema(description = "责任人ID", example = "21622")
    private Long responsiblePersonId;

    @Schema(description = "维护人ID", example = "15620")
    private Long maintenancePersonId;

    @Schema(description = "操作人ID", example = "10635")
    private Long operatorPersonId;

    @Schema(description = "二维码内容")
    private String qrCode;

    @Schema(description = "二维码图片URL", example = "https://www.iocoder.cn")
    private String qrCodeUrl;

    @Schema(description = "说明书文件URL", example = "https://www.iocoder.cn")
    private String manualUrl;

    @Schema(description = "图纸文件URL", example = "https://www.iocoder.cn")
    private String drawingUrl;

    @Schema(description = "供应商")
    private String supplier;

    @Schema(description = "采购日期")
    private LocalDate purchaseDate;

    @Schema(description = "采购价格", example = "27259")
    private BigDecimal purchasePrice;

    @Schema(description = "保修期(月)")
    private Integer warrantyPeriod;

    @Schema(description = "保修到期日期")
    private LocalDate warrantyExpireDate;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}