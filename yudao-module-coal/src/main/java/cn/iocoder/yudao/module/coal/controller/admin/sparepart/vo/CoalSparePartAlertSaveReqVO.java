package cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 备件预警记录新增/修改 Request VO")
@Data
public class CoalSparePartAlertSaveReqVO {

    @Schema(description = "预警ID，更新时必传", example = "1")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "备件ID不能为空")
    private Long sparePartId;

    @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "仓库ID不能为空")
    private Long warehouseId;

    @Schema(description = "预警类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "预警类型不能为空")
    private Integer alertType;

    @Schema(description = "当前库存", requiredMode = Schema.RequiredMode.REQUIRED, example = "5.00")
    @NotNull(message = "当前库存不能为空")
    private BigDecimal currentStock;

    @Schema(description = "阈值", requiredMode = Schema.RequiredMode.REQUIRED, example = "10.00")
    @NotNull(message = "阈值不能为空")
    private BigDecimal thresholdValue;

    @Schema(description = "预警级别", example = "2")
    private Integer alertLevel;

    @Schema(description = "预警信息", requiredMode = Schema.RequiredMode.REQUIRED, example = "库存不足")
    @NotNull(message = "预警信息不能为空")
    private String alertMessage;

}
