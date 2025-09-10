package cn.iocoder.yudao.module.coal.dal.mysql.sparepartalert;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartalert.SparePartAlertDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartalert.vo.*;

/**
 * 备件预警记录 Mapper
 *
 * @author 京京
 */
@Mapper
public interface SparePartAlertMapper extends BaseMapperX<SparePartAlertDO> {

    default PageResult<SparePartAlertDO> selectPage(SparePartAlertPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SparePartAlertDO>()
                .eqIfPresent(SparePartAlertDO::getAlertType, reqVO.getAlertType())
                .eqIfPresent(SparePartAlertDO::getAlertLevel, reqVO.getAlertLevel())
                .eqIfPresent(SparePartAlertDO::getAlertStatus, reqVO.getAlertStatus())
                .eqIfPresent(SparePartAlertDO::getAlertTitle, reqVO.getAlertTitle())
                .eqIfPresent(SparePartAlertDO::getAlertMessage, reqVO.getAlertMessage())
                .betweenIfPresent(SparePartAlertDO::getSendTime, reqVO.getSendTime())
                .eqIfPresent(SparePartAlertDO::getRecipients, reqVO.getRecipients())
                .betweenIfPresent(SparePartAlertDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SparePartAlertDO::getId));
    }

}