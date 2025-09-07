package cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 备件设备关联 Response VO")
@Data
public class CoalSparePartEquipmentRespVO {

    @Schema(description = "关联ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long sparePartId;

    @Schema(description = "备件名称", example = "破碎机轴承")
    private String sparePartName;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long equipmentId;

    @Schema(description = "设备名称", example = "破碎机")
    private String equipmentName;

    @Schema(description = "使用数量", example = "2")
    private Integer usageCount;

    @Schema(description = "是否必需", example = "1")
    private Integer isRequired;

    @Schema(description = "备注", example = "关键备件")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
