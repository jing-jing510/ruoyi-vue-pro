package cn.iocoder.yudao.module.coal.dal.mysql.sparepartequipment;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartequipment.vo.SparePartEquipmentPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartequipment.SparePartEquipmentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 备件设备关联 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SparePartEquipmentMapper extends BaseMapperX<SparePartEquipmentDO> {

    default PageResult<SparePartEquipmentDO> selectPage(SparePartEquipmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SparePartEquipmentDO>()
                .eqIfPresent(SparePartEquipmentDO::getSparePartId, reqVO.getSparePartId())
                .eqIfPresent(SparePartEquipmentDO::getEquipmentId, reqVO.getEquipmentId())
                .eqIfPresent(SparePartEquipmentDO::getIsRequired, reqVO.getIsRequired())
                .eqIfPresent(SparePartEquipmentDO::getReplacementDifficulty, reqVO.getReplacementDifficulty())
                .betweenIfPresent(SparePartEquipmentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SparePartEquipmentDO::getId));
    }

    default List<SparePartEquipmentDO> selectList(SparePartEquipmentPageReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<SparePartEquipmentDO>()
                .eqIfPresent(SparePartEquipmentDO::getSparePartId, reqVO.getSparePartId())
                .eqIfPresent(SparePartEquipmentDO::getEquipmentId, reqVO.getEquipmentId())
                .eqIfPresent(SparePartEquipmentDO::getIsRequired, reqVO.getIsRequired())
                .eqIfPresent(SparePartEquipmentDO::getReplacementDifficulty, reqVO.getReplacementDifficulty())
                .betweenIfPresent(SparePartEquipmentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SparePartEquipmentDO::getId));
    }

    default List<SparePartEquipmentDO> selectListBySparePartId(Long sparePartId) {
        return selectList(new LambdaQueryWrapperX<SparePartEquipmentDO>()
                .eq(SparePartEquipmentDO::getSparePartId, sparePartId)
                .orderByAsc(SparePartEquipmentDO::getId));
    }

    default List<SparePartEquipmentDO> selectListByEquipmentId(Long equipmentId) {
        return selectList(new LambdaQueryWrapperX<SparePartEquipmentDO>()
                .eq(SparePartEquipmentDO::getEquipmentId, equipmentId)
                .orderByAsc(SparePartEquipmentDO::getId));
    }

    default void deleteBySparePartId(Long sparePartId) {
        delete(new LambdaQueryWrapperX<SparePartEquipmentDO>()
                .eq(SparePartEquipmentDO::getSparePartId, sparePartId));
    }

}
