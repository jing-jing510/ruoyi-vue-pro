package cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 检修项目明细分页 Request VO")
@Data
public class MaintenanceOrderItemPageReqVO extends PageParam {

    @Schema(description = "检修单ID", example = "31721")
    private Long orderId;

    @Schema(description = "项目名称", example = "李四")
    private String itemName;

    @Schema(description = "项目类型", example = "1")
    private Integer itemType;

    @Schema(description = "项目描述", example = "你猜")
    private String itemDescription;

    @Schema(description = "项目状态", example = "1")
    private Integer itemStatus;

    @Schema(description = "完成时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] completionTime;

    @Schema(description = "完成质量")
    private Integer completionQuality;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}