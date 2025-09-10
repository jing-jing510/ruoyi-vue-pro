package cn.iocoder.yudao.module.coal.controller.admin.energyalert.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 能源预警记录分页 Request VO")
@Data
public class EnergyAlertPageReqVO extends PageParam {

    @Schema(description = "预警编号")
    private String alertCode;

    @Schema(description = "能源类型ID", example = "15910")
    private Long energyTypeId;

    @Schema(description = "预警类型", example = "2")
    private Integer alertType;

    @Schema(description = "预警级别")
    private Integer alertLevel;

    @Schema(description = "预警状态", example = "2")
    private Integer alertStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}