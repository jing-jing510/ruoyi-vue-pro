package cn.iocoder.yudao.module.coal.controller.admin.sparepartinventorylog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.iocoder.yudao.framework.common.util.date.DateUtils;

@Schema(description = "管理后台 - 备件出入库记录新增/修改 Request VO")
@Data
public class SparePartInventoryLogSaveReqVO {

    @Schema(description = "记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3634")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12975")
    @NotNull(message = "备件ID不能为空")
    private Long sparePartId;

    @Schema(description = "操作类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "操作类型不能为空")
    private Integer operationType;

    @Schema(description = "操作时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "操作时间不能为空")
    private LocalDateTime operationDate;

    @Schema(description = "数量(正数入库，负数出库)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数量(正数入库，负数出库)不能为空")
    private BigDecimal quantity;

    @Schema(description = "单价", example = "14529")
    private BigDecimal unitPrice;

    @Schema(description = "总金额")
    private BigDecimal totalAmount;

    @Schema(description = "操作人ID", example = "4292")
    private Long operatorId;

    @Schema(description = "仓库位置")
    private String warehouseLocation;

    @Schema(description = "批次号")
    private String batchNo;

    @Schema(description = "关联设备ID(出库时)", example = "10458")
    private Long equipmentId;

    @Schema(description = "关联工单ID", example = "5006")
    private Long workOrderId;

    @Schema(description = "供应商ID(入库时)", example = "8256")
    private Long supplierId;

    @Schema(description = "采购单号")
    private String purchaseOrderNo;

    @Schema(description = "审批人ID", example = "4695")
    private Long approverId;

    @Schema(description = "审批时间")
    private LocalDateTime approveTime;

    @Schema(description = "审批状态", example = "2")
    private Integer approveStatus;

    @Schema(description = "操作原因", example = "不好")
    private String operationReason;

    @Schema(description = "备注", example = "随便")
    private String remark;

}