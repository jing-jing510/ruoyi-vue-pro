package cn.iocoder.yudao.module.coal.service.schedule;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.schedule.ScheduleDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.schedule.ScheduleStaffDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 排班管理 (主表) Service 接口
 *
 * @author 京京
 */
public interface ScheduleService {

    /**
     * 创建排班管理 (主表)
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSchedule(@Valid ScheduleSaveReqVO createReqVO);

    /**
     * 更新排班管理 (主表)
     *
     * @param updateReqVO 更新信息
     */
    void updateSchedule(@Valid ScheduleSaveReqVO updateReqVO);

    /**
     * 删除排班管理 (主表)
     *
     * @param id 编号
     */
    void deleteSchedule(Long id);

    /**
    * 批量删除排班管理 (主表)
    *
    * @param ids 编号
    */
    void deleteScheduleListByIds(List<Long> ids);

    /**
     * 获得排班管理 (主表)
     *
     * @param id 编号
     * @return 排班管理 (主表)
     */
    ScheduleDO getSchedule(Long id);

    /**
     * 获得排班管理 (主表)分页
     *
     * @param pageReqVO 分页查询
     * @return 排班管理 (主表)分页
     */
    PageResult<ScheduleDO> getSchedulePage(SchedulePageReqVO pageReqVO);

    // ==================== 子表（人员安排 (子表)） ====================

    /**
     * 获得人员安排 (子表)分页
     *
     * @param pageReqVO 分页查询
     * @param scheduleId 排班ID (关联主表)
     * @return 人员安排 (子表)分页
     */
    PageResult<ScheduleStaffDO> getScheduleStaffPage(PageParam pageReqVO, Long scheduleId);

    /**
     * 创建人员安排 (子表)
     *
     * @param scheduleStaff 创建信息
     * @return 编号
     */
    Long createScheduleStaff(@Valid ScheduleStaffDO scheduleStaff);

    /**
     * 更新人员安排 (子表)
     *
     * @param scheduleStaff 更新信息
     */
    void updateScheduleStaff(@Valid ScheduleStaffDO scheduleStaff);

    /**
     * 删除人员安排 (子表)
     *
     * @param id 编号
     */
    void deleteScheduleStaff(Long id);

    /**
    * 批量删除人员安排 (子表)
    *
    * @param ids 编号
    */
    void deleteScheduleStaffListByIds(List<Long> ids);

	/**
	 * 获得人员安排 (子表)
	 *
	 * @param id 编号
     * @return 人员安排 (子表)
	 */
    ScheduleStaffDO getScheduleStaff(Long id);

}