package cn.iocoder.yudao.module.opcua.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 设备信息新增/修改 Request VO")
@Data
public class DeviceSaveReqVO {

    @Schema(description = "设备ID", example = "1024")
    private Long id;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "DEV001")
    @NotBlank(message = "设备编码不能为空")
    private String code;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1号生产线")
    @NotBlank(message = "设备名称不能为空")
    private String name;

    @Schema(description = "设备类型", example = "生产设备")
    private String type;

    @Schema(description = "设备位置", example = "车间A-1区")
    private String location;

    @Schema(description = "负责人ID", example = "1")
    private Long responsibleUserId;

    @Schema(description = "负责人姓名", example = "张三")
    private String responsibleUserName;

    @Schema(description = "OPC UA 配置ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "OPC UA 配置不能为空")
    private Long configId;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "是否启用不能为空")
    private Boolean enabled;

    @Schema(description = "备注", example = "主要生产线设备")
    private String remark;

}
