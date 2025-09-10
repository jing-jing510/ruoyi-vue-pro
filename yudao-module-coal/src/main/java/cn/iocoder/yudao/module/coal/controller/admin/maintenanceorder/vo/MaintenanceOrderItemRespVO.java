package cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 检修项目明细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MaintenanceOrderItemRespVO {

    @Schema(description = "明细ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19169")
    @ExcelProperty("明细ID")
    private Long id;

    @Schema(description = "检修单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31721")
    @ExcelProperty("检修单ID")
    private Long orderId;

    @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("项目名称")
    private String itemName;

    @Schema(description = "项目类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "项目类型", converter = DictConvert.class)
    @DictFormat("maintenance_item_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer itemType;

    @Schema(description = "项目描述", example = "你猜")
    @ExcelProperty("项目描述")
    private String itemDescription;

    @Schema(description = "作业标准")
    @ExcelProperty("作业标准")
    private String workStandard;

    @Schema(description = "安全要求")
    @ExcelProperty("安全要求")
    private String safetyRequirements;

    @Schema(description = "所需工具")
    @ExcelProperty("所需工具")
    private String requiredTools;

    @Schema(description = "所需材料")
    @ExcelProperty("所需材料")
    private String requiredMaterials;

    @Schema(description = "预计工时(小时)")
    @ExcelProperty("预计工时(小时)")
    private Integer estimatedDuration;

    @Schema(description = "实际工时(小时)")
    @ExcelProperty("实际工时(小时)")
    private Integer actualDuration;

    @Schema(description = "项目状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "项目状态", converter = DictConvert.class)
    @DictFormat("maintenance_item_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer itemStatus;

    @Schema(description = "完成时间")
    @ExcelProperty("完成时间")
    private LocalDateTime completionTime;

    @Schema(description = "完成质量")
    @ExcelProperty(value = "完成质量", converter = DictConvert.class)
    @DictFormat("completion_quality") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer completionQuality;

    @Schema(description = "完成说明")
    @ExcelProperty("完成说明")
    private String completionNotes;

    @Schema(description = "检查人")
    @ExcelProperty("检查人")
    private String inspector;

    @Schema(description = "检查时间")
    @ExcelProperty("检查时间")
    private LocalDateTime inspectionTime;

    @Schema(description = "检查结果")
    @ExcelProperty("检查结果")
    private String inspectionResult;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}