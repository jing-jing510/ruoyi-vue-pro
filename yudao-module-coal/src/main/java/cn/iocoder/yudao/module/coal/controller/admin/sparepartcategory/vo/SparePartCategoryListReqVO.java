package cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 备件分类表 (树表)列表 Request VO")
@Data
public class SparePartCategoryListReqVO {

    @Schema(description = "父分类ID (0=根分类)", example = "13378")
    private Long parentId;

    @Schema(description = "分类名称", example = "李四")
    private String categoryName;

    @Schema(description = "分类编码")
    private String categoryCode;

    @Schema(description = "分类层级：1大类 2中类 3小类")
    private Integer categoryLevel;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态：0禁用 1启用", example = "2")
    private Integer status;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}