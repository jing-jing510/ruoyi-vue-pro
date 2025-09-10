package cn.iocoder.yudao.module.coal.dal.mysql.maintenanceorder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderItemPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder.MaintenanceOrderItemDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 检修项目明细 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MaintenanceOrderItemMapper extends BaseMapperX<MaintenanceOrderItemDO> {

    default PageResult<MaintenanceOrderItemDO> selectPage(MaintenanceOrderItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaintenanceOrderItemDO>()
                .eqIfPresent(MaintenanceOrderItemDO::getOrderId, reqVO.getOrderId())
                .likeIfPresent(MaintenanceOrderItemDO::getItemName, reqVO.getItemName())
                .eqIfPresent(MaintenanceOrderItemDO::getItemType, reqVO.getItemType())
                .eqIfPresent(MaintenanceOrderItemDO::getItemDescription, reqVO.getItemDescription())
                .eqIfPresent(MaintenanceOrderItemDO::getItemStatus, reqVO.getItemStatus())
                .betweenIfPresent(MaintenanceOrderItemDO::getCompletionTime, reqVO.getCompletionTime())
                .eqIfPresent(MaintenanceOrderItemDO::getCompletionQuality, reqVO.getCompletionQuality())
                .betweenIfPresent(MaintenanceOrderItemDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MaintenanceOrderItemDO::getId));
    }

    /**
     * 根据检修单ID分页查询检修项目明细
     */
    default PageResult<MaintenanceOrderItemDO> selectPage(PageParam pageReqVO, Long orderId) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<MaintenanceOrderItemDO>()
                .eq(MaintenanceOrderItemDO::getOrderId, orderId)
                .orderByDesc(MaintenanceOrderItemDO::getId));
    }

    /**
     * 根据检修单ID删除检修项目明细
     */
    default int deleteByOrderId(Long orderId) {
        return delete(new LambdaQueryWrapperX<MaintenanceOrderItemDO>()
                .eq(MaintenanceOrderItemDO::getOrderId, orderId));
    }

    /**
     * 根据检修单ID列表批量删除检修项目明细
     */
    default int deleteByOrderIds(List<Long> orderIds) {
        return delete(new LambdaQueryWrapperX<MaintenanceOrderItemDO>()
                .in(MaintenanceOrderItemDO::getOrderId, orderIds));
    }

}