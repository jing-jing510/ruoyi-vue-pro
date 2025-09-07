package cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 备件设备关联分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class CoalSparePartEquipmentPageReqVO extends PageParam {

    @Schema(description = "备件ID", example = "1")
    private Long sparePartId;

    @Schema(description = "设备ID", example = "1")
    private Long equipmentId;

    @Schema(description = "是否必需", example = "1")
    private Integer isRequired;

}
