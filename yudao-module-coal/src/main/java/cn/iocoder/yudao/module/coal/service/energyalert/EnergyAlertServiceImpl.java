package cn.iocoder.yudao.module.coal.service.energyalert;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.energyalert.vo.EnergyAlertPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.energyalert.vo.EnergyAlertSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.energyalert.EnergyAlertDO;
import cn.iocoder.yudao.module.coal.dal.mysql.energyalert.EnergyAlertMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.ENERGY_ALERT_NOT_EXISTS;

/**
 * 能源预警记录 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class EnergyAlertServiceImpl implements EnergyAlertService {

    @Resource
    private EnergyAlertMapper energyAlertMapper;

    @Override
    public Long createEnergyAlert(EnergyAlertSaveReqVO createReqVO) {
        // 插入
        EnergyAlertDO energyAlert = BeanUtils.toBean(createReqVO, EnergyAlertDO.class);
        energyAlertMapper.insert(energyAlert);

        // 返回
        return energyAlert.getId();
    }

    @Override
    public void updateEnergyAlert(EnergyAlertSaveReqVO updateReqVO) {
        // 校验存在
        validateEnergyAlertExists(updateReqVO.getId());
        // 更新
        EnergyAlertDO updateObj = BeanUtils.toBean(updateReqVO, EnergyAlertDO.class);
        energyAlertMapper.updateById(updateObj);
    }

    @Override
    public void deleteEnergyAlert(Long id) {
        // 校验存在
        validateEnergyAlertExists(id);
        // 删除
        energyAlertMapper.deleteById(id);
    }

    @Override
        public void deleteEnergyAlertListByIds(List<Long> ids) {
        // 删除
        energyAlertMapper.deleteByIds(ids);
        }


    private void validateEnergyAlertExists(Long id) {
        if (energyAlertMapper.selectById(id) == null) {
            throw exception(ENERGY_ALERT_NOT_EXISTS);
        }
    }

    @Override
    public EnergyAlertDO getEnergyAlert(Long id) {
        return energyAlertMapper.selectById(id);
    }

    @Override
    public PageResult<EnergyAlertDO> getEnergyAlertPage(EnergyAlertPageReqVO pageReqVO) {
        return energyAlertMapper.selectPage(pageReqVO);
    }

}