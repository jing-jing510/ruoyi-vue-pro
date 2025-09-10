package cn.iocoder.yudao.module.coal.controller.admin.qualityitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 质量检测项目 Response VO")
@Data
@ExcelIgnoreUnannotated
public class QualityItemRespVO {

    @Schema(description = "检测项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24197")
    @ExcelProperty("检测项目ID")
    private Long id;

    @Schema(description = "检测项目编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检测项目编码")
    private String itemCode;

    @Schema(description = "检测项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("检测项目名称")
    private String itemName;

    @Schema(description = "检测类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "检测类型", converter = DictConvert.class)
    @DictFormat("coal_quality_detection_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer itemType;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计量单位")
    private String unit;

    @Schema(description = "检测方法")
    @ExcelProperty("检测方法")
    private String detectionMethod;

    @Schema(description = "所需设备")
    @ExcelProperty("所需设备")
    private String equipmentRequired;

    @Schema(description = "标准值")
    @ExcelProperty("标准值")
    private BigDecimal standardValue;

    @Schema(description = "最大允许值")
    @ExcelProperty("最大允许值")
    private BigDecimal maxValue;

    @Schema(description = "最小允许值")
    @ExcelProperty("最小允许值")
    private BigDecimal minValue;

    @Schema(description = "预警上限")
    @ExcelProperty("预警上限")
    private BigDecimal warningUpperLimit;

    @Schema(description = "预警下限")
    @ExcelProperty("预警下限")
    private BigDecimal warningLowerLimit;

    @Schema(description = "精度位数")
    @ExcelProperty("精度位数")
    private Integer precisionDigits;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("显示顺序")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("coal_quality_inspection_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}