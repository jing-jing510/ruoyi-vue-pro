package cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 设备分类表 (树表)新增/修改 Request VO")
@Data
public class EquipmentCategorySaveReqVO {

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26853")
    private Long id;

    @Schema(description = "父分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21733")
    @NotNull(message = "父分类ID不能为空")
    private Long parentId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "分类名称不能为空")
    private String categoryName;

    @Schema(description = "分类编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "分类编码不能为空")
    private String categoryCode;

    @Schema(description = "分类层级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分类层级不能为空")
    private Integer categoryLevel;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}