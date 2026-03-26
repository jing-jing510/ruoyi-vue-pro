package cn.iocoder.yudao.module.opcua.service.alarm;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.opcua.controller.admin.alarm.vo.*;
import cn.iocoder.yudao.module.opcua.dal.dataobject.alarm.AlarmEventDO;

import javax.validation.Valid;

/**
 * 报警事件 Service 接口
 *
 * @author 芋道源码
 */
public interface AlarmEventService {

    /**
     * 创建报警事件
     *
     * @param alarmEvent 报警事件
     * @return 报警ID
     */
    Long createAlarmEvent(AlarmEventDO alarmEvent);

    /**
     * 创建报警事件（从VO）
     *
     * @param createReqVO 创建信息
     * @return 报警ID
     */
    Long createAlarmEvent(@Valid AlarmEventSaveReqVO createReqVO);

    /**
     * 更新报警事件
     *
     * @param updateReqVO 更新信息
     */
    void updateAlarmEvent(@Valid AlarmEventSaveReqVO updateReqVO);

    /**
     * 处理报警事件
     *
     * @param reqVO 处理请求
     */
    void handleAlarmEvent(@Valid AlarmEventHandleReqVO reqVO);

    /**
     * 获得报警事件分页
     *
     * @param pageReqVO 分页查询
     * @return 报警事件分页
     */
    PageResult<AlarmEventDO> getAlarmEventPage(AlarmEventPageReqVO pageReqVO);

    /**
     * 获得报警事件
     *
     * @param id 编号
     * @return 报警事件
     */
    AlarmEventDO getAlarmEvent(Long id);

    /**
     * 获取报警统计
     *
     * @param reqVO 统计请求
     * @return 统计结果
     */
    AlarmStatisticsRespVO getAlarmStatistics(@Valid AlarmStatisticsReqVO reqVO);

    /**
     * 删除报警事件
     *
     * @param id 编号
     */
    void deleteAlarmEvent(Long id);

}
