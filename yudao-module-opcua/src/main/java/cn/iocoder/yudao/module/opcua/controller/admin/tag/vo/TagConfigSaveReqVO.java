package cn.iocoder.yudao.module.opcua.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 点位配置新增/修改 Request VO")
@Data
public class TagConfigSaveReqVO {

    @Schema(description = "点位ID", example = "1024")
    private Long id;

    @Schema(description = "OPC UA 配置ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "OPC UA 配置ID不能为空")
    private Long configId;

    @Schema(description = "设备名称", example = "1号生产线")
    private String deviceName;

    @Schema(description = "点位名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "温度传感器")
    @NotBlank(message = "点位名称不能为空")
    private String name;

    @Schema(description = "OPC UA NodeId", requiredMode = Schema.RequiredMode.REQUIRED, example = "ns=2;s=Temperature")
    @NotBlank(message = "NodeId不能为空")
    private String nodeId;

    @Schema(description = "数据类型", example = "Float")
    private String dataType;

    @Schema(description = "是否为报警点位", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "是否为报警点位不能为空")
    private Boolean isAlarm;

    @Schema(description = "报警级别", example = "2")
    private Integer alarmLevel;

    @Schema(description = "报警内容", example = "温度传感器报警")
    private String alarmContent;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "是否启用不能为空")
    private Boolean enabled;

    @Schema(description = "备注", example = "监控生产线温度")
    private String remark;

}
