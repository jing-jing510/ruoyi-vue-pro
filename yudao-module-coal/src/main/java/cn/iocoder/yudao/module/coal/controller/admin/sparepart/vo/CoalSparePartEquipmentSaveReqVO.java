package cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 备件设备关联新增/修改 Request VO")
@Data
public class CoalSparePartEquipmentSaveReqVO {

    @Schema(description = "关联ID，更新时必传", example = "1")
    private Long id;

    @Schema(description = "备件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "备件ID不能为空")
    private Long sparePartId;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "设备ID不能为空")
    private Long equipmentId;

    @Schema(description = "使用数量", example = "2")
    private Integer usageCount;

    @Schema(description = "是否必需", example = "1")
    private Integer isRequired;

    @Schema(description = "备注", example = "关键备件")
    private String remark;

}
