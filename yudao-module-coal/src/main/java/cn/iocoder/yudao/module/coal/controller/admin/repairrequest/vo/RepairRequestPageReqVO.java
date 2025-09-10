package cn.iocoder.yudao.module.coal.controller.admin.repairrequest.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报修单分页 Request VO")
@Data
public class RepairRequestPageReqVO extends PageParam {

    @Schema(description = "报修单编号")
    private String requestCode;

    @Schema(description = "设备ID", example = "11113")
    private Long equipmentId;

    @Schema(description = "设备名称", example = "李四")
    private String equipmentName;

    @Schema(description = "故障类型", example = "2")
    private Integer faultType;

    @Schema(description = "故障级别")
    private Integer faultLevel;

    @Schema(description = "紧急程度")
    private Integer urgencyLevel;

    @Schema(description = "报修状态", example = "1")
    private Integer requestStatus;

    @Schema(description = "修复质量")
    private Integer repairQuality;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}