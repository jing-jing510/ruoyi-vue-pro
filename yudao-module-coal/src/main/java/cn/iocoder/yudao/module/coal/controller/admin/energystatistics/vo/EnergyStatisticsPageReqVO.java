package cn.iocoder.yudao.module.coal.controller.admin.energystatistics.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 能源消耗统计分页 Request VO")
@Data
public class EnergyStatisticsPageReqVO extends PageParam {

    @Schema(description = "统计日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate[] statisticsDate;

    @Schema(description = "能源类型ID", example = "30734")
    private Long energyTypeId;

    @Schema(description = "统计类型", example = "2")
    private Integer statisticsType;

    @Schema(description = "统计状态", example = "1")
    private Integer statisticsStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}