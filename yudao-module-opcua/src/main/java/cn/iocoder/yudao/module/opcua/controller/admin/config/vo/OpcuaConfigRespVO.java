package cn.iocoder.yudao.module.opcua.controller.admin.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - OPC UA 配置 Response VO")
@Data
public class OpcuaConfigRespVO {

    @Schema(description = "配置ID", example = "1024")
    private Long id;

    @Schema(description = "配置名称", example = "WinCC服务器")
    private String name;

    @Schema(description = "OPC UA 服务器地址", example = "opc.tcp://localhost:4840")
    private String serverUrl;

    @Schema(description = "用户名", example = "admin")
    private String username;

    @Schema(description = "安全策略", example = "None")
    private String securityPolicy;

    @Schema(description = "安全模式", example = "None")
    private String securityMode;

    @Schema(description = "连接超时时间（毫秒）", example = "5000")
    private Integer timeout;

    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;

    @Schema(description = "备注", example = "生产线PLC服务器")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
