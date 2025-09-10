package cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 检修单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MaintenanceOrderRespVO {

    @Schema(description = "检修单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27381")
    @ExcelProperty("检修单ID")
    private Long id;

    @Schema(description = "检修单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("检修单编号")
    private String orderCode;

    @Schema(description = "关联计划ID", example = "6584")
    @ExcelProperty("关联计划ID")
    private Long planId;

    @Schema(description = "关联报修单ID", example = "6585")
    @ExcelProperty("关联报修单ID")
    private Long repairRequestId;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24923")
    @ExcelProperty("设备ID")
    private Long equipmentId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("设备名称")
    private String equipmentName;

    @Schema(description = "检修类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "检修类型", converter = DictConvert.class)
    @DictFormat("maintenance_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer maintenanceType;

    @Schema(description = "检修级别", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "检修级别", converter = DictConvert.class)
    @DictFormat("maintenance_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer maintenanceLevel;

    @Schema(description = "检修单状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "检修单状态", converter = DictConvert.class)
    @DictFormat("maintenance_order_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer orderStatus;

    @Schema(description = "优先级", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "优先级", converter = DictConvert.class)
    @DictFormat("priority_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer priorityLevel;

    @Schema(description = "故障描述", example = "你说的对")
    @ExcelProperty("故障描述")
    private String faultDescription;

    @Schema(description = "检修内容")
    @ExcelProperty("检修内容")
    private String maintenanceContent;

    @Schema(description = "安全措施")
    @ExcelProperty("安全措施")
    private String safetyMeasures;

    @Schema(description = "实际开始时间")
    @ExcelProperty("实际开始时间")
    private LocalDateTime startTime;

    @Schema(description = "实际结束时间")
    @ExcelProperty("实际结束时间")
    private LocalDateTime endTime;

    @Schema(description = "实际工期(小时)")
    @ExcelProperty("实际工期(小时)")
    private Integer actualDuration;

    @Schema(description = "负责人", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("负责人")
    private String responsiblePerson;

    @Schema(description = "负责班组")
    @ExcelProperty("负责班组")
    private String responsibleTeam;

    @Schema(description = "参与人员")
    @ExcelProperty("参与人员")
    private String participants;

    @Schema(description = "作业环境")
    @ExcelProperty("作业环境")
    private String workEnvironment;

    @Schema(description = "天气条件")
    @ExcelProperty("天气条件")
    private String weatherCondition;

    @Schema(description = "完成进度(%)")
    @ExcelProperty("完成进度(%)")
    private Integer completionRate;

    @Schema(description = "质量评级")
    @ExcelProperty(value = "质量评级", converter = DictConvert.class)
    @DictFormat("quality_rating") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer qualityRating;

    @Schema(description = "安全评级")
    @ExcelProperty(value = "安全评级", converter = DictConvert.class)
    @DictFormat("safety_rating") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer safetyRating;

    @Schema(description = "检修结果")
    @ExcelProperty("检修结果")
    private String maintenanceResult;

    @Schema(description = "发现问题")
    @ExcelProperty("发现问题")
    private String problemsFound;

    @Schema(description = "改进建议")
    @ExcelProperty("改进建议")
    private String improvementSuggestions;

    @Schema(description = "下次检修日期")
    @ExcelProperty("下次检修日期")
    private LocalDate nextMaintenanceDate;

    @Schema(description = "总费用")
    @ExcelProperty("总费用")
    private BigDecimal totalCost;

    @Schema(description = "人工费用")
    @ExcelProperty("人工费用")
    private BigDecimal laborCost;

    @Schema(description = "材料费用")
    @ExcelProperty("材料费用")
    private BigDecimal materialCost;

    @Schema(description = "其他费用")
    @ExcelProperty("其他费用")
    private BigDecimal otherCost;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
