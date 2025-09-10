package cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 备件分类表 (树表) Response VO")
@Data
@ExcelIgnoreUnannotated
public class SparePartCategoryRespVO {

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27236")
    @ExcelProperty("分类ID")
    private Long id;

    @Schema(description = "父分类ID (0=根分类)", requiredMode = Schema.RequiredMode.REQUIRED, example = "13378")
    @ExcelProperty("父分类ID (0=根分类)")
    private Long parentId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("分类名称")
    private String categoryName;

    @Schema(description = "分类编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分类编码")
    private String categoryCode;

    @Schema(description = "分类层级：1大类 2中类 3小类", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分类层级：1大类 2中类 3小类")
    private Integer categoryLevel;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "状态：0禁用 1启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态：0禁用 1启用")
    private Integer status;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
