package cn.iocoder.yudao.module.coal.service.sparepart;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartEquipmentPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepart.CoalSparePartEquipmentDO;

import java.util.List;

/**
 * 备件设备关联 Service 接口
 *
 * @author 芋道源码
 */
public interface CoalSparePartEquipmentService {

    /**
     * 创建备件设备关联
     *
     * @param sparePartId 备件ID
     * @param equipmentId 设备ID
     * @param usageCount 使用数量
     * @param isRequired 是否必需
     * @param remark 备注
     * @return 关联ID
     */
    Long createSparePartEquipment(Long sparePartId, Long equipmentId, Integer usageCount, Integer isRequired, String remark);

    /**
     * 更新备件设备关联
     *
     * @param id 关联ID
     * @param usageCount 使用数量
     * @param isRequired 是否必需
     * @param remark 备注
     */
    void updateSparePartEquipment(Long id, Integer usageCount, Integer isRequired, String remark);

    /**
     * 删除备件设备关联
     *
     * @param id 关联ID
     */
    void deleteSparePartEquipment(Long id);

    /**
     * 根据备件ID删除关联记录
     *
     * @param sparePartId 备件ID
     */
    void deleteSparePartEquipmentBySparePartId(Long sparePartId);

    /**
     * 根据设备ID删除关联记录
     *
     * @param equipmentId 设备ID
     */
    void deleteSparePartEquipmentByEquipmentId(Long equipmentId);

    /**
     * 根据备件ID查询设备关联列表
     *
     * @param sparePartId 备件ID
     * @return 设备关联列表
     */
    List<CoalSparePartEquipmentDO> getSparePartEquipments(Long sparePartId);

    /**
     * 根据设备ID查询备件关联列表
     *
     * @param equipmentId 设备ID
     * @return 备件关联列表
     */
    List<CoalSparePartEquipmentDO> getEquipmentSpareParts(Long equipmentId);

    /**
     * 根据备件ID和设备ID查询关联记录
     *
     * @param sparePartId 备件ID
     * @param equipmentId 设备ID
     * @return 关联记录
     */
    CoalSparePartEquipmentDO getSparePartEquipment(Long sparePartId, Long equipmentId);

    /**
     * 根据ID查询关联记录
     *
     * @param id 关联ID
     * @return 关联记录
     */
    CoalSparePartEquipmentDO getSparePartEquipment(Long id);

    /**
     * 获得备件设备关联分页
     *
     * @param pageReqVO 分页查询
     * @return 备件设备关联分页
     */
    PageResult<CoalSparePartEquipmentDO> getSparePartEquipmentPage(CoalSparePartEquipmentPageReqVO pageReqVO);

}
