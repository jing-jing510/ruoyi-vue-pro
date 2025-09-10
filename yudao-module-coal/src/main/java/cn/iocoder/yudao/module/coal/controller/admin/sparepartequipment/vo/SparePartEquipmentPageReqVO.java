package cn.iocoder.yudao.module.coal.controller.admin.sparepartequipment.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 备件设备关联分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SparePartEquipmentPageReqVO extends PageParam {

    @Schema(description = "备件ID", example = "2048")
    private Long sparePartId;

    @Schema(description = "设备ID", example = "1024")
    private Long equipmentId;

    @Schema(description = "是否必需：1必需 0非必需", example = "1")
    private Integer isRequired;

    @Schema(description = "更换难度：1容易 2一般 3困难 4很困难", example = "3")
    private Integer replacementDifficulty;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
