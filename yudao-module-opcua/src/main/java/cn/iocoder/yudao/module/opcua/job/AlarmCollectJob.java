package cn.iocoder.yudao.module.opcua.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.opcua.dal.dataobject.alarm.AlarmEventDO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.tag.TagConfigDO;
import cn.iocoder.yudao.module.opcua.enums.AlarmStatusEnum;
import cn.iocoder.yudao.module.opcua.service.alarm.AlarmEventService;
import cn.iocoder.yudao.module.opcua.service.opcua.OpcuaClientService;
import cn.iocoder.yudao.module.opcua.service.tag.TagConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 报警采集定时任务
 * 定时从 OPC UA 服务器读取报警点位数据
 * 当值从 0 变为 1 时触发报警
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class AlarmCollectJob implements JobHandler {

    @Resource
    private TagConfigService tagConfigService;

    @Resource
    private OpcuaClientService opcuaClientService;

    @Resource
    private AlarmEventService alarmEventService;

    @Override
    @TenantJob
    public String execute(String param) throws Exception {
        // 1. 获取所有启用的报警点位
        List<TagConfigDO> alarmTags = tagConfigService.getEnabledAlarmTags();
        log.info("[报警采集] 开始采集报警数据，共 {} 个报警点位", alarmTags.size());

        int alarmCount = 0;

        // 2. 遍历每个报警点位
        for (TagConfigDO tag : alarmTags) {
            try {
                // 读取点位值
                Object value = opcuaClientService.readValue(tag.getConfigId(), tag.getNodeId());
                if (value == null) {
                    continue;
                }

                // 转换为字符串进行比较
                String currentValue = normalizeValue(value);
                String lastValue = normalizeValue(tag.getLastValue());

                // 判断是否从 0/false 变成 1/true（报警触发）
                boolean isAlarm = false;
                if (isAlarmTriggered(lastValue, currentValue)) {
                    isAlarm = true;
                    log.warn("[报警采集] 检测到报警触发 - 点位：{}，值从 {} 变为 {}", 
                            tag.getName(), tag.getLastValue(), value);
                }

                // 更新最后采集值
                tagConfigService.updateLastValue(tag.getId(), currentValue);

                // 如果触发报警，创建报警事件
                if (isAlarm) {
                    AlarmEventDO alarmEvent = AlarmEventDO.builder()
                            .configId(tag.getConfigId())
                            .deviceName(tag.getDeviceName())
                            .tagId(tag.getId())
                            .tagName(tag.getName())
                            .nodeId(tag.getNodeId())
                            .alarmLevel(tag.getAlarmLevel())
                            .alarmContent(tag.getAlarmContent() != null ? tag.getAlarmContent() : "报警触发")
                            .alarmTime(LocalDateTime.now())
                            .status(AlarmStatusEnum.PENDING.getStatus())
                            .build();

                    alarmEventService.createAlarmEvent(alarmEvent);
                    alarmCount++;

                    log.warn("[报警采集] 创建报警事件 - 设备：{}，点位：{}", 
                            tag.getDeviceName(), tag.getName());
                }
            } catch (Exception e) {
                log.error("[报警采集] 采集点位 {} 失败", tag.getName(), e);
            }
        }

        log.info("[报警采集] 采集完成，触发 {} 个报警", alarmCount);
        return String.format("采集完成，触发 %d 个报警", alarmCount);
    }

    /**
     * 标准化值：将 Boolean/Integer 转换为统一格式
     * true/1/"1" -> "1"
     * false/0/"0"/null -> "0"
     */
    private String normalizeValue(Object value) {
        if (value == null) {
            return "0";
        }
        
        String strValue = String.valueOf(value).toLowerCase();
        
        // Boolean 类型
        if ("true".equals(strValue) || "1".equals(strValue)) {
            return "1";
        }
        
        // false/0/其他
        return "0";
    }

    /**
     * 判断是否触发报警
     * 条件：上次值为 0/false，当前值为 1/true
     */
    private boolean isAlarmTriggered(String lastValue, String currentValue) {
        return "0".equals(lastValue) && "1".equals(currentValue);
    }

}
