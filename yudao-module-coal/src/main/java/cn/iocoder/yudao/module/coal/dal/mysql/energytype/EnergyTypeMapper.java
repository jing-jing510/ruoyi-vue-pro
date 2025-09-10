package cn.iocoder.yudao.module.coal.dal.mysql.energytype;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.energytype.EnergyTypeDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.energytype.vo.*;

/**
 * 能源类型配置 Mapper
 *
 * @author 京京
 */
@Mapper
public interface EnergyTypeMapper extends BaseMapperX<EnergyTypeDO> {

    default PageResult<EnergyTypeDO> selectPage(EnergyTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EnergyTypeDO>()
                .eqIfPresent(EnergyTypeDO::getTypeCode, reqVO.getTypeCode())
                .likeIfPresent(EnergyTypeDO::getTypeName, reqVO.getTypeName())
                .eqIfPresent(EnergyTypeDO::getDataSource, reqVO.getDataSource())
                .betweenIfPresent(EnergyTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EnergyTypeDO::getId));
    }

}