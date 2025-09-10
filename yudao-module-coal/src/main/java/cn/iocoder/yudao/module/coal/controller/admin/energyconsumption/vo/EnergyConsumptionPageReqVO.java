package cn.iocoder.yudao.module.coal.controller.admin.energyconsumption.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 能源消耗记录分页 Request VO")
@Data
public class EnergyConsumptionPageReqVO extends PageParam {

    @Schema(description = "记录编号")
    private String recordCode;

    @Schema(description = "能源类型ID", example = "30734")
    private Long energyTypeId;

    @Schema(description = "消耗日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate[] consumptionDate;

    @Schema(description = "数据来源")
    private Integer dataSource;

    @Schema(description = "验证状态", example = "2")
    private Integer verificationStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}