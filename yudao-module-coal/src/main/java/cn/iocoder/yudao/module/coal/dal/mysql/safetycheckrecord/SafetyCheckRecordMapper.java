package cn.iocoder.yudao.module.coal.dal.mysql.safetycheckrecord;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckrecord.SafetyCheckRecordDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckrecord.vo.*;

/**
 * 安全检查记录 Mapper
 *
 * @author 京京
 */
@Mapper
public interface SafetyCheckRecordMapper extends BaseMapperX<SafetyCheckRecordDO> {

    default PageResult<SafetyCheckRecordDO> selectPage(SafetyCheckRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SafetyCheckRecordDO>()
                .eqIfPresent(SafetyCheckRecordDO::getRecordCode, reqVO.getRecordCode())
                .eqIfPresent(SafetyCheckRecordDO::getPlanId, reqVO.getPlanId())
                .eqIfPresent(SafetyCheckRecordDO::getCheckType, reqVO.getCheckType())
                .eqIfPresent(SafetyCheckRecordDO::getCheckCategoryId, reqVO.getCheckCategoryId())
                .eqIfPresent(SafetyCheckRecordDO::getRecordStatus, reqVO.getRecordStatus())
                .orderByDesc(SafetyCheckRecordDO::getId));
    }

}