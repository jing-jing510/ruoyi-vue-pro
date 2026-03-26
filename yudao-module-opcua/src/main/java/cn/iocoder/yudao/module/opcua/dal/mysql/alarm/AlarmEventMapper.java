package cn.iocoder.yudao.module.opcua.dal.mysql.alarm;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.opcua.controller.admin.alarm.vo.AlarmEventPageReqVO;
import cn.iocoder.yudao.module.opcua.controller.admin.alarm.vo.AlarmStatisticsReqVO;
import cn.iocoder.yudao.module.opcua.dal.dataobject.alarm.AlarmEventDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 报警事件 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface AlarmEventMapper extends BaseMapperX<AlarmEventDO> {

    default PageResult<AlarmEventDO> selectPage(AlarmEventPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AlarmEventDO>()
                .eqIfPresent(AlarmEventDO::getConfigId, reqVO.getConfigId())
                .likeIfPresent(AlarmEventDO::getDeviceName, reqVO.getDeviceName())
                .eqIfPresent(AlarmEventDO::getAlarmLevel, reqVO.getAlarmLevel())
                .eqIfPresent(AlarmEventDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(AlarmEventDO::getAlarmTime, reqVO.getAlarmTime())
                .betweenIfPresent(AlarmEventDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AlarmEventDO::getAlarmTime));
    }

    /**
     * 按状态统计报警数量
     */
    @Select("SELECT status, COUNT(*) as count FROM opcua_alarm_event " +
            "WHERE alarm_time BETWEEN #{startTime} AND #{endTime} AND deleted = 0 " +
            "GROUP BY status")
    List<Map<String, Object>> selectCountByStatus(@Param("startTime") LocalDateTime startTime,
                                                    @Param("endTime") LocalDateTime endTime);

    /**
     * 按设备统计报警数量
     */
    @Select("SELECT device_name as deviceName, COUNT(*) as count FROM opcua_alarm_event " +
            "WHERE alarm_time BETWEEN #{startTime} AND #{endTime} AND deleted = 0 " +
            "GROUP BY device_name " +
            "ORDER BY count DESC")
    List<Map<String, Object>> selectCountByDevice(@Param("startTime") LocalDateTime startTime,
                                                    @Param("endTime") LocalDateTime endTime);

    /**
     * 按级别统计报警数量
     */
    @Select("SELECT alarm_level as alarmLevel, COUNT(*) as count FROM opcua_alarm_event " +
            "WHERE alarm_time BETWEEN #{startTime} AND #{endTime} AND deleted = 0 " +
            "GROUP BY alarm_level")
    List<Map<String, Object>> selectCountByLevel(@Param("startTime") LocalDateTime startTime,
                                                   @Param("endTime") LocalDateTime endTime);

}
