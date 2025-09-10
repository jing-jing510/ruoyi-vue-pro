package cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 质量检测数据新增/修改 Request VO")
@Data
public class QualityDataSaveReqVO {

    @Schema(description = "检测数据ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27215")
    private Long id;

    @Schema(description = "检测记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1820")
    @NotNull(message = "检测记录ID不能为空")
    private Long inspectionId;

    @Schema(description = "检测项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6681")
    @NotNull(message = "检测项目ID不能为空")
    private Long qualityItemId;

    @Schema(description = "检测项目编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "检测项目编码不能为空")
    private String qualityItemCode;

    @Schema(description = "检测项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "检测项目名称不能为空")
    private String qualityItemName;

    @Schema(description = "检测值", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检测值不能为空")
    private BigDecimal measuredValue;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "计量单位不能为空")
    private String unit;

    @Schema(description = "标准值")
    private BigDecimal standardValue;

    @Schema(description = "偏差值")
    private BigDecimal deviation;

    @Schema(description = "偏差率(%)")
    private BigDecimal deviationRate;

    @Schema(description = "是否合格", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否合格不能为空")
    private Integer isQualified;

    @Schema(description = "检测方法")
    private String detectionMethod;

    @Schema(description = "检测设备")
    private String detectionEquipment;

    @Schema(description = "检测时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检测时间不能为空")
    private LocalDateTime detectionTime;

    @Schema(description = "操作员ID", example = "31379")
    private Long operatorId;

    @Schema(description = "操作员姓名", example = "李四")
    private String operatorName;

    @Schema(description = "复检次数", example = "10177")
    private Integer retestCount;

    @Schema(description = "是否复检", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否复检不能为空")
    private Integer isRetest;

    @Schema(description = "原始检测值（复检时记录）")
    private BigDecimal originalValue;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}