package cn.iocoder.yudao.module.coal.dal.mysql.safetycheckcategory;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckcategory.SafetyCheckCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckcategory.vo.*;

/**
 * 安全检查分类 Mapper
 *
 * @author 京京
 */
@Mapper
public interface SafetyCheckCategoryMapper extends BaseMapperX<SafetyCheckCategoryDO> {

    default List<SafetyCheckCategoryDO> selectList(SafetyCheckCategoryListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SafetyCheckCategoryDO>()
                .eqIfPresent(SafetyCheckCategoryDO::getParentId, reqVO.getParentId())
                .likeIfPresent(SafetyCheckCategoryDO::getCategoryName, reqVO.getCategoryName())
                .eqIfPresent(SafetyCheckCategoryDO::getCategoryCode, reqVO.getCategoryCode())
                .eqIfPresent(SafetyCheckCategoryDO::getCategoryType, reqVO.getCategoryType())
                .eqIfPresent(SafetyCheckCategoryDO::getSort, reqVO.getSort())
                .eqIfPresent(SafetyCheckCategoryDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(SafetyCheckCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SafetyCheckCategoryDO::getId));
    }

	default SafetyCheckCategoryDO selectByParentIdAndCategoryName(Long parentId, String categoryName) {
	    return selectOne(SafetyCheckCategoryDO::getParentId, parentId, SafetyCheckCategoryDO::getCategoryName, categoryName);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(SafetyCheckCategoryDO::getParentId, parentId);
    }

}