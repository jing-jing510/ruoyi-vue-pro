package cn.iocoder.yudao.module.coal.dal.mysql.energyconsumption;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.energyconsumption.EnergyConsumptionDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.energyconsumption.vo.*;

/**
 * 能源消耗记录 Mapper
 *
 * @author 京京
 */
@Mapper
public interface EnergyConsumptionMapper extends BaseMapperX<EnergyConsumptionDO> {

    default PageResult<EnergyConsumptionDO> selectPage(EnergyConsumptionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EnergyConsumptionDO>()
                .eqIfPresent(EnergyConsumptionDO::getRecordCode, reqVO.getRecordCode())
                .eqIfPresent(EnergyConsumptionDO::getEnergyTypeId, reqVO.getEnergyTypeId())
                .betweenIfPresent(EnergyConsumptionDO::getConsumptionDate, reqVO.getConsumptionDate())
                .eqIfPresent(EnergyConsumptionDO::getDataSource, reqVO.getDataSource())
                .eqIfPresent(EnergyConsumptionDO::getVerificationStatus, reqVO.getVerificationStatus())
                .betweenIfPresent(EnergyConsumptionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EnergyConsumptionDO::getId));
    }

}