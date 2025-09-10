package cn.iocoder.yudao.module.coal.controller.admin.safetyattachment.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 安全附件分页 Request VO")
@Data
public class SafetyAttachmentPageReqVO extends PageParam {

    @Schema(description = "业务类型", example = "2")
    private Integer businessType;

    @Schema(description = "业务ID", example = "1658")
    private Long businessId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}