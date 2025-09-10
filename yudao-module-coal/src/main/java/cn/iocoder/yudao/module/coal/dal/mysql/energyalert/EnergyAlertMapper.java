package cn.iocoder.yudao.module.coal.dal.mysql.energyalert;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.energyalert.EnergyAlertDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.energyalert.vo.*;

/**
 * 能源预警记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface EnergyAlertMapper extends BaseMapperX<EnergyAlertDO> {

    default PageResult<EnergyAlertDO> selectPage(EnergyAlertPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EnergyAlertDO>()
                .eqIfPresent(EnergyAlertDO::getAlertCode, reqVO.getAlertCode())
                .eqIfPresent(EnergyAlertDO::getEnergyTypeId, reqVO.getEnergyTypeId())
                .eqIfPresent(EnergyAlertDO::getAlertType, reqVO.getAlertType())
                .eqIfPresent(EnergyAlertDO::getAlertLevel, reqVO.getAlertLevel())
                .eqIfPresent(EnergyAlertDO::getAlertStatus, reqVO.getAlertStatus())
                .betweenIfPresent(EnergyAlertDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EnergyAlertDO::getId));
    }

}