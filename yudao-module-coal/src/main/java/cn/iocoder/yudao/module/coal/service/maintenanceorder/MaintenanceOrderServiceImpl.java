package cn.iocoder.yudao.module.coal.service.maintenanceorder;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder.MaintenanceOrderDO;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder.MaintenanceOrderItemDO;
import cn.iocoder.yudao.module.coal.dal.mysql.maintenanceorder.MaintenanceOrderItemMapper;
import cn.iocoder.yudao.module.coal.dal.mysql.maintenanceorder.MaintenanceOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.MAINTENANCE_ORDER_ITEM_NOT_EXISTS;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.MAINTENANCE_ORDER_NOT_EXISTS;

/**
 * 检修单 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class MaintenanceOrderServiceImpl implements MaintenanceOrderService {

    @Resource
    private MaintenanceOrderMapper maintenanceOrderMapper;
    @Resource
    private MaintenanceOrderItemMapper maintenanceOrderItemMapper;

    @Override
    public Long createMaintenanceOrder(MaintenanceOrderSaveReqVO createReqVO) {
        // 插入
        MaintenanceOrderDO maintenanceOrder = BeanUtils.toBean(createReqVO, MaintenanceOrderDO.class);
        maintenanceOrderMapper.insert(maintenanceOrder);

        // 返回
        return maintenanceOrder.getId();
    }

    @Override
    public void updateMaintenanceOrder(MaintenanceOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateMaintenanceOrderExists(updateReqVO.getId());
        // 更新
        MaintenanceOrderDO updateObj = BeanUtils.toBean(updateReqVO, MaintenanceOrderDO.class);
        maintenanceOrderMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMaintenanceOrder(Long id) {
        // 校验存在
        validateMaintenanceOrderExists(id);
        // 删除
        maintenanceOrderMapper.deleteById(id);

        // 删除子表
        deleteMaintenanceOrderItemByOrderId(id);
    }

    @Override
        @Transactional(rollbackFor = Exception.class)
    public void deleteMaintenanceOrderListByIds(List<Long> ids) {
        // 删除
        maintenanceOrderMapper.deleteByIds(ids);
    
    // 删除子表
            deleteMaintenanceOrderItemByOrderIds(ids);
    }


    private void validateMaintenanceOrderExists(Long id) {
        if (maintenanceOrderMapper.selectById(id) == null) {
            throw exception(MAINTENANCE_ORDER_NOT_EXISTS);
        }
    }

    @Override
    public MaintenanceOrderDO getMaintenanceOrder(Long id) {
        return maintenanceOrderMapper.selectById(id);
    }

    @Override
    public PageResult<MaintenanceOrderDO> getMaintenanceOrderPage(MaintenanceOrderPageReqVO pageReqVO) {
        return maintenanceOrderMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（检修项目明细） ====================

    @Override
    public PageResult<MaintenanceOrderItemDO> getMaintenanceOrderItemPage(PageParam pageReqVO, Long orderId) {
        return maintenanceOrderItemMapper.selectPage(pageReqVO, orderId);
    }

    @Override
    public Long createMaintenanceOrderItem(MaintenanceOrderItemDO maintenanceOrderItem) {
        maintenanceOrderItem.clean(); // 清理掉创建、更新时间等相关属性值
        maintenanceOrderItemMapper.insert(maintenanceOrderItem);
        return maintenanceOrderItem.getId();
    }

    @Override
    public void updateMaintenanceOrderItem(MaintenanceOrderItemDO maintenanceOrderItem) {
        // 校验存在
        validateMaintenanceOrderItemExists(maintenanceOrderItem.getId());
        // 更新
        maintenanceOrderItem.clean(); // 解决更新情况下：updateTime 不更新
        maintenanceOrderItemMapper.updateById(maintenanceOrderItem);
    }

    @Override
    public void deleteMaintenanceOrderItem(Long id) {
        // 删除
        maintenanceOrderItemMapper.deleteById(id);
    }

	@Override
	public void deleteMaintenanceOrderItemListByIds(List<Long> ids) {
        // 删除
        maintenanceOrderItemMapper.deleteByIds(ids);
	}

    @Override
    public MaintenanceOrderItemDO getMaintenanceOrderItem(Long id) {
        return maintenanceOrderItemMapper.selectById(id);
    }

    private void validateMaintenanceOrderItemExists(Long id) {
        if (maintenanceOrderItemMapper.selectById(id) == null) {
            throw exception(MAINTENANCE_ORDER_ITEM_NOT_EXISTS);
        }
    }

    private void deleteMaintenanceOrderItemByOrderId(Long orderId) {
        maintenanceOrderItemMapper.deleteByOrderId(orderId);
    }

	private void deleteMaintenanceOrderItemByOrderIds(List<Long> orderIds) {
        maintenanceOrderItemMapper.deleteByOrderIds(orderIds);
	}

}