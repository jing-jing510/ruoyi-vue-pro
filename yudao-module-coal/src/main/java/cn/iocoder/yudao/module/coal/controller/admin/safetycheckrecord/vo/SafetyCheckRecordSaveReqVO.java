package cn.iocoder.yudao.module.coal.controller.admin.safetycheckrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 安全检查记录新增/修改 Request VO")
@Data
public class SafetyCheckRecordSaveReqVO {

    @Schema(description = "记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1806")
    private Long id;

    @Schema(description = "记录编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "记录编号不能为空")
    private String recordCode;

    @Schema(description = "计划ID", example = "11940")
    private Long planId;

    @Schema(description = "检查类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "检查类型不能为空")
    private Integer checkType;

    @Schema(description = "检查分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20297")
    @NotNull(message = "检查分类ID不能为空")
    private Long checkCategoryId;

    @Schema(description = "检查日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检查日期不能为空")
    private LocalDate checkDate;

    @Schema(description = "检查时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检查时间不能为空")
    private LocalDateTime checkTime;

    @Schema(description = "检查人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26902")
    @NotNull(message = "检查人ID不能为空")
    private Long checkerId;

    @Schema(description = "检查人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "检查人姓名不能为空")
    private String checkerName;

    @Schema(description = "检查区域", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "检查区域不能为空")
    private String checkArea;

    @Schema(description = "天气情况")
    private String weatherCondition;

    @Schema(description = "温度(℃)")
    private BigDecimal temperature;

    @Schema(description = "湿度(%)")
    private BigDecimal humidity;

    @Schema(description = "检查总结")
    private String checkSummary;

    @Schema(description = "检查项目总数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "检查项目总数不能为空")
    private Integer totalItems;

    @Schema(description = "合格项目数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "合格项目数不能为空")
    private Integer qualifiedItems;

    @Schema(description = "不合格项目数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "不合格项目数不能为空")
    private Integer unqualifiedItems;

    @Schema(description = "合格率(%)")
    private BigDecimal qualifiedRate;

    @Schema(description = "记录状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "记录状态不能为空")
    private Integer recordStatus;

    @Schema(description = "提交时间")
    private LocalDateTime submitTime;

    @Schema(description = "审核人ID", example = "3115")
    private Long auditorId;

    @Schema(description = "审核人姓名", example = "张三")
    private String auditorName;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核备注", example = "你猜")
    private String auditRemark;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}