package cn.iocoder.yudao.module.opcua.controller.admin.alarm.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报警事件分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AlarmEventPageReqVO extends PageParam {

    @Schema(description = "OPC UA 配置ID", example = "1024")
    private Long configId;

    @Schema(description = "设备名称", example = "1号生产线")
    private String deviceName;

    @Schema(description = "报警级别", example = "2")
    private Integer alarmLevel;

    @Schema(description = "报警状态", example = "0")
    private Integer status;

    @Schema(description = "报警时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] alarmTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
