package cn.iocoder.yudao.module.coal.controller.admin.schedule.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 排班管理 (主表) Response VO")
@Data
@ExcelIgnoreUnannotated
public class ScheduleRespVO {

    @Schema(description = "排班ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7783")
    @ExcelProperty("排班ID")
    private Long id;

    @Schema(description = "排班日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排班日期")
    private LocalDate scheduleDate;

    @Schema(description = "班制ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4172")
    @ExcelProperty("班制ID")
    private Long shiftSystemId;

    @Schema(description = "排班状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "排班状态", converter = DictConvert.class)
    @DictFormat("schedule_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer scheduleStatus;

    @Schema(description = "是否生产日", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "是否生产日", converter = DictConvert.class)
    @DictFormat("is_production_day") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean isProductionDay;

    @Schema(description = "当日生产目标(吨)")
    @ExcelProperty("当日生产目标(吨)")
    private BigDecimal productionTarget;

    @Schema(description = "排班类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "排班类型", converter = DictConvert.class)
    @DictFormat("schedule_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer scheduleType;

    @Schema(description = "审批人ID", example = "27681")
    @ExcelProperty("审批人ID")
    private Long approverId;

    @Schema(description = "审批时间")
    @ExcelProperty("审批时间")
    private LocalDateTime approveTime;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
