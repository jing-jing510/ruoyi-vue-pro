package cn.iocoder.yudao.module.coal.controller.admin.energyconsumption.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 能源消耗记录新增/修改 Request VO")
@Data
public class EnergyConsumptionSaveReqVO {

    @Schema(description = "消耗记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30988")
    private Long id;

    @Schema(description = "记录编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "记录编号不能为空")
    private String recordCode;

    @Schema(description = "能源类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30734")
    @NotNull(message = "能源类型ID不能为空")
    private Long energyTypeId;

    @Schema(description = "消耗日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "消耗日期不能为空")
    private LocalDate consumptionDate;

    @Schema(description = "班次ID", example = "27355")
    private Long shiftId;

    @Schema(description = "消耗量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "消耗量不能为空")
    private BigDecimal consumptionValue;

    @Schema(description = "单位成本(元/单位)")
    private BigDecimal unitCost;

    @Schema(description = "总成本(元)")
    private BigDecimal totalCost;

    @Schema(description = "数据来源", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数据来源不能为空")
    private Integer dataSource;

    @Schema(description = "采集时间")
    private LocalDateTime collectionTime;

    @Schema(description = "记录人ID", example = "31872")
    private Long recorderId;

    @Schema(description = "记录人姓名", example = "芋艿")
    private String recorderName;

    @Schema(description = "验证状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "验证状态不能为空")
    private Integer verificationStatus;

    @Schema(description = "验证人ID", example = "24057")
    private Long verifierId;

    @Schema(description = "验证时间")
    private LocalDateTime verificationTime;

    @Schema(description = "验证备注", example = "你说的对")
    private String verificationRemark;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}