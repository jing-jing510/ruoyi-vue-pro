package cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 备件预警记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class CoalSparePartAlertPageReqVO extends PageParam {

    @Schema(description = "备件ID", example = "1")
    private Long sparePartId;

    @Schema(description = "仓库ID", example = "1")
    private Long warehouseId;

    @Schema(description = "预警类型", example = "1")
    private Integer alertType;

    @Schema(description = "预警级别", example = "1")
    private Integer alertLevel;

    @Schema(description = "预警状态", example = "1")
    private Integer alertStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
