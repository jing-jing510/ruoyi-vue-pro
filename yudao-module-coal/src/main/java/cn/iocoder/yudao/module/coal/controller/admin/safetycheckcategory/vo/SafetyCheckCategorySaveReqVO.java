package cn.iocoder.yudao.module.coal.controller.admin.safetycheckcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 安全检查分类新增/修改 Request VO")
@Data
public class SafetyCheckCategorySaveReqVO {

    @Schema(description = "分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "18357")
    private Long id;

    @Schema(description = "父分类ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9869")
    @NotNull(message = "父分类ID不能为空")
    private Long parentId;

    @Schema(description = "分类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "分类名称不能为空")
    private String categoryName;

    @Schema(description = "分类编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "分类编码不能为空")
    private String categoryCode;

    @Schema(description = "分类类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "分类类型不能为空")
    private Integer categoryType;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "备注", example = "随便")
    private String remark;

}