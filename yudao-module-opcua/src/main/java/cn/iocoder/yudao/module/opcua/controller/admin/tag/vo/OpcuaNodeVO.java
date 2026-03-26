package cn.iocoder.yudao.module.opcua.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * OPC UA 节点 VO
 *
 * @author 芋道源码
 */
@Schema(description = "管理后台 - OPC UA 节点 VO")
@Data
public class OpcuaNodeVO {

    @Schema(description = "节点ID", example = "ns=2;s=Temperature")
    private String nodeId;

    @Schema(description = "节点名称", example = "温度传感器")
    private String displayName;

    @Schema(description = "数据类型", example = "Float")
    private String dataType;

    @Schema(description = "当前值", example = "25.5")
    private String value;

    @Schema(description = "是否可读", example = "true")
    private Boolean readable;

    @Schema(description = "是否可写", example = "false")
    private Boolean writable;

}
