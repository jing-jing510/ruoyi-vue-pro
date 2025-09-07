package cn.iocoder.yudao.module.coal.service.equipmentinfo;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentinfo.vo.EquipmentInfoListReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.equipmentinfo.vo.EquipmentInfoSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.equipmentinfo.EquipmentInfoDO;
import cn.iocoder.yudao.module.coal.dal.mysql.equipmentinfo.EquipmentInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 设备档案 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class EquipmentInfoServiceImpl implements EquipmentInfoService {

    @Resource
    private EquipmentInfoMapper equipmentInfoMapper;

    @Override
    public Long createEquipmentInfo(EquipmentInfoSaveReqVO createReqVO) {
        // 校验父设备ID的有效性
        validateParentEquipmentInfo(null, createReqVO.getParentEquipmentId());
        // 校验设备名称的唯一性
        validateEquipmentInfoEquipmentNameUnique(null, createReqVO.getParentEquipmentId(), createReqVO.getEquipmentName());

        // 插入
        EquipmentInfoDO equipmentInfo = BeanUtils.toBean(createReqVO, EquipmentInfoDO.class);
        equipmentInfoMapper.insert(equipmentInfo);

        // 返回
        return equipmentInfo.getId();
    }

    @Override
    public void updateEquipmentInfo(EquipmentInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateEquipmentInfoExists(updateReqVO.getId());
        // 校验父设备ID的有效性
        validateParentEquipmentInfo(updateReqVO.getId(), updateReqVO.getParentEquipmentId());
        // 校验设备名称的唯一性
        validateEquipmentInfoEquipmentNameUnique(updateReqVO.getId(), updateReqVO.getParentEquipmentId(), updateReqVO.getEquipmentName());

        // 更新
        EquipmentInfoDO updateObj = BeanUtils.toBean(updateReqVO, EquipmentInfoDO.class);
        equipmentInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteEquipmentInfo(Long id) {
        // 校验存在
        validateEquipmentInfoExists(id);
        // 校验是否有子设备档案
        if (equipmentInfoMapper.selectCountByParentEquipmentId(id) > 0) {
            throw exception(EQUIPMENT_INFO_EXITS_CHILDREN);
        }
        // 删除
        equipmentInfoMapper.deleteById(id);
    }


    private void validateEquipmentInfoExists(Long id) {
        if (equipmentInfoMapper.selectById(id) == null) {
            throw exception(EQUIPMENT_INFO_NOT_EXISTS);
        }
    }

    private void validateParentEquipmentInfo(Long id, Long parentEquipmentId) {
        if (parentEquipmentId == null || EquipmentInfoDO.PARENT_EQUIPMENT_ID_ROOT.equals(parentEquipmentId)) {
            return;
        }
        // 1. 不能设置自己为父设备档案
        if (Objects.equals(id, parentEquipmentId)) {
            throw exception(EQUIPMENT_INFO_PARENT_ERROR);
        }
        // 2. 父设备档案不存在
        EquipmentInfoDO parentEquipmentInfo = equipmentInfoMapper.selectById(parentEquipmentId);
        if (parentEquipmentInfo == null) {
            throw exception(EQUIPMENT_INFO_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父设备档案，如果父设备档案是自己的子设备档案，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentEquipmentId = parentEquipmentInfo.getParentEquipmentId();
            if (Objects.equals(id, parentEquipmentId)) {
                throw exception(EQUIPMENT_INFO_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父设备档案
            if (parentEquipmentId == null || EquipmentInfoDO.PARENT_EQUIPMENT_ID_ROOT.equals(parentEquipmentId)) {
                break;
            }
            parentEquipmentInfo = equipmentInfoMapper.selectById(parentEquipmentId);
            if (parentEquipmentInfo == null) {
                break;
            }
        }
    }

    private void validateEquipmentInfoEquipmentNameUnique(Long id, Long parentEquipmentId, String equipmentName) {
        EquipmentInfoDO equipmentInfo = equipmentInfoMapper.selectByParentEquipmentIdAndEquipmentName(parentEquipmentId, equipmentName);
        if (equipmentInfo == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的设备档案
        if (id == null) {
            throw exception(EQUIPMENT_INFO_EQUIPMENT_NAME_DUPLICATE);
        }
        if (!Objects.equals(equipmentInfo.getId(), id)) {
            throw exception(EQUIPMENT_INFO_EQUIPMENT_NAME_DUPLICATE);
        }
    }

    @Override
    public EquipmentInfoDO getEquipmentInfo(Long id) {
        return equipmentInfoMapper.selectById(id);
    }

    @Override
    public List<EquipmentInfoDO> getEquipmentInfoList(EquipmentInfoListReqVO listReqVO) {
        return equipmentInfoMapper.selectList(listReqVO);
    }

}