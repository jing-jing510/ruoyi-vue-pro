package cn.iocoder.yudao.module.coal.controller.admin.safetyaccident.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 安全事故记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SafetyAccidentRespVO {

    @Schema(description = "事故ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13238")
    @ExcelProperty("事故ID")
    private Long id;

    @Schema(description = "事故编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("事故编号")
    private String accidentCode;

    @Schema(description = "事故类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "事故类型", converter = DictConvert.class)
    @DictFormat("coal_safety_accident_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer accidentType;

    @Schema(description = "事故等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "事故等级", converter = DictConvert.class)
    @DictFormat("coal_safety_accident_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer accidentLevel;

    @Schema(description = "事故标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("事故标题")
    private String accidentTitle;

    @Schema(description = "事故发生时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("事故发生时间")
    private LocalDateTime accidentTime;

    @Schema(description = "事故地点", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("事故地点")
    private String accidentLocation;

    @Schema(description = "天气情况")
    @ExcelProperty("天气情况")
    private String weatherCondition;

    @Schema(description = "事故描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "你说的对")
    @ExcelProperty("事故描述")
    private String accidentDescription;

    @Schema(description = "事故原因")
    @ExcelProperty("事故原因")
    private String accidentCause;

    @Schema(description = "伤亡人数", requiredMode = Schema.RequiredMode.REQUIRED, example = "12180")
    @ExcelProperty("伤亡人数")
    private Integer casualtiesCount;

    @Schema(description = "经济损失(元)")
    @ExcelProperty("经济损失(元)")
    private BigDecimal economicLoss;

    @Schema(description = "设备损坏情况")
    @ExcelProperty("设备损坏情况")
    private String equipmentDamage;

    @Schema(description = "环境影响")
    @ExcelProperty("环境影响")
    private String environmentalImpact;

    @Schema(description = "应急响应措施")
    @ExcelProperty("应急响应措施")
    private String emergencyResponse;

    @Schema(description = "救援过程")
    @ExcelProperty("救援过程")
    private String rescueProcess;

    @Schema(description = "报告人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21540")
    @ExcelProperty("报告人ID")
    private Long reporterId;

    @Schema(description = "报告人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("报告人姓名")
    private String reporterName;

    @Schema(description = "报告时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("报告时间")
    private LocalDateTime reportTime;

    @Schema(description = "调查人ID", example = "17443")
    @ExcelProperty("调查人ID")
    private Long investigatorId;

    @Schema(description = "调查人姓名", example = "芋艿")
    @ExcelProperty("调查人姓名")
    private String investigatorName;

    @Schema(description = "调查开始时间")
    @ExcelProperty("调查开始时间")
    private LocalDateTime investigationStartTime;

    @Schema(description = "调查结束时间")
    @ExcelProperty("调查结束时间")
    private LocalDateTime investigationEndTime;

    @Schema(description = "调查结果")
    @ExcelProperty("调查结果")
    private String investigationResult;

    @Schema(description = "预防措施")
    @ExcelProperty("预防措施")
    private String preventiveMeasures;

    @Schema(description = "责任人ID", example = "22467")
    @ExcelProperty("责任人ID")
    private Long responsiblePersonId;

    @Schema(description = "责任人姓名", example = "王五")
    @ExcelProperty("责任人姓名")
    private String responsiblePersonName;

    @Schema(description = "处罚措施")
    @ExcelProperty("处罚措施")
    private String punishmentMeasures;

    @Schema(description = "事故状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "事故状态", converter = DictConvert.class)
    @DictFormat("coal_safety_accident_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer accidentStatus;

    @Schema(description = "审批状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "审批状态", converter = DictConvert.class)
    @DictFormat("coal_safety_approval_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer approvalStatus;

    @Schema(description = "审批人ID", example = "30851")
    @ExcelProperty("审批人ID")
    private Long approverId;

    @Schema(description = "审批人姓名", example = "芋艿")
    @ExcelProperty("审批人姓名")
    private String approverName;

    @Schema(description = "审批时间")
    @ExcelProperty("审批时间")
    private LocalDateTime approvalTime;

    @Schema(description = "审批备注", example = "你猜")
    @ExcelProperty("审批备注")
    private String approvalRemark;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}