package cn.iocoder.yudao.module.coal.controller.admin.sparepartinventorylog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.framework.common.util.date.DateUtils;

@Schema(description = "管理后台 - 备件出入库记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SparePartInventoryLogRespVO {

    @Schema(description = "记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3634")
    @ExcelProperty("记录ID")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12975")
    @ExcelProperty("备件ID")
    private Long sparePartId;

    @Schema(description = "操作类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "操作类型", converter = DictConvert.class)
    @DictFormat("operation_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer operationType;

    @Schema(description = "操作时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("操作时间")
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime operationDate;

    @Schema(description = "数量(正数入库，负数出库)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("数量(正数入库，负数出库)")
    private BigDecimal quantity;

    @Schema(description = "单价", example = "14529")
    @ExcelProperty("单价")
    private BigDecimal unitPrice;

    @Schema(description = "总金额")
    @ExcelProperty("总金额")
    private BigDecimal totalAmount;

    @Schema(description = "操作人ID", example = "4292")
    @ExcelProperty("操作人ID")
    private Long operatorId;

    @Schema(description = "仓库位置")
    @ExcelProperty("仓库位置")
    private String warehouseLocation;

    @Schema(description = "批次号")
    @ExcelProperty("批次号")
    private String batchNo;

    @Schema(description = "关联设备ID(出库时)", example = "10458")
    @ExcelProperty("关联设备ID(出库时)")
    private Long equipmentId;

    @Schema(description = "关联工单ID", example = "5006")
    @ExcelProperty("关联工单ID")
    private Long workOrderId;

    @Schema(description = "供应商ID(入库时)", example = "8256")
    @ExcelProperty("供应商ID(入库时)")
    private Long supplierId;

    @Schema(description = "采购单号")
    @ExcelProperty("采购单号")
    private String purchaseOrderNo;

    @Schema(description = "审批人ID", example = "4695")
    @ExcelProperty("审批人ID")
    private Long approverId;

    @Schema(description = "审批时间")
    @ExcelProperty("审批时间")
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime approveTime;

    @Schema(description = "审批状态", example = "2")
    @ExcelProperty(value = "审批状态", converter = DictConvert.class)
    @DictFormat("approve_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer approveStatus;

    @Schema(description = "操作原因", example = "不好")
    @ExcelProperty("操作原因")
    private String operationReason;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
