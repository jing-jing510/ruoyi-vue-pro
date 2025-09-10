package cn.iocoder.yudao.module.coal.dal.mysql.sparepartcategory;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory.vo.SparePartCategoryListReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartcategory.SparePartCategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 备件分类表 (树表) Mapper
 *
 * @author 京京
 */
@Mapper
public interface SparePartCategoryMapper extends BaseMapperX<SparePartCategoryDO> {

    default List<SparePartCategoryDO> selectList(SparePartCategoryListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SparePartCategoryDO>()
                .eqIfPresent(SparePartCategoryDO::getParentId, reqVO.getParentId())
                .likeIfPresent(SparePartCategoryDO::getCategoryName, reqVO.getCategoryName())
                .eqIfPresent(SparePartCategoryDO::getCategoryCode, reqVO.getCategoryCode())
                .eqIfPresent(SparePartCategoryDO::getCategoryLevel, reqVO.getCategoryLevel())
                .eqIfPresent(SparePartCategoryDO::getSort, reqVO.getSort())
                .eqIfPresent(SparePartCategoryDO::getStatus, reqVO.getStatus())
                .eqIfPresent(SparePartCategoryDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(SparePartCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SparePartCategoryDO::getId));
    }

	default SparePartCategoryDO selectByParentIdAndCategoryName(Long parentId, String categoryName) {
	    return selectOne(SparePartCategoryDO::getParentId, parentId, SparePartCategoryDO::getCategoryName, categoryName);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(SparePartCategoryDO::getParentId, parentId);
    }

}