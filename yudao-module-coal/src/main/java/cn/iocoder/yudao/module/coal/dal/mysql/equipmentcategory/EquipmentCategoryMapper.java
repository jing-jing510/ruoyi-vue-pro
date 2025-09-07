package cn.iocoder.yudao.module.coal.dal.mysql.equipmentcategory;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory.vo.EquipmentCategoryListReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.equipmentcategory.EquipmentCategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备分类表 (树表) Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface EquipmentCategoryMapper extends BaseMapperX<EquipmentCategoryDO> {

    default List<EquipmentCategoryDO> selectList(EquipmentCategoryListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EquipmentCategoryDO>()
                .eqIfPresent(EquipmentCategoryDO::getParentId, reqVO.getParentId())
                .likeIfPresent(EquipmentCategoryDO::getCategoryName, reqVO.getCategoryName())
                .eqIfPresent(EquipmentCategoryDO::getCategoryCode, reqVO.getCategoryCode())
                .eqIfPresent(EquipmentCategoryDO::getCategoryLevel, reqVO.getCategoryLevel())
                .eqIfPresent(EquipmentCategoryDO::getSort, reqVO.getSort())
                .eqIfPresent(EquipmentCategoryDO::getStatus, reqVO.getStatus())
                .eqIfPresent(EquipmentCategoryDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(EquipmentCategoryDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EquipmentCategoryDO::getId));
    }

	default EquipmentCategoryDO selectByParentIdAndCategoryName(Long parentId, String categoryName) {
	    return selectOne(EquipmentCategoryDO::getParentId, parentId, EquipmentCategoryDO::getCategoryName, categoryName);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(EquipmentCategoryDO::getParentId, parentId);
    }

}