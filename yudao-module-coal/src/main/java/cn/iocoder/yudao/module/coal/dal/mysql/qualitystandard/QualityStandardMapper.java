package cn.iocoder.yudao.module.coal.dal.mysql.qualitystandard;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualitystandard.QualityStandardDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.qualitystandard.vo.*;

/**
 * 质量标准 Mapper
 *
 * @author 京京
 */
@Mapper
public interface QualityStandardMapper extends BaseMapperX<QualityStandardDO> {

    default PageResult<QualityStandardDO> selectPage(QualityStandardPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QualityStandardDO>()
                .eqIfPresent(QualityStandardDO::getStandardCode, reqVO.getStandardCode())
                .likeIfPresent(QualityStandardDO::getStandardName, reqVO.getStandardName())
                .eqIfPresent(QualityStandardDO::getProductType, reqVO.getProductType())
                .eqIfPresent(QualityStandardDO::getQualityItemId, reqVO.getQualityItemId())
                .eqIfPresent(QualityStandardDO::getStandardType, reqVO.getStandardType())
                .eqIfPresent(QualityStandardDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(QualityStandardDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(QualityStandardDO::getId));
    }

}