package cn.iocoder.yudao.module.coal.controller.admin.qualityitem.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 质量检测项目分页 Request VO")
@Data
public class QualityItemPageReqVO extends PageParam {

    @Schema(description = "检测项目编码")
    private String itemCode;

    @Schema(description = "检测项目名称", example = "王五")
    private String itemName;

    @Schema(description = "检测类型", example = "2")
    private Integer itemType;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}