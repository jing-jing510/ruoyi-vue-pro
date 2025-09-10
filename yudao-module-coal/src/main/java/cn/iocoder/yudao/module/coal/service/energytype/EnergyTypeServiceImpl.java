package cn.iocoder.yudao.module.coal.service.energytype;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.energytype.vo.EnergyTypePageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.energytype.vo.EnergyTypeSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.energytype.EnergyTypeDO;
import cn.iocoder.yudao.module.coal.dal.mysql.energytype.EnergyTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.ENERGY_TYPE_NOT_EXISTS;

/**
 * 能源类型配置 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class EnergyTypeServiceImpl implements EnergyTypeService {

    @Resource
    private EnergyTypeMapper energyTypeMapper;

    @Override
    public Long createEnergyType(EnergyTypeSaveReqVO createReqVO) {
        // 插入
        EnergyTypeDO energyType = BeanUtils.toBean(createReqVO, EnergyTypeDO.class);
        energyTypeMapper.insert(energyType);

        // 返回
        return energyType.getId();
    }

    @Override
    public void updateEnergyType(EnergyTypeSaveReqVO updateReqVO) {
        // 校验存在
        validateEnergyTypeExists(updateReqVO.getId());
        // 更新
        EnergyTypeDO updateObj = BeanUtils.toBean(updateReqVO, EnergyTypeDO.class);
        energyTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteEnergyType(Long id) {
        // 校验存在
        validateEnergyTypeExists(id);
        // 删除
        energyTypeMapper.deleteById(id);
    }

    @Override
        public void deleteEnergyTypeListByIds(List<Long> ids) {
        // 删除
        energyTypeMapper.deleteByIds(ids);
        }


    private void validateEnergyTypeExists(Long id) {
        if (energyTypeMapper.selectById(id) == null) {
            throw exception(ENERGY_TYPE_NOT_EXISTS);
        }
    }

    @Override
    public EnergyTypeDO getEnergyType(Long id) {
        return energyTypeMapper.selectById(id);
    }

    @Override
    public PageResult<EnergyTypeDO> getEnergyTypePage(EnergyTypePageReqVO pageReqVO) {
        return energyTypeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<EnergyTypeDO> getSimpleEnergyTypeList() {
        return energyTypeMapper.selectList();
    }

}