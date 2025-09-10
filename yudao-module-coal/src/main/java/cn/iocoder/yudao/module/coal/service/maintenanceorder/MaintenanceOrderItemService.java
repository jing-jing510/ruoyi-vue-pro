package cn.iocoder.yudao.module.coal.service.maintenanceorder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderItemPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderItemSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder.MaintenanceOrderItemDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 检修项目明细 Service 接口
 *
 * @author 芋道源码
 */
public interface MaintenanceOrderItemService {

    /**
     * 创建检修项目明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMaintenanceOrderItem(@Valid MaintenanceOrderItemSaveReqVO createReqVO);

    /**
     * 更新检修项目明细
     *
     * @param updateReqVO 更新信息
     */
    void updateMaintenanceOrderItem(@Valid MaintenanceOrderItemSaveReqVO updateReqVO);

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

    /**
     * 获得检修项目明细分页
     *
     * @param pageReqVO 分页查询
     * @return 检修项目明细分页
     */
    PageResult<MaintenanceOrderItemDO> getMaintenanceOrderItemPage(MaintenanceOrderItemPageReqVO pageReqVO);

}