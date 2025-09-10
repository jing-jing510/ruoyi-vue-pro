package cn.iocoder.yudao.module.coal.dal.mysql.qualityitem;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityitem.QualityItemDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.qualityitem.vo.*;

/**
 * 质量检测项目 Mapper
 *
 * @author 京京
 */
@Mapper
public interface QualityItemMapper extends BaseMapperX<QualityItemDO> {

    default PageResult<QualityItemDO> selectPage(QualityItemPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QualityItemDO>()
                .eqIfPresent(QualityItemDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(QualityItemDO::getItemName, reqVO.getItemName())
                .eqIfPresent(QualityItemDO::getItemType, reqVO.getItemType())
                .eqIfPresent(QualityItemDO::getStatus, reqVO.getStatus())
                .eqIfPresent(QualityItemDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(QualityItemDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(QualityItemDO::getId));
    }

}