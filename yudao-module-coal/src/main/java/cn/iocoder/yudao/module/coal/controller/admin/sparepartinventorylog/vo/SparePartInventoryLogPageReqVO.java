package cn.iocoder.yudao.module.coal.controller.admin.sparepartinventorylog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 备件出入库记录分页 Request VO")
@Data
public class SparePartInventoryLogPageReqVO extends PageParam {

    @Schema(description = "操作类型", example = "1")
    private Integer operationType;

    @Schema(description = "仓库位置")
    private String warehouseLocation;

    @Schema(description = "审批人ID", example = "4695")
    private Long approverId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}