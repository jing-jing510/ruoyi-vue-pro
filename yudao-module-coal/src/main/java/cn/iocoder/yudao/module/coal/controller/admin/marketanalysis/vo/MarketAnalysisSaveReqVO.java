package cn.iocoder.yudao.module.coal.controller.admin.marketanalysis.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "管理后台 - 市场分析新增/修改 Request VO")
@Data
public class MarketAnalysisSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7000")
    private Long id;

    @Schema(description = "分析日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分析日期不能为空")
    private LocalDate analysisDate;

    @Schema(description = "市场分析文件URL（单文件）", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "市场分析文件URL（单文件）不能为空")
    private String reportFileUrl;

    @Schema(description = "图片URL，多个用英文逗号分隔")
    private String imageUrls;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}