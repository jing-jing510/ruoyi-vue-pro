package cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 备件基础信息新增/修改 Request VO")
@Data
public class SparePartInfoSaveReqVO {

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20979")
    private Long id;

    @Schema(description = "备件编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "备件编号不能为空")
    private String sparePartCode;

    @Schema(description = "备件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "备件名称不能为空")
    private String sparePartName;

    @Schema(description = "备件分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21844")
    @NotNull(message = "备件分类ID不能为空")
    private Long categoryId;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "计量单位")
    private String unit;

    @Schema(description = "备件类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "备件类型不能为空")
    private Integer sparePartType;

    @Schema(description = "材质")
    private String material;

    @Schema(description = "重量(kg)")
    private BigDecimal weight;

    @Schema(description = "外形尺寸")
    private String dimensions;

    @Schema(description = "最低库存预警数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "最低库存预警数量不能为空")
    private BigDecimal minStock;

    @Schema(description = "最高库存数量")
    private BigDecimal maxStock;

    @Schema(description = "安全库存数量")
    private BigDecimal safetyStock;

    @Schema(description = "当前库存数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "当前库存数量不能为空")
    private BigDecimal currentStock;

    @Schema(description = "主要供应商")
    private String supplier;

    @Schema(description = "供应商零件号")
    private String supplierPartNo;

    @Schema(description = "单价", example = "4869")
    private BigDecimal unitPrice;

    @Schema(description = "采购周期(天)")
    private Integer leadTime;

    @Schema(description = "建议更换周期(天)")
    private Integer replacementCycle;

    @Schema(description = "平均使用寿命(天)")
    private Integer averageLifespan;

    @Schema(description = "使用频率")
    private Integer usageFrequency;

    @Schema(description = "存储位置")
    private String storageLocation;

    @Schema(description = "存储条件要求")
    private String storageCondition;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "是否关键备件", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否关键备件不能为空")
    private Integer isCritical;

    @Schema(description = "备件图片URL", example = "https://www.iocoder.cn")
    private String imageUrl;

    @Schema(description = "说明书文件URL", example = "https://www.iocoder.cn")
    private String manualUrl;

    @Schema(description = "图纸文件URL", example = "https://www.iocoder.cn")
    private String drawingUrl;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}