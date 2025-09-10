package cn.iocoder.yudao.module.coal.controller.admin.sparepartalert.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 备件预警记录分页 Request VO")
@Data
public class SparePartAlertPageReqVO extends PageParam {

    @Schema(description = "预警类型", example = "2")
    private Integer alertType;

    @Schema(description = "预警级别", example = "1")
    private Integer alertLevel;

    @Schema(description = "预警状态", example = "1")
    private Integer alertStatus;

    @Schema(description = "预警标题")
    private String alertTitle;

    @Schema(description = "预警信息")
    private String alertMessage;

    @Schema(description = "发送时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] sendTime;

    @Schema(description = "接收人列表(逗号分隔)")
    private String recipients;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}