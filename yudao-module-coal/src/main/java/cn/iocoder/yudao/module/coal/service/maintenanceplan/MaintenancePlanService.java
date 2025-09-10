package cn.iocoder.yudao.module.coal.service.maintenanceplan;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceplan.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceplan.MaintenancePlanDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 检修计划 Service 接口
 *
 * @author 京京
 */
public interface MaintenancePlanService {

    /**
     * 创建检修计划
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMaintenancePlan(@Valid MaintenancePlanSaveReqVO createReqVO);

    /**
     * 更新检修计划
     *
     * @param updateReqVO 更新信息
     */
    void updateMaintenancePlan(@Valid MaintenancePlanSaveReqVO updateReqVO);

    /**
     * 删除检修计划
     *
     * @param id 编号
     */
    void deleteMaintenancePlan(Long id);

    /**
    * 批量删除检修计划
    *
    * @param ids 编号
    */
    void deleteMaintenancePlanListByIds(List<Long> ids);

    /**
     * 获得检修计划
     *
     * @param id 编号
     * @return 检修计划
     */
    MaintenancePlanDO getMaintenancePlan(Long id);

    /**
     * 获得检修计划分页
     *
     * @param pageReqVO 分页查询
     * @return 检修计划分页
     */
    PageResult<MaintenancePlanDO> getMaintenancePlanPage(MaintenancePlanPageReqVO pageReqVO);

}