package cn.iocoder.yudao.module.coal.controller.admin.sparepartstock.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 备件库存记录分页 Request VO")
@Data
public class SparePartStockPageReqVO extends PageParam {

    @Schema(description = "仓库位置")
    private String warehouseLocation;

    @Schema(description = "库存类型", example = "1")
    private Integer stockType;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}