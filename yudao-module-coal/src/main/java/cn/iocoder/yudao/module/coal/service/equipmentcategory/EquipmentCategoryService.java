package cn.iocoder.yudao.module.coal.service.equipmentcategory;

import cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory.vo.EquipmentCategoryListReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory.vo.EquipmentCategorySaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.equipmentcategory.EquipmentCategoryDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 设备分类表 (树表) Service 接口
 *
 * @author 芋道源码
 */
public interface EquipmentCategoryService {

    /**
     * 创建设备分类表 (树表)
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEquipmentCategory(@Valid EquipmentCategorySaveReqVO createReqVO);

    /**
     * 更新设备分类表 (树表)
     *
     * @param updateReqVO 更新信息
     */
    void updateEquipmentCategory(@Valid EquipmentCategorySaveReqVO updateReqVO);

    /**
     * 删除设备分类表 (树表)
     *
     * @param id 编号
     */
    void deleteEquipmentCategory(Long id);


    /**
     * 获得设备分类表 (树表)
     *
     * @param id 编号
     * @return 设备分类表 (树表)
     */
    EquipmentCategoryDO getEquipmentCategory(Long id);

    /**
     * 获得设备分类表 (树表)列表
     *
     * @param listReqVO 查询条件
     * @return 设备分类表 (树表)列表
     */
    List<EquipmentCategoryDO> getEquipmentCategoryList(EquipmentCategoryListReqVO listReqVO);

}