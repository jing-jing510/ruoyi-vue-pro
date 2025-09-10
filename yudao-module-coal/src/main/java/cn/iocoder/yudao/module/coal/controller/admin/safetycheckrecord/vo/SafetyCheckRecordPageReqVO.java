package cn.iocoder.yudao.module.coal.controller.admin.safetycheckrecord.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 安全检查记录分页 Request VO")
@Data
public class SafetyCheckRecordPageReqVO extends PageParam {

    @Schema(description = "记录编号")
    private String recordCode;

    @Schema(description = "计划ID", example = "11940")
    private Long planId;

    @Schema(description = "检查类型", example = "2")
    private Integer checkType;

    @Schema(description = "检查分类ID", example = "20297")
    private Long checkCategoryId;

    @Schema(description = "记录状态", example = "1")
    private Integer recordStatus;

}