package cn.iocoder.yudao.module.coal.service.schedule;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.schedule.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.schedule.ScheduleStaffDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 人员安排 (子表) Service 接口
 *
 * @author 京京
 */
public interface ScheduleStaffService {

    /**
     * 创建人员安排 (子表)
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createScheduleStaff(@Valid ScheduleStaffSaveReqVO createReqVO);

    /**
     * 更新人员安排 (子表)
     *
     * @param updateReqVO 更新信息
     */
    void updateScheduleStaff(@Valid ScheduleStaffSaveReqVO updateReqVO);

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

    /**
     * 获得人员安排 (子表)分页
     *
     * @param pageReqVO 分页查询
     * @return 人员安排 (子表)分页
     */
    PageResult<ScheduleStaffDO> getScheduleStaffPage(ScheduleStaffPageReqVO pageReqVO);

}