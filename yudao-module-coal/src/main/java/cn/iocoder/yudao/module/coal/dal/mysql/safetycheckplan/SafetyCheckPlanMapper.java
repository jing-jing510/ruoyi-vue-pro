package cn.iocoder.yudao.module.coal.dal.mysql.safetycheckplan;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckplan.SafetyCheckPlanDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckplan.vo.*;

/**
 * 安全检查计划 Mapper
 *
 * @author 京京
 */
@Mapper
public interface SafetyCheckPlanMapper extends BaseMapperX<SafetyCheckPlanDO> {

    default PageResult<SafetyCheckPlanDO> selectPage(SafetyCheckPlanPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SafetyCheckPlanDO>()
                .eqIfPresent(SafetyCheckPlanDO::getPlanCode, reqVO.getPlanCode())
                .likeIfPresent(SafetyCheckPlanDO::getPlanName, reqVO.getPlanName())
                .eqIfPresent(SafetyCheckPlanDO::getPlanType, reqVO.getPlanType())
                .eqIfPresent(SafetyCheckPlanDO::getPlanStatus, reqVO.getPlanStatus())
                .betweenIfPresent(SafetyCheckPlanDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SafetyCheckPlanDO::getId));
    }

}