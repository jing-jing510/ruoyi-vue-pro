package cn.iocoder.yudao.module.coal.controller.admin.safetycheckcategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 安全检查分类列表 Request VO")
@Data
public class SafetyCheckCategoryListReqVO {

    @Schema(description = "父分类ID", example = "9869")
    private Long parentId;

    @Schema(description = "分类名称", example = "芋艿")
    private String categoryName;

    @Schema(description = "分类编码")
    private String categoryCode;

    @Schema(description = "分类类型", example = "1")
    private Integer categoryType;

    @Schema(description = "显示顺序")
    private Integer sort;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}