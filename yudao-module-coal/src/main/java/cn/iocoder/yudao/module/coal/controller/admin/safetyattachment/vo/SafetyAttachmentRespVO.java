package cn.iocoder.yudao.module.coal.controller.admin.safetyattachment.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 安全附件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SafetyAttachmentRespVO {

    @Schema(description = "附件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11032")
    @ExcelProperty("附件ID")
    private Long id;

    @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("业务类型")
    private Integer businessType;

    @Schema(description = "业务ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1658")
    @ExcelProperty("业务ID")
    private Long businessId;

    @Schema(description = "文件名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("文件名")
    private String fileName;

    @Schema(description = "文件路径", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("文件路径")
    private String filePath;

    @Schema(description = "文件大小(字节)", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("文件大小(字节)")
    private Long fileSize;

    @Schema(description = "文件类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("文件类型")
    private String fileType;

    @Schema(description = "文件扩展名", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("文件扩展名")
    private String fileExtension;

    @Schema(description = "上传时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("上传时间")
    private LocalDateTime uploadTime;

    @Schema(description = "上传人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29463")
    @ExcelProperty("上传人ID")
    private Long uploaderId;

    @Schema(description = "上传人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("上传人姓名")
    private String uploaderName;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
