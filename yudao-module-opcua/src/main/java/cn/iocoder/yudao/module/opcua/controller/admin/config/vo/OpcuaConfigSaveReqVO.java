package cn.iocoder.yudao.module.opcua.controller.admin.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - OPC UA 配置新增/修改 Request VO")
@Data
public class OpcuaConfigSaveReqVO {

    @Schema(description = "配置ID", example = "1024")
    private Long id;

    @Schema(description = "配置名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "WinCC服务器")
    @NotBlank(message = "配置名称不能为空")
    private String name;

    @Schema(description = "OPC UA 服务器地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "opc.tcp://localhost:4840")
    @NotBlank(message = "服务器地址不能为空")
    private String serverUrl;

    @Schema(description = "用户名", example = "admin")
    private String username;

    @Schema(description = "密码", example = "123456")
    private String password;

    @Schema(description = "安全策略", example = "None")
    private String securityPolicy;

    @Schema(description = "安全模式", example = "None")
    private String securityMode;

    @Schema(description = "连接超时时间（毫秒）", example = "5000")
    @NotNull(message = "连接超时时间不能为空")
    private Integer timeout;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "是否启用不能为空")
    private Boolean enabled;

    @Schema(description = "备注", example = "生产线PLC服务器")
    private String remark;

}
