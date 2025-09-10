package cn.iocoder.yudao.module.coal.dal.mysql.maintenanceplan;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceplan.MaintenancePlanDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceplan.vo.*;

/**
 * 检修计划 Mapper
 *
 * @author 京京
 */
@Mapper
public interface MaintenancePlanMapper extends BaseMapperX<MaintenancePlanDO> {

    default PageResult<MaintenancePlanDO> selectPage(MaintenancePlanPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MaintenancePlanDO>()
                .eqIfPresent(MaintenancePlanDO::getPlanCode, reqVO.getPlanCode())
                .likeIfPresent(MaintenancePlanDO::getPlanName, reqVO.getPlanName())
                .eqIfPresent(MaintenancePlanDO::getPlanType, reqVO.getPlanType())
                .eqIfPresent(MaintenancePlanDO::getPlanStatus, reqVO.getPlanStatus())
                .eqIfPresent(MaintenancePlanDO::getEquipmentId, reqVO.getEquipmentId())
                .likeIfPresent(MaintenancePlanDO::getEquipmentName, reqVO.getEquipmentName())
                .eqIfPresent(MaintenancePlanDO::getMaintenanceType, reqVO.getMaintenanceType())
                .eqIfPresent(MaintenancePlanDO::getMaintenanceLevel, reqVO.getMaintenanceLevel())
                .eqIfPresent(MaintenancePlanDO::getPlannedStartTime, reqVO.getPlannedStartTime())
                .eqIfPresent(MaintenancePlanDO::getPlannedEndTime, reqVO.getPlannedEndTime())
                .orderByDesc(MaintenancePlanDO::getId));
    }

}