package cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备分类表 (树表)列表 Request VO")
@Data
public class EquipmentCategoryListReqVO {

    @Schema(description = "父分类ID", example = "21733")
    private Long parentId;

    @Schema(description = "分类名称", example = "赵六")
    private String categoryName;

    @Schema(description = "分类编码")
    private String categoryCode;

    @Schema(description = "分类层级")
    private Integer categoryLevel;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}