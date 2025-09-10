package cn.iocoder.yudao.module.coal.controller.admin.repairrequest.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 报修单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RepairRequestRespVO {

    @Schema(description = "报修单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "26864")
    @ExcelProperty("报修单ID")
    private Long id;

    @Schema(description = "报修单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("报修单编号")
    private String requestCode;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11113")
    @ExcelProperty("设备ID")
    private Long equipmentId;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("设备名称")
    private String equipmentName;

    @Schema(description = "设备位置")
    @ExcelProperty("设备位置")
    private String equipmentLocation;

    @Schema(description = "故障类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "故障类型", converter = DictConvert.class)
    @DictFormat("fault_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer faultType;

    @Schema(description = "故障级别", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "故障级别", converter = DictConvert.class)
    @DictFormat("fault_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer faultLevel;

    @Schema(description = "故障描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    @ExcelProperty("故障描述")
    private String faultDescription;

    @Schema(description = "故障现象")
    @ExcelProperty("故障现象")
    private String faultSymptoms;

    @Schema(description = "故障原因分析")
    @ExcelProperty("故障原因分析")
    private String faultCause;

    @Schema(description = "影响评估")
    @ExcelProperty("影响评估")
    private String impactAssessment;

    @Schema(description = "紧急程度", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "紧急程度", converter = DictConvert.class)
    @DictFormat("urgency_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer urgencyLevel;

    @Schema(description = "报修状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "报修状态", converter = DictConvert.class)
    @DictFormat("repair_request_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer requestStatus;

    @Schema(description = "报修人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31314")
    @ExcelProperty("报修人ID")
    private Long reporterId;

    @Schema(description = "报修人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @ExcelProperty("报修人姓名")
    private String reporterName;

    @Schema(description = "报修人电话")
    @ExcelProperty("报修人电话")
    private String reporterPhone;

    @Schema(description = "报修时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("报修时间")
    private LocalDateTime reportTime;

    @Schema(description = "故障照片(JSON格式)")
    @ExcelProperty("故障照片(JSON格式)")
    private String faultPhotos;

    @Schema(description = "故障视频(JSON格式)")
    @ExcelProperty("故障视频(JSON格式)")
    private String faultVideos;

    @Schema(description = "故障语音(JSON格式)")
    @ExcelProperty("故障语音(JSON格式)")
    private String faultAudio;

    @Schema(description = "指派处理人")
    @ExcelProperty("指派处理人")
    private String assignedPerson;

    @Schema(description = "指派班组")
    @ExcelProperty("指派班组")
    private String assignedTeam;

    @Schema(description = "派单时间")
    @ExcelProperty("派单时间")
    private LocalDateTime assignmentTime;

    @Schema(description = "预计修复时间")
    @ExcelProperty("预计修复时间")
    private LocalDateTime expectedRepairTime;

    @Schema(description = "实际开始时间")
    @ExcelProperty("实际开始时间")
    private LocalDateTime actualStartTime;

    @Schema(description = "实际结束时间")
    @ExcelProperty("实际结束时间")
    private LocalDateTime actualEndTime;

    @Schema(description = "修复耗时(小时)")
    @ExcelProperty("修复耗时(小时)")
    private Integer repairDuration;

    @Schema(description = "修复方法")
    @ExcelProperty("修复方法")
    private String repairMethod;

    @Schema(description = "更换部件(JSON格式)")
    @ExcelProperty("更换部件(JSON格式)")
    private String replacedParts;

    @Schema(description = "修复费用")
    @ExcelProperty("修复费用")
    private BigDecimal repairCost;

    @Schema(description = "修复质量")
    @ExcelProperty(value = "修复质量", converter = DictConvert.class)
    @DictFormat("repair_quality") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer repairQuality;

    @Schema(description = "测试结果")
    @ExcelProperty("测试结果")
    private String testResult;

    @Schema(description = "预防措施")
    @ExcelProperty("预防措施")
    private String preventionMeasures;

    @Schema(description = "满意度评价")
    @ExcelProperty(value = "满意度评价", converter = DictConvert.class)
    @DictFormat("satisfaction_rating") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer satisfactionRating;

    @Schema(description = "反馈意见")
    @ExcelProperty("反馈意见")
    private String feedbackNotes;

    @Schema(description = "关闭时间")
    @ExcelProperty("关闭时间")
    private LocalDateTime closeTime;

    @Schema(description = "关闭原因", example = "不喜欢")
    @ExcelProperty("关闭原因")
    private String closeReason;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
