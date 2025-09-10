package cn.iocoder.yudao.module.coal.dal.mysql.qualityinspection;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.qualityinspection.vo.QualityInspectionPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityinspection.QualityInspectionDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 质量检测记录 Mapper
 *
 * @author 京京
 */
@Mapper
public interface QualityInspectionMapper extends BaseMapperX<QualityInspectionDO> {

    default PageResult<QualityInspectionDO> selectPage(QualityInspectionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QualityInspectionDO>()
                .eqIfPresent(QualityInspectionDO::getInspectionCode, reqVO.getInspectionCode())
                .betweenIfPresent(QualityInspectionDO::getInspectionDate, reqVO.getInspectionDate())
                .eqIfPresent(QualityInspectionDO::getInspectionStatus, reqVO.getInspectionStatus())
                .betweenIfPresent(QualityInspectionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(QualityInspectionDO::getId));
    }

}