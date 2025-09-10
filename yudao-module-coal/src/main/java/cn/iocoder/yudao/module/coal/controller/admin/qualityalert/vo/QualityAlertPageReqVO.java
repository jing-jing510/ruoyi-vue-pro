package cn.iocoder.yudao.module.coal.controller.admin.qualityalert.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 质量预警记录分页 Request VO")
@Data
public class QualityAlertPageReqVO extends PageParam {

    @Schema(description = "预警编号")
    private String alertCode;

    @Schema(description = "预警类型", example = "2")
    private Integer alertType;

    @Schema(description = "预警级别")
    private Integer alertLevel;

    @Schema(description = "检测项目ID", example = "28743")
    private Long qualityItemId;

    @Schema(description = "关联检测记录ID", example = "1430")
    private Long inspectionId;

    @Schema(description = "产品类型", example = "2")
    private Integer productType;

    @Schema(description = "预警状态", example = "1")
    private Integer alertStatus;

    @Schema(description = "是否自动预警：0手动 1自动")
    private Integer isAutoAlert;

    @Schema(description = "是否已发送通知：0否 1是")
    private Integer notificationSent;

    @Schema(description = "预警时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] alertTime;

}