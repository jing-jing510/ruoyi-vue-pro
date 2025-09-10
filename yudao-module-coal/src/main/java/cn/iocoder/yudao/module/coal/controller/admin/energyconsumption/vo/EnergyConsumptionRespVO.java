package cn.iocoder.yudao.module.coal.controller.admin.energyconsumption.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 能源消耗记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyConsumptionRespVO {

    @Schema(description = "消耗记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30988")
    @ExcelProperty("消耗记录ID")
    private Long id;

    @Schema(description = "记录编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("记录编号")
    private String recordCode;

    @Schema(description = "能源类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30734")
    @ExcelProperty("能源类型ID")
    private Long energyTypeId;

    @Schema(description = "消耗日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("消耗日期")
    private LocalDate consumptionDate;

    @Schema(description = "班次ID", example = "27355")
    @ExcelProperty("班次ID")
    private Long shiftId;

    @Schema(description = "消耗量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("消耗量")
    private BigDecimal consumptionValue;

    @Schema(description = "单位成本(元/单位)")
    @ExcelProperty("单位成本(元/单位)")
    private BigDecimal unitCost;

    @Schema(description = "总成本(元)")
    @ExcelProperty("总成本(元)")
    private BigDecimal totalCost;

    @Schema(description = "数据来源", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "数据来源", converter = DictConvert.class)
    @DictFormat("coal_energy_data_source") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer dataSource;

    @Schema(description = "采集时间")
    @ExcelProperty("采集时间")
    private LocalDateTime collectionTime;

    @Schema(description = "记录人ID", example = "31872")
    @ExcelProperty("记录人ID")
    private Long recorderId;

    @Schema(description = "记录人姓名", example = "芋艿")
    @ExcelProperty("记录人姓名")
    private String recorderName;

    @Schema(description = "验证状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "验证状态", converter = DictConvert.class)
    @DictFormat("coal_energy_verification_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer verificationStatus;

    @Schema(description = "验证人ID", example = "24057")
    @ExcelProperty("验证人ID")
    private Long verifierId;

    @Schema(description = "验证时间")
    @ExcelProperty("验证时间")
    private LocalDateTime verificationTime;

    @Schema(description = "验证备注", example = "你说的对")
    @ExcelProperty("验证备注")
    private String verificationRemark;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
