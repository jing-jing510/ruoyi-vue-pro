package cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 洗选分析报告分页 Request VO")
@Data
public class MarketWashingReportPageReqVO extends PageParam {

    @Schema(description = "报告日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] reportDate;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}