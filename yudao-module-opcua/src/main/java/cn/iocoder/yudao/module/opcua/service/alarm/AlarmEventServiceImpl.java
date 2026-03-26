package cn.iocoder.yudao.module.opcua.service.alarm;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.opcua.controller.admin.alarm.vo.*;
import cn.iocoder.yudao.module.opcua.dal.dataobject.alarm.AlarmEventDO;
import cn.iocoder.yudao.module.opcua.dal.mysql.alarm.AlarmEventMapper;
import cn.iocoder.yudao.module.opcua.enums.AlarmStatusEnum;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.opcua.enums.ErrorCodeConstants.*;

/**
 * 报警事件 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class AlarmEventServiceImpl implements AlarmEventService {

    @Resource
    private AlarmEventMapper alarmEventMapper;

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public Long createAlarmEvent(AlarmEventDO alarmEvent) {
        alarmEventMapper.insert(alarmEvent);
        return alarmEvent.getId();
    }

    @Override
    public Long createAlarmEvent(AlarmEventSaveReqVO createReqVO) {
        // 转换为 DO
        AlarmEventDO alarmEvent = BeanUtils.toBean(createReqVO, AlarmEventDO.class);
        
        // 如果没有指定状态，默认为待处理
        if (alarmEvent.getStatus() == null) {
            alarmEvent.setStatus(AlarmStatusEnum.PENDING.getStatus());
        }
        
        // 插入
        alarmEventMapper.insert(alarmEvent);
        return alarmEvent.getId();
    }

    @Override
    public void updateAlarmEvent(AlarmEventSaveReqVO updateReqVO) {
        // 校验存在
        validateAlarmEventExists(updateReqVO.getId());
        
        // 转换为 DO
        AlarmEventDO updateObj = BeanUtils.toBean(updateReqVO, AlarmEventDO.class);
        
        // 更新
        alarmEventMapper.updateById(updateObj);
    }

    @Override
    public void handleAlarmEvent(AlarmEventHandleReqVO reqVO) {
        // 校验存在
        AlarmEventDO alarmEvent = validateAlarmEventExists(reqVO.getId());

        // 校验状态 - 已处理的报警不能再次处理
        if (alarmEvent.getStatus().equals(AlarmStatusEnum.PROCESSED.getStatus())) {
            throw exception(ALARM_EVENT_STATUS_ERROR);
        }

        // 获取当前登录用户信息
        Long userId = getLoginUserId();
        AdminUserRespDTO user = adminUserApi.getUser(userId);

        // 更新报警事件
        AlarmEventDO updateObj = AlarmEventDO.builder()
                .id(reqVO.getId())
                .status(reqVO.getStatus())
                .handleUserId(userId)
                .handleUserName(user != null ? user.getNickname() : "未知用户")
                .handleRemark(reqVO.getHandleRemark())
                .handleTime(LocalDateTime.now())
                .build();

        alarmEventMapper.updateById(updateObj);
    }

    @Override
    public PageResult<AlarmEventDO> getAlarmEventPage(AlarmEventPageReqVO pageReqVO) {
        return alarmEventMapper.selectPage(pageReqVO);
    }

    @Override
    public AlarmEventDO getAlarmEvent(Long id) {
        return alarmEventMapper.selectById(id);
    }

    @Override
    public AlarmStatisticsRespVO getAlarmStatistics(AlarmStatisticsReqVO reqVO) {
        AlarmStatisticsRespVO respVO = new AlarmStatisticsRespVO();

        // 按状态统计
        List<Map<String, Object>> statusStats = alarmEventMapper.selectCountByStatus(
                reqVO.getStartTime(), reqVO.getEndTime());
        respVO.setStatusStatistics(statusStats);

        // 按设备统计
        List<Map<String, Object>> deviceStats = alarmEventMapper.selectCountByDevice(
                reqVO.getStartTime(), reqVO.getEndTime());
        respVO.setDeviceStatistics(deviceStats);

        // 按级别统计
        List<Map<String, Object>> levelStats = alarmEventMapper.selectCountByLevel(
                reqVO.getStartTime(), reqVO.getEndTime());
        respVO.setLevelStatistics(levelStats);

        // 计算总数
        long totalCount = statusStats.stream()
                .mapToLong(m -> ((Number) m.get("count")).longValue())
                .sum();
        respVO.setTotalCount(totalCount);

        return respVO;
    }

    @Override
    public void deleteAlarmEvent(Long id) {
        // 校验存在
        validateAlarmEventExists(id);
        // 删除
        alarmEventMapper.deleteById(id);
    }

    private AlarmEventDO validateAlarmEventExists(Long id) {
        AlarmEventDO alarmEvent = alarmEventMapper.selectById(id);
        if (alarmEvent == null) {
            throw exception(ALARM_EVENT_NOT_EXISTS);
        }
        return alarmEvent;
    }

}
