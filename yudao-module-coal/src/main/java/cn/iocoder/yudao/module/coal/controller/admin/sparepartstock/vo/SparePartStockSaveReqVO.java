package cn.iocoder.yudao.module.coal.controller.admin.sparepartstock.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 备件库存记录新增/修改 Request VO")
@Data
public class SparePartStockSaveReqVO {

    @Schema(description = "库存记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24362")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3127")
    @NotNull(message = "备件ID不能为空")
    private Long sparePartId;

    @Schema(description = "仓库位置", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "仓库位置不能为空")
    private String warehouseLocation;

    @Schema(description = "库存类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "库存类型不能为空")
    private Integer stockType;

    @Schema(description = "库存数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "库存数量不能为空")
    private BigDecimal quantity;

    @Schema(description = "单位成本")
    private BigDecimal unitCost;

    @Schema(description = "总成本")
    private BigDecimal totalCost;

    @Schema(description = "批次号")
    private String batchNo;

    @Schema(description = "生产日期")
    private LocalDate productionDate;

    @Schema(description = "有效期至")
    private LocalDate expiryDate;

    @Schema(description = "最后入库时间")
    private LocalDateTime lastInDate;

    @Schema(description = "最后出库时间")
    private LocalDateTime lastOutDate;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}