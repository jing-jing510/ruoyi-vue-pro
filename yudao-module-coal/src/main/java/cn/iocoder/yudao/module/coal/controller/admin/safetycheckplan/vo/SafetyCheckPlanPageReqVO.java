package cn.iocoder.yudao.module.coal.controller.admin.safetycheckplan.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 安全检查计划分页 Request VO")
@Data
public class SafetyCheckPlanPageReqVO extends PageParam {

    @Schema(description = "计划编号")
    private String planCode;

    @Schema(description = "计划名称", example = "李四")
    private String planName;

    @Schema(description = "计划类型", example = "2")
    private Integer planType;

    @Schema(description = "计划状态", example = "2")
    private Integer planStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}