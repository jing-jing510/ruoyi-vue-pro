package cn.iocoder.yudao.module.coal.controller.admin.sparepartusagerecord.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 备件使用记录分页 Request VO")
@Data
public class SparePartUsageRecordPageReqVO extends PageParam {

    @Schema(description = "使用类型", example = "2")
    private Integer usageType;

    @Schema(description = "使用日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] usageDate;

    @Schema(description = "性能评级")
    private Integer performanceRating;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "工单ID(关联检修单)", example = "1")
    private Long workOrderId;

    @Schema(description = "设备ID", example = "1")
    private Long equipmentId;

}