package cn.iocoder.yudao.module.coal.dal.mysql.maintenanceorder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceorder.vo.MaintenanceOrderPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceorder.MaintenanceOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 检修单 Mapper
 *
 * @author 京京
 */
@Mapper
public interface MaintenanceOrderMapper extends BaseMapperX<MaintenanceOrderDO> {

    default PageResult<MaintenanceOrderDO> selectPage(MaintenanceOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaintenanceOrderDO>()
                .eqIfPresent(MaintenanceOrderDO::getOrderCode, reqVO.getOrderCode())
                .eqIfPresent(MaintenanceOrderDO::getPlanId, reqVO.getPlanId())
                .eqIfPresent(MaintenanceOrderDO::getEquipmentId, reqVO.getEquipmentId())
                .likeIfPresent(MaintenanceOrderDO::getEquipmentName, reqVO.getEquipmentName())
                .eqIfPresent(MaintenanceOrderDO::getMaintenanceType, reqVO.getMaintenanceType())
                .eqIfPresent(MaintenanceOrderDO::getMaintenanceLevel, reqVO.getMaintenanceLevel())
                .eqIfPresent(MaintenanceOrderDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(MaintenanceOrderDO::getPriorityLevel, reqVO.getPriorityLevel())
                .betweenIfPresent(MaintenanceOrderDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(MaintenanceOrderDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(MaintenanceOrderDO::getQualityRating, reqVO.getQualityRating())
                .eqIfPresent(MaintenanceOrderDO::getSafetyRating, reqVO.getSafetyRating())
                .eqIfPresent(MaintenanceOrderDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(MaintenanceOrderDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MaintenanceOrderDO::getId));
    }

}