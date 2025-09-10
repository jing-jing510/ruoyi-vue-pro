package cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

@Schema(description = "管理后台 - 质量检测数据 Response VO")
@Data
@ExcelIgnoreUnannotated
public class QualityDataRespVO {

    @Schema(description = "检测数据ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27215")
    @ExcelProperty("检测数据ID")
    private Long id;

    @Schema(description = "检测记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1820")
    @ExcelProperty("检测记录ID")
    private Long inspectionId;

    @Schema(description = "检测项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6681")
    @ExcelProperty("检测项目ID")
    private Long qualityItemId;

    @Schema(description = "检测项目编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检测项目编码")
    private String qualityItemCode;

    @Schema(description = "检测项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("检测项目名称")
    private String qualityItemName;

    @Schema(description = "检测值", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检测值")
    private BigDecimal measuredValue;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计量单位")
    private String unit;

    @Schema(description = "标准值")
    @ExcelProperty("标准值")
    private BigDecimal standardValue;

    @Schema(description = "偏差值")
    @ExcelProperty("偏差值")
    private BigDecimal deviation;

    @Schema(description = "偏差率(%)")
    @ExcelProperty("偏差率(%)")
    private BigDecimal deviationRate;

    @Schema(description = "是否合格", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否合格")
    private Integer isQualified;

    @Schema(description = "检测方法")
    @ExcelProperty("检测方法")
    private String detectionMethod;

    @Schema(description = "检测设备")
    @ExcelProperty("检测设备")
    private String detectionEquipment;

    @Schema(description = "检测时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检测时间")
    private LocalDateTime detectionTime;

    @Schema(description = "操作员ID", example = "31379")
    @ExcelProperty("操作员ID")
    private Long operatorId;

    @Schema(description = "操作员姓名", example = "李四")
    @ExcelProperty("操作员姓名")
    private String operatorName;

    @Schema(description = "复检次数", example = "10177")
    @ExcelProperty("复检次数")
    private Integer retestCount;

    @Schema(description = "是否复检", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否复检")
    private Integer isRetest;

    @Schema(description = "原始检测值（复检时记录）")
    @ExcelProperty("原始检测值（复检时记录）")
    private BigDecimal originalValue;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}