package cn.iocoder.yudao.module.coal.controller.admin.shiftsystem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Schema(description = "管理后台 - 班制与班次设置 (树表)新增/修改 Request VO")
@Data
public class ShiftSystemSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8823")
    private Long id;

    @Schema(description = "父ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25347")
    @NotNull(message = "父ID不能为空")
    private Long parentId;

    @Schema(description = "名称 ", requiredMode = Schema.RequiredMode.REQUIRED, example = "班制")
    @NotEmpty(message = "名称 不能为空")
    private String name;

    @Schema(description = "编码", example = "123123")
    private String code;

    @Schema(description = "是否生产班制")
    private Long isProduction;

    @Schema(description = "开始时间 ")
    private LocalTime startTime;

    @Schema(description = "结束时间 ")
    private LocalTime endTime;

    @Schema(description = "班次类型", example = "2")
    private Integer shiftType;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "状态：0禁用 1启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态：0禁用 1启用不能为空")
    private Integer status;

}