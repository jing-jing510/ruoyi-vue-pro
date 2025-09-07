package cn.iocoder.yudao.module.coal.controller.admin.shiftsystem.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Schema(description = "管理后台 - 班制与班次设置 (树表) Response VO")
@Data
@ExcelIgnoreUnannotated
public class ShiftSystemRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8823")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "父ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25347")
    @ExcelProperty("父ID")
    private Long parentId;

    @Schema(description = "名称 ", requiredMode = Schema.RequiredMode.REQUIRED, example = "班制")
    @ExcelProperty("名称 ")
    private String name;

    @Schema(description = "编码", example = "123123")
    @ExcelProperty("编码")
    private String code;

    @Schema(description = "是否生产班制")
    @ExcelProperty(value = "是否生产班制", converter = DictConvert.class)
    @DictFormat("is_production") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Long isProduction;

    @Schema(description = "开始时间 ")
    @ExcelProperty("开始时间 ")
    private LocalTime startTime;

    @Schema(description = "结束时间 ")
    @ExcelProperty("结束时间 ")
    private LocalTime endTime;

    @Schema(description = "班次类型", example = "2")
    @ExcelProperty(value = "班次类型", converter = DictConvert.class)
    @DictFormat("shift_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer shiftType;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "状态：0禁用 1启用", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "状态：0禁用 1启用", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
