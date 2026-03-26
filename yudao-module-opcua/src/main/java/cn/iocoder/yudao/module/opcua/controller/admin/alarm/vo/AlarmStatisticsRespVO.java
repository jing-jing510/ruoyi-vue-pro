package cn.iocoder.yudao.module.opcua.controller.admin.alarm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 报警统计 Response VO")
@Data
public class AlarmStatisticsRespVO {

    @Schema(description = "按状态统计")
    private List<Map<String, Object>> statusStatistics;

    @Schema(description = "按设备统计")
    private List<Map<String, Object>> deviceStatistics;

    @Schema(description = "按级别统计")
    private List<Map<String, Object>> levelStatistics;

    @Schema(description = "总报警数", example = "100")
    private Long totalCount;

}
