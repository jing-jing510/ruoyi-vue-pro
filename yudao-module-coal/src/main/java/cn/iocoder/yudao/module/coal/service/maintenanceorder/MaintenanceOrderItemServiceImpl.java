package cn.iocoder.yudao.module.coal.service.maintenanceorder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderItemPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderItemSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder.MaintenanceOrderItemDO;
import cn.iocoder.yudao.module.coal.dal.mysql.maintenanceorder.MaintenanceOrderItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.MAINTENANCE_ORDER_ITEM_NOT_EXISTS;

/**
 * 检修项目明细 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class MaintenanceOrderItemServiceImpl implements MaintenanceOrderItemService {

    @Resource
    private MaintenanceOrderItemMapper maintenanceOrderItemMapper;

    @Override
    public Long createMaintenanceOrderItem(MaintenanceOrderItemSaveReqVO createReqVO) {
        // 插入
        MaintenanceOrderItemDO maintenanceOrderItem = BeanUtils.toBean(createReqVO, MaintenanceOrderItemDO.class);
        maintenanceOrderItemMapper.insert(maintenanceOrderItem);

        // 返回
        return maintenanceOrderItem.getId();
    }

    @Override
    public void updateMaintenanceOrderItem(MaintenanceOrderItemSaveReqVO updateReqVO) {
        // 校验存在
        validateMaintenanceOrderItemExists(updateReqVO.getId());
        // 更新
        MaintenanceOrderItemDO updateObj = BeanUtils.toBean(updateReqVO, MaintenanceOrderItemDO.class);
        maintenanceOrderItemMapper.updateById(updateObj);
    }

    @Override
    public void deleteMaintenanceOrderItem(Long id) {
        // 校验存在
        validateMaintenanceOrderItemExists(id);
        // 删除
        maintenanceOrderItemMapper.deleteById(id);
    }

    @Override
        public void deleteMaintenanceOrderItemListByIds(List<Long> ids) {
        // 删除
        maintenanceOrderItemMapper.deleteByIds(ids);
        }


    private void validateMaintenanceOrderItemExists(Long id) {
        if (maintenanceOrderItemMapper.selectById(id) == null) {
            throw exception(MAINTENANCE_ORDER_ITEM_NOT_EXISTS);
        }
    }

    @Override
    public MaintenanceOrderItemDO getMaintenanceOrderItem(Long id) {
        return maintenanceOrderItemMapper.selectById(id);
    }

    @Override
    public PageResult<MaintenanceOrderItemDO> getMaintenanceOrderItemPage(MaintenanceOrderItemPageReqVO pageReqVO) {
        return maintenanceOrderItemMapper.selectPage(pageReqVO);
    }

}