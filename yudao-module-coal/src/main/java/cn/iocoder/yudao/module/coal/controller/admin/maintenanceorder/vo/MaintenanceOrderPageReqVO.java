package cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 检修单分页 Request VO")
@Data
public class MaintenanceOrderPageReqVO extends PageParam {

    @Schema(description = "检修单编号")
    private String orderCode;

    @Schema(description = "关联计划ID", example = "6584")
    private Long planId;

    @Schema(description = "关联报修单ID", example = "6585")
    private Long repairRequestId;

    @Schema(description = "设备ID", example = "24923")
    private Long equipmentId;

    @Schema(description = "设备名称", example = "李四")
    private String equipmentName;

    @Schema(description = "检修类型", example = "1")
    private Integer maintenanceType;

    @Schema(description = "检修级别")
    private Integer maintenanceLevel;

    @Schema(description = "检修单状态", example = "2")
    private Integer orderStatus;

    @Schema(description = "优先级")
    private Integer priorityLevel;

    @Schema(description = "实际开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "实际结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "质量评级")
    private Integer qualityRating;

    @Schema(description = "安全评级")
    private Integer safetyRating;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}