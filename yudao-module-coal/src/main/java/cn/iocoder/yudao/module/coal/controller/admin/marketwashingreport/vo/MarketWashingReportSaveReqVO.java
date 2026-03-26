package cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Schema(description = "管理后台 - 洗选分析报告新增/修改 Request VO")
@Data
public class MarketWashingReportSaveReqVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27962")
    private Long id;

    @Schema(description = "报告日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "报告日期不能为空")
    private LocalDate reportDate;

    @Schema(description = "洗选报告文件URL（单文件）", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "洗选报告文件URL（单文件）不能为空")
    private String reportFileUrl;

    @Schema(description = "图片URL，多个用英文逗号分隔")
    private String imageUrls;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}