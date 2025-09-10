package cn.iocoder.yudao.module.coal.dal.mysql.sparepartinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartinfo.SparePartInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartinfo.vo.*;

/**
 * 备件基础信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SparePartInfoMapper extends BaseMapperX<SparePartInfoDO> {

    default PageResult<SparePartInfoDO> selectPage(SparePartInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SparePartInfoDO>()
                .eqIfPresent(SparePartInfoDO::getSparePartCode, reqVO.getSparePartCode())
                .likeIfPresent(SparePartInfoDO::getSparePartName, reqVO.getSparePartName())
                .eqIfPresent(SparePartInfoDO::getCategoryId, reqVO.getCategoryId())
                .eqIfPresent(SparePartInfoDO::getSparePartType, reqVO.getSparePartType())
                .eqIfPresent(SparePartInfoDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SparePartInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SparePartInfoDO::getId));
    }

}