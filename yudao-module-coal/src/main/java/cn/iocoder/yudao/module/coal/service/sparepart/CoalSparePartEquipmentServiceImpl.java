package cn.iocoder.yudao.module.coal.service.sparepart;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.coal.controller.admin.sparepart.vo.CoalSparePartEquipmentPageReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepart.CoalSparePartEquipmentDO;
import cn.iocoder.yudao.module.coal.dal.mysql.sparepart.CoalSparePartEquipmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SPARE_PART_EQUIPMENT_NOT_EXISTS;

/**
 * 备件设备关联 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class CoalSparePartEquipmentServiceImpl implements CoalSparePartEquipmentService {

    @Resource
    private CoalSparePartEquipmentMapper sparePartEquipmentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSparePartEquipment(Long sparePartId, Long equipmentId, Integer usageCount, Integer isRequired, String remark) {
        // 检查是否已存在关联
        CoalSparePartEquipmentDO existingRelation = sparePartEquipmentMapper.selectBySparePartIdAndEquipmentId(sparePartId, equipmentId);
        if (existingRelation != null) {
            throw exception(SPARE_PART_EQUIPMENT_NOT_EXISTS);
        }

        // 创建关联记录
        CoalSparePartEquipmentDO relation = CoalSparePartEquipmentDO.builder()
                .sparePartId(sparePartId)
                .equipmentId(equipmentId)
                .usageCount(usageCount != null ? usageCount : 0)
                .isRequired(isRequired != null ? isRequired : 1)
                .remark(remark)
                .build();
        
        sparePartEquipmentMapper.insert(relation);
        return relation.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSparePartEquipment(Long id, Integer usageCount, Integer isRequired, String remark) {
        // 校验存在
        CoalSparePartEquipmentDO relation = sparePartEquipmentMapper.selectById(id);
        if (relation == null) {
            throw exception(SPARE_PART_EQUIPMENT_NOT_EXISTS);
        }

        // 更新
        CoalSparePartEquipmentDO updateObj = CoalSparePartEquipmentDO.builder()
                .id(id)
                .usageCount(usageCount)
                .isRequired(isRequired)
                .remark(remark)
                .build();
        
        sparePartEquipmentMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSparePartEquipment(Long id) {
        // 校验存在
        CoalSparePartEquipmentDO relation = sparePartEquipmentMapper.selectById(id);
        if (relation == null) {
            throw exception(SPARE_PART_EQUIPMENT_NOT_EXISTS);
        }

        // 删除
        sparePartEquipmentMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSparePartEquipmentBySparePartId(Long sparePartId) {
        sparePartEquipmentMapper.deleteBySparePartId(sparePartId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSparePartEquipmentByEquipmentId(Long equipmentId) {
        sparePartEquipmentMapper.deleteByEquipmentId(equipmentId);
    }

    @Override
    public List<CoalSparePartEquipmentDO> getSparePartEquipments(Long sparePartId) {
        return sparePartEquipmentMapper.selectListBySparePartId(sparePartId);
    }

    @Override
    public List<CoalSparePartEquipmentDO> getEquipmentSpareParts(Long equipmentId) {
        return sparePartEquipmentMapper.selectListByEquipmentId(equipmentId);
    }

    @Override
    public CoalSparePartEquipmentDO getSparePartEquipment(Long sparePartId, Long equipmentId) {
        return sparePartEquipmentMapper.selectBySparePartIdAndEquipmentId(sparePartId, equipmentId);
    }

    @Override
    public CoalSparePartEquipmentDO getSparePartEquipment(Long id) {
        return sparePartEquipmentMapper.selectById(id);
    }

    @Override
    public PageResult<CoalSparePartEquipmentDO> getSparePartEquipmentPage(CoalSparePartEquipmentPageReqVO pageReqVO) {
        return sparePartEquipmentMapper.selectPage(pageReqVO);
    }

}
