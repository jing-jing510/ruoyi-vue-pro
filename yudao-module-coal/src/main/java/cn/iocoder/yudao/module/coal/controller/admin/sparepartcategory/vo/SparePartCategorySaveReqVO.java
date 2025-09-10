package cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 备件分类表 (树表)新增/修改 Request VO")
@Data
public class SparePartCategorySaveReqVO {

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27236")
    private Long id;

    @Schema(description = "父分类ID (0=根分类)", requiredMode = Schema.RequiredMode.REQUIRED, example = "13378")
    @NotNull(message = "父分类ID (0=根分类)不能为空")
    private Long parentId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "分类名称不能为空")
    private String categoryName;

    @Schema(description = "分类编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "分类编码不能为空")
    private String categoryCode;

    @Schema(description = "分类层级：1大类 2中类 3小类", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分类层级：1大类 2中类 3小类不能为空")
    private Integer categoryLevel;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "状态：0禁用 1启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态：0禁用 1启用不能为空")
    private Integer status;

    @Schema(description = "备注", example = "随便")
    private String remark;

}