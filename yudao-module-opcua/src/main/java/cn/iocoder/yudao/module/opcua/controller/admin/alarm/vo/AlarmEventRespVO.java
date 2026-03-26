package cn.iocoder.yudao.module.opcua.controller.admin.alarm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 报警事件 Response VO")
@Data
public class AlarmEventRespVO {

    @Schema(description = "报警ID", example = "1024")
    private Long id;

    @Schema(description = "设备ID", example = "1024")
    private Long deviceId;

    @Schema(description = "设备编码", example = "DEV001")
    private String deviceCode;

    @Schema(description = "设备名称", example = "1号生产线")
    private String deviceName;

    @Schema(description = "点位ID", example = "1024")
    private Long tagId;

    @Schema(description = "点位名称", example = "温度传感器")
    private String tagName;

    @Schema(description = "报警级别", example = "3")
    private Integer alarmLevel;

    @Schema(description = "报警内容", example = "温度超过阈值")
    private String alarmContent;

    @Schema(description = "报警值", example = "105.5")
    private String alarmValue;

    @Schema(description = "报警时间")
    private LocalDateTime alarmTime;

    @Schema(description = "报警状态", example = "0")
    private Integer status;

    @Schema(description = "处理人ID", example = "1")
    private Long handleUserId;

    @Schema(description = "处理人姓名", example = "张三")
    private String handleUserName;

    @Schema(description = "处理时间")
    private LocalDateTime handleTime;

    @Schema(description = "处理意见", example = "已检查设备，温度正常")
    private String handleRemark;

    @Schema(description = "关闭时间")
    private LocalDateTime closeTime;

    @Schema(description = "备注", example = "需要进一步观察")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
