package cn.iocoder.yudao.module.coal.controller.admin.maintenanceplan.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 检修计划 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MaintenancePlanRespVO {

    @Schema(description = "计划ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24465")
    @ExcelProperty("计划ID")
    private Long id;

    @Schema(description = "计划编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计划编号")
    private String planCode;

    @Schema(description = "计划名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("计划名称")
    private String planName;

    @Schema(description = "计划类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "计划类型", converter = DictConvert.class)
    @DictFormat("plan_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer planType;

    @Schema(description = "计划状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "计划状态", converter = DictConvert.class)
    @DictFormat("plan_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer planStatus;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26812")
    @ExcelProperty("设备ID")
    private Long equipmentId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("设备名称")
    private String equipmentName;

    @Schema(description = "检修类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "检修类型", converter = DictConvert.class)
    @DictFormat("maintenance_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer maintenanceType;

    @Schema(description = "检修级别", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "检修级别", converter = DictConvert.class)
    @DictFormat("maintenance_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer maintenanceLevel;

    @Schema(description = "计划开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计划开始时间")
    private LocalDateTime plannedStartTime;

    @Schema(description = "计划结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计划结束时间")
    private LocalDateTime plannedEndTime;

    @Schema(description = "预计工期(小时)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("预计工期(小时)")
    private Integer estimatedDuration;

    @Schema(description = "负责人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("负责人")
    private String responsiblePerson;

    @Schema(description = "负责班组")
    @ExcelProperty("负责班组")
    private String responsibleTeam;

    @Schema(description = "检修内容")
    @ExcelProperty("检修内容")
    private String maintenanceContent;

    @Schema(description = "安全要求")
    @ExcelProperty("安全要求")
    private String safetyRequirements;

    @Schema(description = "所需工具")
    @ExcelProperty("所需工具")
    private String requiredTools;

    @Schema(description = "所需材料")
    @ExcelProperty("所需材料")
    private String requiredMaterials;

    @Schema(description = "预算费用")
    @ExcelProperty("预算费用")
    private BigDecimal budgetCost;

    @Schema(description = "实际费用")
    @ExcelProperty("实际费用")
    private BigDecimal actualCost;

    @Schema(description = "审批状态", example = "1")
    @ExcelProperty(value = "审批状态", converter = DictConvert.class)
    @DictFormat("approve_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer approvalStatus;

    @Schema(description = "审批人ID", example = "29047")
    @ExcelProperty("审批人ID")
    private Long approverId;

    @Schema(description = "审批时间")
    @ExcelProperty("审批时间")
    private LocalDateTime approvalTime;

    @Schema(description = "审批备注", example = "你说的对")
    @ExcelProperty("审批备注")
    private String approvalRemark;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}