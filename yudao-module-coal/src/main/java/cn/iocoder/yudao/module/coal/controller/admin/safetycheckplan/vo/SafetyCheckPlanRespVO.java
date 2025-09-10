package cn.iocoder.yudao.module.coal.controller.admin.safetycheckplan.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 安全检查计划 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SafetyCheckPlanRespVO {

    @Schema(description = "计划ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15507")
    @ExcelProperty("计划ID")
    private Long id;

    @Schema(description = "计划编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计划编号")
    private String planCode;

    @Schema(description = "计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("计划名称")
    private String planName;

    @Schema(description = "计划类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "计划类型", converter = DictConvert.class)
    @DictFormat("coal_safety_plan_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer planType;

    @Schema(description = "检查分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18173")
    @ExcelProperty("检查分类ID")
    private Long checkCategoryId;

    @Schema(description = "检查周期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "检查周期", converter = DictConvert.class)
    @DictFormat("coal_safety_check_cycle") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer checkCycle;

    @Schema(description = "检查频次", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检查频次")
    private Integer checkFrequency;

    @Schema(description = "负责人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22739")
    @ExcelProperty("负责人ID")
    private Long responsiblePersonId;

    @Schema(description = "负责人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("负责人姓名")
    private String responsiblePersonName;

    @Schema(description = "检查区域")
    @ExcelProperty("检查区域")
    private String checkArea;

    @Schema(description = "检查内容")
    @ExcelProperty("检查内容")
    private String checkContent;

    @Schema(description = "检查标准")
    @ExcelProperty("检查标准")
    private String checkStandard;

    @Schema(description = "开始日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("开始日期")
    private LocalDate startDate;

    @Schema(description = "结束日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("结束日期")
    private LocalDate endDate;

    @Schema(description = "计划状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "计划状态", converter = DictConvert.class)
    @DictFormat("coal_safety_plan_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer planStatus;

    @Schema(description = "审批状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "审批状态", converter = DictConvert.class)
    @DictFormat("coal_safety_approval_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer approvalStatus;

    @Schema(description = "审批人ID", example = "10847")
    @ExcelProperty("审批人ID")
    private Long approverId;

    @Schema(description = "审批人姓名", example = "张三")
    @ExcelProperty("审批人姓名")
    private String approverName;

    @Schema(description = "审批时间")
    @ExcelProperty("审批时间")
    private LocalDateTime approvalTime;

    @Schema(description = "审批备注", example = "你说的对")
    @ExcelProperty("审批备注")
    private String approvalRemark;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
