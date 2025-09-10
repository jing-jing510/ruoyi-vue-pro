package cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 质量检测记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class QualityInspectionRespVO {

    @Schema(description = "检测记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1415")
    @ExcelProperty("检测记录ID")
    private Long id;

    @Schema(description = "检测编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检测编号")
    private String inspectionCode;

    @Schema(description = "检测时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检测时间")
    private LocalDateTime inspectionDate;

    @Schema(description = "班次ID", example = "8958")
    @ExcelProperty("班次ID")
    private Long shiftId;

    @Schema(description = "检测人员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25369")
    @ExcelProperty("检测人员ID")
    private Long inspectorId;

    @Schema(description = "检测人员姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("检测人员姓名")
    private String inspectorName;

    @Schema(description = "产品类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("产品类型")
    private Integer productType;

    @Schema(description = "产品规格")
    @ExcelProperty("产品规格")
    private String productSpecification;

    @Schema(description = "采样地点", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("采样地点")
    private String samplingLocation;

    @Schema(description = "采样方法", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "采样方法", converter = DictConvert.class)
    @DictFormat("coal_quality_sampling_method") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer samplingMethod;

    @Schema(description = "样品编号")
    @ExcelProperty("样品编号")
    private String sampleCode;

    @Schema(description = "检测类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "检测类型", converter = DictConvert.class)
    @DictFormat("coal_quality_detection_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer detectionType;

    @Schema(description = "系统位置")
    @ExcelProperty("系统位置")
    private String systemLocation;

    @Schema(description = "批次号")
    @ExcelProperty("批次号")
    private String batchNumber;

    @Schema(description = "检测状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "检测状态", converter = DictConvert.class)
    @DictFormat("coal_quality_inspection_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer inspectionStatus;

    @Schema(description = "审核人员ID", example = "10875")
    @ExcelProperty("审核人员ID")
    private Long reviewerId;

    @Schema(description = "审核人员姓名", example = "赵六")
    @ExcelProperty("审核人员姓名")
    private String reviewerName;

    @Schema(description = "审核时间")
    @ExcelProperty("审核时间")
    private LocalDateTime reviewTime;

    @Schema(description = "审核状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "审核状态", converter = DictConvert.class)
    @DictFormat("coal_quality_review_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer reviewStatus;

    @Schema(description = "审核备注", example = "你猜")
    @ExcelProperty("审核备注")
    private String reviewRemark;

    @Schema(description = "环境温度(℃)")
    @ExcelProperty("环境温度(℃)")
    private BigDecimal temperature;

    @Schema(description = "环境湿度(%)")
    @ExcelProperty("环境湿度(%)")
    private BigDecimal humidity;

    @Schema(description = "天气情况")
    @ExcelProperty("天气情况")
    private String weatherCondition;

    @Schema(description = "备注信息", example = "随便")
    @ExcelProperty("备注信息")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}