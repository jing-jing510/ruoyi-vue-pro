package cn.iocoder.yudao.module.coal.service.safetycheckcategory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckcategory.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckcategory.SafetyCheckCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 安全检查分类 Service 接口
 *
 * @author 京京
 */
public interface SafetyCheckCategoryService {

    /**
     * 创建安全检查分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSafetyCheckCategory(@Valid SafetyCheckCategorySaveReqVO createReqVO);

    /**
     * 更新安全检查分类
     *
     * @param updateReqVO 更新信息
     */
    void updateSafetyCheckCategory(@Valid SafetyCheckCategorySaveReqVO updateReqVO);

    /**
     * 删除安全检查分类
     *
     * @param id 编号
     */
    void deleteSafetyCheckCategory(Long id);


    /**
     * 获得安全检查分类
     *
     * @param id 编号
     * @return 安全检查分类
     */
    SafetyCheckCategoryDO getSafetyCheckCategory(Long id);

    /**
     * 获得安全检查分类列表
     *
     * @param listReqVO 查询条件
     * @return 安全检查分类列表
     */
    List<SafetyCheckCategoryDO> getSafetyCheckCategoryList(SafetyCheckCategoryListReqVO listReqVO);

    /**
     * 获得安全检查分类简单列表，用于下拉选择
     *
     * @return 安全检查分类简单列表
     */
    List<SafetyCheckCategoryDO> getSimpleSafetyCheckCategoryList();

}