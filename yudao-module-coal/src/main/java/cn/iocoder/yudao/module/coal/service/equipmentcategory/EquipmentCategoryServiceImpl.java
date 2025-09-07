package cn.iocoder.yudao.module.coal.service.equipmentcategory;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory.vo.EquipmentCategoryListReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentcategory.vo.EquipmentCategorySaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.equipmentcategory.EquipmentCategoryDO;
import cn.iocoder.yudao.module.coal.dal.mysql.equipmentcategory.EquipmentCategoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 设备分类表 (树表) Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class EquipmentCategoryServiceImpl implements EquipmentCategoryService {

    @Resource
    private EquipmentCategoryMapper equipmentCategoryMapper;

    @Override
    public Long createEquipmentCategory(EquipmentCategorySaveReqVO createReqVO) {
        // 校验父分类ID的有效性
        validateParentEquipmentCategory(null, createReqVO.getParentId());
        // 校验分类名称的唯一性
        validateEquipmentCategoryCategoryNameUnique(null, createReqVO.getParentId(), createReqVO.getCategoryName());

        // 插入
        EquipmentCategoryDO equipmentCategory = BeanUtils.toBean(createReqVO, EquipmentCategoryDO.class);
        equipmentCategoryMapper.insert(equipmentCategory);

        // 返回
        return equipmentCategory.getId();
    }

    @Override
    public void updateEquipmentCategory(EquipmentCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateEquipmentCategoryExists(updateReqVO.getId());
        // 校验父分类ID的有效性
        validateParentEquipmentCategory(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验分类名称的唯一性
        validateEquipmentCategoryCategoryNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getCategoryName());

        // 更新
        EquipmentCategoryDO updateObj = BeanUtils.toBean(updateReqVO, EquipmentCategoryDO.class);
        equipmentCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteEquipmentCategory(Long id) {
        // 校验存在
        validateEquipmentCategoryExists(id);
        // 校验是否有子设备分类表 (树表)
        if (equipmentCategoryMapper.selectCountByParentId(id) > 0) {
            throw exception(EQUIPMENT_CATEGORY_EXITS_CHILDREN);
        }
        // 删除
        equipmentCategoryMapper.deleteById(id);
    }


    private void validateEquipmentCategoryExists(Long id) {
        if (equipmentCategoryMapper.selectById(id) == null) {
            throw exception(EQUIPMENT_CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentEquipmentCategory(Long id, Long parentId) {
        if (parentId == null || EquipmentCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父设备分类表 (树表)
        if (Objects.equals(id, parentId)) {
            throw exception(EQUIPMENT_CATEGORY_PARENT_ERROR);
        }
        // 2. 父设备分类表 (树表)不存在
        EquipmentCategoryDO parentEquipmentCategory = equipmentCategoryMapper.selectById(parentId);
        if (parentEquipmentCategory == null) {
            throw exception(EQUIPMENT_CATEGORY_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父设备分类表 (树表)，如果父设备分类表 (树表)是自己的子设备分类表 (树表)，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentEquipmentCategory.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(EQUIPMENT_CATEGORY_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父设备分类表 (树表)
            if (parentId == null || EquipmentCategoryDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentEquipmentCategory = equipmentCategoryMapper.selectById(parentId);
            if (parentEquipmentCategory == null) {
                break;
            }
        }
    }

    private void validateEquipmentCategoryCategoryNameUnique(Long id, Long parentId, String categoryName) {
        EquipmentCategoryDO equipmentCategory = equipmentCategoryMapper.selectByParentIdAndCategoryName(parentId, categoryName);
        if (equipmentCategory == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的设备分类表 (树表)
        if (id == null) {
            throw exception(EQUIPMENT_CATEGORY_CATEGORY_NAME_DUPLICATE);
        }
        if (!Objects.equals(equipmentCategory.getId(), id)) {
            throw exception(EQUIPMENT_CATEGORY_CATEGORY_NAME_DUPLICATE);
        }
    }

    @Override
    public EquipmentCategoryDO getEquipmentCategory(Long id) {
        return equipmentCategoryMapper.selectById(id);
    }

    @Override
    public List<EquipmentCategoryDO> getEquipmentCategoryList(EquipmentCategoryListReqVO listReqVO) {
        return equipmentCategoryMapper.selectList(listReqVO);
    }

}