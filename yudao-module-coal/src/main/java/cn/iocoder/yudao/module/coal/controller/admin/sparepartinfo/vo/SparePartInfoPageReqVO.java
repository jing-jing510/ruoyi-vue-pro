package cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 备件基础信息分页 Request VO")
@Data
public class SparePartInfoPageReqVO extends PageParam {

    @Schema(description = "备件编号")
    private String sparePartCode;

    @Schema(description = "备件名称", example = "张三")
    private String sparePartName;

    @Schema(description = "备件分类ID", example = "21844")
    private Long categoryId;

    @Schema(description = "备件类型", example = "2")
    private Integer sparePartType;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}