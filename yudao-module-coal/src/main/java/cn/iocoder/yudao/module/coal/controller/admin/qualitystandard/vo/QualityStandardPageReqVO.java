package cn.iocoder.yudao.module.coal.controller.admin.qualitystandard.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 质量标准分页 Request VO")
@Data
public class QualityStandardPageReqVO extends PageParam {

    @Schema(description = "标准编码")
    private String standardCode;

    @Schema(description = "标准名称", example = "张三")
    private String standardName;

    @Schema(description = "产品类型", example = "1")
    private Integer productType;

    @Schema(description = "检测项目ID", example = "5371")
    private Long qualityItemId;

    @Schema(description = "标准类型", example = "2")
    private Integer standardType;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}