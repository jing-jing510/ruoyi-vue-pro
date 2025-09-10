package cn.iocoder.yudao.module.coal.controller.admin.energytype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 能源类型配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class EnergyTypeRespVO {

    @Schema(description = "能源类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17879")
    @ExcelProperty("能源类型ID")
    private Long id;

    @Schema(description = "能源类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("能源类型编码")
    private String typeCode;

    @Schema(description = "能源类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("能源类型名称")
    private String typeName;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计量单位")
    private String unit;

    @Schema(description = "单价(元/单位)", example = "26969")
    @ExcelProperty("单价(元/单位)")
    private BigDecimal unitPrice;

    @Schema(description = "是否实时采集", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否实时采集", converter = DictConvert.class)
    @DictFormat("coal_energy_is_real_time") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean isRealTime;

    @Schema(description = "数据来源")
    @ExcelProperty(value = "数据来源", converter = DictConvert.class)
    @DictFormat("coal_energy_data_source") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String dataSource;

    @Schema(description = "采集间隔(分钟)")
    @ExcelProperty("采集间隔(分钟)")
    private Integer collectionInterval;

    @Schema(description = "预警阈值")
    @ExcelProperty("预警阈值")
    private BigDecimal warningThreshold;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "状态：0禁用 1启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "状态：0禁用 1启用", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}