package cn.iocoder.yudao.module.coal.service.sparepartcategory;

import cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory.vo.SparePartCategoryListReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory.vo.SparePartCategorySaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartcategory.SparePartCategoryDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 备件分类表 (树表) Service 接口
 *
 * @author 京京
 */
public interface SparePartCategoryService {

    /**
     * 创建备件分类表 (树表)
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSparePartCategory(@Valid SparePartCategorySaveReqVO createReqVO);

    /**
     * 更新备件分类表 (树表)
     *
     * @param updateReqVO 更新信息
     */
    void updateSparePartCategory(@Valid SparePartCategorySaveReqVO updateReqVO);

    /**
     * 删除备件分类表 (树表)
     *
     * @param id 编号
     */
    void deleteSparePartCategory(Long id);


    /**
     * 获得备件分类表 (树表)
     *
     * @param id 编号
     * @return 备件分类表 (树表)
     */
    SparePartCategoryDO getSparePartCategory(Long id);

    /**
     * 获得备件分类表 (树表)列表
     *
     * @param listReqVO 查询条件
     * @return 备件分类表 (树表)列表
     */
    List<SparePartCategoryDO> getSparePartCategoryList(SparePartCategoryListReqVO listReqVO);

}