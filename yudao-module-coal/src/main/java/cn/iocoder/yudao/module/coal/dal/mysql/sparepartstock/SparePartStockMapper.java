package cn.iocoder.yudao.module.coal.dal.mysql.sparepartstock;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartstock.SparePartStockDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartstock.vo.*;

/**
 * 备件库存记录 Mapper
 *
 * @author 京京
 */
@Mapper
public interface SparePartStockMapper extends BaseMapperX<SparePartStockDO> {

    default PageResult<SparePartStockDO> selectPage(SparePartStockPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SparePartStockDO>()
                .eqIfPresent(SparePartStockDO::getWarehouseLocation, reqVO.getWarehouseLocation())
                .eqIfPresent(SparePartStockDO::getStockType, reqVO.getStockType())
                .betweenIfPresent(SparePartStockDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SparePartStockDO::getId));
    }

    default SparePartStockDO selectBySparePartId(Long sparePartId) {
        return selectOne(new LambdaQueryWrapperX<SparePartStockDO>()
                .eq(SparePartStockDO::getSparePartId, sparePartId));
    }

}