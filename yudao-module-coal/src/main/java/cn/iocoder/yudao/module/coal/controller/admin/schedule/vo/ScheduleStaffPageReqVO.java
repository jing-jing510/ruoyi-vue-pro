package cn.iocoder.yudao.module.coal.controller.admin.schedule.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 人员安排 (子表)分页 Request VO")
@Data
public class ScheduleStaffPageReqVO extends PageParam {

    @Schema(description = "排班ID (关联主表)", example = "27718")
    private Long scheduleId;

    @Schema(description = "班次ID (关联coal_shift_system的子节点)", example = "3370")
    private Long shiftId;

    @Schema(description = "员工ID", example = "272")
    private Long userId;

    @Schema(description = "岗位类型", example = "2")
    private Integer positionType;

    @Schema(description = "是否班组长")
    private Boolean isLeader;

    @Schema(description = "是否替班")
    private Boolean isSubstitute;

    @Schema(description = "工作状态", example = "2")
    private Integer workStatus;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "紧急联系人")
    private String emergencyContact;

    @Schema(description = "紧急联系电话")
    private String emergencyPhone;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}