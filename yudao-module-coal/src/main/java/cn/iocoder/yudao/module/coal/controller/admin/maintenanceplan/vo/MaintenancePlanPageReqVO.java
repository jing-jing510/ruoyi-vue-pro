package cn.iocoder.yudao.module.coal.controller.admin.maintenanceplan.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 检修计划分页 Request VO")
@Data
public class MaintenancePlanPageReqVO extends PageParam {

    @Schema(description = "计划编号")
    private String planCode;

    @Schema(description = "计划名称", example = "芋艿")
    private String planName;

    @Schema(description = "计划类型", example = "2")
    private Integer planType;

    @Schema(description = "计划状态", example = "1")
    private Integer planStatus;

    @Schema(description = "设备ID", example = "26812")
    private Long equipmentId;

    @Schema(description = "设备名称", example = "张三")
    private String equipmentName;

    @Schema(description = "检修类型", example = "2")
    private Integer maintenanceType;

    @Schema(description = "检修级别")
    private Integer maintenanceLevel;

    @Schema(description = "计划开始时间")
    private LocalDateTime plannedStartTime;

    @Schema(description = "计划结束时间")
    private LocalDateTime plannedEndTime;

}