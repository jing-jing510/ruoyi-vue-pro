package cn.iocoder.yudao.module.coal.controller.admin.marketcostanalysis.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 综合成本核算 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MarketCostAnalysisRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23102")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "分析日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分析日期")
    private LocalDate analysisDate;

    @Schema(description = "成本核算文件URL（单文件）", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("成本核算文件URL（单文件）")
    private String reportFileUrl;

    @Schema(description = "图片URL，多个用英文逗号分隔")
    @ExcelProperty("图片URL，多个用英文逗号分隔")
    private String imageUrls;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
