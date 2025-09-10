package cn.iocoder.yudao.module.coal.controller.admin.safetyaccident.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 安全事故记录分页 Request VO")
@Data
public class SafetyAccidentPageReqVO extends PageParam {

    @Schema(description = "事故编号")
    private String accidentCode;

    @Schema(description = "事故类型", example = "2")
    private Integer accidentType;

    @Schema(description = "事故等级")
    private Integer accidentLevel;

    @Schema(description = "事故状态", example = "1")
    private Integer accidentStatus;

    @Schema(description = "审批状态", example = "1")
    private Integer approvalStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}