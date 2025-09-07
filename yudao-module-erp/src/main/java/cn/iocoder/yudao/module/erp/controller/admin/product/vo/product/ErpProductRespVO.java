package cn.iocoder.yudao.module.erp.controller.admin.product.vo.product;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Schema(description = "管理后台 - ERP 产品 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ErpProductRespVO {

    @Schema(description = "产品编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15672")
    @ExcelProperty("产品编号")
    private Long id;

    @Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("产品名称")
    private String name;

    @Schema(description = "产品条码", requiredMode = Schema.RequiredMode.REQUIRED, example = "X110")
    @ExcelProperty("产品条码")
    private String barCode;

    @Schema(description = "产品分类编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11161")
    private Long categoryId;
    @Schema(description = "产品分类", requiredMode = Schema.RequiredMode.REQUIRED, example = "水果")
    @ExcelProperty("产品分类")
    private String categoryName;

    @Schema(description = "单位编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "8869")
    private Long unitId;
    @Schema(description = "单位", requiredMode = Schema.RequiredMode.REQUIRED, example = "个")
    @ExcelProperty("单位")
    private String unitName;

    @Schema(description = "产品状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("产品状态")
    private Integer status;

    @Schema(description = "产品规格", example = "红色")
    @ExcelProperty("产品规格")
    private String standard;

    @Schema(description = "产品备注", example = "你猜")
    @ExcelProperty("产品备注")
    private String remark;

    @Schema(description = "保质期天数", example = "10")
    @ExcelProperty("保质期天数")
    private Integer expiryDay;

    @Schema(description = "基础重量（kg）", example = "1.00")
    @ExcelProperty("基础重量（kg）")
    private BigDecimal weight;

    @Schema(description = "采购价格，单位：元", example = "10.30")
    @ExcelProperty("采购价格，单位：元")
    private BigDecimal purchasePrice;

    @Schema(description = "销售价格，单位：元", example = "74.32")
    @ExcelProperty("销售价格，单位：元")
    private BigDecimal salePrice;

    @Schema(description = "最低价格，单位：元", example = "161.87")
    @ExcelProperty("最低价格，单位：元")
    private BigDecimal minPrice;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    // ========== 备件管理扩展字段 ==========
    
    @Schema(description = "关联设备ID", example = "1")
    private Long equipmentId;
    
    @Schema(description = "关联设备名称", example = "破碎机")
    @ExcelProperty("关联设备")
    private String equipmentName;
    
    @Schema(description = "备件类型", example = "1")
    @ExcelProperty("备件类型")
    private Integer sparePartType;
    
    @Schema(description = "最低库存", example = "10.00")
    @ExcelProperty("最低库存")
    private BigDecimal minStock;
    
    @Schema(description = "最高库存", example = "100.00")
    @ExcelProperty("最高库存")
    private BigDecimal maxStock;
    
    @Schema(description = "安全库存", example = "20.00")
    @ExcelProperty("安全库存")
    private BigDecimal safetyStock;
    
    @Schema(description = "主要供应商ID", example = "1")
    private Long supplierId;
    
    @Schema(description = "供应商名称", example = "XX轴承厂")
    @ExcelProperty("供应商")
    private String supplierName;
    
    @Schema(description = "更换周期(天)", example = "30")
    @ExcelProperty("更换周期(天)")
    private Integer replacementCycle;
    
    @Schema(description = "最后更换日期", example = "2024-01-01")
    @ExcelProperty("最后更换日期")
    private LocalDate lastReplacementDate;
    
    @Schema(description = "下次更换日期", example = "2024-02-01")
    @ExcelProperty("下次更换日期")
    private LocalDate nextReplacementDate;
    
    @Schema(description = "当前库存", example = "50.00")
    @ExcelProperty("当前库存")
    private BigDecimal currentStock;

}
