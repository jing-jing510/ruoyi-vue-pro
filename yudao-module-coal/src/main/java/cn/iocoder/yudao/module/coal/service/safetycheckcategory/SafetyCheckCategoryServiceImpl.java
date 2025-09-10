package cn.iocoder.yudao.module.coal.service.safetycheckcategory;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckcategory.vo.SafetyCheckCategoryListReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.safetycheckcategory.vo.SafetyCheckCategorySaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.safetycheckcategory.SafetyCheckCategoryDO;
import cn.iocoder.yudao.module.coal.dal.mysql.safetycheckcategory.SafetyCheckCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 安全检查分类 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class SafetyCheckCategoryServiceImpl implements SafetyCheckCategoryService {

    @Resource
    private SafetyCheckCategoryMapper safetyCheckCategoryMapper;

    @Override
    public Long createSafetyCheckCategory(SafetyCheckCategorySaveReqVO createReqVO) {
        // 校验父分类ID的有效性
        validateParentSafetyCheckCategory(null, createReqVO.getParentId());
        // 校验分类名称的唯一性
        validateSafetyCheckCategoryCategoryNameUnique(null, createReqVO.getParentId(), createReqVO.getCategoryName());

        // 插入
        SafetyCheckCategoryDO safetyCheckCategory = BeanUtils.toBean(createReqVO, SafetyCheckCategoryDO.class);
        safetyCheckCategoryMapper.insert(safetyCheckCategory);

        // 返回
        return safetyCheckCategory.getId();
    }

    @Override
    public void updateSafetyCheckCategory(SafetyCheckCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateSafetyCheckCategoryExists(updateReqVO.getId());
        // 校验父分类ID的有效性
        validateParentSafetyCheckCategory(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验分类名称的唯一性
        validateSafetyCheckCategoryCategoryNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getCategoryName());

        // 更新
        SafetyCheckCategoryDO updateObj = BeanUtils.toBean(updateReqVO, SafetyCheckCategoryDO.class);
        safetyCheckCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteSafetyCheckCategory(Long id) {
        // 校验存在
        validateSafetyCheckCategoryExists(id);
        // 校验是否有子安全检查分类
        if (safetyCheckCategoryMapper.selectCountByParentId(id) > 0) {
            throw exception(SAFETY_CHECK_CATEGORY_EXITS_CHILDREN);
        }
        // 删除
        safetyCheckCategoryMapper.deleteById(id);
    }


    private void validateSafetyCheckCategoryExists(Long id) {
        if (safetyCheckCategoryMapper.selectById(id) == null) {
            throw exception(SAFETY_CHECK_CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentSafetyCheckCategory(Long id, Long parentId) {
        if (parentId == null || SafetyCheckCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父安全检查分类
        if (Objects.equals(id, parentId)) {
            throw exception(SAFETY_CHECK_CATEGORY_PARENT_ERROR);
        }
        // 2. 父安全检查分类不存在
        SafetyCheckCategoryDO parentSafetyCheckCategory = safetyCheckCategoryMapper.selectById(parentId);
        if (parentSafetyCheckCategory == null) {
            throw exception(SAFETY_CHECK_CATEGORY_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父安全检查分类，如果父安全检查分类是自己的子安全检查分类，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentSafetyCheckCategory.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(SAFETY_CHECK_CATEGORY_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父安全检查分类
            if (parentId == null || SafetyCheckCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentSafetyCheckCategory = safetyCheckCategoryMapper.selectById(parentId);
            if (parentSafetyCheckCategory == null) {
                break;
            }
        }
    }

    private void validateSafetyCheckCategoryCategoryNameUnique(Long id, Long parentId, String categoryName) {
        SafetyCheckCategoryDO safetyCheckCategory = safetyCheckCategoryMapper.selectByParentIdAndCategoryName(parentId, categoryName);
        if (safetyCheckCategory == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的安全检查分类
        if (id == null) {
            throw exception(SAFETY_CHECK_CATEGORY_CATEGORY_NAME_DUPLICATE);
        }
        if (!Objects.equals(safetyCheckCategory.getId(), id)) {
            throw exception(SAFETY_CHECK_CATEGORY_CATEGORY_NAME_DUPLICATE);
        }
    }

    @Override
    public SafetyCheckCategoryDO getSafetyCheckCategory(Long id) {
        return safetyCheckCategoryMapper.selectById(id);
    }

    @Override
    public List<SafetyCheckCategoryDO> getSafetyCheckCategoryList(SafetyCheckCategoryListReqVO listReqVO) {
        return safetyCheckCategoryMapper.selectList(listReqVO);
    }

    @Override
    public List<SafetyCheckCategoryDO> getSimpleSafetyCheckCategoryList() {
        return safetyCheckCategoryMapper.selectList();
    }

}