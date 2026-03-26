package cn.iocoder.yudao.module.coal.controller.admin.marketanalysis.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 市场分析分页 Request VO")
@Data
public class MarketAnalysisPageReqVO extends PageParam {

    @Schema(description = "分析日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] analysisDate;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}