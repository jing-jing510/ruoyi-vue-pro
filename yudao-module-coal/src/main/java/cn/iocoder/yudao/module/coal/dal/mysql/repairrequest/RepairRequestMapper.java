package cn.iocoder.yudao.module.coal.dal.mysql.repairrequest;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.repairrequest.RepairRequestDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.repairrequest.vo.*;

/**
 * 报修单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RepairRequestMapper extends BaseMapperX<RepairRequestDO> {

    default PageResult<RepairRequestDO> selectPage(RepairRequestPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RepairRequestDO>()
                .eqIfPresent(RepairRequestDO::getRequestCode, reqVO.getRequestCode())
                .eqIfPresent(RepairRequestDO::getEquipmentId, reqVO.getEquipmentId())
                .likeIfPresent(RepairRequestDO::getEquipmentName, reqVO.getEquipmentName())
                .eqIfPresent(RepairRequestDO::getFaultType, reqVO.getFaultType())
                .eqIfPresent(RepairRequestDO::getFaultLevel, reqVO.getFaultLevel())
                .eqIfPresent(RepairRequestDO::getUrgencyLevel, reqVO.getUrgencyLevel())
                .eqIfPresent(RepairRequestDO::getRequestStatus, reqVO.getRequestStatus())
                .eqIfPresent(RepairRequestDO::getRepairQuality, reqVO.getRepairQuality())
                .betweenIfPresent(RepairRequestDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RepairRequestDO::getId));
    }

}