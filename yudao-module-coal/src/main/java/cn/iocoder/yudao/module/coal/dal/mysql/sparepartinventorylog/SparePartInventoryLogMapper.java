package cn.iocoder.yudao.module.coal.dal.mysql.sparepartinventorylog;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinventorylog.SparePartInventoryLogDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinventorylog.vo.*;

/**
 * 备件出入库记录 Mapper
 *
 * @author 京京
 */
@Mapper
public interface SparePartInventoryLogMapper extends BaseMapperX<SparePartInventoryLogDO> {

    default PageResult<SparePartInventoryLogDO> selectPage(SparePartInventoryLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SparePartInventoryLogDO>()
                .eqIfPresent(SparePartInventoryLogDO::getOperationType, reqVO.getOperationType())
                .eqIfPresent(SparePartInventoryLogDO::getWarehouseLocation, reqVO.getWarehouseLocation())
                .eqIfPresent(SparePartInventoryLogDO::getApproverId, reqVO.getApproverId())
                .betweenIfPresent(SparePartInventoryLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SparePartInventoryLogDO::getId));
    }

}