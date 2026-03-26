package cn.iocoder.yudao.module.opcua.dal.dataobject.alarm;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 报警事件 DO
 *
 * @author 芋道源码
 */
@TableName("opcua_alarm_event")
@KeySequence("opcua_alarm_event_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmEventDO extends BaseDO {

    /**
     * 报警ID
     */
    @TableId
    private Long id;

    /**
     * OPC UA 配置ID
     */
    private Long configId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 点位ID
     */
    private Long tagId;

    /**
     * 点位名称
     */
    private String tagName;

    /**
     * OPC UA NodeId
     */
    private String nodeId;

    /**
     * 报警级别
     * 1-提示 2-警告 3-错误 4-严重
     */
    private Integer alarmLevel;

    /**
     * 报警内容
     */
    private String alarmContent;

    /**
     * 报警时间
     */
    private LocalDateTime alarmTime;

    /**
     * 报警状态
     * 0-待处理 1-已处理
     */
    private Integer status;

    /**
     * 处理人ID
     */
    private Long handleUserId;

    /**
     * 处理人姓名
     */
    private String handleUserName;

    /**
     * 处理时间
     */
    private LocalDateTime handleTime;

    /**
     * 处理意见
     */
    private String handleRemark;

}
