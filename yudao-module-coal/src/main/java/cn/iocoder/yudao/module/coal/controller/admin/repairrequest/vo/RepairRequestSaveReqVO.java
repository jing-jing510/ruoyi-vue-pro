package cn.iocoder.yudao.module.coal.controller.admin.repairrequest.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 报修单新增/修改 Request VO")
@Data
public class RepairRequestSaveReqVO {

    @Schema(description = "报修单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26864")
    private Long id;

    @Schema(description = "报修单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "报修单编号不能为空")
    private String requestCode;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11113")
    @NotNull(message = "设备ID不能为空")
    private Long equipmentId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "设备名称不能为空")
    private String equipmentName;

    @Schema(description = "设备位置")
    private String equipmentLocation;

    @Schema(description = "故障类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "故障类型不能为空")
    private Integer faultType;

    @Schema(description = "故障级别", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "故障级别不能为空")
    private Integer faultLevel;

    @Schema(description = "故障描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    @NotEmpty(message = "故障描述不能为空")
    private String faultDescription;

    @Schema(description = "故障现象")
    private String faultSymptoms;

    @Schema(description = "故障原因分析")
    private String faultCause;

    @Schema(description = "影响评估")
    private String impactAssessment;

    @Schema(description = "紧急程度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "紧急程度不能为空")
    private Integer urgencyLevel;

    @Schema(description = "报修状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "报修状态不能为空")
    private Integer requestStatus;

    @Schema(description = "报修人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31314")
    @NotNull(message = "报修人ID不能为空")
    private Long reporterId;

    @Schema(description = "报修人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "报修人姓名不能为空")
    private String reporterName;

    @Schema(description = "报修人电话")
    private String reporterPhone;

    @Schema(description = "报修时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "报修时间不能为空")
    private LocalDateTime reportTime;

    @Schema(description = "故障照片(JSON格式)")
    private String faultPhotos;

    @Schema(description = "故障视频(JSON格式)")
    private String faultVideos;

    @Schema(description = "故障语音(JSON格式)")
    private String faultAudio;

    @Schema(description = "指派处理人")
    private String assignedPerson;

    @Schema(description = "指派班组")
    private String assignedTeam;

    @Schema(description = "派单时间")
    private LocalDateTime assignmentTime;

    @Schema(description = "预计修复时间")
    private LocalDateTime expectedRepairTime;

    @Schema(description = "实际开始时间")
    private LocalDateTime actualStartTime;

    @Schema(description = "实际结束时间")
    private LocalDateTime actualEndTime;

    @Schema(description = "修复耗时(小时)")
    private Integer repairDuration;

    @Schema(description = "修复方法")
    private String repairMethod;

    @Schema(description = "更换部件(JSON格式)")
    private String replacedParts;

    @Schema(description = "修复费用")
    private BigDecimal repairCost;

    @Schema(description = "修复质量")
    private Integer repairQuality;

    @Schema(description = "测试结果")
    private String testResult;

    @Schema(description = "预防措施")
    private String preventionMeasures;

    @Schema(description = "满意度评价")
    private Integer satisfactionRating;

    @Schema(description = "反馈意见")
    private String feedbackNotes;

    @Schema(description = "关闭时间")
    private LocalDateTime closeTime;

    @Schema(description = "关闭原因", example = "不喜欢")
    private String closeReason;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}