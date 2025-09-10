package cn.iocoder.yudao.module.coal.dal.mysql.qualityalert;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.qualityalert.vo.QualityAlertPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.qualityalert.QualityAlertDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 质量预警记录 Mapper
 *
 * @author 京京
 */
@Mapper
public interface QualityAlertMapper extends BaseMapperX<QualityAlertDO> {

    default PageResult<QualityAlertDO> selectPage(QualityAlertPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QualityAlertDO>()
                .eqIfPresent(QualityAlertDO::getAlertCode, reqVO.getAlertCode())
                .eqIfPresent(QualityAlertDO::getAlertType, reqVO.getAlertType())
                .eqIfPresent(QualityAlertDO::getAlertLevel, reqVO.getAlertLevel())
                .eqIfPresent(QualityAlertDO::getQualityItemId, reqVO.getQualityItemId())
                .eqIfPresent(QualityAlertDO::getInspectionId, reqVO.getInspectionId())
                .eqIfPresent(QualityAlertDO::getProductType, reqVO.getProductType())
                .eqIfPresent(QualityAlertDO::getAlertStatus, reqVO.getAlertStatus())
                .eqIfPresent(QualityAlertDO::getIsAutoAlert, reqVO.getIsAutoAlert())
                .eqIfPresent(QualityAlertDO::getNotificationSent, reqVO.getNotificationSent())
                .betweenIfPresent(QualityAlertDO::getAlertTime, reqVO.getAlertTime())
                .orderByDesc(QualityAlertDO::getId));
    }

}