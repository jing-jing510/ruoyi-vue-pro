package cn.iocoder.yudao.module.coal.controller.admin.safetyaccident.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 安全事故记录新增/修改 Request VO")
@Data
public class SafetyAccidentSaveReqVO {

    @Schema(description = "事故ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13238")
    private Long id;

    @Schema(description = "事故编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "事故编号不能为空")
    private String accidentCode;

    @Schema(description = "事故类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "事故类型不能为空")
    private Integer accidentType;

    @Schema(description = "事故等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "事故等级不能为空")
    private Integer accidentLevel;

    @Schema(description = "事故标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "事故标题不能为空")
    private String accidentTitle;

    @Schema(description = "事故发生时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "事故发生时间不能为空")
    private LocalDateTime accidentTime;

    @Schema(description = "事故地点", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "事故地点不能为空")
    private String accidentLocation;

    @Schema(description = "天气情况")
    private String weatherCondition;

    @Schema(description = "事故描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "你说的对")
    @NotEmpty(message = "事故描述不能为空")
    private String accidentDescription;

    @Schema(description = "事故原因")
    private String accidentCause;

    @Schema(description = "伤亡人数", requiredMode = Schema.RequiredMode.REQUIRED, example = "12180")
    @NotNull(message = "伤亡人数不能为空")
    private Integer casualtiesCount;

    @Schema(description = "经济损失(元)")
    private BigDecimal economicLoss;

    @Schema(description = "设备损坏情况")
    private String equipmentDamage;

    @Schema(description = "环境影响")
    private String environmentalImpact;

    @Schema(description = "应急响应措施")
    private String emergencyResponse;

    @Schema(description = "救援过程")
    private String rescueProcess;

    @Schema(description = "报告人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21540")
    @NotNull(message = "报告人ID不能为空")
    private Long reporterId;

    @Schema(description = "报告人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "报告人姓名不能为空")
    private String reporterName;

    @Schema(description = "报告时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "报告时间不能为空")
    private LocalDateTime reportTime;

    @Schema(description = "调查人ID", example = "17443")
    private Long investigatorId;

    @Schema(description = "调查人姓名", example = "芋艿")
    private String investigatorName;

    @Schema(description = "调查开始时间")
    private LocalDateTime investigationStartTime;

    @Schema(description = "调查结束时间")
    private LocalDateTime investigationEndTime;

    @Schema(description = "调查结果")
    private String investigationResult;

    @Schema(description = "预防措施")
    private String preventiveMeasures;

    @Schema(description = "责任人ID", example = "22467")
    private Long responsiblePersonId;

    @Schema(description = "责任人姓名", example = "王五")
    private String responsiblePersonName;

    @Schema(description = "处罚措施")
    private String punishmentMeasures;

    @Schema(description = "事故状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "事故状态不能为空")
    private Integer accidentStatus;

    @Schema(description = "审批状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "审批状态不能为空")
    private Integer approvalStatus;

    @Schema(description = "审批人ID", example = "30851")
    private Long approverId;

    @Schema(description = "审批人姓名", example = "芋艿")
    private String approverName;

    @Schema(description = "审批时间")
    private LocalDateTime approvalTime;

    @Schema(description = "审批备注", example = "你猜")
    private String approvalRemark;

    @Schema(description = "备注", example = "随便")
    private String remark;

}