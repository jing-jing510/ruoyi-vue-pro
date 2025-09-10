package cn.iocoder.yudao.module.coal.controller.admin.safetycheckrecord.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 安全检查记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SafetyCheckRecordRespVO {

    @Schema(description = "记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1806")
    @ExcelProperty("记录ID")
    private Long id;

    @Schema(description = "记录编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("记录编号")
    private String recordCode;

    @Schema(description = "计划ID", example = "11940")
    @ExcelProperty("计划ID")
    private Long planId;

    @Schema(description = "检查类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "检查类型", converter = DictConvert.class)
    @DictFormat("coal_safety_check_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer checkType;

    @Schema(description = "检查分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20297")
    @ExcelProperty("检查分类ID")
    private Long checkCategoryId;

    @Schema(description = "检查日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检查日期")
    private LocalDate checkDate;

    @Schema(description = "检查时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检查时间")
    private LocalDateTime checkTime;

    @Schema(description = "检查人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26902")
    @ExcelProperty("检查人ID")
    private Long checkerId;

    @Schema(description = "检查人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("检查人姓名")
    private String checkerName;

    @Schema(description = "检查区域", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检查区域")
    private String checkArea;

    @Schema(description = "天气情况")
    @ExcelProperty("天气情况")
    private String weatherCondition;

    @Schema(description = "温度(℃)")
    @ExcelProperty("温度(℃)")
    private BigDecimal temperature;

    @Schema(description = "湿度(%)")
    @ExcelProperty("湿度(%)")
    private BigDecimal humidity;

    @Schema(description = "检查总结")
    @ExcelProperty("检查总结")
    private String checkSummary;

    @Schema(description = "检查项目总数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检查项目总数")
    private Integer totalItems;

    @Schema(description = "合格项目数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("合格项目数")
    private Integer qualifiedItems;

    @Schema(description = "不合格项目数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("不合格项目数")
    private Integer unqualifiedItems;

    @Schema(description = "合格率(%)")
    @ExcelProperty("合格率(%)")
    private BigDecimal qualifiedRate;

    @Schema(description = "记录状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "记录状态", converter = DictConvert.class)
    @DictFormat("coal_safety_record_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer recordStatus;

    @Schema(description = "提交时间")
    @ExcelProperty("提交时间")
    private LocalDateTime submitTime;

    @Schema(description = "审核人ID", example = "3115")
    @ExcelProperty("审核人ID")
    private Long auditorId;

    @Schema(description = "审核人姓名", example = "张三")
    @ExcelProperty("审核人姓名")
    private String auditorName;

    @Schema(description = "审核时间")
    @ExcelProperty("审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核备注", example = "你猜")
    @ExcelProperty("审核备注")
    private String auditRemark;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
