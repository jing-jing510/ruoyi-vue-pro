package cn.iocoder.yudao.module.coal.controller.admin.schedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 人员安排 (子表) Response VO")
@Data
@ExcelIgnoreUnannotated
public class ScheduleStaffRespVO {

    @Schema(description = "人员安排ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "221")
    @ExcelProperty("人员安排ID")
    private Long id;

    @Schema(description = "排班ID (关联主表)", requiredMode = Schema.RequiredMode.REQUIRED, example = "27718")
    @ExcelProperty("排班ID (关联主表)")
    private Long scheduleId;

    @Schema(description = "班次ID (关联coal_shift_system的子节点)", requiredMode = Schema.RequiredMode.REQUIRED, example = "3370")
    @ExcelProperty("班次ID (关联coal_shift_system的子节点)")
    private Long shiftId;

    @Schema(description = "员工ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "272")
    @ExcelProperty("员工ID")
    private Long userId;

    @Schema(description = "岗位类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "岗位类型", converter = DictConvert.class)
    @DictFormat("position_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer positionType;

    @Schema(description = "是否班组长", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否班组长")
    private Boolean isLeader;

    @Schema(description = "是否替班", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否替班")
    private Boolean isSubstitute;

    @Schema(description = "替班原因", example = "不好")
    @ExcelProperty("替班原因")
    private String substituteReason;

    @Schema(description = "工作状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "工作状态", converter = DictConvert.class)
    @DictFormat("work_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer workStatus;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String contactPhone;

    @Schema(description = "紧急联系人")
    @ExcelProperty("紧急联系人")
    private String emergencyContact;

    @Schema(description = "紧急联系电话")
    @ExcelProperty("紧急联系电话")
    private String emergencyPhone;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}