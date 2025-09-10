package cn.iocoder.yudao.module.coal.controller.admin.qualityitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 质量检测项目新增/修改 Request VO")
@Data
public class QualityItemSaveReqVO {

    @Schema(description = "检测项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24197")
    private Long id;

    @Schema(description = "检测项目编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "检测项目编码不能为空")
    private String itemCode;

    @Schema(description = "检测项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "检测项目名称不能为空")
    private String itemName;

    @Schema(description = "检测类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "检测类型不能为空")
    private Integer itemType;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "计量单位不能为空")
    private String unit;

    @Schema(description = "检测方法")
    private String detectionMethod;

    @Schema(description = "所需设备")
    private String equipmentRequired;

    @Schema(description = "标准值")
    private BigDecimal standardValue;

    @Schema(description = "最大允许值")
    private BigDecimal maxValue;

    @Schema(description = "最小允许值")
    private BigDecimal minValue;

    @Schema(description = "预警上限")
    private BigDecimal warningUpperLimit;

    @Schema(description = "预警下限")
    private BigDecimal warningLowerLimit;

    @Schema(description = "精度位数")
    private Integer precisionDigits;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}