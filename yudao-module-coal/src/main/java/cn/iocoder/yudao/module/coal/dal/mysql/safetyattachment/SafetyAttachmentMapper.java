package cn.iocoder.yudao.module.coal.dal.mysql.safetyattachment;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetyattachment.SafetyAttachmentDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.safetyattachment.vo.*;

/**
 * 安全附件 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SafetyAttachmentMapper extends BaseMapperX<SafetyAttachmentDO> {

    default PageResult<SafetyAttachmentDO> selectPage(SafetyAttachmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SafetyAttachmentDO>()
                .eqIfPresent(SafetyAttachmentDO::getBusinessType, reqVO.getBusinessType())
                .eqIfPresent(SafetyAttachmentDO::getBusinessId, reqVO.getBusinessId())
                .betweenIfPresent(SafetyAttachmentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SafetyAttachmentDO::getId));
    }

}