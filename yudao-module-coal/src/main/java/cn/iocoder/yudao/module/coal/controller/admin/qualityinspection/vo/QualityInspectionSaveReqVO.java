package cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityDataDO;

@Schema(description = "管理后台 - 质量检测记录新增/修改 Request VO")
@Data
public class QualityInspectionSaveReqVO {

    @Schema(description = "检测记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1415")
    private Long id;

    @Schema(description = "检测编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "检测编号不能为空")
    private String inspectionCode;

    @Schema(description = "检测时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检测时间不能为空")
    private LocalDateTime inspectionDate;

    @Schema(description = "班次ID", example = "8958")
    private Long shiftId;

    @Schema(description = "检测人员ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25369")
    @NotNull(message = "检测人员ID不能为空")
    private Long inspectorId;

    @Schema(description = "检测人员姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "检测人员姓名不能为空")
    private String inspectorName;

    @Schema(description = "产品类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "产品类型不能为空")
    private Integer productType;

    @Schema(description = "产品规格")
    private String productSpecification;

    @Schema(description = "采样地点", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "采样地点不能为空")
    private String samplingLocation;

    @Schema(description = "采样方法", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "采样方法不能为空")
    private Integer samplingMethod;

    @Schema(description = "样品编号")
    private String sampleCode;

    @Schema(description = "检测类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "检测类型不能为空")
    private Integer detectionType;

    @Schema(description = "系统位置")
    private String systemLocation;

    @Schema(description = "批次号")
    private String batchNumber;

    @Schema(description = "检测状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "检测状态不能为空")
    private Integer inspectionStatus;

    @Schema(description = "审核人员ID", example = "10875")
    private Long reviewerId;

    @Schema(description = "审核人员姓名", example = "赵六")
    private String reviewerName;

    @Schema(description = "审核时间")
    private LocalDateTime reviewTime;

    @Schema(description = "审核状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "审核状态不能为空")
    private Integer reviewStatus;

    @Schema(description = "审核备注", example = "你猜")
    private String reviewRemark;

    @Schema(description = "环境温度(℃)")
    private BigDecimal temperature;

    @Schema(description = "环境湿度(%)")
    private BigDecimal humidity;

    @Schema(description = "天气情况")
    private String weatherCondition;

    @Schema(description = "备注信息", example = "随便")
    private String remark;

}