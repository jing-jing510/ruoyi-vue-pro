package cn.iocoder.yudao.module.coal.service.sparepartcategory;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory.vo.SparePartCategoryListReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartcategory.vo.SparePartCategorySaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartcategory.SparePartCategoryDO;
import cn.iocoder.yudao.module.coal.dal.mysql.sparepartcategory.SparePartCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 备件分类表 (树表) Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class SparePartCategoryServiceImpl implements SparePartCategoryService {

    @Resource
    private SparePartCategoryMapper sparePartCategoryMapper;

    @Override
    public Long createSparePartCategory(SparePartCategorySaveReqVO createReqVO) {
        // 校验父分类ID (0=根分类)的有效性
        validateParentSparePartCategory(null, createReqVO.getParentId());
        // 校验分类名称的唯一性
        validateSparePartCategoryCategoryNameUnique(null, createReqVO.getParentId(), createReqVO.getCategoryName());

        // 插入
        SparePartCategoryDO sparePartCategory = BeanUtils.toBean(createReqVO, SparePartCategoryDO.class);
        sparePartCategoryMapper.insert(sparePartCategory);

        // 返回
        return sparePartCategory.getId();
    }

    @Override
    public void updateSparePartCategory(SparePartCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateSparePartCategoryExists(updateReqVO.getId());
        // 校验父分类ID (0=根分类)的有效性
        validateParentSparePartCategory(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验分类名称的唯一性
        validateSparePartCategoryCategoryNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getCategoryName());

        // 更新
        SparePartCategoryDO updateObj = BeanUtils.toBean(updateReqVO, SparePartCategoryDO.class);
        sparePartCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteSparePartCategory(Long id) {
        // 校验存在
        validateSparePartCategoryExists(id);
        // 校验是否有子备件分类表 (树表)
        if (sparePartCategoryMapper.selectCountByParentId(id) > 0) {
            throw exception(SPARE_PART_CATEGORY_EXITS_CHILDREN);
        }
        // 删除
        sparePartCategoryMapper.deleteById(id);
    }


    private void validateSparePartCategoryExists(Long id) {
        if (sparePartCategoryMapper.selectById(id) == null) {
            throw exception(SPARE_PART_CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentSparePartCategory(Long id, Long parentId) {
        if (parentId == null || SparePartCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父备件分类表 (树表)
        if (Objects.equals(id, parentId)) {
            throw exception(SPARE_PART_CATEGORY_PARENT_ERROR);
        }
        // 2. 父备件分类表 (树表)不存在
        SparePartCategoryDO parentSparePartCategory = sparePartCategoryMapper.selectById(parentId);
        if (parentSparePartCategory == null) {
            throw exception(SPARE_PART_CATEGORY_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父备件分类表 (树表)，如果父备件分类表 (树表)是自己的子备件分类表 (树表)，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentSparePartCategory.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(SPARE_PART_CATEGORY_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父备件分类表 (树表)
            if (parentId == null || SparePartCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentSparePartCategory = sparePartCategoryMapper.selectById(parentId);
            if (parentSparePartCategory == null) {
                break;
            }
        }
    }

    private void validateSparePartCategoryCategoryNameUnique(Long id, Long parentId, String categoryName) {
        SparePartCategoryDO sparePartCategory = sparePartCategoryMapper.selectByParentIdAndCategoryName(parentId, categoryName);
        if (sparePartCategory == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的备件分类表 (树表)
        if (id == null) {
            throw exception(SPARE_PART_CATEGORY_CATEGORY_NAME_DUPLICATE);
        }
        if (!Objects.equals(sparePartCategory.getId(), id)) {
            throw exception(SPARE_PART_CATEGORY_CATEGORY_NAME_DUPLICATE);
        }
    }

    @Override
    public SparePartCategoryDO getSparePartCategory(Long id) {
        return sparePartCategoryMapper.selectById(id);
    }

    @Override
    public List<SparePartCategoryDO> getSparePartCategoryList(SparePartCategoryListReqVO listReqVO) {
        return sparePartCategoryMapper.selectList(listReqVO);
    }

}