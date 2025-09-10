package cn.iocoder.yudao.module.coal.controller.admin.qualitystandard.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 质量标准 Response VO")
@Data
@ExcelIgnoreUnannotated
public class QualityStandardRespVO {

    @Schema(description = "质量标准ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25430")
    @ExcelProperty("质量标准ID")
    private Long id;

    @Schema(description = "标准编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("标准编码")
    private String standardCode;

    @Schema(description = "标准名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("标准名称")
    private String standardName;

    @Schema(description = "产品类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "产品类型", converter = DictConvert.class)
    @DictFormat("coal_quality_product_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer productType;

    @Schema(description = "产品规格")
    @ExcelProperty("产品规格")
    private String productSpecification;

    @Schema(description = "检测项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5371")
    @ExcelProperty("检测项目ID")
    private Long qualityItemId;

    @Schema(description = "标准值", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("标准值")
    private BigDecimal standardValue;

    @Schema(description = "最大允许值")
    @ExcelProperty("最大允许值")
    private BigDecimal maxValue;

    @Schema(description = "最小允许值")
    @ExcelProperty("最小允许值")
    private BigDecimal minValue;

    @Schema(description = "允许公差")
    @ExcelProperty("允许公差")
    private BigDecimal tolerance;

    @Schema(description = "标准类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "标准类型", converter = DictConvert.class)
    @DictFormat("coal_quality_standard_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer standardType;

    @Schema(description = "标准来源（如：GB/T标准号）")
    @ExcelProperty("标准来源（如：GB/T标准号）")
    private String standardSource;

    @Schema(description = "生效日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("生效日期")
    private LocalDate effectiveDate;

    @Schema(description = "失效日期")
    @ExcelProperty("失效日期")
    private LocalDate expiryDate;

    @Schema(description = "版本号")
    @ExcelProperty("版本号")
    private String version;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "审批人ID", example = "22878")
    @ExcelProperty("审批人ID")
    private Long approverId;

    @Schema(description = "审批时间")
    @ExcelProperty("审批时间")
    private LocalDateTime approveTime;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
