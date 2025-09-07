package cn.iocoder.yudao.module.coal.dal.mysql.sparepart;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartEquipmentPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepart.CoalSparePartEquipmentDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 备件设备关联 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface CoalSparePartEquipmentMapper extends BaseMapperX<CoalSparePartEquipmentDO> {

    /**
     * 根据备件ID查询设备关联列表
     *
     * @param sparePartId 备件ID
     * @return 设备关联列表
     */
    default List<CoalSparePartEquipmentDO> selectListBySparePartId(Long sparePartId) {
        return selectList(CoalSparePartEquipmentDO::getSparePartId, sparePartId);
    }

    /**
     * 根据设备ID查询备件关联列表
     *
     * @param equipmentId 设备ID
     * @return 备件关联列表
     */
    default List<CoalSparePartEquipmentDO> selectListByEquipmentId(Long equipmentId) {
        return selectList(CoalSparePartEquipmentDO::getEquipmentId, equipmentId);
    }

    /**
     * 根据备件ID和设备ID查询关联记录
     *
     * @param sparePartId 备件ID
     * @param equipmentId 设备ID
     * @return 关联记录
     */
    default CoalSparePartEquipmentDO selectBySparePartIdAndEquipmentId(Long sparePartId, Long equipmentId) {
        return selectOne(CoalSparePartEquipmentDO::getSparePartId, sparePartId,
                CoalSparePartEquipmentDO::getEquipmentId, equipmentId);
    }

    /**
     * 根据备件ID删除关联记录
     *
     * @param sparePartId 备件ID
     * @return 删除数量
     */
    default int deleteBySparePartId(Long sparePartId) {
        return delete(CoalSparePartEquipmentDO::getSparePartId, sparePartId);
    }

    /**
     * 根据设备ID删除关联记录
     *
     * @param equipmentId 设备ID
     * @return 删除数量
     */
    default int deleteByEquipmentId(Long equipmentId) {
        return delete(CoalSparePartEquipmentDO::getEquipmentId, equipmentId);
    }

    /**
     * 分页查询备件设备关联
     *
     * @param reqVO 查询条件
     * @return 分页结果
     */
    default PageResult<CoalSparePartEquipmentDO> selectPage(CoalSparePartEquipmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CoalSparePartEquipmentDO>()
                .eqIfPresent(CoalSparePartEquipmentDO::getSparePartId, reqVO.getSparePartId())
                .eqIfPresent(CoalSparePartEquipmentDO::getEquipmentId, reqVO.getEquipmentId())
                .eqIfPresent(CoalSparePartEquipmentDO::getIsRequired, reqVO.getIsRequired())
                .orderByDesc(CoalSparePartEquipmentDO::getId));
    }

}
