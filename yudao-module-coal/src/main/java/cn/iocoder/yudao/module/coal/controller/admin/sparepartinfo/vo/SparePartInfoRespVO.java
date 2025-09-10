package cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 备件基础信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SparePartInfoRespVO {

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20979")
    @ExcelProperty("备件ID")
    private Long id;

    @Schema(description = "备件编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("备件编号")
    private String sparePartCode;

    @Schema(description = "备件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("备件名称")
    private String sparePartName;

    @Schema(description = "备件分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21844")
    @ExcelProperty("备件分类ID")
    private Long categoryId;

    @Schema(description = "规格型号")
    @ExcelProperty("规格型号")
    private String specification;

    @Schema(description = "品牌")
    @ExcelProperty("品牌")
    private String brand;

    @Schema(description = "计量单位")
    @ExcelProperty("计量单位")
    private String unit;

    @Schema(description = "备件类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "备件类型", converter = DictConvert.class)
    @DictFormat("spare_part_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer sparePartType;

    @Schema(description = "材质")
    @ExcelProperty("材质")
    private String material;

    @Schema(description = "重量(kg)")
    @ExcelProperty("重量(kg)")
    private BigDecimal weight;

    @Schema(description = "外形尺寸")
    @ExcelProperty("外形尺寸")
    private String dimensions;

    @Schema(description = "最低库存预警数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("最低库存预警数量")
    private BigDecimal minStock;

    @Schema(description = "最高库存数量")
    @ExcelProperty("最高库存数量")
    private BigDecimal maxStock;

    @Schema(description = "安全库存数量")
    @ExcelProperty("安全库存数量")
    private BigDecimal safetyStock;

    @Schema(description = "当前库存数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("当前库存数量")
    private BigDecimal currentStock;

    @Schema(description = "主要供应商")
    @ExcelProperty("主要供应商")
    private String supplier;

    @Schema(description = "供应商零件号")
    @ExcelProperty("供应商零件号")
    private String supplierPartNo;

    @Schema(description = "单价", example = "4869")
    @ExcelProperty("单价")
    private BigDecimal unitPrice;

    @Schema(description = "采购周期(天)")
    @ExcelProperty("采购周期(天)")
    private Integer leadTime;

    @Schema(description = "建议更换周期(天)")
    @ExcelProperty("建议更换周期(天)")
    private Integer replacementCycle;

    @Schema(description = "平均使用寿命(天)")
    @ExcelProperty("平均使用寿命(天)")
    private Integer averageLifespan;

    @Schema(description = "使用频率")
    @ExcelProperty(value = "使用频率", converter = DictConvert.class)
    @DictFormat("usage_frequency") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer usageFrequency;

    @Schema(description = "存储位置")
    @ExcelProperty("存储位置")
    private String storageLocation;

    @Schema(description = "存储条件要求")
    @ExcelProperty("存储条件要求")
    private String storageCondition;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("spare_part_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "是否关键备件", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否关键备件", converter = DictConvert.class)
    @DictFormat("is_critical_spare_part")
    private Integer isCritical;

    @Schema(description = "备件图片URL", example = "https://www.iocoder.cn")
    @ExcelProperty("备件图片URL")
    private String imageUrl;

    @Schema(description = "说明书文件URL", example = "https://www.iocoder.cn")
    @ExcelProperty("说明书文件URL")
    private String manualUrl;

    @Schema(description = "图纸文件URL", example = "https://www.iocoder.cn")
    @ExcelProperty("图纸文件URL")
    private String drawingUrl;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
