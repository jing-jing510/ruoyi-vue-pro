package cn.iocoder.yudao.module.coal.controller.admin.energytype.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 能源类型配置分页 Request VO")
@Data
public class EnergyTypePageReqVO extends PageParam {

    @Schema(description = "能源类型编码")
    private String typeCode;

    @Schema(description = "能源类型名称", example = "芋艿")
    private String typeName;

    @Schema(description = "数据来源")
    private String dataSource;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}