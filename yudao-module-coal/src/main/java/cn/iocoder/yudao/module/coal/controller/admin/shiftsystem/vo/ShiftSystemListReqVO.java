package cn.iocoder.yudao.module.coal.controller.admin.shiftsystem.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 班制与班次设置 (树表)列表 Request VO")
@Data
public class ShiftSystemListReqVO {

    @Schema(description = "父ID", example = "25347")
    private Long parentId;

    @Schema(description = "名称 ", example = "班制")
    private String name;

    @Schema(description = "编码", example = "123123")
    private String code;

    @Schema(description = "班次类型", example = "2")
    private Integer shiftType;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}