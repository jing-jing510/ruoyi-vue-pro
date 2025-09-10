package cn.iocoder.yudao.module.coal.controller.admin.sparepartstock.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 备件库存记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SparePartStockRespVO {

    @Schema(description = "库存记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24362")
    @ExcelProperty("库存记录ID")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3127")
    @ExcelProperty("备件ID")
    private Long sparePartId;

    @Schema(description = "仓库位置", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("仓库位置")
    private String warehouseLocation;

    @Schema(description = "库存类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "库存类型", converter = DictConvert.class)
    @DictFormat("stock_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer stockType;

    @Schema(description = "库存数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("库存数量")
    private BigDecimal quantity;

    @Schema(description = "单位成本")
    @ExcelProperty("单位成本")
    private BigDecimal unitCost;

    @Schema(description = "总成本")
    @ExcelProperty("总成本")
    private BigDecimal totalCost;

    @Schema(description = "批次号")
    @ExcelProperty("批次号")
    private String batchNo;

    @Schema(description = "生产日期")
    @ExcelProperty("生产日期")
    private LocalDate productionDate;

    @Schema(description = "有效期至")
    @ExcelProperty("有效期至")
    private LocalDate expiryDate;

    @Schema(description = "最后入库时间")
    @ExcelProperty("最后入库时间")
    private LocalDateTime lastInDate;

    @Schema(description = "最后出库时间")
    @ExcelProperty("最后出库时间")
    private LocalDateTime lastOutDate;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
