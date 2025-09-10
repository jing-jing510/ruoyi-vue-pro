package cn.iocoder.yudao.module.coal.service.maintenanceorder;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder.MaintenanceOrderDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder.MaintenanceOrderItemDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 检修单 Service 接口
 *
 * @author 京京
 */
public interface MaintenanceOrderService {

    /**
     * 创建检修单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMaintenanceOrder(@Valid MaintenanceOrderSaveReqVO createReqVO);

    /**
     * 更新检修单
     *
     * @param updateReqVO 更新信息
     */
    void updateMaintenanceOrder(@Valid MaintenanceOrderSaveReqVO updateReqVO);

    /**
     * 删除检修单
     *
     * @param id 编号
     */
    void deleteMaintenanceOrder(Long id);

    /**
    * 批量删除检修单
    *
    * @param ids 编号
    */
    void deleteMaintenanceOrderListByIds(List<Long> ids);

    /**
     * 获得检修单
     *
     * @param id 编号
     * @return 检修单
     */
    MaintenanceOrderDO getMaintenanceOrder(Long id);

    /**
     * 获得检修单分页
     *
     * @param pageReqVO 分页查询
     * @return 检修单分页
     */
    PageResult<MaintenanceOrderDO> getMaintenanceOrderPage(MaintenanceOrderPageReqVO pageReqVO);

    // ==================== 子表（检修项目明细） ====================

    /**
     * 获得检修项目明细分页
     *
     * @param pageReqVO 分页查询
     * @param orderId 检修单ID
     * @return 检修项目明细分页
     */
    PageResult<MaintenanceOrderItemDO> getMaintenanceOrderItemPage(PageParam pageReqVO, Long orderId);

    /**
     * 创建检修项目明细
     *
     * @param maintenanceOrderItem 创建信息
     * @return 编号
     */
    Long createMaintenanceOrderItem(@Valid MaintenanceOrderItemDO maintenanceOrderItem);

    /**
     * 更新检修项目明细
     *
     * @param maintenanceOrderItem 更新信息
     */
    void updateMaintenanceOrderItem(@Valid MaintenanceOrderItemDO maintenanceOrderItem);

    /**
     * 删除检修项目明细
     *
     * @param id 编号
     */
    void deleteMaintenanceOrderItem(Long id);

    /**
    * 批量删除检修项目明细
    *
    * @param ids 编号
    */
    void deleteMaintenanceOrderItemListByIds(List<Long> ids);

	/**
	 * 获得检修项目明细
	 *
	 * @param id 编号
     * @return 检修项目明细
	 */
    MaintenanceOrderItemDO getMaintenanceOrderItem(Long id);

}