package cn.iocoder.yudao.module.opcua.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 点位配置 Response VO")
@Data
public class TagConfigRespVO {

    @Schema(description = "点位ID", example = "1024")
    private Long id;

    @Schema(description = "OPC UA 配置ID", example = "1024")
    private Long configId;

    @Schema(description = "设备名称", example = "1号生产线")
    private String deviceName;

    @Schema(description = "点位名称", example = "温度传感器")
    private String name;

    @Schema(description = "OPC UA NodeId", example = "ns=2;s=Temperature")
    private String nodeId;

    @Schema(description = "数据类型", example = "Float")
    private String dataType;

    @Schema(description = "是否为报警点位", example = "true")
    private Boolean isAlarm;

    @Schema(description = "报警级别", example = "2")
    private Integer alarmLevel;

    @Schema(description = "报警内容", example = "温度传感器报警")
    private String alarmContent;

    @Schema(description = "上次采集值", example = "0")
    private String lastValue;

    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;

    @Schema(description = "备注", example = "监控生产线温度")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
