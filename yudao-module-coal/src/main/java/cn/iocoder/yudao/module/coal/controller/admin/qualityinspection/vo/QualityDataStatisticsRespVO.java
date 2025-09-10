package cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 质量检测数据统计 Response VO
 *
 * @author 京京
 */
@Schema(description = "管理后台 - 质量检测数据统计 Response VO")
@Data
public class QualityDataStatisticsRespVO {

    @Schema(description = "总检测数据数量")
    private Long totalCount;

    @Schema(description = "合格数据数量")
    private Long qualifiedCount;

    @Schema(description = "不合格数据数量")
    private Long unqualifiedCount;

    @Schema(description = "合格率(%)")
    private Double qualifiedRate;

    @Schema(description = "今日检测数据数量")
    private Long todayCount;

    @Schema(description = "本月检测数据数量")
    private Long monthlyCount;

    @Schema(description = "复检数据数量")
    private Long retestCount;

}
