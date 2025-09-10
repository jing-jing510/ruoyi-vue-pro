package cn.iocoder.yudao.module.coal.dal.mysql.safetyaccident;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetyaccident.SafetyAccidentDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.safetyaccident.vo.*;

/**
 * 安全事故记录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SafetyAccidentMapper extends BaseMapperX<SafetyAccidentDO> {

    default PageResult<SafetyAccidentDO> selectPage(SafetyAccidentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SafetyAccidentDO>()
                .eqIfPresent(SafetyAccidentDO::getAccidentCode, reqVO.getAccidentCode())
                .eqIfPresent(SafetyAccidentDO::getAccidentType, reqVO.getAccidentType())
                .eqIfPresent(SafetyAccidentDO::getAccidentLevel, reqVO.getAccidentLevel())
                .eqIfPresent(SafetyAccidentDO::getAccidentStatus, reqVO.getAccidentStatus())
                .eqIfPresent(SafetyAccidentDO::getApprovalStatus, reqVO.getApprovalStatus())
                .betweenIfPresent(SafetyAccidentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SafetyAccidentDO::getId));
    }

}