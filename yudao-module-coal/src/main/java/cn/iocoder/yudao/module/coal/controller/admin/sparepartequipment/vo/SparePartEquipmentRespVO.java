package cn.iocoder.yudao.module.coal.controller.admin.sparepartequipment.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 备件设备关联 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SparePartEquipmentRespVO {

    @Schema(description = "关联ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("关联ID")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    @ExcelProperty("备件ID")
    private Long sparePartId;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @ExcelProperty("设备ID")
    private Long equipmentId;

    @Schema(description = "单次使用数量", example = "1")
    @ExcelProperty("单次使用数量")
    private Integer usageCount;

    @Schema(description = "安装位置", example = "主轴承座")
    @ExcelProperty("安装位置")
    private String installPosition;

    @Schema(description = "是否必需：1必需 0非必需", example = "1")
    @ExcelProperty("是否必需")
    private Integer isRequired;

    @Schema(description = "更换难度：1容易 2一般 3困难 4很困难", example = "3")
    @ExcelProperty("更换难度")
    private Integer replacementDifficulty;

    @Schema(description = "更换时间(分钟)", example = "60")
    @ExcelProperty("更换时间(分钟)")
    private Integer replacementTime;

    @Schema(description = "所需工具", example = "扳手、起重机")
    @ExcelProperty("所需工具")
    private String toolsRequired;

    @Schema(description = "安全要求", example = "停机后进行，戴安全帽")
    @ExcelProperty("安全要求")
    private String safetyRequirements;

    @Schema(description = "备注", example = "定期检查")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    // 扩展字段，用于显示关联信息
    @Schema(description = "备件名称", example = "轴承")
    @ExcelProperty("备件名称")
    private String sparePartName;

    @Schema(description = "设备名称", example = "破碎机")
    @ExcelProperty("设备名称")
    private String equipmentName;

}
