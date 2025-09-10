package cn.iocoder.yudao.module.coal.controller.admin.sparepartequipment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - 备件设备关联新增/修改 Request VO")
@Data
public class SparePartEquipmentSaveReqVO {

    @Schema(description = "关联ID", example = "1024")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    @NotNull(message = "备件ID不能为空")
    private Long sparePartId;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "设备ID不能为空")
    private Long equipmentId;

    @Schema(description = "单次使用数量", example = "1")
    private Integer usageCount;

    @Schema(description = "安装位置", example = "主轴承座")
    private String installPosition;

    @Schema(description = "是否必需：1必需 0非必需", example = "1")
    private Integer isRequired;

    @Schema(description = "更换难度：1容易 2一般 3困难 4很困难", example = "3")
    private Integer replacementDifficulty;

    @Schema(description = "更换时间(分钟)", example = "60")
    private Integer replacementTime;

    @Schema(description = "所需工具", example = "扳手、起重机")
    private String toolsRequired;

    @Schema(description = "安全要求", example = "停机后进行，戴安全帽")
    private String safetyRequirements;

    @Schema(description = "备注", example = "定期检查")
    private String remark;

}
