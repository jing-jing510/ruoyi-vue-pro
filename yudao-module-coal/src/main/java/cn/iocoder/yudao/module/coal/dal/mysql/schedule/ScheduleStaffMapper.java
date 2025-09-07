package cn.iocoder.yudao.module.coal.dal.mysql.schedule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.ScheduleStaffPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.schedule.ScheduleStaffDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 人员安排 (子表) Mapper
 *
 * @author 京京
 */
@Mapper
public interface ScheduleStaffMapper extends BaseMapperX<ScheduleStaffDO> {

    default PageResult<ScheduleStaffDO> selectPage(ScheduleStaffPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ScheduleStaffDO>()
                .eqIfPresent(ScheduleStaffDO::getScheduleId, reqVO.getScheduleId())
                .eqIfPresent(ScheduleStaffDO::getShiftId, reqVO.getShiftId())
                .eqIfPresent(ScheduleStaffDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ScheduleStaffDO::getPositionType, reqVO.getPositionType())
                .eqIfPresent(ScheduleStaffDO::getIsLeader, reqVO.getIsLeader())
                .eqIfPresent(ScheduleStaffDO::getIsSubstitute, reqVO.getIsSubstitute())
                .eqIfPresent(ScheduleStaffDO::getWorkStatus, reqVO.getWorkStatus())
                .eqIfPresent(ScheduleStaffDO::getContactPhone, reqVO.getContactPhone())
                .eqIfPresent(ScheduleStaffDO::getEmergencyContact, reqVO.getEmergencyContact())
                .eqIfPresent(ScheduleStaffDO::getEmergencyPhone, reqVO.getEmergencyPhone())
                .betweenIfPresent(ScheduleStaffDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ScheduleStaffDO::getId));
    }

    /**
     * 根据排班ID分页查询人员安排
     */
    default PageResult<ScheduleStaffDO> selectPage(PageParam pageReqVO, Long scheduleId) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<ScheduleStaffDO>()
                .eq(ScheduleStaffDO::getScheduleId, scheduleId)
                .orderByDesc(ScheduleStaffDO::getId));
    }

    /**
     * 根据排班ID删除人员安排
     */
    default int deleteByScheduleId(Long scheduleId) {
        return delete(new LambdaQueryWrapperX<ScheduleStaffDO>()
                .eq(ScheduleStaffDO::getScheduleId, scheduleId));
    }

    /**
     * 根据排班ID列表批量删除人员安排
     */
    default int deleteByScheduleIds(List<Long> scheduleIds) {
        return delete(new LambdaQueryWrapperX<ScheduleStaffDO>()
                .in(ScheduleStaffDO::getScheduleId, scheduleIds));
    }

}