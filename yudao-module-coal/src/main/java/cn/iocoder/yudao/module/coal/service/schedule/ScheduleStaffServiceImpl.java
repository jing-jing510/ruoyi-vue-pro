package cn.iocoder.yudao.module.coal.service.schedule;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.ScheduleStaffPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.ScheduleStaffSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.schedule.ScheduleStaffDO;
import cn.iocoder.yudao.module.coal.dal.mysql.schedule.ScheduleStaffMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SCHEDULE_STAFF_NOT_EXISTS;

/**
 * 人员安排 (子表) Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class ScheduleStaffServiceImpl implements ScheduleStaffService {

    @Resource
    private ScheduleStaffMapper scheduleStaffMapper;

    @Override
    public Long createScheduleStaff(ScheduleStaffSaveReqVO createReqVO) {
        // 插入
        ScheduleStaffDO scheduleStaff = BeanUtils.toBean(createReqVO, ScheduleStaffDO.class);
        scheduleStaffMapper.insert(scheduleStaff);

        // 返回
        return scheduleStaff.getId();
    }

    @Override
    public void updateScheduleStaff(ScheduleStaffSaveReqVO updateReqVO) {
        // 校验存在
        validateScheduleStaffExists(updateReqVO.getId());
        // 更新
        ScheduleStaffDO updateObj = BeanUtils.toBean(updateReqVO, ScheduleStaffDO.class);
        scheduleStaffMapper.updateById(updateObj);
    }

    @Override
    public void deleteScheduleStaff(Long id) {
        // 校验存在
        validateScheduleStaffExists(id);
        // 删除
        scheduleStaffMapper.deleteById(id);
    }

    @Override
        public void deleteScheduleStaffListByIds(List<Long> ids) {
        // 删除
        scheduleStaffMapper.deleteByIds(ids);
        }


    private void validateScheduleStaffExists(Long id) {
        if (scheduleStaffMapper.selectById(id) == null) {
            throw exception(SCHEDULE_STAFF_NOT_EXISTS);
        }
    }

    @Override
    public ScheduleStaffDO getScheduleStaff(Long id) {
        return scheduleStaffMapper.selectById(id);
    }

    @Override
    public PageResult<ScheduleStaffDO> getScheduleStaffPage(ScheduleStaffPageReqVO pageReqVO) {
        return scheduleStaffMapper.selectPage(pageReqVO);
    }

}