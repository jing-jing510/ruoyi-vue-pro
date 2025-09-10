package cn.iocoder.yudao.module.coal.service.sparepartequipment;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartequipment.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartequipment.SparePartEquipmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 备件设备关联 Service 接口
 *
 * @author 芋道源码
 */
public interface SparePartEquipmentService {

    /**
     * 创建备件设备关联
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSparePartEquipment(@Valid SparePartEquipmentSaveReqVO createReqVO);

    /**
     * 更新备件设备关联
     *
     * @param updateReqVO 更新信息
     */
    void updateSparePartEquipment(@Valid SparePartEquipmentSaveReqVO updateReqVO);

    /**
     * 删除备件设备关联
     *
     * @param id 编号
     */
    void deleteSparePartEquipment(Long id);

    /**
     * 获得备件设备关联
     *
     * @param id 编号
     * @return 备件设备关联
     */
    SparePartEquipmentDO getSparePartEquipment(Long id);

    /**
     * 获得备件设备关联分页
     *
     * @param pageReqVO 分页查询
     * @return 备件设备关联分页
     */
    PageResult<SparePartEquipmentDO> getSparePartEquipmentPage(SparePartEquipmentPageReqVO pageReqVO);

    /**
     * 获得备件设备关联列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 备件设备关联列表
     */
    List<SparePartEquipmentDO> getSparePartEquipmentList(SparePartEquipmentPageReqVO exportReqVO);

    /**
     * 批量创建备件设备关联列表
     *
     * @param sparePartId 备件ID
     * @param createReqList 创建信息列表
     */
    void createSparePartEquipmentList(Long sparePartId, List<SparePartEquipmentSaveReqVO> createReqList);

    /**
     * 根据备件ID获取关联的设备列表
     *
     * @param sparePartId 备件ID
     * @return 关联的设备列表
     */
    List<SparePartEquipmentDO> getSparePartEquipmentListBySparePartId(Long sparePartId);

    /**
     * 根据设备ID获取关联的备件列表
     *
     * @param equipmentId 设备ID
     * @return 关联的备件列表
     */
    List<SparePartEquipmentDO> getSparePartEquipmentListByEquipmentId(Long equipmentId);

    /**
     * 根据备件ID删除所有关联关系
     *
     * @param sparePartId 备件ID
     */
    void deleteSparePartEquipmentBySparePartId(Long sparePartId);

}
