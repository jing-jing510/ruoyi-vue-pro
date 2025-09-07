package cn.iocoder.yudao.module.coal.service.schedule;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.SchedulePageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.ScheduleSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.schedule.ScheduleDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.schedule.ScheduleStaffDO;
import cn.iocoder.yudao.module.coal.dal.mysql.schedule.ScheduleMapper;
import cn.iocoder.yudao.module.coal.dal.mysql.schedule.ScheduleStaffMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SCHEDULE_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SCHEDULE_STAFF_NOT_EXISTS;

/**
 * 排班管理 (主表) Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private ScheduleStaffMapper scheduleStaffMapper;

    @Override
    public Long createSchedule(ScheduleSaveReqVO createReqVO) {
        // 插入
        ScheduleDO schedule = BeanUtils.toBean(createReqVO, ScheduleDO.class);
        scheduleMapper.insert(schedule);

        // 返回
        return schedule.getId();
    }

    @Override
    public void updateSchedule(ScheduleSaveReqVO updateReqVO) {
        // 校验存在
        validateScheduleExists(updateReqVO.getId());
        // 更新
        ScheduleDO updateObj = BeanUtils.toBean(updateReqVO, ScheduleDO.class);
        scheduleMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSchedule(Long id) {
        // 校验存在
        validateScheduleExists(id);
        // 删除
        scheduleMapper.deleteById(id);

        // 删除子表
        deleteScheduleStaffByScheduleId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deleteScheduleListByIds(List<Long> ids) {
        // 删除
        scheduleMapper.deleteByIds(ids);
    
    // 删除子表
            deleteScheduleStaffByScheduleIds(ids);
    }


    private void validateScheduleExists(Long id) {
        if (scheduleMapper.selectById(id) == null) {
            throw exception(SCHEDULE_NOT_EXISTS);
        }
    }

    @Override
    public ScheduleDO getSchedule(Long id) {
        return scheduleMapper.selectById(id);
    }

    @Override
    public PageResult<ScheduleDO> getSchedulePage(SchedulePageReqVO pageReqVO) {
        return scheduleMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（人员安排 (子表)） ====================

    @Override
    public PageResult<ScheduleStaffDO> getScheduleStaffPage(PageParam pageReqVO, Long scheduleId) {
        return scheduleStaffMapper.selectPage(pageReqVO, scheduleId);
    }

    @Override
    public Long createScheduleStaff(ScheduleStaffDO scheduleStaff) {
        scheduleStaff.clean(); // 清理掉创建、更新时间等相关属性值
        scheduleStaffMapper.insert(scheduleStaff);
        return scheduleStaff.getId();
    }

    @Override
    public void updateScheduleStaff(ScheduleStaffDO scheduleStaff) {
        // 校验存在
        validateScheduleStaffExists(scheduleStaff.getId());
        // 更新
        scheduleStaff.clean(); // 解决更新情况下：updateTime 不更新
        scheduleStaffMapper.updateById(scheduleStaff);
    }

    @Override
    public void deleteScheduleStaff(Long id) {
        // 删除
        scheduleStaffMapper.deleteById(id);
    }

	@Override
	public void deleteScheduleStaffListByIds(List<Long> ids) {
        // 删除
        scheduleStaffMapper.deleteByIds(ids);
	}

    @Override
    public ScheduleStaffDO getScheduleStaff(Long id) {
        return scheduleStaffMapper.selectById(id);
    }

    private void validateScheduleStaffExists(Long id) {
        if (scheduleStaffMapper.selectById(id) == null) {
            throw exception(SCHEDULE_STAFF_NOT_EXISTS);
        }
    }

    private void deleteScheduleStaffByScheduleId(Long scheduleId) {
        scheduleStaffMapper.deleteByScheduleId(scheduleId);
    }

	private void deleteScheduleStaffByScheduleIds(List<Long> scheduleIds) {
        scheduleStaffMapper.deleteByScheduleIds(scheduleIds);
	}

}