package cn.iocoder.yudao.module.coal.controller.admin.energytype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 能源类型配置新增/修改 Request VO")
@Data
public class EnergyTypeSaveReqVO {

    @Schema(description = "能源类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17879")
    private Long id;

    @Schema(description = "能源类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "能源类型编码不能为空")
    private String typeCode;

    @Schema(description = "能源类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "能源类型名称不能为空")
    private String typeName;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "计量单位不能为空")
    private String unit;

    @Schema(description = "单价(元/单位)", example = "26969")
    private BigDecimal unitPrice;

    @Schema(description = "是否实时采集", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否实时采集不能为空")
    private Boolean isRealTime;

    @Schema(description = "数据来源")
    private String dataSource;

    @Schema(description = "采集间隔(分钟)")
    private Integer collectionInterval;

    @Schema(description = "预警阈值")
    private BigDecimal warningThreshold;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "状态：0禁用 1启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态：0禁用 1启用不能为空")
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}