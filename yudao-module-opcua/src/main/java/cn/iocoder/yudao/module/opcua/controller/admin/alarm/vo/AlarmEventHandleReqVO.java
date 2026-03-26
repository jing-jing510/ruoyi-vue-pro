package cn.iocoder.yudao.module.opcua.controller.admin.alarm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 报警事件处理 Request VO")
@Data
public class AlarmEventHandleReqVO {

    @Schema(description = "报警ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "报警ID不能为空")
    private Long id;

    @Schema(description = "处理状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "处理状态不能为空")
    private Integer status;

    @Schema(description = "处理意见", example = "已检查设备，温度正常")
    private String handleRemark;

}
