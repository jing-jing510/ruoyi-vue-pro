package cn.iocoder.yudao.module.coal.controller.admin.safetyattachment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 安全附件新增/修改 Request VO")
@Data
public class SafetyAttachmentSaveReqVO {

    @Schema(description = "附件ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11032")
    private Long id;

    @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "业务类型不能为空")
    private Integer businessType;

    @Schema(description = "业务ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1658")
    @NotNull(message = "业务ID不能为空")
    private Long businessId;

    @Schema(description = "文件名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
//    @NotEmpty(message = "文件名不能为空")
    private String fileName;

    @Schema(description = "文件路径", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotEmpty(message = "文件路径不能为空")
    private String filePath;

    @Schema(description = "文件大小(字节)", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotNull(message = "文件大小(字节)不能为空")
    private Long fileSize;

    @Schema(description = "文件类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
//    @NotEmpty(message = "文件类型不能为空")
    private String fileType;

    @Schema(description = "文件扩展名", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotEmpty(message = "文件扩展名不能为空")
    private String fileExtension;

    @Schema(description = "上传时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "上传时间不能为空")
    private LocalDateTime uploadTime;

    @Schema(description = "上传人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29463")
    @NotNull(message = "上传人ID不能为空")
    private Long uploaderId;

    @Schema(description = "上传人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "上传人姓名不能为空")
    private String uploaderName;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}