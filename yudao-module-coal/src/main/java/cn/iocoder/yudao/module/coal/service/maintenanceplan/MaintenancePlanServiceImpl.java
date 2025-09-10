package cn.iocoder.yudao.module.coal.service.maintenanceplan;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceplan.vo.MaintenancePlanPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.maintenanceplan.vo.MaintenancePlanSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.maintenanceplan.MaintenancePlanDO;
import cn.iocoder.yudao.module.coal.dal.mysql.maintenanceplan.MaintenancePlanMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.MAINTENANCE_PLAN_NOT_EXISTS;

/**
 * 检修计划 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class MaintenancePlanServiceImpl implements MaintenancePlanService {

    @Resource
    private MaintenancePlanMapper maintenancePlanMapper;

    @Override
    public Long createMaintenancePlan(MaintenancePlanSaveReqVO createReqVO) {
        // 插入
        MaintenancePlanDO maintenancePlan = BeanUtils.toBean(createReqVO, MaintenancePlanDO.class);
        maintenancePlanMapper.insert(maintenancePlan);

        // 返回
        return maintenancePlan.getId();
    }

    @Override
    public void updateMaintenancePlan(MaintenancePlanSaveReqVO updateReqVO) {
        // 校验存在
        validateMaintenancePlanExists(updateReqVO.getId());
        // 更新
        MaintenancePlanDO updateObj = BeanUtils.toBean(updateReqVO, MaintenancePlanDO.class);
        maintenancePlanMapper.updateById(updateObj);
    }

    @Override
    public void deleteMaintenancePlan(Long id) {
        // 校验存在
        validateMaintenancePlanExists(id);
        // 删除
        maintenancePlanMapper.deleteById(id);
    }

    @Override
        public void deleteMaintenancePlanListByIds(List<Long> ids) {
        // 删除
        maintenancePlanMapper.deleteByIds(ids);
        }


    private void validateMaintenancePlanExists(Long id) {
        if (maintenancePlanMapper.selectById(id) == null) {
            throw exception(MAINTENANCE_PLAN_NOT_EXISTS);
        }
    }

    @Override
    public MaintenancePlanDO getMaintenancePlan(Long id) {
        return maintenancePlanMapper.selectById(id);
    }

    @Override
    public PageResult<MaintenancePlanDO> getMaintenancePlanPage(MaintenancePlanPageReqVO pageReqVO) {
        return maintenancePlanMapper.selectPage(pageReqVO);
    }

}