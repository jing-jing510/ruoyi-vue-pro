package cn.iocoder.yudao.module.coal.controller.admin.dailyreportattach.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 生产日报附件上传 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DailyReportAttachRespVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20703")
    @ExcelProperty("主键ID")
    private Long id;

    @Schema(description = "日报日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("日报日期")
    private LocalDate reportDate;

    @Schema(description = "日报文件URL（单文件）", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("日报文件URL（单文件）")
    private String reportFileUrl;

    @Schema(description = "图片URL，多个用英文逗号分隔")
    @ExcelProperty("图片URL，多个用英文逗号分隔")
    private String imageUrls;

    @Schema(description = "备注", example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
