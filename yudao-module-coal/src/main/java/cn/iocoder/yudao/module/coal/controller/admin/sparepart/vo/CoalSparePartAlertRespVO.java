package cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 备件预警记录 Response VO")
@Data
public class CoalSparePartAlertRespVO {

    @Schema(description = "预警ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long sparePartId;

    @Schema(description = "备件名称", example = "破碎机轴承")
    private String sparePartName;

    @Schema(description = "仓库ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long warehouseId;

    @Schema(description = "仓库名称", example = "主仓库")
    private String warehouseName;

    @Schema(description = "预警类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer alertType;

    @Schema(description = "当前库存", requiredMode = Schema.RequiredMode.REQUIRED, example = "5.00")
    private BigDecimal currentStock;

    @Schema(description = "阈值", requiredMode = Schema.RequiredMode.REQUIRED, example = "10.00")
    private BigDecimal thresholdValue;

    @Schema(description = "预警级别", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer alertLevel;

    @Schema(description = "预警状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer alertStatus;

    @Schema(description = "预警信息", requiredMode = Schema.RequiredMode.REQUIRED, example = "库存不足")
    private String alertMessage;

    @Schema(description = "处理人ID", example = "1")
    private Long handlerId;

    @Schema(description = "处理人名称", example = "张三")
    private String handlerName;

    @Schema(description = "处理时间", example = "2024-01-01 12:00:00")
    private LocalDateTime handleTime;

    @Schema(description = "处理备注", example = "已补充库存")
    private String handleRemark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
