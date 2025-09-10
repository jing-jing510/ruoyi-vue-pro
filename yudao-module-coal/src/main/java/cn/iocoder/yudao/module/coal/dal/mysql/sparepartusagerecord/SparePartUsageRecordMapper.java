package cn.iocoder.yudao.module.coal.dal.mysql.sparepartusagerecord;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartusagerecord.SparePartUsageRecordDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartusagerecord.vo.*;

/**
 * 备件使用记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SparePartUsageRecordMapper extends BaseMapperX<SparePartUsageRecordDO> {

    default PageResult<SparePartUsageRecordDO> selectPage(SparePartUsageRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SparePartUsageRecordDO>()
                .eqIfPresent(SparePartUsageRecordDO::getUsageType, reqVO.getUsageType())
                .betweenIfPresent(SparePartUsageRecordDO::getUsageDate, reqVO.getUsageDate())
                .eqIfPresent(SparePartUsageRecordDO::getPerformanceRating, reqVO.getPerformanceRating())
                .eqIfPresent(SparePartUsageRecordDO::getWorkOrderId, reqVO.getWorkOrderId())
                .eqIfPresent(SparePartUsageRecordDO::getEquipmentId, reqVO.getEquipmentId())
                .betweenIfPresent(SparePartUsageRecordDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SparePartUsageRecordDO::getId));
    }

    default List<SparePartUsageRecordDO> selectBySparePartId(Long sparePartId) {
        return selectList(new LambdaQueryWrapperX<SparePartUsageRecordDO>()
                .eq(SparePartUsageRecordDO::getSparePartId, sparePartId)
                .orderByAsc(SparePartUsageRecordDO::getUsageDate));
    }

}