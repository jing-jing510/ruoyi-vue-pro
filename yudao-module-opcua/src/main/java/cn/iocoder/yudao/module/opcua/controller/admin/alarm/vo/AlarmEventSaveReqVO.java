package cn.iocoder.yudao.module.opcua.controller.admin.alarm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报警事件新增/修改 Request VO")
@Data
public class AlarmEventSaveReqVO {

    @Schema(description = "报警ID", example = "1024")
    private Long id;

    @Schema(description = "OPC UA 配置ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "OPC UA 配置ID不能为空")
    private Long configId;

    @Schema(description = "设备名称", example = "设备A")
    private String deviceName;

    @Schema(description = "点位ID", example = "1")
    private Long tagId;

    @Schema(description = "点位名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "温度传感器")
    @NotBlank(message = "点位名称不能为空")
    private String tagName;

    @Schema(description = "OPC UA NodeId", example = "ns=3;i=1009")
    private String nodeId;

    @Schema(description = "报警级别", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "报警级别不能为空")
    private Integer alarmLevel;

    @Schema(description = "报警内容", requiredMode = Schema.RequiredMode.REQUIRED, example = "温度超过阈值")
    @NotBlank(message = "报警内容不能为空")
    private String alarmContent;

    @Schema(description = "报警时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "报警时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime alarmTime;

    @Schema(description = "报警状态", example = "0")
    private Integer status;

    @Schema(description = "备注", example = "手动添加的报警记录")
    private String remark;

}
