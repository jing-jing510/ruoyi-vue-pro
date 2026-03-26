package cn.iocoder.yudao.module.coal.controller.admin.marketwashingreport.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Schema(description = "管理后台 - 洗选分析报告 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MarketWashingReportRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27962")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "报告日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("报告日期")
    private LocalDate reportDate;

    @Schema(description = "洗选报告文件URL（单文件）", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("洗选报告文件URL（单文件）")
    private String reportFileUrl;

    @Schema(description = "图片URL，多个用英文逗号分隔")
    @ExcelProperty("图片URL，多个用英文逗号分隔")
    private String imageUrls;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

}
