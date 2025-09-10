package cn.iocoder.yudao.module.coal.service.energystatistics;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.coal.controller.admin.energystatistics.vo.EnergyStatisticsPageReqVO;
import cn.iocoder.yudao.module.coal.controller.admin.energystatistics.vo.EnergyStatisticsSaveReqVO;
import cn.iocoder.yudao.module.coal.dal.dataobject.energystatistics.EnergyStatisticsDO;
import cn.iocoder.yudao.module.coal.dal.mysql.energystatistics.EnergyStatisticsMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.ENERGY_STATISTICS_NOT_EXISTS;

/**
 * 能源消耗统计 Service 实现类
 *
 * @author 京京
 */
@Service
@Validated
public class EnergyStatisticsServiceImpl implements EnergyStatisticsService {

    @Resource
    private EnergyStatisticsMapper energyStatisticsMapper;

    @Override
    public Long createEnergyStatistics(EnergyStatisticsSaveReqVO createReqVO) {
        // 插入
        EnergyStatisticsDO energyStatistics = BeanUtils.toBean(createReqVO, EnergyStatisticsDO.class);
        energyStatisticsMapper.insert(energyStatistics);

        // 返回
        return energyStatistics.getId();
    }

    @Override
    public void updateEnergyStatistics(EnergyStatisticsSaveReqVO updateReqVO) {
        // 校验存在
        validateEnergyStatisticsExists(updateReqVO.getId());
        // 更新
        EnergyStatisticsDO updateObj = BeanUtils.toBean(updateReqVO, EnergyStatisticsDO.class);
        energyStatisticsMapper.updateById(updateObj);
    }

    @Override
    public void deleteEnergyStatistics(Long id) {
        // 校验存在
        validateEnergyStatisticsExists(id);
        // 删除
        energyStatisticsMapper.deleteById(id);
    }

    @Override
        public void deleteEnergyStatisticsListByIds(List<Long> ids) {
        // 删除
        energyStatisticsMapper.deleteByIds(ids);
        }


    private void validateEnergyStatisticsExists(Long id) {
        if (energyStatisticsMapper.selectById(id) == null) {
            throw exception(ENERGY_STATISTICS_NOT_EXISTS);
        }
    }

    @Override
    public EnergyStatisticsDO getEnergyStatistics(Long id) {
        return energyStatisticsMapper.selectById(id);
    }

    @Override
    public PageResult<EnergyStatisticsDO> getEnergyStatisticsPage(EnergyStatisticsPageReqVO pageReqVO) {
        return energyStatisticsMapper.selectPage(pageReqVO);
    }

    @Override
    public Double calculateComparisonWithPlan(Long energyTypeId, String statisticsDate, Integer statisticsType, Double actualValue) {
        if (actualValue == null || actualValue == 0) {
            return 0.0;
        }
        
        // 获取计划值
        Double planValue = energyStatisticsMapper.selectPlanValue(energyTypeId, statisticsDate, statisticsType);
        
        if (planValue == null || planValue == 0) {
            return 0.0;
        }
        
        // 计算与计划对比百分比：(实际值 - 计划值) / 计划值 * 100
        return Math.round(((actualValue - planValue) / planValue * 100) * 100.0) / 100.0;
    }

    @Override
    public Double calculateComparisonWithLastPeriod(Long energyTypeId, String statisticsDate, Integer statisticsType, Double currentValue) {
        if (currentValue == null || currentValue == 0) {
            return 0.0;
        }
        
        // 获取上期值
        Double lastPeriodValue = energyStatisticsMapper.selectLastPeriodValue(energyTypeId, statisticsDate, statisticsType);
        
        if (lastPeriodValue == null || lastPeriodValue == 0) {
            return 0.0;
        }
        
        // 计算与上期对比百分比：(当前值 - 上期值) / 上期值 * 100
        return Math.round(((currentValue - lastPeriodValue) / lastPeriodValue * 100) * 100.0) / 100.0;
    }

}