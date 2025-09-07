package cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备分类表 (树表) Response VO")
@Data
@ExcelIgnoreUnannotated
public class EquipmentCategoryRespVO {

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26853")
    @ExcelProperty("分类ID")
    private Long id;

    @Schema(description = "父分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21733")
    @ExcelProperty("父分类ID")
    private Long parentId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("分类名称")
    private String categoryName;

    @Schema(description = "分类编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分类编码")
    private String categoryCode;

    @Schema(description = "分类层级", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "分类层级", converter = DictConvert.class)
    @DictFormat("equipment_category_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer categoryLevel;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
