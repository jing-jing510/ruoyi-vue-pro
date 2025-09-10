package cn.iocoder.yudao.module.coal.service.sparepartequipment;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.coal.controller.admin.sparepartequipment.vo.*;
import cn.iocoder.yudao.module.coal.dal.dataobject.sparepartequipment.SparePartEquipmentDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.coal.dal.mysql.sparepartequipment.SparePartEquipmentMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.SPARE_PART_EQUIPMENT_NOT_EXISTS;

/**
 * 备件设备关联 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SparePartEquipmentServiceImpl implements SparePartEquipmentService {

    @Resource
    private SparePartEquipmentMapper sparePartEquipmentMapper;

    @Override
    public Long createSparePartEquipment(SparePartEquipmentSaveReqVO createReqVO) {
        // 插入
        SparePartEquipmentDO sparePartEquipment = BeanUtils.toBean(createReqVO, SparePartEquipmentDO.class);
        sparePartEquipmentMapper.insert(sparePartEquipment);
        // 返回
        return sparePartEquipment.getId();
    }

    @Override
    public void updateSparePartEquipment(SparePartEquipmentSaveReqVO updateReqVO) {
        // 校验存在
        validateSparePartEquipmentExists(updateReqVO.getId());
        // 更新
        SparePartEquipmentDO updateObj = BeanUtils.toBean(updateReqVO, SparePartEquipmentDO.class);
        sparePartEquipmentMapper.updateById(updateObj);
    }

    @Override
    public void deleteSparePartEquipment(Long id) {
        // 校验存在
        validateSparePartEquipmentExists(id);
        // 删除
        sparePartEquipmentMapper.deleteById(id);
    }

    private void validateSparePartEquipmentExists(Long id) {
        if (sparePartEquipmentMapper.selectById(id) == null) {
            throw exception(SPARE_PART_EQUIPMENT_NOT_EXISTS);
        }
    }

    @Override
    public SparePartEquipmentDO getSparePartEquipment(Long id) {
        return sparePartEquipmentMapper.selectById(id);
    }

    @Override
    public PageResult<SparePartEquipmentDO> getSparePartEquipmentPage(SparePartEquipmentPageReqVO pageReqVO) {
        return sparePartEquipmentMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SparePartEquipmentDO> getSparePartEquipmentList(SparePartEquipmentPageReqVO exportReqVO) {
        return sparePartEquipmentMapper.selectList(exportReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSparePartEquipmentList(Long sparePartId, List<SparePartEquipmentSaveReqVO> createReqList) {
        if (createReqList == null || createReqList.isEmpty()) {
            return;
        }
        
        // 批量插入
        for (SparePartEquipmentSaveReqVO createReqVO : createReqList) {
            createReqVO.setSparePartId(sparePartId);
            createSparePartEquipment(createReqVO);
        }
    }

    @Override
    public List<SparePartEquipmentDO> getSparePartEquipmentListBySparePartId(Long sparePartId) {
        return sparePartEquipmentMapper.selectListBySparePartId(sparePartId);
    }

    @Override
    public List<SparePartEquipmentDO> getSparePartEquipmentListByEquipmentId(Long equipmentId) {
        return sparePartEquipmentMapper.selectListByEquipmentId(equipmentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSparePartEquipmentBySparePartId(Long sparePartId) {
        sparePartEquipmentMapper.deleteBySparePartId(sparePartId);
    }

}
