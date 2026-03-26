package cn.iocoder.yudao.module.opcua.controller.admin.tag.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 点位配置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TagConfigPageReqVO extends PageParam {

    @Schema(description = "OPC UA 配置ID", example = "1024")
    private Long configId;

    @Schema(description = "设备名称", example = "1号生产线")
    private String deviceName;

    @Schema(description = "点位名称", example = "温度传感器")
    private String name;

    @Schema(description = "是否为报警点位", example = "true")
    private Boolean isAlarm;

    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
