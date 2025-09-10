package cn.iocoder.yudao.module.coal.controller.admin.qualitystandard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 质量标准新增/修改 Request VO")
@Data
public class QualityStandardSaveReqVO {

    @Schema(description = "质量标准ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25430")
    private Long id;

    @Schema(description = "标准编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "标准编码不能为空")
    private String standardCode;

    @Schema(description = "标准名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "标准名称不能为空")
    private String standardName;

    @Schema(description = "产品类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "产品类型不能为空")
    private Integer productType;

    @Schema(description = "产品规格")
    private String productSpecification;

    @Schema(description = "检测项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5371")
    @NotNull(message = "检测项目ID不能为空")
    private Long qualityItemId;

    @Schema(description = "标准值", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "标准值不能为空")
    private BigDecimal standardValue;

    @Schema(description = "最大允许值")
    private BigDecimal maxValue;

    @Schema(description = "最小允许值")
    private BigDecimal minValue;

    @Schema(description = "允许公差")
    private BigDecimal tolerance;

    @Schema(description = "标准类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "标准类型不能为空")
    private Integer standardType;

    @Schema(description = "标准来源（如：GB/T标准号）")
    private String standardSource;

    @Schema(description = "生效日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "生效日期不能为空")
    private LocalDate effectiveDate;

    @Schema(description = "失效日期")
    private LocalDate expiryDate;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "审批人ID", example = "22878")
    private Long approverId;

    @Schema(description = "审批时间")
    private LocalDateTime approveTime;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}