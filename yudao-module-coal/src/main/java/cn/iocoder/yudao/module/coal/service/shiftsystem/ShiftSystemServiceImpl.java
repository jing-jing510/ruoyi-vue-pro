package cn.iocoder.yudao.module.coal.service.shiftsystem;

import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.shiftsystem.vo.ShiftSystemListReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.shiftsystem.vo.ShiftSystemSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.shiftsystem.ShiftSystemDO;
import cn.iocoder.yudao.module.coal.dal.mysql.shiftsystem.ShiftSystemMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 班制与班次设置 (树表) Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ShiftSystemServiceImpl implements ShiftSystemService {

    @Resource
    private ShiftSystemMapper shiftSystemMapper;

    @Override
    public Long createShiftSystem(ShiftSystemSaveReqVO createReqVO) {
        // 校验父ID的有效性
        validateParentShiftSystem(null, createReqVO.getParentId());
        // 校验名称 的唯一性
        validateShiftSystemNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入
        ShiftSystemDO shiftSystem = BeanUtils.toBean(createReqVO, ShiftSystemDO.class);
        shiftSystemMapper.insert(shiftSystem);

        // 返回
        return shiftSystem.getId();
    }

    @Override
    public void updateShiftSystem(ShiftSystemSaveReqVO updateReqVO) {
        // 校验存在
        validateShiftSystemExists(updateReqVO.getId());
        // 校验父ID的有效性
        validateParentShiftSystem(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验名称 的唯一性
        validateShiftSystemNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新
        ShiftSystemDO updateObj = BeanUtils.toBean(updateReqVO, ShiftSystemDO.class);
        shiftSystemMapper.updateById(updateObj);
    }

    @Override
    public void deleteShiftSystem(Long id) {
        // 校验存在
        validateShiftSystemExists(id);
        // 校验是否有子班制与班次设置 (树表)
        if (shiftSystemMapper.selectCountByParentId(id) > 0) {
            throw exception(SHIFT_SYSTEM_EXITS_CHILDREN);
        }
        // 删除
        shiftSystemMapper.deleteById(id);
    }


    private void validateShiftSystemExists(Long id) {
        if (shiftSystemMapper.selectById(id) == null) {
            throw exception(SHIFT_SYSTEM_NOT_EXISTS);
        }
    }

    private void validateParentShiftSystem(Long id, Long parentId) {
        if (parentId == null || ShiftSystemDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父班制与班次设置 (树表)
        if (Objects.equals(id, parentId)) {
            throw exception(SHIFT_SYSTEM_PARENT_ERROR);
        }
        // 2. 父班制与班次设置 (树表)不存在
        ShiftSystemDO parentShiftSystem = shiftSystemMapper.selectById(parentId);
        if (parentShiftSystem == null) {
            throw exception(SHIFT_SYSTEM_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父班制与班次设置 (树表)，如果父班制与班次设置 (树表)是自己的子班制与班次设置 (树表)，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentShiftSystem.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(SHIFT_SYSTEM_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父班制与班次设置 (树表)
            if (parentId == null || ShiftSystemDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentShiftSystem = shiftSystemMapper.selectById(parentId);
            if (parentShiftSystem == null) {
                break;
            }
        }
    }

    private void validateShiftSystemNameUnique(Long id, Long parentId, String name) {
        ShiftSystemDO shiftSystem = shiftSystemMapper.selectByParentIdAndName(parentId, name);
        if (shiftSystem == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的班制与班次设置 (树表)
        if (id == null) {
            throw exception(SHIFT_SYSTEM_NAME_DUPLICATE);
        }
        if (!Objects.equals(shiftSystem.getId(), id)) {
            throw exception(SHIFT_SYSTEM_NAME_DUPLICATE);
        }
    }

    @Override
    public ShiftSystemDO getShiftSystem(Long id) {
        return shiftSystemMapper.selectById(id);
    }

    @Override
    public List<ShiftSystemDO> getShiftSystemList(ShiftSystemListReqVO listReqVO) {
        return shiftSystemMapper.selectList(listReqVO);
    }

}