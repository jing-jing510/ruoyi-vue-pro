package cn.iocoder.yudao.module.coal.controller.admin.safetycheckcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 安全检查分类 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SafetyCheckCategoryRespVO {

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18357")
    @ExcelProperty("分类ID")
    private Long id;

    @Schema(description = "父分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9869")
    @ExcelProperty("父分类ID")
    private Long parentId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("分类名称")
    private String categoryName;

    @Schema(description = "分类编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分类编码")
    private String categoryCode;

    @Schema(description = "分类类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "分类类型", converter = DictConvert.class)
    @DictFormat("coal_safety_category_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer categoryType;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("显示顺序")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}