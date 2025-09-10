package cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 质量检测数据分页 Request VO")
@Data
public class QualityDataPageReqVO extends PageParam {

    @Schema(description = "检测项目编码")
    private String qualityItemCode;

    @Schema(description = "检测项目名称", example = "芋艿")
    private String qualityItemName;

    @Schema(description = "是否合格")
    private Integer isQualified;

    @Schema(description = "是否复检")
    private Integer isRetest;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}